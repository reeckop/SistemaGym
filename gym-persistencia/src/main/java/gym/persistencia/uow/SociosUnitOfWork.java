package gym.persistencia.uow;

import com.mongodb.client.ClientSession;
import gym.persistencia.dao.AccesoDAO;
import gym.persistencia.dao.BeneficioDAO;
import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.dao.MembresiaDAO;
import gym.persistencia.interfaces.IAccesoDAO;
import gym.persistencia.interfaces.IBeneficioDAO;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.interfaces.IMembresiaDAO;

public final class SociosUnitOfWork extends UnitOfWork<SociosUnitOfWork> {
    private final IClienteDAO clientes;
    private final IMembresiaDAO membresias;
    private final IBeneficioDAO beneficios;
    private final IAccesoDAO accesos;

    public SociosUnitOfWork() {
        this(null);
    }

    private SociosUnitOfWork(ClientSession sesion) {
        super(sesion);
        clientes = new ClienteDAO(sesion);
        membresias = new MembresiaDAO(sesion);
        beneficios = new BeneficioDAO(sesion);
        accesos = new AccesoDAO(sesion);
    }

    @Override
    protected SociosUnitOfWork conSesion(ClientSession sesion) {
        return new SociosUnitOfWork(sesion);
    }

    public IClienteDAO clientes() {
        return clientes;
    }

    public IMembresiaDAO membresias() {
        return membresias;
    }

    public IBeneficioDAO beneficios() {
        return beneficios;
    }

    public IAccesoDAO accesos() {
        return accesos;
    }
}
