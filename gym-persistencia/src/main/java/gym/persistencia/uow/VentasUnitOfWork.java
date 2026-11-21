package gym.persistencia.uow;

import com.mongodb.client.ClientSession;
import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.dao.PagoDAO;
import gym.persistencia.dao.ProductosDAO;
import gym.persistencia.dao.VentasDAO;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.interfaces.IPagoDAO;
import gym.persistencia.interfaces.IProductoDAO;
import gym.persistencia.interfaces.IVentaDAO;

public final class VentasUnitOfWork extends UnitOfWork<VentasUnitOfWork> {
    private final IVentaDAO ventas;
    private final IProductoDAO productos;
    private final IPagoDAO pagos;
    private final IClienteDAO clientes;

    public VentasUnitOfWork() {
        this(null);
    }

    private VentasUnitOfWork(ClientSession sesion) {
        super(sesion);
        ventas = new VentasDAO(sesion);
        productos = new ProductosDAO(sesion);
        pagos = new PagoDAO(sesion);
        clientes = new ClienteDAO(sesion);
    }

    @Override
    protected VentasUnitOfWork conSesion(ClientSession sesion) {
        return new VentasUnitOfWork(sesion);
    }

    public IVentaDAO ventas() {
        return ventas;
    }

    public IProductoDAO productos() {
        return productos;
    }

    public IPagoDAO pagos() {
        return pagos;
    }

    public IClienteDAO clientes() {
        return clientes;
    }
}
