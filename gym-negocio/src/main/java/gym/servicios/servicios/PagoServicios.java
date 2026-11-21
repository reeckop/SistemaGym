/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Pago;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.UnitOfWork;
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

    @Override
    public void registrarPago(PagoDTO pago) throws NegocioException {
        validarPago(pago);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            unitOfWork.pagoDAO().guardar(Mappers.convertirAEntidad(pago));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar el pago.", e);
        }
    }

    @Override
    public void actualizarPago(PagoDTO pago) throws NegocioException {
        validarPago(pago);
        validarId(pago.getIdPago());

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            if (unitOfWork.pagoDAO().obtenerPorId(pago.getIdPago()) == null) {
                throw new NegocioException("El pago no existe.");
            }

            unitOfWork.pagoDAO().actualizar(Mappers.convertirAEntidad(pago));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible actualizar el pago.", e);
        }
    }

    @Override
    public PagoDTO consultarPago(String id) throws NegocioException {
        validarId(id);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Pago pago = unitOfWork.pagoDAO().obtenerPorId(id);

            if (pago == null) {
                throw new NegocioException("El pago no existe.");
            }

            PagoDTO resultado = Mappers.convertirADTO(pago);
            unitOfWork.commit();

            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar el pago.", e);
        }
    }

    @Override
    public List<PagoDTO> consultarTodosLosPagos() throws NegocioException {
        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            List<PagoDTO> resultado = new ArrayList<>();
            List<Pago> pagos = unitOfWork.pagoDAO().obtenerTodos();

            if (pagos != null) {
                for (Pago pago : pagos) {
                    if (pago != null) {
                        resultado.add(Mappers.convertirADTO(pago));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
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

        if (pago.getMetodoPago() == null || pago.getMetodoPago().getNombre() == null
                || pago.getMetodoPago().getNombre().isBlank()) {
            throw new NegocioException("El metodo de pago es obligatorio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de pago es obligatorio.");
        }
    }
}
