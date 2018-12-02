/**
 * File: MyRand.java
 * User: ������
 * Date: 2006.11.8
 * Describe: ����˹����� Java ʵ��
 */

package Tetris;

/**
 * ��������ࡣ
 * ��Ҫ�����趨�ĸ������ɶ���˹����
 */
public class MyRand {
	private double[] pro = new double[7];
	
	/**
	 * ���������Ĺ��캯��
	 */
	public MyRand(){		
	}
	
	/**
	 * ���������Ĺ��캯��
	 * @param p1 double, ��һ������˹����ĳ��ָ��� 
	 * @param p2 double, �ڶ�������˹����ĳ��ָ���
	 * @param p3 double, ����������˹����ĳ��ָ���
	 * @param p4 double, ���ĸ�����˹����ĳ��ָ���
	 * @param p5 double, ���������˹����ĳ��ָ���
	 * @param p6 double, ����������˹����ĳ��ָ���
	 * @param p7 double, ���߸�����˹����ĳ��ָ���
	 */
	public MyRand(double p1, 
				  double p2, 
				  double p3, 
				  double p4, 
				  double p5, 
				  double p6, 
				  double p7){
		this();
		pro[0] = p1;
		pro[1] = p2;
		pro[2] = p3;
		pro[3] = p4;
		pro[4] = p5;
		pro[5] = p6;
		pro[6] = p7;
		
	}
	
	/**
	 * ����ÿһ��������ָ���ֵ
	 * @param p1 double, ��һ������˹����ĳ��ָ��� 
	 * @param p2 double, �ڶ�������˹����ĳ��ָ���
	 * @param p3 double, ����������˹����ĳ��ָ���
	 * @param p4 double, ���ĸ�����˹����ĳ��ָ���
	 * @param p5 double, ���������˹����ĳ��ָ���
	 * @param p6 double, ����������˹����ĳ��ָ���
	 * @param p7 double, ���߸�����˹����ĳ��ָ���
	 */
	public void setProbality(double p1, 
			  				 double p2, 
			  				 double p3, 
			  				 double p4, 
			  				 double p5, 
			  				 double p6, 
			  				 double p7){
		pro[0] = p1;
		pro[1] = p2;
		pro[2] = p3;
		pro[3] = p4;
		pro[4] = p5;
		pro[5] = p6;
		pro[6] = p7;
	}
	
	/**
	 * �����趨�ĸ����������0~6�Ķ���˹����ı��
	 * @return i, ʡ�ǵĶ���˹����ı��
	 */
	public int getRandom(){
		int i;
		double sum = 0.0;
		double rand = Math.random();
		
		for(i = 0; i < 7; i++){
			sum += pro[i];
			if(rand < sum+1e-8)
				break;
		}
		return i;
	}
}
