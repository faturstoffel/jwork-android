package faturrahmanstoffel.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class RegisterActivity, untuk proses register
 * @author Fatur Rahman Stoffel
 * @version 21-06-2021
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

      final EditText fieldNameRegister = findViewById(R.id.fieldNameRegister);;
      final EditText fieldEmailRegister = findViewById(R.id.fieldEmailRegister);
      final EditText fieldPasswordRegister = findViewById(R.id.fieldPasswordRegister);
      final Button tombolRegister = findViewById(R.id.tombolRegister);

        /**
         * Saat menekan tombol register
         */
        tombolRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fieldNameRegister.getText().toString();
                String email = fieldEmailRegister.getText().toString();
                String password = fieldPasswordRegister.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}
