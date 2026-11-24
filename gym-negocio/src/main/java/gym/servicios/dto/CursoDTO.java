/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

import java.util.List;

/**
 * Transporta los datos de curso entre las capas del sistema.
 *
 * @author PC GAMER MASTER RACE
 */
public class CursoDTO {
    private String idCurso;
    private String nombre;
    private double precio;
    private int cupo;
    private String imagen;
    private List<HorarioDTO> horarios;

    public CursoDTO() {
    }

    public CursoDTO(String idCurso, String nombre, double precio, int cupo, String imagen, List<HorarioDTO> horarios) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.precio = precio;
        this.cupo = cupo;
        this.imagen = imagen;
        this.horarios = horarios;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
}
