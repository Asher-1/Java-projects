//Calculator.java
package examples.Matrix;

import java.lang.InterruptedException;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import com.ibm.aglet.*;

public class Calculator extends Aglet{

	AgletProxy proxy=null;
    int matone[][]=new int[10][10];
    int mattwo[][]=new int[10][10];
    int matthr[][]=new int[10][10];
    public void onCreation(Object init){
        setText("creation begin");
        try{
            proxy=(AgletProxy)init;
        }catch(Exception e){setText("wrong!1");};
    }
    public void run(){
        setText("Calculator begin");
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

                    matthr[x+5][y]=matthr[x+5][y]+matone[x+5][z]*mattwo[z][y];   
                }
            }          
        };
        String result="";
        setText("sendonewaymsg begin");
        //�ͻ����ϴ�5��ʼ
        for (int x=5;x<10;x++){
             for (int y=0;y<10;y++){
                  result=result+"A["+x+"]["+y+"]="+matthr[x][y]+" ";  
             }
        };
        //��������ص�����
        try{  
        Message msg=new Message("Finish");
        msg.setArg("answer",result);   
        System.out.println(msg.getArg("answer"));   
        this.proxy.sendOnewayMessage(msg);
        }catch(Exception e){setText("wrong!2");};
      }
}
