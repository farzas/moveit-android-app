package com.sample.farza.moveit.movearchived;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sample.farza.moveit.R;
import com.sample.farza.moveit.previousmoveslist.MoveListActivity;

/**
 * Created by farzaali on 5/22/16.
 */
public class MoveArchived extends AppCompatActivity {

    Button seeArchived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_archived_view);
        seeArchived = (Button)findViewById(R.id.seeArchiveButton);
        seeArchived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoveArchived.this, MoveListActivity.class);
                startActivity(intent);
            }
        });

    }
}
