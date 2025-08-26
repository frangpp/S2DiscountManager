/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiendaropa.discount.decorator;

import com.tiendaropa.discount.component.Component;
/**
 *
 * @author franciscagoeppinger
 */
public class DescuentoPorCantidad extends Decorator{
    private final int cantidadMinima;
    private final double porcentaje;
    private final int cantidadComprada;


    public DescuentoPorCantidad(Component componente, int cantidadMinima, double porcentaje, int cantidadComprada) {
        super(componente);
        this.cantidadMinima = cantidadMinima;
        this.porcentaje = porcentaje;
        this.cantidadComprada = cantidadComprada;
    }

    @Override
    public double getPrecio() {
        double precioActual = super.getPrecio();

        if (cantidadComprada >= cantidadMinima) {
            return aplicarDescuento(precioActual);
        }
        return precioActual;
    }

    private double aplicarDescuento(double precio) {
        return precio * (1 - porcentaje / 100.0);
    }

    private void validarParametros(int cantidadMinima, int cantidadComprada, double porcentaje) {
        if (cantidadMinima <= 0) {
            throw new IllegalArgumentException("La cantidad mínima debe ser mayor a 0");
        }
        if (cantidadComprada < 0) {
            throw new IllegalArgumentException("La cantidad comprada no puede ser negativa");
        }
        if (porcentaje < 0.0 || porcentaje > 100.0) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }
    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    @Override
    public String toString() {
        return String.format("Descuento Por Cantidad{min=%d, comprada=%d, porcentaje=%.2f%%}",
                cantidadMinima, cantidadComprada, porcentaje);
    }

}