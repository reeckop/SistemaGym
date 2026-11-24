package gym.persistencia.unitofwork;
/**
 *
 * @author PC GAMER MASTER RACE
 */

/** Crea una unidad de trabajo nueva para cada operación. */
@FunctionalInterface
public interface IUnitOfWorkFactory {
    IUnitOfWork crear();
}
