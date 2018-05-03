package com.example.christofferpiilmann.galgeleg;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by emilyrasmussen on 03/05/2018.
 */

public class login extends AppCompatActivity implements View.OnClickListener {

    TextView user, pass;
    Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);


        // Resources

        user = (TextView) findViewById(R.id.user);
        pass = (TextView) findViewById(R.id.pass);
       

}

    @Override
    public void onClick(View view) {

    }
}
