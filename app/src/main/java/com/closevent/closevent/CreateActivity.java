package com.closevent.closevent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.closevent.closevent.service.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Côme on 07/03/2016.
 */
public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private Event eventCreated;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private TimePickerDialog fromHourPickerDialog;
    private TimePickerDialog toHourPickerDialog;
    private EditText EditDateBegin;
    private EditText EditDateEnd;
    private EditText EditHourBegin;
    private EditText EditHourEnd;
    private int year1;
    private int month1;
    private int day1;
    private int hour1;
    private int minute1;
    private int year2;
    private int month2;
    private int day2;
    private int hour2;
    private int minute2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        eventCreated = new Event();

        EditDateBegin = (EditText) findViewById(R.id.editDateBegin);
        EditDateEnd = (EditText) findViewById(R.id.editDateEnd);
        EditHourBegin = (EditText) findViewById(R.id.editHourBegin);
        EditHourEnd = (EditText) findViewById(R.id.editHourEnd);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

        EditDateBegin.setInputType(InputType.TYPE_NULL);
        EditDateEnd.setInputType(InputType.TYPE_NULL);
        EditHourBegin.setInputType(InputType.TYPE_NULL);
        EditHourEnd.setInputType(InputType.TYPE_NULL);

        EditDateBegin.setOnClickListener(this);
        EditDateEnd.setOnClickListener(this);
        EditHourBegin.setOnClickListener(this);
        EditHourEnd.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EditDateBegin.setText(dateFormatter.format(newDate.getTime()));
                year1=year;
                month1=monthOfYear;
                day1=dayOfMonth;
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EditDateEnd.setText(dateFormatter.format(newDate.getTime()));
                year2=year;
                month2=monthOfYear;
                day2=dayOfMonth;
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromHourPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hour, int minute) {
                EditHourBegin.setText(hour+":"+minute);
                hour1=hour;
                minute1=minute;
            }

        },newCalendar.HOUR_OF_DAY, newCalendar.MINUTE, true);

        toHourPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hour, int minute) {
                EditHourEnd.setText(hour+":"+minute);
                hour2=hour;
                minute2=minute;
            }

        },newCalendar.HOUR_OF_DAY, newCalendar.MINUTE, true);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_validate:
                // Perform action on click
                EditText EditName = (EditText) findViewById(R.id.editNameEvent);
                EditText EditLieu = (EditText) findViewById(R.id.editPlace);
                EditText EditDateBegin = (EditText) findViewById(R.id.editDateBegin);
                EditText EditDateEnd = (EditText) findViewById(R.id.editDateEnd);
                Switch SwitchEvPrivate = (Switch) findViewById(R.id.switchPrivate);

                boolean completed = true;

                System.out.println("in button");


                if(EditName.getText().length()<=0){
                    EditName.setError("You must fill this field");
                    completed = false;
                }
                else{
                    EditName.setError(null);
                }
                if(EditLieu.getText().length()<=0){
                    EditLieu.setError("You must fill this field");
                    completed = false;
                }
                else{
                    EditLieu.setError(null);
                }

                try {

                    Calendar newDate1 = Calendar.getInstance();
                    newDate1.set(year1,month1,day1,hour1,minute1);

                    Calendar newDate2 = Calendar.getInstance();
                    newDate2.set(year2,month2,day2,hour2,minute2);

                    Date d1 = newDate1.getTime();
                    Date d2 = newDate2.getTime();


                    if(d1.after(d2) && EditDateBegin.getText().length()>0 && EditDateEnd.getText().length()>0 && EditHourBegin.getText().length()>0 && EditHourEnd.getText().length()>0){
                        EditDateEnd.setText("");
                        EditHourEnd.setText("");
                        EditHourEnd.setError("Pick a greater date than above");
                        completed = false;
                    }
                    else{
                        if(EditDateBegin.getText().length()<=0){
                            EditDateBegin.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            EditDateBegin.setError(null);
                        }
                        if(EditDateEnd.getText().length()<=0){
                            EditDateEnd.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            EditDateEnd.setError(null);
                        }
                        if(EditHourBegin.getText().length()<=0){
                            EditHourBegin.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            EditHourBegin.setError(null);
                        }
                        if(EditHourEnd.getText().length()<=0){
                            EditHourEnd.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            EditHourEnd.setError(null);
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("in catch");
                }

                if(completed==true){
                    eventCreated.setName(EditName.getText().toString());
                    eventCreated.setAddress(EditLieu.getText().toString());
                    eventCreated.setDateDebut(EditDateBegin.getText().toString());
                    eventCreated.setDateFin(EditDateEnd.getText().toString());
                    eventCreated.setEvPrivate(SwitchEvPrivate.isChecked());
                }


        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        if(view == EditDateBegin) {
            fromDatePickerDialog.show();
        } else if(view == EditDateEnd) {
            toDatePickerDialog.show();
        } else if(view == EditHourBegin){
            fromHourPickerDialog.show();
        } else if(view == EditHourEnd){
            toHourPickerDialog.show();
        }


    }

}
