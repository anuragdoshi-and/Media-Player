package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

private String songNames[];
private int  STORAGE_PERMISSION = 1;
        ArrayList<String> arrayList;
        ArrayAdapter<String> adapter;

        ListView listView;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(MainActivity.this,
        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        requestPermission();
        }
        else{
        doAll();
        }
        }

public void doAll(){
        listView = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, play.class).putExtra("pos", i).putExtra("sNames", arrayList);
                        startActivity(intent);
                }
        });
        }

public void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

        }
        else {
        ActivityCompat.requestPermissions(this,new String[]  {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION );
        }
        }

public void getMusic(){
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if(songCursor != null && songCursor.moveToFirst()){
        int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

                 do{
                          String currnetTitle = songCursor.getString(songTitle);
                          String currnetAtrist = songCursor.getString(songArtist);
                          String currnetLocation = songCursor.getString(songLocation);
                          arrayList.add(currnetTitle);

                 }while(songCursor.moveToNext());

        }

    }
}
