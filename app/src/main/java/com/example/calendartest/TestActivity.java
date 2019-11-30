package com.example.calendartest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Calendars;
import android.util.Log;
import android.widget.Toast;

import java.net.URI;

public class TestActivity extends AppCompatActivity {
    final static int CALENDAR_REQUEST_CODE = 1;
    final static String TAG = "MainActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {

            doCalendarStuff();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR}, CALENDAR_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CALENDAR_REQUEST_CODE) {
            if(permissions.length == 2 && permissions[0].equals(Manifest.permission.WRITE_CALENDAR)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && permissions[1].equals(Manifest.permission.READ_CALENDAR)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //we got the user's permission
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED) {
                    doCalendarStuff();
                } else {
                    ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.WRITE_CALENDAR}, CALENDAR_REQUEST_CODE);
                }
            } else {
                Toast.makeText(this, "Unable to get permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void doCalendarStuff() throws SecurityException{
        String projection[] = new String[] {
                Calendars._ID,
                Events.TITLE,
                Events.DESCRIPTION,
                Events.DTSTART,
                Events.DTEND,
                Events.EVENT_LOCATION

        };
        String selection = Events.EVENT_LOCATION + " IS NOT ''";

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(Events.CONTENT_URI, projection, selection, null, null);
        while(cursor.moveToNext()) {
            Log.d(TAG, "id:" + cursor.getString(0));
            Log.d(TAG, "title:" + cursor.getString(1));
            Log.d(TAG, "description:" + cursor.getString(2));
            Log.d(TAG, "startdate:" + cursor.getString(3));
            Log.d(TAG, "enddate:" + cursor.getString(4));
            Log.d(TAG, "location:" + cursor.getString(5));
        }
    }
}
