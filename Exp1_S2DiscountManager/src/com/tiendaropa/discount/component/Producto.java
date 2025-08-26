package com.tiendaropa.discount.component;
/**
 *
 * @author franciscagoeppinger
 */
public class Producto implements Component {
    private final String nombre;
    private final int precio;
    private final String categoria;

    public Producto(String nombre, int precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }
}
