/**
 * 
 */
package cn.detector.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;


/**
 * 窗体图标及大小设置类
 */
public class UiUtil {
	 static int i = 0;
    private UiUtil(){}
    public static void setFrameImage(JFrame jf){
        Toolkit t = Toolkit.getDefaultToolkit();
        Image im = t.getImage("sources\\icon.jpg");
        jf.setIconImage(im);
    }
     public static void setFrameSize(JFrame jf){
         Toolkit t = Toolkit.getDefaultToolkit();
         Dimension d = t.getScreenSize();
         int screenWidth = (int)(d.getWidth());
         int screenHeigh = (int)(d.getHeight());
         
         jf.setBounds(0, 0, screenWidth, screenHeigh);
     }
}
