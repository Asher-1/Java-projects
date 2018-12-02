package client;

import java.awt.*;
 import javax.swing.*;
 import java.awt.event.*;
 import java.util.LinkedList;
 import java.io.IOException;
 import fishinfo.FishInfo;

public class FishPanel extends JPanel implements MouseListener
 {
 	private Image Background;		//����ͼƬ
 	private Image imageFish[] = new Image[8];	//8����ͼƬ
 	private int fishTotal = 8;			//�������Ϊ8��
 	//Fish fish[] = new Fish[fishTotal];	//8����,��8���߳�
    LinkedList fish_list = new LinkedList();
  	private boolean k = false;		//��־,��Ӧ����¼����õ�
  	public FishClient  client = null;

 	public FishPanel()
 	{
 		setSize(600, 468);
 		addMouseListener(this);
 		//Background = Toolkit.getDefaultToolkit().getImage("./Pics/sea.jpg");
        Background = Toolkit.getDefaultToolkit().getImage("./pics/sea2.jpg");
 		for(int i=0; i<8; i++)
                imageFish[i]= Toolkit.getDefaultToolkit().getImage("./pics/fish0"+i+".gif");

 		int x,		//���x����
 		    y,		//���y����
 		    width=64,	//��ͼƬ�Ŀ��(����ͼƬ��ȶ�Ϊ64)
 		    height = 0;	//��ͼƬ�Ŀ��(��ͼƬ�߶Ȳ�һ��)
 		for(int i=0; i<fishTotal; i++)
 		{//��ʼ��ÿ���㣨�̣߳���״̬
 			switch(i/2)
 			{//��ͬ�����Ӧ��ͼƬ�߶Ȳ�һ��
 				case 0: height = 56; break;		//0-1����ͼƬ�߶�Ϊ56
 				case 1: case 2: height = 53; break;	//2-5����ͼƬ�߶�Ϊ53
 				case 3: height = 37; break;		//6-7����ͼƬ�߶�Ϊ37
 			}

 			//����������ʼ���ֵ�λ��
 			x = (int)(Math.random()*(600 - width));
 			y = (int)(Math.random()*(468 - height));

 			int down = (int)(Math.random()*10)%2;	//down=1 : ������
 	        int right= (int)(Math.random()*10)%2;	//right=1: ������

 			//�߳�ʵ����
 			Fish fish = new Fish(this,x,y,width,height,i,down,right);
 			fish.id = i; //Ϊ�ö��������룬�����Ժ�ɾ��
 			fish_list.add(fish);
 		}
 	}

 	public void initClient()
 	{
 	   try{
           client = new FishClient(this);
           }catch(IOException ie){ie.printStackTrace();}
 	}
 	public void endClient()
 	{
 	  client.Disconnect();
 	   client = null;
 	}
 	public void delFish(int id)
 	{
       int new_x;
       Fish fish = (Fish)fish_list.get(id);
       //����Ҫɾ���Ķ�����Ϣ
       if(fish.x <= 0)
           new_x = this.getWidth() - fish.width;
       else
           new_x = 0;
       FishInfo fishinfo = new FishInfo(new_x,fish.y,fish.width,
                           fish.height,fish.i,fish.down,fish.right);
 	   fish.end = true; //�����ö����߳�
       fish_list.remove(id); //��������ɾ���˶���
       for(int i=id;i<fish_list.size();i++)
       {
        fish = (Fish)fish_list.get(i);
        fish.id = i;
       }
       //���������γ�ȥ�������Ϣ���͵�������
       client.send(fishinfo);
 	}
 	public void addFish(FishInfo fishinfo)
 	{
 	    Fish fish = new Fish(this,fishinfo.x,fishinfo.y,fishinfo.width,
 	                        fishinfo.height,fishinfo.i,fishinfo.down,fishinfo.right);
 	    fish_list.add(fish);
 	}

  	public void mouseClicked(MouseEvent e)
	{//��Ӧ������¼�
	/*
		if(k)
		{
			k = !k;
			for(int i=0; i<fishTotal; i++)
			{
				fish[i] = new Fish(this,fish[i].x,fish[i].y,fish[i].width,fish[i].height,i);
			}
   		}
		else
		{
			k = !k;
			for(int i=0; i<fishTotal; i++)
				fish[i].halt();
		} */
	}
  	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
  	public void mousePressed(MouseEvent e){}
  	public void mouseReleased(MouseEvent e){}

 	public void paintComponent(Graphics g)
 	{
 		super.paintComponent(g);
 		//g.drawImage(Background,0,0,getWidth(),getHeight(),this);
 		g.drawImage(Background,0,0,getWidth(),getHeight(),this);
 		try
 		{//���ر���ͼƬ��,�ȴ�30΢��
 			Thread.sleep(30);
 		}catch(InterruptedException e){}
 		for(int i=0; i<fish_list.size(); i++)
 		{
 			Fish fish;
 			fish = (Fish)fish_list.get(i);
             //���������ζ�ʱ,��ʾ��ͼƬ��ͬ
 			if(fish.right == 1)
 			    g.drawImage(imageFish[(fish.i/2)*2],fish.x,fish.y,this);//��־right=1��ʾ�����Σ�����
 			else
 			    g.drawImage(imageFish[(fish.i/2)*2+1],fish.x,fish.y,this);
 		}
 	}

}