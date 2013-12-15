package com.jbj.euphrasia.dialog_fragments;

import com.jbj.euphrasia.activities.LoginActivity;

import com.jbj.euphrasia.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class NewUserDialog extends AbstractDialog {

	protected EditText myNameField;
	protected EditText myPasswordField;
	private EditText myEmailField;
	
	public NewUserDialog(){
		//empty required
	}
	
	protected void useView(View view){
		myNameField = (EditText) view.findViewById(R.id.account_name);
		myPasswordField = (EditText) view.findViewById(R.id.account_password);
		myEmailField = (EditText) view.findViewById(R.id.account_email);
	}
	
	@Override
	public Dialog makeButtons(AlertDialog.Builder builder, LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.new_user,null);
		this.useView(view);
        builder.setView(view)
        // Add action buttons
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // create a new phrasebook with user data
                	   String accountName = myNameField.getText().toString();
                	   String accountPassword = myPasswordField.getText().toString();
                	   String accountEmail = myEmailField.getText().toString();
                	   EditText[] fields = {myNameField,myPasswordField,myEmailField};
                	   if(accountName.isEmpty()||accountPassword.isEmpty()||accountEmail.isEmpty()){
                		   for(int i=0;i<fields.length;i++){
                			   if(fields[i].getText().toString().length()==0){
                				   fields[i].setHintTextColor(R.color.red);
                			   }
                		   }
                		   Toast.makeText(mySourceActivity, "Please enter all required fields", Toast.LENGTH_LONG).show();
                	   }
                	   else{
	                	   LoginActivity login = (LoginActivity)mySourceActivity;
	                	   login.onUserCreation(accountName, accountPassword, accountEmail);
	                	   dialog.dismiss();
                	   }
               
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                   }
               });
        return builder.create();
	}

	
}
