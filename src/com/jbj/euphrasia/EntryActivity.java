package com.jbj.euphrasia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class EntryActivity extends Activity {
	
	private FieldFactory myFieldFactory;
	private Controller myController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		myFieldFactory = new FieldFactory();
		myController = new Controller(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}
	
	public void updateField(View view){
		/* get appropriate EditText object
		 * Call this method whenever an EditText component losesFocus
		 */
		Field field = myFieldFactory.createField("componentID", "data");
		myController.updateEntryField(field);
	}
	
	/*
	 * To record, user holds a record button. As long as this is held, 
	 * mic continues recording. 
	 * When recording stops, presented option to save, playback, or record again. 
	 */
	public void handleRecording(View view){
		view.setPressed(true);
		//create new audio field
		//pass to controller method
		
	}
	
	
	

}
