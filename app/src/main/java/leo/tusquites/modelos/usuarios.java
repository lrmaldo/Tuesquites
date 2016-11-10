package leo.tusquites.modelos;

/**
 * Created by Leo on 11/10/2016.
 */

//import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
//@IgnoreExtraProperties
public class usuarios {


    public String nombre;
    public String email;
    public String url_foto;

    public usuarios() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public usuarios(String nombre, String email, String url_foto) {
        this.nombre = nombre;
        this.email = email;
        this.url_foto=url_foto;
    }
}
