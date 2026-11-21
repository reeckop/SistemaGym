package gym.persistencia.uow;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import gym.infraestructura.persistencia.MongoConnectionManager;
import gym.persistencia.excepciones.PersistenciaException;

public abstract class UnitOfWork<U extends UnitOfWork<U>> {
    private final ClientSession sesion;

    protected UnitOfWork(ClientSession sesion) {
        this.sesion = sesion;
    }

    protected abstract U conSesion(ClientSession sesion);

    @FunctionalInterface
    public interface Trabajo<U, R, E extends Exception> {
        R ejecutar(U unidad) throws PersistenciaException, E;
    }

    /**
     * Confirma todo el trabajo al terminar. Si falla, aborta la transacción.
     * Los repositorios recibidos por el trabajo solo se usan dentro de la llamada.
     */
    public final <R, E extends Exception> R enTransaccion(Trabajo<U, R, E> trabajo)
            throws PersistenciaException, E {
        if (sesion != null) {
            return trabajo.ejecutar(conSesion(sesion));
        }

        try (ClientSession nuevaSesion = MongoConnectionManager.obtenerConexion().startSession()) {
            nuevaSesion.startTransaction();
            try {
                R resultado = trabajo.ejecutar(conSesion(nuevaSesion));
                nuevaSesion.commitTransaction();
                return resultado;
            } catch (Exception | Error error) {
                if (nuevaSesion.hasActiveTransaction()) {
                    try {
                        nuevaSesion.abortTransaction();
                    } catch (RuntimeException falloAlAbortar) {
                        error.addSuppressed(falloAlAbortar);
                    }
                }
                throw error;
            }
        } catch (MongoException error) {
            throw new PersistenciaException("No fue posible completar la transacción.", error);
        }
    }
}
