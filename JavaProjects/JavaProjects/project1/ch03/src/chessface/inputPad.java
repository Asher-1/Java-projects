package chessface;

import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

/**
 * ������ϢPanel��Panel��ߵ������б����г��������û������֡�
 * �ұߵ��ı����������������Ϣ������س���Ϣ�����͡�
 * ���⻹���� ���ı���������������changename��list�ȡ�
 */

public class inputPad extends Panel {
	public TextField inputWords = new TextField("����س�������Ϣ", 20);

    //ѡ������Ϣ���û�
	public Choice userChoice = new Choice();

	public Label chatLabel = new Label("���뷢����Ϣ:");

	public inputPad() {
        //���ò��ֹ������ͱ�����ɫ(����ɫ)
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(new Color(204, 204, 204));
		//��������û�״̬(��������Ϊ30���û�)
        for (int i = 0; i < 30; i++) {
			userChoice.addItem(i + "." + "��ǰ�����û�");
		}
		userChoice.setSize(60, 24);
        //����Ӧ�ؼ���ӵ�Panel
        add(userChoice);
		add(chatLabel);
		add(inputWords);
	}

}