package com.team06.roadangel;

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

/**
 * User: David.Pechacek
 * Date: 8/25/12
 * Time: 1:00 PM
 */
public class CreateAccountActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        Spinner stateList = (Spinner) findViewById(R.id.editState);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.
                createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateList.setAdapter(stateAdapter);

        // Set the onclick listener for the login button
        Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new SubmitButtonListener());
    }

//    private boolean isValidEmail(String emailAddress) {
//        boolean valid = false;
//        String emailPattern = ".+@.+\\.[a-z]+";
//        try {
//            Pattern pattern = Pattern.compile(emailPattern);
//            Matcher matcher = pattern.matcher(emailAddress);
//            valid = matcher.matches();
//        }
//        catch (RuntimeException ex) {
//            Log.e("RoadAngel: ", Log.getStackTraceString(ex));
//        }
//
//        return valid;
//    }

    private void createAccount() {
//        EditText userName = (EditText) findViewById(R.id.editEmail);
//        EditText password = (EditText) findViewById(R.id.editAccountPassword);
        EditText licensePlate = (EditText) findViewById(R.id.editLicensePlate);
        Spinner state = (Spinner) findViewById(R.id.editState);

//        String userNameString = userName.getText().toString();
//        String passwordString = password.getText().toString();
        String licensePlateString = licensePlate.getText().toString();
        String stateString = state.getSelectedItem().toString();

        if(licensePlateString.isEmpty() || stateString.isEmpty()) {//passwordString.isEmpty() || userNameString.isEmpty()
            Toast toast = Toast.makeText(getApplicationContext(), "All fields must be filled in", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(licensePlateString.length() < 6) {
            Toast toast = Toast.makeText(getApplicationContext(), "License Plate must be at least six characters", Toast.LENGTH_SHORT);
            toast.show();
        }
//        else if(isValidEmail(userNameString)) {
//            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT);
//            toast.show();
//        }

        boolean accountCreated = true;

        //TODO: Do account creation here

        if(accountCreated) {
            Intent intent = new Intent();
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
}