/**
 * @author 陆大海
 */
package cn.detector.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import cn.detector.core.Coordinate_Transformation;
import cn.detector.core.Line;
import cn.detector.core.MyPoint;
import cn.detector.core.Mydraw;
import cn.detector.util.FontChooser;
/**
 *操作界面类
 *用于界面注解的各种操作 
 */

/*
 * 将MyLabel封装成Jlabel的子类，同时实现ActionListener接口
 * 封装成了一个监视器，用于监视导入关节按钮
 * 作为图片的载体和工作平台
 */
public class MyLabel extends JLabel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	Vector<MyPoint> pointsVector = new Vector<MyPoint>(); // 定义pointsVector容器用于存储关节点
	Vector<Vector<MyPoint>> pointsVector_parent = new Vector<Vector<MyPoint>>();//定义元素是容器的容器
	MyPoint myPoint = null; // 用于临时的点对象的存储
	MyPoint tmpPoint = null; // 用于暂时缓存文件中的点，为防止与上面的myPoint对象混淆，在此进行了更名
	Point tmpP; // 定义点成员变量,缓存第一点，用于切断与上一次画点的联系
	public Mydraw mydraw;
	public Graphics m_g = getGraphics();
	public int[] RGB = new int[]{0,0,0};
	public int[] getRGB() {
		return RGB;
	}
	public Font m_font = null;
	public Font getm_font() {
		return m_font;
	}
	public Font font_Temp;
	
	/**
	 * @return pointsVector
	 */
	//获取pointsVector
	public Vector<MyPoint> getPointsVector() {
		return pointsVector;
	}
	Vector<Line> linesVector = new Vector<Line>(); // 定义linesVector容器，用于存储线对象，两点确定一条线
	Vector<Vector<Line>> linesVector_parent = new Vector<Vector<Line>>();//定义元素是容器的容器
	// 返回父关节点容器
	public Vector<Vector<MyPoint>> getPointsVector_parent() {
		return pointsVector_parent;
	}
	//返回父线框容器
	public Vector<Vector<Line>> getLinesVector_parent() {
		return linesVector_parent;
	}
	Line line = null; // 定义线成员变量
	Vector<Boolean> booleansVector = new Vector<Boolean>(); // 设置保存标志
	public int style = 3; // 定义绘制模式标记，默认禁用所有绘制模式
	//定义当前容器所放元素的个数,必须暴露给类对象使用
	public int pointsVector_size;
	public int linesVector_size;
	public int line_Flag = 0; // 默认关闭连线功能
	public boolean is_DrawAnchor = false; // 默认关闭画锚点功能
	
	static WindowOperation win; // 定义窗体类变量，用于接收传进来的窗体对象的引用
	
	/**
	 * @return linesVector
	 */
	//返回linesVector容器
	public Vector<Line> getLinesVector() {
		return linesVector;
	}
	
	/**
	 * @param win 要设置的 win
	 */
	//获取win窗体对象
	public static void setWin(WindowOperation wins) {
		win = wins;
	}


	/*
	 * 将读取到的坐标值自动重新绘制到图片上（非 Javadoc）
	 *监听导入按钮事件，响应该方法
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	//事件响应，监听了inPutCoordinateButton，resetButton，changeToDrawLines，changeToDrawPoints，cutConnectionButton等按钮
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton) e.getSource(); 
		//如果触发此事件的是inPutCoordinateButton按钮，则执行导入坐标，绘制关节操作
		// 导入关节
		if(jb == win.inPutCoordinateButton){
			//防止用户乱操作按钮，如并未打开图片就进行相关的按钮操作
			if(win.txtPath == null){
				JOptionPane.showMessageDialog(null, 
						"用户未打开图片，无法找到坐标文件，导入失败", "错误信息", JOptionPane.ERROR_MESSAGE);
				//清空容器全部内容，排除上次操作数据的干扰
				linesVector_parent.clear();
				pointsVector_parent.clear();
				reset(); //设置重置参数，清空缓存
				repaint(); // 重绘界面
				return;
				}
			
			BufferedReader br =null; // 创建缓存流对象，读取文件效率较高
			String[] buf = null; // 创建字符串数组，用于存储经解析的坐标数据
			//清空容器全部内容，排除上次操作数据的干扰
			linesVector_parent.clear();
			pointsVector_parent.clear();
			reset(); // 设置重置参数，清空缓存
			repaint(); // 重绘界面，清除界面遗留线条
			try {
				File m_file = new File(win.txtPath);
				if(!m_file.exists()){ // 坐标数据文件不存在
					JOptionPane.showMessageDialog(null, "骨骼标注数据文件不存在！！！人体骨骼标注后并保存后，才能执行此操作哦！！！", "错误信息", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				//以缓冲文件读写流的方式读取文件，文件路径为：“win.txtPath”，txtpath是加载图片时预存在win对象中的类成员变量
				br = new BufferedReader(new FileReader(win.txtPath));
				//CoordinateIfo.txt文件里第一行为空，因此在这要先空读一行，防止下面解析错误
				String s = br.readLine();
				//下述方法为从文件读取后处理并解析，最后往linesVector容器中导入坐标值
				while((s = br.readLine()) != null){
					//调用字符串对象的分解方法，得到字符串数组
					buf = s.split(",");
					//获取字符串数组长度
					int length = buf.length;
					// 导入线框坐标
					Vector<Line> linesVector_Child = new Vector<Line>(); // 定义linesVector容器，用于存储线对象，两点确定一条线
					//循环字符串数组，向linesVector_Child容器添加坐标值
					for (int i = 0; i < 16; i+=4) {
						Line temLine = new Line(); // 创建Line对象，存储坐标
						temLine.x0 = Integer.parseInt(buf[i]);
						temLine.y0 = Integer.parseInt(buf[i+1]);
						temLine.x1 = Integer.parseInt(buf[i+2]);
						temLine.y1 = Integer.parseInt(buf[i+3]);
						//将line对象加到容器中
						linesVector_Child.add(temLine);	
					}
					linesVector_parent.add(linesVector_Child); // 将子容器加入父线框容器中
					
					// 导入关节点坐标
					Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(); // 定义pointsVector容器用于存储关节点
					
					int m_parameter = 15;// 定义标注参数，并初始化为15
					if( buf[16].equals("parameter:")){
						m_parameter = Integer.parseInt(buf[17]); // 获取标注参数
					}
					
					int flag = 0;
					for(int j = 18; j < length; j+=2){
						MyPoint temP = null;
						if(buf[j].equals("anchor") && buf[j+1].equals(":")){
							flag = 1;
							continue;
						}
						else if(buf[j].equals("左臂") && buf[j+1].equals(":")){
							flag = 2;
							continue;
						}
						else if(buf[j].equals("右臂") && buf[j+1].equals(":")){
							flag = 3;
							continue;
						}
						else if(buf[j].equals("左腿") && buf[j+1].equals(":")){
							flag = 4;
							continue;
						}
						else if(buf[j].equals("右腿") && buf[j+1].equals(":")){
							flag = 5;
							continue;
						}
						
						
						if(flag == 1){
							temP = new MyPoint();
							temP.point = new Point();
							temP.parameter = m_parameter;
							temP.anchor = true;
							temP.point.x = Integer.parseInt(buf[j]);
							temP.point.y = Integer.parseInt(buf[j+1]);
							//读取关节点坐标
							pointsVector_Child.add(temP);
							continue;
						}
						else if(flag == 2){
							temP = new MyPoint();
							temP.point = new Point();
							temP.body_Parts = 1;
							temP.parameter = m_parameter;
							temP.point.x = Integer.parseInt(buf[j]);
							temP.point.y = Integer.parseInt(buf[j+1]);
							//读取关节点坐标
							pointsVector_Child.add(temP);
							continue;
						}
						else if(flag == 3){
							temP = new MyPoint();
							temP.point = new Point();
							temP.body_Parts = 2;
							temP.parameter = m_parameter;
							temP.point.x = Integer.parseInt(buf[j]);
							temP.point.y = Integer.parseInt(buf[j+1]);
							//读取关节点坐标
							pointsVector_Child.add(temP);
							continue;
						}
						else if(flag == 4){
							temP = new MyPoint();
							temP.point = new Point();
							temP.body_Parts = 3;
							temP.parameter = m_parameter;
							temP.point.x = Integer.parseInt(buf[j]);
							temP.point.y = Integer.parseInt(buf[j+1]);
							//读取关节点坐标
							pointsVector_Child.add(temP);
							continue;
						}
						else if(flag == 5){
							temP = new MyPoint();
							temP.point = new Point();
							temP.parameter = m_parameter;
							temP.body_Parts = 4;
							temP.point.x = Integer.parseInt(buf[j]);
							temP.point.y = Integer.parseInt(buf[j+1]);
							//读取关节点坐标
							pointsVector_Child.add(temP);
							continue;
						}
					
					}
					pointsVector_parent.addElement(pointsVector_Child); // 将子容器加入父关节点容器中
				}
			} 
			catch (IOException e1) {
				// TODO 自动生成的 catch 块n
				e1.printStackTrace();
			}
			catch(NumberFormatException e1){
				//文件损坏则清空容器，不再绘制关节和线条
				linesVector_parent.clear();
				pointsVector_parent.clear();
				reset();//设置重置参数，清空缓存
				JOptionPane.showMessageDialog(null, "坐标文件已损坏，导入失败", "错误信息", 
						JOptionPane.ERROR_MESSAGE);
			}
			finally{
				if(br != null)
					try {
						br.close();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						throw new RuntimeException("关闭失败");
					}
			 }
			//重绘关节
			repaint();
		}
		
		//如果触发此事件的是resetButton按钮，则执行仅重置当前坐标的操作
		// 重置当前坐标
		else if(jb == win.resetButton){
			style = 3; // 禁用所有绘制模式
			is_DrawAnchor = false; // 禁用锚点绘制功能
			line_Flag = 0; // 禁止连线功能
			//防止用户乱操作按钮，如并未打开图片就进行相关的按钮操作
			if(win.txtPath == null){
				reset();//设置重置参数，清空缓存
				repaint(); // 重绘界面
				return; // 结束函数
			}
			int sfd = JOptionPane.showConfirmDialog(null, "确认是否重置？", "重置坐标确认对话框", JOptionPane.YES_NO_OPTION);
			if(sfd == JOptionPane.YES_OPTION){
				
				reset();//设置重置参数，清空缓存
				repaint(); // 重绘界面
			
			}
			//如果取消重置操作，将什么都不做直接返回
			else if(sfd == JOptionPane.NO_OPTION){
				return;
			}
		}
		//如果触发此事件的是resetAllButton按钮，则执行重置全部坐标的操作
		// 重置全部坐标
		else if(jb == win.resetAllButton){
			style = 3; // 禁用所有绘制模式
			is_DrawAnchor = false; // 禁用锚点绘制功能
			line_Flag = 0; // 禁止连线功能
			//防止用户乱操作按钮，如并未打开图片就进行相关的按钮操作
			if(win.txtPath == null){
				//将父容器清空
				pointsVector_parent.clear();
				linesVector_parent.clear();
				reset();//设置重置参数，清空缓存
				repaint(); // 重绘界面
				return; // 结束函数
			}
			BufferedWriter bf = null;
			int sfd = JOptionPane.showConfirmDialog(null, "确认是否重置？此项操作会清空骨骼节点信息数据文件！！！", "重置全部坐标确认对话框", JOptionPane.YES_NO_OPTION);
			if(sfd == JOptionPane.YES_OPTION){
				try {
					//将父容器清空
					pointsVector_parent.clear();
					linesVector_parent.clear();
					reset(); //设置重置参数，清空缓存
					repaint(); // 重绘界面
					
					// 防止用户还未保存骨骼节点数据就进行全部坐标重置操作造成的win.txtPath文件无法找到的情况，而出现异常
					if(!new File(win.txtPath).exists()){
						pointsVector_parent.clear();
						linesVector_parent.clear();
						reset();//设置重置参数，清空缓存
						repaint(); // 重绘界面
						return; // 结束函数
					}
					
					//以缓冲文件读写流的方式写入文件，文件路径为：“win.txtPath”，txtpath是加载图片时预存在win对象中的类成员变量
					bf = new BufferedWriter(new FileWriter(win.txtPath));
					bf.write(""); // 清空文本文档操作
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				finally{
					if(bf != null)
						try {
							bf.close(); // 关闭文件流
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							throw new RuntimeException("重置失败"); // 抛出运行时异常
						}
				}
			}
			//如果取消重置操作，将什么都不做直接返回
			else if(sfd == JOptionPane.NO_OPTION){
				return;
			}
		}
		//画轮廓线
		else if(jb == win.changeToDrawLines){
			
			style = 1; // 切换绘制方式，将绘点方式切换到绘线方式，同时禁用了画点模式
			is_DrawAnchor = false; // 禁用锚点绘制功能
			line_Flag = 0; // 禁止连线功能
		}
		
		//画锚点
		else if(jb == win.changeToDrawAnchor){
			
			is_DrawAnchor = true; // 切换绘制方式，进入画锚点模式
			style = 3; // 禁用画点和画线功能
			line_Flag = 0; // 禁止连线功能
		}
		
		// 画关节点
		else if(jb == win.changeToDrawPoints){
			
			style = 0; // 切换绘制方式，将绘线方式切换到绘点方式，同时禁用了画轮廓线模式
			is_DrawAnchor = false; // 禁用锚点绘制功能
			line_Flag = 0; // 禁止连线功能
		}
		

		// 连线
		else if(jb == win.linkButton){
			/*
			 *在此设置关节点的分层标志，根据人体关节的特点依次连接各点 
			 * */
			line_Flag = 1; // 启动连线功能
			is_DrawAnchor = false; // 禁用锚点绘制功能
			style = 3; // 禁用画点及锚点和划线功能
			//判断所注节点位置是否合理，若注解错误关闭连线功能
			if(!drawJoint(this.getGraphics())){
				int sfd = JOptionPane.showConfirmDialog(null, "关节注解错误，确认是否重置？", "重置坐标确认对话框", JOptionPane.YES_NO_OPTION);
				 if(sfd == JOptionPane.YES_OPTION){
					reset();//设置重置参数，清空缓存
					repaint(); // 重绘界面
					return;
				}
				//如果取消重置操作，将什么都不做直接返回
				else if(sfd == JOptionPane.NO_OPTION){
					line_Flag = 0;// 取消连线，等待用户调整标注参数
					return;
				}
			} 
			repaint(); // 重绘界面
		}
		
		else if(jb == win.paintNextButton){ // 绘制下一个并保存当前
			
			//对多次点击绘制绘制下一个按钮的容错性处理
			if(!(pointsVector.isEmpty()) && !(linesVector.isEmpty())){
				
				Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(pointsVector); // 创建pointsVector子关节点容器对象
				for (Iterator<MyPoint> iterator = pointsVector_Child.iterator(); iterator.hasNext();) {
					
					MyPoint myPoint = (MyPoint) iterator.next();
					myPoint.parameter = Integer.parseInt(win.parameter); // 为每个关节点缓存标注参数

				}
				Vector<Line> linesVector_Child = new Vector<Line>(linesVector); // 定义linesVector子线框容器对象
				pointsVector_parent.add(pointsVector_Child); // 将子关节点容器对象加入父关节点容器中
				linesVector_parent.add(linesVector_Child); // 将子线框容器对象加入父线框容器中
			}
			//如果用户多次点击绘制绘制下一个按钮，将会直接退出事件，不做任何处理
			if(pointsVector.isEmpty() || linesVector.isEmpty()){
				style = 1; // 启用画轮廓线功能
				is_DrawAnchor = false; // 禁用锚点绘制功能
				line_Flag = 0; // 禁止连线功能
				line = null;
				myPoint = null; // 并将缓存点对象置空，防止干扰即上一次残留的线条在界面上
				return;
			}
			
			style = 1; // 启用画轮廓线功能
			is_DrawAnchor = false; // 禁用锚点绘制功能
			line_Flag = 0; // 禁止连线功能
			linesVector.clear();
			pointsVector.clear();
			line = null;
			myPoint = null; // 并将缓存点对象置空，防止干扰即上一次残留的线条在界面上
		}
		
		else if (jb == win.font_Button)// 线条字体粗细设置按钮
		{
			Font font;
			font = new Font("新宋体", Font.BOLD, 12);
			font_Temp = FontChooser.showDialog(win, null, font); // 创建字体选择对话框
			if (font_Temp != null) // 判断是否选择了"确定"按钮
			{
				m_font = font_Temp;
				
				repaint();// 重绘界面
				
			} else {
				//m_font = new Font("新宋体", Font.BOLD, 24);
			}
		}
		
		else if (jb == win.color_Button) // 颜色设置按钮
		{
			Color c = JColorChooser.showDialog(win, "请选择颜色", Color.cyan);// 创建颜色选择对话框
			if (c != null) {
				
				RGB = new int[3];
				RGB[0] = c.getRed();
				RGB[1] = c.getGreen();
				RGB[2] = c.getBlue();
				repaint();// 重绘界面
				
			} else{
				return;
			}
			
		}
	
	}
	
	// 重置函数
	public void reset() {
		//清空容器
		linesVector.clear();
		pointsVector.clear();
		line = null;
		myPoint = null; // 并将缓存点对象置空，防止干扰即上一次残留的线条在界面上
		style = 3; // 禁用所有绘制模式
		is_DrawAnchor = false; // 禁用锚点绘制功能
		line_Flag = 0; // 禁止连线功能
	}

	/**
	 * 无参构造函数，初始化，默认为label对象添加鼠标相关的监视器
	 */
	//无参构造函数，初始化，默认为label对象添加鼠标相关的监视器
	public MyLabel() {
		LineListener  listener = new LineListener();
		//添加鼠标监听事件
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
	}
	/**
	 * @param title
	 */
	//有参构造函数
	public MyLabel(String title) {
		super(title);	
	}
	
	/* （非 Javadoc）
	 * @see javax.swing.JComponent#update(java.awt.Graphics)
	 */
	//更新界面，自动调用paintComponent方法
	@Override
	public void update(Graphics g) {
		super.update(g);
	}
	
	/* （非 Javadoc）
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	//覆写paintComponent方法，绘制线条
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		Font myFont = new Font("TimesRoman",Font.BOLD,20); //设置画笔格式
		g.setFont(myFont);
	    drawOvalStyle(g); // 调用画线算法
		drawJoint(g); // 调用画关节点算法
	}
	/**
	 * @param g
	 * @return 
	 */
	public boolean drawJoint(Graphics g) {
		g.setColor(Color.RED);
		// 判断线框容器是否为空，若不为空则遍历容器并调用画线和画点方法
		if(!(pointsVector.isEmpty())){
			
			for (int i = 0; i < pointsVector.size(); i++) {
				MyPoint tmp = (MyPoint) pointsVector.elementAt(i); // 取出子容器中存储的坐标值
				mydraw = new Mydraw(win.getMyLabel()); // 创建绘画对象
				mydraw.myDrawPoints(tmp, g); // 画点			
			}
			// 判断是否是连线状态
			if(line_Flag == 1){
				mydraw = new Mydraw(win.getMyLabel());
				Coordinate_Transformation transaction = new Coordinate_Transformation(linesVector,pointsVector);
				transaction.setParameter(Integer.parseInt(win.parameter));
				// 调用转换坐标方法
				if(!transaction.change_coordinate()){
					return false; // 函数调用失败
				}
				// 调用分层方法，将每层的关节点按人体特点分别放入五个容器中
				if(!transaction.divide_Layer()){
					return false; // 函数调用失败
				} 
				//获取各个容器
				Vector<MyPoint> vector1 = transaction.getVector1();
				Vector<MyPoint> vector2 = transaction.getVector2();
				Vector<MyPoint> vector3 = transaction.getVector3();
				Vector<MyPoint> vector4 = transaction.getVector4();
				Vector<MyPoint> vector5 = transaction.getVector5();

				if(vector1.size() != 3 | vector2.size() != 3 | vector3.size() != 3 | vector4.size() != 2 | vector5.size() != 2){
					return false;
				}

				mydraw.myDrawLine(vector1, vector2, vector3, vector4, vector5, g); // 连接容器中的节点
			}
			
		}
		
		
		//循环节点父容器
		for (int j = 0; j < pointsVector_parent.size(); j++) {
			Vector<MyPoint> pointsVector_Child = (Vector<MyPoint>) pointsVector_parent.elementAt(j); // 获取子容器
			// 绘制关节点
			for (int i = 0; i < pointsVector_Child.size(); i++) {
				MyPoint tmp = (MyPoint) pointsVector_Child.elementAt(i); // 取出子容器中存储的坐标值
				mydraw = new Mydraw(win.getMyLabel()); // 创建绘画对象
				mydraw.myDrawPoints(tmp, g); // 画点			
			}
			// 连接关节点
			Vector<Line> linesVector_Child = (Vector<Line>) linesVector_parent.elementAt(j); // 获取对应子容器
			mydraw = new Mydraw(win.getMyLabel());
			Coordinate_Transformation transaction = new Coordinate_Transformation(linesVector_Child,pointsVector_Child);
			transaction.setParameter(Integer.parseInt(win.parameter));
			
			// 调用转换坐标方法
			if(!transaction.change_coordinate()){
				return false; // 函数调用失败
			}
			
			if(!transaction.divide_Layer()){// 调用分层方法，将每层的关节点按人体特点分别放入五个容器中
				//调用函数失败
				return false;
			} 
			
			//获取各个容器
			Vector<MyPoint> vector1 = transaction.getVector1();
			Vector<MyPoint> vector2 = transaction.getVector2();
			Vector<MyPoint> vector3 = transaction.getVector3();
			Vector<MyPoint> vector4 = transaction.getVector4();
			Vector<MyPoint> vector5 = transaction.getVector5();
			mydraw.myDrawLine(vector1, vector2, vector3, vector4, vector5, g); // 连接容器中的节点
			}
		return true;
	}	

	public void drawOvalStyle(Graphics g) {
		
		// 判断线框容器是否为空，若不为空则遍历容器并调用画线方法
		if(!(linesVector.isEmpty())){
			for (int i = 0; i < linesVector.size(); i++) {
				Line tmp = (Line) linesVector.elementAt(i); // 取出容器中存储的坐标值
				mydraw = new Mydraw(win.getMyLabel()); // 创建绘画对象
				mydraw.MydrawOval2(tmp.x0, tmp.y0, tmp.x1, tmp.y1, g); // 调用数值微分法的画线算法，进行画线	
			}
		}
		//循环容器
		for (int k = 0; k < linesVector_parent.size(); k++) {
			Vector<Line> linesVector_Child = (Vector<Line>) linesVector_parent.elementAt(k); // 获取子容器
			for (int i = 0; i < linesVector_Child.size(); i++) {
				Line tmp = (Line) linesVector_Child.elementAt(i); // 取出子容器中存储的坐标值
				mydraw = new Mydraw(win.getMyLabel()); // 创建绘画对象
				mydraw.MydrawOval2(tmp.x0, tmp.y0, tmp.x1, tmp.y1, g); // 调用数值微分法的画线算法，进行画线	
			}
		}
		//如果鼠标仍处于拖动状态而未抬起，利用最新line对象读取坐标，继续画当前线条
		if (line != null) {
			mydraw = new Mydraw(win.getMyLabel());
			mydraw.MydrawOval2(line.x0, line.y0, line.x1, line.y1, g);
		}
			
	}

	
	/*
	 *MyLabel对象内封装了 MouseListener,MouseMotionListener等监视器
	 *
	 * 响应并响应鼠标点击时的坐标位置的记录
	 *
	 * */

		//构建监视器，为绘图画线操作准备
		private class LineListener implements MouseListener,MouseMotionListener
		{
			public void mousePressed(MouseEvent event)
			{
				//记下鼠标左键按下时的初始位置
				if(event.getButton() == MouseEvent.BUTTON1){
					//判断画线功能是否开启
					if(style == 1){
						line = new Line();
						line.x0 = event.getX();
						line.y0 = event.getY();
					}
				}
				
				//鼠标中击情况
				else if(event.getButton() == MouseEvent.BUTTON2){
					//判断是否开启画锚点功能开启
					if(is_DrawAnchor){
						//创建一个封装了 上一个对象的第二个点，当前的第一个点以及当前的第二个点的myPoint对象
						myPoint = new MyPoint();
						//获取第一个点对象
						myPoint.point = event.getPoint();
						//最后点击的点缓存下来，供按钮使用
						tmpP = new Point(event.getPoint().x,event.getPoint().y);
						myPoint.anchor = true; // 设置为锚点
						pointsVector.add(myPoint);
						repaint(); // 调用重绘功能实现，显现实时显示
				    }
				
				}
				
				//如果鼠标右击，则只能保存关节点功能
				else if(event.getButton() == MouseEvent.BUTTON3){
					if(style == 0){
					
							//创建一个封装了 上一个对象的第二个点，当前的第一个点以及当前的第二个点的myPoint对象
							myPoint = new MyPoint();
							//获取第一个点对象
							myPoint.point = event.getPoint();
							//最后点击的点缓存下来，供按钮使用
							tmpP = new Point(event.getPoint().x,event.getPoint().y);
							pointsVector.add(myPoint);
							repaint(); // 调用重绘功能实现，显现实时显示
					}	
				}
			}
			// 鼠标拖动时操作
			/** (Javadoc)
			 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
			 */
			public void mouseDragged(MouseEvent event){
				if(event.getModifiers() == MouseEvent.BUTTON1_MASK){
					if(style == 1){
						//获取line坐标
						line.x1 = event.getX();
						line.y1 = event.getY();
						repaint(); // 重绘画面
					}
				}
			}
			// 鼠标释放时操作
			public void mouseReleased(MouseEvent event){
				if(event.getButton() == MouseEvent.BUTTON1){
					if(style == 1){
						//获取最后line坐标
						line.x1 = event.getX();
						line.y1 = event.getY();
						linesVector.add(line);
						line = null;
					}
				}
			}
			public void mouseClicked(MouseEvent event){};
			public void mouseEntered(MouseEvent event){};
			public void mouseExited(MouseEvent event){};
			public void mouseMoved(MouseEvent event){};
		}

}
