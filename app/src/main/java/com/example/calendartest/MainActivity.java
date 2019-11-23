package com.example.calendartest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivityTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, EditEventActivity.class);
        startActivity(intent);
        Button button = findViewById(R.id.button);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case AlertDialog.BUTTON_POSITIVE:
                                insertToCalendar();
                                break;
                            case AlertDialog.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                dialog.setTitle(R.string.add_dialog_title)
                        .setMessage(R.string.add_dialog_message)
                        .setPositiveButton(R.string.dialog_postive_button, listener)
                        .setNegativeButton(R.string.dialog_negative_button, listener)
                        .show();
                return true;
            }
        });
    }

    public void insertToCalendar() {
        /*
        Intent intent = new Intent(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI);
        startActivity(intent);
        */
    }
}
