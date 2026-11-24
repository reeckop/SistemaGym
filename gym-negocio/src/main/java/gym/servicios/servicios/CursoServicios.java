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
 * Gestiona las reglas de negocio de curso.
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
            List<Curso> cursosExistentes = unitOfWork.cursoDAO().obtenerTodos();
            java.util.Map<String, Curso> cursosPorId = new java.util.HashMap<>();
            java.util.Map<String, Curso> cursosPorNombre = new java.util.HashMap<>();
            if (cursosExistentes != null) {
                for (Curso curso : cursosExistentes) {
                    if (curso != null) {
                        cursosPorId.put(curso.getIdCurso(), curso);
                        if (curso.getNombre() != null) {
                            cursosPorNombre.put(
                                    curso.getNombre().trim().toLowerCase(java.util.Locale.ROOT),
                                    curso);
                        }
                    }
                }
            }

            for (CursoDTO inicial : cursosIniciales()) {
                Curso existente = cursosPorId.get(inicial.getIdCurso());
                if (existente == null) {
                    String nombre = inicial.getNombre().trim().toLowerCase(java.util.Locale.ROOT);
                    existente = cursosPorNombre.get(nombre);
                }
                if (existente == null) {
                    unitOfWork.cursoDAO().guardar(Mappers.convertirAEntidad(inicial));
                } else if (!inicial.getImagen().equals(existente.getImagen())) {
                    existente.setImagen(inicial.getImagen());
                    unitOfWork.cursoDAO().actualizar(existente);
                }
            }

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

    private List<CursoDTO> cursosIniciales() {
        java.time.LocalDate primerDia = java.time.LocalDate.now().plusDays(1);
        while (primerDia.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || primerDia.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) primerDia = primerDia.plusDays(1);
        List<CursoDTO> cursos = new ArrayList<>();
        String[][] catalogo = {
            {"Box", "350", "20", "/gym/cursos/box.png"},
            {"Spinning", "400", "18", "/gym/cursos/spinning.png"},
            {"Yoga", "300", "20", "/gym/cursos/yoga.png"},
            {"Zumba", "320", "25", "/gym/cursos/zumba.png"}
        };
        for (int i = 0; i < catalogo.length; i++) {
            java.time.LocalTime inicio = java.time.LocalTime.of(8 + (i % 3) * 2, 0);
            java.util.List<gym.servicios.dto.HorarioDTO> horarios = java.util.List.of(
                    new gym.servicios.dto.HorarioDTO(String.format("672000000000000000000%03d", i * 2 + 1), primerDia, inicio, inicio.plusHours(2)),
                    new gym.servicios.dto.HorarioDTO(String.format("672000000000000000000%03d", i * 2 + 2), primerDia.plusDays(1), inicio.plusHours(2), inicio.plusHours(4)));
            cursos.add(new CursoDTO(String.format("673000000000000000000%03d", i + 1), catalogo[i][0], Double.parseDouble(catalogo[i][1]), Integer.parseInt(catalogo[i][2]), catalogo[i][3], horarios));
        }
        return cursos;
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
