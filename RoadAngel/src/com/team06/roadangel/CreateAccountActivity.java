package com.team06.roadangel;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.team06.roadangel.helper.FileHelper;
import com.team06.roadangel.util.XMLParser;

/**
 * User: David.Pechacek
 * Date: 8/25/12
 * Time: 1:00 PM
 */
public class CreateAccountActivity extends Activity {

    private RoadAngelService roadAngelService;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        roadAngelService = new RoadAngelService(getApplicationContext());

        Spinner stateList = (Spinner) findViewById(R.id.editState);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.
                createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateList.setAdapter(stateAdapter);

        // Set the onclick listener for the login button
        Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new SubmitButtonListener());
        
        // Set the onskip listener for the login button
        Button skipButton = (Button) findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new SkipButtonListener());
    }

    private void createAccount() {
        EditText licensePlate = (EditText) findViewById(R.id.editLicensePlate);
        Spinner state = (Spinner) findViewById(R.id.editState);

        String licensePlateString = licensePlate.getText().toString();
        String stateString = state.getSelectedItem().toString();

        boolean validated = true;
        
        if(licensePlateString.isEmpty() || stateString.isEmpty()) {//passwordString.isEmpty() || userNameString.isEmpty()
            Toast toast = Toast.makeText(getApplicationContext(), "All fields must be filled in", Toast.LENGTH_SHORT);
            toast.show();
            validated = false;
        }
        else if(licensePlateString.length() < 6) {
            Toast toast = Toast.makeText(getApplicationContext(), "License Plate must be at least six characters", Toast.LENGTH_SHORT);
            toast.show();
            validated = false;
        }

        if(!validated) {
        	return;
        }
        
        // Create the account
        String key = roadAngelService.register(licensePlateString,stateString);
     
        boolean accountCreated = true;

        if(key != null && !key.isEmpty()) {
        	accountCreated = true;

        	// Write our key to a file
        	try {
        		FileHelper.write(getCacheDir(), "user", key);
        	}
        	catch(IOException e) {
        		Toast toast = Toast.makeText(getApplicationContext(), "Could not write to file: " + e + ", please try again.", Toast.LENGTH_SHORT);
        		toast.show();
        		accountCreated = false;
        	}
        }
        else {
        	accountCreated = false;
        }
        
        if(accountCreated) {
        	// Redirect to the send message
        	Intent intent = new Intent();
            intent.putExtra("key", key);
        	setResult(RESULT_OK, intent);
        	finish();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Account creation failed, please try again.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class SubmitButtonListener implements AdapterView.OnClickListener {
        public void onClick(View view) {
            createAccount();
        }
    }
    
    private class SkipButtonListener implements AdapterView.OnClickListener {
        public void onClick(View view) {
        	Intent intent = new Intent();
        	setResult(RESULT_OK, intent);
        	finish();
        }
    }
}