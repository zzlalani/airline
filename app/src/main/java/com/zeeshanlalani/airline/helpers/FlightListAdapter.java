package com.zeeshanlalani.airline.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zeeshanlalani.airline.R;
import com.zeeshanlalani.airline.ViewFlightActivity;
import com.zeeshanlalani.airline.ViewFlightListActivity;

/**
 * Created by zzlal on 12/3/2015.
 * Ref: https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
 */
public class FlightListAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;

    public FlightListAdapter(ViewFlightListActivity flightActivity) {
        // TODO Auto-generated constructor stub
        context = flightActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView input_name;
        TextView input_depart;
        TextView input_arrive;
        TextView input_price;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView = inflater.inflate(R.layout.list_view_flight_list, null);

        holder.input_name = (TextView) rowView.findViewById(R.id.input_name);
        holder.input_depart = (TextView) rowView.findViewById(R.id.input_depart);
        holder.input_arrive = (TextView) rowView.findViewById(R.id.input_arrive);
        holder.input_price = (TextView) rowView.findViewById(R.id.input_price);

        holder.input_name.setText("Virgin America");
        holder.input_depart.setText("6:10 PM");
        holder.input_arrive.setText("7:40 PM");
        holder.input_price.setText("$58");

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + (position + 1), Toast.LENGTH_LONG).show();
                Intent flightsIntent = new Intent(context, ViewFlightActivity.class);
                context.startActivity(flightsIntent);
            }
        });
        return rowView;
    }

}