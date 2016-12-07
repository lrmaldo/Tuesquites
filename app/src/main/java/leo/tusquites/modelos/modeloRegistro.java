package leo.tusquites.modelos;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leo on 04/12/2016.
 */
@IgnoreExtraProperties
public class modeloRegistro {
    public String uid;
    public Object Json ;
    public Object tiempo;
     public String total;
     public String usuario;
     public String fecha;

    public modeloRegistro(){

    }

    public modeloRegistro(String uid ,Object Json, Object tiempo, String total, String usuario, String fecha) {
        this.uid=uid;
        this.Json = Json;
        this.tiempo = tiempo;
        this.total = total;
        this.usuario = usuario;
        this.fecha=fecha;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Json", Json);
        result.put("tiempo", tiempo);
        result.put("total", total);
        result.put("usuario", usuario);


        return result;
    }

    public Object getTiempo() {
        return tiempo;
    }
}
