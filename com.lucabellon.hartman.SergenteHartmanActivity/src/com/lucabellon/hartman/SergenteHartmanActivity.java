package com.lucabellon.hartman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lucabellon.hartman.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SergenteHartmanActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener, OnClickListener,
		OnMenuItemClickListener {

	private static final String[] ITEMS = new String[] {
			"Io sono il sergente maggiore Hartman!",
			"Come ti chiami faccia di merda?", "Chi ha parlato?",
			"Abbiamo tra noi un attore comico..", "Si si tu mi piaci!",
			"Brutto sacco di merda, io ti metto sotto!",
			"Tu non riderai, tu non piangerai...",
			"Datti subito una regolata amico mio...",
			"Io dico che la parte migliore dello schizzo...",
			"Io ho sempre saputo che nel texas...", "Tu succhi i cazzi?",
			"Scommetto che sei uno di quegli ingrati...",
			"I tuoi genitori hanno anche figli normali?",
			"Ho deciso di darti tre secondi...",
			"E' meglio che metti il culo in carreggiata...",
			"I bei tempi dei ditalini...", "Sei proprio tu John Wayne?" };

	private MediaPlayer player = null;
	Context context;
	private boolean isLongPressed = false;
	int ringtone;

	@Override
	public void onPause() {
		super.onPause();
		try {
			if (player != null) {
				if (player.isPlaying())
					player.stop();
				player.release();
			}
			player = null;
		} catch (Exception e) {
			player = null;
			e.printStackTrace();
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;

		Display getOrient = getWindowManager().getDefaultDisplay();
		int orientation = getOrient.getOrientation();
		View mainLayout = findViewById(R.id.layout);
		if (orientation == 1)
			mainLayout.setBackgroundResource(R.raw.landscape);
		else
			mainLayout.setBackgroundResource(R.raw.portrait);

		ListView l = (ListView) findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, ITEMS);
		l.setAdapter(adapter);
		l.setOnItemClickListener(this);
		l.setOnItemLongClickListener(this);
	}

	private void Play(int resId) {
		try {
			if (player != null) {
				if (player.isPlaying())
					player.stop();
				player.release();
			}
			player = MediaPlayer.create(context, resId);
			player.start();
		} catch (Exception e) {
			player = null;
			e.printStackTrace();
		}
	}

	private void SetRingtone(int resId, int type) {

		File sdcard = Environment.getExternalStorageDirectory();

		File dir = new File(sdcard.getAbsolutePath() + "/media/ringtone");
		dir.mkdirs();

		File newSoundFile = new File(dir, "hartman_" + type + ".mp3");

		Uri mUri = Uri.parse("android.resource://com.lucabellon.hartman/"
				+ resId);

		ContentResolver mCr = context.getContentResolver();
		AssetFileDescriptor soundFile;

		try {
			soundFile = mCr.openAssetFileDescriptor(mUri, "r");
		} catch (FileNotFoundException e) {
			soundFile = null;
			return;
		}

		try {
			byte[] readData = new byte[1024];
			FileInputStream fis = soundFile.createInputStream();
			FileOutputStream fos = new FileOutputStream(newSoundFile);
			int i = fis.read(readData);

			while (i != -1) {
				fos.write(readData, 0, i);
				i = fis.read(readData);
			}

			fos.close();
		} catch (IOException io) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, newSoundFile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, "Sergente Hartman");
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
		values.put(MediaStore.MediaColumns.SIZE, newSoundFile.length());
		values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		values.put(MediaStore.Audio.Media.IS_ALARM, true);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(newSoundFile
				.getAbsolutePath());

		Uri newUri = mCr.insert(uri, values);

		try {
			RingtoneManager.setActualDefaultRingtoneUri(getBaseContext(), type,
					newUri);						
			
			Toast.makeText(context, "Suoneria impostata", Toast.LENGTH_SHORT)
					.show();

		} catch (Throwable t) {
		}
	}

	private int GetResByPos(int position) {
		switch (position) {
		case 0:
			return (R.raw.s1);
		case 1:
			return (R.raw.s2);
		case 2:
			return (R.raw.s3);
		case 3:
			return (R.raw.s5);
		case 4:
			return (R.raw.s6);
		case 5:
			return (R.raw.s7);
		case 6:
			return (R.raw.s8);
		case 7:
			return (R.raw.s9);
		case 8:
			return (R.raw.s10);
		case 9:
			return (R.raw.s11);
		case 10:
			return (R.raw.s12);
		case 11:
			return (R.raw.s13);
		case 12:
			return (R.raw.s14);
		case 13:
			return (R.raw.s16);
		case 14:
			return (R.raw.s17);
		case 15:
			return (R.raw.s18);
		case 16:
			return (R.raw.s15);
		}

		return (R.raw.s1);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.getId() == R.id.listView) {

			if (isLongPressed) {
				isLongPressed = false;
				return;
			}

			Play(GetResByPos(position));
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		if (parent.getId() == R.id.listView) {

			isLongPressed = true;

			ringtone = GetResByPos(position);

			final CharSequence[] items = { "Imposta suoneria telefono",
					"Imposta suoneria notifica" };

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Opzioni");
			builder.setItems(items, this);
			builder.create();
			builder.show();
		}

		return false;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == 0)
			SetRingtone(ringtone, RingtoneManager.TYPE_RINGTONE);
		else if (which == 1)
			SetRingtone(ringtone, RingtoneManager.TYPE_NOTIFICATION);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Info").setIcon(android.R.drawable.ic_dialog_info).setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem i) {
		if (i.getTitle() == "Info") {			
			String info = "Grazie per aver scaricato l'app del Sergente Hartman!\r\n\r\nPer impostare una frase come suoneria, premi più a lungo sulla frase interessata.\r\n\r\nPuoi inoltre suggerire l'inserimento di altre frasi inviando un' email allo sviluppatore.";
			new AlertDialog.Builder(this).setTitle("Informazioni").setMessage(info)
					.setNeutralButton("Chiudi", null).show();
		}
		return false;
	}
}