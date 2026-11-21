/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Pago;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.uow.VentasUnitOfWork;
import gym.servicios.dto.PagoDTO;
import gym.servicios.interfaces.IPagoServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class PagoServicios implements IPagoServicios {

    private final VentasUnitOfWork unidad;

    public PagoServicios(VentasUnitOfWork unidad) {
        this.unidad = unidad;
    }

    @Override
    public void registrarPago(PagoDTO pago) throws NegocioException {
        validarPago(pago);

        try {
            unidad.pagos().guardar(Mappers.convertirAEntidad(pago));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible registrar el pago.", e);
        }
    }

    @Override
    public void actualizarPago(PagoDTO pago) throws NegocioException {
        validarPago(pago);
        validarId(pago.getIdPago());

        try {
            if (unidad.pagos().obtenerPorId(pago.getIdPago()) == null) {
                throw new NegocioException("El pago no existe.");
            }

            unidad.pagos().actualizar(Mappers.convertirAEntidad(pago));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible actualizar el pago.", e);
        }
    }

    @Override
    public PagoDTO consultarPago(String id) throws NegocioException {
        validarId(id);

        try {
            Pago pago = unidad.pagos().obtenerPorId(id);

            if (pago == null) {
                throw new NegocioException("El pago no existe.");
            }

            return Mappers.convertirADTO(pago);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar el pago.", e);
        }
    }

    @Override
    public List<PagoDTO> consultarTodosLosPagos() throws NegocioException {
        try {
            List<PagoDTO> resultado = new ArrayList<>();
            List<Pago> pagos = unidad.pagos().obtenerTodos();

            if (pagos != null) {
                for (Pago pago : pagos) {
                    if (pago != null) {
                        resultado.add(Mappers.convertirADTO(pago));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar los pagos.", e);
        }
    }

    private void validarPago(PagoDTO pago) throws NegocioException {
        if (pago == null) {
            throw new NegocioException("Los datos del pago son obligatorios.");
        }

        if (pago.getMonto() < 0) {
            throw new NegocioException("El monto no puede ser negativo.");
        }

        if (pago.getFechaHora() == null) {
            throw new NegocioException("La fecha del pago es obligatoria.");
        }

        if (pago.getEstado() == null) {
            throw new NegocioException("El estado del pago es obligatorio.");
        }

        if (pago.getMetodoPago() == null || pago.getMetodoPago().getNombre() == null || pago.getMetodoPago().getNombre().isBlank()) {
            throw new NegocioException("El método de pago es obligatorio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de pago es obligatorio.");
        }
    }
}
