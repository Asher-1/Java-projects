package server;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * ��ʾ���������û���Ϣ��Panel��
 */
class MessageServerPanel extends Panel {
    // ����Ϣ����
    TextArea messageBoard = new TextArea("", 22, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);

    Label statusLabel = new Label("��ǰ������:", Label.LEFT);

    Panel boardPanel = new Panel();// ����ʾ��Panel

    Panel statusPanel = new Panel();// ����״̬Panel

    MessageServerPanel() {
        setSize(350, 300);
        setBackground(new Color(204, 204, 204));
        setLayout(new BorderLayout());
        boardPanel.setLayout(new FlowLayout());
        boardPanel.setSize(210, 210);
        statusPanel.setLayout(new BorderLayout());
        statusPanel.setSize(210, 50);
        boardPanel.add(messageBoard);
        statusPanel.add(statusLabel, BorderLayout.WEST);
        add(boardPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
    }
}

/**
 * �������߳�,��Ҫ���ڷ�������ͻ��˵�ͨ��
 */
class ServerThread extends Thread {
    Socket clientSocket;

    Hashtable clientDataHash;// Socket�뷢�����ݵ�����ӳ��

    Hashtable clientNameHash;// Socket���û�����ӳ��

    Hashtable chessPeerHash;// ���ĵ������ͻ����û�����ӳ��

    MessageServerPanel server;

    boolean isClientClosed = false;

    /**
     * ���������̵߳Ĺ��캯�������ڳ�ʼ��һЩ����
     */
    ServerThread(Socket clientSocket, Hashtable clientDataHash, Hashtable clientNameHash, Hashtable chessPeerHash,
            MessageServerPanel server) {
        this.clientSocket = clientSocket;
        this.clientDataHash = clientDataHash;
        this.clientNameHash = clientNameHash;
        this.chessPeerHash = chessPeerHash;
        this.server = server;
    }

    /**
     * �Կͻ��˷�������Ϣ����ĺ����������ת���ؿͻ��ˡ�������Ϣ�Ĺ��̱Ƚϸ��ӣ� Ҫ��Ժܶ�������ֱ���
     */
    public void messageTransfer(String message) {
        String clientName, peerName;
        /////////////�����///////////////////////////////
        // �����Ϣ�ԡ�/����ͷ��������������Ϣ��
        if (message.startsWith("/")) {

            // �����Ϣ�ԡ�/list����ͷ������������ͻ����Ը����û��б�
            if (message.equals("/list")) {
                Feedback(getUserList());
            }
            // �����Ϣ��"/creatgame [inchess]"��ͷ�����޸�clientNameHashӳ��
            // ��chessPeerHashӳ�䡣
            else if (message.startsWith("/creatgame [inchess]")) {
                //
                String chessServerName = message.substring(20);
                synchronized (clientNameHash) {
                    clientNameHash.put(clientSocket, message.substring(11));
                }
                synchronized (chessPeerHash) {//�մ������ȴ������˼���
                    chessPeerHash.put(chessServerName, "wait");
                }
                Feedback("/yourname " + clientNameHash.get(clientSocket));
                chessPeerTalk(chessServerName, "/OK");
                publicTalk(getUserList());
            }
            // �����Ϣ�ԡ�/joingame����ͷ������Ϣ�ķ�������ֺͱ����û�����ȡ������
            // Ȼ���޸�clientNameHash���chessPeerHash��
            if (message.startsWith("/joingame ")) {
                StringTokenizer userToken = new StringTokenizer(message, " ");
                String getUserToken, serverName, selfName;
                String[] chessNameOpt = {"0", "0"};
                int getOptNum = 0;
                // ��ȡ������û����ͱ����û���
                while (userToken.hasMoreTokens()) {
                    getUserToken = (String) userToken.nextToken(" ");
                    if (getOptNum >= 1 && getOptNum <= 2) {
                        chessNameOpt[getOptNum - 1] = getUserToken;
                    }
                    getOptNum++;
                }
                serverName = chessNameOpt[0];
                selfName = chessNameOpt[1];
                // ����з�����ڵȴ���ʼ���
                if (chessPeerHash.containsKey(serverName) && chessPeerHash.get(serverName).equals("wait")) {
                    // �޸�Socket������ӳ��
                    synchronized (clientNameHash) {
                        clientNameHash.put(clientSocket, ("[inchess]" + selfName));
                    }
                    // �޸�chessPeerHashӳ��
                    synchronized (chessPeerHash) {
                        chessPeerHash.put(serverName, selfName);
                    }
                    publicTalk(getUserList());
                    chessPeerTalk(selfName, ("/peer " + "[inchess]" + serverName));
                    chessPeerTalk(serverName, ("/peer " + "[inchess]" + selfName));
                } else {
                    chessPeerTalk(selfName, "/reject");
                    try {
                        clientClose();
                    } catch (Exception ez) {
                    }
                }
            }
            // �����Ϣ�ԡ�/[inchess]����ͷ�����ȡҪ������Ϣ���û����ͷ��͵���Ϣ
            // Ȼ���ͳ�ȥ��
            else if (message.startsWith("/[inchess]")) {
                int firstLocation = 0, lastLocation;

                lastLocation = message.indexOf(" ", 0);

                peerName = message.substring((firstLocation + 1), lastLocation);
                message = message.substring((lastLocation + 1));
                if (chessPeerTalk(peerName, message)) {
                    Feedback("/error");
                }
            }
            // �����Ϣ�ԡ�/giveup����ͷ�����ж��Ƕ���˫���ķ������ˡ�
            else if (message.startsWith("/giveup ")) {
                String chessClientName = message.substring(8);
                if (chessPeerHash.containsKey(chessClientName)
                        && !((String) chessPeerHash.get(chessClientName)).equals("wait")) {
                    // ������񷽷�����������Ϣ��/youwin�������Է���ʤ
                    chessPeerTalk((String) chessPeerHash.get(chessClientName), "/youwin");
                    //�޳���Զ��ĵ��ˡ���
                    synchronized (chessPeerHash) {
                        chessPeerHash.remove(chessClientName);
                    }
                }
                if (chessPeerHash.containsValue(chessClientName)) {
                    // ����ͻ���������Ҳ������Ϣ��/youwin�������Է���ʤ
                    chessPeerTalk((String) getHashKey(chessPeerHash, chessClientName), "/youwin");
                    synchronized (chessPeerHash) {
                        chessPeerHash.remove((String) getHashKey(chessPeerHash, chessClientName));
                    }
                }
            }
            // ����Ҳ���������Ϣ���û����������Ϣ˵��û������û���
            else {
                int firstLocation = 0, lastLocation;
                lastLocation = message.indexOf(" ", 0);
                if (lastLocation == -1) {
                    Feedback("��Ч����");
                    return;
                } else {
                    peerName = message.substring((firstLocation + 1), lastLocation);
                    message = message.substring((lastLocation + 1));
                    message = (String) clientNameHash.get(clientSocket) + ">" + message;
                    if (peerTalk(peerName, message)) {
                        Feedback("û������û�:" + peerName + "\n");
                    }
                }

            }

        }
        // //////////////////////////////////////////////
        // ������ԡ�/����ͷ����������ͨ��Ϣ��ֱ�ӷ���
        else {
            message = clientNameHash.get(clientSocket) + ">" + message;
            server.messageBoard.append(message + "\n");
            publicTalk(message);
            server.messageBoard.setCaretPosition(server.messageBoard.getText().length());
        }

    }

    /**
     * ���͹�����Ϣ�ĺ���������Ϣ��ÿ���ͻ��˶�����һ��
     */
    public void publicTalk(String publicTalkMessage) {

        synchronized (clientDataHash) {
            //ö�ٱ������пͻ������������
            for (Enumeration enu = clientDataHash.elements(); enu.hasMoreElements();) {
                DataOutputStream outData = (DataOutputStream) enu.nextElement();
                try {
                    //�����Ϣ�������������
                    outData.writeUTF(publicTalkMessage);
                } catch (IOException es) {
                    //��ӡ�쳣��ջ��������ֹ���򡣡�
                    es.printStackTrace();
                }
            }
        }
    }

    /**
     * ѡ���������Ϣ������peerTalkΪ���͵��û���������Ĳ���Ϊ���͵���Ϣ
     */
    public boolean peerTalk(String peerTalk, String talkMessage) {
        //
        for (Enumeration enu = clientDataHash.keys(); enu.hasMoreElements();) {
            Socket userClient = (Socket) enu.nextElement();
            // �ҵ�������Ϣ�Ķ��󣬻�ȡ����������Է�����Ϣ
            if (peerTalk.equals((String) clientNameHash.get(userClient))
                    && !peerTalk.equals((String) clientNameHash.get(clientSocket))) {
                synchronized (clientDataHash) {
                    DataOutputStream peerOutData = (DataOutputStream) clientDataHash.get(userClient);
                    try {
                        peerOutData.writeUTF(talkMessage);
                    } catch (IOException es) {
                        es.printStackTrace();
                    }
                }
                Feedback(talkMessage);
                return (false);
            }
            // ����Ƿ����Լ��ģ�ֱ�ӻ���
            else if (peerTalk.equals((String) clientNameHash.get(clientSocket))) {
                Feedback(talkMessage);
                return (false);
            }
        }

        return (true);

    }

    /**
     * �˺���Ҳ����ѡ������Ϣ�������ܷ��͸��Լ���
     */
    public boolean chessPeerTalk(String chessPeerTalk, String chessTalkMessage) {

        for (Enumeration enu = clientDataHash.keys(); enu.hasMoreElements();) {
            Socket userClient = (Socket) enu.nextElement();

            if (chessPeerTalk.equals((String) clientNameHash.get(userClient))
                    && !chessPeerTalk.equals((String) clientNameHash.get(clientSocket))) {
                synchronized (clientDataHash) {
                    DataOutputStream peerOutData = (DataOutputStream) clientDataHash.get(userClient);
                    try {
                        peerOutData.writeUTF(chessTalkMessage);
                    } catch (IOException es) {
                        es.printStackTrace();
                    }
                }
                return (false);
            }
        }
        return (true);
    }

    /**
     * ���ڴ�����Ϣ�����ĺ���
     */
    public void Feedback(String feedbackString) {
        synchronized (clientDataHash) {
            //�õ������������
            DataOutputStream outData = (DataOutputStream) clientDataHash.get(clientSocket);
            try {
                //�����Ϣ������
                outData.writeUTF(feedbackString);
            } catch (Exception eb) {
                //��ӡ��ջ�켣��������ֹ����
                eb.printStackTrace();
            }
        }
    }

    /**
     * ��ȡ�û��б�ĺ������˺�����ȡclientNameHash��ȡ�û��б� Ȼ���䱣����һ���ַ���userList�С�
     */
    public String getUserList() {
        String userList = "/userlist";
        //����hashtable�е�values���Կո�ָ���������
        for (Enumeration enu = clientNameHash.elements(); enu.hasMoreElements();) {
            userList = userList + " " + (String) enu.nextElement();
        }
        return userList;
    }

    /**
     * ����HashTable��ֵ���󣬻�ȡ���Ӧ�ü�ֵ�ĺ�����
     */
    public Object getHashKey(Hashtable targetHash, Object hashValue) {
        Object hashKey;
        for (Enumeration enu = targetHash.keys(); enu.hasMoreElements();) {
            hashKey = (Object) enu.nextElement();
            //�������Ӧ��ֵ��hashValue���,�򷵻����key
            if (hashValue.equals((Object) targetHash.get(hashKey)))
                return (hashKey);
        }
        return (null);
    }

    public void firstCome() {
        //���û���һ�μ��롢�㲥�û��б���
        publicTalk(getUserList());
        //����������û������޸���Ӧ��ʾ��Ϣ����
        Feedback("/yourname " + (String) clientNameHash.get(clientSocket));
        Feedback("Java����������ͻ���");
        Feedback("/changename <�������> --��������");
        Feedback("/list --�����û��б�");
        Feedback("/<�û���> <Ҫ˵�Ļ�> --˽��");
        Feedback("ע�⣺�������ʱ���Ȱ�̸���Ķ���Ϊ������");
    }

    /**
     * ���ںͿͻ��˶Ͽ��ĺ�����
     */
    public void clientClose() {
        server.messageBoard.append("�û��Ͽ�:" + clientSocket + "\n");
        // �������Ϸ�ͻ�������
        synchronized (chessPeerHash) {
            if (chessPeerHash.containsKey(clientNameHash.get(clientSocket))) {
                chessPeerHash.remove((String) clientNameHash.get(clientSocket));
            }
            if (chessPeerHash.containsValue(clientNameHash.get(clientSocket))) {
                chessPeerHash.put((String) getHashKey(chessPeerHash, (String) clientNameHash.get(clientSocket)),
                        "tobeclosed");
            }
        }
        // ��������HashTable����������
        synchronized (clientDataHash) {
            clientDataHash.remove(clientSocket);
        }
        synchronized (clientNameHash) {
            clientNameHash.remove(clientSocket);
        }
        //�㲥�����û��б�����
        publicTalk(getUserList());
        // ���㵱ǰ������������ʾ��״̬����
        server.statusLabel.setText("��ǰ������:" + clientDataHash.size());
        try {
            clientSocket.close();
        } catch (IOException exx) {
        }
        isClientClosed = true;
    }

    public void run() {
        DataInputStream inData;
        synchronized (clientDataHash) {
            server.statusLabel.setText("��ǰ������:" + clientDataHash.size());
        }
        try {
            inData = new DataInputStream(clientSocket.getInputStream());
            firstCome();
            while (true) {
                String message = inData.readUTF();
                messageTransfer(message);
            }
        } catch (IOException esx) {
        } finally {
            if (!isClientClosed) {
                clientClose();
            }
        }
    }

}

/**
 * @author wufenghanren �������˿����
 */
public class chessServer extends Frame implements ActionListener {

    Button messageClearButton = new Button("�����ʾ");

    Button serverStatusButton = new Button("������״̬");

    Button serverOffButton = new Button("�رշ�����");

    Panel buttonPanel = new Panel();

    MessageServerPanel server = new MessageServerPanel();

    ServerSocket serverSocket;

    Hashtable clientDataHash = new Hashtable(50);

    Hashtable clientNameHash = new Hashtable(50);

    Hashtable chessPeerHash = new Hashtable(50);

    /**
     * �����Ĺ��캯��
     */
    chessServer() {
        super("Java�����������");
        setBackground(new Color(204, 204, 204));

        buttonPanel.setLayout(new FlowLayout());
        messageClearButton.setSize(60, 25);
        buttonPanel.add(messageClearButton);
        messageClearButton.addActionListener(this);
        serverStatusButton.setSize(75, 25);
        buttonPanel.add(serverStatusButton);
        serverStatusButton.addActionListener(this);
        serverOffButton.setSize(75, 25);
        buttonPanel.add(serverOffButton);
        serverOffButton.addActionListener(this);

        add(server, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        // �˳����ڵļ�����
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        setSize(400, 450);
        setResizable(false);
        validate();
        try {
            makeMessageServer(4331, server);
        } catch (Exception e) {
            System.out.println("e");
        }
    }

    /**
     * ��ʼ����Ϣ����������
     */
    public void makeMessageServer(int port, MessageServerPanel server) throws IOException {
        Socket clientSocket;
        long clientAccessNumber = 1;
        this.server = server;

        try {
            // �����������������Ϣ
            serverSocket = new ServerSocket(port);
            server.messageBoard.setText("��������ʼ��:" + serverSocket.getInetAddress().getLocalHost() + ":"
                    + serverSocket.getLocalPort() + "\n");

            while (true) {
                clientSocket = serverSocket.accept();
                server.messageBoard.append("�û�����:" + clientSocket + "\n");
                DataOutputStream outData = new DataOutputStream(clientSocket.getOutputStream());
                //���������������Hashtable���ڶ�Ӧ�߳����õ�����
                clientDataHash.put(clientSocket, outData);
                clientNameHash.put(clientSocket, ("���" + clientAccessNumber++));

                //ÿ����һ���û�������һ���̣߳��������񡣡�
                ServerThread thread = new ServerThread(clientSocket, clientDataHash, clientNameHash, chessPeerHash,
                        server);
                thread.start();
            }
        } catch (IOException ex) {
            System.out.println("�Ѿ��з�����������. \n");
        }
    }

    /**
     * ��ť���¼�����������Ӧ��ť����¼�
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == messageClearButton) {
            server.messageBoard.setText("");
        }
        // ����������״̬����ť���ʱ����ʾ������״̬
        if (e.getSource() == serverStatusButton) {
            try {//��ʾ��������Ϣ
                server.messageBoard.append("��������Ϣ:" + serverSocket.getInetAddress().getLocalHost() + ":"
                        + serverSocket.getLocalPort() + "\n");
            } catch (Exception ee) {
                System.out.println("serverSocket.getInetAddress().getLocalHost() error \n");
            }
        }
        if (e.getSource() == serverOffButton) {//�رշ��񡣡�
            System.exit(0);
        }
    }

    public static void main(String args[]) {//������������������������
        chessServer chessServer = new chessServer();
    }
}