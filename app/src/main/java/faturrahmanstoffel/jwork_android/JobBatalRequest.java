package faturrahmanstoffel.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class JobBatalRequest, berfungsi membuat request URL untuk membatalkan job
 * @author Fatur Rahman Stoffel
 * @version 21-06-2021
 */

public class JobBatalRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;

    /**
     * Method untuk membatalkan request job
     * @param id invoice
     * @param listener response dari objek yang ada pada view
     */
    public JobBatalRequest(String id, Response.Listener<String> listener) {
        super(Method.PUT, URL + id, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("status", "Cancelled");
    }

    /**
     * Mengembalikan parameter map dari POST yang dipakai pada request invoice
     * @return parameter requet
     * @throws AuthFailureError jika ada kesalahan autentikasi
     */
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
