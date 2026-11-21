package gym.persistencia.uow;

import com.mongodb.client.ClientSession;
import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.dao.NotificacionDAO;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.interfaces.INotificacionDAO;

public final class NotificacionesUnitOfWork extends UnitOfWork<NotificacionesUnitOfWork> {
    private final INotificacionDAO notificaciones;
    private final IClienteDAO clientes;

    public NotificacionesUnitOfWork() {
        this(null);
    }

    private NotificacionesUnitOfWork(ClientSession sesion) {
        super(sesion);
        notificaciones = new NotificacionDAO(sesion);
        clientes = new ClienteDAO(sesion);
    }

    @Override
    protected NotificacionesUnitOfWork conSesion(ClientSession sesion) {
        return new NotificacionesUnitOfWork(sesion);
    }

    public INotificacionDAO notificaciones() {
        return notificaciones;
    }

    public IClienteDAO clientes() {
        return clientes;
    }
}
