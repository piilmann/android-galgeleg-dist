package com.example.christofferpiilmann.galgeleg;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by emilyrasmussen on 03/05/2018.
 */

public class login extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    TextView statusText;
    EditText user, pass;
    Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        statusText = (TextView) findViewById(R.id.statusText);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);


        // Resources
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);


}

    public void loginUser(){

        String username = ""+user.getText();
        String password = ""+pass.getText();

        if(username == ""){
            username = "null";
        } else if(password == ""){
            password = "null";
        }

        try {
            String result = galgeLogik.hentUrl("http://ubuntu4.saluton.dk:4482/user/"+username+"/pass/"+password);
            JSONObject jsonResult = new JSONObject(result);

            Boolean loginStatus = jsonResult.getBoolean("login");

            if(loginStatus == true){
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                statusText.setVisibility(View.VISIBLE);
                user.setText("");
                pass.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == login) {
            progressBar.setVisibility(View.VISIBLE);

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object... arg0) {
                    try {
                        loginUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Fejl ved login: "+e;
                    }
                    return "Svar fra loginserver modtaget";
                }

                @Override
                protected void onPostExecute(Object resultat) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }.execute();



        }


    }

}
