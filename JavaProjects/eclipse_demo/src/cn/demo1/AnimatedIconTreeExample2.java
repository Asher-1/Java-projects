/**
 * 
 */
/**
 * @author A
 *
 */
package cn.demo1;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * @version 1.0 06/20/99
 */
public class AnimatedIconTreeExample2 extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

final static String CAT = "cat";

  final static String DOG = "dog";

  public AnimatedIconTreeExample2() {
    super("AnimatedIconTreeExample2");
    String[] strs = { "CARNIVORA", // 0
        "Felidae", // 1
        "Acinonyx jutatus  (cheetah)", // 2
        "Panthera leo  (lion)", // 3
        "Canidae", // 4
        "Canis lupus  (wolf)", // 5
        "Lycaon pictus  (lycaon)", // 6
        "Vulpes Vulpes  (fox)" }; // 7

    IconNode[] nodes = new IconNode[strs.length];
    for (int i = 0; i < strs.length; i++) {
      nodes[i] = new IconNode(strs[i]);
    }
    nodes[0].add(nodes[1]);
    nodes[0].add(nodes[4]);
    nodes[1].add(nodes[2]);
    nodes[1].add(nodes[3]);
    nodes[4].add(nodes[5]);
    nodes[4].add(nodes[6]);
    nodes[4].add(nodes[7]);

    // If you want to share the ImageIcon with a node.

    nodes[2].setIconName(CAT);
    nodes[3].setIconName(CAT);
    nodes[5].setIconName(DOG);
    nodes[6].setIconName(DOG);
    nodes[7].setIconName(DOG);

    Hashtable<String, ImageIcon> icons = new Hashtable<String, ImageIcon>();
    icons.put(CAT, new ImageIcon("Java2sAnimation.gif"));
    icons.put(DOG, new ImageIcon("Java2sAnimation.gif"));

    JTree tree = new JTree(nodes[0]);
    tree.putClientProperty("JTree.icons", icons);
    tree.setCellRenderer(new IconNodeRenderer());
    setImageObserver(tree, nodes, icons);

    JScrollPane sp = new JScrollPane(tree);
    getContentPane().add(sp, BorderLayout.CENTER);
  }

  private void setImageObserver(JTree tree, IconNode[] nodes, Hashtable<String, ImageIcon> icons) {
    Hashtable<String, Vector<IconNode>> observers = new Hashtable<String, Vector<IconNode>>();

    for (int i = 0; i < nodes.length; i++) {
      ImageIcon icon = (ImageIcon) nodes[i].getIcon();
      if (icon != null) {
        Vector<IconNode> repaintNodes = new Vector<IconNode>();
        repaintNodes.addElement(nodes[i]);
        icon
            .setImageObserver(new NodeImageObserver(tree,
                repaintNodes));
      } else {
        String iconName = nodes[i].getIconName();
        if (iconName != null) {
          Vector<IconNode> repaintNodes = (Vector<IconNode>) observers.get(iconName);
          if (repaintNodes == null) {
            repaintNodes = new Vector<IconNode>();
            observers.put(iconName, repaintNodes);
          }
          repaintNodes.addElement(nodes[i]);
        }
      }
    }

    Enumeration<String> e = observers.keys();
    while (e.hasMoreElements()) {
      String iconName = (String) e.nextElement();
      Vector<IconNode> repaintNodes = (Vector<IconNode>) observers.get(iconName);
      ImageIcon icon = (ImageIcon) icons.get(iconName);
      icon.setImageObserver(new NodeImageObserver(tree, repaintNodes));
    }
  }

  class NodeImageObserver implements ImageObserver {
    JTree tree;

    DefaultTreeModel model;

    Vector<IconNode> nodes;

    NodeImageObserver(JTree tree, Vector<IconNode> nodes) {
      this.tree = tree;
      this.model = (DefaultTreeModel) tree.getModel();
      this.nodes = nodes;
    }

    public boolean imageUpdate(Image img, int flags, int x, int y, int w,
        int h) {
      if ((flags & (FRAMEBITS | ALLBITS)) != 0) {
        Enumeration<IconNode> e = nodes.elements();
        while (e.hasMoreElements()) {
          TreeNode node = (TreeNode) e.nextElement();
          TreePath path = new TreePath(model.getPathToRoot(node));
          Rectangle rect = tree.getPathBounds(path);
          if (rect != null) {
            tree.repaint(rect);
          }
        }
      }
      return (flags & (ALLBITS | ABORT)) == 0;
    }
  }

  public static void main(String args[]) {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception evt) {}
  
    AnimatedIconTreeExample2 frame = new AnimatedIconTreeExample2();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    frame.setSize(280, 200);
    frame.setVisible(true);
  }
}

class IconNodeRenderer extends DefaultTreeCellRenderer {

  public Component getTreeCellRendererComponent(JTree tree, Object value,
      boolean sel, boolean expanded, boolean leaf, int row,
      boolean hasFocus) {

    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
        row, hasFocus);

    Icon icon = ((IconNode) value).getIcon();

    if (icon == null) {
      Hashtable<?, ?> icons = (Hashtable<?, ?>) tree.getClientProperty("JTree.icons");
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

class IconNode extends DefaultMutableTreeNode {

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