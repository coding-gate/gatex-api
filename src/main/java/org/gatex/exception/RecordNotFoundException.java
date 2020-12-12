package org.gatex.exception;

public class RecordNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(){
		super();
	}
	
	public RecordNotFoundException(String msg){
		super(msg);
	}
	public RecordNotFoundException(String msg, Throwable ex){
		super(msg, ex);
	}
}
