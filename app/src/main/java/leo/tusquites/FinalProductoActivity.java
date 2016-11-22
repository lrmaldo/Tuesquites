package leo.tusquites;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import java.util.ArrayList;

import leo.tusquites.modelos.SQLiteHelper;
import leo.tusquites.modelos.arrayproductosfinal;
import leo.tusquites.modelos.tabla;

public class FinalProductoActivity extends AppCompatActivity {
    ArrayList<arrayproductosfinal> lista = new ArrayList<arrayproductosfinal>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_producto_main);

       /* lista = (ArrayList<arrayproductosfinal>) getIntent().getSerializableExtra("miLista");

        for(int i=0 ;i<lista.size();i++){
            Log.e("salida:",lista.get(i).getDetalle()+" "+lista.get(i).getSubtotal());
        }*/

        /*List listaLimpia = new ArrayList();


        for (int i = 0; i< comunicador.getObjeto().size(); i++){
            arrayproductosfinal obj=(arrayproductosfinal) comunicador.getObjeto().get(i);
            lista.add(obj);

            Log.e("Salidad actividad",lista.get(i).getDetalle()+" = "+lista.get(i).getSubtotal());
        }

        Map<Integer, arrayproductosfinal> mapPersonas = new HashMap<Integer, arrayproductosfinal>(lista.size());
        //Aquí está la magia
        for(arrayproductosfinal p : lista) {
            mapPersonas.put(p.getInteger(), p);
        }

        for(Map.Entry<Integer, arrayproductosfinal> p : mapPersonas.entrySet()) {
            listaLimpia.add(p.getValue());
            Log.e("listalimpia", p.getValue().toString());
        }*/

        //instancia con la clase tabla constructor
        tabla tab = new tabla(this, (TableLayout)findViewById(R.id.tabla));


        final SQLiteHelper admin = new SQLiteHelper(getApplication(), "esquites.db", null, 1);
        final SQLiteDatabase db = admin.getReadableDatabase();
        String []a={"descripcion","subtotal","precio"};
        Cursor c =db.query("productos_imp", a, null, null, null, null, null);

        while(c.moveToNext()){
            String descripcion = c.getString(0);
            String subtotal =c.getString(1);
            String precio=c.getString(2);
            float su = Float.parseFloat(subtotal);
           // String con = precio.replace("$","");
            Log.e("Salida BD","salida descripcion: "+descripcion+" subtotal: "+subtotal+" precio: "+precio) ;

            // lista.add(new arrayproductosfinal(descripcion,"$"+precio,subtotal));
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add(descripcion);
            elementos.add("$"+precio);
            elementos.add("$"+subtotal);
            tab.agregarFilaTabla(elementos);
        }


    }
    @Override
    public void onBackPressed() {
    /*    for (int i = 0; i< comunicador.getObjeto().size(); i++){

           // lista.remove(i);

            Log.e("elimino", "lista de esta actividad");
        }*/

        final SQLiteHelper admin = new SQLiteHelper(getApplication(), "esquites.db", null, 1);
        final SQLiteDatabase db = admin.getReadableDatabase();
        db.delete("productos_imp",null,null);
        db.close();
        Log.e("elimino", "datos de esta actividad");

        Intent intent = new Intent(FinalProductoActivity.this, InsertarProductoActivity.class);
        //intent.putExtra("miLista", input);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}
