/**
 * 
 */
package cn.detector.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import cn.detector.view.MyLabel;
import cn.detector.view.WindowOperation;

/**
 * 关节坐标信息保存类
 *
 */
public class Save_Information{
	//定义换行常量，根据运行的不同系统环境，调用当前系统的换行符常量，体现平台无关性
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	Vector<Vector<Line>> linesVector_parent = new Vector<Vector<Line>>();//定义元素是容器的容器
	Vector<Vector<MyPoint>> pointsVector_parent = new Vector<Vector<MyPoint>>();//定义元素是容器的容器
	
	public static WindowOperation win;
	public WindowOperation getWin() {
		return win;
	}

	public static void setWin(WindowOperation wins) {
		win = wins;
	}

	private String path_contains_pic;
	public String txtPath;
	/**
	 * @return txtPath
	 */
	@SuppressWarnings("unused")
	private  MyLabel myLabel;
	private String fileName;
	/**
	 * @param linesVector
	 * @param path_contains_pic
	 * @param picName
	 * @param myLabel
	 */
	// 构造函数
	public Save_Information(String path_contains_pic, String txtPath,String fileName,MyLabel myLabel) {
		super();
		this.path_contains_pic = path_contains_pic;
		this.fileName = fileName;
		this.txtPath = txtPath;
		this.myLabel = myLabel;
		linesVector_parent = myLabel.getLinesVector_parent();
		pointsVector_parent = myLabel.getPointsVector_parent();
		
	}

	/**
	 * @param myLabel 要设置的 myLabel
	 */
	
	public void save(){
		BufferedWriter bf = null ;
		
		if (pointsVector_parent.size() == 0) {// 容错性判断
			JOptionPane.showMessageDialog(null, "节点数据为空，保存失败", "错误信息", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {	
			File file = new File(path_contains_pic + File.separator + fileName);
			
			file.mkdir();
			//以缓冲文件写入流打开文件，并以清空原来数据的方式打开
			bf = new BufferedWriter(new FileWriter(txtPath));

			//循环节点父容器
			for (int j = 0; j < pointsVector_parent.size(); j++) {
				int count = 1;
				// 保存线框坐标
				Vector<Line> linesVector_Child = (Vector<Line>) linesVector_parent.elementAt(j); //通过父线框容器 获取对应子容器
				// 绘制线框
				for (int i = 0; i < linesVector_Child.size(); i++) {
					Line temLine = (Line) linesVector_Child.elementAt(i); // 取出子容器中存储的线框对象
					
					if(count == 1){
						bf.write(LINE_SEPARATOR); // 换行
						count = 0;
					}
					
					//解析坐标，并写入CoordinateIfo.txt文件中
					bf.write(Integer.toString(temLine.x0) + "," + Integer.toString(temLine.y0) + ","+
					Integer.toString(temLine.x1)+","+Integer.toString(temLine.y1)+",");
			
					//及时刷新，防止意外而导致文件数据丢失
					bf.flush();
				}
				// 保存关节点坐标
				Vector<MyPoint> pointsVector_Child = (Vector<MyPoint>) pointsVector_parent.elementAt(j); // 获取子容器
				
				// 绘制关节点
				int step = 0; // 用于限制写入文件的人体各个部位名称只写一次
				for (int i = 0; i < pointsVector_Child.size(); i++) {
					MyPoint tmP = (MyPoint) pointsVector_Child.elementAt(i); // 取出子容器中存储的点对象
					if(i == 0){
						bf.write("parameter:" + "," + Integer.toString(tmP.parameter) +  ",");
					}
					
					if(tmP.anchor){// 判断是否是锚点
						bf.write("anchor" + "," + 
								":" + ",");
					}
					else if(tmP.body_Parts == 1){// 判断是否是左臂
						if(step == 0){
							bf.write("左臂" + "," + 
									":" + ",");
						}
						step = 1;
					}
					else if(tmP.body_Parts == 2){// 判断是否是右臂
						if(step == 1){
							bf.write("右臂" + "," + 
									":" + ",");
						}
						step = 2;
						
					}
					else if(tmP.body_Parts == 3){// 判断是否是左腿
						if(step == 2){
							bf.write("左腿" + "," + 
									":" + ",");
						}
						step = 3;
						
					}
					else if(tmP.body_Parts == 4){// 判断是否是右腿
						if(step == 3){
							bf.write("右腿" + "," + 
									":" + ",");
						}
						step = 4;
						
					}
					
					bf.write(Integer.toString(tmP.point.x) + "," + 
							Integer.toString(tmP.point.y) + ",");
					//及时刷新，防止意外而导致文件数据丢失
					bf.flush();
				}
					
			}
			
		} 
		catch (IOException e1) {
			// TODO 自动生成的 catch 块n
			
			e1.printStackTrace();
		}
		finally{
			if(bf != null)
			try {
				bf.close();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				throw new RuntimeException("关闭失败");
				}
		}
	}

	
	
//		// 判断双腿是否交叉函数
//		private boolean is_PropIntersectPoint(MyPoint p1, MyPoint p2, MyPoint p3, MyPoint p4) { // 判断双腿是否交叉
//			double x1 = p1.point.x, y1 = p1.point.y, x2 = p2.point.x, y2 = p2.point.y;   
//			double a = (y1 - y2) / (x1 - x2);  
//			double b = (x1 * y2 - x2 * y1) / (x1 - x2);  
//			System.out.println("求出该直线方程为: y=" + a + "x + " + b);  
//			
//			//第二条  
//			double x3 = p3.point.x, y3 = p3.point.y, x4 = p4.point.x, y4 = p4.point.y;  
//			double c = (y3 - y4) / (x3 - x4);  
//			double d = (x3 * y4 - x4 * y3) / (x3 - x4);  
//			System.out.println("求出该直线方程为: y=" + c + "x + " + d);  
//			
//			double x = ((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))  
//					/ ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));  
//			
//			double y = ((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))  
//					/ ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));
//			if(x > p1.point.x && x < p4.point.x && y > p1.point.y && y < p2.point.y){// 说明两线交点在允许范围内，双腿处于交叉状态
//				
//				return true;	
//			}
//			else{// 否则该交点为虚交点，不在允许范围内，双腿未交叉
//				
//				return false;
//			}
//		}
//		
	
//	if(!file.exists() && !file.isDirectory()){// 如果文件夹不存在则创建 
//		//创建文件夹
//		file.mkdir();
//	}
//	
//	else{// 文件夹已存在
//		int n = JOptionPane.showConfirmDialog(myLabel, fileName + "文件夹已存在，是否替换",
//				 "保存提醒", JOptionPane.YES_NO_OPTION);
//			if(n == JOptionPane.YES_OPTION){
//				// 替换
//				if(deleteDir(file)){
//					file.mkdir();
//					
//				}
//				else{
//					JOptionPane.showMessageDialog(null, "删除替换失败", "错误信息", 
//							JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				
//			}
//			else if(n == JOptionPane.NO_OPTION){
//				//不替换
//				
//			}
//	
//	}
	
//	private boolean deleteDir(File dir) {
//		 if (dir.isDirectory()) {
//	            String[] children = dir.list();
//	            //递归删除目录中的子目录下
//	            for (int i=0; i<children.length; i++) {
//	                boolean success = deleteDir(new File(dir, children[i]));
//	                if (!success) {
//	                    return false;
//	                }
//	            }
//	        }
//	        // 目录此时为空，可以删除
//	        return dir.delete();
//	}

	
}
