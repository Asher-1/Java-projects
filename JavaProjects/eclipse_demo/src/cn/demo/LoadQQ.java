package cn.demo;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class LoadQQ extends JFrame implements MouseListener,ActionListener{
    
    Image img1,img2,img3,img4;
    JLabel jl1,jl2,jl3;
    JTextField username;
    JPasswordField password;
    JButton jb1,jb2,jb3,jmin,jclose;
    JCheckBox jcb1,jcb2;
    JComboBox jc1;
    private Border BorderFactry;
    
    public static void main(String[]args){
        LoadQQ lg=new LoadQQ();
    }
    public LoadQQ()
    {
        //创建一个容器
        Container ct=this.getContentPane();
        this.setLayout(null);
        //设置背景
        backImage bi=new backImage();
        bi.setBounds(0, 0, 429, 328);
        
        // QQ登录头像设定
        jl1 = new JLabel();
        img1 = new ImageIcon("image//touxiang1.jpg").getImage();
        jl1.setIcon(new ImageIcon(img1));
        jl1.setBounds(40, 190, 80, 80);
        jc1=new JComboBox();
        jc1.addItem("我在线上");
        jc1.addItem("Q我吧");
        jc1.addItem("离开");
        jc1.addItem("忙碌");
        jc1.addItem("请勿打挠");
        jc1.addItem("隐身");
        jc1.setBounds(100, 250, 20, 20);
        
        
        //用户账号输入框
        username=new JTextField();
        username.setBounds(134, 193, 191, 25);
        username.setBorder(null);
        //设置边框下凹
  //      username.setBorder(BorderFactory.createLoweredBevelBorder());
        jl2=new JLabel("注册账号");
        jl2.setFont(new Font("宋体", Font.PLAIN, 12));
        jl2.setForeground(Color.BLUE);
        jl2.setBounds(335, 187, 100, 30);
        
        
        //用户密码输入框
        password=new JPasswordField();
        password.setBounds(134, 221, 191, 25);
        password.setBorder(null);
  //      password.setBorder(BorderFactory.createLoweredBevelBorder());
        jl3=new JLabel("找回密码");
        jl3.setFont(new Font("宋体", Font.PLAIN, 12));
        jl3.setForeground(Color.blue);
        jl3.setBounds(335, 219, 100, 30);
        
        //下方登录复选框
        jcb1=new JCheckBox("记住密码");
        jcb1.setBounds(130, 250, 100, 30);
        jcb1.setFont(new Font("宋体", Font.PLAIN, 12));
        jcb2=new JCheckBox("自动登录");
        jcb2.setBounds(255, 250, 100, 30);
        jcb2.setFont(new Font("宋体", Font.PLAIN, 12));
        
        jb1=new JButton();
        //img2 = new ImageIcon("image//loginbutton.png").getImage();
        //jb1.setIcon(new ImageIcon(img2));
        jb1.setBounds(132, 284, 195, 31);
        jb1.setBorderPainted(false);
        jb1.setOpaque(false);
        jb1.setContentAreaFilled(false); 
        jb1.addMouseListener(this);
        
        //多账户和微信登录按钮
        jb2=new JButton();
        jb2.setBounds(5,295,30,30);
        jb2.setBorderPainted(false);
        jb2.setOpaque(false);
        jb2.setContentAreaFilled(false);
        jb2.addMouseListener(this);
        jb3=new JButton();
        jb3.setBounds(392, 295,30,30);
        jb3.setOpaque(false);
        jb3.setContentAreaFilled(false);
        jb3.setBorderPainted(false);
        jb3.addMouseListener(this);
        
        jmin=new JButton();
        jmin.setBounds(370,0,30,30);
        jmin.setBorderPainted(false);
        jmin.setOpaque(false);
        jmin.setContentAreaFilled(false);
        jmin.addMouseListener(this);

        jclose=new JButton();
        jclose.setBounds(400,0,30,30);
        jclose.setBorderPainted(false);
        jclose.setOpaque(false);
        jclose.setContentAreaFilled(false);
        jclose.addMouseListener(this);
        
        ct.add(jl1);
        ct.add(jc1);
        ct.add(jl2);
        ct.add(jl3);
        ct.add(username);
        ct.add(password);
        ct.add(jcb1);
        ct.add(jcb2);
        ct.add(jb1);
        ct.add(jb2);
        ct.add(jb3);
        ct.add(jmin);
        ct.add(jclose);
        ct.add(bi);
        
        this.setSize(429,328);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==jb1){
            System.out.println("登录验证");
        }else if(e.getSource()==jmin){
            
        }else if(e.getSource()==jclose){
            System.exit(0);
        }
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==jb2)
        {
            img1 = new ImageIcon("icon.jpg").getImage();
            jb2.setIcon(new ImageIcon(img1));
        }else if(e.getSource()==jb3)
        {
            img3 = new ImageIcon("1.jpg").getImage();
            jb3.setIcon(new ImageIcon(img3));
        }
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==jb2)
        {
            img2 = new ImageIcon("icon.jpg").getImage();
            jb2.setIcon(new ImageIcon(img2));
        }else if(e.getSource()==jb3)
        {
            img3 = new ImageIcon("1.jpg").getImage();
            jb3.setIcon(new ImageIcon(img3));
        }
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    


}
//定义一个内部类，用来设置面板背景
class backImage extends JPanel{
    Image img;
    public backImage()
    {
        try {
            img=ImageIO.read(new File("1.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void paintComponent(Graphics g)
    {
        g.drawImage(img, 0, 0, 429, 328,this);
    }
}
