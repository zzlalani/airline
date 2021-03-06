package com.zeeshanlalani.airline;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ViewBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.activity_view_booking), iconFont);

        Button btn_book = (Button) findViewById(R.id.btn_book);
        btn_book.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent bookingListIntent = new Intent(ViewBookingActivity.this, ViewBookingListActivity.class);
                ViewBookingActivity.this.startActivity(bookingListIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_bookings:
                // action
                break;
            case R.id.action_search:
                // action
                break;
            case R.id.action_profile:
                // action
                break;
            case R.id.action_logout:
                // action
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
