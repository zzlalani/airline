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
import com.zeeshanlalani.airline.ViewBookingActivity;
import com.zeeshanlalani.airline.ViewBookingListActivity;
import com.zeeshanlalani.airline.models.Booking;

import java.util.List;

/**
 * Created by zzlal on 12/3/2015.
 * Ref: https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
 */
public class BookingListAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    List<Booking> bookingList;

    public BookingListAdapter(ViewBookingListActivity bookingListActivity, List<Booking> bookingList) {
        // TODO Auto-generated constructor stub
        context = bookingListActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bookingList = bookingList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.bookingList.size();
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

        Booking b = bookingList.get(position);

        holder.input_destination = (TextView) rowView.findViewById(R.id.input_destination);
        holder.input_source = (TextView) rowView.findViewById(R.id.input_source);
        holder.input_date = (TextView) rowView.findViewById(R.id.input_date);
        holder.input_price = (TextView) rowView.findViewById(R.id.input_price);
        holder.input_type = (TextView) rowView.findViewById(R.id.input_type);

        holder.input_destination.setText(b.getFlight().getFrom().getCode());
        holder.input_source.setText(b.getFlight().getTo().getCode());
        holder.input_date.setText(b.getBookingDate());

        String priceText = "";
        if (b.getType() == "First Class") {
            priceText = b.getFlight().getPricefc();
        } else if (b.getType() == "Business Class") {
            priceText = b.getFlight().getPricebc();
        } else {
            priceText = b.getFlight().getPriceec();
        }

        holder.input_price.setText(priceText);
        holder.input_type.setText(b.getType());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent bookingListIntent = new Intent(context, ViewBookingActivity.class);
                context.startActivity(bookingListIntent);
            }
        });
        return rowView;
    }

}