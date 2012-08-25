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
import com.team06.roadangel.model.User;

public class RoadAngelActivity extends Activity {
    private UserDao persistenceDataSource = null;
    private static User currentUser = null;
    private static final int GET_CODE = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        openDataSource();

        User user = persistenceDataSource.getUser();

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
        // Load the user if they've logged in before
        //if(user != null) {
            //currentUser = user;

            //TODO: Start server now

//            TextView userName = (TextView) findViewById(R.id.editUserName);
//            TextView password = (TextView) findViewById(R.id.editPassword);
//            CheckBox rememberMe = (CheckBox) findViewById(R.id.editRememberMe);
//
//            if (!user.getUserName().isEmpty()) {
//                userName.setText(user.getUserName());
//            }
//
//            if(!user.getUserPw().isEmpty() && user.getRemember() == 1) {
//                password.setText(user.getUserPw());
//                rememberMe.setChecked(true);
//            }
        }
        // create an account if there's no user (the application hasn't been used yet
        else {
            Intent intent = new Intent(RoadAngelActivity.this, CreateAccountActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
        // Make sure the AlertNotificationService is started
        //Intent intent = new Intent(this, AlertNotificationService.class);
        //intent.getExtras().putString(getString(R.string.server_tag_varname), "XYZ");
        //startService(intent);
    }

    private void openDataSource() {
        if(persistenceDataSource == null) {
            persistenceDataSource = new UserDao(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_CODE){
            if (resultCode == RESULT_OK) {
                //TODO: Start server
            }
        }
    }

    /**
    private void doLogin() {
        TextView userName = (TextView) findViewById(R.id.editUserName);
        TextView password = (TextView) findViewById(R.id.editPassword);
        CheckBox remember = (CheckBox) findViewById(R.id.editRememberMe);

        String userNameString = userName.getText().toString();
        String userPassword = password.getText().toString();
        boolean rememberMe = remember.isChecked();

        boolean loginSuccessful = true;
        //TODO: Do login here.
        if(loginSuccessful) {
            if(currentUser == null) {
                if(rememberMe) {
                    persistenceDataSource.createUser(userNameString, userPassword, rememberMe);
                }
                else {
                    persistenceDataSource.createUser(userNameString, "", rememberMe);
                }
            }
            else {
                if(rememberMe) {
                    persistenceDataSource.updateUser(currentUser.getUserId(), userNameString, userPassword, rememberMe);
                }
                else {
                    persistenceDataSource.updateUser(currentUser.getUserId(), userNameString, "", rememberMe);
                }

            }
        }

        //TODO: Call send message screen

        Toast toast = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT);
        toast.show();
    }*/

    private void sendMessage() {

    }

    private class SubmitButtonListener implements AdapterView.OnClickListener {
        public void onClick(View view) {
            //doLogin();
            sendMessage();
        }
    }
}
