package com.jbj.euphrasia.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

public class AudioUploadTask extends AbstractRemoteTask {

	@Override
	protected HttpUriRequest getUriRequest(String[]... params) {
		HttpPost post = new HttpPost(getServiceUrl());
		byte[] data = convertAudio("");
		String encodedData = Base64.encodeToString(data, Base64.DEFAULT);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("audio", encodedData));
	    try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post;
	}
	
	@Override
	protected Bundle doInBackground(String[]... params) {
		super.doInBackground(params);
		try {
			int success = myJsonObject.getInt("success");
			if(success == 1){
				Log.i("SUCCESS","File uploaded successfully");
			}
			else{
				Log.i("FAILURE","File not uploaded");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] convertAudio(String filePath){
		File f = new File(filePath);
		byte[] soundFileByteArray = new byte[(int) f.length()];
		try {
			FileInputStream fis = new FileInputStream(f);
			fis.read(soundFileByteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			return soundFileByteArray;
		}
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/db_audio_upload.php";
	}

}
