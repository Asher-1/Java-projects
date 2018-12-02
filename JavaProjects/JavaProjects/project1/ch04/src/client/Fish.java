package client;
class Fish extends Thread
 {
 	 int id;
     int x,		  //�����x����
 	    y,		  //�����y����
 	    width,	  //����ʹ�õ�ͼƬ�Ŀ��
 	    height;	  //����ʹ�õ�ͼƬ�ĸ߶�

	int space_mul  = 40,  //����vֵ��һ������
	    space_plus = 10,  //����vֵ��һ������
	    lean_mul  = 10,  //����lֵ��һ������
	    x1_mul = 1800,//����x1ֵ��һ������
	    y1_mul = 800; //����y1ֵ��һ������

 	//����������ʼ�εķ���
 	//int down = (int)(Math.random()*10)%2;	//down=1 : ������
 	//int right= (int)(Math.random()*10)%2;	//right=1: ������
 	int down,
 	    right;
 	int space = (int)(Math.random()*space_mul)+space_plus;//space��������ˢ������,spaceԽС,����ˢ��֮���ʱ������ԽС,�Ӷ��ٶȾ�Խ��
 	int lean_angle = (int)(Math.random()*lean_mul);	//�������б�νǶȱ�x:y=lean_angle,����x�������ζ�l��֮�����y�������ƶ�һ��

	//������������������ζ���������ޣ������
 	int x1 = (int)(Math.random()*x1_mul);   //ֵ��0~1800֮��
 	int y1 = (int)(Math.random()*y1_mul);    // 0~800

 	int i,	//��ı�־����i����(�������й���8����)
 	    count_x1 = 0,//x�������������
 	    count_y1 = 0;//y�������������


 	boolean running = true;
 	boolean end = false;
 	FishPanel fishpanel;

 	public Fish(FishPanel _fishpanel, int _x, int _y, int _width, int _height, int _i,int _down,int _right)
 	{
 		fishpanel = _fishpanel;
 		        x = _x;
 		        y = _y;
 		    width = _width;
 		   height = _height;
 		        i = _i;
                down = _down;
                right = _right;
 		start();
 	}

 	public void start()
 	{
 		running = true;
 		super.start();
 	}

 	public void run()
 	{
        while(running)
 		{
 			if(end)
 			    return;
            computeX();
 			computeY();
 			try
 			{
 				sleep(space);//v
 			}catch(Exception e){}
 			fishpanel.repaint();
 		}
 	}

 	public void halt()
 	{
 		running = false;
 	}

 	public void computeX()
 	{
         if(right == 1)
 		{
 			if(x >= fishpanel.getWidth()-width)
 			{//�ж��Ƿ��Ҷ˱߿��ﵽ������
 				  //��������ӵ����磬���轫����������ɾ��
                  if(NetState.b_connect)
 				 {
                    //�ӱ�����ɾ���˶���
                    fishpanel.delFish(this.id);
 				 }
                  else
 				 {
                     x = 0;
                     reset('x');
                 }
 			}
 			else if(count_x1 == x1)
 			    right = 0;
 			x++;
 		}
 		else
 		{
 			 if(x <= 0)
 			 {//�ж��Ƿ���˱߿��ﵽ������
 			 	//��������ӵ����磬���轫����������ɾ��
                  if(NetState.b_connect)
 				 {
                    //�ӱ�����ɾ���˶���
                    fishpanel.delFish(this.id);
 				 }
 				 else
 				 {
                     x = fishpanel.getWidth()-width;
                     reset('x');
 			     }
              }
              else if(count_x1 == x1)
 			    right = 1;
 			 x--;
 		}
 		count_x1++;
 	}

 	public void computeY()
 	{//������������ζ�
 		if(lean_angle != 0)//�ж������lean_angle�Ƿ�Ϊ0������ˮƽ�ζ����������Ƕ�
 		if(down == 1 && count_x1%lean_angle == 0)//x former
 		{//down=1��ʾ�������ҵ�x++��x--��lean_angle�κ�y++
 			if(y >= fishpanel.getHeight()-height || count_y1 == y1)
 			{//�ж��Ƿ񵽵׻��������������
 				down=0; reset('y');
 			}
 			y++;
 		}
 		else if(count_x1%lean_angle == 0 || count_y1 == y1)//x former
 		{//���������ҵ�x++��x--��l��֮��,y--
 			if(y <= 0)
 			{
 				down=1; reset('y');
 			}
 			y--;
 		}
 		count_y1++;
 	}

 	public void reset(char xory)
 	{//���ζ�״̬�ı���������ɸ�����ֵ
 		if(xory == 'x')
 		{
            x1 = (int)(Math.random()*x1_mul);//���������µ��������(�ж����ݣ�x)
 			count_x1 = 0; //�������������0
 		}
 		else
 		{
 			y1 = (int)(Math.random()*y1_mul);//���������µ��������(�ж����ݣ�y)
 			count_y1 = 0;//�������������0
 		}
 		space = (int)(Math.random()*space_mul)+space_plus;//���������µ��ٶ�
 		lean_angle = (int)(Math.random()*lean_mul);//���������µĽǶȱ�
 	}
 }