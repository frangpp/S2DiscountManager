/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiendaropa.command;

import com.tiendaropa.discount.component.Component;
import com.tiendaropa.discount.decorator.DescuentoGeneral;
/**
 *
 * @author franciscagoeppinger
 */
public class AplicarDescuentoGeneral implements Command{
    private final Component producto;
    private final double porcentaje;
    private Component resultado;

    public AplicarDescuentoGeneral(Component producto, double porcentaje) {
        this.producto = producto;
        this.porcentaje = porcentaje;
    }

    @Override
    public void ejecutar(){
        resultado = new DescuentoGeneral(producto, porcentaje);
    }

    //devuelve el producto con el descuento aplicado
    public Component getResultado() {
        return resultado;
    }
}
