package chessface;

import java.awt.*;

/**
 * �û��б�Panel����Panelά��һ���������ĵ�ǰ�û��б� ���е��û���������ʾ���б��С�
 */
public class userPad extends Panel {
    //�û��б�
    public List userList = new List(10);
    public userPad() {
        //���ò��ֹ������������Ϣ
        setLayout(new BorderLayout());
        for (int i = 0; i < 30; i++) {
            userList.add(i + "." + "��ǰ�����û�");
        }
        //��ӵ�Panel
        add(userList, BorderLayout.CENTER);
    }
}
