package cn.demo;

import java.awt.BorderLayout;  
import javax.swing.JFrame;  
import javax.swing.JButton;  
  
  
public class s {  
  
    public static void main(String[] args) {  
        //����һ��JFrame,JFrame��Ĭ��LayoutManagerΪBorderLayout   
        JFrame f=new JFrame("BorderLayout");  
        JButton btn=new JButton("BorderLayout.NORTH");  
        f.add(btn,BorderLayout.NORTH);  
        btn=new JButton("BorderLayout.SOUTH");  
        f.add(btn,BorderLayout.SOUTH);  
        btn=new JButton("BorderLayout.EAST");  
        f.add(btn,BorderLayout.EAST);  
        btn=new JButton("BorderLayout.West");  
        f.add(btn,BorderLayout.WEST);  
        btn=new JButton("BorderLayout.CENTER");  
        f.add(btn,BorderLayout.CENTER);  
        f.pack();  
        f.setVisible(true);  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
  
} 