package com.jbj.euphrasia.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.ClearRemoteTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;

public class MainActivity extends ActionBarActivity implements Constants {
	
	private String myUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myUser = this.getIntent().getStringExtra(EXTRA_EXISTING_USER);
		ActionBar actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public void onStartEntry(View view){
		startActivity(new Intent(this, EntryActivity.class));
	}
	
	public void onSyncDatabase(View view){
		Toast.makeText(this, "Attempting to sync to remote", Toast.LENGTH_LONG).show();
//		AbstractRemoteTask clear = new ClearRemoteTask();
//		clear.setActivity(this);
//		clear.execute(new Object[]{"user", myUser});
//		AbstractRemoteTask write = new WriteRemoteTask();
//		write.setActivity(this);
//		Cursor cursor = getContentResolver().query(EntryProvider.CONTENT_URI, SELECT_ALL_PROJECTION, null,null,null);
//		int j;
//		while(cursor.moveToNext()){
//			j = 0;
//			String[][] params = new String[][]{}; 
//			for(int i=0;i<cursor.getColumnCount();i++){
//				String[] param = new String[]{};
//				param[0] = cursor.getColumnName(i);
//				param[1] = cursor.getString(i);
//				params[j] = param;
//				j++;
//			}
//			//write each entry to the remote database before continuing
//			write.execute(params);
		AbstractRemoteTask testTask = new WriteRemoteTask();
		String[][] stuff = new String[][]{};
		for(int i = 0; i<SELECT_ALL_PROJECTION.length;i++){
			if(i==2){
				String[] nestedStuff = {SELECT_ALL_PROJECTION[i],"2002-09-22 11:11:11"};
				stuff[i] = nestedStuff;
			}
			else{
				String[] nestedStuff = {SELECT_ALL_PROJECTION[i],"blah"};
				stuff[i] = nestedStuff;
			}
		}
		testTask.execute(stuff);
	}
	
	public void onStartSearch(View view){
		//retrieve all of the languages in the database and pass extra to Intermediate activity
		Intent toIntermediateIntent = new Intent(this,IntermediateSearchActivity.class);
		toIntermediateIntent.setAction(ACTION_SHOW_LANGUAGES);
		startActivity(toIntermediateIntent);
	}
	

}
