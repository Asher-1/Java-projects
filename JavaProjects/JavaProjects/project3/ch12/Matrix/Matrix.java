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
	transient MyDialog my_dialog = null;  //û�д��л�
    URL dgp = null;//�ͻ��˵�ַ
    String message = null;

    //�����ʼ��
    int matone[][]=new int[10][10];
    int mattwo[][]=new int[10][10];
    int matthr[][]=new int[10][10];
    boolean Flag;//���ñ�־��
    int all = 0;
    AgletProxy outer;//Agent�ͻ�����ַ
  
    //���ó�ʼ����
    public void onCreation(Object init) {  
    	Flag=true;
        setMessage("Choose remote machine and GO!");//������ʾ��Ϣ
        createGUI(); //��������
    }

  //��������
    protected void createGUI(){
        my_dialog = new MyDialog(this);
        my_dialog.pack();
        my_dialog.setSize(my_dialog.getPreferredSize());
        my_dialog.setVisible(true);
    }

  //������ʾ��Ϣ 
   public void setMessage(String message){
        this.message = message;
    }

  //������Ϣ
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

  //�����Ѿ����ز�����
    public void OutPut(Message msg) {
        setText("output begin");
        while (Flag){
           waitMessage(5*10);
        };
        my_dialog.msg.append("\n"+msg.getArg("answer").toString()+"\n"); 
    }
    public synchronized void startTrip(Message msg) {  //����Զ�̻���
        String destination = (String)msg.getArg();  //�õ�Զ�̻�����ַ
        //�ͻ��˵õ�Agent������
        try{
        outer=getAgletContext().createAglet(null,"examples.Matrix.Calculator",getProxy());
        }catch(Exception e){setText("wrong!");};
        try{   
        dgp=new URL(destination);  //dgpΪ�ͻ��˵�ַ
        }catch(MalformedURLException e){setText("wrong!");};
        try{
        outer.dispatch(dgp); //���͵��ͻ���
        }catch(Exception e){setText("wrong!");};
        matrixrun();    //����������ʼ����
    }
     
   //�����ϳ���
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
       //��ӡ�������
        my_dialog.msg.append("The result shows down:"+"\n"+result+"\n"+"Local is over!");
        setText("matrixrun over");
    } 
}

//MyDialog ����
class MyDialog extends Frame implements WindowListener, ActionListener{
    private Matrix aglet  = null;
   //������һЩGUI���
    private AddressChooser dest = new AddressChooser();
    public TextArea msg =
    	new TextArea("",10,20,TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Button go           = new Button("GO!");
    private Button close        = new Button("CLOSE");

   //�����Ի�����
    MyDialog(Matrix aglet) {
        this.aglet = aglet;
        layoutComponents();
        addWindowListener(this);
        go.addActionListener(this);
        close.addActionListener(this);
    }

    //����Ĳ���
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

   //�¼��Ĵ���
    public void actionPerformed(ActionEvent ae){
        //�ԡ�go����ť�Ĵ���
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
        
        // �ԡ�close����ť�Ĵ���
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
