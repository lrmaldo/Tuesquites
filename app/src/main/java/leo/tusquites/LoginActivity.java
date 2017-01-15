package leo.tusquites;

import android.app.Dialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements LoginFragment.Callback {

    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupTranslucentStatusBar();
        /*uso del disco sin conexion*/
     try {
         FirebaseDatabase.getInstance().setPersistenceEnabled(true);

         LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                 .findFragmentById(R.id.login_container);
         if (loginFragment == null) {
             loginFragment = LoginFragment.newInstance();
             getSupportFragmentManager().beginTransaction()
                     .add(R.id.login_container, loginFragment)
                     .commit();
         }

         LoginInteractor loginInteractor = new LoginInteractor(
                 getApplicationContext(), FirebaseAuth.getInstance());
         new LoginPresenter(loginFragment, loginInteractor);


     }catch (Exception e){
         e.printStackTrace();
     }
        }

    private void setupTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onInvokeGooglePlayServices(int errorCode) {
        showPlayServicesErrorDialog(errorCode);
    }

    void showPlayServicesErrorDialog(
            final int errorCode) {
        Dialog dialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(
                        LoginActivity.this,
                        errorCode,
                        REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}

