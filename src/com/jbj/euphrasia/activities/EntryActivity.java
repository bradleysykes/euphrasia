package com.jbj.euphrasia.activities;

import java.util.HashMap;
import java.util.Map;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.id;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.fields.AudioField;
import com.jbj.euphrasia.fields.DateField;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.fields.FieldFactory;
import com.jbj.euphrasia.fields.TitleField;
import com.jbj.euphrasia.interfaces.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class EntryActivity extends Activity implements Constants, EntryContract {
	
	private FieldFactory myFieldFactory;
	private Controller myController;
	private Map<String,EditText> myTextViews = new HashMap<String,EditText>();
	private ContentValues myInitialData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		if(Constants.ACTION_GET_ENTRY_DATA.equals(getIntent().getAction())) {
			myInitialData = processIntent();
		}
		myFieldFactory = new FieldFactory();
		myController = new Controller(this);
		findTextViews();
		setUpTextViews();
		loadInitialData();
	}
	
	private void loadInitialData() {
		if(myInitialData != null) {
			String audioPath = myInitialData.getAsString(EntryColumns.COLUMN_NAME_AUDIO);
			myController.updateEntryField(new AudioField(audioPath));
			String title = myInitialData.getAsString(EntryColumns.COLUMN_NAME_TITLE);
			myController.updateEntryField(new TitleField(title));
			String date = myInitialData.getAsString(EntryColumns.COLUMN_NAME_DATE);
			myController.updateEntryField(new DateField(date));
		}
	}

	/**
	 * adds listeners and checks for initial data
	 */
	private void setUpTextViews() {
		for(String key:myTextViews.keySet()){
			EditText textView = myTextViews.get(key);
			textView.setOnFocusChangeListener(new OnFocusChangeListener(){
				@Override
			    public void onFocusChange(View view, boolean isFocused) {
			        if (!isFocused) {
			        	Log.d("TAG","forein_focus_change");
			            updateField(view);
			        }
			    }
			});
			if (myInitialData != null && myInitialData.containsKey(key)) {
				String initialValue = myInitialData.getAsString(key);
				textView.setText(initialValue);
			}
		}
	}

	/**
	 * stores all view items in a map to reference by database column names
	 */
	private void findTextViews() {
		EditText nativeText = (EditText) findViewById(R.id.native_text);
		EditText foreignText = (EditText) findViewById(R.id.foreign_text);
		EditText tagText = (EditText) findViewById(R.id.edit_tags);
		myTextViews.put(EntryColumns.COLUMN_NAME_NATIVE_TEXT,nativeText);
		myTextViews.put(EntryColumns.COLUMN_NAME_FOREIGN_TEXT,foreignText);
		myTextViews.put(EntryColumns.COLUMN_NAME_TAG,tagText);
	}

	private ContentValues processIntent() {
		Intent intent = this.getIntent();
		return (ContentValues)intent.getParcelableExtra(ENTRY_INTENT_PARCELABLE);
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}
	
/** get appropriate EditText object
 * Call this method whenever an EditText component losesFocus
 */
	public void updateField(View view){
		if(!view.hasFocus()){
			//TODO is it too inefficient to create a new field object every time?
			EditText editText = (EditText) view;
			Field field = myFieldFactory.createField(view.getId(), editText.getText().toString());
			myController.updateEntryField(field);
			Log.i("new field",field.toString() + field.getClass().getName());
		}
	}
	
/**
 * @author Bradley
 * To record, user presses a record button. Toggles boolean value to indicate whether recording 
 * should begin.
 * When toggled off, presented option to save, playback, or record again. 
 */
	public void handleRecording(View view){
		myController.onRecord();
	}
	
	public void handlePlay(View view){
		myController.onPlay();
	}
	
	public void handleSave(View view){
		myController.onSave();
	}
}


