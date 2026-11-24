package gym.ventas;

import gym.servicios.dto.ProductoDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  Mantiene los productos seleccionados durante el flujo de compra.
 *
 * @author PC GAMER MASTER RACE
 */
public final class CarritoVentas {
    private final Map<String, Item> items = new LinkedHashMap<>();

    public void agregar(ProductoDTO producto, int cantidad) {
        Item item = items.get(producto.getIdProducto());
        if (item == null) {
            items.put(producto.getIdProducto(), new Item(producto, cantidad));
        } else {
            item.cantidad += cantidad;
        }
    }

    public void quitar(String idProducto) {
        items.remove(idProducto);
    }

    public List<Item> obtenerItems() {
        return new ArrayList<>(items.values());
    }

    public double obtenerTotal() {
        double total = 0;
        for (Item item : items.values()) {
            total += item.obtenerSubtotal();
        }
        return total;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public int obtenerCantidadTotal() {
        int cantidadTotal = 0;
        for (Item item : items.values()) {
            cantidadTotal += item.getCantidad();
        }
        return cantidadTotal;
    }

    public static final class Item {
        private final ProductoDTO producto;
        private int cantidad;

        private Item(ProductoDTO producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public ProductoDTO getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double obtenerSubtotal() {
            return producto.getPrecio() * cantidad;
        }
    }
}
