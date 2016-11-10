package leo.tusquites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import leo.tusquites.modelos.vistaUsuariosView;
import leo.tusquites.ordenador_reciclador.usuarioViewHolder;

public class VistaUsuariosActivity extends AppCompatActivity {


    private static final String TAG = "PostListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

   private FirebaseRecyclerAdapter <vistaUsuariosView, usuarioViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    protected View dialogLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuarios);
// [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]
cargadorLoader carga = new cargadorLoader(VistaUsuariosActivity.this);
        carga.execute();

       ///aui va el codigo
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

    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("usuarios")
                ;
        return recentPostsQuery;
    }

    private class cargadorLoader extends AsyncTask<String, Void, Boolean> {
        //private GameResultsAdapter adapter;
       private ProgressDialog dialog;
        Activity acti;


        public cargadorLoader(Activity acti) {
            this.acti = acti;
            context = acti;
            dialog = new ProgressDialog(context);


        }

        Context context;

        @Override
        protected void onPreExecute() {

            this.dialog.setMessage("Descargando contenido" +
                    "");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                mRecycler = (RecyclerView) findViewById(R.id.messages_list);


                // Set up FirebaseRecyclerAdapter with the Query
                Query postsQuery = getQuery(mDatabase);
                mAdapter = new FirebaseRecyclerAdapter <vistaUsuariosView, usuarioViewHolder>(vistaUsuariosView.class, R.layout.item_usuarios,
                        usuarioViewHolder.class, postsQuery) {
                    @Override
                    protected void populateViewHolder(final usuarioViewHolder viewHolder, final vistaUsuariosView model, final int position) {
                        final DatabaseReference postRef = getRef(position);

                        // Set click listener for the whole post view
                        final String postKey = postRef.getKey();
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Launch PostDetailActivity
                                Intent intent = new Intent(getApplication(), DetalleUsuarioActivity.class);
                                intent.putExtra(DetalleUsuarioActivity.EXTRA_POST_KEY, postKey);
                                startActivity(intent);
                                //  Toast.makeText(getApplication(), "toch"+ model.nombre+" "+model.correo, Toast.LENGTH_SHORT).show();
                            }


                        });
                        viewHolder.bindToPost(model, new View.OnClickListener() {
                            @Override
                            public void onClick(View starView) {
                                // Need to write to both places the post is stored
                                DatabaseReference globalPostRef = mDatabase.child("usuarios").child(postRef.getKey());
                                // DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                                // Run two transactions
                                //onStarClicked(globalPostRef);
                                //onStarClicked(userPostRef);
                            }
                        });
                    }


                };



                // listGames = GameResultsCache.getInstance().getGameResults();
                //adapter = new GameResultsAdapter(getBaseContext(), listGames);
                //listViewGameResults = (ListView)findViewById(R.id.listViewGameResults);
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                finish();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {


try {
    mRecycler.setHasFixedSize(true);
    mManager = new LinearLayoutManager(getApplication());
    mManager.setReverseLayout(true);
    mManager.setStackFromEnd(true);
    mRecycler.setLayoutManager(mManager);
    mRecycler.setAdapter(mAdapter);

    dialog.dismiss();
    return;
}
    catch (Exception e){
        dialog.dismiss();
    }



            // Set up Layout Manager, reverse layout

            //listViewGameResults.setAdapter(adapter);
            //listViewGameResults.setDivider(null);
            //listViewGameResults.setDividerHeight(0);
           //ProgressBar pb = (ProgressBar)findViewById(R.id.progressbar_loading);
            //pb.setVisibility(View.GONE);


        }



    }



}


