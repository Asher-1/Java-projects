package cn.demo;

import java.awt.Color;  
import java.awt.Component;  
  
import javax.swing.DefaultCellEditor;  
import javax.swing.JComboBox;  
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTable;  
import javax.swing.table.DefaultTableCellRenderer;  
import javax.swing.table.DefaultTableModel;  
import javax.swing.table.TableColumn;  
import javax.swing.table.TableModel;  
//在单元格中添加基本组件 下拉框  
public class Tabel5 extends JFrame   
{  
    JTable table;  
    JScrollPane pane;  
    TableModel model;  
    //数据  
    Object[][] data = {{"张三","男",3},  
                        {"李四","男",4},  
                        {"小红","女",5}};  
    //列名  
    Object[] clomnName = {"姓名","性别","年龄"};  
    Tabel5()  
    {  
        super("表格测试程序");  
        init();  
    }  
    private void init()  
    {  
        //初始化TableModel，使用数据data，列名clomnName  
        model = new DefaultTableModel(data, clomnName);  
        //使用上面的model作为参数初始化JTabel  
        table = new JTable(model);  
          
        //初始化下拉列表框  
        JComboBox box = new JComboBox();  
        box.addItem("男");  
        box.addItem("女");  
          
        //获取性别这一列对象  
        TableColumn d = table.getColumn("性别");  
          
        //将下拉列表框放入表格编辑器  
        DefaultCellEditor dce = new DefaultCellEditor(box);   
          
        d.setCellEditor(dce);  
          
        //初始化JScrollPane  
        pane = new JScrollPane();  
        //将table添加到JScrollPane上 这里使用下面的第一种方法不能添加  
        //使用第一个先获得JViewPort对象，然后再添加  
        pane.getViewport().add(table);  
        this.getContentPane().add(pane);  
        setSize(400,300);//设置Frame的大小  
        setResizable(false);//设置窗口不可以调整大小  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出时关闭窗口  
        setVisible(true);//设置窗口显示出来  
    }  
    public static void main(String[] args)  
    {  
        new Tabel5();  
    }  
}  
