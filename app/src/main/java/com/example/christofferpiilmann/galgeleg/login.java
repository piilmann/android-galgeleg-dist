package com.example.christofferpiilmann.galgeleg;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by emilyrasmussen on 03/05/2018.
 */

public class login extends AppCompatActivity implements View.OnClickListener {

    EditText user, pass;
    Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);


        // Resources

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);


}

    @Override
    public void onClick(View view) {
        if (view == login) {
          
        }
    }
}
