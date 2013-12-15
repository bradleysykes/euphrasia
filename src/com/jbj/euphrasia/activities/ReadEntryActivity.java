package com.jbj.euphrasia.activities;

import java.util.HashMap;
import java.util.Map;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.fields.AudioField;
import com.jbj.euphrasia.fields.DateField;
import com.jbj.euphrasia.fields.FieldFactory;
import com.jbj.euphrasia.fields.ForeignTextField;
import com.jbj.euphrasia.fields.LanguageField;
import com.jbj.euphrasia.fields.NativeTextField;
import com.jbj.euphrasia.fields.TagField;
import com.jbj.euphrasia.fields.TitleField;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.spinners.LanguageSpinner;
import com.jbj.euphrasia.spinners.PhrasebookSpinner;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReadEntryActivity extends Activity implements Constants{
	
	private ContentValues myInitialData;
	private Map<String,TextView> myTextViews = new HashMap<String,TextView>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		if(Constants.ACTION_GET_ENTRY_DATA.equals(getIntent().getAction())) {
			myInitialData = processIntent();
		}
		findTextViews();
		setUpTextViews();
		loadInitialData();
		
	}

	private void findTextViews() {
		TextView titleView = (TextView)findViewById(R.id.entry_title_read);
		TextView nativeView = (TextView)findViewById(R.id.entry_native_read);
		TextView foreignView = (TextView)findViewById(R.id.entry_foreign_read);
		TextView tagView = (TextView)findViewById(R.id.entry_tags_read);
		TextView phrasebookView = (TextView)findViewById(R.id.entry_phrasebook_read);
		TextView languageView = (TextView)findViewById(R.id.entry_language_read);
		myTextViews.put(EntryColumns.COLUMN_NAME_NATIVE_TEXT,nativeView);
		myTextViews.put(EntryColumns.COLUMN_NAME_FOREIGN_TEXT,foreignView);
		myTextViews.put(EntryColumns.COLUMN_NAME_TAG,tagView);
		myTextViews.put(EntryColumns.COLUMN_NAME_TITLE, titleView);
		myTextViews.put(EntryColumns.COLUMN_NAME_LANGUAGE,languageView);
		myTextViews.put(EntryColumns.COLUMN_NAME_PHRASEBOOK,phrasebookView);
	}

	private void loadInitialData() {
		if(myInitialData != null) {
			String audioPath = myInitialData.getAsString(EntryColumns.COLUMN_NAME_AUDIO);
			String title = myInitialData.getAsString(EntryColumns.COLUMN_NAME_TITLE);
			String date = myInitialData.getAsString(EntryColumns.COLUMN_NAME_DATE);
			String language = myInitialData.getAsString(EntryColumns.COLUMN_NAME_LANGUAGE);
			String nativeText = myInitialData.getAsString(EntryColumns.COLUMN_NAME_NATIVE_TEXT);
			String foreignText = myInitialData.getAsString(EntryColumns.COLUMN_NAME_FOREIGN_TEXT);
			String tagText = myInitialData.getAsString(EntryColumns.COLUMN_NAME_TAG);
//			myController.setUri(Uri.withAppendedPath(EntryProvider.CONTENT_URI, 
//					String.valueOf(myInitialData.getAsLong("URI_id"))));
			
		}
		else{
			((Button)findViewById(R.id.entry_play_btn)).setEnabled(false);
		}
		
	}
	
	private void setUpTextViews() {
		for(String key:myTextViews.keySet()){
			TextView textView = myTextViews.get(key);
			if (myInitialData != null && myInitialData.containsKey(key)) {
				String initialValue = myInitialData.getAsString(key);
				textView.setText(initialValue);
			}
		}
	}

	private ContentValues processIntent() {
		Intent intent = this.getIntent();
		return (ContentValues)intent.getParcelableExtra(ENTRY_INTENT_PARCELABLE);
	}


}
