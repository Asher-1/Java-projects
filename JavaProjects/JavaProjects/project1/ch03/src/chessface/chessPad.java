package chessface;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * ��ʾ���̵�Panel����Panelʵ������������
 */
public class chessPad extends Panel implements MouseListener {
    public int chessPoint_x = -1, chessPoint_y = -1, chessColor = 1;

    int chessBlack_x[] = new int[200];// ���ӵ�x����

    int chessBlack_y[] = new int[200];// ���ӵ�y����

    int chessWhite_x[] = new int[200];// ���ӵ�x����

    int chessWhite_y[] = new int[200];// ���ӵ�y����

    int chessBlackCount = 0, chessWhiteCount = 0;

    int chessBlackWin = 0, chessWhiteWin = 0;

    public boolean isMouseEnabled = false, isWin = false, isInGame = false;

    public Label statusLabel = new Label("�ͻ���״̬");

    public TextField statusText = new TextField("�������ӷ�����");// ��ʾ�ͻ���״̬���ı���

    public Socket chessSocket;

    DataInputStream inData;

    DataOutputStream outData;

    public String chessSelfName = null;// ����������

    public String chessPeerName = null;// �Է�������

    public String host = null; //������ip��ַ

    public int port = 4331; //���ӵĶ˿ں�

    public chessThread chessthread = new chessThread(this);

    /**
     * ����Panel�Ĺ��캯��
     */
    public chessPad() {
        // ���ô�С
        setSize(440, 440);
        // ���ò���
        setLayout(null);
        // ������ɫ
        setBackground(new Color(204, 204, 204));
        // �����������
        addMouseListener(this);
        // ���״̬Label
        add(statusLabel);
        // ����״̬Label��С
        statusLabel.setBounds(30, 5, 70, 24);
        // ��ʾ�ͻ���״̬���ı���
        add(statusText);
        statusText.setBounds(100, 5, 300, 24);
        // ����Ϊ���ɱ༭
        statusText.setEditable(false);
    }

    /**
     * �ͷ�����ͨ�ŵĺ���
     */
    public boolean connectServer(String ServerIP, int ServerPort) throws Exception {
        try {
            // ���ò�������һ��Socket��ʵ������ɺͷ�����֮�����Ϣ����
            // ���ӷ�����.
            chessSocket = new Socket(ServerIP, ServerPort);
            inData = new DataInputStream(chessSocket.getInputStream());
            outData = new DataOutputStream(chessSocket.getOutputStream());
            // ����һ���û��߳�
            chessthread.start();
            return true;
        } catch (IOException ex) {
            statusText.setText("chessPad:connectServer:�޷����� \n");
        }
        return false;
    }

    /**
     * һ����ʤʱ�Ķ���ֵĴ���
     */
    public void chessVictory(int chessColorWin) {
        // ������е�����
        this.removeAll();
        // ���������к���Ͱ����λ�������������գ�Ϊ��һ������׼����
        for (int i = 0; i <= chessBlackCount; i++) {
            chessBlack_x[i] = 0;
            chessBlack_y[i] = 0;
        }
        for (int i = 0; i <= chessWhiteCount; i++) {
            chessWhite_x[i] = 0;
            chessWhite_y[i] = 0;
        }
        // ���塢�����������
        chessBlackCount = 0;
        chessWhiteCount = 0;
        // �������״̬��Ϣ
        add(statusText);
        statusText.setBounds(40, 5, 360, 24);
        // ��������ʤ������˫����ʤ��������˫����ս������״̬�ı�����ʾ������
        if (chessColorWin == 1) {
            chessBlackWin++;// �����ʤ����+1
            statusText.setText("����ʤ,��:��Ϊ" + chessBlackWin + ":" + chessWhiteWin + ",���¿���,�ȴ���������...");
        }
        // �����ʤ��ͬ�ϡ�
        else if (chessColorWin == -1) {
            chessWhiteWin++;
            statusText.setText("����ʤ,��:��Ϊ" + chessBlackWin + ":" + chessWhiteWin + ",���¿���,�ȴ���������...");
        }
    }

    /**
     * ���������ӵ����걣����������
     */
    public void getLocation(int a, int b, int color) {

        if (color == 1) {// ����
            chessBlack_x[chessBlackCount] = a * 20;
            chessBlack_y[chessBlackCount] = b * 20;
            chessBlackCount++;
        } else if (color == -1) {// ����
            chessWhite_x[chessWhiteCount] = a * 20;
            chessWhite_y[chessWhiteCount] = b * 20;
            chessWhiteCount++;
        }
    }

    /**
     * �������������������ж�ĳ����ʤ����������жϺᡢ������б����б�����ض���ɫ���������ӵ������Ƿ񳬹�5�����������5����ΪӮ��
     * ��ÿ��������ж��ַ�Ϊ�����򡢺ͷ���ֱ�ͳ�Ƽ�����
     */
    public boolean checkWin(int a, int b, int checkColor) {
        int step = 1, chessLink = 1, chessLinkTest = 1, chessCompare = 0;
        if (checkColor == 1) {// ���巽
            chessLink = 1;// ���Ӹ���
            //////////////////////////////
            // �����������ѭ���ж���ֱ����������������
            //////////////////////////////
            // ���µķ������Ӹ���
            for (step = 1; step <= 4; step++) {
                // �²�ѭ���ж���ֱ���µ�step��λ���Ƿ��а���
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    // ����к���
                    if (((a + step) * 20 == chessBlack_x[chessCompare]) && ((b * 20) == chessBlack_y[chessCompare])) {
                        chessLink = chessLink + 1;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))// ��ֱ���»��к�ɫ����
                    chessLinkTest++;
                else
                    break;
            }
            //���ϵ����Ӹ���
            for (step = 1; step <= 4; step++) {
                //�²�ѭ���ж���ֱ���ϵ�step��λ���Ƿ��к���
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    //����к��ӣ���ʵ�������break������
                    if (((a - step) * 20 == chessBlack_x[chessCompare]) && (b * 20 == chessBlack_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                //����¸�λ�û��к�ɫ���ӣ����Ի��ñ�־���жϣ�
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else //���û�У�����ֹ���˳�ѭ��
                    break;
            }
            ///////////////////////////////
            //�����������ѭ��ͳ�ƺ�������Ӹ���
            ///////////////////////////////
            chessLink = 1;
            chessLinkTest = 1;
           //�����ұߵ�������ͳ��
            for (step = 1; step <= 4; step++) {
                //�Ҳ��step��λ���Ƿ���ͬɫ����
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    //����еĻ������߿���˼��������ɷ�break������
                    if ((a * 20 == chessBlack_x[chessCompare]) && ((b + step) * 20 == chessBlack_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))//�¸�λ���Ƿ��ҵ�����
                    chessLinkTest++;
                else
                    break;
            }
            //��������������ͳ��
            for (step = 1; step <= 4; step++) {
                //����step��λ���Ƿ���ͬɫ����
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    //����ҵ�ͬɫ����
                    if ((a * 20 == chessBlack_x[chessCompare]) && ((b - step) * 20 == chessBlack_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }

            ///////////////////////////////
            //�����������ѭ��ͳ����б��������Ӹ���
            ///////////////////////////////
            chessLink = 1;
            chessLinkTest = 1;
            //����ͳ�ƺ���������������Ӹ���һ������б����Ҳ����������������ͳ��
            //Ȼ���ۼ���������������Ӹ������õ�������������Ӹ���
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    if (((a - step) * 20 == chessBlack_x[chessCompare])
                            && ((b + step) * 20 == chessBlack_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    if (((a + step) * 20 == chessBlack_x[chessCompare])
                            && ((b - step) * 20 == chessBlack_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            //////////////////////////////////
            //�����������ѭ��ͳ����б��������Ӹ���
            /////////////////////////////////
            chessLink = 1;
            chessLinkTest = 1;
            //����ͳ�ƺ���������������Ӹ���һ������б����Ҳ����������������ͳ��
            //Ȼ���ۼ���������������Ӹ������õ�������������Ӹ���
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    if (((a + step) * 20 == chessBlack_x[chessCompare])
                            && ((b + step) * 20 == chessBlack_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessBlackCount; chessCompare++) {
                    if (((a - step) * 20 == chessBlack_x[chessCompare])
                            && ((b - step) * 20 == chessBlack_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
        } else if (checkColor == -1) {
            //����ǰ�ɫ���ӣ�ͬ��ɫ����һ�����Ժᡢ������б����б
            //��������ͳ�ƣ�������κ�һ���������Ӹ�������5������ΪӮ��
            //ֻ��������¸��ض�λ���Ƿ����ض���ɫ����ʱ��Ҫʹ�ð����Ӧ�������顣
            //����ͬ�Ժ�����ж�һ��һ��������ע�Ϳ��Բο��Ժ����жϲ��֡�
            ////////////////////////////////////
            //����ͳ��
            ////////////////////////////////////
            chessLink = 1;
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if (((a + step) * 20 == chessWhite_x[chessCompare]) && (b * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if (((a - step) * 20 == chessWhite_x[chessCompare]) && (b * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            ////////////////////////////////////
            //����ͳ��
            ////////////////////////////////////
            chessLink = 1;
            chessLinkTest = 1;
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if ((a * 20 == chessWhite_x[chessCompare]) && ((b + step) * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if ((a * 20 == chessWhite_x[chessCompare]) && ((b - step) * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            ////////////////////////////////////
            //��б����ͳ��
            ////////////////////////////////////
            chessLink = 1;
            chessLinkTest = 1;
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if (((a - step) * 20 == chessWhite_x[chessCompare])
                            && ((b + step) * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if (((a + step) * 20 == chessWhite_x[chessCompare])
                            && ((b - step) * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            ////////////////////////////////////
            //��б����ͳ��
            ////////////////////////////////////
            chessLink = 1;
            chessLinkTest = 1;
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if (((a + step) * 20 == chessWhite_x[chessCompare])
                            && ((b + step) * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
            for (step = 1; step <= 4; step++) {
                for (chessCompare = 0; chessCompare <= chessWhiteCount; chessCompare++) {
                    if (((a - step) * 20 == chessWhite_x[chessCompare])
                            && ((b - step) * 20 == chessWhite_y[chessCompare])) {
                        chessLink++;
                        if (chessLink == 5) {
                            return (true);
                        }
                    }
                }
                if (chessLink == (chessLinkTest + 1))
                    chessLinkTest++;
                else
                    break;
            }
        }
        //����κη���û�����ӳ���5�������в��ֳܷ�ʤ��
        return (false);
    }

    /**
     * �������̣������̻��Ƴ�19*19�ĸ��Ӳ���������Ӧ�е������׼�������ȥ��
     */
    public void paint(Graphics g) {
        //���̵ķ�����
        for (int i = 40; i <= 380; i = i + 20) {
            g.drawLine(40, i, 400, i);
        }
        g.drawLine(40, 400, 400, 400);
        for (int j = 40; j <= 380; j = j + 20) {
            g.drawLine(j, 40, j, 400);
        }
        g.drawLine(400, 40, 400, 400);
        //�����׼��
        g.fillOval(97, 97, 6, 6);
        g.fillOval(337, 97, 6, 6);
        g.fillOval(97, 337, 6, 6);
        g.fillOval(337, 337, 6, 6);
        g.fillOval(217, 217, 6, 6);
    }

    /**
     * ���ӵ�ʱ�����ض�λ�û����ض���ɫ������
     */
    public void chessPaint(int chessPoint_a, int chessPoint_b, int color) {
        chessPoint_black chesspoint_black = new chessPoint_black(this);
        chessPoint_white chesspoint_white = new chessPoint_white(this);

        if (color == 1 && isMouseEnabled) {
            // ����������ʱ�����´��ӵ�λ��
            getLocation(chessPoint_a, chessPoint_b, color);
            // �ж��Ƿ��ʤ
            isWin = checkWin(chessPoint_a, chessPoint_b, color);
            if (isWin == false) {
                // ���û�л�ʤ����Է�����������Ϣ������������
                chessthread.sendMessage("/" + chessPeerName + " /chess " + chessPoint_a + " " + chessPoint_b + " "
                        + color);
                this.add(chesspoint_black);
                chesspoint_black.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                // ��״̬�ı�����ʾ������Ϣ
                statusText.setText("��(��" + chessBlackCount + "��)" + chessPoint_a + " " + chessPoint_b + ",���������");
                isMouseEnabled = false;
            } else {
                // �����ʤ��ֱ�ӵ���chessVictory��ɺ�������
                chessthread.sendMessage("/" + chessPeerName + " /chess " + chessPoint_a + " " + chessPoint_b + " "
                        + color);
                this.add(chesspoint_black);
                chesspoint_black.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                chessVictory(1);
                isMouseEnabled = false;
            }
        }
        // �������ӣ�ͬ�������ƴ���
        else if (color == -1 && isMouseEnabled) {
            getLocation(chessPoint_a, chessPoint_b, color);
            isWin = checkWin(chessPoint_a, chessPoint_b, color);
            if (isWin == false) {
                chessthread.sendMessage("/" + chessPeerName + " /chess " + chessPoint_a + " " + chessPoint_b + " "
                        + color);
                this.add(chesspoint_white);
                chesspoint_white.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                statusText.setText("��(��" + chessWhiteCount + "��)" + chessPoint_a + " " + chessPoint_b + ",���������");
                isMouseEnabled = false;
            } else {
                chessthread.sendMessage("/" + chessPeerName + " /chess " + chessPoint_a + " " + chessPoint_b + " "
                        + color);
                this.add(chesspoint_white);
                chesspoint_white.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                chessVictory(-1);
                isMouseEnabled = false;
            }
        }
    }

    /**
     * ����ʱ�ڶԷ��ͻ��˻������ӡ� �Է����ܵ���������������Ϣ�����ô˺����������ӣ���ʾ����״̬�ȵ�
     */
    public void netChessPaint(int chessPoint_a, int chessPoint_b, int color) {
        //��ɫ���ӺͰ�ɫ���Ӷ���
        chessPoint_black chesspoint_black = new chessPoint_black(this);
        chessPoint_white chesspoint_white = new chessPoint_white(this);
        getLocation(chessPoint_a, chessPoint_b, color);
        if (color == 1) { //�����Ƿ�ʤ��
            isWin = checkWin(chessPoint_a, chessPoint_b, color);
            if (isWin == false) { //���û��ʤ��
                //����ɫ���Ӽӵ�����
                this.add(chesspoint_black);
                //����λ��
                chesspoint_black.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                //����״̬��Ϣ
                statusText.setText("��(��" + chessBlackCount + "��)" + chessPoint_a + " " + chessPoint_b + ",���������");
                //�����Ｄ�����
                isMouseEnabled = true;
            } else {//����Ѿ�ʤ��
                //�ڵ��λ����Ӻ�ɫ����
                this.add(chesspoint_black);
                chesspoint_black.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                //����ʤ��״̬---����������̵ȹ���
                chessVictory(1);
                isMouseEnabled = true;
            }
        } else if (color == -1) {//��ɫ����
            //��ɫ�����Ƿ�ʤ��
            isWin = checkWin(chessPoint_a, chessPoint_b, color);
            if (isWin == false) {//��ɫ����û��ʤ��
                //����ɫ������ӵ�Panel,���趨��ʾλ��
                this.add(chesspoint_white);
                chesspoint_white.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                //����״̬��Ϣ,�������
                statusText.setText("��(��" + chessWhiteCount + "��)" + chessPoint_a + " " + chessPoint_b + ",���������");
                isMouseEnabled = true;
            } else {//����ʤ��
                //����ʤ����Ϣ
                chessthread.sendMessage("/" + chessPeerName + " /victory " + color);
                //��Ӱ�ɫ����
                this.add(chesspoint_white);
                chesspoint_white.setBounds(chessPoint_a * 20 - 7, chessPoint_b * 20 - 7, 16, 16);
                chessVictory(-1);
                isMouseEnabled = true;
            }
        }
    }

    /**
     * ����갴��ʱ��Ӧ�Ķ��������µ�ǰ�������λ�ã�����ǰ���ӵ�λ�á�
     * ��������������,�����ض�λ�û�������.
     */
    public void mousePressed(MouseEvent e) {
        if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
            chessPoint_x = (int) e.getX();
            chessPoint_y = (int) e.getY();
            //��һ�������̷���λ��
            int a = (chessPoint_x + 10) / 20, b = (chessPoint_y + 10) / 20;
            //����������������,����...
            if (chessPoint_x / 20 < 2 || chessPoint_y / 20 < 2 || chessPoint_x / 20 > 19 || chessPoint_y / 20 > 19) {
            } else {
                //�����������ض�λ��....
                chessPaint(a, b, chessColor);
            }
        }
    }

    //����Ӧ���¼�
    public void mouseReleased(MouseEvent e) {
    }
    //����Ӧ���¼�
    public void mouseEntered(MouseEvent e) {
    }
    //����Ӧ���¼�
    public void mouseExited(MouseEvent e) {
    }
    //����Ӧ���¼�
    public void mouseClicked(MouseEvent e) {
    }

}

/**
 * ��ʾ���ӵ���
 */

class chessPoint_black extends Canvas {
    chessPad chesspad = null;

    chessPoint_black(chessPad p) {
        setSize(20, 20);
        chesspad = p;
    }
   //����һ����ɫ���ӡ�����
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(0, 0, 14, 14);
    }

}

/**
 * ��ʾ���ӵ���
 */

class chessPoint_white extends Canvas {
    chessPad chesspad = null;

    chessPoint_white(chessPad p) {
        setSize(20, 20);
        chesspad = p;
    }
    //����һ����ɫ���ӡ���������
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(0, 0, 14, 14);
    }

}
