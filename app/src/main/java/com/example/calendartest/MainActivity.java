package com.example.calendartest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    CalendarHelper calendarHelper;

    final static String TAG = "MainActivityTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a new calendar helper object to interact with the calendar provider
        calendarHelper = new CalendarHelper(this);

        //set up listener for the button
        Button button = findViewById(R.id.button);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            /**
             * Triggers when the button is long-clicked
             * @param v a reference to the button that was clicked
             * @return true if this consumed the click event, false otherwise
             */
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    /**
                     * Triggers when
                     * @param dialog the dialog object which was interacted with
                     * @param which the button that was pressed
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case AlertDialog.BUTTON_POSITIVE:
                                insertToCalendar();
                                printEventsList();
                                break;
                            case AlertDialog.BUTTON_NEGATIVE:
                                printEventsList();
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

    /**
     * Starts an activity using android's calendar app so the user can add an event
     * without our application needing to handle the event creation
     */
    public void insertToCalendar() {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Gonzaga University");
        startActivity(intent);
    }

    /**
     * outputs a list of events to the log
     */
    public void printEventsList() {
        List<Event> eventList = calendarHelper.getEventsList();
        for(Event e: eventList) {
            Log.d(TAG, "printEventsList: " + e);
        }
    }
}
