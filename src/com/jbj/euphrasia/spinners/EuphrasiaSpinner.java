package com.jbj.euphrasia.spinners;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.activities.EntryActivity;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.fields.PhrasebookField;

import dialog_fragments.CreatePhrasebookDialog;
import dialog_fragments.EntryDialogFragment;

import android.app.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public abstract class EuphrasiaSpinner extends Spinner {
	
	protected Activity mySourceActivity;
	protected SimpleCursorAdapter myAdapter;
	private AdapterView mySpinnerParent;
	protected int mySize;
	protected boolean canCreateItems = false;

	public EuphrasiaSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnItemSelectedListener(this.getOnItemSelectedListener());
		mySize = this.getCount();
	}
	
	public void setActivitySource(Activity source){
		mySourceActivity = source;
		if(source instanceof EntryActivity){
			canCreateItems = true;
		}
		this.initialize();
	}
	
	protected void initialize(){
		String[] froms = this.getFroms();
		int[] tos = this.getTos();
		myAdapter = new SimpleCursorAdapter(mySourceActivity, android.R.layout.simple_spinner_item, 
				this.getCursor(null), froms, tos, 0);
		myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(myAdapter);
	}
	
	public void doSelect(AdapterView<?> parent, View view, int position,
			long id){
		String selected = parent.getSelectedItem().toString();
		EntryActivity entryActivity = (EntryActivity)mySourceActivity;
		Controller controller = entryActivity.getController();
		if(id == -2 && canCreateItems){
			Log.i("onItemSelected", "found create method");
			EntryDialogFragment dlg = getDialogFragment();
			dlg.setSourceSpinner(this);
		    dlg.show(entryActivity.getSupportFragmentManager(), this.getDialogLayout());
		    Log.i("onItemSelected",""+dlg.isVisible());
		}
		else if(!controller.hasValid(this)){
			controller.updateEntryField(this.createField(selected));
		}
		mySize = parent.getCount();
		mySpinnerParent = parent;
	}
	
	public void onCreated(Editable createdName){
		EntryActivity entryActivity = (EntryActivity)mySourceActivity;
		Controller controller = entryActivity.getController();
		controller.updateEntryField(this.createField(createdName.toString()));
    	//Log.i("EntryActivity.java","New Phrasebook created with name ="+createdName.toString());
    	Cursor cursor = getCursor(createdName.toString());
    	myAdapter.swapCursor(cursor);
    	mySpinnerParent.setSelection(mySpinnerParent.getCount() - 1);
    }
	
	protected abstract Field createField(String selected);
	
	protected abstract String getDialogLayout();
	
	protected abstract EntryDialogFragment getDialogFragment();
	
	public abstract OnItemSelectedListener retrieveOnItemSelectedListener();
	
	protected abstract String getDefaultSpinnerMessage();
	
	protected abstract String[] getFroms();
	
	protected int[] getTos(){
		return new int[]{android.R.id.text1};
	}
	
	public abstract Uri getColumnUri();
	
	protected abstract String getColumnName();
	
	protected abstract int getArrayData();
	
	protected abstract String getLogString();
	
	public Cursor getCursor(String newItem){
		ContentResolver resolver = mySourceActivity.getContentResolver();
		Cursor cursor = resolver.query(this.getColumnUri(), null, null, null, null);
		
		String[] froms = {this.getColumnName(), EntryColumns._ID};
		MatrixCursor extras = new MatrixCursor(froms);
		String[] extraPhrasebooks = getResources().getStringArray(this.getArrayData());
		for(int i = 1; i <= extraPhrasebooks.length; i++) {
			extras.addRow(new String[] {extraPhrasebooks[i - 1], String.valueOf(-1*i)});
		}
		
		MatrixCursor newRow = new MatrixCursor(froms);
		
		if(!TextUtils.isEmpty(newItem)) {
			newRow.addRow(new String[] {newItem, String.valueOf(Long.MAX_VALUE)});
			Log.i(this.getLogString(), String.valueOf(Long.MAX_VALUE));
		}
		
		Cursor[] cursors = {extras, cursor, newRow};
		
		return new MergeCursor(cursors);
	}



}
