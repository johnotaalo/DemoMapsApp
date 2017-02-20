package com.example.eric.demomapsapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eric.demomapsapp.model.Event;

public class ImageUpload extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    protected String eventId, eventName, eventDescription, eventStartTime, eventEndTime, imagePath = "";

    DbHelper db;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        db  = new DbHelper(ImageUpload.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle bundle = getIntent().getExtras();

        eventId = bundle.getString("eventId");
        eventName = bundle.getString("eventName");
        eventDescription = bundle.getString("eventDescription");
        eventStartTime = bundle.getString("eventStart");
        eventEndTime = bundle.getString("eventEnd");

        if(!eventId.isEmpty()){
            Event event = db.getEvent(Integer.parseInt(eventId));
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(event.getEvent_image()));
            this.imagePath = event.getEvent_image();
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordlayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex); // String picturePath contains the path of selected Image
                this.imagePath = picturePath;
                cursor.close();

                ImageView imageView = (ImageView) findViewById(R.id.imgView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
    }

    public void onTvimgProceed(View view) {
        if (!imagePath.isEmpty()) {
            Event event = new Event();

            event.setEvent_name(eventName);
            event.setEvent_description(eventDescription);
            event.setEvent_start_time(eventStartTime);
            event.setEvent_end_time(eventEndTime);
            event.setEvent_loc_lat("0");
            event.setEvent_loc_lng("0");
            event.setEvent_image(imagePath);
            if (!eventId.isEmpty()){
                event.setId(Integer.parseInt(eventId));
                db.updateEvent(event);
            }else{
                int id = db.addEvent(event);
                eventId = String.valueOf(id);
            }

            Intent intent = new Intent(ImageUpload.this, EventTickets.class);
            intent.putExtra("eventId", eventId);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
        }

    }

    public void onTvBack(View view) {
        finish();
    }
}
