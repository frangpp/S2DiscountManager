package com.tiendaropa;

import com.tiendaropa.discount.DiscountManager;
import com.tiendaropa.discount.component.Component;
import com.tiendaropa.discount.component.Producto;
import com.tiendaropa.discount.decorator.DescuentoCategoria;
import com.tiendaropa.discount.decorator.DescuentoPorCantidad;

import java.util.*;
/**
 *
 * @author franciscagoeppinger
 */
public class Tienda {
    //catalogo (nombre,precio,categoria)
    private final Map<Integer, Producto> catalogo;
    {
        catalogo = new LinkedHashMap<>();
        catalogo.put(1, new Producto("Polera Algodon", 10000, "Camisetas"));
        catalogo.put(2, new Producto("Jeans Baggy", 15000, "Pantalones"));
        catalogo.put(3, new Producto("Chaqueta", 20000, "Abrigos"));
        catalogo.put(4, new Producto("Cardigan", 25000, "Abrigos"));
    }
    private final List<Component> carrito = new ArrayList<>();

    public void iniciarCompra() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) {
            System.out.println("Elije la ropa que quieres añadir al carrito:");
            for (Map.Entry<Integer, Producto> entry : catalogo.entrySet()) { // Mostrar productos del catálogo
                Producto producto = entry.getValue();
                System.out.println(entry.getKey() + ". " + producto.getNombre() +
                        " - $" + Math.round(producto.getPrecio()) +
                        " (" + producto.getCategoria() + ")");
            }
            System.out.println("5. Finalizar compra");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un numero valido.");
                continue;
            }

            if (opcion == 0) break;

            if (catalogo.containsKey(opcion)) {
                // AQUÍ ES DONDE SE APLICAN LOS DECORADORES
                Component producto = catalogo.get(opcion);
                Component productoConDescuentos = aplicarDescuentosAutomaticos(producto);
                carrito.add(productoConDescuentos);
                System.out.println(catalogo.get(opcion).getNombre() + " añadido al carrito.");

            } else if (opcion == 5) {
                finalizarCompra(scanner);
                break;
            } else {
                System.out.println("Opcion no valida, por favor intenta de nuevo.");
            }
        }
        scanner.close();
    }
    private Component aplicarDescuentosAutomaticos(Component producto) {
        System.out.println("=== APLICANDO DESCUENTOS ==="); // DEBUG
        System.out.println("Carrito actual tiene: " + carrito.size() + " items"); // DEBUG

        Component productoConDescuentos = producto;

        //Descuento por cantidad si hay +2 items en carrito
        if (carrito.size() >= 1) {
            productoConDescuentos = new DescuentoPorCantidad(productoConDescuentos, 2, carrito.size() + 1, 5);
            System.out.println("¡Descuento por cantidad aplicado! (5%)");
        }

        //Descuento para la seccion abrigosss
        if (producto instanceof Producto) {
            Producto prod = (Producto) producto;
            if ("Abrigos".equals(prod.getCategoria())) {
                productoConDescuentos = new DescuentoCategoria(productoConDescuentos, "Abrigos", 10.0);
                System.out.println("¡Descuento por categoría 'Abrigos' aplicado! (10%)");
            }
        }
        return productoConDescuentos;
    }

    private void finalizarCompra(Scanner scanner) {
        System.out.println("Carrito de compras:");

        double totalOriginal = 0;
        double totalConDescuentos = 0;

        for (Component item : carrito) {
            if (item instanceof Producto) { // Obtener el producto original para comparar precios
                Producto prod = (Producto) item;
                System.out.println("- " + prod.getNombre());
                totalOriginal += prod.getPrecio();
            } else {
                Component base = obtenerProductoBase(item); // Es un decorador, buscar el producto base
                if (base instanceof Producto) {
                    Producto prod = (Producto) base;
                    String descuentos = detectarTiposDescuento(item);
                    System.out.println("- " + prod.getNombre() + " (con descuentos: " + descuentos + ")");
                    totalOriginal += prod.getPrecio();
                }
            }
            totalConDescuentos += item.getPrecio();
        }

        System.out.println("Subtotal original: $" + Math.round(totalOriginal));
        System.out.println("Total con descuentos: $" + Math.round(totalConDescuentos));

        // Descuento adicional con código
        System.out.print("Si tienes un codigo de descuento, ingresalo ahora (o presiona Enter para continuar):");
        String codigoDescuento = scanner.nextLine().trim();

        double totalFinal = totalConDescuentos;
        if (!codigoDescuento.isEmpty()) {
            DiscountManager dm = DiscountManager.getInstance();
            double descuentoAdicional = dm.calculadoraDeDescuentos((int) totalConDescuentos, codigoDescuento);
            if (descuentoAdicional > 0) {
                System.out.println("Descuento adicional aplicado :) $" + Math.round(descuentoAdicional));
                totalFinal = totalConDescuentos - descuentoAdicional;
            }
        }
        System.out.println("Total de la compra: $" + Math.round(totalFinal));
        //System.out.println("Decorator: " + (totalOriginal != totalConDescuentos ? "Descuentos aplicados" : "Sin descuentos"));
        //System.out.println("Singleton: DiscountManager usado para códigos");
        System.out.println("Gracias por tu compra!");
    }
    private String detectarTiposDescuento(Component component) {
        List<String> descuentos = new ArrayList<>();

        if (component instanceof DescuentoPorCantidad) {
            descuentos.add("Cantidad (5%)");
        }
        if (component instanceof DescuentoCategoria) {
            descuentos.add("Categoría (10%)");
        }

        return String.join(", ", descuentos);
    }

    private Component obtenerProductoBase(Component component) {
        if (component instanceof Producto) {
            return component;
        }
        // Si es un decorador, buscar recursivamente
        try {
            java.lang.reflect.Field field = component.getClass().getSuperclass().getDeclaredField("componente");
            field.setAccessible(true);
            Component base = (Component) field.get(component);
            return obtenerProductoBase(base);
        } catch (Exception e) {
            return component;
        }
    }

    }
