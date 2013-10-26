package com.jbj.euphrasia;

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

public class EntryDatabaseManager {
	
	private Field myForeignText;
	private Field myNativeText;
	private Field myAudioField;
	private Field myDateField;
	private Field myTagField;
	private Field myTitleField;
	private Context myContext;
	
	//TODO add the remaining fields
	
	private EntryDatabaseHelper myDatabaseHelper;
	
	/**
	 * @param context is the current context, found by calling getContext()
	 */
	public EntryDatabaseManager(Context context){
		/*
		 * Set up a new object
		 * (You gotta set 'em up kid
		 * set 'em up)
		 */
		
		myContext = context;
		
		myDatabaseHelper = new EntryDatabaseHelper(context);
		myForeignText = new NullField();
		myNativeText = new NullField();
		myAudioField = new NullField();
		myDateField = new NullField();
		myTagField = new NullField();
		myTitleField = new NullField();
	}
	
	public void saveEntry() {
		/*
		 * Write current entry to database
		 */
		
		//TODO add a new database entry
		
		int id = 1; 
		//TODO change this id!!
		
		ContentValues values = new ContentValues();
		values.put(EntryColumns.COLUMN_NAME_ENTRY_ID, id);
		values.put(EntryColumns.COLUMN_NAME_TITLE, myTitleField.toString());
		values.put(EntryColumns.COLUMN_NAME_NATIVE_TEXT, myNativeText.toString());
		values.put(EntryColumns.COLUMN_NAME_FOREIGN_TEXT, myForeignText.toString());
		values.put(EntryColumns.COLUMN_NAME_AUDIO, myAudioField.toString());
		values.put(EntryColumns.COLUMN_NAME_TAG, myTagField.toString());
		values.put(EntryColumns.COLUMN_NAME_DATE, myDateField.toString());
		
		Intent intent = new Intent(myContext, DatabaseWriteIntentService.class);
		intent.putExtra("values", values);
	}

	public Field getNativeText() {
		return myNativeText;
	}
	
	public Field getForeignText() {
		return myForeignText;
	}
	
	public Field getAudioField() {
		return myAudioField;
	}

	public void setNativeText(Field data) {
		myNativeText = data;
	}

	public void setForeignText(Field field) {
		/*
		 * Accessed by ForeignTextField. 
		 * TODO - add logic to make sure change is valid
		 */
		myForeignText = field;
	}

	public void setAudioField(AudioField audioField) {
		myAudioField = audioField;
		
	}

}
