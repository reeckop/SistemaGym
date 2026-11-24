package gym.persistencia.unitofwork;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public final class UnitOfWorkFactory implements IUnitOfWorkFactory {
    private static final UnitOfWorkFactory INSTANCIA = new UnitOfWorkFactory();

    private UnitOfWorkFactory() {
    }

    public static UnitOfWorkFactory obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public IUnitOfWork crear() {
        return new UnitOfWork();
    }
}
