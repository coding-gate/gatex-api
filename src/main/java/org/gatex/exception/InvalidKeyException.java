package org.gatex.exception;

public class InvalidKeyException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidKeyException(){
		super();
	}

	public InvalidKeyException(String msg){
		super(msg);
	}
	public InvalidKeyException(String msg, Throwable ex){
		super(msg, ex);
	}
}
