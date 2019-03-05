
package org.asu.ss.C55_Backend;

public class OTPException extends RuntimeException {

	public OTPException() {
	}

	public OTPException(String message) {
		super(message);
	}

	public OTPException(Throwable cause) {
		super(cause);
	}

	public OTPException(String message, Throwable cause) {
		super(message, cause);
	}

}