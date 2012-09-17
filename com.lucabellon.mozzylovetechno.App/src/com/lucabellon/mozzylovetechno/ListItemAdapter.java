package com.lucabellon.mozzylovetechno;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<ListItem> {

	private Context context;
	private ArrayList<ListItem> dataItems;
	
	public ListItemAdapter(Context context, int resource, ArrayList<ListItem> objects) {
		super(context, resource, objects);
		this.context = context;
		this.dataItems = objects;		
	}
	
	@Override	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);            
            view = vi.inflate(R.drawable.list_view_row, null);            
            
            ListItem item = dataItems.get(position);
            
            if(item != null)
            {
            	TextView text = (TextView) view.findViewById(R.id.text);
            	text.setText(item.Text);            	
            }
        }
		
		return view;
	}
}