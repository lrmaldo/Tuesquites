package leo.tusquites.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import leo.tusquites.Detalle_RegistroActivity;
import leo.tusquites.R;

import leo.tusquites.modelos.modeloRegistro;
import leo.tusquites.ordenador_reciclador.RegistroViewHolder;


/**
 * Created by Leo on 01/12/2016.
 */

public abstract class RegistroListFragmento extends Fragment {

    private DatabaseReference mDatabase;
    // [END define_database_reference]

   /* private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;*/
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    TextView textvacio;



    private FirebaseRecyclerAdapter<modeloRegistro, RegistroViewHolder> mAdapter;

    public RegistroListFragmento() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragmento_todos, container, false);

        // [START create_database_reference]

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);


        // [END create_database_reference]
        textvacio = (TextView) rootView.findViewById(R.id.recyclerview_data_empty);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);



        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        if(mAdapter!=null){

        }

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(FirebaseDatabase.getInstance().getReference());
        mAdapter = new FirebaseRecyclerAdapter<modeloRegistro, RegistroViewHolder>(modeloRegistro.class, R.layout.item_registro,
                RegistroViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final RegistroViewHolder viewHolder, final modeloRegistro model, final int position) {
                final DatabaseReference postRef = getRef(position);
                viewHolder.setTimestamp( DateUtils.getRelativeTimeSpanString(Long.parseLong(String.valueOf(model.tiempo))).toString());
            viewHolder.bindTorRegistro(model);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), Detalle_RegistroActivity.class);
                        intent.putExtra(Detalle_RegistroActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
               /* if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }*/

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                /*viewHolder.bindTorRegistro(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                       *//* onStarClicked(globalPostRef);
                        onStarClicked(userPostRef);*//*
                    }
                });*/
            }
        };
        mRecycler.setVisibility(View.VISIBLE);
        textvacio.setText("");
        mRecycler.setAdapter(mAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}
