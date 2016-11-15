package leo.tusquites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import leo.tusquites.modelos.SQLiteHelper;
import leo.tusquites.modelos.modeloProducto;

import static leo.tusquites.R.id.lista;
import static leo.tusquites.R.id.nombreProducto;

public class InsertarProductoActivity extends AppCompatActivity {
    EditText nombre_producto, precio;
    private Context context;
    ListView listView;
    ArrayList<modeloProducto> pro = new ArrayList<>();
    ArrayAdapter<modeloProducto> adapter;
    private List<modeloProducto> listaProducto;
    boolean vista = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_producto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SQLiteHelper  admin1 = new SQLiteHelper(getApplication(),"esquites.db",null,1);
        final ContentValues registro = new ContentValues();
        final SQLiteDatabase bd = admin1.getWritableDatabase();
       // bd.delete("productos",null,null);

        context =this;

        addView();

        re_cursor();


    }



    private void addView() {
        nombre_producto = (EditText) findViewById(R.id.nombreProducto);
        precio = (EditText) findViewById(R.id.edi_precio);
        listView = (ListView) findViewById(lista);

        //listView.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nombre_producto.getText())|| TextUtils.isEmpty(precio.getText())){
                    Snackbar.make(view, "falta poner el producto o el precio", Snackbar.LENGTH_LONG).setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                            .setAction("Action", null).show();

                }else {
                    final SQLiteHelper  admin1 = new SQLiteHelper(getApplication(),"esquites.db",null,1);
                    final ContentValues registro = new ContentValues();
                    final SQLiteDatabase bd = admin1.getWritableDatabase();
                    // bd.delete("productos",null,null);

                    //listaProducto.add(get(nombre_producto.getText().toString(), precio.getText().toString()));
                   registro.put("nombre",nombre_producto.getText().toString());
                    registro.put("precio",precio.getText().toString());
                    registro.put("cantidad","0");

                    bd.insert("productos", null, registro);
                    bd.close();
                    adapter.notifyDataSetChanged();
                    nombre_producto.getText().clear();
                    precio.getText().clear();
                    re_cursor();
                    Snackbar.make(view, "Se ha agregado un nuevo producto", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });





                adapter = new list_adapter(InsertarProductoActivity.this,pro);
        listView.setAdapter(adapter);

    }


    private List<modeloProducto> getListaProductos(){
        listaProducto= new ArrayList<modeloProducto>();
        String  nombre = "nombre";
        String  precio = "2";

        //descripciones= ctx.getResources().getStringArray(R.array.descripciones);


        listaProducto.add(get(nombre,precio));


        //listaProducto.get(0).setSelected(true);
        return listaProducto;
    }



    private modeloProducto get(String nombre,String precio ) {
        return new modeloProducto(nombre, precio,"0");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu meu){
        getMenuInflater().inflate(R.menu.menu_main,meu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();


        switch (id){

            //  case R.id.menu_duplicar:

//                for (int i=0; i<listaProducto.size(); i++){


//                    if(listaProducto.get(i).isSelected()){
            //                      listaProducto.add(get(listaProducto.get(i).getTitulo(), listaProducto.get(i).getDescripcion(), listaProducto.get(i).getId_imagen()));
            //                    listaProducto.get(i).setSelected(false);
            //              }

            //        }

            //      adapter.notifyDataSetChanged();

            //    break;

            case R.id.menu_borrar:

                for (int i=pro.size()-1; i>=0; i--){
                    if( pro.get(i).getSelected()){
                        final SQLiteHelper admin = new SQLiteHelper(this, "esquites.db", null, 1);
                        final SQLiteDatabase db = admin.getReadableDatabase();
                        db.delete("productos","nombre = '"+pro.get(i).getNombre()+"'",null);
                        pro.remove(i);
                        //listaProducto.remove(i);

                    }
                }
                adapter.notifyDataSetChanged();




                break;

            case  R.id.menu_ocultar:
            {
             if(vista){
                 listView.setVisibility(View.INVISIBLE);
                 vista=false;
             }else {
                 listView.setVisibility(View.VISIBLE);
                 vista=true;
             }


             break;
            }

            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }
    private void re_cursor(){
       pro.clear();
        final SQLiteHelper admin = new SQLiteHelper(this, "esquites.db", null, 1);
        final SQLiteDatabase db = admin.getReadableDatabase();
        String []a={"nombre","precio","cantidad"};
        Cursor c =db.query("productos", a, null, null, null, null, null);
        //recursivo
        while(c.moveToNext()){
            String nombre = c.getString(0);
            String precio =c.getString(1);
            String cantidad=c.getString(2);

            modeloProducto d = new modeloProducto(nombre,precio
            ,cantidad);
            pro.add(d);
        }
        if(!(pro.size()<1)){
            listView.setAdapter(adapter);
        }
        db.close();
    }







    public void onClick(View v) {
        ArrayList<String> QrEtOccurence = new ArrayList<String>();
        TextView tvNomDuQr;
        NumberPicker npNbJours;
        Integer j = 0;

        //for (int i = 0; i < 10; i++) {
        // for (int i = 0; i < lv.getCount(); i++) {
        //v = lv.getChildAt(i);
        // tvNomDuQr=(TextView)v.findViewById(R.id.NombreCa);
        // npNbJours=(NumberPicker)v.findViewById(R.id.numberPickerOccurence);
        // String NomDuQr=tvNomDuQr.getText().toString();
        //Integer NbJours=npNbJours.getValue();
        //String output = "Présenter durant "+NbJours+" jours le questionnaire "+NomDuQr;
        //QrEtOccurence.add(output);}

        //String[] outputStrArr = new String[QrEtOccurence.size()];
        //for (int i = 0; i < QrEtOccurence.size(); i++) {
        //   outputStrArr[i] = QrEtOccurence.get(i);}

    }

}
