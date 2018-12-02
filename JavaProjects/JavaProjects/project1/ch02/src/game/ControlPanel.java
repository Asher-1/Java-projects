/**
 * File: ControlPanel.java
 * User: Administrator
 * Date: Dec 15, 2004
 * Describe: ����˹������Ϸ�Ŀ��Ʋ��ֵ�ʵ��
 */
package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * ��������࣬�̳�JPanel. �ϱ߰���Ԥ�Լ������ֵķ������͵Ĵ��ڡ��Ѷȼ��𡢵÷֡����ư�ť ��Ҫ����������Ϸ���̡�
 */
class ControlPanel extends JPanel {
	// ��һ���ı�����ʾ�Ѷȼ���
	private JTextField tfLevel = new JTextField(""
			+ ErsBlocksGame.DEFAULT_LEVEL),
	// ��һ���ı�����ʾ��ҵ÷�
			tfScore = new JTextField("0");

	// ����һ����ư�ť
	private JButton btPlay = new JButton("��ʼ"), btPause = new JButton("");

	
	/**
	 * ���켸�����,�ֱ�ڷŲ�ͬ���͵Ĺ���
	 */

	// ��ʾ��һ����Ϸ������
	private JPanel plTip = new JPanel(new BorderLayout());

	private TipPanel plTipBlock = new TipPanel();

	// ��ʾ�Է������Ϸ�����
	private JPanel useInfo = new JPanel(new BorderLayout());

	private ShowPanel infoBlock = new ShowPanel();

	// ��ʾ��Ϸ��ǰ��Ϣ����壬4��1��
	private JPanel plInfo = new JPanel(new GridLayout(4, 1));

	private Timer timer;

	// ��ǰ����Ϸ��
	private ErsBlocksGame game;

	// ����ͻ����EtchedBorder���͵ı߿�
	private Border border = new EtchedBorder(EtchedBorder.RAISED, Color.white,
			new Color(148, 145, 140));

	/**
	 * �����������Ĺ��캯��
	 * 
	 * @param game
	 *            ErsBlocksGame, ErsBoxesGame���һ��ʵ�����ã� ����ֱ�ӿ���ErsBoxesGame�����Ϊ��
	 */
	public ControlPanel(final ErsBlocksGame game) {
		// �������������3������壬�ڷ���1�У�
		// ÿ�еļ��Ϊ4
		setLayout(new GridLayout(3, 1, 0, 4));
		this.game = game;

		// Ԥ��ʾ���������������߽�
		plTip.add(new JLabel("  ��һ������"), BorderLayout.NORTH);
		plTip.add(plTipBlock);
		plTip.setBorder(border);

		// ��ʾ�Է�������������������߽�
		useInfo.add(new JLabel("   �Է����  "), BorderLayout.NORTH);
		useInfo.add(infoBlock);
		// useInfo.setBorder(border);

		// ��Ϸ��Ϣ��ʾ����������ǩ�������ı��򼰱߽�
		plInfo.add(btPlay);
		plInfo.add(btPause);
		plInfo.add(new JLabel("     �÷�      "));
		plInfo.add(tfScore);
		plInfo.setBorder(border);

		// �����ı����ǲ��ɱ༭�ģ�ֻ������ʾ��Ϣ

		tfScore.setEditable(false);

		// ��3���ϳ������뵽�������
		add(plTip);
		add(plInfo);
		add(useInfo);

		// ���Ӽ��̵ļ�����
		addKeyListener(new ControlKeyListener());

		// ���Ӱ�ť�ļ�����
		btPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.getMGame().setEnabled(false);
				game.getMControl().setEnabled(false);
				game.getMInfo().setEnabled(false);
				game.playGame();
			}
		});
		/**
		 * Timer��Ķ��������һ����ʱ��������һ�������¼� ����һ����ʱ��Ҫ����һ��Timer����ע��һ����������������
		 * ����start����������ʱ�� �˴���ʱ����Ϊ500���룬��ʾ��ҵĵ÷֣��Ѷȼ���
		 */
		timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// ��ʾ��ҵĵ÷�
				tfScore.setText("" + game.getScore());
				// ���÷ֽ����Ѷȼ���
				int scoreForLevelUpdate = game.getScoreForLevelUpdate();
				// ��ʾ���º���Ѷȼ���
				if (scoreForLevelUpdate >= ErsBlocksGame.PER_LEVEL_SCORE
						&& scoreForLevelUpdate > 0)
					game.levelUpdate();
			}
		});
		timer.start();// ������ʱ��
	}

	/**
	 * ����Ԥ�Դ��ڵ���ʽ��
	 * 
	 * @param style
	 *            int,��ӦErsBlock���STYLES�е�28��ֵ
	 */
	public void setTipStyle(int style) {
		plTipBlock.setStyle(style);
	}

	public void setUseInfo(int[][] date) {
		infoBlock.setInfo(date);
	}

	/**
	 * ȡ���û����õ���Ϸ�ȼ���
	 * 
	 * @return int, �Ѷȵȼ���1 �� ErsBlocksGame.MAX_LEVEL
	 */
	public int getLevel() {
		int level = 0;
		try {
			level = Integer.parseInt(tfLevel.getText());
		} catch (NumberFormatException e) {
		}
		return level;
	}

	/**
	 * ���û��޸���Ϸ�Ѷȵȼ���
	 * 
	 * @param level
	 *            �޸ĺ����Ϸ�Ѷȵȼ�
	 */
	public void setLevel(int level) {
		if (level > 0 && level < 11)
			tfLevel.setText("" + level);
	}

	/**
	 * ����"��ʼ"��ť��״̬��
	 */
	public void setPlayButtonEnable(boolean enable) {
		btPlay.setEnabled(enable);
	}

	/**
	 * ���ÿ�����壬�÷���Ϊ0
	 */
	public void reset() {
		tfScore.setText("0");
		plTipBlock.setStyle(0);
		infoBlock.resetInfo(0);

	}

	/**
	 * ���¼���TipPanel���boxes[][]��ķ���Ĵ�С
	 */
	public void fanning() {
		plTipBlock.fanning();
	}

	/**
	 * ��ʾ��Ϣ����ʵ��ϸ����
	 */
	private class TipPanel extends JPanel {

		// ��ʾ��Ϣ����ǰ���ͱ�����ɫ
		private Color backColor = Color.darkGray, frontColor = Color.lightGray;

		// �����趨�������������Ķ���˹����
		private ErsBox[][] boxes = new ErsBox[ErsBlock.BOXES_ROWS][ErsBlock.BOXES_COLS];

		// �����̬�����Ⱥ͸߶�
		private int style, boxWidth, boxHeight;

		// isTiled�Ƿ�ƽ��
		private boolean isTiled = false;

		/**
		 * Ԥ��ʾ�����๹�캯��
		 */
		public TipPanel() {
			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++)
					boxes[i][j] = new ErsBox(false);
			}
		}

		/**
		 * Ԥ��ʾ�����๹�캯��
		 * 
		 * @param backColor
		 *            Color, ���ڵı���ɫ
		 * @param frontColor
		 *            Color, ���ڵ�ǰ��ɫ
		 */
		public TipPanel(Color backColor, Color frontColor) {
			this();
			this.backColor = backColor;
			this.frontColor = frontColor;
		}

		/**
		 * ����Ԥ�Դ��ڵķ�����ʽ
		 * 
		 * @param style
		 *            int,��ӦErsBlock���STYLES�е�28��ֵ
		 */
		public void setStyle(int style) {
			this.style = style;
			repaint();
		}

		/**
		 * ����JComponent��ĺ������������
		 * 
		 * @param g
		 *            ͼ���豸����
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (!isTiled)
				fanning();

			int key = 0x8000;
			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++) {
					Color color = (((key & style) != 0) ? frontColor
							: backColor);
					g.setColor(color);
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth,
							boxHeight, true);
					key >>= 1;
				}
			}
		}

		/**
		 * ���ݴ��ڵĴ�С���Զ���������Ĵ�С
		 */
		public void fanning() {
			boxWidth = getSize().width / ErsBlock.BOXES_COLS;
			boxHeight = getSize().height / ErsBlock.BOXES_ROWS;
			isTiled = true;
		}
	}

	private class ShowPanel extends JPanel {

		// ��ʾ��Ϣ����ǰ���ͱ�����ɫ
		private Color backColor = Color.darkGray, frontColor = Color.lightGray;

		// private int rows ,clos;

		// �����趨�������������Ķ���˹����
		private ErsBox[][] boxes = new ErsBox[GameCanvas.CANVAS_ROWS][GameCanvas.CANVAS_COLS];

		// �����̬�����Ⱥ͸߶�
		private int boxWidth, boxHeight;

		// isTiled�Ƿ�ƽ��
		private boolean isTiled = false;

		private int[][] date = new int[GameCanvas.CANVAS_ROWS][GameCanvas.CANVAS_COLS];

		/**
		 * Ԥ��ʾ�����๹�캯��
		 */
		public ShowPanel() {

			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++)
					boxes[i][j] = new ErsBox(false);
			}
		}

		/**
		 * Ԥ��ʾ�����๹�캯��
		 * 
		 * @param backColor
		 *            Color, ���ڵı���ɫ
		 * @param frontColor
		 *            Color, ���ڵ�ǰ��ɫ
		 */
		public ShowPanel(Color backColor, Color frontColor) {
			this();
			this.backColor = backColor;
			this.frontColor = frontColor;
		}

		/**
		 * ����Ԥ�Դ��ڵķ�����ʽ
		 * 
		 * @param style
		 *            int,��ӦErsBlock���STYLES�е�28��ֵ
		 */
		/*
		 * public void setStyle(int style) { this.style = style; repaint(); }
		 */
		public void setInfo(int[][] date) {
			this.date = date;
			repaint();
		}

		public void resetInfo(int style) {
			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++) {
					date[i][j] = style;
				}
			}
			repaint();
		}

		/**
		 * ����JComponent��ĺ������������
		 * 
		 * @param g
		 *            ͼ���豸����
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (!isTiled)
				fanning();

			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++) {
					Color color = ((date[i][j] == 1) ? frontColor : backColor);
					g.setColor(color);
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth,
							boxHeight, true);
				}
			}
		}

		/**
		 * ���ݴ��ڵĴ�С���Զ���������Ĵ�С
		 */
		public void fanning() {
			boxWidth = getSize().width / GameCanvas.CANVAS_COLS;
			boxHeight = getSize().height / GameCanvas.CANVAS_ROWS;
			isTiled = true;
		}

	}

	// ��Ϸ�İ�ť���Ƽ��ļ�����
	private class ControlKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent ke) {
			if (!game.isPlaying())
				return;

			ErsBlock block = game.getCurBlock();
			if (ke.getKeyCode() == game.getUp()) {
				block.turnNext();
				return;
			}
			if (ke.getKeyCode() == game.getDown()) {
				block.moveDown();
				return;
			}
			if (ke.getKeyCode() == game.getLeft()) {
				block.moveLeft();
				return;
			}
			if (ke.getKeyCode() == game.getRight()) {
				block.moveRight();
				return;
			}
		}
	}
}