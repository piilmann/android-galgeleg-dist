package com.example.christofferpiilmann.galgeleg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by christofferpiilmann on 07/11/2017.
 */

public class HighscoreList extends ArrayAdapter<Score> {

        private Activity context;
        private List<Score> scores;

        public HighscoreList(Activity context, List<Score> scores) {
            super(context, R.layout.layout_highscore_list, scores);
            this.context = context;
            this.scores = scores;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_highscore_list, null, true);

            TextView textViewName = (TextView) listViewItem.findViewById(R.id.name);
            TextView textViewScore = (TextView) listViewItem.findViewById(R.id.score);

            Score score = scores.get(position);
            textViewName.setText(score.getName());
            textViewScore.setText("Antal forkerte gæt: "+score.getScore().toString());

            return listViewItem;
        }
    }
