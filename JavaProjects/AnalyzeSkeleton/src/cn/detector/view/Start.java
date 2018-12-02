/**
 * 
 */
package cn.detector.view;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

//import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.detector.core.MyLookAndFeel;
import cn.detector.core.Mydraw;
import cn.detector.core.Save_Information;
/**
 * @author ½��
 *
 */
public class Start {
	static WindowOperation win;
	static AudioClip wlcome_sound;
	static AudioClip background_sound;
//	static String[] path;
	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
				
		win = new WindowOperation("������̬�Զ���עϵͳ"); //��������ע��ϵͳ����
		MyLabel.setWin(win); //�������������ô���MyLabel��
		Mydraw.setWin(win);
		Save_Information.setWin(win);
		win.setVisible(true); //���ô���ɼ� 
		
		//setScale(win);
		
		// �������ִ��벿��
		File f1 = new File("sources\\Garden.wav"); //����������������ļ����ڵľ���·��
		@SuppressWarnings("deprecation")
		URL url1 = f1.toURL();
		wlcome_sound = Applet.newAudioClip(url1); // ���ɻ�ӭ����
		wlcome_sound.play();
		
		File f2 = new File("sources\\��������.wav"); //����������������ļ����ڵľ���·��
		@SuppressWarnings("deprecation")
		URL url2 = f2.toURL();
		background_sound = Applet.newAudioClip(url2); // ���ɱ�������
		
		setSkin(); //���ô���Ƥ�����
	}
	
	
	
	public static void setScale(WindowOperation win){
		  win.addMouseMotionListener(new MouseAdapter() {
			    private boolean top = false;
			    private boolean down = false;
			    private boolean left = false;
			    private boolean right = false;
			    @SuppressWarnings("unused")
				private boolean drag = false;
			    private Point draggingAnchor = null;
			       @Override
			       public void mouseMoved(MouseEvent e) {
			             if(  e.getPoint().getY() == 0 ){
			             win.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			             top = true;
			             }else if(Math.abs(e.getPoint().getY()- win.getSize().getHeight()) <= 1 ){
			             win.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			             down = true;
			             }else if(e.getPoint().getX() == 0 ){
			             win.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			             left = true;
			             }else if(Math.abs(e.getPoint().getX()- win.getSize().getWidth()) <=1 ){
			             win.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			             right = true;
			             }else{
			             win.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			             draggingAnchor = new Point(e.getX() + win.getX(), e.getY() + win.getY());
			             top = false;
			             down = false;
			             left = false;
			             right = false;
			             drag = true;
			             }
			  
			       }
			            
			       @Override
			       public void mouseDragged(MouseEvent e) {
			             Dimension dimension = win.getSize();
			             if(top){
			             
			             dimension.setSize(dimension.getWidth() ,dimension.getHeight()-e.getY());
			             win.setSize(dimension);
			             win.setLocation(win.getLocationOnScreen().x, win.getLocationOnScreen().y + e.getY());
			             }else if(down){
			              
			             dimension.setSize(dimension.getWidth() , e.getY());
			             win.setSize(dimension);
			      
			             }else if(left){
			              
			             dimension.setSize(dimension.getWidth() - e.getX() ,dimension.getHeight() );
			             win.setSize(dimension);
			             
			     
			             win.setLocation(win.getLocationOnScreen().x + e.getX(),win.getLocationOnScreen().y );
			                
			             }else if(right){
			              
			             dimension.setSize(e.getX(),dimension.getHeight());
			             win.setSize(dimension);
			             }else { 
			                    win.setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
			             }
			    }
		});
	}
	
	//����Ƥ������
	public final static void setSkin() {

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();// ��ȡ��ǰ�����¼��Ĺ�����
		manager.addKeyEventPostProcessor(new KeyEventPostProcessor() {// Ϊ��������Ӽ����û��¼�
			@Override
			public boolean postProcessKeyEvent(KeyEvent e) {
				// ��������
				if(e.getKeyCode() == KeyEvent.VK_L){
					background_sound.loop();
				}
				else if(e.getKeyCode() == KeyEvent.VK_P){
					background_sound.play();
				}
				else if(e.getKeyCode() == KeyEvent.VK_S){
					background_sound.stop();
				}
				
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O){//�򿪱�עͼƬ�����ļ���

					try {
						Runtime.getRuntime().exec("explorer.exe "+win.path_contains_pic);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F1){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.SYS_NIMBUS );
						dispose_to_create();
						//SwingUtilities.updateComponentTreeUI(win);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F2){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.SYS_CDE_MOTIF );
						dispose_to_create();
						//SwingUtilities.updateComponentTreeUI(win);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F3){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.SYS_WINDOWS );
						dispose_to_create();
						//SwingUtilities.updateComponentTreeUI(win);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F4){
					win.dispose();
					try {

						UIManager.setLookAndFeel(MyLookAndFeel.SYS_WINDOWS_CLASSIC );
						dispose_to_create();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F5){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.JTATTOO_HIFI );
						dispose_to_create();
						SwingUtilities.updateComponentTreeUI(win);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F6){
					win.dispose();
					try {
						
						UIManager.setLookAndFeel(MyLookAndFeel.SYS_METAL );
						dispose_to_create();
						
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F7){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.JTATTOO_LUNA );
						dispose_to_create();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F8){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.LIQUIDINF );
						dispose_to_create();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F9){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.JTATTOO_NOIRE );
						dispose_to_create();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F10){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.JTATTOO_SMART );
						dispose_to_create();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F11){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.JTATTOO_BERNSTEIN );
						dispose_to_create();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F12){
					win.dispose();
					try {
						UIManager.setLookAndFeel(MyLookAndFeel.JTATTOO_MCWIN );
						dispose_to_create();
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			/*
			 * ע�����ﲻ�ܼ���else{}�������ԣ���������ᵼ�½�����˸
			 * 
			 * 
			 * */	
				return false;
			}

			private void dispose_to_create() {//
				//win.dispose();
				win = new WindowOperation("������̬�Զ���עϵͳ"); //��������ע��ϵͳ����
		    	//UiUtil.setFrameSize(win);//���ô����С����СΪ��Ļ��С
		    	
				MyLabel.setWin(win); //�������������ô���MyLabel��
				Mydraw.setWin(win);
				Save_Information.setWin(win);
				win.setVisible(true); //���ô���ɼ� 
				//win.setResizable(false);
				//setScale(win);
			}
		});
	}
}