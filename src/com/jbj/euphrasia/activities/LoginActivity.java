package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.dialog_fragments.ExistingUserDialog;
import com.jbj.euphrasia.dialog_fragments.NewUserDialog;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.CreateUserTask;
import com.jbj.euphrasia.remote.ReadUserTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity implements Constants{

	private String myUsername;
	private String myEmail;
	private String myPassword;
	private boolean rememberMe = false;
	private static final String PREFS_NAME = "My Preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		SharedPreferences sharedPreferences = this.getSharedPreferences(PREFS_NAME, 0);
		String storedUsername = sharedPreferences.getString("user_name", "DNE");
		String storedPassword = sharedPreferences.getString("pass", "DNE");
		if(!storedUsername.equals("DNE")&&!storedPassword.equals("DNE")){
			//user remembered
			Log.i("User check","Stored user = "+storedUsername);
			this.startActivity(new Intent(this,MainActivity.class));
		}
	}
	
	public void onRememberMe(View view){
		CheckBox checkBox = (CheckBox) view;
		if(checkBox.isChecked()){
			rememberMe = true;
		}
	}

	
	public void doNewUser(View view){
		//launch a dialogue to create a new user account
		NewUserDialog dialog = new NewUserDialog();
		dialog.setSourceActivity(this);
		dialog.show(getSupportFragmentManager(), "new_user");
	}
	
	public void doExistingUser(View view){
		//launch a dialogue to login to existing account
		ExistingUserDialog dialog = new ExistingUserDialog();
		dialog.setSourceActivity(this);
		dialog.show(getSupportFragmentManager(), "existing_user");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void onUserCreation(String accountName, String accountPassword, String accountEmail) {
		// user has specified the desired credentials for a new user account. Initialize
		// database. 
		AbstractRemoteTask writeUser = new CreateUserTask();
		writeUser.setActivity(this);
		String[] name = new String[]{"user_name",accountName};
		String[] email = new String[]{"user_mail",accountEmail};
		String[] password = new String[]{"pass",accountPassword};
		writeUser.execute(new String[][]{name,email,password});
	}
	
	public void login(String id){
		if(rememberMe){
			SharedPreferences sharedPreferences = this.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("user_name", myUsername);
			editor.putString("pass", myPassword);
			editor.commit();
			Log.i("stored user name",sharedPreferences.getString("user_name" , "DNE"));
			Log.i("stored password",sharedPreferences.getString("pass" , "DNE"));
		}
		
		Intent intent = new Intent(this,MainActivity.class);
		intent.setAction(ACTION_EXISTING_LOGIN);
		intent.putExtra(EXTRA_EXISTING_USER, id);
		startActivity(intent);
	}
	
	public void onLoginAttempt(String name, String password){
		// check the database to see if these 
		// credentials match an existing user
		AbstractRemoteTask checkUser = new ReadUserTask();
		checkUser.setActivity(this);
		myUsername = name;
		myPassword = password;
		String[] userName = new String[]{"user_name",name};
		String[] userPassword = new String[]{"pass",password};
		String[][] params = new String[][]{userName,userPassword};
		checkUser.execute(params);
//		if(name.equals("Euphrasia") && password.equals("1234")){
//			Intent intent = new Intent(this,MainActivity.class);
//			intent.setAction(ACTION_EXISTING_LOGIN);
//			intent.putExtra(EXTRA_EXISTING_USER, name);
//			startActivity(intent);
//		}
	}

}
