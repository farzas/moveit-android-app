package com.sample.farza.moveit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by farzaali on 5/19/16.
 */
public class PackingTipsVideo extends AppCompatActivity {

    Button packRoom, packBathroom, packKitchen, packLiving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packing_tips);

        packRoom = (Button)findViewById(R.id.packingRoomButton);
        packRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bKbfeoTXhz4"));
                startActivity(intent);
            }
        });

        packBathroom = (Button)findViewById(R.id.packBathroomButtom);
        packBathroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=REApMUYDIt4"));
                startActivity(intent);
            }
        });
        packKitchen = (Button)findViewById(R.id.packKitchenButton);
        packKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=PmtRKN05APs"));
                startActivity(intent);
            }
        });
        packLiving = (Button)findViewById(R.id.packLivingButton);
        packLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=uyJalsCJfeM"));
                startActivity(intent);
            }
        });
    }

    }
