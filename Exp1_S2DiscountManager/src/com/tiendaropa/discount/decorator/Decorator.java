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
public abstract class Decorator implements Component {
    protected final Component componente;

    public Decorator(Component componente) {
        this.componente = componente;
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio();
    }
}
