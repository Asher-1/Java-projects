package cn.demo;
import java.awt.Image;  
import javax.swing.ImageIcon;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
  
/** ����JLabel������ͼƬ */  
  
public class LoadPic {  
    JLabel jlpic = new JLabel();  
    JFrame jframe = new JFrame();  
  
    public LoadPic() {  
  
        init1Frame();  
    }  
  
    public void init1Frame() {  
        ImageIcon icon = new ImageIcon("1.jpg");  
        icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),  
                icon.getIconHeight(), Image.SCALE_DEFAULT));  
        System.out.println(icon.getIconHeight() + "" + icon.getIconWidth());  
        jlpic.setBounds(0, 0, 1900, 1080);  
        jlpic.setHorizontalAlignment(0);  
        jlpic.setIcon(icon);  
        jframe.setSize(1900, 1080);  
        jframe.add(jlpic);  
        jframe.pack();  
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        jframe.setVisible(true);  
  
    }  
  
    public static void main(String args[]) {  
  
        new LoadPic();  
    }  
}  

