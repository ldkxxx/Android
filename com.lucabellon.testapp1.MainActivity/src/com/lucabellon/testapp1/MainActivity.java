package com.lucabellon.testapp1;

import java.util.List;
import java.util.Vector;

import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	ViewPager mPager;
	TitlePageIndicator mIndicator;
	PagerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, View1.class.getName()));
		fragments.add(Fragment.instantiate(this, View2.class.getName()));
		mAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
