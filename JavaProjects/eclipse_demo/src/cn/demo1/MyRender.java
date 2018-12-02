package cn.demo1;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class MyRender extends JLabel implements TableCellRenderer
{
   Icon icon;
   public MyRender(Icon icon)
   {
      this.icon = icon;
   }
   
@Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {
    //假设将第一列设为图标
   if(column == 0)
   {
      this.setIcon(icon);
    }
    return this;
}

}

