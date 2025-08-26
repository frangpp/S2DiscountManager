package com.tiendaropa.discount;

import com.tiendaropa.discount.component.Component;
import com.tiendaropa.discount.decorator.DescuentoCategoria;
import com.tiendaropa.discount.decorator.DescuentoGeneral;
import com.tiendaropa.discount.component.Producto;
/**
 *
 * @author franciscagoeppinger
 */
public class DiscountManager {
    private static final DiscountManager INSTANCE = new DiscountManager();

    private DiscountManager(){}

    public static DiscountManager getInstance() {
        return INSTANCE;
    }

    public Component aplicarDescuentos(Component producto, String codigoDescuento) {
        Component productoConDescuento = producto;

        if(codigoDescuento != null && !codigoDescuento.isEmpty()){
            //GENRAL
            if (codigoDescuento.equals("DESCUENTO10")) {
                productoConDescuento = new DescuentoGeneral(productoConDescuento, 10);
            } else if (codigoDescuento.equals("DESCUENTO20")) {
                productoConDescuento = new DescuentoGeneral(productoConDescuento, 20);
            } else if (codigoDescuento.equals("DESCUENTO30")) {
                productoConDescuento = new DescuentoGeneral(productoConDescuento, 30);
            }

            //TEMPORADA
            if (producto instanceof Producto && ((Producto) producto).getCategoria().equals("TEMPORADA")) {
                productoConDescuento = new DescuentoCategoria(productoConDescuento, "TEMPORADA", 15);
            }
        }
        return productoConDescuento;
    }


    public double calculadoraDeDescuentos(int precio, String cupon) {
        double descuento = 0;
        if (cupon.equals("DESCUENTO10")) {
            descuento = precio * 0.10;
        } else if (cupon.equals("DESCUENTO20")) {
            descuento = precio * 0.20;
        } else if (cupon.equals("DESCUENTO30")) {
            descuento = precio * 0.30;
        }
        return descuento;
    }
    /*
    //Se aplica un dcto dependiendo del cupon que se ingrese, el parametro precio es el precio original del producto, codigoDescuento es el cupon, (ej. "DESCUENTO10"), y se devuelve el descuento aplicado.
    public double calculadoraDeDescuentos(int precio, String codigoDescuento) {
        return switch (codigoDescuento){
            case "DESCUENTO10" -> precio * 0.10; // 10%
            case "DESCUENTO20" -> precio * 0.20; // 20%
            case "DESCUENTO30" -> precio * 0.30; // 30%
            default -> 0.0; // no hay dcto
        };
    }
    /*
    public static void main(String[] args) {
        //Tester
        DiscountManager dm = DiscountManager.getInstance();
        int precioOriginal = 100000;
        String codigoDescuento = "DESCUENTO20";
        double descuento = dm.calculadoraDeDescuentos(precioOriginal, codigoDescuento);
        System.out.println("Descuento aplicado: " + Math.round(descuento) + "%");
    }
   */

}
