package com.jbj.euphrasia;

import android.content.Context;
import android.media.AudioManager;

public abstract class MediaManager {

	protected boolean myStatus;
	protected AudioManager myAudioManager;
	protected Context myContext;
	protected Controller myController;
	
	public MediaManager(Context context, Controller controller){
		myStatus = false;
		myAudioManager = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
		myContext = context;
		myController = controller;
	}
	
	public void execute(){
		if(!myStatus)
			this.start();
		else{
			this.stop();
		}
		myStatus = !myStatus;
	}
	
	public boolean isGoing(){
		return myStatus;
	}
	
	protected abstract void start();
	
	protected abstract void stop();
	
}
