package com.example.phungminhhoang.mydict;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    EditText searchEditText;
    Button searchButton;
    ListView dictionaryListView;
    ImageButton voiceImageButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText)findViewById(R.id.searchEditText);
        voiceImageButton = (ImageButton)findViewById(R.id.voiceImageButton);
        searchButton = (Button)findViewById(R.id.searchButton);
        dictionaryListView = (ListView)findViewById(R.id.dictionaryListView);

        //event click
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String string=searchEditText.getText().toString();

             //   WordDefinition wordDefinition=myDictionaryDatabaseHelper.getWordDefinition(string);

            //    if (wordDefinition==null) {
             //       Toast.makeText(MainActivity.this, "Word not found", Toast.LENGTH_LONG).show();
            //    }else {
                    Intent intent =new Intent(MainActivity.this, word_definition_detail.class);
                    intent.putExtra("word", searchEditText.getText().toString());
                    intent.putExtra("definition", "definition");

                    startActivity(intent);
            //    }


            }
        });

        voiceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();//////////////
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        populateMyFirstMenu(menu);
        return true;
    }

    private void populateMyFirstMenu(Menu menu) {
        int groupId = 0;

        //arguments: groupId, optionId, order, title
        MenuItem item1 = menu.add(groupId, 1, 1, "Lịch sử");
        MenuItem item2 = menu.add(groupId, 2, 2, "Từ yêu thích");
        MenuItem item3 = menu.add(groupId, 3, 3, "Thoát");

        //set icons
        item1.setIcon(R.drawable.history);
        item2.setIcon(R.drawable.favorite_star);
        item3.setIcon(R.drawable.power);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
         //   return true;
        //}
        if(id == 1) {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            return true;
        }
        else if(id == 2)
            return true;
        else if(id == 3) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //   txtSpeechInput.setText(result.get(0));
                    Intent intent =new Intent(MainActivity.this, word_definition_detail.class);
                    intent.putExtra("word", result.get(0));
                    startActivity(intent);
                }
                //if(data  == null)
                //   Toast.makeText(getApplicationContext(),"No data", Toast.LENGTH_LONG).show();
                break;
            }

        }
    }
}
