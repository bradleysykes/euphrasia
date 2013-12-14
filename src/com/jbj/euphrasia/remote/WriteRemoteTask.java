package com.jbj.euphrasia.remote;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class WriteRemoteTask extends AbstractRemoteTask {

	@Override
	protected Void doInBackground(String[]... arg0) {
		Looper.getMainLooper().prepare();
		super.doInBackground(arg0);
		try{
			int success = myJsonObject.getInt("success");
			Log.i("YOMAMA", myJsonObject.getString("message"));
			Log.i("JOMAMA", String.valueOf(success));
			if(success==1){
				Toast.makeText(mySourceActivity, "Successfully synced to remote database.", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(mySourceActivity, "Failed to sync to remote database.", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected HttpUriRequest getUriRequest() {
		HttpPost post = new HttpPost(myServiceUrl);
		try {
			post.setEntity(new UrlEncodedFormEntity(myPairs));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/db_create_entry.php";
	}

}
