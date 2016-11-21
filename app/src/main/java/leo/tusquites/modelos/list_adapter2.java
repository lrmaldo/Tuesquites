package leo.tusquites.modelos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import leo.tusquites.R;
import leo.tusquites.list_adapter;

/**
 * Created by Leo on 20/11/2016.
 */

public class list_adapter2 extends ArrayAdapter<modeloProducto> {
    private final List<modeloProducto> list;
    private final Context context;

    public list_adapter2(Context context,List<modeloProducto> list) {
        super(context, R.layout.item_productos, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {//Un miembro protegido es accesible dentro de su clase y por instancias de clases derivadas.

        protected NumberPicker cantidad;
        protected TextView nombre;

        protected CheckBox checkbox;


    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator =LayoutInflater.from(context);// Activity.getLayoutInflater();

            view = inflator.inflate(R.layout.item_productos, null);



            final list_adapter2.ViewHolder viewHolder = new list_adapter2.ViewHolder();
            viewHolder.nombre = (TextView) view.findViewById(R.id.NombreCa_precio);

            viewHolder.cantidad = (NumberPicker) view.findViewById(R.id.numberPicker);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkBox);

            viewHolder.cantidad.setMinValue(0);
            // viewHolder.cantidad.setMaxValue(200);
            viewHolder.cantidad.setWrapSelectorWheel(false);

            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override                    //checkbox ,                activado:true-false

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    modeloProducto element = (modeloProducto) viewHolder.checkbox.getTag();
                    element.setSelected(buttonView.isChecked());

                }
            });

            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((list_adapter2.ViewHolder) view.getTag()).checkbox.setTag(list.get(position));

        }
        list_adapter2.ViewHolder holder = (list_adapter2.ViewHolder) view.getTag();

        holder.nombre.setText(list.get(position).getNombre());
        holder.checkbox.setText("$"+list.get(position).getPrecio());
      //  holder.cantidad.setValue(Integer.parseInt(list.get(position).getCantidad()));
        holder.cantidad.setMaxValue(Integer.parseInt(list.get(position).getCantidad()));
        holder.checkbox.setChecked(list.get(position).getSelected());
        return view;
    }







    @Override

    public int getCount() {
        return super.getCount();
    }

    @Override

    public modeloProducto getItem(int position) {
        return super.getItem(position);
    }


}
