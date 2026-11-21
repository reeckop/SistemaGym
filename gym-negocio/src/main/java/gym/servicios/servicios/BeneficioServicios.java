/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Beneficio;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.facade.IPersistenciaFachada;
import gym.servicios.dto.BeneficioDTO;
import gym.servicios.interfaces.IBeneficioServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class BeneficioServicios implements IBeneficioServicios {

    private final IPersistenciaFachada fachada;

    public BeneficioServicios(IPersistenciaFachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void registrarBeneficio(BeneficioDTO beneficio) throws NegocioException {
        validarBeneficio(beneficio);
        try {
            fachada.guardarBeneficio(Mappers.convertirAEntidad(beneficio));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible registrar el beneficio.", e);
        }
    }

    @Override
    public void actualizarBeneficio(BeneficioDTO beneficio) throws NegocioException {
        validarBeneficio(beneficio);
        validarId(beneficio.getIdBeneficio());
        try {
            if (fachada.obtenerBeneficioPorId(beneficio.getIdBeneficio()) == null) {
                throw new NegocioException("El beneficio no existe.");
            }

            fachada.actualizarBeneficio(Mappers.convertirAEntidad(beneficio));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible actualizar el beneficio.", e);
        }
    }

    @Override
    public void eliminarBeneficio(String id) throws NegocioException {
        validarId(id);
        try {
            if (fachada.obtenerBeneficioPorId(id) == null) {
                throw new NegocioException("El beneficio no existe.");
            }

            fachada.eliminarBeneficio(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible eliminar el beneficio.", e);
        }
    }

    @Override
    public BeneficioDTO consultarBeneficio(String id) throws NegocioException {
        validarId(id);
        try {
            Beneficio beneficio = fachada.obtenerBeneficioPorId(id);

            if (beneficio == null) {
                throw new NegocioException("El beneficio no existe.");
            }

            return Mappers.convertirADTO(beneficio);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar el beneficio.", e);
        }
    }

    @Override
    public List<BeneficioDTO> consultarTodosLosBeneficios() throws NegocioException {
        try {
            List<BeneficioDTO> resultado = new ArrayList<>();
            List<Beneficio> beneficios = fachada.obtenerTodosLosBeneficios();

            if (beneficios != null) {
                for (Beneficio beneficio : beneficios) {
                    if (beneficio != null) {
                        resultado.add(Mappers.convertirADTO(beneficio));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar los beneficios.", e);
        }
    }

    private void validarBeneficio(BeneficioDTO beneficio) throws NegocioException {
        if (beneficio == null) {
            throw new NegocioException("Los datos del beneficio son obligatorios.");
        }

        if (beneficio.getNombre() == null || beneficio.getNombre().isBlank()) {
            throw new NegocioException("El nombre del beneficio es obligatorio.");
        }

        if (beneficio.getValor() < 0) {
            throw new NegocioException("El valor no puede ser negativo.");
        }

        if (beneficio.getUnidad() == null || beneficio.getUnidad().isBlank()) {
            throw new NegocioException("La unidad del beneficio es obligatoria.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de beneficio es obligatorio.");
        }
    }
}
