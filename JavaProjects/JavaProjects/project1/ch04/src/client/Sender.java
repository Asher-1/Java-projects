package client;

import java.net.*;
import java.net.*;
import java.io.*;
import fishinfo.FishInfo;

public class Sender{

       //private DatagramSocket socket;
       //private DatagramPacket packet;
       InetAddress ser_inet;
       FishInfo info;

       public Sender(FishInfo  info,InetAddress ser_inet){
          this.ser_inet = ser_inet;
          this.info = info;
          send();
       }
       private synchronized void  send()
       {
       try {
            DatagramSocket socket;
            DatagramPacket packet;
          ByteArrayOutputStream byteStream = new ByteArrayOutputStream(500);
            ObjectOutputStream os = new ObjectOutputStream(new
                                    BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(info);
            os.flush();
            byte[] sendBuf = byteStream.toByteArray();

           //����udp���ݰ��Է�������
           System.out.println("send data to:"+ser_inet.getHostAddress());
           packet = new  DatagramPacket(sendBuf,sendBuf.length,
                                        ser_inet,5003);
           //��������udp�׽��֣�ָ���׽��ֿڡ�
           //�󶨶˿�5002������ͨ������
           //����ö˿��Ѱ󶨣���ȴ�Ƭ�̺����°�
           while(true)
           {
           try{
           socket = new DatagramSocket(5002);
           break;
           }
           catch(BindException be)
           {
             Thread.sleep(20);
             continue;
           }
           }
           os.close();
           //�󶨳ɹ��󣬷���ͨͨ������
           socket.send(packet);
           //������Ϲر��׽��֡�
           socket.close();
           }catch(Throwable t) {
            t.printStackTrace();
        }
       }
}