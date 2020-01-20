package com.example.jihyunkim.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;

public class PlayListActivity extends AppCompatActivity {

    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        Intent intent = getIntent();
        int play_list = intent.getIntExtra("play_list", 0);
        level = intent.getStringExtra("level"); // 레벨을 받아옴

        final ArrayAdapter<CharSequence> Adapter;
        Adapter = ArrayAdapter.createFromResource(this, play_list, android.R.layout.simple_spinner_dropdown_item);

        ListView list = findViewById(R.id.play_list_by_level);
        list.setAdapter(Adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PlaySongActivity.class);

                switch (position) {
                    case 0:
                        String selected_song="";
                        if(level.equals("level_5"))
                            selected_song="hungarian_dance";
                        intent.putExtra("selected_song", selected_song);
                        startActivity(intent);
                        break;

                    case 1:
                        String selected_song2="";
                        if(level.equals("level_4"))
                            selected_song2="reminiscence";
                        intent.putExtra("selected_song", selected_song2);
                        startActivity(intent);
                        break;

                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });

    }
}