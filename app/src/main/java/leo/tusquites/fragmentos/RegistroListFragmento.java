package leo.tusquites.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import leo.tusquites.InsertarProductoActivity;
import leo.tusquites.PrincipalActivity;
import leo.tusquites.R;
import leo.tusquites.RegistroActivity;
import leo.tusquites.VistaUsuariosActivity;


/**
 * Created by Leo on 01/12/2016.
 */

public class RegistroListFragmento extends Fragment {

    private DatabaseReference mDatabase;
    // [END define_database_reference]

   /* private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;*/
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    public RegistroListFragmento() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragmento_todos, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]
        final Context context = getActivity().getApplicationContext();
        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

       /* materialDesignFAM = (FloatingActionMenu) rootView.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item3);



        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             *//*  Intent in = new Intent(getActivity(),RegistroActivity.class);
                ((Re) getActivity()).startActivity(in);*//*
              *//* startActivity(new Intent(getActivity().getContext(), RegistroActivity.class));*//*
                Intent intent = new Intent(context, RegistroActivity.class);
                startActivity(intent);
                //finish();

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                startActivity(new Intent(getActivity(),InsertarProductoActivity.class));


            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                startActivity(new Intent(getActivity(), VistaUsuariosActivity.class));
                //finish();
            }
        });*/

        return rootView;
    }
}
