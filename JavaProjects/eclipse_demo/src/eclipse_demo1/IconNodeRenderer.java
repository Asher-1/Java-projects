package eclipse_demo1;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

class IconNodeRenderer extends DefaultTreeCellRenderer {  
	  
	  public Component getTreeCellRendererComponent(JTree tree, Object value,  
	      boolean sel, boolean expanded, boolean leaf, int row,  
	      boolean hasFocus) {  
	  
	    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
	        row, hasFocus);  
	  
	    Icon icon = ((IconNode) value).getIcon();  
	  
	    if (icon == null) {  
	      Hashtable icons = (Hashtable) tree.getClientProperty("title4.gif");  
	      String name = ((IconNode) value).getIconName();  
	      if ((icons != null) && (name != null)) {  
	        icon = (Icon) icons.get(name);  
	        if (icon != null) {  
	          setIcon(icon);  
	        }  
	      }  
	    } else {  
	      setIcon(icon);  
	    }  
	  
	    return this;  
	  }  
	}  
