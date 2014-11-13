package com.example.GPS_Wake_upper;
/**
 * Created by Adam on 2014-11-05.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GPSWakeUpperActivity extends Activity {
    /**
     * Called when the activity is first created.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    Button btnStart = (Button) findViewById(R.id.startButton);


    View.OnClickListener handler = new View.OnClickListener() {
        public void onClick(View view) {
            btnStart.setOnClickListener(handler);
            Intent mainIntent = new Intent(GPSWakeUpperActivity.this, startingActivity.class);
            GPSWakeUpperActivity.this.startActivity(mainIntent);
            Log.i("Content", "Main layout");
        }
    };


}
