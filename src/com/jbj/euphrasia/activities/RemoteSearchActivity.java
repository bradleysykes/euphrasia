package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.SyncManager;
import com.jbj.euphrasia.R.id;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.ReadRemoteTask;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RemoteSearchActivity extends Activity implements Constants{
	
	private int myParamIndex;
	private AbstractRemoteTask myTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remote_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.remote_search, menu);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onRadioButtonClicked(View view){
		RadioGroup radioGroup = (RadioGroup)findViewById(R.id.browse_radio_group);
		if(radioGroup.getCheckedRadioButtonId()!=-1){
		    int id= radioGroup.getCheckedRadioButtonId();
		    View radioButton = radioGroup.findViewById(id);
		    int radioId = radioGroup.indexOfChild(radioButton);
		    
		    myParamIndex = radioId;
		}
	}
	
	public void doBrowse(View view){
		AbstractRemoteTask readRemote = new ReadRemoteTask();
		myTask = readRemote;
		readRemote.setActivity(this);
		EditText text = (EditText)findViewById(R.id.browse_filter_param);
		String[] filterIndex = new String[]{"filter_index",String.valueOf(myParamIndex)};
		String[] filterParam = new String[]{"field",text.getText().toString()};
		String[][] params = new String[][]{filterIndex,filterParam};
		readRemote.execute(params);
	}
	
	public void doBrowseAll(View view){
		AbstractRemoteTask readRemote = new ReadRemoteTask();
		myTask = readRemote;
		readRemote.setActivity(this);
		EditText text = (EditText)findViewById(R.id.browse_filter_param);
		String[] filterIndex = new String[]{"filter_index",""+3};
		//String[] filterParam = new String[]{"field",text.getText().toString()};
		String[][] params = new String[][]{filterIndex};
		readRemote.execute(params);
	}

	public void acceptResult(Bundle bundle) {
		// send bundle to search activity
		Intent displayResults = new Intent(this,SearchActivity.class);
		displayResults.putExtra(EXTRA_REMOTE_BUNDLE, bundle);
		displayResults.setAction(ACTION_REMOTE_QUERY);
		startActivity(displayResults);
	}

}
