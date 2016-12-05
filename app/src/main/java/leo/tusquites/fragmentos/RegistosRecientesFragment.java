package leo.tusquites.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import leo.tusquites.R;

/**
 * Created by Leo on 01/12/2016.
 */
public class RegistosRecientesFragment extends RegistroListFragmento {

    public RegistosRecientesFragment(){

    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child("Registros")
                ;
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
