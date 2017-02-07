package com.sample.farza.moveit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sample.farza.moveit.database.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by farzaali on 5/14/16.
 */
public class AddNewItemActivity extends AppCompatActivity{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    DataSource dataSource;

    final String externalAlbum = "moveit_external_camera_album";
    File photoFile;

    //DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

    String imagePath;
    String itemString;
    Integer moveId; //to insert into item database

    SQLiteDatabase sqLiteDatabase;

    EditText captionEditText;
    Button saveButton, cameraButton;
    Spinner categorySpinner;

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
            Intent intent = new Intent(AddNewItemActivity.this, AddNewItemActivity.class);
            //intent.putExtra("moveId", moveId);
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_item);
        //Intent intent = getIntent();
        //moveId = intent.getIntExtra("moveId", 1);
        //moveId = dataSource.getMoveId(moveName);

        dataSource = new DataSource(getApplicationContext());

        captionEditText = (EditText)findViewById(R.id.itemEditText);
        saveButton = (Button)findViewById(R.id.saveButton);
        cameraButton = (Button)findViewById(R.id.cameraButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemString = captionEditText.getText().toString();
                categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
                String category = String.valueOf(categorySpinner.getSelectedItem());


                //verify if caption is empty

                if (itemString == null || itemString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter item name", Toast.LENGTH_SHORT).show();

                } else {
                    //open connection
                    dataSource.open();
                   moveId =  dataSource.getLatestMoveId();
                    dataSource.createItem(itemString, category, imagePath, moveId);
                    // ** Amy
                    dataSource.createItemBox(itemString, category, imagePath, moveId);
                    Log.i("AMY", "Item box table created");
                    // ** Amy
                    dataSource.close();
                    //Toast.makeText(getApplicationContext(), "Photo saved. Please go back", Toast.LENGTH_SHORT).show();
                    finish();
                    //Intent intent = new Intent(AddNewItemActivity.this, ItemListActivity.class);
                    //startActivity(intent);

                }
            }
        });


        //open camera
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(getSystemAvailableFeatures().hasSystemFeature(PackageManager.FEATURE_CAMERA)){

                //}

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(takePictureIntent.resolveActivity(getPackageManager()) == null){
                    Toast.makeText(getApplicationContext(), "Unable to take picture", Toast.LENGTH_LONG).show();
                    return;
                }

                //create the photo file in external storage

                photoFile = createPhotoFile();
                //Toast.makeText(getApplicationContext(), imagePath, Toast.LENGTH_SHORT).show();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode != REQUEST_IMAGE_CAPTURE){
            return;
        }

        if(resultCode != RESULT_OK){
            Toast.makeText(getApplicationContext(), resultCode, Toast.LENGTH_SHORT).show();
            photoFile.delete();
            return;
        }

        try {
            InputStream inputStream = new FileInputStream(photoFile);

            //Enable save button if inout stream is not null

            if (inputStream != null){
                Button saveButton = (Button)findViewById(R.id.saveButton);
                saveButton.setEnabled(true);
            }


            //Set captured photo as image view
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setImageDrawable(Drawable.createFromStream(inputStream, null));
            galleryAddPic();
        }

        catch (Exception e) {
            e.printStackTrace();
        }


    }



    //creates a new file name and inserts this file into the directory
    //created by getAlbumStorageDirectory
    public File createPhotoFile(){
        File photoFile = null;
        try{
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getAlbumStorageDirectory();

            //creates file in the specified directory
            photoFile = File.createTempFile(imageFileName,".jpg",storageDir);


            imagePath = photoFile.getAbsolutePath();
           // Toast.makeText(getApplicationContext(), "Inside createPhotoFile()"+imagePath, Toast.LENGTH_LONG).show();
        }
        catch(IOException e){
            Log.d("abc", e.getMessage());
        }
        return photoFile;
    }


    //tell the MediaProvider to scan through
    // the locations again to pick that image and show it in gallery.
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    //creates a new directory to store the photo
    public File getAlbumStorageDirectory(){

        //get path of existing folder or create new one
        File albumFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), externalAlbum);

        if(albumFile.exists()){
            Log.d("farza", "Already Exists");
        }

        //creates a ne directory if it does not exist
        else if(albumFile.mkdirs()){
            Log.i("farza","New directory created");
        }

        //does not have ample permission to create directory
        else{
            Log.e("farza", "Failed to create. Check permissions.");
        }

        return albumFile;
    }


}


