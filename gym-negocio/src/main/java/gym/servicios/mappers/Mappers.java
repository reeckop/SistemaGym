/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.mappers;

import gym.persistencia.entidades.Beneficio;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Curso;
import gym.persistencia.entidades.DetalleVenta;
import gym.persistencia.entidades.Empleado;
import gym.persistencia.entidades.Horario;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.entidades.MetodoPago;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.entidades.Pago;
import gym.persistencia.entidades.Producto;
import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.entidades.Venta;
import gym.servicios.dto.BeneficioDTO;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.dto.CursoDTO;
import gym.servicios.dto.DetalleVentaDTO;
import gym.servicios.dto.EmpleadoDTO;
import gym.servicios.dto.HorarioDTO;
import gym.servicios.dto.InscripcionDTO;
import gym.servicios.dto.MembresiaDTO;
import gym.servicios.dto.MetodoPagoDTO;
import gym.servicios.dto.NotificacionDTO;
import gym.servicios.dto.PagoDTO;
import gym.servicios.dto.ProductoDTO;
import gym.servicios.dto.RegistroIngresoDTO;
import gym.servicios.dto.VentaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Convierte los objetos de persistencia en DTO y viceversa.
 *
 * @author PC GAMER MASTER RACE
 */
public class Mappers {
    // CLIENTE
    public static Cliente convertirAEntidad(ClienteDTO dto, Membresia membresia) {
        if (dto == null) return null;

        Cliente entidad = new Cliente();
        entidad.setIdCliente(dto.getIdCliente());
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTelefono(dto.getTelefono());
        entidad.setFechaIngreso(dto.getFechaIngreso());
        entidad.setMembresia(membresia);
        entidad.setFechaInicioMembresia(dto.getFechaInicioMembresia());
        entidad.setFechaFinMembresia(dto.getFechaFinMembresia());

        return entidad;
    }

    public static ClienteDTO convertirADTO(Cliente entidad) {
        if (entidad == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(entidad.getIdCliente());
        dto.setNombre(entidad.getNombre());
        dto.setApellido(entidad.getApellido());
        dto.setTelefono(entidad.getTelefono());
        dto.setFechaIngreso(entidad.getFechaIngreso());
        dto.setFechaInicioMembresia(entidad.getFechaInicioMembresia());
        dto.setFechaFinMembresia(entidad.getFechaFinMembresia());

        if (entidad.getMembresia() != null) {
            dto.setIdMembresia(entidad.getMembresia().getIdMembresia());
        }

        return dto;
    }

    // BENEFICIO
    public static Beneficio convertirAEntidad(BeneficioDTO dto) {
        if (dto == null) return null;

        return new Beneficio(
                dto.getIdBeneficio(),
                dto.getNombre(),
                dto.getValor(),
                dto.getUnidad()
        );
    }

    public static BeneficioDTO convertirADTO(Beneficio entidad) {
        if (entidad == null) return null;

        return new BeneficioDTO(
                entidad.getIdBeneficio(),
                entidad.getNombre(),
                entidad.getValor(),
                entidad.getUnidad()
        );
    }

    // HORARIO
    public static Horario convertirAEntidad(HorarioDTO dto) {
        if (dto == null) return null;

        return new Horario(
                dto.getIdHorario(),
                dto.getFecha(),
                dto.getHoraInicio(),
                dto.getHoraFin()
        );
    }

    public static HorarioDTO convertirADTO(Horario entidad) {
        if (entidad == null) return null;

        return new HorarioDTO(
                entidad.getIdHorario(),
                entidad.getFecha(),
                entidad.getHoraInicio(),
                entidad.getHoraFin()
        );
    }

    // MEMBRESIA
    public static Membresia convertirAEntidad(MembresiaDTO dto) {
        if (dto == null) return null;

        Membresia entidad = new Membresia();

        entidad.setIdMembresia(dto.getIdMembresia());
        entidad.setNombre(dto.getNombre());
        entidad.setPrecio(dto.getPrecio());
        entidad.setImagen(dto.getImagen());

        if (dto.getEstado() != null) {
            entidad.setEstado(gym.persistencia.enums.EstadoMembresia.valueOf(dto.getEstado().name())
            );
        }

        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());

        List<Beneficio> beneficios = new ArrayList<>();

        if (dto.getBeneficios() != null) {
            for (BeneficioDTO beneficio : dto.getBeneficios()) {
                if (beneficio != null) {
                    beneficios.add(convertirAEntidad(beneficio));
                }
            }
        }

        entidad.setBeneficios(beneficios);

        return entidad;
    }

    public static MembresiaDTO convertirADTO(Membresia entidad) {
        if (entidad == null) return null;

        List<BeneficioDTO> beneficios = new ArrayList<>();

        if (entidad.getBeneficios() != null) {
            for (Beneficio beneficio : entidad.getBeneficios()) {
                if (beneficio != null) {
                    beneficios.add(convertirADTO(beneficio));
                }
            }
        }

        return new MembresiaDTO(
                entidad.getIdMembresia(),
                entidad.getNombre(),
                entidad.getPrecio(),
                entidad.getImagen(),
                entidad.getEstado() == null ? null : gym.servicios.enums.EstadoMembresia.valueOf(entidad.getEstado().name()),
                entidad.getFechaInicio(),
                entidad.getFechaFin(),
                beneficios
        );
    }

    // CURSO
    public static Curso convertirAEntidad(CursoDTO dto) {
        if (dto == null) return null;

        Curso entidad = new Curso(
                dto.getIdCurso(),
                dto.getNombre(),
                dto.getPrecio(),
                dto.getCupo(),
                dto.getImagen()
        );

        List<Horario> horarios = new ArrayList<>();

        if (dto.getHorarios() != null) {
            for (HorarioDTO horario : dto.getHorarios()) {
                if (horario != null) {
                    horarios.add(convertirAEntidad(horario));
                }
            }
        }

        entidad.setHorarios(horarios);

        return entidad;
    }

    public static CursoDTO convertirADTO(Curso entidad) {
        if (entidad == null) return null;

        List<HorarioDTO> horarios = new ArrayList<>();

        if (entidad.getHorarios() != null) {
            for (Horario horario : entidad.getHorarios()) {
                if (horario != null) {
                    horarios.add(convertirADTO(horario));
                }
            }
        }

        return new CursoDTO(
                entidad.getIdCurso(),
                entidad.getNombre(),
                entidad.getPrecio(),
                entidad.getCupo(),
                entidad.getImagen(),
                horarios
        );
    }

    // INSCRIPCION
    public static Inscripcion convertirAEntidad(InscripcionDTO dto, Cliente cliente) {
        if (dto == null) return null;

        Inscripcion entidad = new Inscripcion();

        entidad.setIdInscripcion(dto.getIdInscripcion());
        entidad.setFecha(dto.getFecha());

        if (dto.getEstado() != null) {
            entidad.setEstado(
                gym.persistencia.enums.EstadoInscripcion.valueOf(dto.getEstado().name())
            );
        }

        entidad.setCliente(cliente);

        if (dto.getHorario() != null) {
            entidad.setHorario(convertirAEntidad(dto.getHorario()));
        }

        return entidad;
    }

    public static InscripcionDTO convertirADTO(Inscripcion entidad) {
        if (entidad == null) return null;

        return new InscripcionDTO(
                entidad.getIdInscripcion(),
                entidad.getFecha(),
                entidad.getEstado() == null ? null : gym.servicios.enums.EstadoInscripcion.valueOf(entidad.getEstado().name()),
                entidad.getCliente() == null ? null : entidad.getCliente().getIdCliente(),
                entidad.getHorario() == null ? null : convertirADTO(entidad.getHorario())
        );
    }

    // REGISTRO DE INGRESO
    public static RegistroIngreso convertirAEntidad(RegistroIngresoDTO dto, Cliente cliente) {
        if (dto == null) return null;

        return new RegistroIngreso(
                dto.getIdAcceso(),
                dto.getFechaHoraEntrada(),
                dto.getFechaHoraSalida(),
                dto.getEstado() == null ? null : gym.persistencia.enums.EstadoRegistroIngreso.valueOf(dto.getEstado().name()),
                cliente
        );
    }

    public static RegistroIngresoDTO convertirADTO(RegistroIngreso entidad) {
        if (entidad == null) return null;

        return new RegistroIngresoDTO(
                entidad.getIdAcceso(),
                entidad.getFechaHoraEntrada(),
                entidad.getFechaHoraSalida(),
                entidad.getEstado() == null ? null : gym.servicios.enums.EstadoRegistroIngreso.valueOf(entidad.getEstado().name()),
                entidad.getCliente() == null ? null : entidad.getCliente().getIdCliente()
        );
    }

    // PRODUCTO
    public static Producto convertirAEntidad(ProductoDTO dto) {
        if (dto == null) return null;

        return new Producto(
                dto.getIdProducto(),
                dto.getNombre(),
                dto.getCategoria(),
                dto.getPrecio(),
                dto.getStock(),
                dto.getImagen()
        );
    }

    public static ProductoDTO convertirADTO(Producto entidad) {
        if (entidad == null) return null;

        return new ProductoDTO(
                entidad.getIdProducto(),
                entidad.getNombre(),
                entidad.getCategoria(),
                entidad.getPrecio(),
                entidad.getStock(),
                entidad.getImagen()
        );
    }

    // METODO DE PAGO
    public static MetodoPago convertirAEntidad(MetodoPagoDTO dto) {
        if (dto == null) return null;

        return new MetodoPago(
                dto.getIdMetodoPago(),
                dto.getNombre()
        );
    }

    public static MetodoPagoDTO convertirADTO(MetodoPago entidad) {
        if (entidad == null) return null;

        return new MetodoPagoDTO(
                entidad.getIdMetodoPago(),
                entidad.getNombre()
        );
    }

    // PAGO
    public static Pago convertirAEntidad(PagoDTO dto) {
        if (dto == null) return null;

        return new Pago(
                dto.getIdPago(),
                dto.getMonto(),
                dto.getFechaHora(),
                dto.getEstado() == null ? null : gym.persistencia.enums.EstadoPago.valueOf(dto.getEstado().name()),
                convertirAEntidad(dto.getMetodoPago())
        );
    }

    public static PagoDTO convertirADTO(Pago entidad) {
        if (entidad == null) return null;

        return new PagoDTO(
                entidad.getIdPago(),
                entidad.getMonto(),
                entidad.getFechaHora(),
                entidad.getEstado() == null ? null : gym.servicios.enums.EstadoPago.valueOf(entidad.getEstado().name()),
                convertirADTO(entidad.getMetodoPago())
        );
    }

    // DETALLE DE VENTA
    public static DetalleVenta convertirAEntidad(DetalleVentaDTO dto) {
        if (dto == null) return null;

        return new DetalleVenta(
                convertirAEntidad(dto.getProducto()),
                dto.getCantidad(),
                dto.getPrecioUnitario(),
                dto.getSubtotal()
        );
    }

    public static DetalleVentaDTO convertirADTO(DetalleVenta entidad) {
        if (entidad == null) return null;

        return new DetalleVentaDTO(
                convertirADTO(entidad.getProducto()),
                entidad.getCantidad(),
                entidad.getPrecioUnitario(),
                entidad.getSubtotal()
        );
    }

    // VENTA
    public static Venta convertirAEntidad(VentaDTO dto, Cliente cliente) {
        if (dto == null) return null;

        Venta entidad = new Venta();

        entidad.setIdVenta(dto.getIdVenta());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setDescuento(dto.getDescuento());
        entidad.setTotal(dto.getTotal());
        entidad.setCliente(cliente);

        List<DetalleVenta> detalles = new ArrayList<>();

        if (dto.getDetalles() != null) {
            for (DetalleVentaDTO detalle : dto.getDetalles()) {
                if (detalle != null) {
                    detalles.add(convertirAEntidad(detalle));
                }
            }
        }

        entidad.setDetalles(detalles);
        entidad.setPago(convertirAEntidad(dto.getPago()));

        return entidad;
    }

    public static VentaDTO convertirADTO(Venta entidad) {
        if (entidad == null) return null;

        List<DetalleVentaDTO> detalles = new ArrayList<>();

        if (entidad.getDetalles() != null) {
            for (DetalleVenta detalle : entidad.getDetalles()) {
                if (detalle != null) {
                    detalles.add(convertirADTO(detalle));
                }
            }
        }

        return new VentaDTO(
                entidad.getIdVenta(),
                entidad.getFechaHora(),
                entidad.getSubtotal(),
                entidad.getDescuento(),
                entidad.getTotal(),
                entidad.getCliente() == null ? null : entidad.getCliente().getIdCliente(),
                detalles,
                convertirADTO(entidad.getPago())
        );
    }

    // NOTIFICACION
    public static Notificacion convertirAEntidad(NotificacionDTO dto, Cliente cliente) {
        if (dto == null) return null;

        return new Notificacion(
                dto.getIdNotificacion(),
                dto.getTipo(),
                dto.getMensaje(),
                dto.getFechaHora(),
                dto.getEstado() == null ? null : gym.persistencia.enums.EstadoNotificacion.valueOf(dto.getEstado().name()),
                cliente
        );
    }

    public static NotificacionDTO convertirADTO(Notificacion entidad) {
        if (entidad == null) return null;

        return new NotificacionDTO(
                entidad.getIdNotificacion(),
                entidad.getTipo(),
                entidad.getMensaje(),
                entidad.getFechaHora(),
                entidad.getEstado() == null ? null : gym.servicios.enums.EstadoNotificacion.valueOf(entidad.getEstado().name()),
                entidad.getCliente() == null ? null : entidad.getCliente().getIdCliente()
        );
    }
    
    // EMPLEADO
    public static Empleado convertirAEntidad(EmpleadoDTO empleado) {
        Empleado entidad = new Empleado();

        entidad.setIdEmpleado(empleado.getIdEmpleado());
        entidad.setNombre(empleado.getNombre());
        entidad.setApellido(empleado.getApellido());
        entidad.setTelefono(empleado.getTelefono());
        entidad.setNip(empleado.getNip());
        entidad.setContrasena(empleado.getContrasena());
        entidad.setFechaIngreso(empleado.getFechaIngreso());
        entidad.setPuesto(empleado.getPuesto());

        return entidad;
    }

    public static EmpleadoDTO convertirADTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();

        dto.setIdEmpleado(empleado.getIdEmpleado());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setTelefono(empleado.getTelefono());
        dto.setNip(empleado.getNip());
        dto.setContrasena(empleado.getContrasena());
        dto.setFechaIngreso(empleado.getFechaIngreso());
        dto.setPuesto(empleado.getPuesto());

        return dto;
    }
}
    
