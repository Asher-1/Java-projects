/**
 * @author ½��
 */
package cn.detector.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import cn.detector.core.*;
import cn.detector.util.*;
/**
 * @author ½��
 */
public class WindowOperation extends JFrame implements TreeSelectionListener{
	private static final long serialVersionUID = 1L;
	private JFileChooser chooser;
	//�ڵ����
	IconNode root,chosen,child;
	//Ĭ�ϵ���ģʽ
	DefaultTreeModel model;
	JTree tree;
	//�����ַ�������������ͼƬ�����ļ���Ŀ¼�ľ���·��
	Vector<String> pathStringsVector = new Vector<String>();
	//�����ַ�������������Ҷ�ӽڵ����ƣ�������ͼƬ�ļ�����
	Vector<String> leafStringsVector = new Vector<String>();
	//�������갴ť
	private JButton saveButton;
	//�����ļ��а�ť
	private JButton inPutFileButton;
	//��ͼ��ť
	private JButton printScreenButton;
	//�л�Ϊ���߷�ʽ��ť,����Ҫ��¶���ⲿ��myLabel����ʹ��
	public JButton changeToDrawLines;
	//�л�Ϊ���㷽ʽ��ť,����Ҫ��¶���ⲿ��myLabel����ʹ��
	public JButton changeToDrawAnchor;
	//�л�Ϊ���㷽ʽ��ť,����Ҫ��¶���ⲿ��myLabel����ʹ��
	public JButton changeToDrawPoints;
	//��Ҫ��¶���ⲿ��myLabel����ʹ��
	public JButton inPutCoordinateButton;
	//��Ҫ��¶���ⲿ��myLabel����ʹ��
	public JButton resetButton;
	//��Ҫ��¶���ⲿ��myLabel����ʹ��
	public JButton resetAllButton;
	//��Ҫ��¶���ⲿ��myLabel����ʹ��,ͨ������mystop�����ж���һ�λ������µ���ϵ
	public JButton linkButton;
	public JButton paintNextButton;
	public JButton font_Button;
	public JButton color_Button;
	public JButton parameter_Button;
	public JPanel panel;
	public MyLabel myLabel;
	public Box box;
	public String parameter = "15"; // �����������
	//���ڴ洢����װ��File�����ͼƬ�ļ�
	File[] picFiles;
	//ͼƬ�ļ��ľ���·��
	private String picAbsPath;
	//ͼƬ�ļ�����Ŀ¼�ľ���·��
	public String path_contains_pic; //�ṩ��start�����ڴ���ѡͼƬ�����ļ���
	//ͼƬ�ļ�������
	private String picName = null;
	//��������ֵ���ı��ļ��ľ���·��
	public String txtPath = null;
	//�������ļ������ƣ����ļ�Ŀ¼���ŵ��Ǵ���������ı��ļ�
	private String fileName = null;
	public String thick = "4";
	
	private boolean isFile_Selected = false;

	public String getThick() { // ��ȡ������ϸ
		return thick;
	}
	private String color = "green";
	public String getColor() { // ��ȡ������ɫ����
		return color;
	}
	//�޲ι��캯��
	WindowOperation(){}
	//�вι��캯��
	WindowOperation(String title){
	 	//���ô����С����СΪ��Ļ��С
    	UiUtil.setFrameSize(this);
    	//���ô���ͼ��
    	UiUtil.setFrameImage(this);
		//���ô����������
		setTitle(title);
		//setResizable(false);
		
		//��ʼ������
    	init();
   
    	//���ô��ڲ���Ϊ�߿򲼾�
    	setLayout(new BorderLayout());
    	// ����Ĭ���˳�ģʽ
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	public MyLabel getMyLabel() {
		return myLabel;
	}
	
	/* ���� Javadoc��
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	//ʵ������л���Ҷ�ڵ�ʱ�����л�ͼƬʱ������ͼƬ���غ�ͼƬ�ϻ��Ƶ�����ı���
	//ʵ��valueChanged���������ļ����������ڵ�
	public void valueChanged(TreeSelectionEvent e){
		//��ȡ�û����һ��ѡ��Ľڵ�
		IconNode node=(IconNode)tree.getLastSelectedPathComponent();
		//������ڵ��������ʱ��node��Ϊ�գ��ڴ��ݴ�,���ؿ�
		if(node == null){
			return;
		}
		if(node.isLeaf()){
			//���linesVector�����ǿգ�ִ�����´���
			if(!(myLabel.linesVector.isEmpty())){
			
				int n = JOptionPane.showConfirmDialog(myLabel, "����ؽڱ��δ���棬�Ƿ����ڱ��棿",
					 "��������", JOptionPane.YES_NO_CANCEL_OPTION);
				if(n == JOptionPane.CANCEL_OPTION){
					return;
				}
				else if(n == JOptionPane.NO_OPTION){
					//��ȡ��һ��ͼƬ��������������ֵ
					readNextPicture(e);	
				}
				else if(n == JOptionPane.YES_OPTION){
					//�������汣��������Ϣ���󣬴���ͼƬ�����ļ���·��path_contains_pic��ͼƬ����picName�ͱ�ǩmyLabel
					Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(myLabel.pointsVector); // ����pointsVector�ӹؽڵ���������
					Vector<Line> linesVector_Child = new Vector<Line>(myLabel.linesVector); // ����linesVector���߿���������
					myLabel.pointsVector_parent.add(pointsVector_Child); // ���ӹؽڵ�����������븸�ؽڵ�������
					myLabel.linesVector_parent.add(linesVector_Child); // �����߿�����������븸�߿�������
				
					Save_Information save_Information = new Save_Information(path_contains_pic,txtPath,fileName,myLabel);
					save_Information.save(); // ���ñ��淽������������ֵ
					//��ո�����
					myLabel.pointsVector_parent.clear();
					myLabel.linesVector_parent.clear();
					myLabel.reset();//�������ò�������ջ���
					readNextPicture(e);	// ��ȡ��һ��ͼƬ������������ֵ
				}
				//���򣬲��ٴα�������
				else{
					readNextPicture(e);	//��ȡ��һ��ͼƬ������������ֵ
					return;
				}
			}
			//����ǵ�һ�ε��Ҷ�ڵ㣬linesVectorΪ��ֵ����ֱ�Ӷ�ȡͼƬ�������ѱ�������
			else{
				readNextPicture(e); //��ȡ��һ��ͼƬ������������ֵ
			}
		}
		
		else if(!(node.isLeaf()) && node != null){
			readNextPicture(e); //��ȡ��һ��ͼƬ������������ֵ
		}
		else{
			return;
		}
		
		
}
	/**
	 * @param e
	 */
	//������һ��ͼƬ��label��
	public void readNextPicture(TreeSelectionEvent e) {
		//�����������
		myLabel.pointsVector_parent.clear();
		myLabel.linesVector_parent.clear();
		myLabel.reset();//�������ò�������ջ���
		myLabel.repaint();
		if(e.getSource() == tree)
		{	//��ȡ�û����һ��ѡ��Ľڵ�
			IconNode node=(IconNode)tree.getLastSelectedPathComponent();
			//����ýڵ��Ƿ�Ҷ�ӽڵ����node�ڵ�Ϊ�գ�ֱ�ӷ��ؿ�
			if(node == null || !(node.isLeaf())){
				if(isFile_Selected){
					
					loadPic_OnLabel(node);
					isFile_Selected = false;
				}
				else{
					
					return;
				}
			}
			//�����Ҷ�ڵ㣬��ִ�����´��뽫ͼƬ���ص�myLabel��
			else{
				loadPic_OnLabel(node);	
			}
		}
	}
	private void loadPic_OnLabel(IconNode node) {
		//ѭ�����������ﲻ��Ҫ��i�ֲ���������++�������������Զ�ѭ����������
		for(int i=0; i<leafStringsVector.size();i++){
		//��ȡ��ѡĿ¼�ļ�������
		String tmp = (String) leafStringsVector.elementAt(i);
		if(node.getParent() == null){
			System.out.println(node.getParent()+"loadPic_OnLabel()�����쳣");// ���ڵ���
			return;
		}
		//���Ҷ�ڵ�ĸ��ڵ�������leafStringsVector�������Ӧ�±���ļ���Ŀ¼������ͬ
		if(tmp.equals(node.getParent().toString())){
			//��ȡpathStringsVector����������leafStringsVector������Ӧ�±���ͬ���ַ��������ļ���Ŀ¼����·��
			path_contains_pic = (String) pathStringsVector.elementAt(i);
			//����ѡ�ڵ�����ִ���picName
			picName = node.toString();
			//��������ȡͼƬ�ľ���·��
			picAbsPath = new String(path_contains_pic + File.separator + picName);
			
			
			//����ͼƬ����
			ImageIcon imageIcon = new ImageIcon(picAbsPath);
			double scale = 1.0*imageIcon.getIconWidth() / imageIcon.getIconHeight();
			//��ȡmyLabel�ؼ��Ŀ�͸�
			//int width = myLabel.getWidth();
			int height = myLabel.getHeight();
			
			
			//����imageIcon����ĳߴ���myLabel�ؼ��ĳߴ���ͬ
			imageIcon.setImage(imageIcon.getImage().getScaledInstance((int)(height*scale),height,Image.SCALE_DEFAULT));
			
			myLabel.setHorizontalAlignment(SwingConstants.CENTER); // ����ͼƬ������ʾ,������Ҳ����0
			
			myLabel.setIcon(imageIcon); // ����myLabel�ؼ��ı���ͼƬ��������ͼƬ��׼���ؽ�ע��
			//Ԥ������Ϣ����
			{
				//��ȡ�������ļ������ƣ��������ͼƬ������ͬ,��ȥ��ͼƬ����picName�ĺ�׺��
				if(picName.endsWith(".png")){
					fileName = picName.replaceAll(".png", "");
				}
				else if(picName.endsWith(".jpg")){
					fileName = picName.replaceAll(".jpg", "");
				}
				else if(picName.endsWith(".bmp")){
					fileName = picName.replaceAll(".bmp", "");
				}
				else if(picName.endsWith(".gif")){
					fileName = picName.replaceAll(".gif", "");
				}
				else if(picName.endsWith(".jpeg")){
					fileName = picName.replaceAll(".jpeg", "");
				}
				//��ֹͼƬ�ļ��ĺ�׺���������ϵ�����һ�ֵ��£�fileName��ֵ��ɴ���txtPath�ַ�������׼ȷ
				//ֱ������׺��һ�𸳸�fileName
				else{
					fileName = picName;
				}
				
				//��ȡ���������ı��ļ�����
				String txtName = new String(fileName + ".txt");
				//��ȡ��д����ı��ļ��ľ��Ե�ַ
				txtPath = new String(path_contains_pic + File.separator + fileName +File.separator + txtName);
			
				}
			//�ҵ�ͼƬ�ļ������غ󣬱��˳�ѭ�������Ч��
			break;
			}
		}
	}
	
    private void setImageObserver(JTree tree, IconNode[] nodes) {  
		    for (int i = 0; i < nodes.length; i++) {  
		      ImageIcon icon = (ImageIcon) nodes[i].getIcon();  
		      if (icon != null) {  
		        icon.setImageObserver(new NodeImageObserver(tree, nodes[i]));  
		      }  
		    }  
		  }
	
	/**
	 * 
	 */
	// ��ʼ������
	private void init() {
		//����myLabel�ؼ�������ӵ����������λ��
		myLabel = new MyLabel();
		myLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//��������������ʽ��������ƶ��������ϣ����������
		add(new JScrollPane(myLabel),BorderLayout.CENTER); // ��myLabel��ǩ������봰��
		
		Dimension size = new Dimension(this.getWidth()/18,this.getHeight()/20); // ���尴ť�ߴ�
		Font font = new Font("����", Font.BOLD, 15); // ���������С����ϸ������
			
//		// ����������ϸ������
//		String[] cord_Items = {"1","2","3","4","5","6"};
//		JComboBox<String> box_thick = new JComboBox<String>(cord_Items);   
//		box_thick.setPreferredSize(size); // ���ð�ť��С
//	    box_thick.setFont(font); //����������ʾ
//	    box_thick.addItemListener(new ItemListener() {// �л�������ϸ���¼�������
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//	
//				thick = e.getItem().toString(); // ��ȡ��ѡ���������
//				if (e.getStateChange() == ItemEvent.DESELECTED) {
//
//					thick = "3"; //Ĭ����������Ϊ3.0f��
//				}
//				myLabel.repaint(); // �����ػ湦��ʵ�֣�����ʵʱ��ʾ
//			}
//		});
//		
//	    // ����������ɫ����������
//	    String[] color_Items = {"��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ"};
//		JComboBox<String> box_color = new JComboBox<String>(color_Items);  
//		box_color.setPreferredSize(size); // ���ð�ť��С
//		box_color.setFont(font); //����������ʾ
//		box_color.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {// �л�������ɫ���¼�������
//				String source = e.getItem().toString(); // ��ȡ��ѡ���������
//				if (source == "��ɫ") {
//					
//					color = "red";
//					
//				} else if (source == "��ɫ") {
//
//					color = "green";
//					
//				} else if (source == "��ɫ") {
//
//					color = "blue";
//				} else if (source == "��ɫ") {
//
//					color = "black";
//					
//				}
//				else if (source == "��ɫ") {
//				
//					color = "white";
//				
//				}
//				else if (source == "��ɫ") {
//					
//					color = "yellow";
//					
//				}
//				if (e.getStateChange() == ItemEvent.DESELECTED) {
//
//					color = "blue";
//				}
//				myLabel.repaint(); // �����ػ湦��ʵ�֣�����ʵʱ��ʾ
//			}
//		});
//		
			
		// ��ע����
		String[] param_Items = {"15","14","13","12","11","10","9","8","7","6",
								"5","4","3","2","16","17","18","19","20","21",
								"22","23","24","25","30","32","34","35","40"};
		JComboBox<String> boxParam = new JComboBox<String>(param_Items);  
		boxParam.setPreferredSize(new Dimension(this.getWidth()/40,this.getHeight()/20)); // ���ð�ť��С
		boxParam.setFont(font); //����������ʾ
		boxParam.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {// �л�������ɫ���¼�������
				parameter = e.getItem().toString(); // ��ȡ��ѡ���������
				myLabel.repaint(); // �����ػ湦��ʵ�֣�����ʵʱ��ʾ
			}
		});
			
		
		//���������ļ��а�ť
		inPutFileButton = new JButton("�����ļ���");
		//inPutFileButton.setPreferredSize(new Dimension(140,50));
		inPutFileButton.setPreferredSize(size);
		inPutFileButton.setFont(font);
		inPutFileButton.addActionListener(new ActionListener() {
			// Ϊ�����ļ��а�ť��Ӽ�����
			@Override
			public void actionPerformed(ActionEvent e) {
				// �����ļ�����
				File fileDir = null;
				chooser = new JFileChooser();
				// �����ļ�ѡ��ģʽΪ�ȿ���ѡ��Ŀ¼�ļ���Ҳ����ѡ���ļ�
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				// ����Ĭ�ϴ�·��
//				chooser.setCurrentDirectory(new File("G:\\develop\\cocosPNG"));
				chooser.setCurrentDirectory(new File("�����ļ�"));
				chooser.setMultiSelectionEnabled(true);
				// ��ʾ�ļ�ѡ��Ի���
				int result = chooser.showOpenDialog(null);
				// ���ѡ���ļ��в�ȷ��
				if (result == JFileChooser.APPROVE_OPTION) {
					fileDir = chooser.getSelectedFile();
					File[] myFiles = chooser.getSelectedFiles();
					// ��ȡ�û�ѡ����ļ��еľ���·��
					// ����û�ѡ������ļ�Ŀ¼�������getSelectedFile��������ȡ��ѡ�ļ�Ŀ¼�ľ���·��������װ���ļ����󷵻�
					if (fileDir.isDirectory()) {
						// �˴���õ�·�������磺e:\\abc\\ab\\c��ʽ�ľ���·��
						fileDir = chooser.getSelectedFile();

						// ����ǰ�ļ���Ŀ¼�ľ���·���洢������Ϊ������ͼƬ��ȡ��������
						String picPathStrings = new String(fileDir.getAbsolutePath());// ��TreeSelectionListener��������϶�ȡͼƬ���ļ�����Ϊ����·�����ڶ�ȡ

						// ���ļ���Ŀ¼�ľ���·�����뵽pathStringsVector������
						pathStringsVector.add(picPathStrings);

						// ��ȡ�ļ���Ŀ¼�ľ���·���������ļ���Ŀ¼������
						String[] filename = picPathStrings.split("\\\\");
						// ����ѡ�ļ�����Ŀ¼�����ƻ�����ѡ�ļ���Ŀ¼�����ƴ����ַ�������
						String leafStrings = new String(filename[filename.length - 1]);

						// ����ѡ�ļ������ļ���Ŀ¼�����ƻ�����ѡ�ļ���Ŀ¼�����Ƽ���leafStringsVector������
						leafStringsVector.add(leafStrings);

						// �����ӽڵ㣺��leafStringsΪ�ֽڵ�����
						child = new IconNode(leafStrings);

						// �����ļ���������ֻ��ʾͼƬ��ʽ�ļ�
						FileAcceptFilter fileAcceptFilter = new FileAcceptFilter();
						// �������ͼƬ��ʽ
						fileAcceptFilter.setExtendName("java");

						isFile_Selected = false;
						picFiles = fileDir.listFiles(fileAcceptFilter);
						// ����ڵ����飬���ڴ洢�ڵ�
						IconNode treeNodes[];

						// �����ڵ������Ա���forѭ�����Դ˸���ͼƬ�������ڵ�
						treeNodes = new IconNode[picFiles.length];

						// ѭ���ļ����飬������ڵ�
						for (int i = 0; i < picFiles.length; i++) {// ѭ��ͼ���ļ�

							treeNodes[i] = new IconNode(picFiles[i].getName());// picFiles[i].getName()��ȡ�����ļ��ĵ�ȫ��
							ImageIcon icon = new ImageIcon(picPathStrings + File.separator + picFiles[i].getName()); // ��ȡͼ��
							// ����ͼ���С
							icon.setImage(icon.getImage().getScaledInstance((int) (box.getWidth() / 3.0),
									(int) (box.getWidth() / 4.0), Image.SCALE_DEFAULT));

							treeNodes[i].setIcon(icon); // Ϊ���ڵ�����ͼ������ͼ

							child.add(treeNodes[i]); // ����ļ���ͼƬ�ļ�Ҷ�ڵ㵽�ӽڵ�
						}

						tree.setCellRenderer(new IconNodeRenderer()); // �������ڵ�ͼ����Ⱦ��
						setImageObserver(tree, treeNodes); // �������ؼ���ͼƬ����

						// ��ȡ��ǰ�û�ѡ��Ľڵ㣬Ŀ����ѡ��child�ĸ��ڵ�
						chosen = (IconNode) tree.getLastSelectedPathComponent();
						if (chosen == null) {
							// �����ǰ�û�δѡ��ڵ㣬��ѡ��root���ڵ���Ϊ���ڵ�
							chosen = root;
						}
						// ��child�ӽڵ���ӵ�chosen���ڵ�
						model.insertNodeInto(child, chosen, 0);
					}

					// ����û�ѡ������ļ��������getCurrentDirectory��������ȡ��ѡ�ļ�����Ŀ¼�ľ���·��������װ���ļ����󷵻�
					else if (fileDir.isFile() | myFiles.length != 1) {
						isFile_Selected = true;
						// �˴���õ�·�������磺e:\\abc\\ab\\c��ʽ�ľ���·��
						fileDir = chooser.getCurrentDirectory();
						// ����ǰ�ļ���Ŀ¼�ľ���·���洢������Ϊ������ͼƬ��ȡ��������
						String picPathStrings = new String(fileDir.getAbsolutePath());// ��TreeSelectionListener��������϶�ȡͼƬ���ļ�����Ϊ����·�����ڶ�ȡ
						// ���ļ���Ŀ¼�ľ���·�����뵽pathStringsVector������
						pathStringsVector.add(picPathStrings);

						// ��ȡ�ļ���Ŀ¼�ľ���·���������ļ���Ŀ¼������
						String[] filename = picPathStrings.split("\\\\");

						// ����ѡ�ļ�����Ŀ¼�����ƻ�����ѡ�ļ���Ŀ¼�����ƴ����ַ�������
						String leafStrings = new String(filename[filename.length - 1]);
						// ����ѡ�ļ������ļ���Ŀ¼�����ƻ�����ѡ�ļ���Ŀ¼�����Ƽ���leafStringsVector������
						leafStringsVector.add(leafStrings);

						// �����ӽڵ㣺��leafStringsΪ�ֽڵ�����
						child = new IconNode(leafStrings);

						// �����ļ���������ֻ��ʾͼƬ��ʽ�ļ�
						FileAcceptFilter fileAcceptFilter = new FileAcceptFilter();
						// �������ͼƬ��ʽ
						fileAcceptFilter.setExtendName("java");

						IconNode treeNode[]; // ����ڵ����飬���ڴ洢�ڵ�
						int file_Number = myFiles.length;
						// �����ڵ������Ա���forѭ�����Դ˸���ͼƬ�������ڵ�
						treeNode = new IconNode[file_Number];
						// ��������ȡ�ļ���ͼƬ�ļ�Ҷ�ڵ�
						for (int i = 0; i < file_Number; i++) {

							treeNode[i] = new IconNode(myFiles[i].getName());// ��ȡ����ѡ���ļ��ĵ�ȫ��
							ImageIcon icon = new ImageIcon(fileDir + File.separator + treeNode[i].toString());

							// ����ͼ���С
							icon.setImage(icon.getImage().getScaledInstance((int) (box.getWidth() / 3.0),
									(int) (box.getWidth() / 4.0), Image.SCALE_DEFAULT));
							treeNode[i].setIcon(icon); // Ϊ���ڵ�����ͼ������ͼ

							child.add(treeNode[i]); // ����ļ���ͼƬ�ļ�Ҷ�ڵ㵽�ӽڵ�
						}
						tree.setCellRenderer(new IconNodeRenderer());
						setImageObserver(tree, treeNode);

						// ��ȡ��ǰ�û�ѡ��Ľڵ㣬Ŀ����ѡ��child�ĸ��ڵ�
						chosen = (IconNode) tree.getLastSelectedPathComponent();
						if (chosen == null) {
							// �����ǰ�û�δѡ��ڵ㣬��ѡ��root���ڵ���Ϊ���ڵ�
							chosen = root;
						}
						// ��child�ӽڵ���ӵ�chosen���ڵ�
						model.insertNodeInto(child, chosen, 0);

					}

				}
			}
		});
		
		//�������߰�ť
		linkButton =new JButton("�� ��");
		//����¼������¼�
		linkButton.addActionListener(myLabel);
		linkButton.setPreferredSize(size); // ���ð�ť��С
		linkButton.setFont(font); //�����������ͣ���С����ϸ
		linkButton.setBackground(Color.blue); // ���ð�ť����ɫ
		
		//�����������갴ť
		saveButton = new JButton("��������");
		saveButton.setPreferredSize(size);
		saveButton.setFont(font);
		//Ϊ���水ť��Ӽ����¼�
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ֹ�û��Ҳ�����ť���粢δ��ͼƬ�ͽ�����صİ�ť����
				if(fileName == null || txtPath == null){
					JOptionPane.showMessageDialog(null, "��δ��ͼƬ���޷�������Ӧ��ͼƬ��Ϣ", "������Ϣ", JOptionPane.ERROR_MESSAGE);
					myLabel.reset(); // �������ò�������ջ���
					myLabel.repaint(); //�ػ����
					return;
				}
				
				//�Զ�ε�����ƻ�����һ����ť���ݴ��Դ���
				if(!(myLabel.pointsVector.isEmpty()) && !(myLabel.linesVector.isEmpty())){
					Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(myLabel.pointsVector); // ����pointsVector�ӹؽڵ���������
					for (Iterator<MyPoint> iterator = pointsVector_Child.iterator(); iterator.hasNext();) {
						MyPoint myPoint = (MyPoint) iterator.next();
						myPoint.parameter = Integer.parseInt(parameter);
						
					}
					Vector<Line> linesVector_Child = new Vector<Line>(myLabel.linesVector); // ����linesVector���߿���������
					myLabel.pointsVector_parent.add(pointsVector_Child); // ���ӹؽڵ�����������븸�ؽڵ�������
					myLabel.linesVector_parent.add(linesVector_Child); // �����߿�����������븸�߿�������
				}
				
				
				//[start]�������ê�㣨3����->��ۣ�3����-> �ұۣ�3����-> ���ȣ�2����-> ���ȣ�2����˳�����Źؽڵ�
				/*
				 * �������Žڵ�˳��
				 * ʵ����������򱣴棬����˳��Ϊ��
				 * ê�㣨3����->��ۣ�3����-> �ұۣ�3����-> ���ȣ�2����-> ���ȣ�2����
				 * 
				 * */ 
				Vector<Vector<MyPoint>> tempVector_Parent = new Vector<Vector<MyPoint>>(myLabel.pointsVector_parent);//����Ԫ��������������,��ʱ��������
				myLabel.pointsVector_parent.clear();// ��վɵĸ��ڵ�����
				
				for (int j = 0; j < tempVector_Parent.size(); j++) {
					Vector<MyPoint> tempVector_Child = new Vector<MyPoint>();// ���ڻ���ؽڵ���ӻ�������
					Vector<MyPoint> pointsVector_Child = (Vector<MyPoint>) tempVector_Parent.elementAt(j); // ��ȡ������
					Vector<Line> linesVector_Child = (Vector<Line>) myLabel.linesVector_parent.elementAt(j); // ��ȡ��Ӧ������		
					
					Coordinate_Transformation transaction = new Coordinate_Transformation(linesVector_Child,pointsVector_Child);
					transaction.setParameter(Integer.parseInt(parameter));
					
					transaction.change_coordinate();// ����ת�����귽��
					transaction.divide_Layer();// ���÷ֲ㷽������ÿ��Ĺؽڵ㰴�����ص�ֱ�������������
					
					//��ȡ��������
					Vector<MyPoint> vector1 = transaction.getVector1();
					Vector<MyPoint> vector2 = transaction.getVector2();
					Vector<MyPoint> vector3 = transaction.getVector3();
					Vector<MyPoint> vector4 = transaction.getVector4();
					Vector<MyPoint> vector5 = transaction.getVector5();
					
					// ��ê�������������
					tempVector_Child.add(new MyPoint(vector1.get(0)));
					tempVector_Child.add(new MyPoint(vector1.get(1)));
					tempVector_Child.add(new MyPoint(vector1.get(2)));
					
					// ��˳�������۹ؽڵ�
					MyPoint p1 = new MyPoint();
					MyPoint p2 = new MyPoint();
					MyPoint p3 = new MyPoint();
					for (int i = 0; i < 3; i++) {
						if(vector2.get(i).order == 1){
							p1 = new MyPoint(vector2.get(i));
						}
						else if(vector2.get(i).order == 2){
							p2 = new MyPoint(vector2.get(i));
							
						}
						else if(vector2.get(i).order == 3){
							p3 = new MyPoint(vector2.get(i));
							
						}
					}
					tempVector_Child.add(p1);
					tempVector_Child.add(p2);
					tempVector_Child.add(p3);
					
					// ��˳������ұ۹ؽڵ�
					MyPoint p4 = new MyPoint();
					MyPoint p5 = new MyPoint();
					MyPoint p6 = new MyPoint();
					for (int i = 0; i < 3; i++) {
						if(vector3.get(i).order == 1){
							p4 = new MyPoint(vector3.get(i));
						}
						else if(vector3.get(i).order == 2){
							p5 = new MyPoint(vector3.get(i));
							
						}
						else if(vector3.get(i).order == 3){
							p6 = new MyPoint(vector3.get(i));
							
						}
					}
					tempVector_Child.add(p4);
					tempVector_Child.add(p5);
					tempVector_Child.add(p6);
					
					// ��˳��������ȹؽڵ�
					MyPoint p7 = new MyPoint();
					MyPoint p8 = new MyPoint();
					for (int i = 0; i < 2; i++) {
						if(vector4.get(i).order == 1){
							p7 = new MyPoint(vector4.get(i));
						}
						else if(vector4.get(i).order == 2){
							p8 = new MyPoint(vector4.get(i));
						}
					}
					tempVector_Child.add(p7);
					tempVector_Child.add(p8);
					
					// ��˳��������ȹؽڵ�
					MyPoint p9 = new MyPoint();
					MyPoint p10 = new MyPoint();
					for (int i = 0; i < 2; i++) {
						if(vector5.get(i).order == 1){
							p9 = new MyPoint(vector5.get(i));
						}
						else if(vector5.get(i).order == 2){
							p10 = new MyPoint(vector5.get(i));
						}
					}
					tempVector_Child.add(p9);
					tempVector_Child.add(p10);
					int i = 1;
					for (Iterator<MyPoint> iterator = tempVector_Child.iterator(); iterator.hasNext();) {
						MyPoint myPoint = (MyPoint) iterator.next();
						
						if(i >= 4 && i <= 6){
							myPoint.body_Parts = 1; // ��¼�˹ؽڵ�Ϊ��۽ڵ�
						}
						else if(i >= 7 && i <= 9){
							myPoint.body_Parts = 2; // ��¼�˹ؽڵ�Ϊ�ұ۽ڵ�
						}
						else if(i >= 10 && i <= 11){
							myPoint.body_Parts = 3; // ��¼�˹ؽڵ�Ϊ���Ƚڵ�
						}
						else if(i >= 12 && i <= 13){
							myPoint.body_Parts = 4; // ��¼�˹ؽڵ�Ϊ���Ƚڵ�
						}
						i++;
						
					}
					
					myLabel.pointsVector_parent.add(tempVector_Child); // �����ź�ؽڵ�������ʱ�����ڸ��ؽڵ㻺��������
					}
				
				/*
				 * ���Ͼ�Ϊ���Ŵ��룬Ŀ����Ϊ�˰�����ڵ�˳�򱣴�������Ϣ
				 * 
				 * */ 
				//[end]
				
				//�������汣��������Ϣ���󣬴���ͼƬ�����ļ���·��path_contains_pic���ı��ļ����ڵľ���·����txtPath �� ��ǩmyLabel
				Save_Information save_Information = new Save_Information(path_contains_pic,txtPath,fileName,myLabel);
				save_Information.save(); // ���ñ��淽������������ֵ
				myLabel.reset();//�������ò�������ջ���
			}
		});
		
		//�����������갴ť
		resetButton = new JButton("���õ�ǰ");
		//resetButton.setPreferredSize(new Dimension(120,50));
		resetButton.setPreferredSize(size);
		resetButton.setFont(font);
		resetButton.addActionListener(myLabel);
		
		resetAllButton = new JButton("����ȫ��");
		//resetAllButton.setPreferredSize(new Dimension(120,50));
		resetAllButton.setPreferredSize(size);
		resetAllButton.setFont(font);
		//Ϊ��ť����¼������¼���������Ϊʵ����ActionListener�ӿڵ�MyLabel�����ɵĶ���
		resetAllButton.addActionListener(myLabel);
		
		//������ͼ��ť
		printScreenButton = new JButton("��ͼ");
		printScreenButton.setPreferredSize(size);
		printScreenButton.setFont(font);
		//Ϊ��ͼ��ť��Ӽ�����
		printScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//������ͼ���壬���н�ͼ����
				if(txtPath != null){
				new AWTCutPicture().setMypic(txtPath);
				}
				else{
					JOptionPane.showMessageDialog(null, "�ļ���δ������û�δ��ͼƬ���޷��ҵ������ͼ", "������Ϣ", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//��������ؽڰ�ť
		inPutCoordinateButton = new JButton("����ؽ�");
		inPutCoordinateButton.setPreferredSize(size);
		inPutCoordinateButton.setFont(font);
		//Ϊ����ؽڰ�ť��inPutCoordinateButton��Ӽ����¼���
		//����myLabel���������Ӵ˰�ť�������˲˵�������myLabel�����������������꣬�����»���������ͼƬ�� 
		inPutCoordinateButton.addActionListener(myLabel);	
		
		changeToDrawLines = new JButton("��������");	
		changeToDrawLines.setPreferredSize(size);
		changeToDrawLines.setFont(font);
		changeToDrawLines.setBackground(Color.blue);
		changeToDrawLines.addActionListener(myLabel);
		
		 
		changeToDrawAnchor = new JButton("��ê��");
		changeToDrawAnchor.setPreferredSize(size);
		changeToDrawAnchor.setFont(font);
		changeToDrawAnchor.setBackground(Color.blue);
		changeToDrawAnchor.addActionListener(myLabel);
		
		changeToDrawPoints = new JButton("���ؽڵ�");
		changeToDrawPoints.setPreferredSize(size);
		changeToDrawPoints.setFont(font);
		changeToDrawPoints.setBackground(Color.blue);
		changeToDrawPoints.addActionListener(myLabel);
		
		paintNextButton = new JButton("����һ��");
		//paintNextButton.setPreferredSize(new Dimension(200,50));
		paintNextButton.setPreferredSize(size);
		paintNextButton.setFont(font);
		paintNextButton.setBackground(Color.blue);
		paintNextButton.addActionListener(myLabel);
		
		font_Button = new JButton("������ϸ");
		//font_Button.setPreferredSize(new Dimension(120,50));
		font_Button.setPreferredSize(size);
		font_Button.setFont(font);
		font_Button.addActionListener(myLabel);
		
		color_Button = new JButton("������ɫ");
		//color_Button.setPreferredSize(new Dimension(120,50));
		color_Button.setPreferredSize(size);
		color_Button.setFont(font);
		color_Button.addActionListener(myLabel);
		
		
		panel = new JPanel(); // ����panel��壬���ð�ť
		//panel.setLayout(new FlowLayout());
		panel.setLayout(new GridLayout());
		//����ť��ӵ������
//		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
//		panel.setLayout(boxLayout);
		
		JLabel para_label = new JLabel("��ע����:");
		para_label.setPreferredSize(size);
		para_label.setForeground(Color.blue);
		para_label.setFont(font);
		
		panel.add(para_label);
		panel.add(boxParam);
		panel.add(inPutFileButton);
		panel.add(changeToDrawLines);
		panel.add(changeToDrawAnchor);
		panel.add(changeToDrawPoints);
		panel.add(linkButton);
		panel.add(paintNextButton);
		panel.add(resetButton);
		panel.add(resetAllButton);
		panel.add(saveButton);
		panel.add(printScreenButton);
		panel.add(inPutCoordinateButton);
		panel.add(color_Button);
		panel.add(font_Button);
		

//		Label thick_label = new Label("������ϸ��");
//		thick_label.setPreferredSize(size);
//		thick_label.setFont(font);
//		panel.add(color_label);
//		panel.add(box_color);
//		panel.add(thick_label);
//		panel.add(box_thick);
		
		add(panel,BorderLayout.NORTH); // ��panel��ӵ������ϣ��������
		
		box = Box.createVerticalBox(); //����Box �����  
		
		//��ʼ��root�ڵ�
		root = new IconNode("Pictures");
		//�����ڵ�����������
		tree = new JTree(root);
 		tree.addTreeSelectionListener(this);//Ϊ������Ľڵ����addTreeSelectionListener������
 		tree.putClientProperty("JTree.lineStyle", "Angled"); // ʹJTree��������Windows�ļ���������ֱ����
 		//��Box ������ӹ������  
        box.add(new JScrollPane(tree), BorderLayout.CENTER);
       
        // ����box�ؼ���С
        Dimension treeSize = new Dimension((int)this.getWidth()/8, (int)(this.getHeight()*6.0/7));
        box.setPreferredSize(treeSize); // ����box�ؼ���С
        add(box,BorderLayout.WEST); // ���������ؼ���box�ؼ�����Jframe�������
        
		JPanel m_panel = new JPanel(); // ����panel��壬���ð�ť
		JLabel jbJLabel = new JLabel("�� ע ϵ ͳ");
		jbJLabel.setPreferredSize(new Dimension(this.getWidth()/8,this.getHeight()/20));
		jbJLabel.setFont(new Font("������", Font.BOLD, 24));
		jbJLabel.setForeground(Color.BLACK);
		m_panel.add(jbJLabel);
		m_panel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/20));
		add(m_panel,BorderLayout.SOUTH);
		
		model = (DefaultTreeModel) tree.getModel(); // ������ݶ���DefaultTreeModel
		
		//ʹ��Ч
		validate();
		pack();
	}

}
