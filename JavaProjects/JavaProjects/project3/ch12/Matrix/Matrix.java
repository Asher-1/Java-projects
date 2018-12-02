package examples.Matrix;

import com.ibm.aglet.*;
import com.ibm.aglet.event.*;
import com.ibm.aglet.util.*;

import java.lang.InterruptedException;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class Matrix extends Aglet {
	transient MyDialog my_dialog = null;  //没有串行化
    URL dgp = null;//客户端地址
    String message = null;

    //矩阵初始化
    int matone[][]=new int[10][10];
    int mattwo[][]=new int[10][10];
    int matthr[][]=new int[10][10];
    boolean Flag;//设置标志量
    int all = 0;
    AgletProxy outer;//Agent客户机地址
  
    //设置初始窗口
    public void onCreation(Object init) {  
    	Flag=true;
        setMessage("Choose remote machine and GO!");//窗口显示信息
        createGUI(); //创建窗口
    }

  //创建窗口
    protected void createGUI(){
        my_dialog = new MyDialog(this);
        my_dialog.pack();
        my_dialog.setSize(my_dialog.getPreferredSize());
        my_dialog.setVisible(true);
    }

  //窗口显示信息 
   public void setMessage(String message){
        this.message = message;
    }

  //处理信息
    public boolean handleMessage(Message msg) {
      if (msg.sameKind("Finish")) {
            OutPut(msg);
        } else if (msg.sameKind("startTrip")){
            startTrip(msg);
        }else{ 
            return false;
        };
        return true;
    }

  //报告已经返回并销毁
    public void OutPut(Message msg) {
        setText("output begin");
        while (Flag){
           waitMessage(5*10);
        };
        my_dialog.msg.append("\n"+msg.getArg("answer").toString()+"\n"); 
    }
    public synchronized void startTrip(Message msg) {  //到达远程机器
        String destination = (String)msg.getArg();  //得到远程机器地址
        //客户端得到Agent的内容
        try{
        outer=getAgletContext().createAglet(null,"examples.Matrix.Calculator",getProxy());
        }catch(Exception e){setText("wrong!");};
        try{   
        dgp=new URL(destination);  //dgp为客户端地址
        }catch(MalformedURLException e){setText("wrong!");};
        try{
        outer.dispatch(dgp); //发送到客户端
        }catch(Exception e){setText("wrong!");};
        matrixrun();    //本机上任务开始运行
    }
     
   //本机上程序
    public void matrixrun(){
        setText("matrixrun begin");
        for (int x=0;x<10;x++){
            for(int y=0;y<10;y++){
                matone[x][y]=1;
                mattwo[x][y]=1;
                matthr[x][y]=0;
            }
        };
        for (int x=0;x<5;x++){
            for (int y=0;y<10;y++){
                for (int z=0;z<10;z++){
                    matthr[x][y]=matthr[x][y]+matone[x][z]*mattwo[z][y];   
                }
            }          
        };
        Flag=false;
        String result=""; 
        for (int x=0;x<5;x++){
             for (int y=0;y<10;y++){
                  result=result+"A["+x+"]["+y+"]="+matthr[x][y]+" ";  
             }
        }
       //打印本机结果
        my_dialog.msg.append("The result shows down:"+"\n"+result+"\n"+"Local is over!");
        setText("matrixrun over");
    } 
}

//MyDialog 窗口
class MyDialog extends Frame implements WindowListener, ActionListener{
    private Matrix aglet  = null;
   //下面是一些GUI组件
    private AddressChooser dest = new AddressChooser();
    public TextArea msg =
    	new TextArea("",10,20,TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Button go           = new Button("GO!");
    private Button close        = new Button("CLOSE");

   //创建对话窗口
    MyDialog(Matrix aglet) {
        this.aglet = aglet;
        layoutComponents();
        addWindowListener(this);
        go.addActionListener(this);
        close.addActionListener(this);
    }

    //组件的布局
    private void layoutComponents() {
        msg.setText(aglet.message);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints cns = new GridBagConstraints();
        setLayout(grid);

        cns.weightx = 0.5;
        cns.ipadx = cns.ipady = 5;
        cns.fill = GridBagConstraints.HORIZONTAL;
        cns.insets = new Insets(5,5,5,5);

        cns.weightx = 1.0;
        cns.gridwidth = GridBagConstraints.REMAINDER;
        grid.setConstraints(dest, cns);
        add(dest);

        cns.gridwidth = GridBagConstraints.REMAINDER;
        cns.fill = GridBagConstraints.BOTH;
        cns.weightx = 1.0;
        cns.weighty = 1.0;
        cns.gridheight = 2;
        grid.setConstraints(msg, cns);
        add(msg);

        cns.weighty = 0.0;
        cns.fill = GridBagConstraints.NONE;
        cns.gridheight = 1;

        Panel p = new Panel();

        grid.setConstraints(p, cns);
        add(p);
        p.setLayout(new FlowLayout());
        p.add(go);
        p.add(close);
    } 

   //事件的处理
    public void actionPerformed(ActionEvent ae){
        //对“go”按钮的处理
        if("GO!".equals(ae.getActionCommand())){
            aglet.setMessage(msg.getText());
            msg.setText("");

            try{
                AgletProxy p = aglet.getProxy();
                p.sendOnewayMessage(new Message("startTrip", dest.getAddress()));
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        // 对“close”按钮的处理
        else if("CLOSE".equals(ae.getActionCommand())){
            setVisible(false);
        }
    }

     public void windowClosing(WindowEvent we){
        dispose();
     }

     public void windowOpened(WindowEvent we){
     }

     public void windowIconified(WindowEvent we){
     }

     public void windowDeiconified(WindowEvent we){
     }

     public void windowClosed(WindowEvent we){
     }

     public void windowActivated(WindowEvent we){
     }

     public void windowDeactivated(WindowEvent we){
     }
}
