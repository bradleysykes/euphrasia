package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.spinners.LanguageSpinner;
import com.jbj.euphrasia.spinners.PhrasebookSpinner;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class IntermediateSearchActivity extends Activity implements Constants{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		LanguageSpinner languageChoices = (LanguageSpinner) findViewById(R.id.browse_languages);
		languageChoices.setActivitySource(this);
		PhrasebookSpinner phrasebookChoices = (PhrasebookSpinner) findViewById(R.id.browse_phrasebooks);
		phrasebookChoices.setActivitySource(this);
	}

//	private SimpleCursorAdapter getLanguageAdapter() {
//		Cursor langCursor = getLanguages();
//		String[] froms = {EntryColumns.COLUMN_NAME_LANGUAGE, EntryColumns._ID};
//		int[] tos = {android.R.id.text1};
//		SimpleCursorAdapter languageAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, langCursor,
//                froms, tos, 0);
//		
//		return languageAdapter;
//	}
//	
//	private Cursor getLanguages() {
//		Cursor cursor = getContentResolver().query(EntryProvider.CONTENT_LANGUAGES_URI, null, null, null, null);
//		
//		String[] froms = {EntryColumns.COLUMN_NAME_LANGUAGE, EntryColumns._ID};
//		MatrixCursor extras = new MatrixCursor(froms);
////		String[] extraPhrasebooks = getResources().getStringArray(R.array.test_phrasebooks);
////		for(int i = 1; i <= extraPhrasebooks.length; i++) {
////			extras.addRow(new String[] {extraPhrasebooks[i - 1], String.valueOf(-1*i)});
////		}
//		extras.addRow(new String[] {"Choose language","-1"});
//		
//		Cursor[] cursors = {extras, cursor};
//		
//		return new MergeCursor(cursors);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse, menu);
		return true;
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
