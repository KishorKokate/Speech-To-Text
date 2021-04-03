package com.example.speechtotext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btn,hear;
    TextView textView;
    EditText hear_text;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn);
        textView=findViewById(R.id.text);
        hear=findViewById(R.id.hear_btn);
        hear_text=findViewById(R.id.speak_text);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.ENGLISH);

            }
        });

        hear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=hear_text.getText().toString().trim();
                if (text.isEmpty()){
                    hear_text.setError("Enter Text!!!!");
                }else {
                    textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                Intent record=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                record.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
                try {
                    startActivityForResult(record,1500);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Speech Recognizer now work", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1500){
            if (resultCode==RESULT_OK && data!= null){
                ArrayList<String> list=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                textView.setText(list.get(0));
            }

        }
    }
}