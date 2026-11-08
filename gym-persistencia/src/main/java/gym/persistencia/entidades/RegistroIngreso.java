package gym.persistencia.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad que registra el control de acceso/asistencia de los socios al gimnasio.
 */
public class RegistroIngreso implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idAcceso;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private String estado;
    private Cliente cliente;

    public RegistroIngreso() {
    }

    public RegistroIngreso(int idAcceso, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, String estado) {
        this.idAcceso = idAcceso;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.estado = estado;
    }

    public RegistroIngreso(int idAcceso, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, String estado, Cliente cliente) {
        this.idAcceso = idAcceso;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.estado = estado;
        this.cliente = cliente;
    }

    // Métodos del diagrama
    public boolean validarAcceso() {
        return "ACTIVO".equalsIgnoreCase(this.estado) || "PERMITIDO".equalsIgnoreCase(this.estado);
    }

    public void registrarEntrada() {
        this.fechaHoraEntrada = LocalDateTime.now();
        this.estado = "DENTRO";
    }

    public void registrarSalida() {
        this.fechaHoraSalida = LocalDateTime.now();
        this.estado = "SALIDO";
    }

    // Getters y Setters
    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        this.idAcceso = idAcceso;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroIngreso that = (RegistroIngreso) o;
        return idAcceso == that.idAcceso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAcceso);
    }

    @Override
    public String toString() {
        return "RegistroIngreso{" +
                "idAcceso=" + idAcceso +
                ", fechaHoraEntrada=" + fechaHoraEntrada +
                ", fechaHoraSalida=" + fechaHoraSalida +
                ", estado='" + estado + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "N/A") +
                '}';
    }
}
