package com.closevent.closevent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private SimpleDateFormat timeFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private TimePickerDialog fromHourPickerDialog;
    private TimePickerDialog toHourPickerDialog;
    private EditText editDateBegin;
    private EditText editDateEnd;
    private EditText editHourBegin;
    private EditText editHourEnd;
    private TextView editLocation;
    private Switch switchPrivate;
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

    private Date getEndDate(Date startDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Date now = new Date();
        eventCreated = new Event();

        editLocation = (TextView) findViewById(R.id.editLocation);
        editDateBegin = (EditText) findViewById(R.id.editStartDate);
        editDateEnd = (EditText) findViewById(R.id.editDateEnd);
        editHourBegin = (EditText) findViewById(R.id.editStartTime);
        editHourEnd = (EditText) findViewById(R.id.editEndTime);
        switchPrivate = (Switch) findViewById(R.id.switchPrivate);

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
        timeFormatter = new SimpleDateFormat("HH 'h' mm", Locale.FRANCE);
        editDateBegin.setHint(dateFormatter.format(now));
        editDateEnd.setHint(dateFormatter.format(this.getEndDate(now)));
        editHourBegin.setHint(timeFormatter.format(now));
        editHourEnd.setHint(timeFormatter.format(now));

        editDateBegin.setInputType(InputType.TYPE_NULL);
        editDateEnd.setInputType(InputType.TYPE_NULL);
        editHourBegin.setInputType(InputType.TYPE_NULL);
        editHourEnd.setInputType(InputType.TYPE_NULL);

        editDateBegin.setOnClickListener(this);
        editDateEnd.setOnClickListener(this);
        editHourBegin.setOnClickListener(this);
        editHourEnd.setOnClickListener(this);
        switchPrivate.setOnClickListener(this);
        editLocation.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editDateBegin.setText(dateFormatter.format(newDate.getTime()));
                year1=year;
                month1=monthOfYear;
                day1=dayOfMonth;
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editDateEnd.setText(dateFormatter.format(newDate.getTime()));
                year2=year;
                month2=monthOfYear;
                day2=dayOfMonth;
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromHourPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hour, int minute) {
                editHourBegin.setText(hour+":"+minute);
                hour1=hour;
                minute1=minute;
            }

        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        toHourPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hour, int minute) {
                editHourEnd.setText(hour+":"+minute);
                hour2=hour;
                minute2=minute;
            }

        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);


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
                EditText editName = (EditText) findViewById(R.id.editNameEvent);
                EditText editLieu = (EditText) findViewById(R.id.editPlace);
                EditText editDateBegin = (EditText) findViewById(R.id.editStartDate);
                EditText editDateEnd = (EditText) findViewById(R.id.editDateEnd);
                Switch switchEvPrivate = (Switch) findViewById(R.id.switchPrivate);

                boolean completed = true;

                System.out.println("in button");


                if(editName.getText().length()<=0){
                    editName.setError("You must fill this field");
                    completed = false;
                }
                else{
                    editName.setError(null);
                }
                if(editLieu.getText().length()<=0){
                    editLieu.setError("You must fill this field");
                    completed = false;
                }
                else{
                    editLieu.setError(null);
                }

                try {

                    Calendar newDate1 = Calendar.getInstance();
                    newDate1.set(year1,month1,day1,hour1,minute1);

                    Calendar newDate2 = Calendar.getInstance();
                    newDate2.set(year2,month2,day2,hour2,minute2);

                    Date d1 = newDate1.getTime();
                    Date d2 = newDate2.getTime();


                    if(d1.after(d2) && editDateBegin.getText().length()>0 && editDateEnd.getText().length()>0 && editHourBegin.getText().length()>0 && editHourEnd.getText().length()>0){
                        editDateEnd.setText("");
                        editHourEnd.setText("");
                        editHourEnd.setError("Pick a greater date than above");

                        completed = false;
                    }
                    else{
                        if(editDateBegin.getText().length()<=0){
                            editDateBegin.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            editDateBegin.setError(null);
                        }
                        if(editDateEnd.getText().length()<=0){
                            editDateEnd.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            editDateEnd.setError(null);
                        }
                        if(editHourBegin.getText().length()<=0){
                            editHourBegin.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            editHourBegin.setError(null);
                        }
                        if(editHourEnd.getText().length()<=0){
                            editHourEnd.setError("You must fill this field");
                            completed = false;
                        }
                        else{
                            editHourEnd.setError(null);
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("in catch");
                }

                if(completed){
                    eventCreated.setName(editName.getText().toString());
                    eventCreated.setAddress(editLieu.getText().toString());
                    System.out.println(editDateBegin.getText().toString());
                    //eventCreated.setDateDebut(EditDateBegin.getText().toString());
                    //eventCreated.setDateFin(EditDateEnd.getText().toString());
                    eventCreated.setEvPrivate(switchEvPrivate.isChecked());
                }


        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        if(view == editDateBegin) {
            fromDatePickerDialog.show();
        } else if(view == editDateEnd) {
            toDatePickerDialog.show();
        } else if(view == editHourBegin){
            fromHourPickerDialog.show();
        } else if(view == editHourEnd){
            toHourPickerDialog.show();
        } else if(view == switchPrivate) {
            if (switchPrivate.isChecked()) {
                switchPrivate.setTextColor(getResources().getColor(R.color.colorTextField));
            } else {
                switchPrivate.setTextColor(getResources().getColor(R.color.colorHint));
            }
        }else if(view == editLocation){
            System.out.println("here1");
            Intent mapIntent = new Intent(this, MapsActivity.class);
            System.out.println("here2");
            startActivity(mapIntent);
            System.out.println("here3");
        }

    }

}
