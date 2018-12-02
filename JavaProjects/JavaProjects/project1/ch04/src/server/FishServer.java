package server;

/**
 *<p>
 * * Copyright (C),2007
 * by huanghao
 * Desctiptoin: ���������������
 * </p>
 *
 */
import java.io.*;
import java.net.*;
import java.util.*;
public class FishServer {

       Transmitter  transmit;
       public FishServer()
       {
        transmit = new Transmitter();
        //����4446�˿ڵĹ㲥�׽���
        try{
            MulticastSocket socket=new MulticastSocket(5004);
            InetAddress address;
            //�ͻ���Ϣ�б�
            //�õ�230.0.0.1�ĵ�ַ��Ϣ
            try{
                address=InetAddress.getByName("230.0.0.1");
                //ʹ��joinGroup()���ಥ�׽��ְ󶨵���ַ��
                socket.joinGroup(address);
                }catch(UnknownHostException e)
                {e.printStackTrace();}

            DatagramPacket packet;
            boolean bn = true;
             System.out.println("begin recieve message");
             while(bn){
                  byte[] buf=new byte[7];
                  int i;
                  /***********************************************
                  * �������ڿͻ��˵�����������Ϣ
                  **********************************************/
                  packet=new DatagramPacket(buf,buf.length);
                  socket.receive(packet); //����
                  String received = new String(packet.getData());
                  InetAddress inet =   packet.getAddress();
                  String addr = inet.getHostAddress();
                  int port = packet.getPort();

                    for(i = 0;i < ClientList.list.size();i++)
                     {
                       ClientInfo clientinfo = (ClientInfo)ClientList.list.get(i);
                       if(clientinfo.address.equals(addr))
                         //System.out.println("same to :"+i);
                         break;
                     }
                  if(received.equals("request"))
                  {

                     //����ÿͻ��˻����ڿͻ���Ϣ���У�������������Ϣ��
                     if(i >= ClientList.list.size())
                     {
                        ClientInfo client_info = new ClientInfo(addr,port,new Date().toString());
                        ClientList.list.add(client_info);
                        System.out.println("link apply from:"+addr+":"+port);
                        /***********************************************
                        * ��ͻ��˻�ִ��Ϣ
                        **********************************************/
                        String msg = "connect";
                        buf = msg.getBytes();
                        packet = new DatagramPacket(buf,buf.length,inet,port);
                        socket.send(packet);
                     }
                  }
                  else if(received.equals("cutlink"))
                  {
                     if(i < ClientList.list.size())
                     {
                       ClientList.list.remove(i);
                        System.out.println("\""+received+"\" message  from:"+addr+":"+port);
                        /***********************************************
                        * ��ͻ��˻�ִ��Ϣ
                        **********************************************/
                        String msg = "cutlink";
                        buf = msg.getBytes();
                        packet = new DatagramPacket(buf,buf.length,inet,port);
                        socket.send(packet);
                     }
                  }
                  System.out.println("client list now:");
                  for(i = 0;i < ClientList.list.size();i++)
                  {
                     ClientInfo clientinfo = (ClientInfo)ClientList.list.get(i);
                     System.out.println("address:"+clientinfo.address+" port:"+clientinfo.port);
                  }
                }
        //�ѹ㲥�׽��ִӵ�ַ�Ͻ����
        socket.close(); //�رչ㲥�׽���
        }catch(IOException e)
        {e.printStackTrace();}
        }
   public static void main(String args[]) throws IOException
   {
     new FishServer();
     }
}