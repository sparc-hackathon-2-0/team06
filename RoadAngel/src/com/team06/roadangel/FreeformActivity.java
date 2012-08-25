package com.team06.roadangel;

import org.ispeech.FreeformType;
import org.ispeech.SpeechRecognizer;
import org.ispeech.SpeechRecognizerEvent;
import org.ispeech.SpeechResult;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class FreeformActivity extends Activity {
	private static final String TAG = "iSpeech SDK Sample";
	SpeechRecognizer recognizer;
	private Context _context;

	private void updateInfoMessage(String msg) {
		Log.v(TAG, "INFO message: " + msg);
		TextView t = (TextView) findViewById(R.id.freeform_text);
		t.setText(msg);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this.getApplicationContext();

		setContentView(R.layout.asr_freeform);
		findViewById(R.id.freeform_record).setOnClickListener(new FreeFormListener());

		setupFreeFormDictation();

		updateInfoMessage("Start recording");
	}


	private void setupFreeFormDictation() {
		try {

			recognizer = SpeechRecognizer.getInstance(_context);
			recognizer.setFreeForm(FreeformType.FREEFORM_DICTATION);

		} catch (InvalidApiKeyException e) {
			e.printStackTrace();
		}
	}


	private class FreeFormListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			updateInfoMessage("Recording started");
			//startRecognition();
		}
	}


	


}