package io.yanamba.aleien.yanumba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tim = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tim.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
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

    public void googleIt() {


    }
}
