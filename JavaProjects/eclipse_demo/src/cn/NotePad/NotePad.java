/**
 * 
 */
/**
 * @author A
 *
 */
package cn.NotePad;

import java.io.File;
import java.io.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
public class NotePad extends JFrame implements ActionListener,ItemListener{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton b_save, b_close;
	JTextArea textArea;
	Boolean isCurrentFileExists = false;
	File tempFile;
	JPanel jp;
	JMenu file, edit, style, help;
	JCheckBoxMenuItem s_autoLine;
	JMenuItem f_new, f_open, f_save, f_close, f_saveas, e_copy, e_paste, e_cut, e_clear, e_selectAll, s_font, s_color,
			h_editor, h_help;
	JMenuBar jmb;
	JScrollPane jsp;
	JPopupMenu popUpMenu = new JPopupMenu();
	JLabel stateBar;
	JLabel jl, jj;
	JFileChooser jfc = new JFileChooser();
	JMenuItem je_copy, je_paste, je_cut, je_clear, je_selectAll;

	public NotePad() {
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("文本文件(*.txt)", "txt"));
		jp = new JPanel();
		jl = new JLabel("JAVA记事本V1.0");
		jj = new JLabel("状态栏:");
		jmb = new JMenuBar();
		textArea = new JTextArea();
		jsp = new JScrollPane(textArea);
		file = new JMenu("文件");
		edit = new JMenu("编辑");
		style = new JMenu("格式");
		help = new JMenu("帮助");
		je_copy = new JMenuItem("复制");
		je_paste = new JMenuItem("粘贴");
		je_cut = new JMenuItem("剪切");
		je_clear = new JMenuItem("清除");
		je_selectAll = new JMenuItem("全选");
		je_copy.addActionListener(this);
		je_paste.addActionListener(this);
		je_cut.addActionListener(this);
		je_clear.addActionListener(this);
		je_selectAll.addActionListener(this);
		f_new = new JMenuItem("新建(N)");
		f_new.setMnemonic('N'); // 设置无修饰符快捷键
		f_new.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK, false)); // 设置带修饰符快捷键
		f_new.addActionListener(this);
		f_open = new JMenuItem("打开(O)");
		f_open.setMnemonic('O');
		f_open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK, false));
		f_open.addActionListener(this);
		f_save = new JMenuItem("保存(S)");
		f_save.setMnemonic('S');
		f_save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK, false));
		f_save.addActionListener(this);
		f_saveas = new JMenuItem("另存为");
		f_saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		f_saveas.addActionListener(this);
		f_close = new JMenuItem("关闭(W)");
		f_close.setMnemonic('W');
		f_close.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_MASK, false));
		f_close.addActionListener(this);
		e_copy = new JMenuItem("复制(C)");
		e_copy.setMnemonic('C');
		e_copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK, false));
		e_copy.addActionListener(this);
		e_paste = new JMenuItem("粘贴(V)");
		e_paste.setMnemonic('V');
		e_paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK, false));
		e_paste.addActionListener(this);
		e_cut = new JMenuItem("剪切(X)");
		e_cut.setMnemonic('X');
		e_cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK, false));
		e_cut.addActionListener(this);
		e_clear = new JMenuItem("清除(D)");
		e_clear.setMnemonic('D');
		e_clear.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK, false));
		e_clear.addActionListener(this);
		e_selectAll = new JMenuItem("全选(A)");
		e_selectAll.setMnemonic('A');
		e_selectAll.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK, false));
		e_selectAll.addActionListener(this);
		s_font = new JMenuItem("字体(T)");
		s_font.setMnemonic('T');
		s_font.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK, false));
		s_font.addActionListener(this);
		s_color = new JMenuItem("颜色(C)...");
		s_color.setMnemonic('C');
		s_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		s_color.addActionListener(this);
		s_autoLine = new JCheckBoxMenuItem("自动换行", true);
		s_autoLine.addItemListener(this);
		h_editor = new JMenuItem("关于记事本");
		h_editor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		h_editor.addActionListener(this);
		h_help = new JMenuItem("帮助信息(H)");
		h_help.setMnemonic('H');
		h_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		h_help.addActionListener(this);
		stateBar = new JLabel("新文档");
		stateBar.setHorizontalAlignment(SwingConstants.LEFT);
		stateBar.setBorder(BorderFactory.createEtchedBorder());
		// 添加右键弹出式菜单
		// popUpMenu = edit.getPopupMenu();
		popUpMenu.add(je_copy);
		popUpMenu.add(je_paste);
		popUpMenu.add(je_cut);
		popUpMenu.add(je_clear);
		popUpMenu.addSeparator();
		popUpMenu.add(je_selectAll);

		// 编辑区键盘事件
		textArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				stateBar.setText("已修改");
			}
		});
		// 编辑区鼠标事件,点击右键弹出"编辑"菜单
		textArea.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					popUpMenu.show(e.getComponent(), e.getX(), e.getY());
			} // e.getComponent()和textArea具有同等效果

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					popUpMenu.setVisible(false);
			}
		});
		this.setJMenuBar(jmb);
		this.setTitle("记事本");
		Image image = this.getToolkit().getImage("yan.gif");
		this.setIconImage(image);
		file.add(f_new);
		file.add(f_open);
		file.addSeparator();
		file.add(f_save);
		file.add(f_saveas);
		file.addSeparator();
		file.add(f_close);
		edit.add(e_copy);
		edit.add(e_paste);
		edit.add(e_cut);
		edit.add(e_clear);
		edit.addSeparator();
		edit.add(e_selectAll);
		style.add(s_autoLine);
		style.addSeparator();
		style.add(s_font);
		style.add(s_color);
		help.add(h_editor);
		help.add(h_help);
		jmb.add(file);
		jmb.add(edit);
		jmb.add(style);
		jmb.add(help);
		b_save = new JButton("保存");
		b_save.addActionListener(this);
		b_close = new JButton("关闭");
		b_close.addActionListener(this);
		jp.add(jj);
		jp.add(stateBar, "West");
		jp.add(b_save);
		jp.add(b_close);
		jp.add(jl);
		this.add(jp, "South");
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		this.add(jsp);
		this.setSize(800, 600);
		this.setVisible(true);
		// 设置窗口居中显示
		int W = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int H = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((W - this.getWidth()) / 2, (H - this.getHeight()) / 2);
		// 为窗口添加"关闭"事件的响应
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// 上面这句话非常重要，因为下面的处理方法会产生一个异常现象
		// 有了上面的语句就可以避免异常的发生
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				whenExit();
			}
		});
	} ///////////// 构造器到此结束///////////////////

	
	// 响应菜单项"自动换行"的事件
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == s_autoLine) {
			if (s_autoLine.getState()) {
				textArea.setLineWrap(true);
			} else
				textArea.setLineWrap(false);
		}
	}

	public boolean isCurrentFileSaved() { // 判断文件是否被修改
		if (stateBar.getText().equals("已保存") || stateBar.getText().equals("新文档")) {
			return true;
		} else {
			return false;
		}
	}

	public void openFileDialog() {
		int m = jfc.showOpenDialog(this);
		if (m == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			for (int i = 0; i <= f.length(); i++) {
				char[] ch = new char[i];
				try {
					@SuppressWarnings("resource")
					FileReader fr = new FileReader(f);
					fr.read(ch);
					String str = new String(ch);

					textArea.setText(str);

					isCurrentFileExists = true;
					tempFile = f;
				} catch (FileNotFoundException fe) {
					JOptionPane.showMessageDialog(null, "未找到需要打开的文件!");
				} catch (IOException ie) {
					System.err.println(ie);
				}
			}
		} else
			return;
	}

	public void saveFileDialog() // 保存文件对话框
	{
		int options = jfc.showSaveDialog(this);
		String fname = null;
		if (options == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();// 如果没有选取文件,下面的jfc.getName(f)将会返回输入的文件名
			fname = jfc.getName(f);
			if (fname != null && fname.trim().length() > 0) {
				if (fname.endsWith(".txt"))
					;
				else {
					fname = fname.concat(".txt");
				}
			}
			if (f.isFile())
				fname = f.getName();
			f = jfc.getCurrentDirectory();
			f = new File(f.getPath().concat(File.separator).concat(fname));
			if (f.exists()) {
				int i = JOptionPane.showConfirmDialog(null, "该文件已经存在，确定要覆盖吗？");
				if (i == JOptionPane.YES_OPTION)
					;
				else
					return;
			}
			try {
				f.createNewFile();
				String str = textArea.getText();
				FileWriter fw = new FileWriter(f);
				fw.write(str);
				fw.close();
				isCurrentFileExists = true;
				tempFile = f;
				stateBar.setText("已保存");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "出错：" + ex.getMessage());
				return;
			}
		}
	}

	public void cut() { // 剪切
		textArea.cut();
		stateBar.setText("已修改");
		popUpMenu.setVisible(false);
	}

	public void copy() { // 复制
		textArea.copy();
		popUpMenu.setVisible(false);
	}

	public void paste() { // 粘贴
		textArea.paste();
		stateBar.setText("已修改");
		popUpMenu.setVisible(false);
	}

	public void clear() { // 清除
		textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
		stateBar.setText("已修改");
		popUpMenu.setVisible(false);
	}

	public void selectAll() { // 全选
		textArea.selectAll();
		popUpMenu.setVisible(false);
	}

	public void whenExit() // 当退出程序时判断文档状态
	{
		if (isCurrentFileSaved()) // 关闭窗口时判断是否保存了文件
		{
			dispose();
			System.exit(0);
		} else {
			int i = JOptionPane.showConfirmDialog(null, "文件未保存，是否要保存？", "提示", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (i == JOptionPane.YES_OPTION)
				saveFileDialog();
			else if (i == JOptionPane.NO_OPTION) {
				dispose();
				System.exit(0);
			} else
				return;
		}
	}

	public void actionPerformed(ActionEvent e) {
		// 响应菜单项"新建"的事件
		if (e.getSource() == f_new) {
			if (isCurrentFileSaved()) {
				textArea.setText("");
				stateBar.setText("新文档");
			} else {
				int i = JOptionPane.showConfirmDialog(null, "文件未保存，是否要保存？", "提示", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (i == JOptionPane.YES_OPTION)
					saveFileDialog();
				else if (i == JOptionPane.NO_OPTION) {
					textArea.setText("");
					stateBar.setText("新文档");
				} else
					return;
			}
		}
		// 响应菜单项"打开"的事件
		if (e.getSource() == f_open) {
			if (isCurrentFileSaved()) {
				openFileDialog();
			} else {
				int i = JOptionPane.showConfirmDialog(null, "文件未保存，是否要保存？", "提示", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (i == JOptionPane.YES_OPTION)
					saveFileDialog();
				else if (i == JOptionPane.NO_OPTION) {
					openFileDialog();
				} else
					return;
			}
		}
		// 响应菜单项"保存"和"按钮"保存"的事件
		if ((e.getSource() == b_save) || (e.getSource() == f_save)) {
			if (isCurrentFileExists) {
				try {
					String str = textArea.getText();
					FileWriter fw = new FileWriter(tempFile);
					fw.write(str);
					fw.close();
					isCurrentFileExists = true;
					tempFile = tempFile;
					stateBar.setText("已保存");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "出错：" + ex.getMessage());
					return;
				}
			} else
				saveFileDialog();
		}
		// 响应菜单"另存为"的事件
		if (e.getSource() == f_saveas) {
			int option = jfc.showDialog(this, "另存为");
			String fname = null;
			if (option == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();// 如果没有选取文件,下面的jfc.getName(f)将会返回输入的文件名
				fname = jfc.getName(f);
				if (fname != null && fname.trim().length() > 0) {
					if (fname.endsWith(".txt"))
						;
					else {
						fname = fname.concat(".txt");
					}
				}
				if (f.isFile())
					fname = f.getName();
				f = jfc.getCurrentDirectory();
				f = new File(f.getPath().concat(File.separator).concat(fname));
				if (f.exists()) {
					int i = JOptionPane.showConfirmDialog(null, "该文件已经存在，确定要覆盖吗？");
					if (i == JOptionPane.YES_OPTION)
						;
					else
						return;
				}
				try {
					f.createNewFile();
					String str = textArea.getText();
					FileWriter fw = new FileWriter(f);
					fw.write(str);
					fw.close();
					stateBar.setText("已保存");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "出错：" + ex.getMessage());
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "文件没有保存!");
			}
		}
		// 响应"退出"菜单和"关闭"按钮的事件
		if ((e.getSource() == f_close) || (e.getSource() == b_close)) {
			whenExit();
		}
		if (e.getSource() == e_copy || e.getSource() == je_copy) {
			copy();
		}
		if (e.getSource() == e_paste || e.getSource() == je_paste) {
			paste();
		}
		if (e.getSource() == e_cut || e.getSource() == je_cut) {
			cut();
		}
		if (e.getSource() == e_clear || e.getSource() == je_clear) {
			clear();
		}
		if (e.getSource() == e_selectAll || e.getSource() == je_selectAll) {
			selectAll();
		}
		if (e.getSource() == s_font) // 创建字体选择对话框
		{
			Font font;
			font = new Font("新宋体", Font.PLAIN, 12);
			font = FontChooser.showDialog(this, null, font);
			if (font != null) // 判断是否选择了"确定"按钮
			{
				textArea.setFont(font);
			} else {
				textArea.setFont(textArea.getFont());
			}
		}
		if (e.getSource() == s_color) // 创建颜色选择对话框
		{
			Color c = JColorChooser.showDialog(this, "请选择文字颜色", Color.cyan);
			if (c != null) {
				textArea.setForeground(c);
			} else
				textArea.setForeground(textArea.getForeground());
		}
		if (e.getSource() == h_editor) {
			JOptionPane.showMessageDialog(this, "作者:  yager \n编程感想: 学习JAVA很快乐!\n温情提醒:  好好学习,天天向上! ", "关于记事本",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource() == h_help) // 打开windows的记事本帮助文件
		{
			try {
				String filePath = "C:/WINDOWS/Help/notepad.chm";
				Runtime.getRuntime().exec("cmd.exe /c " + filePath);
			} catch (Exception ee) {
				JOptionPane.showMessageDialog(this, "打开系统的记事本帮助文件出错!", "错误信息", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void main(String[] args) // 创建一个NotePad的匿名对象
	{
		new NotePad();
	}
}
