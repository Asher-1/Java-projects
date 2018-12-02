package client;
/**
 * <p>
 * Copyright (C),2006
 * Originally created �ųɷ�
 * Revised by huanghao
 *
 * Desctiptoin:   ��Ҫ��������ѭ������
 * Functoin List:
 *          <Name>             <Desc>
 * 1��      ���췽��          �߳�����
 * 2��       run               �߳�����
 * 3��       main         ����ʵ��������������
 * </p>
 *
 * @version 1.00
 * @see     Sprite
 */
import java.io.FileInputStream;
import sun.audio.AudioStream;
public class MusicPlay extends Thread{
 FileInputStream music;//�����ļ�������
 private static final int SleepTime = 1500;
 int i;
 public boolean  play = true;

 AudioStream play_music;//������Ƶ������
 /*
  *���췽�����߳�����
  */
 MusicPlay(){
  this.start();
 }

 /*
  *�߳�����
  */
 public void run(){

  //ѭ������
  while(play){
   try{
    i = (int)Math.floor(Math.random()*2);
    music=new FileInputStream("./sound/bubble"+i+".wav");//�����ļ�������
    //music=new FileInputStream("../windmusic.wav");
    play_music=new sun.audio.AudioStream(music);//������Ƶ������
   }catch(Exception e){System.out.println(e);}

   sun.audio.AudioPlayer.player.start(play_music);//��ʼ����
   try{
    if(i == 0)
    Thread.sleep(6000);//��Ƶ����ʱ��
    else
    Thread.sleep(3000);
   }catch(Exception e){System.out.println(e);}
  }
  return;
 }
 /*
  *���������С�
  */
/* public static void main(String[] args){
  new MusicPlay();
 } */
}