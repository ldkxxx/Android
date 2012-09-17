package com.lucabellon.dadi;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

public class DadiActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ShakeListener MyShake = new ShakeListener(
				(SensorManager) getApplicationContext().getSystemService(
						Context.SENSOR_SERVICE));

		MyShake.setForceThreshHold(1.9);
		MyShake.setOnShakeListener(new ShakeListener.OnShakeListener() {
			@Override
			public void onShake() {

				ImageView dado1 = (ImageView) findViewById(R.id.imageView1);
				ImageView dado2 = (ImageView) findViewById(R.id.imageView2);

				dado1.setImageResource(Lancia());
				dado2.setImageResource(Lancia());
			}
		});

	}

	int Lancia() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(5);
		int val = randomInt + 1;

		switch (val) {
		case 1:
			return R.drawable.dado_1;
		case 2:
			return R.drawable.dado_2;
		case 3:
			return R.drawable.dado_3;
		case 4:
			return R.drawable.dado_4;
		case 5:
			return R.drawable.dado_5;
		case 6:
			return R.drawable.dado_6;
		default:
			return R.drawable.dado_1;

		}
	}
}