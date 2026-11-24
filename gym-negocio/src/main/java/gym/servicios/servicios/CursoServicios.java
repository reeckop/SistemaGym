/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Curso;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.IUnitOfWorkFactory;
import gym.persistencia.unitofwork.UnitOfWorkFactory;
import gym.servicios.dto.CursoDTO;
import gym.servicios.interfaces.ICursoServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class CursoServicios implements ICursoServicios {
    private final IUnitOfWorkFactory unidadDeTrabajoFactory;

    public CursoServicios() {
        this(UnitOfWorkFactory.obtenerInstancia());
    }

    public CursoServicios(IUnitOfWorkFactory unidadDeTrabajoFactory) {
        this.unidadDeTrabajoFactory = java.util.Objects.requireNonNull(unidadDeTrabajoFactory);
    }

    @Override
    public void registrarCurso(CursoDTO curso) throws NegocioException {
        validarCurso(curso);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            unitOfWork.cursoDAO().guardar(Mappers.convertirAEntidad(curso));
            unitOfWork.commit();
        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar el curso.", e);
        }
    }

    @Override
    public void actualizarCurso(CursoDTO curso) throws NegocioException {
        validarCurso(curso);
        validarId(curso.getIdCurso());

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            unitOfWork.cursoDAO().actualizar(Mappers.convertirAEntidad(curso));
            unitOfWork.commit();
        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible actualizar el curso.", e);
        }
    }

    @Override
    public void eliminarCurso(String id) throws NegocioException {
        validarId(id);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            unitOfWork.cursoDAO().eliminar(id);
            unitOfWork.commit();
        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible eliminar el curso.", e);
        }
    }

    @Override
    public List<CursoDTO> consultarTodosLosCursos() throws NegocioException {
        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            List<CursoDTO> resultado = new ArrayList<>();
            List<Curso> cursos = unitOfWork.cursoDAO().obtenerTodos();

            if (cursos != null) {
                for (Curso curso : cursos) {
                    if (curso != null) {
                        resultado.add(Mappers.convertirADTO(curso));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar los cursos.", e);
        }
    }

    private void validarCurso(CursoDTO curso) throws NegocioException {
        if (curso == null) {
            throw new NegocioException("Los datos del curso son obligatorios.");
        }

        if (curso.getNombre() == null || curso.getNombre().isBlank()) {
            throw new NegocioException("El nombre del curso es obligatorio.");
        }

        if (curso.getPrecio() < 0) {
            throw new NegocioException("El precio no puede ser negativo.");
        }

        if (curso.getCupo() <= 0) {
            throw new NegocioException("El cupo debe ser mayor que cero.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de curso es obligatorio.");
        }
    }
}
