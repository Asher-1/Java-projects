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
		Queue Q = new Queue(); //创建一个空队列
		String token = new String(""); //创建一个空字符串
		for (int i = 0; i < input.length(); i++) {
			char cc = input.charAt(i);
			//如果当前字符不是一个有效的操作符，就认为它是一个数的一部分
			if (!TokenOperator.isValidOp(cc))
				token += cc; //于是将它添加到token串的后面
			else {//否则，当前存储在token中的数是一个完整的数
				if (!token.trim().equals(""))
					//用它构造一个TokenNumeric对象，然后将它插入队列Q中
					Q.enqueue(new TokenNumeric(token.trim()));
				//用当前字符构造一个TokenOperator，并加入到Q中
				Character operator = new Character(cc);
				Q.enqueue(new TokenOperator(operator.toString()));
				token = new String(""); //令token为空，准备接收下一个数
			}
		}
		if (!token.trim().equals(""))
			Q.enqueue(new TokenNumeric(token.trim()));
		return Q;
	}

	protected static Queue toPostfix(Queue infix) throws TokenException {
		Stack stack = new Stack(); //初始化一个空栈用于存储操作符，即为“运算符栈”
		Queue postfix = new Queue(); //初始化一个空的队列用于存储计算结果，即为“后缀队列”
		try {
			while (!infix.isEmpty()) {
				Object token = infix.dequeue();
				if (token instanceof TokenNumeric)
					postfix.enqueue(token); //如果这个标记串是一个数，将它插入后缀队列中
				else {
					TokenOperator op = (TokenOperator) token;
					if (op.isOpenParen())
						stack.push(op); //如果是左括号，则压入“运算符栈”
					else if (op.isCloseParen()) { //如果是右括号
						while (!((TokenOperator) stack.peek()).isOpenParen())
							postfix.enqueue(stack.pop());
						Object dummy = stack.pop();
					} else if (stack.isEmpty())
						stack.push(op); //如果栈空，则操作符入栈
					else if (((TokenOperator) stack.peek()).order() < op
							.order()) //如果运算符栈的顶部的操作符的优先级小于当前标记串，
						stack.push(op); //则将当前标记串压入栈中
					else {
						while ((!stack.isEmpty())
								&& (((TokenOperator) stack.peek()).order() >= op
										.order()))
							//将运算符栈中所有优先级大于等于当前标记串的标记串弹出栈，
							postfix.enqueue(stack.pop());//并将它们插入后缀队列
						stack.push(op); //将当前标记串压入栈中
					}
				}
			}
			//当表达式队列中的所有标记串都处理完后，从运算符栈中弹出剩余的标记串，
			//并将它们插入到后缀队列中
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
				if (token instanceof TokenOperator) { //如果标记串是操作符
					TokenNumeric n1 = (TokenNumeric) numbers.pop();
					TokenNumeric n2 = (TokenNumeric) numbers.pop();
					TokenOperator op = (TokenOperator) token;
					//根据操作符执行相应的运算，并把计算结果入栈
					numbers.push(op.evaluate(n1.getNumber(), n2.getNumber()));
				} else
					numbers.push(token); //如果标记串是一个数，将它入栈
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

