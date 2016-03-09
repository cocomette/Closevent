package com.closevent.closevent;

import android.app.DatePickerDialog;
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
    private EditText EditDateBegin;
    private EditText EditDateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        eventCreated = new Event();

        EditDateBegin = (EditText) findViewById(R.id.editDateBegin);
        EditDateEnd = (EditText) findViewById(R.id.editDateEnd);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

        EditDateBegin.setInputType(InputType.TYPE_NULL);
        EditDateEnd.setInputType(InputType.TYPE_NULL);

        EditDateBegin.setOnClickListener(this);
        EditDateEnd.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EditDateBegin.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EditDateEnd.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



        final Button button = (Button) findViewById(R.id.sendButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                try {
                    Date d1 = dateFormatter.parse(EditDateBegin.getText().toString());
                    Date d2 = dateFormatter.parse(EditDateEnd.getText().toString());
                    if(d1.after(d2)){
                        EditDateEnd.setText("");
                        EditDateEnd.setError("Pick a greater date than above");
                        completed = false;
                    }
                    else{
                        EditDateEnd.setError(null);
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
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reduced, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        if(view == EditDateBegin) {
            fromDatePickerDialog.show();
        } else if(view == EditDateEnd) {
            toDatePickerDialog.show();
        }
    }

}
