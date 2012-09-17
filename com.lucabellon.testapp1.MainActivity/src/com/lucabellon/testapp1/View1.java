package com.lucabellon.testapp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class View1 extends ListFragment implements OnItemClickListener {

	List<Map<String, Object>> mItems;
	SimpleAdapter mAdapter;
	ListView mListview;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.mItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < 100; i++) {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("title", "Item " + i);
			temp.put("value", Integer.toString(i));
			this.mItems.add(temp);
		}

		this.mAdapter = new SimpleAdapter(getActivity(), this.mItems,
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 });

		setListAdapter(this.mAdapter);

		this.mListview = getListView();
		this.mListview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Map<String, Object> map = this.mItems.get(arg2);
		String value = (String) map.get("value");		
		Intent intent = new Intent(getActivity(), ListDetails.class);
		Bundle b = new Bundle();
		b.putString("key", value); //Your id
		intent.putExtras(b); //Put your id to your next Intent
		startActivity(intent);		
	}
}
