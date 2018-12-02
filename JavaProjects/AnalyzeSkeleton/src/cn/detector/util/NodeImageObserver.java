package cn.detector.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class NodeImageObserver implements ImageObserver {  
    JTree tree;  
  
    DefaultTreeModel model;  
  
    TreeNode node;  
  
    public NodeImageObserver(JTree tree, TreeNode node) {  
      this.tree = tree;  
      this.model = (DefaultTreeModel) tree.getModel();  
      this.node = node;  
    }  
  
    public boolean imageUpdate(Image img, int flags, int x, int y, int w,int h) {  
      if ((flags & (FRAMEBITS | ALLBITS)) != 0) {  
        TreePath path = new TreePath(model.getPathToRoot(node));  
        Rectangle rect = tree.getPathBounds(path);  
        if (rect != null) {  
          tree.repaint(rect);  
        }  
      }  
      return (flags & (ALLBITS | ABORT)) == 0;  
    }  
  }  
