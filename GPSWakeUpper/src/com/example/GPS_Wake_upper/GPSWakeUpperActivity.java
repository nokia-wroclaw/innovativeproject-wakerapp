package com.example.GPS_Wake_upper;
/**
 * Created by Adam on 2014-11-05.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GPSWakeUpperActivity extends Activity {
    /**
     * Called when the activity is first created.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }




        public void onClick(View view) {
            Intent mainIntent = new Intent(GPSWakeUpperActivity.this, startingActivity.class);
            GPSWakeUpperActivity.this.startActivity(mainIntent);
            Log.i("Content", "Main layout");
        }


}
