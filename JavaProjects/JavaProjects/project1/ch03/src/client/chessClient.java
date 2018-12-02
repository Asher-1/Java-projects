package client;

import chessface.chatPad;
import chessface.inputPad;
import chessface.userPad;
import chessface.chessPad;
import chessface.controlPad;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

/*
 * ������ͻ��˿�ܣ�ʵ���˶����������ͼ��̼�����
 */

public class chessClient extends Frame implements ActionListener, KeyListener {

    userPad userpad = new userPad();// �û��б�Panel

    chatPad chatpad = new chatPad();// ������ϢPanel

    controlPad controlpad = new controlPad();// ����Panel

    chessPad chesspad = new chessPad();// ����Panel

    inputPad inputpad = new inputPad();// ��Ϣ����Panel

    Socket chatSocket;

    DataInputStream in;

    DataOutputStream out;

    String chessClientName = null;

    String host = null;

    int port = 4331;

    boolean isOnChat = false; // �Ƿ�������

    boolean isOnChess = false; // �Ƿ�������

    boolean isGameConnected = false; // �Ƿ�����Ŀͻ�������

    boolean isServer = false; // �Ƿ�����Ϸ������

    boolean isClient = false; // �Ƿ������Ϸ�Ŀͻ���

    Panel northPanel = new Panel();

    Panel centerPanel = new Panel();

    Panel eastPanel = new Panel();

    /*
     * ������ͻ��˿�ܵĹ��캯����������ʼ��һЩ���󡢲��ֺ�Ϊ��ť��Ӽ�������
     */
    public chessClient() {
        // ���ڱ���
        super("������ͻ���");
        // ���ò���
        setLayout(new BorderLayout());
        // ��ȡ������ip��ַ
        host = controlpad.inputIP.getText();
        // �Ҳ��Panel,����������û��б��������Ϣ�б��Panel
        // ����eastPanel����
        eastPanel.setLayout(new BorderLayout());
        // ��������������б�
        eastPanel.add(userpad, BorderLayout.NORTH);
        // ���������������Ϣ����
        eastPanel.add(chatpad, BorderLayout.CENTER);
        // �趨����ɫ
        eastPanel.setBackground(new Color(204, 204, 204));

        // �Ա༭�����Ϣ��������¼�������..
        inputpad.inputWords.addKeyListener(this);
        // ��������Panel����ķ�������ַ
        chesspad.host = controlpad.inputIP.getText();
        // ��centerPanel��������̺��û�������Ϣpanel,���趨������ɫ
        centerPanel.add(chesspad, BorderLayout.CENTER);
        centerPanel.add(inputpad, BorderLayout.SOUTH);
        centerPanel.setBackground(new Color(204, 204, 204));

        // Ϊcontrolpad�еİ�ť������¼�����...
        controlpad.connectButton.addActionListener(this);
        controlpad.creatGameButton.addActionListener(this);
        controlpad.joinGameButton.addActionListener(this);
        controlpad.cancelGameButton.addActionListener(this);
        controlpad.exitGameButton.addActionListener(this);

        // ��ʼ�趨�⼸����ťΪ�Ǽ���״̬..
        controlpad.creatGameButton.setEnabled(false);
        controlpad.joinGameButton.setEnabled(false);
        controlpad.cancelGameButton.setEnabled(false);
        // ���controlpad��northPanel---���϶˵�panel
        northPanel.add(controlpad, BorderLayout.CENTER);
        northPanel.setBackground(new Color(204, 204, 204));
        // ��Ӵ��ڼ������������ڹر�ʱ���ر�����ͨѶ��Socket��
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isOnChat) { //������������ѽ��������ͷ�
                    try {
                        chatSocket.close();
                    } catch (Exception ed) {
                    }
                }
                if (isOnChess || isGameConnected) { //������������ѽ��������ͷ�
                    try {
                        chesspad.chessSocket.close();
                    } catch (Exception ee) {
                    }
                }
                System.exit(0);
            }
        });
        // �����Щpanel��frame��������մ���
        add(eastPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);
        // ���ô�С��
        setSize(670, 560);
        setVisible(true);
        setResizable(true);
        validate();
    }

    /**
     * �ͷ������������Ӳ�ͨ�ŵĺ�����
     * @return true ������ӳɹ�, false �������ʧ��..
     */
    public boolean connectServer(String serverIP, int serverPort) throws Exception {
        try {
           // System.out.println("in chessClient#connectServer");
            //��������socket
            chatSocket = new Socket(serverIP, serverPort);
            //��socket�õ����������.
            in = new DataInputStream(chatSocket.getInputStream());
            out = new DataOutputStream(chatSocket.getOutputStream());
            //�����߳�
            clientThread clientthread = new clientThread(this);
            clientthread.start();
            isOnChat = true;
            return true;
        } catch (IOException ex) {//���Ӳ��ɹ�
            chatpad.chatLineArea.setText("chessClient:connectServer:�޷�����,���������������� \n");
        }
        return false;
    }

    /**
     * ��������������Ӧ��ť���������
     */
    public void actionPerformed(ActionEvent e) {
        ////////////////////////////////////////////////////
        // ���������ǡ�������������ť�����û�ȡ�ķ��������������ӷ�������
        if (e.getSource() == controlpad.connectButton) {
            host = chesspad.host = controlpad.inputIP.getText();
            try {
                if (connectServer(host, port)) {
                    //���ӳɹ�
                    chatpad.chatLineArea.setText("");
                    //���Ӱ�ť����Ϊ�Ǽ���̬..
                    controlpad.connectButton.setEnabled(false);
                    //�����,������Ϸ��ť
                    controlpad.creatGameButton.setEnabled(true);
                    controlpad.joinGameButton.setEnabled(true);
                    chesspad.statusText.setText("���ӳɹ����봴����Ϸ�������Ϸ");
                }

            } catch (Exception ei) { //����ʧ��
                chatpad.chatLineArea.setText("controlpad.connectButton:�޷�����,���������������� \n");
            }
        }
        //////////////////////////////////////////////////////////
        // ���������ǡ��رճ��򡱰�ť����ر����ڽ���ͨ�ŵ�Socekt���˳���Ϸ��
        if (e.getSource() == controlpad.exitGameButton) {
            if (isOnChat) { //����������ӽ������ͷ�
                try {
                    chatSocket.close();
                } catch (Exception ed) {
                }
            }
            if (isOnChess || isGameConnected) {//����������ӽ���,���ͷ�
                try {
                    chesspad.chessSocket.close();
                } catch (Exception ee) {
                }
            }
            System.exit(0);

        }
        ///////////////////////////////////////////////////////
        // ���������ǡ�������Ϸ����ť�������ж�ѡ���ļ����Ŀ���Ƿ���Ч��
        // ���ѡ����Ŀ��Ϊ�ջ����������Ϊ�䱾������ΪĿ����Ч��
        if (e.getSource() == controlpad.joinGameButton) {
            // �õ�ѡ����û�
            String selectedUser = userpad.userList.getSelectedItem();
            if (selectedUser == null || selectedUser.startsWith("[inchess]") || selectedUser.equals(chessClientName)) {
                chesspad.statusText.setText("������ѡ��һ����Ч�û�");
            } else {
                try {
                    // ���δ����������������ӣ��������ӣ��趨�û��ĵ�ǰ״̬��
                    // ���⻹Ҫ�԰�ť��һЩ���������������ӡ���ť�͡�������Ϸ��ť����Ϊ�����á�
                    if (!isGameConnected) {
                        if (chesspad.connectServer(chesspad.host, chesspad.port)) {
                            isGameConnected = true;
                            isOnChess = true;
                            isClient = true;
                            controlpad.creatGameButton.setEnabled(false);
                            controlpad.joinGameButton.setEnabled(false);
                            controlpad.cancelGameButton.setEnabled(true);
                            //������������û�������Ϸ����Ϣ
                            chesspad.chessthread.sendMessage("/joingame " + userpad.userList.getSelectedItem() + " "
                                    + chessClientName);
                        }
                    }
                    // ����ѽ������ӣ�ʡȥ�������ӵĲ�����
                    else {
                        isOnChess = true;
                        isClient = true;
                        controlpad.creatGameButton.setEnabled(false);
                        controlpad.joinGameButton.setEnabled(false);
                        controlpad.cancelGameButton.setEnabled(true);
                        //������������û�������Ϸ����Ϣ
                        chesspad.chessthread.sendMessage("/joingame " + userpad.userList.getSelectedItem() + " "
                                + chessClientName);
                    }
                } catch (Exception ee) {//������Ϸ ʧ�ܡ���
                    isGameConnected = false;
                    isOnChess = false;
                    isClient = false;
                    controlpad.creatGameButton.setEnabled(true);
                    controlpad.joinGameButton.setEnabled(true);
                    controlpad.cancelGameButton.setEnabled(false);
                    chatpad.chatLineArea.setText("chesspad.connectServer�޷����� \n" + ee);
                }

            }
        }
        ////////////////////////////////////////////////////////////
        // ���������ǡ�������Ϸ����ť���趨�û�״̬����ť״̬��Ȼ���������ͨѶ��
        if (e.getSource() == controlpad.creatGameButton) {
            try {
                // δ��������ʱ�Ĳ�����
                if (!isGameConnected) {
                    if (chesspad.connectServer(chesspad.host, chesspad.port)) {
                        isGameConnected = true;
                        isOnChess = true;
                        isServer = true;
                        controlpad.creatGameButton.setEnabled(false);
                        controlpad.joinGameButton.setEnabled(false);
                        controlpad.cancelGameButton.setEnabled(true);
                        chesspad.chessthread.sendMessage("/creatgame " + "[inchess]" + chessClientName);
                    }
                }
                // ��������ʱ�Ĳ�����
                else {
                    isOnChess = true;
                    isServer = true;
                    controlpad.creatGameButton.setEnabled(false);
                    controlpad.joinGameButton.setEnabled(false);
                    controlpad.cancelGameButton.setEnabled(true);
                    chesspad.chessthread.sendMessage("/creatgame " + "[inchess]" + chessClientName);
                }
            } catch (Exception ec) {
                isGameConnected = false;
                isOnChess = false;
                isServer = false;
                controlpad.creatGameButton.setEnabled(true);
                controlpad.joinGameButton.setEnabled(true);
                controlpad.cancelGameButton.setEnabled(false);
                ec.printStackTrace();
                chatpad.chatLineArea.setText("chesspad.connectServer�޷����� \n" + ec);
            }

        }
        ////////////////////////////////////////////////
        // ���������ǡ�ȡ����Ϸ����ť��ͬ��Ҫ�޸İ�ť״̬��
        if (e.getSource() == controlpad.cancelGameButton) {
            // ���������ڽ��У��ж��˳���Ϸ��һ����
            if (isOnChess) {
                chesspad.chessthread.sendMessage("/giveup " + chessClientName);
                chesspad.chessVictory(-1 * chesspad.chessColor);
                controlpad.creatGameButton.setEnabled(true);
                controlpad.joinGameButton.setEnabled(true);
                controlpad.cancelGameButton.setEnabled(false);
                chesspad.statusText.setText("�뽨����Ϸ���߼�����Ϸ");
            }
            if (!isOnChess) {
                controlpad.creatGameButton.setEnabled(true);
                controlpad.joinGameButton.setEnabled(true);
                controlpad.cancelGameButton.setEnabled(false);
                chesspad.statusText.setText("�뽨����Ϸ���߼�����Ϸ");
            }
            isClient = isServer = false;
        }

    }

    /**
     * ���̼���������Ӧ���س����¡��¼�. ������Ϣ..
     */
    public void keyPressed(KeyEvent e) {
        /*
                                         * TextField inputWords = (TextField)
                                         * e.getSource();
                                         * //���ѡ���������˷���Ϣ����������Ϣֱ�ӷ��������� if
                                         * (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                         * if
                                         * (inputpad.userChoice.getSelectedItem().equals("������")) {
                                         * try {
                                         * out.writeUTF(inputWords.getText());
                                         * inputWords.setText(""); } catch
                                         * (Exception ea) { chatpad.chatLineArea
                                         * .setText("chessClient:KeyPressed�޷�����,������������
                                         * \n"); userpad.userList.removeAll();
                                         * inputpad.userChoice.removeAll();
                                         * inputWords.setText("");
                                         * controlpad.connectButton.setEnabled(true); } }
                                         * //���ѡ����һ���˷���Ϣ����������Ϣ��װ��һ����ʽ���������� else {
                                         * try { out.writeUTF("/" +
                                         * inputpad.userChoice.getSelectedItem() + " " +
                                         * inputWords.getText());
                                         * inputWords.setText(""); } catch
                                         * (Exception ea) { chatpad.chatLineArea
                                         * .setText("chessClient:KeyPressed�޷�����,������������
                                         * \n"); userpad.userList.removeAll();
                                         * inputpad.userChoice.removeAll();
                                         * inputWords.setText("");
                                         * controlpad.connectButton.setEnabled(true); } } }
                                         */

    }

    public void keyTyped(KeyEvent e) {//���Ը��¼�...
    }

    public void keyReleased(KeyEvent e) {//���Բ�������¼�
    }

    public static void main(String args[]) {//������,�����ͻ���
        chessClient chessClient = new chessClient();
    }
}

class clientThread extends Thread {
    chessClient chessclient;

    clientThread(chessClient chessclient) {
        this.chessclient = chessclient;
    }

    /**
     * �ͻ����̶߳Խ��յ�����Ϣ���д���ĺ���
     */
    public void acceptMessage(String recMessage) {
        if (recMessage.startsWith("/userlist ")) {
            // ������յ�����Ϣ��"/userlist "��ͷ���������û�����ȡ��������ӵ�
            // ������ϢPanel��ߵ��û��б��С�
            StringTokenizer userToken = new StringTokenizer(recMessage, " ");
            int userNumber = 0;
            
            chessclient.userpad.userList.removeAll();
            chessclient.inputpad.userChoice.removeAll();
            chessclient.inputpad.userChoice.addItem("������");
            while (userToken.hasMoreTokens()) {
                String user = (String) userToken.nextToken(" ");
                if (userNumber > 0 && !user.startsWith("[inchess]")) {
                    chessclient.userpad.userList.add(user);
                    chessclient.inputpad.userChoice.addItem(user);
                }
                userNumber++;
            }
            chessclient.inputpad.userChoice.select("������");
        }
        // ���������յ�����Ϣ��"/yourname "��ͷ,���û�����ʾ�ڿͻ��˶Ի����������
        else if (recMessage.startsWith("/yourname ")) {
            //֮������10��Ϊ��/yourname ������10���ַ�����������û�������
            chessclient.chessClientName = recMessage.substring(10);
            chessclient.setTitle("������ͻ��� " + "��ǰ�û���:" + chessclient.chessClientName);
        }
        // ���������յ�����Ϣ��"/reject"��ͷ����״̬����ʾ�ܾ�������Ϸ��
        else if (recMessage.equals("/reject")) {
            try {
                chessclient.chesspad.statusText.setText("���ܼ�����Ϸ");
                //���°�ť״̬
                chessclient.controlpad.cancelGameButton.setEnabled(false);
                chessclient.controlpad.joinGameButton.setEnabled(true);
                chessclient.controlpad.creatGameButton.setEnabled(true);
            } catch (Exception ef) {
                chessclient.chatpad.chatLineArea.setText("chessclient.chesspad.chessSocket.close�޷��ر�");
            }
            chessclient.controlpad.joinGameButton.setEnabled(true);
        }
        // ���������յ�����Ϣ��"/peer"��ͷ,����¶Է������֣�Ȼ�����ȴ�״̬
        else if (recMessage.startsWith("/peer ")) {
            //ͬ��,��Ϊ/peer �����ַ�,������6
            chessclient.chesspad.chessPeerName = recMessage.substring(6);
            if (chessclient.isServer) {//��������
                chessclient.chesspad.chessColor = 1;
                chessclient.chesspad.isMouseEnabled = true;
                chessclient.chesspad.statusText.setText("���������");
            } else if (chessclient.isClient) {
                chessclient.chesspad.chessColor = -1;
                chessclient.chesspad.statusText.setText("�Ѽ�����Ϸ���ȴ��Է�����...");
            }

        } else if (recMessage.equals("/youwin")) {
            chessclient.isOnChess = false;
            chessclient.chesspad.chessVictory(chessclient.chesspad.chessColor);
            chessclient.chesspad.statusText.setText("�Է��˳�����������Ϸ�˳�����");
            chessclient.chesspad.isMouseEnabled = false;
        } else if (recMessage.equals("/OK")) {
            chessclient.chesspad.statusText.setText("������Ϸ�ɹ����ȴ����˼���...");
        } else if (recMessage.equals("/error")) {
            chessclient.chatpad.chatLineArea.append("����������˳��������¼��� \n");
        } else {//��ͨ����
            chessclient.chatpad.chatLineArea.append(recMessage + "\n");
            chessclient.chatpad.chatLineArea.setCaretPosition(chessclient.chatpad.chatLineArea.getText().length());
        }
    }

    public void run() {
        String message = "";
        try {
            while (true) {
                //ͬchessface.chessThread��һ������������socket��ȡ������������
                message = chessclient.in.readUTF();
                acceptMessage(message);
            }
        } catch (IOException es) {
            //��ȡ�쳣..���߿������������Ӧ����,�����������������Ӧ����Ϣ��ʾ��,
            //�Ӷ�����߳������пɿ��ԡ�
        }
    }

}