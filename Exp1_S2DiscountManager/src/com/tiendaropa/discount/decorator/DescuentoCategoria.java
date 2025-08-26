/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiendaropa.discount.decorator;

import com.tiendaropa.discount.component.Component;
import com.tiendaropa.discount.component.Producto;
/**
 *
 * @author franciscagoeppinger
 */
public class DescuentoCategoria extends Decorator {
    private static final double MIN_PORCENTAJE = 0.0;
    private static final double MAX_PORCENTAJE = 100.0;
    private final String categoriaObjetivo;
    private final double porcentaje;

    public DescuentoCategoria(Component componente, String categoria, double porcentaje) {
        super(componente);
        validarParametros(categoria, porcentaje);
        this.categoriaObjetivo = categoria.trim();
        this.porcentaje = porcentaje;
    }

    @Override
    public double getPrecio() {
        // Obtener el precio ya procesado por decoradores anteriores
        double precioActual = super.getPrecio();

        // Aplicar descuento solo si es un producto de la categoría correcta
        if (esProductoDeCategoriaObjetivo()) {
            return aplicarDescuento(precioActual);
        }

        return precioActual;
    }

    private boolean esProductoDeCategoriaObjetivo() {
        if (!(componente instanceof Producto)) {
            return false;
        }
        Producto producto = (Producto) componente;
        String categoriaProducto = producto.getCategoria();

        return categoriaProducto != null &&
                categoriaProducto.trim().equalsIgnoreCase(categoriaObjetivo);
    }

    private double aplicarDescuento(double precio) {
        return precio * (1 - porcentaje / 100.0);
    }

    private void validarParametros(String categoria, double porcentaje) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede ser nula o vacía");
        }
    }

    public String getCategoriaObjetivo() {
        return categoriaObjetivo;
    }
    public double getPorcentaje() {
        return porcentaje;
    }
}
