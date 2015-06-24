package com.example.phungminhhoang.mydict;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;


public class word_definition_detail extends ActionBarActivity implements
        TextToSpeech.OnInitListener{

    TextView wordTextView;
    TextView definitionTextView;
    ImageButton speechImageButton;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_definition_detail);

        tts = new TextToSpeech(this, this);

        wordTextView = (TextView)findViewById(R.id.textView1);
        definitionTextView = (TextView)findViewById(R.id.definitionTextView);
        speechImageButton = (ImageButton)findViewById(R.id.speechImageButton);

        wordTextView.setText(getIntent().getStringExtra("word"));
        definitionTextView.setText(getIntent().getStringExtra("definition"));

        speechImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                speakOut();

            }
        });
    }

    private void speakOut() {

        String text = wordTextView.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speechImageButton.setEnabled(true);
              //  speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_definition_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
