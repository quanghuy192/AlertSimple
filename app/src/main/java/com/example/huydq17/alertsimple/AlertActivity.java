package com.example.huydq17.alertsimple;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huydq17 on 10/2/2015.
 */
public class AlertActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView hours, minutes, seconds;
    private Button start;
    private GridView gridView;
    private String st1OfPosition, nd2OfPosition;
    private static Utilities timeUltilities = new Utilities();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        hours = (TextView) findViewById(R.id.hour);
        minutes = (TextView) findViewById(R.id.minute);
        seconds = (TextView) findViewById(R.id.second);
        start = (Button) findViewById(R.id.start);
        gridView = (GridView) findViewById(R.id.gridView);

        hours.setOnClickListener(this);
        minutes.setOnClickListener(this);
        seconds.setOnClickListener(this);
        start.setOnClickListener(this);

        // Custom Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, getDataList());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    // Data for GridView
    private List<String> getDataList() {
        String[] list = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "Xoa Het", "0", "Xoa"};
        return Arrays.asList(list);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hour:
            case R.id.minute:
            case R.id.second: {
                setText(view);
            }
            break;
            case R.id.start: {

                //set no touch to gridView
                ((GridView) findViewById(R.id.gridView)).setOnItemClickListener(null);
                // countdown time
                new CountDownTimer(getCurrentMillionSeconds(), 1000) {

                    // update time
                    public void onTick(long millisUntilFinished) {
                        Log.i("Remaining: ", String.valueOf(millisUntilFinished / 1000));

                        timeUltilities.setMillionSeconds(millisUntilFinished);

                        // check hour if < 10
                        if (Integer.valueOf(timeUltilities.getHour()) < 10) {
                            hours.setText("0" + timeUltilities.getHour().toString());
                        } else {
                            hours.setText(timeUltilities.getHour().toString());
                        }

                        // get munite and seconds to update
                        // asa same as hour

                        if (Integer.valueOf(timeUltilities.getMinutes()) < 10) {
                            minutes.setText("0" + timeUltilities.getMinutes().toString());
                        } else {
                            minutes.setText(timeUltilities.getMinutes().toString());
                        }


                        if (Integer.valueOf(timeUltilities.getSeconds()) < 10) {
                            seconds.setText("0" + timeUltilities.getSeconds().toString());
                        } else {
                            seconds.setText(timeUltilities.getSeconds().toString());
                        }

                    }

                    // When finish , show toast messenges
                    public void onFinish() {
                        seconds.setText("00");
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        ((GridView) findViewById(R.id.gridView)).setOnItemClickListener(AlertActivity.this);
                    }
                }.start();
            }
            break;
        }
    }

    // Change color for Hour , Minutes, Second
    // If Hour set color into cyan, minute and second set color into white
    // Otherwise
    private void setText(View view) {
        st1OfPosition = null;
        TextView timeTextView = (TextView) view;
        hours.setTextColor(getResources().getColor(R.color.white));
        minutes.setTextColor(getResources().getColor(R.color.white));
        seconds.setTextColor(getResources().getColor(R.color.white));
        timeTextView.setTextColor(getResources().getColor(R.color.cyan));
    }

    // Set value for Hour, Minutes, Second
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Remove all
        if (position == 9) {
            hours.setText("00");
            minutes.setText("00");
            seconds.setText("00");
        } else if (position == 11) {

            // Check value of Hour, if focus (color == cyan)
            // Set time to default
            // As same as minutes and second
            if (hours.getCurrentTextColor() == (getResources().getColor(R.color.cyan))) {
                hours.setText("00");
            } else if (minutes.getCurrentTextColor() == (getResources().getColor(R.color.cyan))) {
                minutes.setText("00");
            } else {
                seconds.setText("00");
            }
        } else {

            // Check value of Hour, if focus (color == cyan)
            if (hours.getCurrentTextColor() == (getResources().getColor(R.color.cyan))) {
                if (st1OfPosition != null) {
                    // Second click set value 1nd for hour
                    // Then, set value of 1st = null
                    // change focus to minutes
                    nd2OfPosition = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    Log.i(">>>>>>>>>>>>>>>", st1OfPosition + nd2OfPosition);
                    hours.setText(st1OfPosition + nd2OfPosition);
                    setText(minutes);
                    st1OfPosition = null;
                } else {
                    // First click set value 2nd for hour
                    st1OfPosition = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    hours.setText("0" + st1OfPosition);
                }

                // As same as Hour
            } else if (minutes.getCurrentTextColor() == (getResources().getColor(R.color.cyan))) {
                if (st1OfPosition != null) {
                    nd2OfPosition = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    Log.i(">>>>>>>>>>>>>>>", st1OfPosition + nd2OfPosition);
                    minutes.setText(st1OfPosition + nd2OfPosition);
                    if (checkTime(st1OfPosition + nd2OfPosition)) {
                        setText(seconds);
                    } else {
                        minutes.setText("00");
                        setText(minutes);
                    }
                    st1OfPosition = null;
                } else {
                    st1OfPosition = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    minutes.setText("0" + st1OfPosition);
                }
                // As same as Hour
            } else if (seconds.getCurrentTextColor() == (getResources().getColor(R.color.cyan))) {
                if (st1OfPosition != null) {
                    nd2OfPosition = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    Log.i(">>>>>>>>>>>>>>>", st1OfPosition + nd2OfPosition);
                    if (checkTime(st1OfPosition + nd2OfPosition)) {
                        seconds.setText(st1OfPosition + nd2OfPosition);
                    } else {
                        seconds.setText("00");
                    }
                    st1OfPosition = null;
                } else {
                    st1OfPosition = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    seconds.setText("0" + st1OfPosition);
                }
            } else {
                // Nothing happen
            }

        }
    }

    // Check value of minutes and second
    // return false when time > 59
    private boolean checkTime(String time) {
        return Integer.valueOf(time) < 60 ? true : false;
    }

    // convernt time to millionSeconds
    private int getCurrentMillionSeconds() {
        int currentHours = Integer.valueOf(hours.getText().toString());
        int currentMinutes = Integer.valueOf(minutes.getText().toString());
        int currentSeconds = Integer.valueOf(seconds.getText().toString());
        int currentTime = (currentHours * 60 * 60 * 1000) + (currentMinutes * 60 * 1000) + (currentSeconds * 1000);
        Log.i("currentTime>>>", String.valueOf(currentTime));
        return currentTime;

    }
}

