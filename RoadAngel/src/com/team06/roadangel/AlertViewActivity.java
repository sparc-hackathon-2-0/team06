package com.team06.roadangel;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import view.AlertListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: David.Pechacek
 * Date: 8/25/12
 * Time: 5:01 PM
 */
public class AlertViewActivity extends ListActivity {
    private AlertListAdapter adapter = null;
    private Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertviewer);

        context = getApplicationContext();

        getAlerts();
    }

    private void getAlerts() {
        List<String> alerts = new ArrayList<String>();

        //TODO: Get alerts

        adapter = new AlertListAdapter(context, R.layout.alert, alerts);
        setListAdapter(adapter);
    }
}