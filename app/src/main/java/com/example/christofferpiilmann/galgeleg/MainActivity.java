package com.example.christofferpiilmann.galgeleg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button startbtn, helpbtn, highscorebtn, namechange;
    ListView usernames;
    final Context mContext = this;
    AlertDialog namelist, submitHighscore;
    TextView user;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbtn = (Button) findViewById(R.id.startbtn);
        helpbtn = (Button) findViewById(R.id.helpbtn);
        highscorebtn = (Button) findViewById(R.id.highscorebtn);
        namechange = (Button) findViewById(R.id.namechange);

        startbtn.setOnClickListener(this);
        helpbtn.setOnClickListener(this);
        highscorebtn.setOnClickListener(this);
        namechange.setOnClickListener(this);

        usernames = new ListView(this);
        user = (TextView) findViewById(R.id.username);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        dialogInit();

        String navn = prefs.getString("navn", "");
        if(navn == ""){
            namelist.show();
        } else {
            user.setText("Hej " + navn +"!");
        }
    }



    public void onClick(View v) {
        if (v == startbtn) {
            Intent i = new Intent(this, game.class);
            startActivity(i);
        } else if (v == helpbtn) {
            Intent i = new Intent(this, help.class);
            startActivity(i);

        } else if (v == highscorebtn) {
            Intent i = new Intent(this, highscore.class);
            startActivity(i);
        } else if (v == namechange) {
            namelist.show();
        }
    }

    private void dialogInit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Hej, hvad vil du gerne hedde?");
        builder.setMessage("Dit navn vil blive brugt på highscore listen");
        builder.setView(usernames);

        String[] items={"Bob","Mario","Chris","John","Andet?"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                R.layout.layout_username, R.id.txtitem,items);

        usernames.setAdapter(adapter);

        usernames.setOnItemClickListener(new
            AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ViewGroup vg=(ViewGroup)view;
                    TextView txt=(TextView)vg.findViewById(R.id.txtitem);
                    String usernam = txt.getText().toString();

                    if(usernam == "Andet?"){
                        submitHighscore.show();
                    } else {
                        prefs.edit().putString("navn", usernam).commit();
                        user.setText("Hej "+ usernam + "!");

                    }

                    namelist.dismiss();
                }

            });

        builder
                .setCancelable(false);
        namelist = builder.create();
        namelist.dismiss();


        //Highscore dialog
        LayoutInflater li = LayoutInflater.from(mContext);
        View dialogView = li.inflate(R.layout.layout_nameinput, null);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
        builder2.setTitle("Hvad vil du hedde?");
        builder2.setView(dialogView);
        final EditText userInput = (EditText) dialogView
                .findViewById(R.id.et_input);
        builder2
                .setCancelable(false)
                .setMessage("")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // get user input and
                                String usernam = userInput.getText().toString();
                                prefs.edit().putString("navn", usernam).commit();
                                user.setText("Hej "+ usernam + "!");
                            }
                        });
        submitHighscore = builder2.create();
        submitHighscore.dismiss();

    }
}