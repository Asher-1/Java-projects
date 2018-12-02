package cn.detector.util;
/*
作者:Asher
功能:用于截取图片,方便快捷!
mail:ludahai#163.com (注意:其中#为@)
*/
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * 截图类
 * 用于截图按钮的调用
 * 
 */

@SuppressWarnings("serial")
public class AWTCutPicture extends Frame implements MouseListener,MouseMotionListener,ActionListener{
//定义txtPath成员变量，用于截图后的图片保存地址问题
String txtPath =null;
 /**
 * @param mypic 要设置的 mypic
 */
//获取存储坐标值的文本文件的绝对地址
public void setMypic(String mypics) {
	txtPath = new String(mypics);
}
private int frameWidth,frameHeight,firstPointx,firstPointy;
// private int firstWith,firstHeight,firstX,firstY;
 private BufferedImage bi,sbi;
 @SuppressWarnings("unused")
private BufferedImage original;
 private Robot robot;
 private Rectangle rectangle;
 private Rectangle rectangleCursorUp,rectangleCursorDown,rectangleCursorLeft,rectangleCursorRight;
// private Rectangle rectangleCursor;
 private Rectangle rectangleCursorRU,rectangleCursorRD,rectangleCursorLU,rectangleCursorLD;
 private Image bis;
 private Dimension dimension;
 private Button button,button2,clearButton;
 private Point[] point=new Point[3];
 private int width,height;
 private int nPoints=5;
 private Panel panel;
 private boolean drawHasFinish=false,change=false;
 private int changeFirstPointX,changeFirstPointY,changeWidth,changeHeight;
 private boolean changeUP=false,changeDOWN=false,changeLEFT=false,changeRIGHT=false,changeRU=false,changeRD=false,changeLU=false,changeLD=false;
// private boolean clearPicture=false;
 private boolean redraw=false;
 @SuppressWarnings("unused")
private FileDialog fileDialog;
 public AWTCutPicture(){
  //取得屏幕大小
  dimension=Toolkit.getDefaultToolkit().getScreenSize();
  frameWidth=dimension.width;
  frameHeight=dimension.height;
  
  fileDialog=new FileDialog(this,"关节截图",FileDialog.SAVE);
  rectangle=new Rectangle(frameWidth,frameHeight);
  panel=new Panel();
  button=new Button("退出");
  button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  button.setBackground(Color.green);
  button2=new Button("截取");
  button2.setBackground(Color.darkGray);
  button2.addActionListener(new MyTakePicture(this));
  button2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  button.addActionListener(this);
  clearButton=new Button("重绘");
  clearButton.setBackground(Color.green);
  clearButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  clearButton.addActionListener(new MyClearPicture(this));
  panel.setLayout(new BorderLayout());
  panel.add(clearButton, BorderLayout.SOUTH);
  
  panel.add(button, BorderLayout.NORTH);
  panel.add(button2, BorderLayout.CENTER);
  try {
   robot=new Robot();
  } catch (AWTException e) {
   
   e.printStackTrace();
  }
  
  //截取全屏
  bi=robot.createScreenCapture(rectangle);
  original=bi;
  this.setSize(frameWidth,frameHeight);
  this.setUndecorated(true);
  this.addMouseListener(this);
  this.addMouseMotionListener(this);
  this.add(panel,BorderLayout.EAST);
  this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
  this.setVisible(true);
  this.repaint();
 }
// public static void main(String[] args){
//  new AWTpicture();
// }
 public void paint(Graphics g) {
  
   this.drawR(g);
  
 }
  
 //缓存图片
 public void update(Graphics g){
  if(bis==null){
   bis=this.createImage(frameWidth, frameHeight);
  }
  Graphics ga=bis.getGraphics();
  Color c=ga.getColor();
  ga.setColor(Color.black);
  ga.fillRect(0, 0, frameWidth, frameHeight);
  ga.setColor(c);
  paint(ga);
  g.drawImage(bis, 0, 0, frameWidth, frameHeight, null);
 }
 public void mouseClicked(MouseEvent e) {

}
 public void mouseEntered(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }
 public void mouseExited(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }
 public void mousePressed(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }
 public void mouseReleased(MouseEvent e) {
  if(!drawHasFinish){
   if(point[1].x<point[2].x && point[1].y<point[2].y){
    firstPointx=point[1].x;
    firstPointy=point[1].y;
   }
   if(point[1].x>point[2].x && point[1].y<point[2].y){
    firstPointx=point[2].x;
    firstPointy=point[1].y;
   }
   if(point[1].x<point[2].x && point[1].y>point[2].y){
    firstPointx=point[1].x;
    firstPointy=point[2].y;
   }
   if(point[1].x>point[2].x && point[1].y>point[2].y){
    firstPointx=point[2].x;
    firstPointy=point[2].y;
   }
   changeFirstPointX=firstPointx;
   changeFirstPointY=firstPointy;
   if(point[1]!=null && point[2]!=null ){
   rectangleCursorUp=new Rectangle(firstPointx+20,firstPointy-10,width-40,20);
   rectangleCursorDown=new Rectangle(firstPointx+20,firstPointy+height-10,width-40,20);
   rectangleCursorLeft=new Rectangle(firstPointx-10,firstPointy+10,20,height-20);
   rectangleCursorRight=new Rectangle(firstPointx+width-10,firstPointy+10,20,height-20);
   rectangleCursorLU=new Rectangle(firstPointx-10,firstPointy-10,30,20);
   rectangleCursorLD=new Rectangle(firstPointx-10,firstPointy+height-10,30,20);
   rectangleCursorRU=new Rectangle(firstPointx+width-10,firstPointy-10,20,20);
   rectangleCursorRD=new Rectangle(firstPointx+width-10,firstPointy+height-10,20,20);
   drawHasFinish=true;
   }
   
  }
  //确定每边能改变大小的矩形
  if(drawHasFinish){
   rectangleCursorUp=new Rectangle(changeFirstPointX+20,changeFirstPointY-10,changeWidth-40,20);
   rectangleCursorDown=new Rectangle(changeFirstPointX+20,changeFirstPointY+changeHeight-10,changeWidth-40,20);
   rectangleCursorLeft=new Rectangle(changeFirstPointX-10,changeFirstPointY+10,20,changeHeight-20);
   rectangleCursorRight=new Rectangle(changeFirstPointX+changeWidth-10,changeFirstPointY+10,20,changeHeight-20);
   rectangleCursorLU=new Rectangle(changeFirstPointX-2,changeFirstPointY-2,10,10);
   rectangleCursorLD=new Rectangle(changeFirstPointX-2,changeFirstPointY+changeHeight-2,10,10);
   rectangleCursorRU=new Rectangle(changeFirstPointX+changeWidth-2,changeFirstPointY-2,10,10);
   rectangleCursorRD=new Rectangle(changeFirstPointX+changeWidth-2,changeFirstPointY+changeHeight-2,10,10);
  }

}
 public void mouseDragged(MouseEvent e) {
  point[2]=e.getPoint();
  //if(!drawHasFinish){
   this.repaint();
 // }

//托动鼠标移动大小
  if(change){
   if(changeUP){
    changeHeight=changeHeight+changeFirstPointY-e.getPoint().y;
    changeFirstPointY=e.getPoint().y;
    
   }
   if(changeDOWN){
    changeHeight=e.getPoint().y-changeFirstPointY;
   }
   if(changeLEFT){
    changeWidth=changeWidth+changeFirstPointX-e.getPoint().x;
    changeFirstPointX=e.getPoint().x;
   }
   if(changeRIGHT){
    changeWidth=e.getPoint().x-changeFirstPointX;
   }
   if(changeLU){
    changeWidth=changeWidth+changeFirstPointX-e.getPoint().x;
    changeHeight=changeHeight+changeFirstPointY-e.getPoint().y;
    changeFirstPointX=e.getPoint().x;
    changeFirstPointY=e.getPoint().y;
   }
   if(changeLD){
    changeWidth=changeWidth+changeFirstPointX-e.getPoint().x;
    changeHeight=e.getPoint().y-changeFirstPointY;
    changeFirstPointX=e.getPoint().x;
    
   }
   if(changeRU){
    changeWidth=e.getPoint().x-changeFirstPointX;
    changeHeight=changeHeight+changeFirstPointY-e.getPoint().y;
    changeFirstPointY=e.getPoint().y;
   }
   if(changeRD){
    changeWidth=e.getPoint().x-changeFirstPointX;
    changeHeight=e.getPoint().y-changeFirstPointY;
   
   }
   this.repaint();
  }
  
 }
 public void mouseMoved(MouseEvent e) {
  point[1]=e.getPoint();
  //改变鼠标的形状
  if(rectangleCursorUp!=null && rectangleCursorUp.contains(point[1])){
   
   this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
   change=true;
   changeUP=true;
  }else if(rectangleCursorDown!=null && rectangleCursorDown.contains(point[1])){
   this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
   change=true;
   changeDOWN=true;
  }else if(rectangleCursorLeft!=null && rectangleCursorLeft.contains(point[1])){
   this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
   change=true;
   changeLEFT=true;
  }else if(rectangleCursorRight!=null && rectangleCursorRight.contains(point[1]) ){
   this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
   change=true;
   changeRIGHT=true;
  }else if(rectangleCursorLU !=null && rectangleCursorLU.contains(point[1])){
   this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
   change=true;
   changeLU=true;
  }else if(rectangleCursorLD !=null && rectangleCursorLD.contains(point[1])){
   this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
   change=true;
   changeLD=true;
  }else if(rectangleCursorRU!=null && rectangleCursorRU.contains(point[1])){
   this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
   change=true;
   changeRU=true;
  }else if(rectangleCursorRD!=null && rectangleCursorRD.contains(point[1])){
   this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
   change=true;
   changeRD=true;
  }else{
   this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
   changeUP=false;changeDOWN=false;changeRIGHT=false;changeLEFT=false;changeRU=false;
   changeRD=false;changeLU=false;changeLD=false;
  }
  redraw=false;
 }
 
 
 @Override
 //退出窗体
 public void actionPerformed(ActionEvent e) {
	 this.dispose();

 }
 
 
 class MyTakePicture implements ActionListener{
  AWTCutPicture aWTpicture;
  MyTakePicture(AWTCutPicture aWTpicture){
   this.aWTpicture=aWTpicture;
  }
  
  
  
  //保存图片
  public void actionPerformed(ActionEvent e) {
	//防止用户乱操作按钮，如并未打开图片就进行相关的按钮操作
		if(txtPath == null){
			JOptionPane.showMessageDialog(null, 
					"用户未打开图片，截图失败", "错误信息", JOptionPane.ERROR_MESSAGE);
			return;
			}
//   fileDialog.setVisible(true);
   if(changeWidth>0){
    sbi=bi.getSubimage(changeFirstPointX,changeFirstPointY,changeWidth,changeHeight);
    
//    if(fileDialog.getDirectory() == null){
//    	return;
//    }
//    File file=new File(fileDialog.getDirectory());
//    file.mkdir();
    
    try {

    String myPath = new String(txtPath.replaceAll(".txt", "")+".jpg");
    //保存到文本文件所在目录，与文本文件放在一起
    File m_f = new File(txtPath);
    File f = new File(txtPath.replaceAll(".txt", "")+".jpg");
    if(!m_f.exists()){
    	JOptionPane.showMessageDialog(null, 
				"请先保存坐标后，再进行截图操作", "错误信息", JOptionPane.ERROR_MESSAGE);
		return;
    }
    if (f.exists()) {
		int i = JOptionPane.showConfirmDialog(null, "该截图已经存在，确定要覆盖吗？");
		if (i == JOptionPane.YES_OPTION){
			
			ImageIO.write(sbi, "jpeg", f);
			   //显示截图成功
		     JOptionPane.showMessageDialog(null, 
						"截图成功！图片保存在"+myPath+"中", "保存成功信息", JOptionPane.INFORMATION_MESSAGE);
		}
		
		else
			return;
	}
    else{ 
    	ImageIO.write(sbi, "jpeg", f);
    	 //显示截图成功
        JOptionPane.showMessageDialog(null, 
   				"截图成功！图片保存在"+myPath+"中", "保存成功信息",JOptionPane.INFORMATION_MESSAGE);
    }
  
    } catch (IOException e1) {
     
     e1.printStackTrace();
    	}
   	}
   
 }
  
  
  

}
 class MyClearPicture implements ActionListener{
  AWTCutPicture aWTpicture;
  MyClearPicture(AWTCutPicture aWTpicture){
   this.aWTpicture=aWTpicture;
  }
  
  public void actionPerformed(ActionEvent e) {
   drawHasFinish=false;
   change=false;
   redraw=true;
   rectangleCursorUp=null;
   rectangleCursorDown=null;
   rectangleCursorLeft=null;
   rectangleCursorRight=null;
   rectangleCursorRU=null;
   rectangleCursorRD=null;
   rectangleCursorLU=null;
   rectangleCursorLD=null;
   changeWidth=0;
   changeHeight=0;
   
   aWTpicture.repaint();

}
  
 }
 public void drawR(Graphics g){
  g.drawImage(bi, 0,0,frameWidth,frameHeight, null);
  
  if(point[1]!=null &&point[2]!=null &&!drawHasFinish && !redraw){
   int[] xPoints={point[1].x,point[2].x,point[2].x,point[1].x,point[1].x};
   int[] yPoints={point[1].y,point[1].y,point[2].y,point[2].y,point[1].y};
   width=(point[2].x-point[1].x)>0?(point[2].x-point[1].x):(point[1].x-point[2].x);
   height=(point[2].y-point[1].y)>0?(point[2].y-point[1].y):(point[1].y-point[2].y);
   changeWidth=width;
   changeHeight=height;
//   Color c=g.getColor();
   g.setColor(Color.red);
   g.drawString(width+"*"+height, point[1].x, point[1].y-5);
   //画点
   /*int i;
   if()*/
   if(point[1].x<point[2].x && point[1].y<point[2].y){
    firstPointx=point[1].x;
    firstPointy=point[1].y;
   }
   if(point[1].x>point[2].x && point[1].y<point[2].y){
    firstPointx=point[2].x;
    firstPointy=point[1].y;
   }
   if(point[1].x<point[2].x && point[1].y>point[2].y){
    firstPointx=point[1].x;
    firstPointy=point[2].y;
   }
   if(point[1].x>point[2].x && point[1].y>point[2].y){
    firstPointx=point[2].x;
    firstPointy=point[2].y;
   }
   
   g.fillRect(firstPointx-2,firstPointy-2 , 5,5);
   g.fillRect(firstPointx+(width)/2,firstPointy-2 , 5,5);
   g.fillRect(firstPointx+width-2,firstPointy-2 , 5,5);
   g.fillRect(firstPointx+width-2,firstPointy+ height/2-2, 5,5);
   g.fillRect(firstPointx+width-2,firstPointy+height-2, 5,5);
   g.fillRect(firstPointx+(width)/2,firstPointy+height-2, 5,5);
   g.fillRect(firstPointx-2,firstPointy+height-2, 5,5);
   g.fillRect(firstPointx-2,firstPointy+ height/2-2, 5,5);
   //画矩形
   //g.drawString("fafda", point[1].x-100, point[1].y-5);
   g.drawPolyline(xPoints, yPoints, nPoints);
   
  }
  
  if(change){
   g.setColor(Color.red);
   g.drawString(changeWidth+"*"+changeHeight, changeFirstPointX, changeFirstPointY-5);
   
   g.fillRect(changeFirstPointX-2,changeFirstPointY-2 , 5,5);
   g.fillRect(changeFirstPointX+(changeWidth)/2,changeFirstPointY-2 , 5,5);
   g.fillRect(changeFirstPointX+changeWidth-2,changeFirstPointY-2 , 5,5);
   g.fillRect(changeFirstPointX+changeWidth-2,changeFirstPointY+ changeHeight/2-2, 5,5);
   g.fillRect(changeFirstPointX+changeWidth-2,changeFirstPointY+changeHeight-2, 5,5);
   g.fillRect(changeFirstPointX+(changeWidth)/2,changeFirstPointY+changeHeight-2, 5,5);
   g.fillRect(changeFirstPointX-2,changeFirstPointY+changeHeight-2, 5,5);
   g.fillRect(changeFirstPointX-2,changeFirstPointY+ changeHeight/2-2, 5,5);
   
   g.drawRect(changeFirstPointX, changeFirstPointY, changeWidth, changeHeight);
  		}
 	}
}