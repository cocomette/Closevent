package com.closevent.closevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.closevent.closevent.service.Event;

import java.util.Calendar;
import java.util.Date;

public class CodeActivity extends AppCompatActivity {

    private EditText editCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
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
        editCode = (EditText)(findViewById(R.id.eventCode));

        switch (item.getItemId()) {
            case R.id.action_validate:
                // Perform action on click
                //fillForm();
                boolean completed = true;

                if (editCode.getText().length() <= 0) {
                    editCode.setError("You must fill this field");
                    completed = false;
                } else {
                    editCode.setError(null);
                }


                if (completed) {
                    CodeActivity.this.finish();
                }
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
