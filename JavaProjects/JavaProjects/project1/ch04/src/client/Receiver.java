package client;
import java.net.*;
import java.util.*;
import java.net.*;
import java.io.*;
import fishinfo.FishInfo;

public class Receiver extends Thread{

       private DatagramPacket packet;
       private DatagramSocket socket;
       byte[] recvBuf = new byte[500];
       FishInfo  fishinfo;
       FishPanel  fishpanel;
       boolean running = true;

       public Receiver(FishPanel  fishpanel){
       super();

       try{
       socket = new DatagramSocket(5001);
       }catch(SocketException e)
       {e.printStackTrace();}

       this.fishpanel = fishpanel;

       start();
       }
       public void run(){
       while(running)
       {
       try{
       //����udp���Խ������ݡ�

       packet = new DatagramPacket(recvBuf,recvBuf.length);
        socket.receive(packet);
        ByteArrayInputStream byteStream = new
                           ByteArrayInputStream(recvBuf);
        ObjectInputStream is = new ObjectInputStream(new
                           BufferedInputStream(byteStream));
        Object o = is.readObject();
        fishinfo = (FishInfo)o;
        fishpanel.addFish(fishinfo); //�����յ���'����'���������
       is.close();
       //��������࣬�����յ�����Ϣ�������
       }
         catch(Throwable t) {
            t.printStackTrace();
       }
       }
       }
}