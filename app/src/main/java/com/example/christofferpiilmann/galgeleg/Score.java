package com.example.christofferpiilmann.galgeleg;

import android.support.annotation.NonNull;

/**
 * Created by christofferpiilmann on 06/11/2017.
 *
 * Klasse benyttes til at registrere en highscore, som sendes til Firebase.
 */

public class Score implements Comparable<Score> {

    public String name;
    public Integer attempts;

    public Score() {
    }

    public Score(String name, Integer attempts) {
        this.name = name;
        this.attempts = attempts;
    }

    public String getName(){
        return name;
    }

    public Integer getScore(){
        return attempts;
    }

    @Override
    public int compareTo(Score comparesto) {
        int compareattempts=((Score)comparesto).getScore();
        return this.attempts-compareattempts;
    }
}
