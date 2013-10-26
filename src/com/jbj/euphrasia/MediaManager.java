package com.jbj.euphrasia;

import java.io.FileDescriptor;
import android.content.Context;
import android.media.AudioManager;

public abstract class MediaManager {
	
	protected FileDescriptor myFileName;
	protected boolean myStatus;
	protected AudioManager myAudioManager;
	protected Context myContext;
	
	public MediaManager(FileDescriptor fileName, Context context){
		myFileName = fileName;
		myStatus = false;
		myAudioManager = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
		myContext = context;
	}
	
	public void execute(){
		if(!myStatus)
			this.start();
		else
			this.stop();
		myStatus = !myStatus;
	}
	
	protected abstract void start();
	
	protected abstract void stop();
	
	public abstract void pause();
}
