package com.team06.roadangel;

import org.ispeech.FreeformType;
import org.ispeech.SpeechRecognizer;
import org.ispeech.SpeechRecognizerEvent;
import org.ispeech.SpeechResult;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.team06.roadangel.dao.UserDao;
import com.team06.roadangel.helper.FileHelper;
import com.team06.roadangel.model.User;

public class RoadAngelActivity extends Activity {
    private static final int GET_CODE = 0;
    
    SpeechRecognizer recognizer;

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

        Button speakButton = (Button) findViewById(R.id.speakButton);
        speakButton.setOnClickListener(new SpeakButtonListener());
        
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

    private void sendMessage() {}

    private class SpeakButtonListener implements AdapterView.OnClickListener {
        public void onClick(View view) {        	
        	startRecognition();      
        }
    }
    
    private class SubmitButtonListener implements AdapterView.OnClickListener {
        public void onClick(View view) {
            //doLogin();
            sendMessage();
        }
    }
    
    private void setupFreeFormDictation() {
		try {

			recognizer = SpeechRecognizer.getInstance(getApplicationContext());
			recognizer.setFreeForm(FreeformType.FREEFORM_DICTATION);

		} catch (InvalidApiKeyException e) {
			Toast toast = Toast.makeText(getApplicationContext(), "Error! " + e, Toast.LENGTH_LONG);
            toast.show();
			e.printStackTrace();
		}
	}
    
    private void startRecognition() {
		setupFreeFormDictation();
		
		if(recognizer == null) {
			Toast toast = Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG);
            toast.show();
            return;
		}
		
		try {
			recognizer.startRecord(new SpeechRecognizerEvent() {
				@Override
				public void onRecordingComplete() {
					//updateInfoMessage("Recording completed.");
				}

				@Override
				public void onRecognitionComplete(SpeechResult result) {
					//Log.v(TAG, "Recognition complete");
					if (result != null) {
						
						Toast toast = Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_LONG);
			            toast.show();
						
			            // SC License plate ABC123
			            String s = result.getText();
			            if(s.length() > 0 && s.indexOf("license plate") > -1) {
			            	
			            	String s1 = s.substring(0, s.indexOf("license plate"));
			            	String s2 = s.substring(s.indexOf("license plate") + 13);
			            	
			            	if(s1.length() > 0 && s2.length() > 0) {
			            		
			            	}
			            	
			            	
			            }
			            
			            
			            
			            
						//Log.d(TAG, "Text Result:" + result.getText());
						//Log.d(TAG, "Text Conf:" + result.getConfidence());

						//updateInfoMessage("Result: " + result.getText() + "\n\nconfidence: " + result.getConfidence());

					} else {
						//Log.d(TAG, "Result is null...");
					}
				}

				@Override
				public void onRecordingCancelled() {
					//updateInfoMessage("Recording cancelled.");
				}

				@Override
				public void onError(Exception exception) {
					//updateInfoMessage("ERROR: " + exception.getMessage());
					exception.printStackTrace();
				}
			});
		} catch (BusyException e) {
			e.printStackTrace();
		} catch (NoNetworkException e) {
			e.printStackTrace();
		}
	}
}
