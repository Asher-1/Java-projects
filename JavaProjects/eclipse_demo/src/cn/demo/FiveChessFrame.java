package cn.demo;
//package eclipse_demo1;
//import java.awt.Font;//������
//import java.awt.Graphics;//�Զ����������paint()��������Ҫ��д
//import java.awt.Image;
//import java.awt.Toolkit;//��ȡ������Ļ�ĳ��Ϳ�
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.image.BufferedImage;//����ͼƬ��Ϊ����(��)
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//
//public class FiveChessFrame extends JFrame implements MouseListener {
//	int width = Toolkit.getDefaultToolkit().getScreenSize().width;//��Ļ�Ŀ�
//	int height = Toolkit.getDefaultToolkit().getScreenSize().height;//��Ļ�ĳ�
//    Image bgImage = null;//ͼƬ������
//    
//	public FiveChessFrame()
//	{
//		this.setTitle("������");
//		this.setSize(500,500);//���ó������С
//		this.setLocation((width-500)/2,(height-500)/2);//ʹ�ó��򴰿ڳ�������Ļ��������
//		this.setResizable(false);//���ó�����Ĵ�С���ɱ༭
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ʹ�õ��'�ر�'����ʱ������ֹͣ����
//		this.setVisible(true);
//		
//		this.addMouseListener(this);
////		try
////		{//������ͼƬ���ֽ�������ʽ��ȡ���ڴ棬��ͨ��paint()�����������Ϊ����ı���
//			bgImage = (new ImageIcon("src\\eclipse_demo1\\1.jpg")).getImage();
////		}
////		catch (IOException e)
////		{
////			e.printStackTrace();
////		}
//	}
//	
//	public void paint(Graphics g)//������Զ����ô˷���
//	{
//		g.drawImage(bgImage,3,20,this);//this��ʾ�ڽ�ͼƬ��Ϊ�˴���ı���
//		Graphics gt = bgImage.getGraphics();
//		gt.setFont(new Font("����",Font.BOLD,20));//���á���Ϸ��Ϣ��������
//		gt.drawString("��Ϸ��Ϣ", 150, 50);
//		gt.setFont(new Font("����",0,14));//��Ϊ����������������
//		gt.drawString("�ڷ�ʱ�䣺������", 45, 470);
//		gt.drawString("�׷�ʱ�䣺������", 260, 470);
//		for(int i=0;i<19;i++)
//		{//ǰ������������ʾ������꣬��������������ʾ�յ�����(�Դ������Ͻ���Ϊ����ԭ��)
//			gt.drawLine(13, 70+20*i, 372, 70+20*i);//��19������
//			gt.drawLine(13+20*i,70,13+20*i,430);//��19������
//		}	
//		gt.fillOval(71, 128, 4, 4);//�������л�һЩ�ؼ���
//		gt.fillOval(311, 128, 4, 4);
//		gt.fillOval(311, 368, 4, 4);
//		gt.fillOval(71, 368, 4, 4);
//		gt.fillOval(311, 248, 4, 4);
//		gt.fillOval(191, 128, 4, 4);
//		gt.fillOval(71, 248, 4, 4);
//		gt.fillOval(191, 368, 4, 4);
//		gt.fillOval(191, 248, 4, 4);
//	}
//	
//
//	@Override
//	public void mouseClicked(MouseEvent e) {
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//	}
//
//	public static void main(String[] args) {
//
//		FiveChessFrame ff = new FiveChessFrame(); 
//
//	}
//
//}
