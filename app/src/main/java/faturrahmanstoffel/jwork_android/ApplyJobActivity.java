package faturrahmanstoffel.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class ApplyJobActivity, berfungsi untuk mengatur activity pada applied job
 * @author Fatur Rahman Stoffel
 * @version 21-06-2021
 */

public class ApplyJobActivity extends AppCompatActivity {

    private int jobseekerID;
    private int jobID;
    private String jobName;
    private String jobCategory;
    private double jobFee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerID = extras.getInt("jobseekerId");
            jobID = extras.getInt("job_id");
            jobName = extras.getString("job_name");
            jobCategory = extras.getString("job_category");
            jobFee = extras.getInt("job_fee");
        }

        /**
         * Finalisasi komponen yang ada sesuai dengan activity_apply_job.xml
         */
        final EditText mainReferralCode = findViewById(R.id.referral_code);
        final TextView mainCode = findViewById(R.id.textCode);
        final TextView mainJobName = findViewById(R.id.job_name);
        final TextView mainJobCategory = findViewById(R.id.job_category);
        final TextView mainJobFee = findViewById(R.id.job_fee);
        final TextView mainTotalFee = findViewById(R.id.total_fee);
        final RadioGroup mainVia = findViewById(R.id.radioGroup);
        final Button mainCount = findViewById(R.id.hitung);
        final Button mainApply = findViewById(R.id.btnApply);

        mainApply.setVisibility(View.INVISIBLE);
        mainCode.setVisibility(View.INVISIBLE);
        mainReferralCode.setVisibility(View.INVISIBLE);

        mainJobName.setText(jobName);
        mainJobCategory.setText(jobCategory);
        mainJobFee.setText("Rp. " + jobFee);
        mainTotalFee.setText("Rp. 0");

        /**
         * Digunakan saat memilih plihan pembayaran
         */
        mainVia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int z) {
                RadioButton mRadioButton = findViewById(z);
                mainCount.setEnabled(true);
                mainApply.setVisibility(View.INVISIBLE);
                switch (z){
                    case R.id.bank:
                        mainCode.setVisibility(View.INVISIBLE);
                        mainReferralCode.setVisibility(View.INVISIBLE);
                        mainCount.setVisibility(View.VISIBLE);
                        break;

                    case R.id.ewallet:
                        mainCode.setVisibility(View.VISIBLE);
                        mainReferralCode.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        /**
         * Digunakan saat menekan tombol count, sesuai dengan pilihan metode pembayaran
         */
        mainCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int z = mainVia.getCheckedRadioButtonId();
                switch (z){
                    case R.id.bank:
                        mainTotalFee.setText("Rp." +jobFee);
                        mainCount.setVisibility(View.INVISIBLE);
                        mainApply.setVisibility(View.VISIBLE);
                        break;

                    case R.id.ewallet:
                        String refCode = mainReferralCode.getText().toString();
                        final Response.Listener<String> bonusResponse = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (refCode.isEmpty()) {
                                    Toast.makeText(ApplyJobActivity.this, "Tidak ada referral code yang digunakan!!", Toast.LENGTH_SHORT).show();
                                    mainTotalFee.setText("Rp. " + jobFee);
                                } else {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        int extraFee = jsonResponse.getInt("extraFee");
                                        int minTotalFee = jsonResponse.getInt("minTotalFee");
                                        boolean bonusStatus = jsonResponse.getBoolean("active");

                                        if (!bonusStatus) {
                                            Toast.makeText(ApplyJobActivity.this, "Bonus Tidak Ada!", Toast.LENGTH_LONG).show();
                                        } else if (bonusStatus) {
                                            if (jobFee < extraFee || jobFee < minTotalFee) {
                                                Toast.makeText(ApplyJobActivity.this, "Referral Code Tidak Memenuhi Persyaratan", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(ApplyJobActivity.this, "Referral Code Berhasil Dipakai", Toast.LENGTH_LONG).show();
                                                mainTotalFee.setText("Rp. " + (jobFee + extraFee));
                                                mainCount.setVisibility(View.INVISIBLE);
                                                mainApply.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(ApplyJobActivity.this, "Referral Code Tidak Ditemukan", Toast.LENGTH_LONG).show();
                                        mainTotalFee.setText("Rp. " + jobFee);
                                    }
                                }

                            }
                        };
                        BonusRequest bonusRequest = new BonusRequest(refCode, bonusResponse);
                        RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                        queue.add(bonusRequest);
                        break;

                }
            }
        });


        /**
         * Digunakan saat menekan tombol apply, sehingga akan mengambil json object sesuai response yang ada
         */

        mainApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioId = mainVia.getCheckedRadioButtonId();
                ApplyJobRequest request = null;

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(ApplyJobActivity.this, "Apply Job Berhasil!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                Toast.makeText(ApplyJobActivity.this, "Apply Job Gagal!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ApplyJobActivity.this, "Apply Gagal!", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                if(selectedRadioId == R.id.bank) {
                    request = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }
                else if(selectedRadioId == R.id.ewallet) {
                    String refCode = mainReferralCode.getText().toString();
                    request = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), refCode, responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }
            }
        });
    }
}