package leo.tusquites.modelos;

import java.security.Key;
import java.util.HashMap;

/**
 * Created by Leo on 20/11/2016.
 */

public class arrayproductosfinal extends HashMap<String, String> {


    String detalle;
    String precio;
    String subtotal;




    public arrayproductosfinal(String detalle, String precio, String subtotal) {
        this.precio= precio;
        this.detalle = detalle;
        this.subtotal = subtotal;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "arrayproductosfinal{" +
                "detalle='" + detalle + '\'' +
                ", precio='" + precio + '\'' +
                ", subtotal='" + subtotal + '\'' +
                '}';
    }
}
