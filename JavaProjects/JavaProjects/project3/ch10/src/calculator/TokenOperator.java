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
public class TokenOperator extends Token {
	private static String OPERATORS = "+-/*^()"; //������Ч�Ĳ�����

	private static int[] PRECEDENCE = { 1, 1, 2, 2, 3, 0, 9 }; //�������ȼ�

	private String token;

	public TokenOperator(String _token) throws TokenException {
		if (OPERATORS.indexOf(_token) >= 0) //�жϲ����Ƿ�Ϊ��Ч�Ĳ�����
			token = _token;
		else
			throw new TokenException("invalid operator: " + _token);
	}

	public boolean isOpenParen() {
		return token.equals("(");
	}

	public boolean isCloseParen() {
		return token.equals(")");
	}

	public int order() { //���ز����������ȼ�
		return PRECEDENCE[OPERATORS.indexOf(token)];
	}

	public static boolean isValidOp(char op) { //�жϲ����Ƿ�Ϊ��Ч�Ĳ�����
		return (OPERATORS.indexOf(op) >= 0);
	}

	public String toString() {
		return "Operator: " + token;
	}

	public TokenNumeric evaluate(double num1, double num2)
			throws TokenException { //���ݲ�ͬ�Ĳ�����ִ����Ӧ�Ĳ���
		if (token.equals("+"))
			return new TokenNumeric(num2 + num1);
		else if (token.equals("-"))
			return new TokenNumeric(num2 - num1);
		else if (token.equals("*"))
			return new TokenNumeric(num2 * num1);
		else if (token.equals("/"))
			return new TokenNumeric(num2 / num1);
		else if (token.equals("^"))
			return new TokenNumeric(Math.pow(num2, num1));
		else
			throw new TokenException("invalid token, can not evaluate");
	}
}

