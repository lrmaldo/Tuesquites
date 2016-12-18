package leo.tusquites;

import android.content.DialogInterface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import leo.tusquites.modelos.modeloRegistro;
import leo.tusquites.modelos.tabla;

public class Detalle_RegistroActivity extends AppCompatActivity {
    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    private ImageView cabecera;
    CollapsingToolbarLayout collapser;
    String uid_usuario ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__registro);
        setToolbar();// Añadir action bar
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get post key from intent
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Registros").child(mPostKey);
        mPostReference.keepSynced(true);
        Toast.makeText(Detalle_RegistroActivity.this, "llave"+mPostKey.toString(),
                Toast.LENGTH_SHORT).show();

        collapser =
                (CollapsingToolbarLayout) findViewById(R.id.collapser);
        cabecera = (ImageView) findViewById(R.id.image_paralax);


    }
    @Override
    public void onStart() {
        super.onStart();
       final tabla tab = new tabla(this, (TableLayout)findViewById(R.id.tabla));
        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                modeloRegistro registro = dataSnapshot.getValue(modeloRegistro.class);


                // [START_EXCLUDE]
              /*  mAuthorView.setText(post.author);
                mTitleView.setText(post.title);
                mBodyView.setText(post.body);*/
                // [END_EXCLUDE]


                try {
                    uid_usuario=registro.uid;
                    collapser.setTitle(registro.fecha);
                    //JSONObject a = new JSONObject(registro.Json.toString());
                    JSONArray array = new JSONArray(registro.Json.toString());
                    for(int i=0; i<array.length(); i++){
                       JSONObject jsonObj  = array.getJSONObject(i);
                        ArrayList<String> elementos = new ArrayList<String>();
                        elementos.add(jsonObj.getString("descripcion"));
                        elementos.add("$"+jsonObj.getString("precio"));
                        elementos.add("$"+jsonObj.getString("subtotal"));
                        tab.agregarFilaTabla(elementos);

                       /* System.out.println(jsonObj.getString("descripcion"));
                        System.out.println(jsonObj.getString("precio"));
                        System.out.println(jsonObj.getString("subtotal"));*/
                        Log.e("JSONObjeto",jsonObj.getString("descripcion")+" "+jsonObj.getString("precio")+"  "+jsonObj.getString("subtotal"));
                        Log.e("JSon completo",registro.Json.toString());

                    }
                    ArrayList<String> ultima = new ArrayList<String>();

                    ultima.add("");
                    ultima.add("Total:");

                    ultima.add("$"+registro.total);
                    tab.agregarUltimaTabla(ultima);
                    String tr = registro.total.replace("$","");
                    float tot =Float.parseFloat(tr);
                    if(tot<300){
                        cabecera.setBackgroundColor(getResources().getColor(R.color.red));

                    }else if(tot<800){
                        cabecera.setBackgroundColor(getResources().getColor(R.color.medium));

                    }else if(tot>800){
                        cabecera.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("dettakeregistro", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(Detalle_RegistroActivity.this, "Falló al ver registro.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;

       /* // Listen for comments
        mAdapter = new CommentAdapter(this, mCommentsReference);
        mCommentsRecycler.setAdapter(mAdapter);*/
    }
    private void setToolbar() {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete:
               // showSnackBar("Se abren los ajustes");
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Eliminar");
                builder.setMessage("¿Desea eliminar este registro?").setIcon(R.drawable.ic_error)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/Registros/" + mPostKey.toString()+"/" ,null );
                        childUpdates.put("/usuario-registro/" + uid_usuario + "/" + mPostKey.toString() + "/",null);

                        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
                        finish();

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

}
