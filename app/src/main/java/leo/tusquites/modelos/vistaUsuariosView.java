package leo.tusquites.modelos;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 13/10/2016.
 */

@IgnoreExtraProperties
public class vistaUsuariosView {

    public String uid;
    public String nombre;
    public String correo;
    public String url_foto;

    public vistaUsuariosView(){

    }
    public vistaUsuariosView(String uid, String correo, String nombre, String url_foto ){
        this.uid =uid;
        this.correo = correo;
        this.nombre = nombre;
        this.url_foto= url_foto;



    }


    // [START post_to_map]
   // @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //Map<String, Object> result = new Gson().fromJson(String.valueOf(result2), new TypeToken<HashMap<String, Object>>() {}.getType());
      result.put("uid", uid);
        result.put("email", correo);
      result.put("nombre", nombre);
        result.put("url_foto",url_foto);


    return result;
   }
    // [END post_to_map]






}
