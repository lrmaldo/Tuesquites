package leo.tusquites;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import leo.tusquites.modelos.usuarios;
import leo.tusquites.modelos.vistaUsuariosView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.R.attr.bitmap;
import static leo.tusquites.R.drawable.ic_action_account_circle_40;

public class DetalleUsuarioActivity extends AppCompatActivity  implements View.OnClickListener{

    public static final String EXTRA_POST_KEY = "post_key";
    private static final String TAG = "Storage#FinalProductoActivity";

    private static final int RC_TAKE_PICTURE = 101;
    private static final int RC_STORAGE_PERMS = 102;

    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";
    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    //autenticacion
    private FirebaseAuth mAuth;
    // [START declare_ref]
    private StorageReference mStorageRef;
    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private String mPostKey;
    ImageView foto;
    TextView nombre, correo, edi_nombre;
    Button Editar, eliminar, guardar_edit;
    DatabaseReference correo2;
    private ValueEventListener dbListener;
    private ProgressDialog mProgressDialog;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        // Restaurar  instancias
        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
        }


// Get post key from intent
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
       //inicio de intancia de autenticacion
        mAuth = FirebaseAuth.getInstance();
        // Initialize Database
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("usuarios").child(mPostKey);

        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]

        correo2 = mPostReference.child("email");
        foto = (ImageView) findViewById(R.id.foto_usuario);
        edi_nombre =(TextView)  findViewById(R.id.edit_nombre);
        guardar_edit =(Button) findViewById(R.id.guardarE_boton);
        nombre = (TextView) findViewById(R.id.nombre_usuario);
        correo = (TextView) findViewById(R.id.email_vista);
        Editar = (Button) findViewById(R.id.editar_btn);
        eliminar = (Button) findViewById(R.id.btn_eliminar);

        //// ***********Eliminar usuario
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://console.firebase.google.com/project/tuesquites/authentication/users"));
                startActivity(intent);
            }
        });

        ////******Editar usuario
        Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
edi_nombre.setVisibility(View.VISIBLE);
                guardar_edit.setVisibility(View.VISIBLE);
            }
        });


////// GUARDAR EDICION
guardar_edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // String key = mDatabase.child("posts").push().getKey();
      String validar = edi_nombre.getText().toString();
        if(validar.isEmpty()){
            Toast.makeText(DetalleUsuarioActivity.this, "Falta el poner el nuevo nombre",
                    Toast.LENGTH_SHORT).show();
        }
else {
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("nombre", edi_nombre.getText().toString());
            mPostReference.updateChildren(childUpdates);

            guardar_edit.setVisibility(View.GONE);
            edi_nombre.setVisibility(View.GONE);
        }
        }

});



/////*****AGREGAR FOTO AL USUARIO
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

 launchCamera();

            }
        });




    }
    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        if (requestCode == RC_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (mFileUri != null) {
                    uploadFromUri(mFileUri);
                } else {
                    Log.w(TAG, "File URI is null");
                }
            } else {
                Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @AfterPermissionGranted(RC_STORAGE_PERMS)
    private void launchCamera() {
        Log.d(TAG, "launchCamera");

        // Check that we have permission to read images from external storage.
        String perm = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !EasyPermissions.hasPermissions(this, perm)) {
            EasyPermissions.requestPermissions(this, "This sample reads images from your camera to demonstrate uploading.",
                    RC_STORAGE_PERMS, perm);
            return;
        }

        // Create intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Choose file storage location
        File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
        mFileUri = Uri.fromFile(file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

        // Launch intent
        startActivityForResult(takePictureIntent, RC_TAKE_PICTURE);
    }

    // [START upload_from_uri]
    private void uploadFromUri(Uri fileUri) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());

        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = mStorageRef.child("fotos_usuarios")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage
        // [START_EXCLUDE]
        showProgressDialog();
        // [END_EXCLUDE]
        Log.d(TAG, "uploadFromUri:dst:" + photoRef.getPath());
        photoRef.putFile(fileUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded
                        Log.d(TAG, "uploadFromUri:onSuccess");

                        // Get the public download URL
                        mDownloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("url_foto", mDownloadUrl.toString());
                        mPostReference.updateChildren(childUpdates);
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        Toast.makeText(DetalleUsuarioActivity.this, "Foto subida exitosamente!",
                                Toast.LENGTH_SHORT).show();
                        updateUI(mAuth.getCurrentUser());
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w(TAG, "uploadFromUri:onFailure", exception);

                        mDownloadUrl = null;

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        Toast.makeText(DetalleUsuarioActivity.this, "Error al subir foto",
                                Toast.LENGTH_SHORT).show();
                        updateUI(mAuth.getCurrentUser());
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END upload_from_uri]


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Subiendo Foto...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void onStart() {
        super.onStart();




      correo2.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              String text = dataSnapshot.getValue(String.class);
              correo.setText(text);
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                final vistaUsuariosView post = dataSnapshot.getValue(vistaUsuariosView.class);
                // [START_EXCLUDE]

              //DataSnapshot correoDb = dataSnapshot.child("usuarios").child(post.uid);
                nombre.setText(post.nombre);
                if(post.url_foto.equals("false")){
                    foto.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_account_circle_40));

                }
                else{
                    //Entra en un llamado en red para capturar la url  publica de la imagen
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            URL imageURL = null;


                            try {


                                imageURL = new URL(post.url_foto.toString());
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            try {
                                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                              //  bitmap1 = BitmapFactory.decodeStream(fondoUrl.openConnection().getInputStream());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);

                           foto.setImageBitmap(bitmap);
                            foto.setRotation(90);
                            //fondoheader.setBackground(new BitmapDrawable(bitmap1));

                        }
                    }.execute();

                }
               //correo.setText(correoDb.toString());
                //mBodyView.setText(post.body);
                // [END_EXCLUDE]


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
              //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(DetalleUsuarioActivity.this, "Falló al cargar datos",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        dbListener = postListener;


    }
    private void updateUI(FirebaseUser user) {
        // Signed in or Signed out
        if (user != null) {
           // findViewById(R.id.layout_signin).setVisibility(View.GONE);
           // findViewById(R.id.layout_storage).setVisibility(View.VISIBLE);
        } else {
            //findViewById(R.id.layout_signin).setVisibility(View.VISIBLE);
            //findViewById(R.id.layout_storage).setVisibility(View.GONE);
        }

        // Download URL and Download button
        if (mDownloadUrl != null) {
            //((TextView) findViewById(R.id.picture_download_uri))
               //     .setText(mDownloadUrl.toString());
            //findViewById(R.id.layout_download).setVisibility(View.VISIBLE);
        } else {
            //((TextView) findViewById(R.id.picture_download_uri))
             ///       .setText(null);
            //findViewById(R.id.layout_download).setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (dbListener != null) {
            mPostReference.removeEventListener(dbListener);
        }

        // Clean up comments listener
      //  mAdapter.cleanupListener();
    }

    @Override
    public void onClick(View view) {

    }




}




