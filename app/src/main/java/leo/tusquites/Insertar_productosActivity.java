package leo.tusquites;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import leo.tusquites.modelos.modeloProducto;

import static leo.tusquites.R.id.lista;


public class Insertar_productosActivity extends AppCompatActivity {

    EditText nombre_producto, precio;
private Context context;
    ListView listView;
     ArrayAdapter<modeloProducto> adapter;
    private List<modeloProducto> listaProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_productos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //btn_cant = (Button) findViewById(R.id.button3) ;


context =this;

 addView();

      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {


                //  listaProducto.add(get(nombre_producto.getText().toString(),precio.getText().toString()));
                //adapter.notifyDataSetChanged();
                //nombre_producto.getText().clear();
                //precio.getText().clear();

              //  Snackbar.make(view, "Se agrego un nuevo producto", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();


//            }
//        });



    }

    private void addView() {
        nombre_producto = (EditText) findViewById(R.id.nombreProducto);
        precio = (EditText) findViewById(R.id.edi_precio);
        listView = (ListView) findViewById(lista);







        adapter = new list_adapter(Insertar_productosActivity.this,getListaProductos());
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

                for (int i=listaProducto.size()-1; i>=0; i--){
                    if(listaProducto.get(i).getSelected()){
                        listaProducto.remove(i);

                    }
                }
                adapter.notifyDataSetChanged();




                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);
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

