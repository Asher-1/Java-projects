package fishinfo;
/**
 *<p>
 * * Copyright (C),200
 * by huanghao
 * Desctiptoin: ͨ��ʱ��Ҫ���͵�����
 * </p>
 *
 */
import  java.io.Serializable;
public class FishInfo implements Serializable{
    public
	int x,		  //�����x����
 	    y,		  //�����y����
 	    width,	  //����ʹ�õ�ͼƬ�Ŀ��
 	    height,	  //����ʹ�õ�ͼƬ�ĸ߶�
 	    i,
        down,
        right;
 	public FishInfo(int x,int y,int width,int height,
                    int i,int down,int right)
                    {
                    this.x = x;
                    this.y= y;
                    this.width = width;
                    this.height =height;
                    this.i = i;
                    this.down = down;
                    this.right = right;
                    }
}