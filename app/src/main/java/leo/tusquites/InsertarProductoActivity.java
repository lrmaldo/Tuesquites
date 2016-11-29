package leo.tusquites;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import leo.tusquites.modelos.SQLiteHelper;
import leo.tusquites.modelos.arrayproductosfinal;
import leo.tusquites.modelos.list_adapter2;
import leo.tusquites.modelos.modeloProducto;

import static leo.tusquites.R.id.lista;

public class InsertarProductoActivity extends AppCompatActivity {
    EditText nombre_producto, precio,edi_cantidad;
    private Context context;
    ListView listView, listView_final;
    TextView titulo;
    Button guardarlist;
    ArrayList<modeloProducto> pro = new ArrayList<>();
    Dialog customDialog = null;
    NumberPicker npNbJours;

    ArrayList<modeloProducto> pro_final = new ArrayList<>();
    ArrayAdapter<modeloProducto> adapter,ad_final;

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


eliminarActualizar();




        }






    private void addView() {
        guardarlist = (Button) findViewById(R.id.guardarlista);
        nombre_producto = (EditText) findViewById(R.id.nombreProducto);
        precio = (EditText) findViewById(R.id.edi_precio);
        edi_cantidad = (EditText) findViewById(R.id.edi_cantidad);

        listView = (ListView) findViewById(lista);
        listView_final= (ListView)  findViewById(R.id.lista_final2);
titulo = (TextView) findViewById(R.id.textView);
        //listView.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nombre_producto.getText())|| TextUtils.isEmpty(precio.getText())||TextUtils.isEmpty(edi_cantidad.getText())){
                    Snackbar snackbar =  Snackbar.make(view, "Tal vez falta poner el nombre, precio o cantidad", Snackbar.LENGTH_LONG).setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();

                }else {
                    final SQLiteHelper  admin1 = new SQLiteHelper(getApplication(),"esquites.db",null,1);
                    final ContentValues registro = new ContentValues();
                    final SQLiteDatabase bd = admin1.getWritableDatabase();
                    // bd.delete("productos",null,null);

                    //listaProducto.add(get(nombre_producto.getText().toString(), precio.getText().toString()));
                   registro.put("nombre",nombre_producto.getText().toString());
                    registro.put("precio",precio.getText().toString());
                    registro.put("cantidad",edi_cantidad.getText().toString());
                    registro.put("cantidad_final","0");

                    bd.insert("productos", null, registro);
                    bd.close();
                    adapter.notifyDataSetChanged();
                    ad_final.notifyDataSetChanged();
                    nombre_producto.getText().clear();
                    precio.getText().clear();
                    edi_cantidad.getText().clear();
                    re_cursor();
                    Snackbar.make(view, "Se ha agregado un nuevo producto", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    finish();
                    startActivity(new Intent(getApplication(),InsertarProductoActivity.class));
                }
            }
        });




                ad_final=new list_adapter2(InsertarProductoActivity.this,pro_final);
                adapter = new list_adapter(InsertarProductoActivity.this,pro);
        listView_final.setAdapter(ad_final);
        listView.setAdapter(adapter);
      //listView_final;




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

             case R.id.menu_agregar:
                 customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
                 //deshabilitamos el título por defecto
                 customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                 //obligamos al usuario a pulsar los botones para cerrarlo
                 //customDialog.setCancelable(false);
                 //establecemos el contenido de nuestro dialog
                 customDialog.setContentView(R.layout.dialog_insertar);

                // TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                 //titulo.setText("Título del Dialog");

//                 TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
  //               contenido.setText("Mensaje con el contenido del dialog");
                    Button es = (Button) customDialog.findViewById(R.id.entrar_boton);
                 es.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {


                         ArrayList<String> cadena = new ArrayList<String>();
                         ArrayList<arrayproductosfinal> input = new ArrayList<arrayproductosfinal>();
                        // comunicador co = new comunicador();
                        // NumberPicker npNbJours;
                         final SQLiteHelper admin = new SQLiteHelper(getApplication(), "esquites.db", null, 1);
                         final SQLiteDatabase db = admin.getReadableDatabase();
                         String []a={"nombre","precio","cantidad","cantidad_final"};
                         Cursor c =db.query("productos", a, null, null, null, null, null);
                         //recursivo
                          //  co.setObjeto(null);

                         final SQLiteHelper  admin1 = new SQLiteHelper(getApplication(),"esquites.db",null,1);
                         final ContentValues registro = new ContentValues();
                         final SQLiteDatabase bd = admin1.getWritableDatabase();
                         // bd.delete("productos",null,null);

                         //listaProducto.add(get(nombre_producto.getText().toString(), precio.getText().toString()));

                         //for (int i = 0; i <= listView_final.getCount(); i++) {
                           // int i =0;
                           //  for(int i =0; i <= listView_final.getCount();i++) {
                             while   (c.moveToNext()){



                                 String nombre = c.getString(0);
                                 String precio = c.getString(1);
                                 int cantidad = Integer.valueOf(c.getString(2));
                                 int cantidad_final =Integer.valueOf(c.getString(3));
                                 String con = precio.replace("$", "");
                                 float prec = Float.parseFloat(con);
                                 float sub = (cantidad - cantidad_final) * prec;
                                 Log.e("Salida BD", "salida " + cantidad + " - " + cantidad_final + " *" + prec + "  =" + (cantidad - cantidad_final) * prec);


                                 //String output = "Présenter durant "+NbJours+" jours le questionnaire "+NomDuQr;
                                 String tex = "cantidad de " + cantidad_final;
                                 cadena.add(tex);
                                // input.add(new arrayproductosfinal(i,nombre + " x " + (cantidad - NbJours), sub));
                                 //QrEtOccurence.add(output);}
                                 registro.put("descripcion",nombre + " x "+(cantidad- cantidad_final));
                                 registro.put("subtotal",sub);
                                 registro.put("precio",precio);

                                 bd.insert("productos_imp", null, registro);

                                 Log.e("Insert producto_imp","salida "+c.getPosition()) ;
                              // i++;
                             }

                        // }
                         bd.close();





                         //recursivo
                         while(c.moveToNext()){
                             String nombre = c.getString(0);
                             String precio =c.getString(1);
                             String cantidad=c.getString(2);
                             String con = precio.replace("$","");
                             Log.e("Salida BD","salida "+con+" "+cantidad) ;

                         }




                    customDialog.dismiss();

                         Intent intent = new Intent(InsertarProductoActivity.this, FinalProductoActivity.class);
                         //intent.putExtra("miLista", input);
                         startActivity(intent);
                         finish();
                     }
                 });

customDialog.show();




//                for (int i=0; i<listaProducto.size(); i++){


//                    if(listaProducto.get(i).isSelected()){
            //                      listaProducto.add(get(listaProducto.get(i).getTitulo(), listaProducto.get(i).getDescripcion(), listaProducto.get(i).getId_imagen()));
            //                    listaProducto.get(i).setSelected(false);
            //              }

            //        }

            //      adapter.notifyDataSetChanged();

              break;

            case R.id.menu_borrar:

                for (int i=pro.size()-1; i>=0; i--){
                    if( pro.get(i).getSelected()){
                        final SQLiteHelper admin = new SQLiteHelper(this, "esquites.db", null, 1);
                        final SQLiteDatabase db = admin.getReadableDatabase();
                        db.delete("productos","nombre = '"+pro.get(i).getNombre()+"'",null);
                        pro.remove(i);
                        pro_final.remove(i);
                        //listaProducto.remove(i);

                    }
                }
                ad_final.notifyDataSetChanged();
                adapter.notifyDataSetChanged();




                break;

            case  R.id.menu_ocultar:
            {
             if(vista){
                 titulo.setText("Ahora pon los productos que te quedaron");

                 listView_final.setVisibility(View.VISIBLE);

                 listView.setVisibility(View.INVISIBLE);
                 nombre_producto.setVisibility(View.GONE);
                 precio.setVisibility(View.GONE);
                 edi_cantidad.setVisibility(View.GONE);
                 vista=false;
             }else {
                 nombre_producto.setVisibility(View.VISIBLE);
                 precio.setVisibility(View.VISIBLE);
                 edi_cantidad.setVisibility(View.VISIBLE);
                 titulo.setText("Lista  de productos");
                 listView_final.setVisibility(View.INVISIBLE);
                 listView.setVisibility(View.VISIBLE);
                 vista=true;
             }


             break;
            }

            case R.id.menu_editar:{



                for (int i=0; i<pro.size(); i++){


                    if(pro.get(i).getSelected()){
                        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
                        //deshabilitamos el título por defecto
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        //obligamos al usuario a pulsar los botones para cerrarlo
                        customDialog.setCancelable(false);
                        //establecemos el contenido de nuestro dialog
                        customDialog.setContentView(R.layout.dialog_insertar);
                        TextView titulo = (TextView)customDialog.findViewById(R.id.info_text);
                       final EditText cantidad = (EditText)customDialog.findViewById(R.id.cantidad_dialog);
                        final EditText nombre = (EditText)customDialog.findViewById(R.id.nombre_input);
                        final EditText precio = (EditText) customDialog.findViewById(R.id.Precio_dialog);
                        titulo.setText("Edite el producto");
                        nombre.setText(pro.get(i).getNombre());
                        cantidad.setText(pro.get(i).getCantidad());
                        precio.setText(pro.get(i).getPrecio());
                        final SQLiteHelper admin = new SQLiteHelper(this, "esquites.db", null, 1);
                        final SQLiteDatabase db = admin.getReadableDatabase();
                        db.delete("productos","nombre = '"+pro.get(i).getNombre()+"'",null);
                        pro.remove(i);
                        pro_final.remove(i);
                        Button guar = (Button)customDialog.findViewById(R.id.entrar_boton);
                        guar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final SQLiteHelper  admin1 = new SQLiteHelper(getApplication(),"esquites.db",null,1);
                                final ContentValues registro = new ContentValues();
                                final SQLiteDatabase bd = admin1.getWritableDatabase();
                                // bd.delete("productos",null,null);

                                //listaProducto.add(get(nombre_producto.getText().toString(), precio.getText().toString()));
                                registro.put("nombre",nombre.getText().toString());
                                registro.put("precio",precio.getText().toString());
                                registro.put("cantidad",cantidad.getText().toString());

                                bd.insert("productos", null, registro);
                                bd.close();

                                adapter.notifyDataSetChanged();
                                ad_final.notifyDataSetChanged();
                                customDialog.dismiss();
                                finish();
                               startActivity(new Intent(getApplication(),InsertarProductoActivity.class));
                            }
                        });

                        customDialog.show();
                    }

//                    if(listaProducto.get(i).isSelected()){
                    //                      listaProducto.add(get(listaProducto.get(i).getTitulo(), listaProducto.get(i).getDescripcion(), listaProducto.get(i).getId_imagen()));
                    //                    listaProducto.get(i).setSelected(false);
                    //              }

                            }

                    //      adapter.notifyDataSetChanged();



                break;
            }

            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }
    private void re_cursor(){
      // pro.clear();
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
            modeloProducto d1 = new modeloProducto(nombre,precio,cantidad);
            pro.add(d);
            pro_final.add(d1);

        }
        if(!(pro.size()<1)){
            listView_final.setAdapter(ad_final);
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
public void eliminarActualizar(){
    //SQLiteDatabase mDatabase = openOrCreateDatabase("esquites.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
    final SQLiteHelper admin = new SQLiteHelper(getApplication(), "esquites.db", null, 1);
    final SQLiteDatabase db = admin.getReadableDatabase();
    Cursor c = null;
    boolean tableExists = false;
/* get cursor on it */
    try
    {
        c = db.query("productos_imp", null,
                null, null, null, null, null);
        tableExists = true;
        if (tableExists){
            db.delete("productos_imp",null,null);
            final SQLiteHelper  admin1 = new SQLiteHelper(getApplication(),"esquites.db",null,1);
            final ContentValues registro = new ContentValues();
            final SQLiteDatabase bd = admin1.getWritableDatabase();
            registro.put("cantidad_final","0");

            String []a={"nombre","precio","cantidad","cantidad_final"};
            Cursor c1 =db.query("productos_imp", a, null, null, null, null, null);
            registro.put("cantidad_final","0");
            while (c1.moveToNext()){
                String nombre =c1.getString(0);
                bd.update("productos",registro,"nombre = '"+nombre+"'",null);
            }


            db.close();
            Log.e("elimino", "datos de esta actividad");
        }


    }
    catch (Exception e) {
    /* fail */
        Log.e("NOT", "tabla not exist :(((");
    }
}




}
