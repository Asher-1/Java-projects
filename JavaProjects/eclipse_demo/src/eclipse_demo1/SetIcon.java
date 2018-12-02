package eclipse_demo1;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;


public class SetIcon extends JFrame {  
	  
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public SetIcon() {  
	    super("SetIcon");  
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
	  
	    nodes[2].setIcon(new ImageIcon("1.jpg"));  
	    nodes[3].setIcon(new ImageIcon("1.jpg"));  
	    nodes[5].setIcon(new ImageIcon("1.jpg"));  
	    nodes[6].setIcon(new ImageIcon("1.jpg"));  
	    nodes[7].setIcon(new ImageIcon("1.jpg"));  
	  
	    JTree tree = new JTree(nodes[0]);  
	    tree.setCellRenderer(new IconNodeRenderer());  
	    setImageObserver(tree, nodes);  
	  
	    JScrollPane sp = new JScrollPane(tree);  
	    getContentPane().add(sp, BorderLayout.CENTER);  
	  }  
	  
	  private void setImageObserver(JTree tree, IconNode[] nodes) {  
	    for (int i = 0; i < nodes.length; i++) {  
	      ImageIcon icon = (ImageIcon) nodes[i].getIcon();  
	      if (icon != null) {  
	        icon.setImageObserver(new NodeImageObserver(tree, nodes[i]));  
	      }  
	    }  
	  }  
	  

	  
	  public static void main(String args[]) {  
	    try {  
	        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  
	    } catch (Exception evt) {}  
	  
	    SetIcon frame = new SetIcon();  
	    frame.addWindowListener(new WindowAdapter() {  
	      public void windowClosing(WindowEvent e) {  
	        System.exit(0);  
	      }  
	    });  
	    frame.setSize(280, 200);  
	    frame.setVisible(true);  
	  }  
	}  
	  

	  
