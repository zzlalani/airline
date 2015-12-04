package com.zeeshanlalani.airline;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zzlal on 12/3/2015.
 * Ref: https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
 */
public class BookingListAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;

    public BookingListAdapter(ViewBookingListActivity bookingListActivity) {
        // TODO Auto-generated constructor stub
        context = bookingListActivity;
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
        TextView input_destination;
        TextView input_source;
        TextView input_date;
        TextView input_price;
        TextView input_type;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView = inflater.inflate(R.layout.list_view_booking_list, null);

        holder.input_destination = (TextView) rowView.findViewById(R.id.input_destination);
        holder.input_source = (TextView) rowView.findViewById(R.id.input_source);
        holder.input_date = (TextView) rowView.findViewById(R.id.input_date);
        holder.input_price = (TextView) rowView.findViewById(R.id.input_price);
        holder.input_type = (TextView) rowView.findViewById(R.id.input_type);

        holder.input_destination.setText("BOS");
        holder.input_source.setText("SFO");
        holder.input_date.setText("May, 23 15");
        holder.input_price.setText("$100");
        holder.input_type.setText("One way");

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + (position + 1), Toast.LENGTH_LONG).show();
                Intent bookingListIntent = new Intent(context, ViewBookingActivity.class);
                context.startActivity(bookingListIntent);
            }
        });
        return rowView;
    }

}