package com.example.christofferpiilmann.galgeleg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class highscore extends AppCompatActivity {

    private DatabaseReference mdatabaseHighscore;
    private ListView highscorelist;
    List<Score> scores;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        highscorelist = (ListView) findViewById(R.id.highscorelist);

        scores = new ArrayList<>();

        mdatabaseHighscore = FirebaseDatabase.getInstance().getReference("highscore");
    }

    @Override
    public void onStart() {
        super.onStart();

        mdatabaseHighscore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                scores.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Score score = postSnapshot.getValue(Score.class);
                    scores.add(score);
                    Collections.sort(scores);
                }

                HighscoreList ScoreAdapter = new HighscoreList(highscore.this, scores);

                highscorelist.setAdapter(ScoreAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("DB", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(highscore.this, "Failed to load score.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void submitHighscore(String name, Integer attempts){
        // Firebase init
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("highscore");

        // Push highscore resultat med et random generated ID som child
        Score newscore = new Score(name, attempts);
        myRef.push().setValue(newscore);
    }


}
