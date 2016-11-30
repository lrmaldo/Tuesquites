package leo.tusquites;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import leo.tusquites.modelos.SQLiteHelper;
import leo.tusquites.modelos.arrayproductosfinal;
import leo.tusquites.modelos.tabla;

import static android.R.attr.duration;
import static android.R.id.message;

public class FinalProductoActivity extends AppCompatActivity {

    ArrayList<arrayproductosfinal> lista = new ArrayList<arrayproductosfinal>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_producto_main);

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
        while(c.moveToNext()){
            String descripcion = c.getString(0);
            String subtotal =c.getString(1);
            String precio=c.getString(2);
            float su = Float.parseFloat(subtotal);
            total= total+su;
           // String con = precio.replace("$","");
            Log.e("Salida BD","salida descripcion: "+descripcion+" subtotal: "+subtotal+" precio: "+precio+" total "+total) ;

            // lista.add(new arrayproductosfinal(descripcion,"$"+precio,subtotal));
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add(descripcion);
            elementos.add("$"+precio);
            elementos.add("$"+subtotal);
            tab.agregarFilaTabla(elementos);


        }

       ArrayList<String> ultima = new ArrayList<String>();
        String totalString =Float.toString(total);
        ultima.add("");
        ultima.add("Total:");

        ultima.add("$"+totalString);
        tab.agregarUltimaTabla(ultima);



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
}

