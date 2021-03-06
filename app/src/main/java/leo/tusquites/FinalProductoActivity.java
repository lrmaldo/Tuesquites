package leo.tusquites;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import leo.tusquites.fragmentos.dialogAlert;
import leo.tusquites.modelos.SQLiteHelper;
import leo.tusquites.modelos.arrayproductosfinal;
import leo.tusquites.modelos.tabla;
import leo.tusquites.modelos.usuarios;

public class FinalProductoActivity extends BaseActivity  implements DatePickerDialog.OnDateSetListener {

    ArrayList<arrayproductosfinal> lista = new ArrayList<>();
    Map<String, String> result = new TreeMap<String,String>();
    Gson gson = new Gson();
    Toolbar toolbar;
    CollapsingToolbarLayout collapser;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_producto_main);

       /* FirebaseDatabase.getInstance().setPersistenceEnabled(true);*/
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        collapser =
                (CollapsingToolbarLayout) findViewById(R.id.collapser);
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E d MMM  yyyy");
        collapser.setTitle(format.format(ca.getTime()));

        collapser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        dialogAlert newFragment = new dialogAlert();

                        /*FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(android.R.id.content, newFragment, "dialogAlert")
                                .commit();*/
                        newFragment.show(fragmentManager,"dialogAlert");
                        Log.e("Error","toco el collapser");
                    }
                }
        );

        Log.e("netHabilitada", Boolean.toString(isNetDisponible()));
        if (isNetDisponible()|| isOnlineNet()){

        }else{
            View rootView =this.getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar snackbar =  Snackbar.make(rootView, "No hay servicio de internet, por favor intentelo más tarde", Snackbar.LENGTH_LONG).setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setAction("Action", null);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();
        }

        //instancia con la clase tabla constructor
        tabla tab = new tabla(this, (TableLayout)findViewById(R.id.tabla));


        final SQLiteHelper admin = new SQLiteHelper(getApplication(), "esquites.db", null, 1);
        final SQLiteDatabase db = admin.getReadableDatabase();
        String []a={"descripcion","subtotal","precio"};
        Cursor c =db.query("productos_imp", a, null, null, null, null, null);
        float total =0;
        int i =0;
        while(c.moveToNext()){
            String descripcion = c.getString(0);
            String subtotal =c.getString(1);
            String precio=c.getString(2);
            float su = Float.parseFloat(subtotal);
            total= total+su;
           // String con = precio.replace("$","");
            Log.e("Salida BD","salida descripcion: "+descripcion+" subtotal: "+subtotal+" precio: "+precio+" total "+total) ;


            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add(descripcion);
            elementos.add("$"+precio);
            elementos.add("$"+subtotal);
            tab.agregarFilaTabla(elementos);
            //String it =String.valueOf(i);
            //result.put(it,new arrayproductosfinal(descripcion,"$"+precio,"$"+subtotal));
            i++;
           // lista.add(new arrayproductosfinal(descripcion,"$"+precio,subtotal));

        }
        //String it =String.valueOf(i+1);

       ArrayList<String> ultima = new ArrayList<String>();
        String totalString =Float.toString(total);
        ultima.add("");
        ultima.add("Total:");
        result.put("total","$"+total);
        ultima.add("$"+totalString);
        tab.agregarUltimaTabla(ultima);
       /* View rootView =this.getWindow().getDecorView().findViewById(android.R.id.content);
        iniciarFecha(rootView);*/


    }
    public void setDateView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("E d MMM yyyy");
        collapser.setTitle(format.format(c.getTime()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu meu){
        getMenuInflater().inflate(R.menu.menu_final,meu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch (id) {

            case R.id.menu_subir:
                if (isNetDisponible()|| isOnlineNet()) {
                    AlertDialog.Builder buider = new AlertDialog.Builder(this);
                    buider.setTitle("Subir Registro").setIcon(R.drawable.ic_backup_dialog)
                            .setMessage("¿Deseas subir este registro?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String userId = getUid();
                                    mDatabase.child("usuarios").child(userId).addListenerForSingleValueEvent(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    // Get user value
                                                    usuarios user = dataSnapshot.getValue(usuarios.class);

                                                    // [START_EXCLUDE]
                                                    if (user == null) {
                                                        // User is null, error out
                                                        Log.e("final", "User " + userId + " is unexpectedly null");
                                                        Toast.makeText(FinalProductoActivity.this,
                                                                "Error: could not fetch user.",
                                                                Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // Write new post
                                                        Long timestamp = System.currentTimeMillis();

                                                        result.put("uid",userId);
                                                        result.put("usuario",user.nombre);
                                                        result.put("tiempo",timestamp.toString());
                                                        result.put("Json",getResults().toString());
                                                        result.put("fecha",collapser.getTitle().toString());
                                                        escribirRegistro(userId,user.nombre,timestamp);
                                                    }

                                                    // Finish this Activity, back to the stream

                                                    finish();
                                                    // [END_EXCLUDE]
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.w("final", "getUser:onCancelled", databaseError.toException());
                                                    // [START_EXCLUDE]
                                                    //setEditingEnabled(true);
                                                    // [END_EXCLUDE]
                                                }
                                            });
                                }
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Subir registro").setIcon(R.drawable.ic_error)
                            .setMessage("Al parecer no hay  internet," +
                                    " el registro se subirá cuando encuentre conectividad ")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String userId = getUid();
                                    mDatabase.child("usuarios").child(userId).addListenerForSingleValueEvent(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    // Get user value
                                                    usuarios user = dataSnapshot.getValue(usuarios.class);

                                                    // [START_EXCLUDE]
                                                    if (user == null) {
                                                        // User is null, error out
                                                        Log.e("final", "User " + userId + " is unexpectedly null");
                                                        Toast.makeText(FinalProductoActivity.this,
                                                                "Error: could not fetch user.",
                                                                Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // Write new post
                                                        Long timestamp = System.currentTimeMillis();

                                                        result.put("uid",userId);
                                                        result.put("usuario",user.nombre);
                                                        result.put("tiempo",timestamp.toString());
                                                        result.put("Json",getResults().toString());
                                                        result.put("fecha",collapser.getTitle().toString());
                                                        escribirRegistro(userId,user.nombre,timestamp);
                                                    }

                                                    // Finish this Activity, back to the stream

                                                    finish();
                                                    // [END_EXCLUDE]
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.w("final", "getUser:onCancelled", databaseError.toException());
                                                    // [START_EXCLUDE]
                                                    //setEditingEnabled(true);
                                                    // [END_EXCLUDE]
                                                }
                                            });
                                }
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();



                }







                break;


           default:
                break;

        }


        return super.onOptionsItemSelected(item);

    }


    private void escribirRegistro(String userId, String usuario, Long time) {


        try {
            String key = mDatabase.child("Registros").push().getKey();


            Map<String, Object> childUpdates = new HashMap<>();


            childUpdates.put("/Registros/" + key+"/" ,result );
            childUpdates.put("/usuario-registro/" + userId + "/" + key + "/",result);

           /* Map<String, Object> postValues = new HashMap<>();
            postValues.put("usuario",usuario);
            postValues.put("tiempo",time);
            childUpdates.put("/Registros/" + key+"/" ,postValues);
            childUpdates.put("/usuario-registro/" + userId + "/" + key + "/" ,postValues);*/

            mDatabase.updateChildren(childUpdates);

        }catch (Exception e){
            Log.e("Error",e.toString());
        }



    }
    private JSONArray getResults()
    {

       /*Convierte la base de datos a JSON*/


        final SQLiteHelper admin = new SQLiteHelper(getApplication(), "esquites.db", null, 1);
        final SQLiteDatabase db = admin.getReadableDatabase();


        String myTable = "productos_imp";//Set name of your table



        String searchQuery = "SELECT  * FROM " + myTable;
        Cursor cursor = db.rawQuery(searchQuery, null );

        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultSet.toString() );
        return resultSet;

    }


    private void iniciarFecha(View view) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E MMM d yyyy");
        collapser.setTitle(format.format(c.getTime()));

        collapser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        dialogAlert newFragment = new dialogAlert();

                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(android.R.id.content, newFragment, "dialogAlert")
                                .commit();
                    }
                }
        );
    }





    @Override
    public void onBackPressed() {


        final SQLiteHelper admin = new SQLiteHelper(getApplication(), "esquites.db", null, 1);
        final SQLiteDatabase db = admin.getReadableDatabase();
        db.delete("productos_imp",null,null);
       // db.close();
        Log.e("elimino", "datos de esta actividad");


       /* final SQLiteHelper  admin1 = new SQLiteHelper(getApplication(),"esquites.db",null,1);
        final ContentValues registro = new ContentValues();
        final SQLiteDatabase bd = admin1.getWritableDatabase();
        registro.put("cantidad_final","0");

        String []a={"nombre"};
        Cursor c1 =db.query("productos", a, null, null, null, null, null);
        registro.put("cantidad_final","0");
        while (c1.moveToNext()){
            String nombre =c1.getString(0);
            bd.update("productos",registro,"nombre = '"+nombre+"'",null);
        }
        db.close();*/

        Intent intent = new Intent(FinalProductoActivity.this, InsertarProductoActivity.class);
        //intent.putExtra("miLista", input);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
    







    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
setDateView(year,month,dayOfMonth);
    }
}

