package com.lucabellon.mozzylovetechno;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
//import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lucabellon.mozzylovetechno.R;

public class App extends Activity implements OnClickListener, OnTouchListener,
		OnItemClickListener, OnMenuItemClickListener, OnCompletionListener,
		OnBufferingUpdateListener, OnPreparedListener {

	private ImageButton buttonPlayPause;
	private SeekBar seekBarProgress;

	private MediaPlayer mediaPlayer;
	private int mediaFileLengthInMilliseconds;

	private ListView listView;
	private boolean busy = true;

	private ListItem selectedListItem;
	private ArrayList<ListItem> myItems;

	private final Handler handler = new Handler();

	@Override
	public void onPause() {
		super.onPause();
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying())
				mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
	}

	private void initView() {

		/*
		 * ConnectivityManager conMan = (ConnectivityManager)
		 * getSystemService(Context.CONNECTIVITY_SERVICE); State wifi =
		 * conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		 * 
		 * if(wifi != NetworkInfo.State.CONNECTED) { Toast.makeText(this,
		 * R.string.wifi_required, Toast.LENGTH_LONG).show(); return; }
		 */

		listView = (ListView) findViewById(R.id.ListViewItems);
		listView.setOnItemClickListener(this);

		DataSourceManager ds = new DataSourceManager(
				getString(R.string.xml_repository_url));
		myItems = ds.getItems();

		if (myItems == null || myItems.isEmpty()) {
			Toast.makeText(this, R.string.no_item_found, Toast.LENGTH_LONG)
					.show();
			return;
		}

		ListItemAdapter adapter = new ListItemAdapter(this,
				R.drawable.list_view_row, myItems);
		listView.setAdapter(adapter);

		buttonPlayPause = (ImageButton) findViewById(R.id.ButtonPlayPause);
		buttonPlayPause.setOnClickListener(this);

		seekBarProgress = (SeekBar) findViewById(R.id.SeekBarPlay);
		seekBarProgress.setMax(99);
		seekBarProgress.setOnTouchListener(this);

		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);

		busy = false;
	}

	private void primarySeekBarProgressUpdater() {
		seekBarProgress.setProgress((int) (((float) mediaPlayer
				.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100));
		if (mediaPlayer.isPlaying()) {
			handler.postDelayed(notification, 1000);
		}
	}

	Runnable notification = new Runnable() {
		public void run() {
			primarySeekBarProgressUpdater();
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ButtonPlayPause) {
			if (selectedListItem == null) {
				Toast.makeText(this, R.string.select_item, Toast.LENGTH_LONG)
						.show();
				return;
			}

			if (busy) {
				Toast.makeText(this, R.string.busy, Toast.LENGTH_LONG).show();
				return;
			}

			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
				buttonPlayPause.setBackgroundResource(R.drawable.equalizer);
				AnimationDrawable anim = (AnimationDrawable) buttonPlayPause
						.getBackground();
				anim.start();
				buttonPlayPause
						.setImageResource(android.R.drawable.ic_media_pause);
			} else {
				mediaPlayer.pause();
				buttonPlayPause.setBackgroundDrawable(null);
				buttonPlayPause
						.setImageResource(android.R.drawable.ic_media_play);
			}

			handler.removeCallbacks(notification);
			primarySeekBarProgressUpdater();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.SeekBarPlay) {
			if (mediaPlayer.isPlaying()) {
				SeekBar sb = (SeekBar) v;
				int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100)
						* sb.getProgress();
				mediaPlayer.seekTo(playPositionInMillisecconds);
			}
		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		seekBarProgress.setSecondaryProgress(percent);
	}

	@Override
	public void onItemClick(AdapterView<?> a, View v, int i, long li) {
		if (a.getId() == R.id.ListViewItems) {
			selectedListItem = myItems.get(i);
			buttonPlayPause.setBackgroundDrawable(null);
			buttonPlayPause
					.setImageResource(android.R.drawable.stat_notify_sync);
			try {
				mediaPlayer.reset();
				mediaPlayer.setDataSource(selectedListItem.Value);
				busy = true;
				mediaPlayer.prepareAsync();
			} catch (Exception e) {
				busy = false;
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(R.string.about_title).setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem i) {
		new AlertDialog.Builder(this).setTitle(R.string.about_title)
				.setMessage(R.string.about)
				.setNeutralButton(R.string.close, null).show();
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
		mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
		busy = false;
		Toast.makeText(this, R.string.loaded, Toast.LENGTH_LONG).show();
	}

}