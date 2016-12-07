package leo.tusquites.ordenador_reciclador;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import java.security.interfaces.DSAPublicKey;

import leo.tusquites.R;
import leo.tusquites.modelos.modeloRegistro;

/**
 * Created by Leo on 04/12/2016.
 */

public class RegistroViewHolder extends RecyclerView.ViewHolder{
    public TextView usuario;
     public TextView fecha;
     public TextView Total;
    public  TextView detalle;


    public RegistroViewHolder(View itemView) {
        super(itemView);
        usuario = (TextView) itemView.findViewById(R.id.nombre_autor);
        fecha = (TextView) itemView.findViewById(R.id.text_fecha);
        Total = (TextView) itemView.findViewById(R.id.text_total_dia);
        detalle = (TextView) itemView.findViewById(R.id.texto_cantidad_dia);
    }
    public void setTimestamp(String timestamp) {
        fecha.setText(timestamp);
    }
    public void bindTorRegistro(modeloRegistro registro) {
        usuario.setText(registro.usuario);
       /* fecha.setText(DateUtils.getRelativeTimeSpanString(
                (long) registro.tiempo).toString());*/
        //numStarsView.setText(String.valueOf(post.starCount));
        Total.setText(registro.total);
        detalle.setText("Fecha: "+registro.fecha);

       // starView.setOnClickListener(starClickListener);
    }
}
