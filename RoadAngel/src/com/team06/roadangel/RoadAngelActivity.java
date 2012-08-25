package com.team06.roadangel;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class RoadAngelActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Make sure the AlertNotificationService is started
        Intent intent = new Intent(this, AlertNotificationService.class);
        intent.getExtras().putString(getString(R.string.server_tag_varname), "XYZ");
        startService(intent);
    }
}
