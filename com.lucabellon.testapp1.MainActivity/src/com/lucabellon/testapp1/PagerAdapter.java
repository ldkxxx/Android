package com.lucabellon.testapp1;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments;	
	public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int pos) {
		return this.fragments.get(pos);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "View " + position;
	}
	
	@Override
	public int getCount() {
		return this.fragments.size();
	}

}
