package com.example.christofferpiilmann.galgeleg;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public class help extends AppCompatActivity {

ImageView galgeHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        galgeHelp = (ImageView) findViewById(R.id.galgeImg);
    }
}
