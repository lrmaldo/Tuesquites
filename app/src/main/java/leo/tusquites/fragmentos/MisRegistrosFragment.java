package leo.tusquites.fragmentos;

import android.support.v4.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Leo on 01/12/2016.
 */
public class MisRegistrosFragment extends RegistroListFragmento {

public MisRegistrosFragment(){

    }
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("usuario-registro")
                .child(getUid());
    }
}
