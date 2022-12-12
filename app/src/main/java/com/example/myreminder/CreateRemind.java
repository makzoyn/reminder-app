package com.example.myreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;

import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CreateRemind extends AppCompatActivity implements View.OnClickListener{
    private TaskAdapter adapter;
    private ArrayList<Task> tasks = new ArrayList<>();
    EditText etRemind;
    Button  setTimeBtn, setDateBtn, doneBtn;
    String timeToNotify;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_remind);
        databaseHelper = new DatabaseHelper(this);
        tasks = databaseHelper.getTasks();
        etRemind = findViewById(R.id.etRemind);
        setTimeBtn = findViewById(R.id.setTimeBtn);
        setDateBtn = findViewById(R.id.setDateBtn);
        doneBtn = findViewById(R.id.doneBtn);
        setTimeBtn.setOnClickListener(this);
        setDateBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);
        adapter = new TaskAdapter(tasks);

    }

    @Override
    public void onClick(View view) {
        if(view == setTimeBtn)
        {
            selectTime();
        }
        else if(view == setDateBtn)
        {
            selectDate();
        }
        else
        {
            submit();
        }
    }
    private void submit()
    {
        String text = etRemind.getText().toString().trim();
        if(text.isEmpty())
        {
            Toast.makeText(this, "Please enter your remind text", Toast.LENGTH_LONG).show();
        }
        else {
            if(setTimeBtn.getText().toString().equals("SET TIME") || setDateBtn.getText().toString().equals("SET DATE"))
            {
                Toast.makeText(this, "Please select Time and Date", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // запись в sharedpreferences

                String value = etRemind.getText().toString().trim();
                String time = setTimeBtn.getText().toString().trim();
                String date = setDateBtn.getText().toString().trim();

                adapter.notifyItemInserted(0);
                tasks.add(0, new Task(value, date, time));
                databaseHelper.saveRemind(tasks);
                goToMainActivity();


            }
        }
    }
    private void selectTime()
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hour1, minute1) -> {
            timeToNotify = hour1 + ":" + minute1;
            setTimeBtn.setText(FormatTime(hour1, minute1));
        }, hour, minute, false);
        timePickerDialog.show();
    }
    private void selectDate()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            month1 += 1;
            setDateBtn.setText(day1 + "-" + month1 + "-" + year1);

        }, year, month,day);
        datePickerDialog.show();
    }
    public String FormatTime(int hour, int minute)
    {
        String time;

        String formattedMinute;

        if(minute/10==0)
        {
            formattedMinute = "0" + minute;
        }
        else
        {
            formattedMinute = "" + minute;
        }

        if(hour == 0)
        {
            time = "12" + ":" + formattedMinute + " AM";
        }
        else if(hour < 12)
        {
            time = hour +":"+formattedMinute+" AM";
        }
        else if(hour == 12)
        {
            time = "12" + ":" + formattedMinute + " PM";
        }
        else
        {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;
    }
    private void goToMainActivity()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}