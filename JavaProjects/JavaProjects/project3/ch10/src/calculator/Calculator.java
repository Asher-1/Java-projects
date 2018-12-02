package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class Calculator {
	private static Queue tokens = null;

	protected static Queue tokenize(String input) throws TokenException {
		Queue Q = new Queue(); //����һ���ն���
		String token = new String(""); //����һ�����ַ���
		for (int i = 0; i < input.length(); i++) {
			char cc = input.charAt(i);
			//�����ǰ�ַ�����һ����Ч�Ĳ�����������Ϊ����һ������һ����
			if (!TokenOperator.isValidOp(cc))
				token += cc; //���ǽ�����ӵ�token���ĺ���
			else {//���򣬵�ǰ�洢��token�е�����һ����������
				if (!token.trim().equals(""))
					//��������һ��TokenNumeric����Ȼ�����������Q��
					Q.enqueue(new TokenNumeric(token.trim()));
				//�õ�ǰ�ַ�����һ��TokenOperator�������뵽Q��
				Character operator = new Character(cc);
				Q.enqueue(new TokenOperator(operator.toString()));
				token = new String(""); //��tokenΪ�գ�׼��������һ����
			}
		}
		if (!token.trim().equals(""))
			Q.enqueue(new TokenNumeric(token.trim()));
		return Q;
	}

	protected static Queue toPostfix(Queue infix) throws TokenException {
		Stack stack = new Stack(); //��ʼ��һ����ջ���ڴ洢����������Ϊ�������ջ��
		Queue postfix = new Queue(); //��ʼ��һ���յĶ������ڴ洢����������Ϊ����׺���С�
		try {
			while (!infix.isEmpty()) {
				Object token = infix.dequeue();
				if (token instanceof TokenNumeric)
					postfix.enqueue(token); //��������Ǵ���һ���������������׺������
				else {
					TokenOperator op = (TokenOperator) token;
					if (op.isOpenParen())
						stack.push(op); //����������ţ���ѹ�롰�����ջ��
					else if (op.isCloseParen()) { //�����������
						while (!((TokenOperator) stack.peek()).isOpenParen())
							postfix.enqueue(stack.pop());
						Object dummy = stack.pop();
					} else if (stack.isEmpty())
						stack.push(op); //���ջ�գ����������ջ
					else if (((TokenOperator) stack.peek()).order() < op
							.order()) //��������ջ�Ķ����Ĳ����������ȼ�С�ڵ�ǰ��Ǵ���
						stack.push(op); //�򽫵�ǰ��Ǵ�ѹ��ջ��
					else {
						while ((!stack.isEmpty())
								&& (((TokenOperator) stack.peek()).order() >= op
										.order()))
							//�������ջ���������ȼ����ڵ��ڵ�ǰ��Ǵ��ı�Ǵ�����ջ��
							postfix.enqueue(stack.pop());//�������ǲ����׺����
						stack.push(op); //����ǰ��Ǵ�ѹ��ջ��
					}
				}
			}
			//�����ʽ�����е����б�Ǵ���������󣬴������ջ�е���ʣ��ı�Ǵ���
			//�������ǲ��뵽��׺������
			while (!stack.isEmpty()) {
				if (!((TokenOperator) stack.peek()).isOpenParen())
					postfix.enqueue(stack.pop());
				else
					throw new TokenException("unmatched bracket");
			}
			return postfix;
		} catch (StackException se) {
			throw new TokenException("unmatched bracket or invalid input");
		} catch (QueueException qe) {
			throw new TokenException("unknown exception");
		}
	}

	protected static double evaluatePostfix(Queue postfix)
			throws TokenException {
		Stack numbers = new Stack();
		try {
			while (!postfix.isEmpty()) {
				Object token = postfix.dequeue();
				if (token instanceof TokenOperator) { //�����Ǵ��ǲ�����
					TokenNumeric n1 = (TokenNumeric) numbers.pop();
					TokenNumeric n2 = (TokenNumeric) numbers.pop();
					TokenOperator op = (TokenOperator) token;
					//���ݲ�����ִ����Ӧ�����㣬���Ѽ�������ջ
					numbers.push(op.evaluate(n1.getNumber(), n2.getNumber()));
				} else
					numbers.push(token); //�����Ǵ���һ������������ջ
			}
			TokenNumeric answer = (TokenNumeric) numbers.pop();
			if (numbers.isEmpty())
				return answer.getNumber();
			else
				throw new TokenException("invalid expression");
		} catch (StackException se) {
			throw new TokenException("invalid expression");
		} catch (QueueException qe) {
			throw new TokenException("unknown error");
		}
	}

	public static void main(String args[]) {
		String prompt = "Calc [q to quit] => ";
		System.out.print("Calc [q to quit] => ");
		BufferedReader buff = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			String input = buff.readLine();
			while (!input.equalsIgnoreCase("q")) {
				if (!input.equals("")) {
					try {
						double ans = evaluatePostfix(toPostfix(tokenize(input)));
						System.out.println(prompt + input + " = " + ans);
					} catch (TokenException te) {
						System.err.println(te);
					}
				}
				System.out.print(prompt);
				input = buff.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}

