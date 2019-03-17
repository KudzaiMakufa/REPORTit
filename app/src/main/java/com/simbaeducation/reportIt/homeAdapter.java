package com.simbaeducation.reportIt;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class homeAdapter extends ArrayAdapter<LeaveTaken> {
    private ArrayList<LeaveTaken> leaveList;
    private final Context context;
    private SparseBooleanArray mSelectedItemsIds;
    ViewHolder holder;

    public homeAdapter(Context context, int textViewResourceId,
                       ArrayList<LeaveTaken> leaveList) {
        super(context, textViewResourceId, leaveList);
        this.leaveList = new ArrayList<LeaveTaken>();
        mSelectedItemsIds = new SparseBooleanArray();
        this.leaveList.addAll(leaveList);
        this.context = context;
        holder= new ViewHolder();
    }

    public void refresh(ArrayList<LeaveTaken> leaveList)
    {
        this.leaveList = leaveList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public RelativeLayout lay1;
        public TextView id;
        public TextView title;
        public TextView subtitle;
        public TextView desc;
        public TextView amount;
        public int position;
    }


        @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeaveTaken leavetaken= null;
            View row = convertView;
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = mInflater.inflate(R.layout.reportlist_view, null);
                holder.lay1 = (RelativeLayout) row.findViewById(R.id.layoutroot);
                holder.id = (TextView) row.findViewById(R.id.Id);
                holder.title = (TextView) row.findViewById(R.id.leavetype);
                holder.subtitle = (TextView) row.findViewById(R.id.datetaken);
                holder.desc = (TextView) row.findViewById(R.id.notes);
                holder.amount = (TextView) row.findViewById(R.id.duration);
                holder.position= position;
                row.setTag(holder);

            //holder = (ViewHolder) convertView.getTag();
        leavetaken = leaveList.get(position);
        String type = leavetaken.getType();
        if(type.equals("Medical leave")){
            holder.subtitle.setText("Medical Leave");
        }else if(type.equals("Vacational Leave")){
            holder.subtitle.setText("Vacation Leave");
        }else if(type.equals("Maternity Leave")){
            holder.subtitle.setText("Maternity Leave");
        }else{
            holder.subtitle.setText("Other Leave");
        }
        holder.id.setText(String.valueOf(leavetaken.getId()));
        Date date=null;
        SimpleDateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
             date = formatter.parse(leavetaken.getDate());
        }catch (ParseException e){

        }
        //String datetoshow= format.format(date);
        holder.title.setText(leavetaken.getDate());
            holder.desc.setText(leavetaken.getNotes());
        if(leavetaken.getDaytype().equals("HD-PM")){
            holder.amount.setText("Half Day(PM)");
        }else if(leavetaken.getDaytype().equals("HD-AM")){
            holder.amount.setText("Half Day(AM)");
        }else{
            holder.amount.setText("Whole Day");
        }
        if( mSelectedItemsIds.get(holder.position) ==true){
            holder.lay1.setBackgroundResource(R.drawable.menuhigh);
        }

        return row;
    }



    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }


    @Override
    public int getCount()
    {
        return leaveList.size();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
