package cn.detector.util;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

public class IconNode extends DefaultMutableTreeNode {  
	  
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Icon icon;  
	  
	  protected String iconName;  
	  
	  public IconNode() {  
	    this(null);  
	  }  
	  
	  public IconNode(Object userObject) {  
	    this(userObject, true, null);  
	  }  
	  
	  public IconNode(Object userObject, boolean allowsChildren, Icon icon) {  
	    super(userObject, allowsChildren);  
	    this.icon = icon;  
	  }  
	  
	  public void setIcon(Icon icon) {  
	    this.icon = icon;  
	  }  
	  
	  public Icon getIcon() {  
	    return icon;  
	  }  
	  
	  public String getIconName() {  
	    if (iconName != null) {  
	      return iconName;  
	    } else {  
	      String str = userObject.toString();  
	      int index = str.lastIndexOf(".");  
	      if (index != -1) {  
	        return str.substring(++index);  
	      } else {  
	        return null;  
	      }  
	    }  
	  }  
	  
	  public void setIconName(String name) {  
	    iconName = name;  
	  }  
	  
	}  
