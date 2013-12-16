package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.LogoutManager;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.SyncManager;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.spinners.LanguageSpinner;
import com.jbj.euphrasia.spinners.PhrasebookSpinner;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class IntermediateSearchActivity extends Activity implements Constants{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intermediate_search);
		Log.i("BBBB","Making language Spinner");
		LanguageSpinner languageChoices = (LanguageSpinner) findViewById(R.id.browse_languages);
		languageChoices.setActivitySource(this);
		Log.i("CCCC","MAKING PHRASEBOOK CHOICES");
		PhrasebookSpinner phrasebookChoices = (PhrasebookSpinner) findViewById(R.id.browse_phrasebooks);
		phrasebookChoices.setActivitySource(this);
		Log.i("EEEE","WHAT IS GOING ON");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.browse, menu);
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
	
	/**
	 * launches listview of every phrasebook in the database. 
	 */
	public void onFilter(String selected, String action, String columnKey){
		// retrieve all phrasebooks from the database and pass extra to next activity. 
		Intent intent = new Intent(this,SearchActivity.class);
		intent.setAction(action);
		intent.putExtra(columnKey, selected);
		startActivity(intent);
	}
	
	/**
	 * Launches listview of every entry in the database. 
	 */
	public void onNoFilter(View view){
		Intent viewAllIntent = new Intent(this,SearchActivity.class);
		viewAllIntent.setAction(ACTION_VIEW_ALL);
		startActivity(new Intent(this,SearchActivity.class));
	}

}
