package client;
import java.net.*;
import java.io.*;

public class MultiCast{

InetAddress inet;
private InetAddress ser_inet = null;
protected DatagramSocket socket;

     public MultiCast(String msg)
     {
       try{
            socket=new DatagramSocket(5000);
            }catch(SocketException e){
            e.printStackTrace();
            }
            try{
            inet = InetAddress.getLocalHost();
            byte[] buf=new byte[7];
            //����������
            //String dString="request";
            buf=msg.getBytes();
            InetAddress group=InetAddress.getByName("230.0.0.1");
            //�õ�230.0.0.1�ĵ�ַ��Ϣ
            DatagramPacket packet=new DatagramPacket(buf,buf.length,group,5004);
            //���ݻ��������㲥��ַ���Ͷ˿ںŴ���DatagramPacket����
            socket.send(packet); //���͸�Packet
            packet = new DatagramPacket(buf,buf.length);
            //���ջظ���Ϣ
            socket.setSoTimeout(1000);
            try{
            socket.receive(packet);
            String received = new String(packet.getData());
            //���ݻظ���Ϣ�õ���������ַ���˿�
            if(received.equals("connect"))
                {
                ser_inet = packet.getAddress();
                NetState.b_connect = true;
                }
            if(received.equals("cutlink"))
                {
                NetState.b_connect = false;
                }

            }catch(SocketTimeoutException e)
            {
               e.printStackTrace();
               socket.close();
            }


         }catch(IOException  e){ //�쳣����
                         e.printStackTrace( ); //��ӡ����ջ
                        }
        socket.close();
     }
     public InetAddress  GetServerAddr()
     {
      return ser_inet;
     }

}