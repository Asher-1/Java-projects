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
public class TokenNumeric extends Token {

	/**
	 * 
	 * @uml.property name="number" 
	 */
	private double number = 0.0;


	public TokenNumeric(double _number) {
		number = _number;
	}

	public TokenNumeric(String _number) throws TokenException {
		try { //将字符串转换为数值
			number = Double.valueOf(_number).doubleValue();
		} catch (NumberFormatException ne) {
			throw new TokenException("Invalid numeric format: " + _number);
		}
	}

	/**
	 * 
	 * @uml.property name="number"
	 */
	public double getNumber() {
		return number;
	}

	public String toString() {
		return String.valueOf(number);
	}
}

