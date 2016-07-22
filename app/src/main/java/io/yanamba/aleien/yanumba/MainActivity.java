package io.yanamba.aleien.yanumba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tim = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tim.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);

        googleIt("89636361667");
    }

    // TODO: Incallservice
    // TODO: Read call log
    // TODO: System_alert_window
    // Boradcast reciever
    private class PhoneStateListener extends android.telephony.PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // called when someone is ringing to this phone

                    Toast.makeText(MainActivity.this,
                            "Incoming: " + incomingNumber,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public void googleIt(String phoneNumber) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://yandex.ru/search/?text=" + phoneNumber;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Volley", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", error.getLocalizedMessage());
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(request);

    }
}
