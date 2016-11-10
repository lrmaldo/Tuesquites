package leo.tusquites.ordenador_reciclador;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import leo.tusquites.R;
import leo.tusquites.modelos.vistaUsuariosView;

/**
 * Created by Leo on 12/10/2016.
 */



public class usuarioViewHolder extends RecyclerView.ViewHolder{

    public TextView Correo;
    public TextView nombre;
    public ImageView foto_u;

    public usuarioViewHolder(View itemView) {
        super(itemView);
       // Correo = (TextView)  itemView.findViewById(R.id.email_vista);
        nombre = (TextView) itemView.findViewById(R.id.nombre_usuario_list);
        foto_u = (ImageView) itemView.findViewById(R.id.foto_usuario_list);
    }
    public void bindToPost(vistaUsuariosView usuario, View.OnClickListener starClickListener) {
        nombre.setText(usuario.nombre);
        Glide.with(usuarioViewHolder.this.foto_u.getContext())
                .load(usuario.url_foto)
                .centerCrop()
                .into(foto_u);
       // nombre.setText(usuario.nombre);
        //numStarsView.setText(String.valueOf(post.starCount));
        //bodyView.setText(post.body);

//        starView.setOnClickListener(starClickListener);
    }
}
