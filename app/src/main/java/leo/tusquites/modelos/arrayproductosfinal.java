package leo.tusquites.modelos;

/**
 * Created by Leo on 20/11/2016.
 */

public class arrayproductosfinal {

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

}
