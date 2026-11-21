package gym.persistencia.uow;

import com.mongodb.client.ClientSession;
import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.dao.CursoDAO;
import gym.persistencia.dao.InscripcionDAO;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.interfaces.ICursoDAO;
import gym.persistencia.interfaces.IInscripcionDAO;

public final class CursosUnitOfWork extends UnitOfWork<CursosUnitOfWork> {
    private final ICursoDAO cursos;
    private final IInscripcionDAO inscripciones;
    private final IClienteDAO clientes;

    public CursosUnitOfWork() {
        this(null);
    }

    private CursosUnitOfWork(ClientSession sesion) {
        super(sesion);
        cursos = new CursoDAO(sesion);
        inscripciones = new InscripcionDAO(sesion);
        clientes = new ClienteDAO(sesion);
    }

    @Override
    protected CursosUnitOfWork conSesion(ClientSession sesion) {
        return new CursosUnitOfWork(sesion);
    }

    public ICursoDAO cursos() {
        return cursos;
    }

    public IInscripcionDAO inscripciones() {
        return inscripciones;
    }

    public IClienteDAO clientes() {
        return clientes;
    }
}
