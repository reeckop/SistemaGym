package gym.persistencia.unitofwork;

/**
 * Define la creación de unidades de trabajo para las operaciones de persistencia.
 *
 * @author PC GAMER MASTER RACE
 */
@FunctionalInterface
public interface IUnitOfWorkFactory {
    IUnitOfWork crear();
}
