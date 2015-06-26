package com.example.phungminhhoang.mydict;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import dict.Dict;


public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {
    EditText searchEditText;
    Button searchButton;
    ImageButton voiceImageButton;

    TextView wordTextView;
    TextView definitionTextView;
    ImageButton speechImageButton;
    ImageButton favoriteImageButton;

    ScrollView definitionScrollView;

    private TextToSpeech tts;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    Dict dict = new Dict();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchEditText = (EditText)findViewById(R.id.searchEditText);
        voiceImageButton = (ImageButton)findViewById(R.id.voiceImageButton);
        searchButton = (Button)findViewById(R.id.searchButton);

        tts = new TextToSpeech(this, this);

        wordTextView = (TextView)findViewById(R.id.textView1);
        definitionTextView = (TextView)findViewById(R.id.definitionTextView);
        //definitionScrollView = (ScrollView)findViewById(R.id.definitionScrollView);
        speechImageButton = (ImageButton)findViewById(R.id.speechImageButton);
        favoriteImageButton = (ImageButton)findViewById(R.id.favoriteImageButton);

        //event click
        speechImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                speakOut();

            }
        });

        favoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //lưu từ yêu thích

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String wordSeach = searchEditText.getText().toString();
                wordTextView.setText(wordSeach);
                String wordDef = dict.Search(wordSeach);
                definitionTextView.setText(getIntent().getStringExtra(wordDef));
            }
        });

        voiceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        definitionTextView.setText("@a /ei, ə/\n" +
                "*  danh từ,  số nhiều as,  a's\n" +
                "- (thông tục) loại a, hạng nhất, hạng tốt nhất hạng rất tốt\n" +
                "=his health is a+ sức khoẻ anh ta vào loại a\n" +
                "- (âm nhạc) la\n" +
                "=a sharp+ la thăng\n" +
                "=a flat+ la giáng\n" +
                "- người giả định thứ nhất; trường hợp giả định thứ nhất\n" +
                "=from a to z+ từ đầu đến đuôi, tường tận\n" +
                "=not to know a from b+ không biết tí gì cả; một chữ bẻ đôi cũng không biết\n" +
                "*  mạo từ\n" +
                "- một; một (như kiểu); một (nào đó)\n" +
                "=a very cold day+ một ngày rất lạnh\n" +
                "=a dozen+ một tá\n" +
                "=a few+ một ít\n" +
                "=all of a size+ tất cả cùng một cỡ\n" +
                "=a Shakespeare+ một (văn hào như kiểu) Sếch-xpia\n" +
                "=a Mr Nam+ một ông Nam (nào đó)\n" +
                "- cái, con, chiếc, cuốn, người, đứa...;\n" +
                "=a cup+ cái chén\n" +
                "=a knife+ con dao\n" +
                "=a son of the Party+ người con của Đảng\n" +
                "=a Vietnamese grammar+ cuốn ngữ pháp Việt Nam\n" +
                "*  giới từ\n" +
                "- mỗi, mỗi một\n" +
                "=twice a week+ mỗi tuần hai lần");
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

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speechImageButton.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        populateMyFirstMenu(menu);
        return true;
    }

    private void populateMyFirstMenu(Menu menu) {
        int groupId = 0;

        int smGroupId = 0; // don't care, same as Menu.NONE
        int smItemId = 5;  // fifth element
        int smOrder = 5;  // don't care, same as Menu.NONE

        SubMenu mySubMenu1 =  menu.addSubMenu(smGroupId, smItemId, smOrder, "Lịch sử");
        for(int i = 0; i < 20; i++)
        {
            mySubMenu1.add(smGroupId, 1, i, "WordSearchHistory");
        }

        SubMenu mySubMenu2 =  menu.addSubMenu(smGroupId, smItemId, smOrder, "Từ yêu thích");
        for(int i = 0; i < 20; i++)
        {
            mySubMenu2.add(smGroupId, 2, i, "WordSearchFavorite");
        }

        MenuItem item3 = menu.add(groupId, 3, 3, "Thoát");

        //set icons
        //item1.setIcon(R.drawable.history);
        //item2.setIcon(R.drawable.favorite_star);
        //item3.setIcon(R.drawable.power);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == 1) {
            String temp = item.getTitle().toString();
            wordTextView.setText(temp);

            //load dữ liệu history

            return true;
        }
        else if(id == 2) {
            String temp = item.getTitle().toString();
            wordTextView.setText(temp);

            //load dữ liệu favorite

            return true;
        }
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
                       wordTextView.setText(result.get(0));
                }
                //if(data  == null)
                //   Toast.makeText(getApplicationContext(),"No data", Toast.LENGTH_LONG).show();
                break;
            }

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
}
