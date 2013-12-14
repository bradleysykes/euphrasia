package com.jbj.euphrasia.remote;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

public class CreateUserTask extends AbstractRemoteTask {

	@Override
	protected HttpUriRequest getUriRequest(String[]...params) {
		return new HttpPost(myServiceUrl);
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com//php//db_create_user.php";
	}

}
