/**
 * @author ½��
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
 *����������
 *���ڽ���ע��ĸ��ֲ��� 
 */

/*
 * ��MyLabel��װ��Jlabel�����࣬ͬʱʵ��ActionListener�ӿ�
 * ��װ����һ�������������ڼ��ӵ���ؽڰ�ť
 * ��ΪͼƬ������͹���ƽ̨
 */
public class MyLabel extends JLabel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	Vector<MyPoint> pointsVector = new Vector<MyPoint>(); // ����pointsVector�������ڴ洢�ؽڵ�
	Vector<Vector<MyPoint>> pointsVector_parent = new Vector<Vector<MyPoint>>();//����Ԫ��������������
	MyPoint myPoint = null; // ������ʱ�ĵ����Ĵ洢
	MyPoint tmpPoint = null; // ������ʱ�����ļ��еĵ㣬Ϊ��ֹ�������myPoint����������ڴ˽����˸���
	Point tmpP; // ������Ա����,�����һ�㣬�����ж�����һ�λ������ϵ
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
	//��ȡpointsVector
	public Vector<MyPoint> getPointsVector() {
		return pointsVector;
	}
	Vector<Line> linesVector = new Vector<Line>(); // ����linesVector���������ڴ洢�߶�������ȷ��һ����
	Vector<Vector<Line>> linesVector_parent = new Vector<Vector<Line>>();//����Ԫ��������������
	// ���ظ��ؽڵ�����
	public Vector<Vector<MyPoint>> getPointsVector_parent() {
		return pointsVector_parent;
	}
	//���ظ��߿�����
	public Vector<Vector<Line>> getLinesVector_parent() {
		return linesVector_parent;
	}
	Line line = null; // �����߳�Ա����
	Vector<Boolean> booleansVector = new Vector<Boolean>(); // ���ñ����־
	public int style = 3; // �������ģʽ��ǣ�Ĭ�Ͻ������л���ģʽ
	//���嵱ǰ��������Ԫ�صĸ���,���뱩¶�������ʹ��
	public int pointsVector_size;
	public int linesVector_size;
	public int line_Flag = 0; // Ĭ�Ϲر����߹���
	public boolean is_DrawAnchor = false; // Ĭ�Ϲرջ�ê�㹦��
	
	static WindowOperation win; // ���崰������������ڽ��մ������Ĵ�����������
	
	/**
	 * @return linesVector
	 */
	//����linesVector����
	public Vector<Line> getLinesVector() {
		return linesVector;
	}
	
	/**
	 * @param win Ҫ���õ� win
	 */
	//��ȡwin�������
	public static void setWin(WindowOperation wins) {
		win = wins;
	}


	/*
	 * ����ȡ��������ֵ�Զ����»��Ƶ�ͼƬ�ϣ��� Javadoc��
	 *�������밴ť�¼�����Ӧ�÷���
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	//�¼���Ӧ��������inPutCoordinateButton��resetButton��changeToDrawLines��changeToDrawPoints��cutConnectionButton�Ȱ�ť
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton) e.getSource(); 
		//����������¼�����inPutCoordinateButton��ť����ִ�е������꣬���ƹؽڲ���
		// ����ؽ�
		if(jb == win.inPutCoordinateButton){
			//��ֹ�û��Ҳ�����ť���粢δ��ͼƬ�ͽ�����صİ�ť����
			if(win.txtPath == null){
				JOptionPane.showMessageDialog(null, 
						"�û�δ��ͼƬ���޷��ҵ������ļ�������ʧ��", "������Ϣ", JOptionPane.ERROR_MESSAGE);
				//�������ȫ�����ݣ��ų��ϴβ������ݵĸ���
				linesVector_parent.clear();
				pointsVector_parent.clear();
				reset(); //�������ò�������ջ���
				repaint(); // �ػ����
				return;
				}
			
			BufferedReader br =null; // �������������󣬶�ȡ�ļ�Ч�ʽϸ�
			String[] buf = null; // �����ַ������飬���ڴ洢����������������
			//�������ȫ�����ݣ��ų��ϴβ������ݵĸ���
			linesVector_parent.clear();
			pointsVector_parent.clear();
			reset(); // �������ò�������ջ���
			repaint(); // �ػ���棬���������������
			try {
				File m_file = new File(win.txtPath);
				if(!m_file.exists()){ // ���������ļ�������
					JOptionPane.showMessageDialog(null, "������ע�����ļ������ڣ��������������ע�󲢱���󣬲���ִ�д˲���Ŷ������", "������Ϣ", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				//�Ի����ļ���д���ķ�ʽ��ȡ�ļ����ļ�·��Ϊ����win.txtPath����txtpath�Ǽ���ͼƬʱԤ����win�����е����Ա����
				br = new BufferedReader(new FileReader(win.txtPath));
				//CoordinateIfo.txt�ļ����һ��Ϊ�գ��������Ҫ�ȿն�һ�У���ֹ�����������
				String s = br.readLine();
				//��������Ϊ���ļ���ȡ���������������linesVector�����е�������ֵ
				while((s = br.readLine()) != null){
					//�����ַ�������ķֽⷽ�����õ��ַ�������
					buf = s.split(",");
					//��ȡ�ַ������鳤��
					int length = buf.length;
					// �����߿�����
					Vector<Line> linesVector_Child = new Vector<Line>(); // ����linesVector���������ڴ洢�߶�������ȷ��һ����
					//ѭ���ַ������飬��linesVector_Child�����������ֵ
					for (int i = 0; i < 16; i+=4) {
						Line temLine = new Line(); // ����Line���󣬴洢����
						temLine.x0 = Integer.parseInt(buf[i]);
						temLine.y0 = Integer.parseInt(buf[i+1]);
						temLine.x1 = Integer.parseInt(buf[i+2]);
						temLine.y1 = Integer.parseInt(buf[i+3]);
						//��line����ӵ�������
						linesVector_Child.add(temLine);	
					}
					linesVector_parent.add(linesVector_Child); // �����������븸�߿�������
					
					// ����ؽڵ�����
					Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(); // ����pointsVector�������ڴ洢�ؽڵ�
					
					int m_parameter = 15;// �����ע����������ʼ��Ϊ15
					if( buf[16].equals("parameter:")){
						m_parameter = Integer.parseInt(buf[17]); // ��ȡ��ע����
					}
					
					int flag = 0;
					for(int j = 18; j < length; j+=2){
						MyPoint temP = null;
						if(buf[j].equals("anchor") && buf[j+1].equals(":")){
							flag = 1;
							continue;
						}
						else if(buf[j].equals("���") && buf[j+1].equals(":")){
							flag = 2;
							continue;
						}
						else if(buf[j].equals("�ұ�") && buf[j+1].equals(":")){
							flag = 3;
							continue;
						}
						else if(buf[j].equals("����") && buf[j+1].equals(":")){
							flag = 4;
							continue;
						}
						else if(buf[j].equals("����") && buf[j+1].equals(":")){
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
							//��ȡ�ؽڵ�����
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
							//��ȡ�ؽڵ�����
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
							//��ȡ�ؽڵ�����
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
							//��ȡ�ؽڵ�����
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
							//��ȡ�ؽڵ�����
							pointsVector_Child.add(temP);
							continue;
						}
					
					}
					pointsVector_parent.addElement(pointsVector_Child); // �����������븸�ؽڵ�������
				}
			} 
			catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��n
				e1.printStackTrace();
			}
			catch(NumberFormatException e1){
				//�ļ�����������������ٻ��ƹؽں�����
				linesVector_parent.clear();
				pointsVector_parent.clear();
				reset();//�������ò�������ջ���
				JOptionPane.showMessageDialog(null, "�����ļ����𻵣�����ʧ��", "������Ϣ", 
						JOptionPane.ERROR_MESSAGE);
			}
			finally{
				if(br != null)
					try {
						br.close();
					} catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
						throw new RuntimeException("�ر�ʧ��");
					}
			 }
			//�ػ�ؽ�
			repaint();
		}
		
		//����������¼�����resetButton��ť����ִ�н����õ�ǰ����Ĳ���
		// ���õ�ǰ����
		else if(jb == win.resetButton){
			style = 3; // �������л���ģʽ
			is_DrawAnchor = false; // ����ê����ƹ���
			line_Flag = 0; // ��ֹ���߹���
			//��ֹ�û��Ҳ�����ť���粢δ��ͼƬ�ͽ�����صİ�ť����
			if(win.txtPath == null){
				reset();//�������ò�������ջ���
				repaint(); // �ػ����
				return; // ��������
			}
			int sfd = JOptionPane.showConfirmDialog(null, "ȷ���Ƿ����ã�", "��������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			if(sfd == JOptionPane.YES_OPTION){
				
				reset();//�������ò�������ջ���
				repaint(); // �ػ����
			
			}
			//���ȡ�����ò�������ʲô������ֱ�ӷ���
			else if(sfd == JOptionPane.NO_OPTION){
				return;
			}
		}
		//����������¼�����resetAllButton��ť����ִ������ȫ������Ĳ���
		// ����ȫ������
		else if(jb == win.resetAllButton){
			style = 3; // �������л���ģʽ
			is_DrawAnchor = false; // ����ê����ƹ���
			line_Flag = 0; // ��ֹ���߹���
			//��ֹ�û��Ҳ�����ť���粢δ��ͼƬ�ͽ�����صİ�ť����
			if(win.txtPath == null){
				//�����������
				pointsVector_parent.clear();
				linesVector_parent.clear();
				reset();//�������ò�������ջ���
				repaint(); // �ػ����
				return; // ��������
			}
			BufferedWriter bf = null;
			int sfd = JOptionPane.showConfirmDialog(null, "ȷ���Ƿ����ã������������չ����ڵ���Ϣ�����ļ�������", "����ȫ������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
			if(sfd == JOptionPane.YES_OPTION){
				try {
					//�����������
					pointsVector_parent.clear();
					linesVector_parent.clear();
					reset(); //�������ò�������ջ���
					repaint(); // �ػ����
					
					// ��ֹ�û���δ��������ڵ����ݾͽ���ȫ���������ò�����ɵ�win.txtPath�ļ��޷��ҵ���������������쳣
					if(!new File(win.txtPath).exists()){
						pointsVector_parent.clear();
						linesVector_parent.clear();
						reset();//�������ò�������ջ���
						repaint(); // �ػ����
						return; // ��������
					}
					
					//�Ի����ļ���д���ķ�ʽд���ļ����ļ�·��Ϊ����win.txtPath����txtpath�Ǽ���ͼƬʱԤ����win�����е����Ա����
					bf = new BufferedWriter(new FileWriter(win.txtPath));
					bf.write(""); // ����ı��ĵ�����
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				finally{
					if(bf != null)
						try {
							bf.close(); // �ر��ļ���
						} catch (IOException e1) {
							// TODO �Զ����ɵ� catch ��
							throw new RuntimeException("����ʧ��"); // �׳�����ʱ�쳣
						}
				}
			}
			//���ȡ�����ò�������ʲô������ֱ�ӷ���
			else if(sfd == JOptionPane.NO_OPTION){
				return;
			}
		}
		//��������
		else if(jb == win.changeToDrawLines){
			
			style = 1; // �л����Ʒ�ʽ������㷽ʽ�л������߷�ʽ��ͬʱ�����˻���ģʽ
			is_DrawAnchor = false; // ����ê����ƹ���
			line_Flag = 0; // ��ֹ���߹���
		}
		
		//��ê��
		else if(jb == win.changeToDrawAnchor){
			
			is_DrawAnchor = true; // �л����Ʒ�ʽ�����뻭ê��ģʽ
			style = 3; // ���û���ͻ��߹���
			line_Flag = 0; // ��ֹ���߹���
		}
		
		// ���ؽڵ�
		else if(jb == win.changeToDrawPoints){
			
			style = 0; // �л����Ʒ�ʽ�������߷�ʽ�л�����㷽ʽ��ͬʱ�����˻�������ģʽ
			is_DrawAnchor = false; // ����ê����ƹ���
			line_Flag = 0; // ��ֹ���߹���
		}
		

		// ����
		else if(jb == win.linkButton){
			/*
			 *�ڴ����ùؽڵ�ķֲ��־����������ؽڵ��ص��������Ӹ��� 
			 * */
			line_Flag = 1; // �������߹���
			is_DrawAnchor = false; // ����ê����ƹ���
			style = 3; // ���û��㼰ê��ͻ��߹���
			//�ж���ע�ڵ�λ���Ƿ������ע�����ر����߹���
			if(!drawJoint(this.getGraphics())){
				int sfd = JOptionPane.showConfirmDialog(null, "�ؽ�ע�����ȷ���Ƿ����ã�", "��������ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
				 if(sfd == JOptionPane.YES_OPTION){
					reset();//�������ò�������ջ���
					repaint(); // �ػ����
					return;
				}
				//���ȡ�����ò�������ʲô������ֱ�ӷ���
				else if(sfd == JOptionPane.NO_OPTION){
					line_Flag = 0;// ȡ�����ߣ��ȴ��û�������ע����
					return;
				}
			} 
			repaint(); // �ػ����
		}
		
		else if(jb == win.paintNextButton){ // ������һ�������浱ǰ
			
			//�Զ�ε�����ƻ�����һ����ť���ݴ��Դ���
			if(!(pointsVector.isEmpty()) && !(linesVector.isEmpty())){
				
				Vector<MyPoint> pointsVector_Child = new Vector<MyPoint>(pointsVector); // ����pointsVector�ӹؽڵ���������
				for (Iterator<MyPoint> iterator = pointsVector_Child.iterator(); iterator.hasNext();) {
					
					MyPoint myPoint = (MyPoint) iterator.next();
					myPoint.parameter = Integer.parseInt(win.parameter); // Ϊÿ���ؽڵ㻺���ע����

				}
				Vector<Line> linesVector_Child = new Vector<Line>(linesVector); // ����linesVector���߿���������
				pointsVector_parent.add(pointsVector_Child); // ���ӹؽڵ�����������븸�ؽڵ�������
				linesVector_parent.add(linesVector_Child); // �����߿�����������븸�߿�������
			}
			//����û���ε�����ƻ�����һ����ť������ֱ���˳��¼��������κδ���
			if(pointsVector.isEmpty() || linesVector.isEmpty()){
				style = 1; // ���û������߹���
				is_DrawAnchor = false; // ����ê����ƹ���
				line_Flag = 0; // ��ֹ���߹���
				line = null;
				myPoint = null; // �������������ÿգ���ֹ���ż���һ�β����������ڽ�����
				return;
			}
			
			style = 1; // ���û������߹���
			is_DrawAnchor = false; // ����ê����ƹ���
			line_Flag = 0; // ��ֹ���߹���
			linesVector.clear();
			pointsVector.clear();
			line = null;
			myPoint = null; // �������������ÿգ���ֹ���ż���һ�β����������ڽ�����
		}
		
		else if (jb == win.font_Button)// ���������ϸ���ð�ť
		{
			Font font;
			font = new Font("������", Font.BOLD, 12);
			font_Temp = FontChooser.showDialog(win, null, font); // ��������ѡ��Ի���
			if (font_Temp != null) // �ж��Ƿ�ѡ����"ȷ��"��ť
			{
				m_font = font_Temp;
				
				repaint();// �ػ����
				
			} else {
				//m_font = new Font("������", Font.BOLD, 24);
			}
		}
		
		else if (jb == win.color_Button) // ��ɫ���ð�ť
		{
			Color c = JColorChooser.showDialog(win, "��ѡ����ɫ", Color.cyan);// ������ɫѡ��Ի���
			if (c != null) {
				
				RGB = new int[3];
				RGB[0] = c.getRed();
				RGB[1] = c.getGreen();
				RGB[2] = c.getBlue();
				repaint();// �ػ����
				
			} else{
				return;
			}
			
		}
	
	}
	
	// ���ú���
	public void reset() {
		//�������
		linesVector.clear();
		pointsVector.clear();
		line = null;
		myPoint = null; // �������������ÿգ���ֹ���ż���һ�β����������ڽ�����
		style = 3; // �������л���ģʽ
		is_DrawAnchor = false; // ����ê����ƹ���
		line_Flag = 0; // ��ֹ���߹���
	}

	/**
	 * �޲ι��캯������ʼ����Ĭ��Ϊlabel������������صļ�����
	 */
	//�޲ι��캯������ʼ����Ĭ��Ϊlabel������������صļ�����
	public MyLabel() {
		LineListener  listener = new LineListener();
		//����������¼�
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
	}
	/**
	 * @param title
	 */
	//�вι��캯��
	public MyLabel(String title) {
		super(title);	
	}
	
	/* ���� Javadoc��
	 * @see javax.swing.JComponent#update(java.awt.Graphics)
	 */
	//���½��棬�Զ�����paintComponent����
	@Override
	public void update(Graphics g) {
		super.update(g);
	}
	
	/* ���� Javadoc��
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	//��дpaintComponent��������������
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		Font myFont = new Font("TimesRoman",Font.BOLD,20); //���û��ʸ�ʽ
		g.setFont(myFont);
	    drawOvalStyle(g); // ���û����㷨
		drawJoint(g); // ���û��ؽڵ��㷨
	}
	/**
	 * @param g
	 * @return 
	 */
	public boolean drawJoint(Graphics g) {
		g.setColor(Color.RED);
		// �ж��߿������Ƿ�Ϊ�գ�����Ϊ����������������û��ߺͻ��㷽��
		if(!(pointsVector.isEmpty())){
			
			for (int i = 0; i < pointsVector.size(); i++) {
				MyPoint tmp = (MyPoint) pointsVector.elementAt(i); // ȡ���������д洢������ֵ
				mydraw = new Mydraw(win.getMyLabel()); // �����滭����
				mydraw.myDrawPoints(tmp, g); // ����			
			}
			// �ж��Ƿ�������״̬
			if(line_Flag == 1){
				mydraw = new Mydraw(win.getMyLabel());
				Coordinate_Transformation transaction = new Coordinate_Transformation(linesVector,pointsVector);
				transaction.setParameter(Integer.parseInt(win.parameter));
				// ����ת�����귽��
				if(!transaction.change_coordinate()){
					return false; // ��������ʧ��
				}
				// ���÷ֲ㷽������ÿ��Ĺؽڵ㰴�����ص�ֱ�������������
				if(!transaction.divide_Layer()){
					return false; // ��������ʧ��
				} 
				//��ȡ��������
				Vector<MyPoint> vector1 = transaction.getVector1();
				Vector<MyPoint> vector2 = transaction.getVector2();
				Vector<MyPoint> vector3 = transaction.getVector3();
				Vector<MyPoint> vector4 = transaction.getVector4();
				Vector<MyPoint> vector5 = transaction.getVector5();

				if(vector1.size() != 3 | vector2.size() != 3 | vector3.size() != 3 | vector4.size() != 2 | vector5.size() != 2){
					return false;
				}

				mydraw.myDrawLine(vector1, vector2, vector3, vector4, vector5, g); // ���������еĽڵ�
			}
			
		}
		
		
		//ѭ���ڵ㸸����
		for (int j = 0; j < pointsVector_parent.size(); j++) {
			Vector<MyPoint> pointsVector_Child = (Vector<MyPoint>) pointsVector_parent.elementAt(j); // ��ȡ������
			// ���ƹؽڵ�
			for (int i = 0; i < pointsVector_Child.size(); i++) {
				MyPoint tmp = (MyPoint) pointsVector_Child.elementAt(i); // ȡ���������д洢������ֵ
				mydraw = new Mydraw(win.getMyLabel()); // �����滭����
				mydraw.myDrawPoints(tmp, g); // ����			
			}
			// ���ӹؽڵ�
			Vector<Line> linesVector_Child = (Vector<Line>) linesVector_parent.elementAt(j); // ��ȡ��Ӧ������
			mydraw = new Mydraw(win.getMyLabel());
			Coordinate_Transformation transaction = new Coordinate_Transformation(linesVector_Child,pointsVector_Child);
			transaction.setParameter(Integer.parseInt(win.parameter));
			
			// ����ת�����귽��
			if(!transaction.change_coordinate()){
				return false; // ��������ʧ��
			}
			
			if(!transaction.divide_Layer()){// ���÷ֲ㷽������ÿ��Ĺؽڵ㰴�����ص�ֱ�������������
				//���ú���ʧ��
				return false;
			} 
			
			//��ȡ��������
			Vector<MyPoint> vector1 = transaction.getVector1();
			Vector<MyPoint> vector2 = transaction.getVector2();
			Vector<MyPoint> vector3 = transaction.getVector3();
			Vector<MyPoint> vector4 = transaction.getVector4();
			Vector<MyPoint> vector5 = transaction.getVector5();
			mydraw.myDrawLine(vector1, vector2, vector3, vector4, vector5, g); // ���������еĽڵ�
			}
		return true;
	}	

	public void drawOvalStyle(Graphics g) {
		
		// �ж��߿������Ƿ�Ϊ�գ�����Ϊ����������������û��߷���
		if(!(linesVector.isEmpty())){
			for (int i = 0; i < linesVector.size(); i++) {
				Line tmp = (Line) linesVector.elementAt(i); // ȡ�������д洢������ֵ
				mydraw = new Mydraw(win.getMyLabel()); // �����滭����
				mydraw.MydrawOval2(tmp.x0, tmp.y0, tmp.x1, tmp.y1, g); // ������ֵ΢�ַ��Ļ����㷨�����л���	
			}
		}
		//ѭ������
		for (int k = 0; k < linesVector_parent.size(); k++) {
			Vector<Line> linesVector_Child = (Vector<Line>) linesVector_parent.elementAt(k); // ��ȡ������
			for (int i = 0; i < linesVector_Child.size(); i++) {
				Line tmp = (Line) linesVector_Child.elementAt(i); // ȡ���������д洢������ֵ
				mydraw = new Mydraw(win.getMyLabel()); // �����滭����
				mydraw.MydrawOval2(tmp.x0, tmp.y0, tmp.x1, tmp.y1, g); // ������ֵ΢�ַ��Ļ����㷨�����л���	
			}
		}
		//�������Դ����϶�״̬��δ̧����������line�����ȡ���꣬��������ǰ����
		if (line != null) {
			mydraw = new Mydraw(win.getMyLabel());
			mydraw.MydrawOval2(line.x0, line.y0, line.x1, line.y1, g);
		}
			
	}

	
	/*
	 *MyLabel�����ڷ�װ�� MouseListener,MouseMotionListener�ȼ�����
	 *
	 * ��Ӧ����Ӧ�����ʱ������λ�õļ�¼
	 *
	 * */

		//������������Ϊ��ͼ���߲���׼��
		private class LineListener implements MouseListener,MouseMotionListener
		{
			public void mousePressed(MouseEvent event)
			{
				//��������������ʱ�ĳ�ʼλ��
				if(event.getButton() == MouseEvent.BUTTON1){
					//�жϻ��߹����Ƿ���
					if(style == 1){
						line = new Line();
						line.x0 = event.getX();
						line.y0 = event.getY();
					}
				}
				
				//����л����
				else if(event.getButton() == MouseEvent.BUTTON2){
					//�ж��Ƿ�����ê�㹦�ܿ���
					if(is_DrawAnchor){
						//����һ����װ�� ��һ������ĵڶ����㣬��ǰ�ĵ�һ�����Լ���ǰ�ĵڶ������myPoint����
						myPoint = new MyPoint();
						//��ȡ��һ�������
						myPoint.point = event.getPoint();
						//������ĵ㻺������������ťʹ��
						tmpP = new Point(event.getPoint().x,event.getPoint().y);
						myPoint.anchor = true; // ����Ϊê��
						pointsVector.add(myPoint);
						repaint(); // �����ػ湦��ʵ�֣�����ʵʱ��ʾ
				    }
				
				}
				
				//�������һ�����ֻ�ܱ���ؽڵ㹦��
				else if(event.getButton() == MouseEvent.BUTTON3){
					if(style == 0){
					
							//����һ����װ�� ��һ������ĵڶ����㣬��ǰ�ĵ�һ�����Լ���ǰ�ĵڶ������myPoint����
							myPoint = new MyPoint();
							//��ȡ��һ�������
							myPoint.point = event.getPoint();
							//������ĵ㻺������������ťʹ��
							tmpP = new Point(event.getPoint().x,event.getPoint().y);
							pointsVector.add(myPoint);
							repaint(); // �����ػ湦��ʵ�֣�����ʵʱ��ʾ
					}	
				}
			}
			// ����϶�ʱ����
			/** (Javadoc)
			 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
			 */
			public void mouseDragged(MouseEvent event){
				if(event.getModifiers() == MouseEvent.BUTTON1_MASK){
					if(style == 1){
						//��ȡline����
						line.x1 = event.getX();
						line.y1 = event.getY();
						repaint(); // �ػ滭��
					}
				}
			}
			// ����ͷ�ʱ����
			public void mouseReleased(MouseEvent event){
				if(event.getButton() == MouseEvent.BUTTON1){
					if(style == 1){
						//��ȡ���line����
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
