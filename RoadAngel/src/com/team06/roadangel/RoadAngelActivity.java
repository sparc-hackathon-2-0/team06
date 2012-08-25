package com.team06.roadangel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.team06.roadangel.dao.UserDao;
import com.team06.roadangel.helper.FileHelper;
import com.team06.roadangel.model.User;

public class RoadAngelActivity extends Activity {
    private static final int GET_CODE = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Set the onclick listener for the login button
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new SubmitButtonListener());

        Spinner stateList = (Spinner) findViewById(R.id.selectState);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.
                createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateList.setAdapter(stateAdapter);

        Spinner messageList = (Spinner) findViewById(R.id.messageSelect);
        ArrayAdapter<CharSequence> messageListAdapter = ArrayAdapter.
                createFromResource(this, R.array.messages, android.R.layout.simple_spinner_dropdown_item);
        messageList.setAdapter(messageListAdapter);

        String key = FileHelper.loadFile(getCacheDir(), "user");

        // If the user doesn't exist, redirect to the register screen
        if(key != null && key.length() > 0) {
            startNotificationService(key);
            checkAlerts(key);
        }
        // create an account if there's no user (the application hasn't been used yet
        else {
            Intent intent = new Intent(RoadAngelActivity.this, CreateAccountActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_CODE){
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String key = (String) bundle.get("key");
                startNotificationService(key);
                checkAlerts(key);
            }
        }
    }

    private void startNotificationService(String key) {
        // Make sure the AlertNotificationService is started
        Intent alertServiceIntent = new Intent(this, AlertNotificationService.class);
        alertServiceIntent.putExtra(getString(R.string.server_key_varname), key);
        startService(alertServiceIntent);
    }

    private void checkAlerts(String key) {
        //If key exists, see if the user has any alerts
        RoadAngelService roadAngelService = new RoadAngelService(getApplicationContext());
        int numberOfAlerts = roadAngelService.getAlertCount(key);

        if(numberOfAlerts > 0) {
            Intent intent = new Intent(RoadAngelActivity.this, AlertViewActivity.class);
            startActivity(intent);
        }
    }

    private void sendMessage() {

    }

    private class SubmitButtonListener implements AdapterView.OnClickListener {
        public void onClick(View view) {
            //doLogin();
            sendMessage();
        }
    }
}
