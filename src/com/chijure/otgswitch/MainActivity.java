package com.chijure.otgswitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Method;

public class MainActivity extends Activity {
    private static final String PROP = "sys.otg.mode";

    private ToggleButton mToggle;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.main);
        mStatus = (TextView) findViewById(R.id.status);
        mToggle = (ToggleButton) findViewById(R.id.toggle);
        mToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean host = mToggle.isChecked();
                if (!setProp(host ? "host" : "peripheral")) {
                    Toast.makeText(MainActivity.this, R.string.set_failed,
                            Toast.LENGTH_LONG).show();
                }
                refresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        boolean host = "host".equals(getProp());
        mToggle.setChecked(host);
        mStatus.setText(host ? R.string.status_host : R.string.status_periph);
        mStatus.setTextColor(getResources().getColor(
                host ? R.color.neon_green : R.color.neon_magenta));
    }

    // android.os.SystemProperties is hidden: reflection keeps one source for
    // every Android version this device gets (Gingerbread to Jelly Bean).
    private static String getProp() {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method m = c.getMethod("get", String.class, String.class);
            return (String) m.invoke(null, PROP, "");
        } catch (Exception e) {
            return "";
        }
    }

    private static boolean setProp(String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method m = c.getMethod("set", String.class, String.class);
            m.invoke(null, PROP, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
