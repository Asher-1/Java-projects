package cn.demo;
//package eclipse_demo1;
//import java.awt.Font;//改字体
//import java.awt.Graphics;//自动调用里面的paint()方法，需要重写
//import java.awt.Image;
//import java.awt.Toolkit;//获取电脑屏幕的长和宽
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.image.BufferedImage;//导入图片作为背景(流)
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//
//public class FiveChessFrame extends JFrame implements MouseListener {
//	int width = Toolkit.getDefaultToolkit().getScreenSize().width;//屏幕的宽
//	int height = Toolkit.getDefaultToolkit().getScreenSize().height;//屏幕的长
//    Image bgImage = null;//图片缓冲流
//    
//	public FiveChessFrame()
//	{
//		this.setTitle("五子棋");
//		this.setSize(500,500);//设置程序窗体大小
//		this.setLocation((width-500)/2,(height-500)/2);//使得程序窗口出现在屏幕的正中央
//		this.setResizable(false);//设置程序窗体的大小不可编辑
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使得点击'关闭'符号时，程序停止运行
//		this.setVisible(true);
//		
//		this.addMouseListener(this);
////		try
////		{//将背景图片以字节流的形式读取到内存，再通过paint()方法输出，作为程序的背景
//			bgImage = (new ImageIcon("src\\eclipse_demo1\\1.jpg")).getImage();
////		}
////		catch (IOException e)
////		{
////			e.printStackTrace();
////		}
//	}
//	
//	public void paint(Graphics g)//程序会自动调用此方法
//	{
//		g.drawImage(bgImage,3,20,this);//this表示在将图片作为此窗体的背景
//		Graphics gt = bgImage.getGraphics();
//		gt.setFont(new Font("黑体",Font.BOLD,20));//设置“游戏信息”的字体
//		gt.drawString("游戏信息", 150, 50);
//		gt.setFont(new Font("宋体",0,14));//再为以下文字设置字体
//		gt.drawString("黑方时间：无限制", 45, 470);
//		gt.drawString("白方时间：无限制", 260, 470);
//		for(int i=0;i<19;i++)
//		{//前面两个参数表示起点坐标，后面两个参数表示终点坐标(以窗体左上角作为坐标原点)
//			gt.drawLine(13, 70+20*i, 372, 70+20*i);//画19条横线
//			gt.drawLine(13+20*i,70,13+20*i,430);//画19条竖线
//		}	
//		gt.fillOval(71, 128, 4, 4);//在棋盘中画一些关键点
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
