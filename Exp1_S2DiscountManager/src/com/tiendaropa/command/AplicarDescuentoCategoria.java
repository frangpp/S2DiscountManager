package com.tiendaropa.command;
import com.tiendaropa.discount.component.Component;
/**
 *
 * @author franciscagoeppinger
 */
public class AplicarDescuentoCategoria implements Command {
    private final Component producto;
    private final String categoria;
    private Component resultado;
    private final double porcentaje;

    public AplicarDescuentoCategoria(Component producto, String categoria, double porcentaje) {
        if (producto == null || categoria == null || porcentaje < 0) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }
        this.producto = producto;
        this.categoria = categoria;
        this.porcentaje = porcentaje;
    }

    @Override
    public void ejecutar() {
        resultado = new com.tiendaropa.discount.decorator.DescuentoCategoria(producto, categoria, porcentaje);
    }

    public Component getResultado() {
        return resultado;
    }

}
