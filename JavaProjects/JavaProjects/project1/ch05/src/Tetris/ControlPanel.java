/**
 * File: ControlPanel.java
 * User: ������
 * Date: 2006.11.8
 * Describe: ����˹����� Java ʵ��
 */
package Tetris;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * ��������࣬�̳���JPanel.
 * �ϱ߰���Ԥ�Դ��ڡ��ȼ����÷֡����ư�ť
 * ��Ҫ����������Ϸ���̡�
 */
class ControlPanel extends JPanel {
	private JTextField
	        tfLevel = new JTextField("" + ErsBlocksGame.DEFAULT_LEVEL),
	        tfScore = new JTextField("0"),
	        tfScore1 = new JTextField("0"),
	        tfScore2 = new JTextField("0"),
	        tfScore3 = new JTextField("0"),
			tfScore4 = new JTextField("0");
	private JButton
	        btCplay = new JButton("������Ϸ"),
	        btHplay = new JButton("�˹���Ϸ"),
	        btPause = new JButton("��ͣ��Ϸ"),
	        btResume= new JButton("�ָ���Ϸ"),
	        btStop = new JButton("������Ϸ");

	private JPanel plTip = new JPanel(new BorderLayout());
	private TipPanel plTipBlock = new TipPanel();
	private JPanel plInfo = new JPanel(new GridLayout(4, 1));
	private JPanel plButton = new JPanel(new GridLayout(5, 1));

	private Timer timer;
	private ErsBlocksGame game;

	private Border border = new EtchedBorder(
	        EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));

	/**
	 * ���������Ĺ��캯��
	 * @param game ErsBlocksGame, ErsBoxesGame���һ��ʵ�����ã�
	 * ����ֱ�ӿ���ErsBoxesGame�����Ϊ��
	 */
	public ControlPanel(final ErsBlocksGame game) {
		setLayout(new GridLayout(3, 1, 3, 4));
		this.game = game;

		plTip.add(new JLabel("��һ����"), BorderLayout.NORTH);
		plTip.add(plTipBlock);
		plTip.setBorder(border);
		
		//plInfo.add(new JLabel("�Ѷ�"));
		//plInfo.add(tfLevel);
		//plInfo.add(new JLabel("��������"));
		//plInfo.add(tfScore);
		
		plInfo.add(new JLabel("  4��"));
		plInfo.add(tfScore4);
		plInfo.add(new JLabel("  3��"));
		plInfo.add(tfScore3);
		plInfo.add(new JLabel("  2��"));
		plInfo.add(tfScore2);
		plInfo.add(new JLabel("  1��"));
		plInfo.add(tfScore1);
		plInfo.setBorder(border);

		tfLevel.setEditable(false);
		tfScore.setEditable(false);
		tfScore1.setEditable(false);
		tfScore2.setEditable(false);
		tfScore3.setEditable(false);
		tfScore4.setEditable(false);

		plButton.add(btCplay);
		plButton.add(btHplay);
		plButton.add(btPause);
		plButton.add(btResume);
		plButton.add(btStop);
		plButton.setBorder(border);

		add(plTip);
		add(plInfo);
		add(plButton);

		addKeyListener(new ControlKeyListener());

		btCplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.computerPlayGame();
			}
		});
		btHplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.humanPlayGame();
			}
		});
		btPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.pauseGame();
			}
		});
		btResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.resumeGame();
			}
		});
		btStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.stopGame();
			}
		});

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				plTipBlock.fanning();
			}
		});

		timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				tfScore1.setText("" + game.getDelLine1());
				tfScore2.setText("" + game.getDelLine2());
				tfScore3.setText("" + game.getDelLine3());
				tfScore4.setText("" + game.getDelLine4());
				int scoreForLevelUpdate =
				        game.getScoreForLevelUpdate();
				if (scoreForLevelUpdate >= ErsBlocksGame.PER_LEVEL_SCORE
				        && scoreForLevelUpdate > 0)
					game.levelUpdate();
			}
		});
		timer.start();
	}

	/**
	 * ����Ԥ�Դ��ڵ���ʽ��
	 * @param style int,��ӦErsBlock���STYLES�е�28��ֵ
	 */
	public void setTipStyle(int style) {
		plTipBlock.setStyle(style);
	}

	/**
	 * ȡ���û����õ���Ϸ�ȼ���
	 * @return int, �Ѷȵȼ���1������ErsBlocksGame.MAX_LEVEL
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
	 * @param level �޸ĺ����Ϸ�Ѷȵȼ�
	 */
	public void setLevel(int level) {
		if (level > 0 && level < 11) tfLevel.setText("" + level);
	}

	/**
	 * ����"�˹���ʼ"��ť��״̬��
	 */
	public void setHumanPlayButtonEnabled(boolean enable) {
		btHplay.setEnabled(enable);
	}
	
	/**
	 * ����"��Ϸ��ʼ"��ť��״̬��
	 */
	public void setComputerPlayButtonEnabled(boolean enable) {
		btCplay.setEnabled(enable);
	}
	
	/**
	 * ����"��Ϸ��ͣ"��ť��״̬��
	 */
	public void setPauseButtonEnabled(boolean pause) {
		btPause.setEnabled(pause);
	}
	
	/**
	 * ����"��Ϸ�ָ�"��ť��״̬��
	 */
	public void setResumeButtonEnabled(boolean resume){
		btResume.setEnabled(resume);
	}

	/**
	 * ���ÿ������
	 */
	public void reset() {
		tfScore.setText("0");
		plTipBlock.setStyle(0);
	}

	/**
	 * ���¼���TipPanel���boxes[][]���С��Ĵ�С
	 */
	public void fanning() {
		plTipBlock.fanning();
	}

	/**
	 * Ԥ�Դ��ڵ�ʵ��ϸ����
	 */
	private class TipPanel extends JPanel {
		private Color backColor = Color.darkGray;
		private Color frontColor = Color.lightGray;
		private ErsBox[][] boxes =
		        new ErsBox[ErsBlock.BOXES_ROWS][ErsBlock.BOXES_COLS];

		private int style, boxWidth, boxHeight;
		private boolean isTiled = false;

		/**
		 * Ԥ�Դ����๹�캯��
		 */
		public TipPanel() {
			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++)
					boxes[i][j] = new ErsBox(false);
			}
		}

		/**
		 * Ԥ�Դ����๹�캯��
		 * @param backColor Color, ���ڵı���ɫ
		 * @param frontColor Color, ���ڵ�ǰ��ɫ
		 */
		public TipPanel(Color backColor, Color frontColor) {
			this();
			this.backColor = backColor;
			this.frontColor = frontColor;
		}

		/**
		 * ����Ԥ�Դ��ڵķ�����ʽ
		 * @param style int,��ӦErsBlock���STYLES�е�28��ֵ
		 */
		public void setStyle(int style) {
			this.style = style;
			repaint();
		}

		/**
		 * ����JComponent��ĺ������������
		 * @param g ͼ���豸����
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (!isTiled) fanning();

			int key = 0x8000;
			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++) {
					Color color = (((key & style) != 0) ? frontColor : backColor);
					g.setColor(color);
					g.fill3DRect(j * boxWidth, i * boxHeight,
					        boxWidth, boxHeight, true);
					key >>= 1;
				}
			}
		}

		/**
		 * ���ݴ��ڵĴ�С���Զ���������ĳߴ�
		 */
		public void fanning() {
			boxWidth = getSize().width / ErsBlock.BOXES_COLS;
			boxHeight = getSize().height / ErsBlock.BOXES_ROWS;
			isTiled = true;
		}
	}

	/**
	 * ���̼����࣬�̳���KeyAdapter
	 */
	private class ControlKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent ke) {
			if (!game.isPlaying() || game.autoPlay) return;

			ErsBlock block = game.getCurBlock();
			switch (ke.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					block.moveDown();
					break;
				case KeyEvent.VK_LEFT:
					block.moveLeft();
					break;
				case KeyEvent.VK_RIGHT:
					block.moveRight();
					break;
				case KeyEvent.VK_UP:
					block.turnNext();
					break;
				default:
					break;
			}
		}
	}
}
