/**
 * һ���ͻ����û��߳�. �����û����ӵĵ���¼�����,����Ϣ����.
 */
package chessface;

import java.io.IOException;
import java.util.StringTokenizer;

public class chessThread extends Thread {
    chessPad chesspad;

    public chessThread(chessPad chesspad) {
        this.chesspad = chesspad;
    }

    /**
     * ������Ϣ
     */
    public void sendMessage(String sndMessage) {
        try {
            // ������socket�ṩ��������������������Ϣ
            chesspad.outData.writeUTF(sndMessage);
        } catch (Exception ea) {
            System.out.println("chessThread.sendMessage:" + ea);
        }
    }

    /**
     * ������Ϣ,�������������Ϣ,��������λ��,��ɫ��.
     */
    public void acceptMessage(String recMessage) {
        // ����յ�����Ϣ�ԡ�/chess����ͷ�������е�������Ϣ����ɫ��Ϣ��ȡ����
        if (recMessage.startsWith("/chess ")) {
            StringTokenizer userToken = new StringTokenizer(recMessage, " ");
            String chessToken;
            String[] chessOpt = {"-1", "-1", "0"};
            int chessOptNum = 0;
            // ʹ��Tokenizer���ո�ָ����ַ����ֳ�����
            while (userToken.hasMoreTokens()) {
                chessToken = (String) userToken.nextToken(" ");
                if (chessOptNum >= 1 && chessOptNum <= 3) {
                    chessOpt[chessOptNum - 1] = chessToken;
                }
                chessOptNum++;
            }
            // ��������������Ϣ�����Ӱڷŵ�λ�á����ӵ���ɫΪ������ʹ��netChessPaint����
            // �ǶԷ��ͻ���Ҳ��������������λ�á�
            chesspad.netChessPaint(Integer.parseInt(chessOpt[0]), Integer.parseInt(chessOpt[1]), Integer
                    .parseInt(chessOpt[2]));

        } else if (recMessage.startsWith("/yourname ")) {
            chesspad.chessSelfName = recMessage.substring(10);
        } else if (recMessage.equals("/error")) {
            chesspad.statusText.setText("����:û������û������˳��������¼���");
        }
    }

    public void run() {
        String message = "";
        try {
            while (true) {
                //֮����Ҫ���̡߳�����Ϊ����socket��ȡ��������������Ϣ����������
                message = chesspad.inData.readUTF();
                //����������Ϣ
                acceptMessage(message);
            }
        } catch (IOException es) {
            //��ȡ�쳣..���߿������������Ӧ����,�����������������Ӧ����Ϣ��ʾ��,
            //�Ӷ�����߳������пɿ���
        }
    }

}
