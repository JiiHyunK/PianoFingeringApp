package com.example.jihyunkim.afinal;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LevelListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);

        final ArrayAdapter<CharSequence> Adapter;
        Adapter = ArrayAdapter.createFromResource(this, R.array.levels, android.R.layout.simple_spinner_dropdown_item);

        ListView list = findViewById(R.id.level_list);
        list.setAdapter(Adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PlayListActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra("play_list", R.array.level_1);
                        intent.putExtra("level", "level_1");
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("play_list", R.array.level_2);
                        intent.putExtra("level", "level_2");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("play_list", R.array.level_3);
                        intent.putExtra("level", "level_3");
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("play_list", R.array.level_4);
                        intent.putExtra("level", "level_4");
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("play_list", R.array.level_5);
                        intent.putExtra("level", "level_5");
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}