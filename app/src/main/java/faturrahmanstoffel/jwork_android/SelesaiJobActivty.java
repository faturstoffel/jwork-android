package faturrahmanstoffel.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class SelesaiJobActivity, saat selesai apply job
 * @author Fatur Rahman Stoffel
 * @version 21-06-2021
 */

public class SelesaiJobActivty extends AppCompatActivity {

    TextView staticInvoiceId, staticJobseeker, staticInvoiceDate, staticPaymentType, staticInvoiceStatus, staticReferralCode, staticJob, staticTotalFee, staticJobName;
    TextView mainJobseekerName, mainInvoiceDate, mainPaymentType, mainInvoiceStatus, mainRefCode, mainJobFee, mainTotalFee;
    Button btnCancel, btnFinish;
    View viewAtas, viewBawah;

    private static int jobSeekerId;
    private int jobSeekerInvoiceId;
    private String date;
    private String paymentType;
    private int totalFee;
    private static String jobSeekerName;
    private static String jobName;
    private static int jobFee;
    private String invoiceStatus;
    private String refCode;
    private JSONObject bonus;

    /**
     * Memeriksa apakah ada apply job atau tidak
     * jika ada maka akan ditampilkan
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job_activty);

        staticInvoiceId = findViewById(R.id.invoice_id);
        staticJobseeker = findViewById(R.id.s_jobseeker);
        staticInvoiceDate = findViewById(R.id.s_InvoiceDate);
        staticPaymentType = findViewById(R.id.s_PaymentType);
        staticInvoiceStatus = findViewById(R.id.s_InvoiceStatus);
        staticReferralCode = findViewById(R.id.s_ReferralCode);
        staticJob = findViewById(R.id.s_Job);
        staticJobName = findViewById(R.id.jobName);
        staticTotalFee = findViewById(R.id.s_total_fee);
        mainJobseekerName = findViewById(R.id.jobseeker_name);
        mainInvoiceDate = findViewById(R.id.invoice_date);
        mainPaymentType = findViewById(R.id.payment_type);
        mainInvoiceStatus = findViewById(R.id.invoice_status);
        mainRefCode = findViewById(R.id.refCode);
        mainJobFee = findViewById(R.id.jobFee);
        mainTotalFee = findViewById(R.id.totalFee);
        btnCancel = findViewById(R.id.btnCancel);
        btnFinish = findViewById(R.id.btnFinish);
        viewAtas = findViewById(R.id.viewBatasAtas);
        viewBawah = findViewById(R.id.viewBatasBawah);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobSeekerId = extras.getInt("jobseekerid");
        }

        staticInvoiceId.setText("Tidak ada pesanan");
        staticJobseeker.setVisibility(View.INVISIBLE);
        staticInvoiceDate.setVisibility(View.INVISIBLE);
        staticPaymentType.setVisibility(View.INVISIBLE);
        staticInvoiceStatus.setVisibility(View.INVISIBLE);
        staticReferralCode.setVisibility(View.INVISIBLE);
        staticJob.setVisibility(View.INVISIBLE);
        staticJobName.setVisibility(View.INVISIBLE);
        staticTotalFee.setVisibility(View.INVISIBLE);
        mainJobseekerName.setVisibility(View.INVISIBLE);
        mainInvoiceDate.setVisibility(View.INVISIBLE);
        mainPaymentType.setVisibility(View.INVISIBLE);
        mainInvoiceStatus.setVisibility(View.INVISIBLE);
        mainRefCode.setVisibility(View.INVISIBLE);
        mainJobFee.setVisibility(View.INVISIBLE);
        mainTotalFee.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.INVISIBLE);
        viewAtas.setVisibility(View.INVISIBLE);
        viewBawah.setVisibility(View.INVISIBLE);

        fetchJob();
        buttonSelect();
    }

    /**
     * Untuk mengambil informasi mengenai job yang telah di apply
     */
    private void fetchJob() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.isEmpty()) {
                    Toast.makeText(SelesaiJobActivty.this, "Tidak ada job yang di applied!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SelesaiJobActivty.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    staticInvoiceId.setText("Invoice ID: ");
                    staticJobseeker.setVisibility(View.VISIBLE);
                    staticInvoiceDate.setVisibility(View.VISIBLE);
                    staticPaymentType.setVisibility(View.VISIBLE);
                    staticInvoiceStatus.setVisibility(View.VISIBLE);
                    staticReferralCode.setVisibility(View.VISIBLE);
                    staticJob.setVisibility(View.VISIBLE);
                    staticJobName.setVisibility(View.VISIBLE);
                    staticTotalFee.setVisibility(View.VISIBLE);
                    mainJobseekerName.setVisibility(View.VISIBLE);
                    mainInvoiceDate.setVisibility(View.VISIBLE);
                    mainPaymentType.setVisibility(View.VISIBLE);
                    mainInvoiceStatus.setVisibility(View.VISIBLE);
                    mainRefCode.setVisibility(View.VISIBLE);
                    mainJobFee.setVisibility(View.VISIBLE);
                    mainTotalFee.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.VISIBLE);
                    viewAtas.setVisibility(View.VISIBLE);
                    viewBawah.setVisibility(View.VISIBLE);
                    mainJobseekerName.setText(jobSeekerName);
                }
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject jsonInvoice = jsonResponse.getJSONObject(i);
                        invoiceStatus = jsonInvoice.getString("invoiceStatus");
                        jobSeekerInvoiceId = jsonInvoice.getInt("id");
                        date = jsonInvoice.getString("date");
                        paymentType = jsonInvoice.getString("paymentType");
                        totalFee = jsonInvoice.getInt("totalFee");
                        refCode = "---";
                        try {
                            bonus = jsonInvoice.getJSONObject("bonus");
                            refCode = bonus.getString("referralCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        staticInvoiceId.setText("Invoice ID: "  + jobSeekerInvoiceId);
                        mainInvoiceDate.setText(date.substring(0, 10));
                        mainPaymentType.setText(paymentType);
                        mainTotalFee.setText(String.valueOf(totalFee));
                        mainInvoiceStatus.setText(invoiceStatus);
                        mainRefCode.setText(refCode);

                        JSONObject jsonCustomer = jsonInvoice.getJSONObject("jobseeker");
                        jobSeekerName = jsonCustomer.getString("name");
                        mainJobseekerName.setText(jobSeekerName);

                        JSONArray jsonJobs = jsonInvoice.getJSONArray("jobs");
                        for (int j = 0; j < jsonJobs.length(); j++) {
                            JSONObject jsonJobObj = jsonJobs.getJSONObject(j);
                            jobName = jsonJobObj.getString("name");
                            staticJobName.setText(jobName);
                            jobFee = jsonJobObj.getInt("fee");
                            mainJobFee.setText(String.valueOf(jobFee));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        JobFetchRequest fetchRequest = new JobFetchRequest(String.valueOf(jobSeekerId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivty.this);
        queue.add(fetchRequest);
    }

    /**
     * Untuk membatalkan apply job yang ada, dan mengubah category invoice menjadi canceled
     */
    private void buttonSelect() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> cancelListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Intent intent = new Intent(SelesaiJobActivty.this, MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Toast.makeText(SelesaiJobActivty.this, "Job anda telah dibatalkan!", Toast.LENGTH_LONG).show();
                JobBatalRequest batalRequest = new JobBatalRequest(String.valueOf(jobSeekerInvoiceId), cancelListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivty.this);
                queue.add(batalRequest);
            }
        });

        /**
         * Untuk menyelesaikan job, dan category invoice menjadi finished
         */
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> doneListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Intent intent = new Intent(SelesaiJobActivty.this, MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Toast.makeText(SelesaiJobActivty.this, "Job anda telah selesai", Toast.LENGTH_LONG).show();
                JobSelesaiRequest selesaiRequest = new JobSelesaiRequest(String.valueOf(jobSeekerInvoiceId), doneListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivty.this);
                queue.add(selesaiRequest);
            }
        });
    }
}