package com.team06.roadangel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.team06.roadangel.dao.UserDao;
import com.team06.roadangel.model.User;

public class RoadAngelActivity extends Activity {
    private UserDao persistenceDataSource = null;
    private static User currentUser = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        openDataSource();

        User user = persistenceDataSource.getUser();

        // Set the onclick listener for the login button
        Button loginButton = (Button) findViewById(R.id.submitButton);
        loginButton.setOnClickListener(new SubmitButtonListener());

        // Load the user if they've logged in before
        if(user != null) {
            currentUser = user;

//            TextView userName = (TextView) findViewById(R.id.editUserName);
//            TextView password = (TextView) findViewById(R.id.editPassword);
//            CheckBox rememberMe = (CheckBox) findViewById(R.id.editRememberMe);

//            if (!user.getUserName().isEmpty()) {
//                userName.setText(user.getUserName());
//            }
//
//            if(!user.getUserPw().isEmpty() && user.getRemember() == 1) {
//               password.setText(user.getUserPw());
//                rememberMe.setChecked(true);
//            }
        }

        // Make sure the AlertNotificationService is started
        Intent intent = new Intent(this, AlertNotificationService.class);
        intent.putExtra(getString(R.string.server_tag_varname), "XYZ");
        startService(intent);
    }

    private void openDataSource() {
        if(persistenceDataSource == null) {
            persistenceDataSource = new UserDao(this);
        }
    }

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
                persistenceDataSource.createUser(userNameString, userPassword, rememberMe);
            }
            else {
                persistenceDataSource.updateUser(currentUser.getUserId(), userNameString, userPassword, rememberMe);
            }
        }

        //TODO: Call send message screen

        Toast toast = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT);
        toast.show();
    }

    private class SubmitButtonListener implements AdapterView.OnClickListener {
        public void onClick(View view) {
            doLogin();
        }
    }
}
