package chessface;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;

/**
 * ������ϢPanel��Panel�ϵ��ı��������ʾ�û�������Ϣ��
 */
public class chatPad extends Panel {
    //�����յ��ı���
    public TextArea chatLineArea = new TextArea("", 18, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);

    public chatPad() {
        //�����Ű��ʽ
        setLayout(new BorderLayout());
       // ���ı�����ӵ�Panel����
        add(chatLineArea, BorderLayout.CENTER);
    }

}