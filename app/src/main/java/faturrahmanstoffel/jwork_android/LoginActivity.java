package faturrahmanstoffel.jwork_android;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class LoginActivity, digunakan saat proses login halaman awal saat aplikasi berjalan
 * @author Fatur Rahman Stoffel
 * @version 21-06-2021
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Untuk membuat status bar transparan
        setTransparentStatusBar(LoginActivity.this);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final  EditText fieldEmail = findViewById(R.id.fieldEmail);
        final EditText fieldPassword = findViewById(R.id.fieldPassword);
        final Button tombolLogin = findViewById(R.id.tombolLogin);
        final  TextView textRegister = findViewById(R.id.textRegister);


        /**
         * Saat tombol login ditekan, maka field password dan email akan diperiksa, apakah sesuai regex
         */
        tombolLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String regexEmail = "^([\\w\\&\\*_~]+\\.{0,1})+@[\\w][\\w\\-]*(\\.[\\w\\-]+)+$";
                String regexPassword = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$";

                String email = fieldEmail.getText().toString();
                String password = fieldPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    fieldEmail.setError("Email dibutuhkan");
                    tombolLogin.setEnabled(true);
                    return;
                }

                if(!Pattern.matches(regexEmail, email)){
                    fieldEmail.setError("Email tidak valid");
                    tombolLogin.setEnabled(true);
                    return;
                }

                if(!Pattern.matches(regexPassword, password)){
                    fieldPassword.setError("Password tidak valid");
                    tombolLogin.setEnabled(true);
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    fieldPassword.setError("Password dibutuhkan");
                    tombolLogin.setEnabled(true);
                    return;
                }
                else{
                    tombolLogin.setEnabled(false);
                }

                /**
                 * Json object untuk menerima response
                 */
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("jobseekerid", jsonObject.getInt("id"));
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        /**
         * Untuk tombol register dan mengaraj ke RegisterActivity
         */
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Untuk membuat status bar transparan dan full screen
     * @param activity
     */
    public void setTransparentStatusBar(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}