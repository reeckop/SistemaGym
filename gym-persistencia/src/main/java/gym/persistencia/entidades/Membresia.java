package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que modela un plan de membresía del gimnasio.
 */
public class Membresia implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idMembresia;
    private String nombre;
    private double precio;
    private List<Beneficio> beneficios = new ArrayList<>();

    public Membresia() {
    }

    public Membresia(int idMembresia, String nombre, double precio) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.beneficios = new ArrayList<>();
    }

    public Membresia(int idMembresia, String nombre, double precio, List<Beneficio> beneficios) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.beneficios = beneficios != null ? new ArrayList<>(beneficios) : new ArrayList<>();
    }

    // Métodos del diagrama
    public void agregar() {
        // Método de ciclo de vida del diagrama
    }

    public void editar() {
        // Método de ciclo de vida del diagrama
    }

    public void eliminar() {
        // Método de ciclo de vida del diagrama
    }

    public List<Membresia> consultarMembresias() {
        return Collections.singletonList(this);
    }

    // Métodos de ayuda para la relación con Beneficio
    public void agregarBeneficio(Beneficio beneficio) {
        if (beneficio != null) {
            this.beneficios.add(beneficio);
        }
    }

    public void removerBeneficio(Beneficio beneficio) {
        this.beneficios.remove(beneficio);
    }

    // Getters y Setters
    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
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

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<Beneficio> beneficios) {
        this.beneficios = beneficios != null ? new ArrayList<>(beneficios) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membresia membresia = (Membresia) o;
        return idMembresia == membresia.idMembresia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMembresia);
    }

    @Override
    public String toString() {
        return "Membresia{" +
                "idMembresia=" + idMembresia +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", beneficiosCount=" + (beneficios != null ? beneficios.size() : 0) +
                '}';
    }
}
