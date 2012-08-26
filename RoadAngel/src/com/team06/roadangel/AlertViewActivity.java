package com.team06.roadangel;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import com.team06.roadangel.model.Alert;
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
    RoadAngelService roadAngelService = null;
    private String serverKey;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertviewer);

        Intent intent = getIntent();
        String key = (String) intent.getExtras().get("key");
        context = getApplicationContext();
        serverKey = key;
        showAlerts(key);

        Button clearAlertsButton = (Button) findViewById(R.id.clearAlertsButton);
        clearAlertsButton.setOnClickListener(new ClearAlertsListener());
    }

    private void startRoadAngelService() {
        if(roadAngelService == null) {
            roadAngelService = new RoadAngelService(getApplicationContext());
        }
    }

    private void showAlerts(String key) {
        List<String> alerts = new ArrayList<String>();
        List<Alert> alertList = new ArrayList<Alert>();

        TextView header = (TextView) findViewById(R.id.alertHeader);
        String headerString = "";

        String[] messages = getResources().getStringArray(R.array.messages);

        startRoadAngelService();

        if(key != null) {
            alertList.addAll(roadAngelService.getAlerts(key));
        }

        if(alertList.size() > 1) {
            headerString = getResources().getString(R.string.alertHeaderMsgs);
        }
        else {
            headerString = getResources().getString(R.string.alertHeaderMsgs);
        }

        header.append(headerString);

        for(Alert alert : alertList) {
            String message = messages[Integer.parseInt(alert.getReason())];
            alerts.add(message);
        }

        adapter = new AlertListAdapter(context, R.layout.alert, alerts);
        setListAdapter(adapter);
    }

    private class ClearAlertsListener implements AdapterView.OnClickListener {
        public void onClick(View view) {
            roadAngelService.clearAlerts(serverKey);
        }
    }
}