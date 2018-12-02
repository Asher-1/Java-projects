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
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("�ı��ļ�(*.txt)", "txt"));
		jp = new JPanel();
		jl = new JLabel("JAVA���±�V1.0");
		jj = new JLabel("״̬��:");
		jmb = new JMenuBar();
		textArea = new JTextArea();
		jsp = new JScrollPane(textArea);
		file = new JMenu("�ļ�");
		edit = new JMenu("�༭");
		style = new JMenu("��ʽ");
		help = new JMenu("����");
		je_copy = new JMenuItem("����");
		je_paste = new JMenuItem("ճ��");
		je_cut = new JMenuItem("����");
		je_clear = new JMenuItem("���");
		je_selectAll = new JMenuItem("ȫѡ");
		je_copy.addActionListener(this);
		je_paste.addActionListener(this);
		je_cut.addActionListener(this);
		je_clear.addActionListener(this);
		je_selectAll.addActionListener(this);
		f_new = new JMenuItem("�½�(N)");
		f_new.setMnemonic('N'); // ���������η���ݼ�
		f_new.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK, false)); // ���ô����η���ݼ�
		f_new.addActionListener(this);
		f_open = new JMenuItem("��(O)");
		f_open.setMnemonic('O');
		f_open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK, false));
		f_open.addActionListener(this);
		f_save = new JMenuItem("����(S)");
		f_save.setMnemonic('S');
		f_save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK, false));
		f_save.addActionListener(this);
		f_saveas = new JMenuItem("���Ϊ");
		f_saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		f_saveas.addActionListener(this);
		f_close = new JMenuItem("�ر�(W)");
		f_close.setMnemonic('W');
		f_close.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_MASK, false));
		f_close.addActionListener(this);
		e_copy = new JMenuItem("����(C)");
		e_copy.setMnemonic('C');
		e_copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK, false));
		e_copy.addActionListener(this);
		e_paste = new JMenuItem("ճ��(V)");
		e_paste.setMnemonic('V');
		e_paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK, false));
		e_paste.addActionListener(this);
		e_cut = new JMenuItem("����(X)");
		e_cut.setMnemonic('X');
		e_cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK, false));
		e_cut.addActionListener(this);
		e_clear = new JMenuItem("���(D)");
		e_clear.setMnemonic('D');
		e_clear.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK, false));
		e_clear.addActionListener(this);
		e_selectAll = new JMenuItem("ȫѡ(A)");
		e_selectAll.setMnemonic('A');
		e_selectAll.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK, false));
		e_selectAll.addActionListener(this);
		s_font = new JMenuItem("����(T)");
		s_font.setMnemonic('T');
		s_font.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK, false));
		s_font.addActionListener(this);
		s_color = new JMenuItem("��ɫ(C)...");
		s_color.setMnemonic('C');
		s_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		s_color.addActionListener(this);
		s_autoLine = new JCheckBoxMenuItem("�Զ�����", true);
		s_autoLine.addItemListener(this);
		h_editor = new JMenuItem("���ڼ��±�");
		h_editor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		h_editor.addActionListener(this);
		h_help = new JMenuItem("������Ϣ(H)");
		h_help.setMnemonic('H');
		h_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		h_help.addActionListener(this);
		stateBar = new JLabel("���ĵ�");
		stateBar.setHorizontalAlignment(SwingConstants.LEFT);
		stateBar.setBorder(BorderFactory.createEtchedBorder());
		// ����Ҽ�����ʽ�˵�
		// popUpMenu = edit.getPopupMenu();
		popUpMenu.add(je_copy);
		popUpMenu.add(je_paste);
		popUpMenu.add(je_cut);
		popUpMenu.add(je_clear);
		popUpMenu.addSeparator();
		popUpMenu.add(je_selectAll);

		// �༭�������¼�
		textArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				stateBar.setText("���޸�");
			}
		});
		// �༭������¼�,����Ҽ�����"�༭"�˵�
		textArea.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					popUpMenu.show(e.getComponent(), e.getX(), e.getY());
			} // e.getComponent()��textArea����ͬ��Ч��

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					popUpMenu.setVisible(false);
			}
		});
		this.setJMenuBar(jmb);
		this.setTitle("���±�");
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
		b_save = new JButton("����");
		b_save.addActionListener(this);
		b_close = new JButton("�ر�");
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
		// ���ô��ھ�����ʾ
		int W = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int H = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((W - this.getWidth()) / 2, (H - this.getHeight()) / 2);
		// Ϊ�������"�ر�"�¼�����Ӧ
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// ������仰�ǳ���Ҫ����Ϊ����Ĵ����������һ���쳣����
		// ������������Ϳ��Ա����쳣�ķ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				whenExit();
			}
		});
	} ///////////// ���������˽���///////////////////

	
	// ��Ӧ�˵���"�Զ�����"���¼�
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == s_autoLine) {
			if (s_autoLine.getState()) {
				textArea.setLineWrap(true);
			} else
				textArea.setLineWrap(false);
		}
	}

	public boolean isCurrentFileSaved() { // �ж��ļ��Ƿ��޸�
		if (stateBar.getText().equals("�ѱ���") || stateBar.getText().equals("���ĵ�")) {
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
					JOptionPane.showMessageDialog(null, "δ�ҵ���Ҫ�򿪵��ļ�!");
				} catch (IOException ie) {
					System.err.println(ie);
				}
			}
		} else
			return;
	}

	public void saveFileDialog() // �����ļ��Ի���
	{
		int options = jfc.showSaveDialog(this);
		String fname = null;
		if (options == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();// ���û��ѡȡ�ļ�,�����jfc.getName(f)���᷵��������ļ���
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
				int i = JOptionPane.showConfirmDialog(null, "���ļ��Ѿ����ڣ�ȷ��Ҫ������");
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
				stateBar.setText("�ѱ���");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "����" + ex.getMessage());
				return;
			}
		}
	}

	public void cut() { // ����
		textArea.cut();
		stateBar.setText("���޸�");
		popUpMenu.setVisible(false);
	}

	public void copy() { // ����
		textArea.copy();
		popUpMenu.setVisible(false);
	}

	public void paste() { // ճ��
		textArea.paste();
		stateBar.setText("���޸�");
		popUpMenu.setVisible(false);
	}

	public void clear() { // ���
		textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
		stateBar.setText("���޸�");
		popUpMenu.setVisible(false);
	}

	public void selectAll() { // ȫѡ
		textArea.selectAll();
		popUpMenu.setVisible(false);
	}

	public void whenExit() // ���˳�����ʱ�ж��ĵ�״̬
	{
		if (isCurrentFileSaved()) // �رմ���ʱ�ж��Ƿ񱣴����ļ�
		{
			dispose();
			System.exit(0);
		} else {
			int i = JOptionPane.showConfirmDialog(null, "�ļ�δ���棬�Ƿ�Ҫ���棿", "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION,
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
		// ��Ӧ�˵���"�½�"���¼�
		if (e.getSource() == f_new) {
			if (isCurrentFileSaved()) {
				textArea.setText("");
				stateBar.setText("���ĵ�");
			} else {
				int i = JOptionPane.showConfirmDialog(null, "�ļ�δ���棬�Ƿ�Ҫ���棿", "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (i == JOptionPane.YES_OPTION)
					saveFileDialog();
				else if (i == JOptionPane.NO_OPTION) {
					textArea.setText("");
					stateBar.setText("���ĵ�");
				} else
					return;
			}
		}
		// ��Ӧ�˵���"��"���¼�
		if (e.getSource() == f_open) {
			if (isCurrentFileSaved()) {
				openFileDialog();
			} else {
				int i = JOptionPane.showConfirmDialog(null, "�ļ�δ���棬�Ƿ�Ҫ���棿", "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (i == JOptionPane.YES_OPTION)
					saveFileDialog();
				else if (i == JOptionPane.NO_OPTION) {
					openFileDialog();
				} else
					return;
			}
		}
		// ��Ӧ�˵���"����"��"��ť"����"���¼�
		if ((e.getSource() == b_save) || (e.getSource() == f_save)) {
			if (isCurrentFileExists) {
				try {
					String str = textArea.getText();
					FileWriter fw = new FileWriter(tempFile);
					fw.write(str);
					fw.close();
					isCurrentFileExists = true;
					tempFile = tempFile;
					stateBar.setText("�ѱ���");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "����" + ex.getMessage());
					return;
				}
			} else
				saveFileDialog();
		}
		// ��Ӧ�˵�"���Ϊ"���¼�
		if (e.getSource() == f_saveas) {
			int option = jfc.showDialog(this, "���Ϊ");
			String fname = null;
			if (option == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();// ���û��ѡȡ�ļ�,�����jfc.getName(f)���᷵��������ļ���
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
					int i = JOptionPane.showConfirmDialog(null, "���ļ��Ѿ����ڣ�ȷ��Ҫ������");
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
					stateBar.setText("�ѱ���");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "����" + ex.getMessage());
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "�ļ�û�б���!");
			}
		}
		// ��Ӧ"�˳�"�˵���"�ر�"��ť���¼�
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
		if (e.getSource() == s_font) // ��������ѡ��Ի���
		{
			Font font;
			font = new Font("������", Font.PLAIN, 12);
			font = FontChooser.showDialog(this, null, font);
			if (font != null) // �ж��Ƿ�ѡ����"ȷ��"��ť
			{
				textArea.setFont(font);
			} else {
				textArea.setFont(textArea.getFont());
			}
		}
		if (e.getSource() == s_color) // ������ɫѡ��Ի���
		{
			Color c = JColorChooser.showDialog(this, "��ѡ��������ɫ", Color.cyan);
			if (c != null) {
				textArea.setForeground(c);
			} else
				textArea.setForeground(textArea.getForeground());
		}
		if (e.getSource() == h_editor) {
			JOptionPane.showMessageDialog(this, "����:  yager \n��̸���: ѧϰJAVA�ܿ���!\n��������:  �ú�ѧϰ,��������! ", "���ڼ��±�",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource() == h_help) // ��windows�ļ��±������ļ�
		{
			try {
				String filePath = "C:/WINDOWS/Help/notepad.chm";
				Runtime.getRuntime().exec("cmd.exe /c " + filePath);
			} catch (Exception ee) {
				JOptionPane.showMessageDialog(this, "��ϵͳ�ļ��±������ļ�����!", "������Ϣ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void main(String[] args) // ����һ��NotePad����������
	{
		new NotePad();
	}
}
