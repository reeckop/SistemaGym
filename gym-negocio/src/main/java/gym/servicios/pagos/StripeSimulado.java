package gym.servicios.pagos;

/**
 *  Simulación local de Stripe para pruebas de interfaz, sin cobros ni llamadas externas.
 *
 * @author PC GAMER MASTER RACE
 */
public final class StripeSimulado {
    public ResultadoPago procesar(double monto, boolean simularAprobacion) {
        if (!Double.isFinite(monto) || monto <= 0 || !simularAprobacion) {
            return ResultadoPago.RECHAZADO;
        }
        return ResultadoPago.APROBADO;
    }
}
