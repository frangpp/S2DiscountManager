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
public class DescuentoGeneral extends Decorator {
    private static final double MIN_PORCENTAJE = 0.0;
    private static final double MAX_PORCENTAJE = 100.0;
    private final double porcentaje;

    public DescuentoGeneral(Component component, double porcentaje) {
        super(component);
        validarPorcentaje(porcentaje);
        this.porcentaje = porcentaje;
    }

    @Override
    public double getPrecio() {
        double precioActual = super.getPrecio();
        return aplicarDescuento(precioActual);
    }

    private double aplicarDescuento(double precio) {
        return precio * (1 - porcentaje / 100.0);
    }

    private void validarPorcentaje(double porcentaje) {
        if (porcentaje < MIN_PORCENTAJE || porcentaje > MAX_PORCENTAJE) {
            throw new IllegalArgumentException(
                    String.format("El porcentaje debe estar entre %.1f y %.1f",
                            MIN_PORCENTAJE, MAX_PORCENTAJE)
            );
        }
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    @Override
    public String toString() {
        return String.format("Descuento General{porcentaje=%.2f%%}", porcentaje);
    }

}

