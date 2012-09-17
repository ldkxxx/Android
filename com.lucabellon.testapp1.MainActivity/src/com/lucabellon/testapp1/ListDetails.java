package com.lucabellon.testapp1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ListDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle b = getIntent().getExtras();
		String value = b.getString("key");
		
		setContentView(R.layout.list_details);
		
		TextView text = (TextView) findViewById(R.id.detail_text);
		text.setText("The key value is: " + value);
	}
}
