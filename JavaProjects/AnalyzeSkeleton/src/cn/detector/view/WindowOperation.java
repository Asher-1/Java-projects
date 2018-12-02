/**
 * @author 陆大海
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
 * @author 陆大海
 */
public class WindowOperation extends JFrame implements TreeSelectionListener{
	private static final long serialVersionUID = 1L;
	private JFileChooser chooser;
	//节点对象
	IconNode root,chosen,child;
	//默认的树模式
	DefaultTreeModel model;
	JTree tree;
	//定义字符串容器，缓存图片所在文件夹目录的绝对路径
	Vector<String> pathStringsVector = new Vector<String>();
	//定义字符串容器，缓存叶子节点名称，即缓存图片文件名称
	Vector<String> leafStringsVector = new Vector<String>();
	//保存坐标按钮
	private JButton saveButton;
	//导入文件夹按钮
	private JButton inPutFileButton;
	//截图按钮
	private JButton printScreenButton;
	//切换为画线方式按钮,并需要暴露给外部的myLabel对象使用
	public JButton changeToDrawLines;
	//切换为画点方式按钮,并需要暴露给外部的myLabel对象使用
	public JButton changeToDrawAnchor;
	//切换为画点方式按钮,并需要暴露给外部的myLabel对象使用
	public JButton changeToDrawPoints;
	//需要暴露给外部的myLabel对象使用
	public JButton inPutCoordinateButton;
	//需要暴露给外部的myLabel对象使用
	public JButton resetButton;
	//需要暴露给外部的myLabel对象使用
	public JButton resetAllButton;
	//需要暴露给外部的myLabel对象使用,通过设置mystop，来切断上一次画点留下的联系
	public JButton linkButton;
	public JButton paintNextButton;
	public JButton font_Button;
	public JButton color_Button;
	public JButton parameter_Button;
	public JPanel panel;
	public MyLabel myLabel;
	public Box box;
	public String parameter = "15"; // 定义解析参数
	//用于存储被封装成File对象的图片文件
	File[] picFiles;
	//图片文件的绝对路径
	private String picAbsPath;
	//图片文件所在目录的绝对路径
	public String path_contains_pic; //提供给start类用于打开所选图片所在文件夹
	//图片文件的名称
	private String picName = null;
	//保存坐标值的文本文件的绝对路径
	public String txtPath = null;
	//待创建文件的名称，该文件目录里存放的是储存坐标的文本文件
	private String fileName = null;
	public String thick = "4";
	
	private boolean isFile_Selected = false;

	public String getThick() { // 获取线条粗细
		return thick;
	}
	private String color = "green";
	public String getColor() { // 获取线条颜色类型
		return color;
	}
	//无参构造函数
	WindowOperation(){}
	//有参构造函数
	WindowOperation(String title){
	 	//设置窗体大小，大小为屏幕大小
    	UiUtil.setFrameSize(this);
    	//设置窗体图标
    	UiUtil.setFrameImage(this);
		//设置窗体标题名称
		setTitle(title);
		//setResizable(false);
		
		//初始化窗体
    	init();
   
    	//设置窗口布局为边框布局
    	setLayout(new BorderLayout());
    	// 设置默认退出模式
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	public MyLabel getMyLabel() {
		return myLabel;
	}
	
	/* （非 Javadoc）
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	//实现鼠标切换树叶节点时，即切换图片时，进行图片加载和图片上绘制的坐标的保存
	//实现valueChanged方法，将文件对象导入树节点
	public void valueChanged(TreeSelectionEvent e){
		//获取用户最后一次选择的节点
		IconNode node=(IconNode)tree.getLastSelectedPathComponent();
		//当点击节点的收起标记时，node会为空，在此容错,返回空
		if(node == null){
			return;
		}
		if(node.isLeaf()){
			//如果linesVector容器非空，执行以下代码
			if(!(myLabel.linesVector.isEmpty())){
			
				int n = JOptionPane.showConfirmDialog(myLabel, "人体关节标记未保存，是否现在保存？",
					 "保存提醒", JOptionPane.YES_NO_CANCEL_OPTION);
				if(n == JOptionPane.CANCEL_OPTION){
					return;
				}
				else if(n == JOptionPane.NO_OPTION){
					//读取下一张图片，但不保存坐标值
					readNextPicture(e);	
				}
				else if(n == JOptionPane.YES_OPTION){
					//创建保存保存坐标信息对象，传入图片所在文件夹路径path_contains_pic，图片名称picName和标签myLabel
					Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(myLabel.pointsVector); // 创建pointsVector子关节点容器对象
					Vector<Line> linesVector_Child = new Vector<Line>(myLabel.linesVector); // 定义linesVector子线框容器对象
					myLabel.pointsVector_parent.add(pointsVector_Child); // 将子关节点容器对象加入父关节点容器中
					myLabel.linesVector_parent.add(linesVector_Child); // 将子线框容器对象加入父线框容器中
				
					Save_Information save_Information = new Save_Information(path_contains_pic,txtPath,fileName,myLabel);
					save_Information.save(); // 调用保存方法，保存坐标值
					//清空父容器
					myLabel.pointsVector_parent.clear();
					myLabel.linesVector_parent.clear();
					myLabel.reset();//设置重置参数，清空缓存
					readNextPicture(e);	// 读取下一张图片，并保存坐标值
				}
				//否则，不再次保存坐标
				else{
					readNextPicture(e);	//读取下一张图片，并保存坐标值
					return;
				}
			}
			//如果是第一次点击叶节点，linesVector为空值，则直接读取图片而不提醒保存坐标
			else{
				readNextPicture(e); //读取下一张图片，并保存坐标值
			}
		}
		
		else if(!(node.isLeaf()) && node != null){
			readNextPicture(e); //读取下一张图片，并保存坐标值
		}
		else{
			return;
		}
		
		
}
	/**
	 * @param e
	 */
	//加载下一张图片到label上
	public void readNextPicture(TreeSelectionEvent e) {
		//将父容器清空
		myLabel.pointsVector_parent.clear();
		myLabel.linesVector_parent.clear();
		myLabel.reset();//设置重置参数，清空缓存
		myLabel.repaint();
		if(e.getSource() == tree)
		{	//获取用户最后一次选择的节点
			IconNode node=(IconNode)tree.getLastSelectedPathComponent();
			//如果该节点是非叶子节点或者node节点为空，直接返回空
			if(node == null || !(node.isLeaf())){
				if(isFile_Selected){
					
					loadPic_OnLabel(node);
					isFile_Selected = false;
				}
				else{
					
					return;
				}
			}
			//如果是叶节点，便执行以下代码将图片加载到myLabel上
			else{
				loadPic_OnLabel(node);	
			}
		}
	}
	private void loadPic_OnLabel(IconNode node) {
		//循环容器，这里不需要对i局部变量进行++操作，容器会自动循环搜索数据
		for(int i=0; i<leafStringsVector.size();i++){
		//获取所选目录文件的名称
		String tmp = (String) leafStringsVector.elementAt(i);
		if(node.getParent() == null){
			System.out.println(node.getParent()+"loadPic_OnLabel()出现异常");// 用于调试
			return;
		}
		//如果叶节点的父节点名称与leafStringsVector容器里对应下标的文件夹目录名称相同
		if(tmp.equals(node.getParent().toString())){
			//获取pathStringsVector容器里面与leafStringsVector容器对应下标相同的字符串，即文件夹目录绝对路径
			path_contains_pic = (String) pathStringsVector.elementAt(i);
			//将所选节点的名字传给picName
			picName = node.toString();
			//构建并获取图片的绝对路径
			picAbsPath = new String(path_contains_pic + File.separator + picName);
			
			
			//创建图片对象
			ImageIcon imageIcon = new ImageIcon(picAbsPath);
			double scale = 1.0*imageIcon.getIconWidth() / imageIcon.getIconHeight();
			//获取myLabel控件的宽和高
			//int width = myLabel.getWidth();
			int height = myLabel.getHeight();
			
			
			//设置imageIcon对象的尺寸与myLabel控件的尺寸相同
			imageIcon.setImage(imageIcon.getImage().getScaledInstance((int)(height*scale),height,Image.SCALE_DEFAULT));
			
			myLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置图片居中显示,括号内也可置0
			
			myLabel.setIcon(imageIcon); // 设置myLabel控件的背景图片，即加载图片，准备关节注解
			//预保存信息语句块
			{
				//获取待创建文件的名称，名称与该图片名称相同,并去除图片名称picName的后缀名
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
				//防止图片文件的后缀名不是以上的任意一种导致，fileName无值造成创建txtPath字符串对象不准确
				//直接连后缀名一起赋给fileName
				else{
					fileName = picName;
				}
				
				//获取待创建的文本文件名称
				String txtName = new String(fileName + ".txt");
				//获取待写入的文本文件的绝对地址
				txtPath = new String(path_contains_pic + File.separator + fileName +File.separator + txtName);
			
				}
			//找到图片文件，加载后，便退出循环，提高效率
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
	// 初始化函数
	private void init() {
		//创建myLabel控件，并添加到窗体的中心位置
		myLabel = new MyLabel();
		myLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//给文字添加鼠标样式，当鼠标移动到文字上，鼠标变成手型
		add(new JScrollPane(myLabel),BorderLayout.CENTER); // 将myLabel标签组件加入窗体
		
		Dimension size = new Dimension(this.getWidth()/18,this.getHeight()/20); // 定义按钮尺寸
		Font font = new Font("宋体", Font.BOLD, 15); // 定义字体大小，粗细，类型
			
//		// 创建线条粗细下拉框
//		String[] cord_Items = {"1","2","3","4","5","6"};
//		JComboBox<String> box_thick = new JComboBox<String>(cord_Items);   
//		box_thick.setPreferredSize(size); // 设置按钮大小
//	    box_thick.setFont(font); //设置字体显示
//	    box_thick.addItemListener(new ItemListener() {// 切换线条粗细的事件监视器
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//	
//				thick = e.getItem().toString(); // 获取被选的项的名称
//				if (e.getStateChange() == ItemEvent.DESELECTED) {
//
//					thick = "3"; //默认设置线条为3.0f粗
//				}
//				myLabel.repaint(); // 调用重绘功能实现，显现实时显示
//			}
//		});
//		
//	    // 创建线条颜色类型下拉框
//	    String[] color_Items = {"绿色","蓝色","红色","黑色","白色","黄色"};
//		JComboBox<String> box_color = new JComboBox<String>(color_Items);  
//		box_color.setPreferredSize(size); // 设置按钮大小
//		box_color.setFont(font); //设置字体显示
//		box_color.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {// 切换线条颜色的事件监视器
//				String source = e.getItem().toString(); // 获取被选的项的名称
//				if (source == "红色") {
//					
//					color = "red";
//					
//				} else if (source == "绿色") {
//
//					color = "green";
//					
//				} else if (source == "蓝色") {
//
//					color = "blue";
//				} else if (source == "黑色") {
//
//					color = "black";
//					
//				}
//				else if (source == "白色") {
//				
//					color = "white";
//				
//				}
//				else if (source == "黄色") {
//					
//					color = "yellow";
//					
//				}
//				if (e.getStateChange() == ItemEvent.DESELECTED) {
//
//					color = "blue";
//				}
//				myLabel.repaint(); // 调用重绘功能实现，显现实时显示
//			}
//		});
//		
			
		// 标注参数
		String[] param_Items = {"15","14","13","12","11","10","9","8","7","6",
								"5","4","3","2","16","17","18","19","20","21",
								"22","23","24","25","30","32","34","35","40"};
		JComboBox<String> boxParam = new JComboBox<String>(param_Items);  
		boxParam.setPreferredSize(new Dimension(this.getWidth()/40,this.getHeight()/20)); // 设置按钮大小
		boxParam.setFont(font); //设置字体显示
		boxParam.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {// 切换线条颜色的事件监视器
				parameter = e.getItem().toString(); // 获取被选的项的名称
				myLabel.repaint(); // 调用重绘功能实现，显现实时显示
			}
		});
			
		
		//创建导入文件夹按钮
		inPutFileButton = new JButton("导入文件夹");
		//inPutFileButton.setPreferredSize(new Dimension(140,50));
		inPutFileButton.setPreferredSize(size);
		inPutFileButton.setFont(font);
		inPutFileButton.addActionListener(new ActionListener() {
			// 为导入文件夹按钮添加监视器
			@Override
			public void actionPerformed(ActionEvent e) {
				// 定义文件对象
				File fileDir = null;
				chooser = new JFileChooser();
				// 设置文件选择模式为既可以选择目录文件，也可以选择文件
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				// 设置默认打开路径
//				chooser.setCurrentDirectory(new File("G:\\develop\\cocosPNG"));
				chooser.setCurrentDirectory(new File("测试文件"));
				chooser.setMultiSelectionEnabled(true);
				// 显示文件选择对话框
				int result = chooser.showOpenDialog(null);
				// 如果选中文件夹并确定
				if (result == JFileChooser.APPROVE_OPTION) {
					fileDir = chooser.getSelectedFile();
					File[] myFiles = chooser.getSelectedFiles();
					// 获取用户选择的文件夹的绝对路径
					// 如果用户选择的是文件目录，则调用getSelectedFile函数，获取所选文件目录的绝对路径，并封装成文件对象返回
					if (fileDir.isDirectory()) {
						// 此处获得的路径是形如：e:\\abc\\ab\\c形式的绝对路径
						fileDir = chooser.getSelectedFile();

						// 将当前文件夹目录的绝对路径存储下来，为将来的图片读取创造条件
						String picPathStrings = new String(fileDir.getAbsolutePath());// 与TreeSelectionListener监视器配合读取图片或文件，因为完整路径易于读取

						// 将文件夹目录的绝对路径加入到pathStringsVector容器中
						pathStringsVector.add(picPathStrings);

						// 获取文件夹目录的绝对路径中所有文件夹目录的名称
						String[] filename = picPathStrings.split("\\\\");
						// 以所选文件所在目录的名称或者所选文件夹目录的名称创建字符串对象
						String leafStrings = new String(filename[filename.length - 1]);

						// 将所选文件所在文件夹目录的名称或者所选文件夹目录的名称加入leafStringsVector容器中
						leafStringsVector.add(leafStrings);

						// 创建子节点：以leafStrings为字节点名称
						child = new IconNode(leafStrings);

						// 创建文件过滤器，只显示图片格式文件
						FileAcceptFilter fileAcceptFilter = new FileAcceptFilter();
						// 额外添加图片格式
						fileAcceptFilter.setExtendName("java");

						isFile_Selected = false;
						picFiles = fileDir.listFiles(fileAcceptFilter);
						// 定义节点数组，用于存储节点
						IconNode treeNodes[];

						// 创建节点数组以便在for循环中以此根据图片名创建节点
						treeNodes = new IconNode[picFiles.length];

						// 循环文件数组，添加树节点
						for (int i = 0; i < picFiles.length; i++) {// 循环图像文件

							treeNodes[i] = new IconNode(picFiles[i].getName());// picFiles[i].getName()获取的是文件的的全名
							ImageIcon icon = new ImageIcon(picPathStrings + File.separator + picFiles[i].getName()); // 获取图像
							// 设置图像大小
							icon.setImage(icon.getImage().getScaledInstance((int) (box.getWidth() / 3.0),
									(int) (box.getWidth() / 4.0), Image.SCALE_DEFAULT));

							treeNodes[i].setIcon(icon); // 为树节点设置图像缩略图

							child.add(treeNodes[i]); // 添加文件或图片文件叶节点到子节点
						}

						tree.setCellRenderer(new IconNodeRenderer()); // 设置树节点图标渲染器
						setImageObserver(tree, treeNodes); // 设置树控件的图片监视

						// 获取当前用户选择的节点，目的是选择child的父节点
						chosen = (IconNode) tree.getLastSelectedPathComponent();
						if (chosen == null) {
							// 如果当前用户未选择节点，则选择root根节点作为父节点
							chosen = root;
						}
						// 把child子节点添加到chosen父节点
						model.insertNodeInto(child, chosen, 0);
					}

					// 如果用户选择的是文件，则调用getCurrentDirectory函数，获取所选文件所在目录的绝对路径，并封装成文件对象返回
					else if (fileDir.isFile() | myFiles.length != 1) {
						isFile_Selected = true;
						// 此处获得的路径是形如：e:\\abc\\ab\\c形式的绝对路径
						fileDir = chooser.getCurrentDirectory();
						// 将当前文件夹目录的绝对路径存储下来，为将来的图片读取创造条件
						String picPathStrings = new String(fileDir.getAbsolutePath());// 与TreeSelectionListener监视器配合读取图片或文件，因为完整路径易于读取
						// 将文件夹目录的绝对路径加入到pathStringsVector容器中
						pathStringsVector.add(picPathStrings);

						// 获取文件夹目录的绝对路径中所有文件夹目录的名称
						String[] filename = picPathStrings.split("\\\\");

						// 以所选文件所在目录的名称或者所选文件夹目录的名称创建字符串对象
						String leafStrings = new String(filename[filename.length - 1]);
						// 将所选文件所在文件夹目录的名称或者所选文件夹目录的名称加入leafStringsVector容器中
						leafStringsVector.add(leafStrings);

						// 创建子节点：以leafStrings为字节点名称
						child = new IconNode(leafStrings);

						// 创建文件过滤器，只显示图片格式文件
						FileAcceptFilter fileAcceptFilter = new FileAcceptFilter();
						// 额外添加图片格式
						fileAcceptFilter.setExtendName("java");

						IconNode treeNode[]; // 定义节点数组，用于存储节点
						int file_Number = myFiles.length;
						// 创建节点数组以便在for循环中以此根据图片名创建节点
						treeNode = new IconNode[file_Number];
						// 创建并获取文件或图片文件叶节点
						for (int i = 0; i < file_Number; i++) {

							treeNode[i] = new IconNode(myFiles[i].getName());// 获取的是选择文件的的全名
							ImageIcon icon = new ImageIcon(fileDir + File.separator + treeNode[i].toString());

							// 设置图像大小
							icon.setImage(icon.getImage().getScaledInstance((int) (box.getWidth() / 3.0),
									(int) (box.getWidth() / 4.0), Image.SCALE_DEFAULT));
							treeNode[i].setIcon(icon); // 为树节点设置图像缩略图

							child.add(treeNode[i]); // 添加文件或图片文件叶节点到子节点
						}
						tree.setCellRenderer(new IconNodeRenderer());
						setImageObserver(tree, treeNode);

						// 获取当前用户选择的节点，目的是选择child的父节点
						chosen = (IconNode) tree.getLastSelectedPathComponent();
						if (chosen == null) {
							// 如果当前用户未选择节点，则选择root根节点作为父节点
							chosen = root;
						}
						// 把child子节点添加到chosen父节点
						model.insertNodeInto(child, chosen, 0);

					}

				}
			}
		});
		
		//创建连线按钮
		linkButton =new JButton("连 线");
		//添加事件监听事件
		linkButton.addActionListener(myLabel);
		linkButton.setPreferredSize(size); // 设置按钮大小
		linkButton.setFont(font); //设置字体类型，大小，粗细
		linkButton.setBackground(Color.blue); // 设置按钮背景色
		
		//创建保存坐标按钮
		saveButton = new JButton("保存坐标");
		saveButton.setPreferredSize(size);
		saveButton.setFont(font);
		//为保存按钮添加监听事件
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//防止用户乱操作按钮，如并未打开图片就进行相关的按钮操作
				if(fileName == null || txtPath == null){
					JOptionPane.showMessageDialog(null, "还未打开图片，无法保存相应的图片信息", "错误信息", JOptionPane.ERROR_MESSAGE);
					myLabel.reset(); // 设置重置参数，清空缓存
					myLabel.repaint(); //重绘界面
					return;
				}
				
				//对多次点击绘制绘制下一个按钮的容错性处理
				if(!(myLabel.pointsVector.isEmpty()) && !(myLabel.linesVector.isEmpty())){
					Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(myLabel.pointsVector); // 创建pointsVector子关节点容器对象
					for (Iterator<MyPoint> iterator = pointsVector_Child.iterator(); iterator.hasNext();) {
						MyPoint myPoint = (MyPoint) iterator.next();
						myPoint.parameter = Integer.parseInt(parameter);
						
					}
					Vector<Line> linesVector_Child = new Vector<Line>(myLabel.linesVector); // 定义linesVector子线框容器对象
					myLabel.pointsVector_parent.add(pointsVector_Child); // 将子关节点容器对象加入父关节点容器中
					myLabel.linesVector_parent.add(linesVector_Child); // 将子线框容器对象加入父线框容器中
				}
				
				
				//[start]按人体的锚点（3个）->左臂（3个）-> 右臂（3个）-> 左腿（2个）-> 右腿（2个）顺序重排关节点
				/*
				 * 用于重排节点顺序
				 * 实现坐标的有序保存，保存顺序为：
				 * 锚点（3个）->左臂（3个）-> 右臂（3个）-> 左腿（2个）-> 右腿（2个）
				 * 
				 * */ 
				Vector<Vector<MyPoint>> tempVector_Parent = new Vector<Vector<MyPoint>>(myLabel.pointsVector_parent);//定义元素是容器的容器,暂时缓存容器
				myLabel.pointsVector_parent.clear();// 清空旧的父节点容器
				
				for (int j = 0; j < tempVector_Parent.size(); j++) {
					Vector<MyPoint> tempVector_Child = new Vector<MyPoint>();// 用于缓存关节点的子缓存容器
					Vector<MyPoint> pointsVector_Child = (Vector<MyPoint>) tempVector_Parent.elementAt(j); // 获取子容器
					Vector<Line> linesVector_Child = (Vector<Line>) myLabel.linesVector_parent.elementAt(j); // 获取对应子容器		
					
					Coordinate_Transformation transaction = new Coordinate_Transformation(linesVector_Child,pointsVector_Child);
					transaction.setParameter(Integer.parseInt(parameter));
					
					transaction.change_coordinate();// 调用转换坐标方法
					transaction.divide_Layer();// 调用分层方法，将每层的关节点按人体特点分别放入五个容器中
					
					//获取各个容器
					Vector<MyPoint> vector1 = transaction.getVector1();
					Vector<MyPoint> vector2 = transaction.getVector2();
					Vector<MyPoint> vector3 = transaction.getVector3();
					Vector<MyPoint> vector4 = transaction.getVector4();
					Vector<MyPoint> vector5 = transaction.getVector5();
					
					// 将锚点加入子容器中
					tempVector_Child.add(new MyPoint(vector1.get(0)));
					tempVector_Child.add(new MyPoint(vector1.get(1)));
					tempVector_Child.add(new MyPoint(vector1.get(2)));
					
					// 按顺序加入左臂关节点
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
					
					// 按顺序加入右臂关节点
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
					
					// 按顺序加入左腿关节点
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
					
					// 按顺序加入右腿关节点
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
							myPoint.body_Parts = 1; // 记录此关节点为左臂节点
						}
						else if(i >= 7 && i <= 9){
							myPoint.body_Parts = 2; // 记录此关节点为右臂节点
						}
						else if(i >= 10 && i <= 11){
							myPoint.body_Parts = 3; // 记录此关节点为左腿节点
						}
						else if(i >= 12 && i <= 13){
							myPoint.body_Parts = 4; // 记录此关节点为右腿节点
						}
						i++;
						
					}
					
					myLabel.pointsVector_parent.add(tempVector_Child); // 将重排后关节点容器暂时缓存于父关节点缓存容器中
					}
				
				/*
				 * 以上均为重排代码，目的是为了按人体节点顺序保存坐标信息
				 * 
				 * */ 
				//[end]
				
				//创建保存保存坐标信息对象，传入图片所在文件夹路径path_contains_pic，文本文件所在的绝对路径：txtPath 和 标签myLabel
				Save_Information save_Information = new Save_Information(path_contains_pic,txtPath,fileName,myLabel);
				save_Information.save(); // 调用保存方法，保存坐标值
				myLabel.reset();//设置重置参数，清空缓存
			}
		});
		
		//创建重置坐标按钮
		resetButton = new JButton("重置当前");
		//resetButton.setPreferredSize(new Dimension(120,50));
		resetButton.setPreferredSize(size);
		resetButton.setFont(font);
		resetButton.addActionListener(myLabel);
		
		resetAllButton = new JButton("重置全部");
		//resetAllButton.setPreferredSize(new Dimension(120,50));
		resetAllButton.setPreferredSize(size);
		resetAllButton.setFont(font);
		//为按钮添加事件监听事件，监视器为实现了ActionListener接口的MyLabel类生成的对象
		resetAllButton.addActionListener(myLabel);
		
		//创建截图按钮
		printScreenButton = new JButton("截图");
		printScreenButton.setPreferredSize(size);
		printScreenButton.setFont(font);
		//为截图按钮添加监听器
		printScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//创建截图窗体，进行截图操作
				if(txtPath != null){
				new AWTCutPicture().setMypic(txtPath);
				}
				else{
					JOptionPane.showMessageDialog(null, "文件尚未保存或用户未打开图片，无法找到画面截图", "错误信息", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//创建导入关节按钮
		inPutCoordinateButton = new JButton("导入关节");
		inPutCoordinateButton.setPreferredSize(size);
		inPutCoordinateButton.setFont(font);
		//为导入关节按钮即inPutCoordinateButton添加监听事件，
		//并将myLabel监视器监视此按钮，触动此菜单项会调用myLabel监视器，而导入坐标，并重新绘制线条到图片上 
		inPutCoordinateButton.addActionListener(myLabel);	
		
		changeToDrawLines = new JButton("画轮廓线");	
		changeToDrawLines.setPreferredSize(size);
		changeToDrawLines.setFont(font);
		changeToDrawLines.setBackground(Color.blue);
		changeToDrawLines.addActionListener(myLabel);
		
		 
		changeToDrawAnchor = new JButton("画锚点");
		changeToDrawAnchor.setPreferredSize(size);
		changeToDrawAnchor.setFont(font);
		changeToDrawAnchor.setBackground(Color.blue);
		changeToDrawAnchor.addActionListener(myLabel);
		
		changeToDrawPoints = new JButton("画关节点");
		changeToDrawPoints.setPreferredSize(size);
		changeToDrawPoints.setFont(font);
		changeToDrawPoints.setBackground(Color.blue);
		changeToDrawPoints.addActionListener(myLabel);
		
		paintNextButton = new JButton("绘下一个");
		//paintNextButton.setPreferredSize(new Dimension(200,50));
		paintNextButton.setPreferredSize(size);
		paintNextButton.setFont(font);
		paintNextButton.setBackground(Color.blue);
		paintNextButton.addActionListener(myLabel);
		
		font_Button = new JButton("线条粗细");
		//font_Button.setPreferredSize(new Dimension(120,50));
		font_Button.setPreferredSize(size);
		font_Button.setFont(font);
		font_Button.addActionListener(myLabel);
		
		color_Button = new JButton("线条颜色");
		//color_Button.setPreferredSize(new Dimension(120,50));
		color_Button.setPreferredSize(size);
		color_Button.setFont(font);
		color_Button.addActionListener(myLabel);
		
		
		panel = new JPanel(); // 创建panel面板，放置按钮
		//panel.setLayout(new FlowLayout());
		panel.setLayout(new GridLayout());
		//将按钮添加到面板中
//		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
//		panel.setLayout(boxLayout);
		
		JLabel para_label = new JLabel("标注参数:");
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
		

//		Label thick_label = new Label("线条粗细：");
//		thick_label.setPreferredSize(size);
//		thick_label.setFont(font);
//		panel.add(color_label);
//		panel.add(box_color);
//		panel.add(thick_label);
//		panel.add(box_thick);
		
		add(panel,BorderLayout.NORTH); // 将panel添加到窗体上，居中添加
		
		box = Box.createVerticalBox(); //创建Box 类对象  
		
		//初始化root节点
		root = new IconNode("Pictures");
		//将根节点加入树组件中
		tree = new JTree(root);
 		tree.addTreeSelectionListener(this);//为树组件的节点添加addTreeSelectionListener监视器
 		tree.putClientProperty("JTree.lineStyle", "Angled"); // 使JTree具有类似Windows文件管理器的直角连
 		//向Box 容器添加滚动面板  
        box.add(new JScrollPane(tree), BorderLayout.CENTER);
       
        // 定义box控件大小
        Dimension treeSize = new Dimension((int)this.getWidth()/8, (int)(this.getHeight()*6.0/7));
        box.setPreferredSize(treeSize); // 设置box控件大小
        add(box,BorderLayout.WEST); // 将带有树控件的box控件加入Jframe窗体左侧
        
		JPanel m_panel = new JPanel(); // 创建panel面板，放置按钮
		JLabel jbJLabel = new JLabel("标 注 系 统");
		jbJLabel.setPreferredSize(new Dimension(this.getWidth()/8,this.getHeight()/20));
		jbJLabel.setFont(new Font("新宋体", Font.BOLD, 24));
		jbJLabel.setForeground(Color.BLACK);
		m_panel.add(jbJLabel);
		m_panel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/20));
		add(m_panel,BorderLayout.SOUTH);
		
		model = (DefaultTreeModel) tree.getModel(); // 获得数据对象DefaultTreeModel
		
		//使生效
		validate();
		pack();
	}

}
