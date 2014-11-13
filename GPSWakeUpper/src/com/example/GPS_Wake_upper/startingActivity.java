package com.example.GPS_Wake_upper;

/**
 * Created by Adam on 2014-11-13.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class startingActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localize);
    }

    Button btnLocalize = (Button) findViewById(R.id.localizeButton);

    View.OnClickListener handler = new View.OnClickListener() {
        public void onClick(View view) {
            btnLocalize.setOnClickListener(handler);
            Intent gpsIntent = new Intent(startingActivity.this, gpsActivity.class);
            startingActivity.this.startActivity(gpsIntent);
            Log.i("Content", "Localization layout");
        }
    };
}


