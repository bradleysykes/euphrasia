package com.jbj.euphrasia.activities;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.LogoutManager;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.SyncManager;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.AudioUploadTask;
import com.jbj.euphrasia.remote.ClearRemoteTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;

public class MainActivity extends Activity implements Constants {
	
	private String myUser;
	private Cursor myCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myUser = this.getIntent().getStringExtra(EXTRA_EXISTING_USER);
		//ActionBar actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public void onStartEntry(View view){
		startActivity(new Intent(this, EntryActivity.class));
	}
	
	public void onSyncDatabase(View view){
		SyncManager.setActivity(this);
		SyncManager.sync();
	}

	public void onStartSearch(View view){
		//retrieve all of the languages in the database and pass extra to Intermediate activity
		Intent toIntermediateIntent = new Intent(this,IntermediateSearchActivity.class);
		toIntermediateIntent.setAction(ACTION_SHOW_LANGUAGES);
		startActivity(toIntermediateIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    menu.findItem(R.id.sync).setIcon(R.drawable.sync);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.sync:
	        	SyncManager.setActivity(this);
	        	SyncManager.sync();
	        	return true;
	        case R.id.logout:
	        	LogoutManager.setActivity(this);
	        	LogoutManager.logout();
	        	return true;
	        case R.id.about:
	        	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://goeuphrasia.com"));
	        	startActivity(browserIntent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}


	public void onBrowseRemote(View view){
		startActivity(new Intent(this,RemoteSearchActivity.class));
	}
	

}
