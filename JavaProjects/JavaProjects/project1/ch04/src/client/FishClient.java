package client;

/**
 *<p>
 * * Copyright (C),200
 * by huanghao
 * Desctiptoin: ����ͻ���UDP�㲥����
 * </p>
 *
 */
import java.io.*;
import java.net.*;
import java.util.*;
import fishinfo.FishInfo;

public class FishClient{

       InetAddress inet;
       InetAddress ser_inet;
       FishPanel  fishpanel;
       MultiCast cast = null;

     public FishClient(FishPanel fishpanel) throws IOException
     {
       this.fishpanel = fishpanel;
       //����ҵ��������ˣ��������ݽ����߳�
       cast = new MultiCast("request");

       if(NetState.b_connect)
       {
          this.ser_inet = cast.GetServerAddr();
          new Receiver(fishpanel);
          System.out.println("linked to the server:"+ser_inet.getHostAddress());

       }
      }
      public void send(FishInfo info)
      {
         if(ser_inet != null)
         new Sender(info,ser_inet);
      }
      public void  Disconnect()
      {
         MultiCast cast = new MultiCast("cutlink");
      }
}