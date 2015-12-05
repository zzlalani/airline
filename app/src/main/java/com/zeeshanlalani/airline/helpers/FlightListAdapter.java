package com.zeeshanlalani.airline.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zeeshanlalani.airline.R;
import com.zeeshanlalani.airline.ViewFlightActivity;
import com.zeeshanlalani.airline.ViewFlightListActivity;
import com.zeeshanlalani.airline.models.Flight;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zzlal on 12/3/2015.
 * Ref: https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
 */
public class FlightListAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    List<Flight> flights;
    String seatType;
    JSONObject searchObj;

    public FlightListAdapter(ViewFlightListActivity flightActivity, List<Flight> _flights, JSONObject _searchObj) {
        // TODO Auto-generated constructor stub
        context = flightActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        flights = _flights;
        searchObj = _searchObj;

        try {
            seatType = searchObj.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return flights.size();
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
        final Flight f = flights.get(position);

        View rowView = inflater.inflate(R.layout.list_view_flight_list, null);

        holder.input_name = (TextView) rowView.findViewById(R.id.input_name);
        holder.input_depart = (TextView) rowView.findViewById(R.id.input_depart);
        holder.input_arrive = (TextView) rowView.findViewById(R.id.input_arrive);
        holder.input_price = (TextView) rowView.findViewById(R.id.input_price);

        holder.input_name.setText(f.getFullName());
        holder.input_depart.setText(f.getTimeDepart());
        holder.input_arrive.setText(f.getTimeArrive());
        String price = "0";
        Log.d("seatType", seatType);
        if ( seatType == "First Class" ) {
            price = f.getPricefc();
        } else if ( seatType == "Business Class" ) {
            price = f.getPricebc();
        } else {
            price = f.getPriceec();
        }
        holder.input_price.setText("Rs." + price);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent flightsIntent = new Intent(context, ViewFlightActivity.class);
                flightsIntent.putExtra("id", f.getId());
                flightsIntent.putExtra("search", searchObj.toString());

                context.startActivity(flightsIntent);
            }
        });
        return rowView;
    }

}