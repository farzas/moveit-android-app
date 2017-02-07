package com.sample.farza.moveit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.farza.moveit.data.Item;

import java.io.File;

/**
 * Created by farzaali on 5/14/16.
 */
public class ItemDescription extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_add){
            //add photo activity
            //photoAdded = true;
            Intent intent = new Intent(ItemDescription.this, AddNewItemActivity.class);
            startActivity(intent);
        }else if (id == R.id.action_uninstall){
            //Unistall the app
            Uri packageUri = Uri.parse("package:com.sample.farza.moveit");
            Intent uninstall = new Intent(Intent.ACTION_DELETE,packageUri);
            startActivity(uninstall);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle intentInfo = intent.getExtras();

        //get photo selected from the intent
        Item item = (Item)intentInfo.getSerializable("item");
        setContentView(R.layout.item_description);

        //set the caption of the picture
        TextView picCaption = (TextView)findViewById(R.id.textView2);
        picCaption.setText(item.getName());

        try {
            // Set the  image from the path given
            ImageView animalImage = (ImageView) findViewById(R.id.imageView);
            String path = item.getImagePath();
            File imgFile = new File(path);
            //Toast.makeText(getApplicationContext(), "Inside on create photo desc" +imgFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            if(imgFile.exists()) {
                BitmapFactory.Options options=new BitmapFactory.Options();
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);
                animalImage.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}