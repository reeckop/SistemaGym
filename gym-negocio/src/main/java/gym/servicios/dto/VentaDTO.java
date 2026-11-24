/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Transporta los datos de venta entre las capas del sistema.
 *
 * @author PC GAMER MASTER RACE
 */
public class VentaDTO {
    private String idVenta;
    private LocalDateTime fechaHora;
    private double subtotal;
    private double descuento;
    private double total;
    private String idCliente;
    private List<DetalleVentaDTO> detalles;
    private PagoDTO pago;

    public VentaDTO() {
    }

    public VentaDTO(String idVenta, LocalDateTime fechaHora, double subtotal, double descuento, double total, String idCliente, List<DetalleVentaDTO> detalles, PagoDTO pago) {
        this.idVenta = idVenta;
        this.fechaHora = fechaHora;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
        this.idCliente = idCliente;
        this.detalles = detalles;
        this.pago = pago;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }

    public PagoDTO getPago() {
        return pago;
    }

    public void setPago(PagoDTO pago) {
        this.pago = pago;
    }
}