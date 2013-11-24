package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class NullField extends Field {

	private static final String NULL_DATA = "THIS IS NULL";

	public NullField() {
		super();
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		return entryManager;
	}
	
	@Override
	public boolean isNull(){
		return true;
	}

}
