package cn.detector.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Toolkit;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*           此字体对话框的使用方法
*   Font font = null;
*   font = FontChooser.showDialog(this,null,font);
*   textArea.setFont(font);
*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class FontChooser extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] styleList = new String[] { "常规", "粗体", "斜体", "粗斜体","下划线" };
	String[] sizeList = new String[] { "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "22", "24", "26", "28", "30", "34", "39", "45", "50", "60", "72","100" };
	MyList StyleList;
	MyList FontList;
	MyList SizeList;
	static JLabel Sample = new JLabel();
	public boolean isSelected = false;

	private FontChooser(Frame parent, boolean modal, Font font) {
		super(parent, modal);
		initAll();
		setTitle("字体");
		if (font == null)
			font = Sample.getFont();
		FontList.setSelectedItem(font.getName());
		SizeList.setSelectedItem(font.getSize() + "");
		StyleList.setSelectedItem(styleList[font.getStyle()]);
	}

	public static Font showDialog(Frame parent, String s, Font font) {
		FontChooser fd = new FontChooser(parent, true, font);
		if (s != null)
			fd.setTitle(s);
		fd.setVisible(true);
		Font fo = null;
		if (fd.isSelected)
			fo = Sample.getFont(); // 如果选择OK按钮,则获取字体
		fd.dispose();
		return (fo);
	}

	private void initAll() {
		getContentPane().setLayout(null);
		int W = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int H = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setBounds((W - 460) / 2, (H - 490) / 2, 460, 490); // 设置字体窗口居屏幕中间显示
		addLists();
		addButtons();
		Sample.setBounds(10, 312, 430, 90);
		Sample.setForeground(Color.black);
		getContentPane().add(Sample);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				setVisible(false);
				isSelected = false;
			}
		});
	}

	private void addLists() {
		FontList = new MyList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		StyleList = new MyList(styleList);
		SizeList = new MyList(sizeList);
		FontList.setBounds(10, 10, 260, 295);
		StyleList.setBounds(280, 10, 80, 295);
		SizeList.setBounds(370, 10, 70, 295);
		getContentPane().add(FontList);
		getContentPane().add(StyleList);
		getContentPane().add(SizeList);
	}

	private void addButtons() {
		JButton ok = new JButton("确定");
		ok.setMargin(new Insets(0, 0, 0, 0));
		JButton ca = new JButton("取消");
		ca.setMargin(new Insets(0, 0, 0, 0));
		ok.setBounds(260, 410, 70, 30);
		ok.setFont(new Font(" ", 1, 12));
		ca.setBounds(340, 410, 70, 30);
		ca.setFont(new Font(" ", 1, 12));
		getContentPane().add(ok);
		getContentPane().add(ca);

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				isSelected = true;
			}
		});

		ca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				isSelected = false;
			}
		});

	}

	private void showSample() {
		int g = 0;
		try {
			g = Integer.parseInt(SizeList.getSelectedValue());
		} catch (NumberFormatException nfe) {
		}
		String st = StyleList.getSelectedValue();
		int s = Font.PLAIN;
		if (st.equalsIgnoreCase("粗体"))
			s = Font.BOLD;
		if (st.equalsIgnoreCase("斜体"))
			s = Font.ITALIC;
		if (st.equalsIgnoreCase("粗斜体"))
			s = Font.ITALIC | Font.BOLD;
		Sample.setFont(new Font(FontList.getSelectedValue(), s, g));
		Sample.setHorizontalAlignment(SwingConstants.LEFT);
		Sample.setBorder(BorderFactory.createEtchedBorder());
		Sample.setText("这是一个字体示例(This is a font example)");
	}

	@SuppressWarnings("serial")
	public class MyList extends JPanel {
		JList<?> jl;
		JScrollPane sp;
		JLabel jt;
		String si = " ";

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public MyList(String[] values) {
			setLayout(null);
			jl = new JList(values);
			sp = new JScrollPane(jl);
			jt = new JLabel();
			jt.setBackground(Color.white);
			jt.setForeground(Color.black);
			jt.setOpaque(true);
			jt.setBorder(new JTextField().getBorder());
			jt.setFont(getFont());
			jl.setBounds(0, 0, 100, 1000);
			jl.setBackground(Color.white);
			jl.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					jt.setText((String) jl.getSelectedValue());
					si = (String) jl.getSelectedValue();
					showSample();
				}
			});
			add(sp);
			add(jt);
		}

		public String getSelectedValue() {
			return (si);
		}

		public void setSelectedItem(String s) {
			jl.setSelectedValue(s, true);
		}

		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, y, w, h);
			sp.setBounds(0, y + 12, w, h - 23);
			sp.revalidate();
			jt.setBounds(0, 0, w, 20);
		}
	}
}
