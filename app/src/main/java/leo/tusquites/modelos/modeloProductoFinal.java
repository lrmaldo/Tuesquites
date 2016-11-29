package leo.tusquites.modelos;

/**
 * Created by Leo on 09/11/2016.
 */

public class modeloProductoFinal {
    String nombre;
    String precio;
    String cantidad;
    Boolean Selected;
    String cantidadFinal;

    public modeloProductoFinal(String nombre, String precio, String cantidad,String cantidadFinal) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
         this.cantidadFinal= cantidadFinal;
        this.Selected = false;
    }

    public String getCantidadFinal() {
        return cantidadFinal;
    }

    public void setCantidadFinal(String cantidadFinal) {
        this.cantidadFinal = cantidadFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getSelected() {
        return Selected;
    }

    public void setSelected(Boolean selected) {
        Selected = selected;
    }
}
