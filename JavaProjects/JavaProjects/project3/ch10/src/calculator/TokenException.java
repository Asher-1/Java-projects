package calculator;

/*
 * Created on 2004-11-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author tom1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TokenException extends Exception {
	public TokenException() {
		super("Invalid token");
	}

	public TokenException(String msg) {
		super(msg);
	}

}