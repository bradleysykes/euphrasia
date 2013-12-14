package com.jbj.euphrasia.remote;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.os.Bundle;
import android.os.Looper;


public class ReadRemoteTask extends AbstractRemoteTask {

	@Override
	protected Bundle doInBackground(String[]... args) { 
		Looper.getMainLooper().prepare();
		super.doInBackground(args);
		return null;
	}

	@Override
	protected HttpUriRequest getUriRequest(String[]...params) {
		return new HttpGet(myServiceUrl);
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/read.php";
	}


}
