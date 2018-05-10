package com.example.christofferpiilmann.galgeleg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class game extends AppCompatActivity implements OnClickListener {

    TextView synligOrd, statusText;
    galgeDistLogik logik = new galgeDistLogik();
    Button btna, btnb, btnc, btnd, btne, btnf, btng, btnh, btni, btnj, btnk, btnl, btnm, btnn, btno, btnp, btnq, btnr, btns, btnt, btnu, btnv, btnw, btnx, btny, btnz, btnæ, btnø, btnå;
    ImageView galgeImg;
    Button[] btnlist = new Button[]{btna, btnb, btnc, btnd, btne, btnf, btng, btnh, btni, btnj, btnk, btnl, btnm, btnn, btno, btnp, btnq, btnr, btns, btnt, btnu, btnv, btnw, btnx, btny, btnz, btnæ, btnø, btnå};
    AlertDialog alertDialog, submitHighscore;
    ProgressBar progressBar;
    MediaPlayer mpWin, mpLose;
    SharedPreferences prefs;
    char[] alfabet = "abcdefghijklmnopqrstuvwxyzæøå".toCharArray();
    final Context mContext = this;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // AlertDialogs setup med builders
        dialogInit();

        // Knapper (keyboard)
        for(int i = 0; i<=btnlist.length-4; i++){
            String buttonID = "btn"+alfabet[i];
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); //fungerer ikke med æøå
            btnlist[i] = (Button) findViewById(resID);
            btnlist[i].setOnClickListener(this);
        }
        btnæ = (Button) findViewById(R.id.btnæ);
        btnø = (Button) findViewById(R.id.btnø);
        btnå = (Button) findViewById(R.id.btnå);
        btnæ.setOnClickListener(this);
        btnø.setOnClickListener(this);
        btnå.setOnClickListener(this);

        // Resources
        galgeImg = (ImageView) findViewById(R.id.galgeImg);
        synligOrd = (TextView) findViewById(R.id.synligOrd);
        statusText = (TextView) findViewById(R.id.statusText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mpWin = MediaPlayer.create(this, R.raw.win);
        mpLose = MediaPlayer.create(this, R.raw.lose);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (savedInstanceState == null) {
            // Nyt spil init
            logik.hentOrdFraDR(); //done
            logik.nulstil();
            setAllButtonsBlack();
            synligOrd.setText(logik.getSynligtOrd());

        } else {
            // Gendan spillet, ved ændring af instans (Skærmrotation etc.)
            // TODO: Få den til at overføre keyboard UI knapfarver

            ArrayList<String> mBrugteBogstaver = savedInstanceState.getStringArrayList("BRUGTEBOGSTAVER");
            String mOrdet = savedInstanceState.getString("ORDET");
            int mForkerteBogstaver = savedInstanceState.getInt("ANTALFORKERTE");
            //logik.indlæsSpil(mBrugteBogstaver,mForkerteBogstaver,mOrdet);

            synligOrd.setText(logik.getSynligtOrd());
            opdaterGalgeImg();
            statusText.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }




    public void gætBogstav(String input){
        logik.gætBogstav(input);
        if(logik.erSpilletSlut()){
            if(logik.erSpilletVundet()){
                mpWin.start();
                alertDialog.setMessage("Du har vundet");
                submitHighscore.setMessage("Dit antal forkerte forsøg var: " + logik.getAntalForkerteBogstaver());
                submitHighscore.show();
            } else {
                mpLose.start();
                alertDialog.setMessage("Du har tabt. Ordet du skulle gætte var: "+logik.getOrdet());
                alertDialog.show();
            }
        }

    }

    public void submitHighscore(String name2, Integer attempts){
        // Firebase init
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("highscore");
        String name = prefs.getString("navn", "");

        // Push highscore resultat med et random generated ID som child
        Score newscore = new Score(name, attempts);
        myRef.push().setValue(newscore);

        /*
        //Kunne også gemmes lokalt på telefonen med følgende linjer:
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //SKRIV VÆRDI TIL LOKAL:
        prefs.edit().putString("fornavn", fornavn).commit();

        //LÆS FRA LOKAL:
        String fornavn = prefs.getString("fornavn", "(ukendt)");
        */
    }

    private void setAllButtonsBlack(){
        for(int i = 0; i<btnlist.length-3;i++){
            Button currentbtn = btnlist[i];
            currentbtn.setTextColor(Color.BLACK);
        }
        btnæ.setTextColor(Color.BLACK);
        btnø.setTextColor(Color.BLACK);
        btnå.setTextColor(Color.BLACK);
    }


    private void dialogInit(){
        //Gameover dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("")
                .setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.newgame, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked newgame btn
                        logik.nulstil();
                        synligOrd.setText(logik.getSynligtOrd());
                        galgeImg.setImageResource(R.drawable.galge);
                        setAllButtonsBlack();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        finish();
                    }
                });

        alertDialog = builder.create();
        alertDialog.dismiss();

        //Highscore dialog
        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
        builder2.setTitle("Tilføj dit resultat til highscoren?");
        builder2
                .setCancelable(false)
                .setMessage("")
                .setPositiveButton("Ja",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // get user input and
                                // SUBMIT HIGHSCORE
                                String name = prefs.getString("name", "");
                                submitHighscore(name, logik.getAntalForkerteBogstaver());
                                alertDialog.show();
                            }
                        })
                .setNegativeButton("Nej",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                alertDialog.show();
                            }
                        });
        submitHighscore = builder2.create();
        submitHighscore.dismiss();

    }


    public void onClick(View v) {

        if (v instanceof Button) {
            Button b = (Button) v;
            if(logik.getOrdet().contains(b.getText().toString().toLowerCase())){
                b.setTextColor(Color.GREEN);
            } else {
                b.setTextColor(Color.RED);
            }
            gætBogstav(b.getText().toString().toLowerCase());
        }

        synligOrd.setText(logik.getSynligtOrd());
        System.out.println(logik.getOrdet());

        if(!logik.erSidsteBogstavKorrekt()){
            opdaterGalgeImg();
        }

    }
    public void opdaterGalgeImg(){
        if(logik.getAntalForkerteBogstaver() == 0){
            return;
        } else if(logik.getAntalForkerteBogstaver() == 1){
            galgeImg.setImageResource(R.drawable.forkert1);
        } else if(logik.getAntalForkerteBogstaver() == 2){
            galgeImg.setImageResource(R.drawable.forkert2);
        } else if(logik.getAntalForkerteBogstaver() == 3){
            galgeImg.setImageResource(R.drawable.forkert3);
        } else if(logik.getAntalForkerteBogstaver() == 4){
            galgeImg.setImageResource(R.drawable.forkert4);
        } else if(logik.getAntalForkerteBogstaver() == 5){
            galgeImg.setImageResource(R.drawable.forkert5);
        } else if(logik.getAntalForkerteBogstaver() == 6){
            galgeImg.setImageResource(R.drawable.forkert6);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("ANTALFORKERTE", logik.getAntalForkerteBogstaver());
        savedInstanceState.putString("ORDET", logik.getOrdet());
        //savedInstanceState.putStringArrayList("BRUGTEBOGSTAVER",logik.getBrugteBogstaver());


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}