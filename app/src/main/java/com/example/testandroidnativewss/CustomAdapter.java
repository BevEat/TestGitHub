package com.example.testandroidnativewss;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GestureDetectorCompat;

public class CustomAdapter extends BaseAdapter{

    private float x1,x2;
    static final int MIN_DISTANCE = 20;

    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context=context;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
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
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // ... Respond to touch events
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaX) > MIN_DISTANCE)
                        {
                            // Left to Right swipe action
                            if (x2 > x1) {
                                Toast.makeText(context, "Left to Right swipe [Next]:"+result[position], Toast.LENGTH_SHORT).show();


                                // Right to left swipe action
                            } else
                            {
                                Toast.makeText(context, "Right to Left swipe [Previous]"+result[position], Toast.LENGTH_SHORT).show ();

                            }

                        }
                        else
                        {
                            // consider as something else - a screen tap for example
                        }
                        break;
                }

                return true;
            }
        });
        //rowView.setOnClickListener(new OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
                // TODO Auto-generated method stub
        //        Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
        //    }
        //});
        return rowView;
    }
}
