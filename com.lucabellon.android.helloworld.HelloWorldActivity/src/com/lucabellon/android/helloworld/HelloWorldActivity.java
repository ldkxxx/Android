package com.lucabellon.android.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HelloWorldActivity extends Activity {
	/** Called when the activity is first created. */

	Button button1;
	Toast toast1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setUpViews();

		toast1 = Toast.makeText(this, "Questo è un toast", Toast.LENGTH_LONG);

		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				toast1.show();
			}
		});
	}

	private void setUpViews() {
		button1 = (Button) findViewById(R.id.button1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Comando1");
		menu.add("Comando2");
		menu.add("Comando3");
		SubMenu submenu = menu.addSubMenu("Altri comandi");
		submenu.add("Comando4");
		submenu.add("Comando5");
		submenu.add("Comando6");
		return true;
	}

}