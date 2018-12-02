/**
 * File: ErsBlocksGame.java
 * User: Administrator
 * Date: Dec 15, 2003
 * Describe: ����˹����� Java ʵ��
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ��Ϸ���࣬�̳�JFrame�࣬������Ϸ��ȫ�ֿ��ơ�
 * �ں�
 * 1, һ��GameCanvas�������ʵ�����ã�
 * 2, һ�����浱ǰ���(ErsBlock)ʵ�������ã�
 * 3, һ�����浱ǰ������壨ControlPanel��ʵ��������;
 */
public class ErsBlocksGame extends JFrame {
	/**
	 *  ÿ����һ�мƶ��ٷ�
	 */
	public final static int PER_LINE_SCORE = 100;
	/**
	 * �����ٷ��Ժ�������
	 */
	public final static int PER_LEVEL_SCORE = PER_LINE_SCORE * 20;
	/**
	 * �������10��
	 */
	public final static int MAX_LEVEL = 10;
	/**
	 * Ĭ�ϼ�����5
	 */
	public final static int DEFAULT_LEVEL = 5;


   //һ��GameCanvas�������ʵ�����ã�
 
 
	private GameCanvas canvas;
   // һ�����浱ǰ���(ErsBlock)ʵ�������ã�
	private ErsBlock block;
	
    // һ�����浱ǰ������壨ControlPanel��ʵ��������;
	private ControlPanel ctrlPanel;
    
	private boolean playing = false;
	
	private JMenuBar bar = new JMenuBar();
	//�˵�������4���˵�
	private JMenu
	        mGame = new JMenu("��Ϸ"),
			mControl = new JMenu("����"),
			mWindowStyle = new JMenu("���ڷ��"),
			mInfo = new JMenu("����");

	//4���˵��зֱ�����Ĳ˵���
	private JMenuItem
	        miNewGame = new JMenuItem("����Ϸ"),
			miSetBlockColor = new JMenuItem("���÷�����ɫ"),
			miSetBackColor = new JMenuItem("���ñ�����ɫ"),
			miTurnHarder = new JMenuItem("�����Ѷ�"),
			miTurnEasier = new JMenuItem("�����Ѷ�"),
			miExit = new JMenuItem("�˳�"),

			miPlay = new JMenuItem("��ʼ"),
			miPause = new JMenuItem("��ͣ"),
			miResume = new JMenuItem("����"),
			miStop = new JMenuItem("ֹͣ"),

			miAuthor = new JMenuItem("���� : Java��Ϸ�����"),
			miSourceInfo = new JMenuItem("�汾��1.0");

         //���ô��ڷ��Ĳ˵�

        	private JCheckBoxMenuItem
	        miAsWindows = new JCheckBoxMenuItem("Windows"),
			miAsMotif = new JCheckBoxMenuItem("Motif"),
			miAsMetal = new JCheckBoxMenuItem("Metal", true);

	/**
	 * ����Ϸ��Ĺ��캯��
	 * @param title String�����ڱ���
	 */
	public ErsBlocksGame(String title) {
		super(title);

	//��ʼ���ڵĴ�С���û��ɵ���
		setSize(315, 392);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
    
	 //����Ϸ����������Ļ����
		setLocation((scrSize.width - getSize().width) / 2,
		        (scrSize.height - getSize().height) / 2);
      
	//�����˵�
		createMenu();

		Container container = getContentPane();
	
	// ���ֵ�ˮƽ����֮����6�����صľ���
		container.setLayout(new BorderLayout(6, 0));
    
	 // ����20������ߣ�12����������Ϸ����
		canvas = new GameCanvas(20, 12);
	 
	 //����һ���������
		ctrlPanel = new ControlPanel(this);

	 //��Ϸ�����Ϳ������֮�����Ұڷ�
		container.add(canvas, BorderLayout.CENTER);
		container.add(ctrlPanel, BorderLayout.EAST);
    
	//���Ӵ��ڼ�����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				stopGame();
				System.exit(0);
			}
		});

    //���ӹ�������������һ�������ı��С���͵���
	//fanning()�������Զ���������ĳߴ�
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				canvas.fanning();
			}
		});

		show();    //setVisiable
		
		// ���ݴ��ڵĴ�С���Զ���������ĳߴ�
		canvas.fanning();
		
	}

	/**
	 * ����Ϸ����λ��
	 */
	public void reset() {
		ctrlPanel.reset();     //���ƴ��ڸ�λ
		canvas.reset();        //��Ϸ���帴λ

	}

	/**
	 * �ж���Ϸ�Ƿ��ڽ���
	 * @return boolean, true-�������У�false-�Ѿ�ֹͣ
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * �õ���ǰ��Ŀ�
	 * @return ErsBlock, ��ǰ��������
	 */
	public ErsBlock getCurBlock() {
		return block;
	}

	/**
	 * �õ���ǰ����
	 * @return GameCanvas, ��ǰ����������
	 */
	public GameCanvas getCanvas() {
		return canvas;
	}

	/**
	 * ��ʼ��Ϸ
	 */
	public void playGame() {
		play();
		ctrlPanel.setPlayButtonEnable(false);
		miPlay.setEnabled(false);
		ctrlPanel.requestFocus();
	}

	/**
	 * ��Ϸ��ͣ
	 */
	public void pauseGame() {
		if (block != null) block.pauseMove();

		ctrlPanel.setPauseButtonLabel(false);
		miPause.setEnabled(false);
		miResume.setEnabled(true);
	}

	/**
	 * ����ͣ�е���Ϸ����
	 */
	public void resumeGame() {
		if (block != null) block.resumeMove();
		ctrlPanel.setPauseButtonLabel(true);
		miPause.setEnabled(true);
		miResume.setEnabled(false);
		ctrlPanel.requestFocus();
	}

	/**
	 * �û�ֹͣ��Ϸ
	 */
	public void stopGame() {
		playing = false;
		if (block != null) block.stopMove();
		miPlay.setEnabled(true);
		miPause.setEnabled(true);
		miResume.setEnabled(false);
		ctrlPanel.setPlayButtonEnable(true);
		ctrlPanel.setPauseButtonLabel(true);
	}

	/**
	 * �õ���ǰ��Ϸ�����õ���Ϸ�Ѷ�
	 * @return int, ��Ϸ�Ѷ�1��MAX_LEVEL��10
	 */
	public int getLevel() {
		return ctrlPanel.getLevel();
	}

	/**
	 * ���û�������Ϸ�Ѷ�ϵ��
	 * @param level int, ��Ϸ�Ѷ�ϵ��Ϊ1��MAX_LEVEL��10
	 */
	public void setLevel(int level) {
		if (level < 11 && level > 0) ctrlPanel.setLevel(level);
	}

	/**
	 * �õ���Ϸ����
	 * @return int, ���֡�
	 */
	public int getScore() {
		if (canvas != null) return canvas.getScore();
		return 0;
	}

	/**
	 * �õ����ϴ�������������Ϸ���֣������Ժ󣬴˻�������
	 * @return int, ���֡�
	 */
	public int getScoreForLevelUpdate() {
		if (canvas != null) return canvas.getScoreForLevelUpdate();
		return 0;
	}

	/**
	 * �������ۼƵ�һ��������ʱ����һ�μ�
	 * @return boolean, ture-���³ɹ�, false-����ʧ��
	 */
	public boolean levelUpdate() {
		int curLevel = getLevel();
		if (curLevel < MAX_LEVEL) {
			setLevel(curLevel + 1);
			canvas.resetScoreForLevelUpdate();
			return true;
		}
		return false;
	}

	/**
	 * ��Ϸ��ʼ
	 */
	private void play() {
		reset();
		playing = true;
		Thread thread = new Thread(new Game());
		thread.start();
	}

	/**
	 * ������Ϸ������
	 */
	private void reportGameOver() {
		JOptionPane.showMessageDialog(this, "��Ϸ����!");
	}

	/**
	 * ���������ô��ڲ˵�
	 */
	private void createMenu() {
		bar.add(mGame);
		bar.add(mControl);
		bar.add(mWindowStyle);
		bar.add(mInfo);

		mGame.add(miNewGame);
		mGame.addSeparator();
		mGame.add(miSetBlockColor);
		mGame.add(miSetBackColor);
		mGame.addSeparator();
		mGame.add(miTurnHarder);
		mGame.add(miTurnEasier);
		mGame.addSeparator();
		mGame.add(miExit);

		mControl.add(miPlay);
		mControl.add(miPause);
		mControl.add(miResume);
		mControl.add(miStop);

		mWindowStyle.add(miAsWindows);
		mWindowStyle.add(miAsMotif);
		mWindowStyle.add(miAsMetal);

		mInfo.add(miAuthor);
		mInfo.add(miSourceInfo);

		setJMenuBar(bar);

		miPause.setAccelerator(
		        KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		miResume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

		miNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				stopGame();
				reset();
				setLevel(DEFAULT_LEVEL);
			}
		});
        //JColorChooser���ṩһ����׼��Gui���������û�ѡ��ɫ��
		//ʹ��JColorChooser�ķ���ѡ�񷽿����ɫ
		miSetBlockColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Color newFrontColor =
				        JColorChooser.showDialog(ErsBlocksGame.this,
				                "���÷�����ɫ", canvas.getBlockColor());
				if (newFrontColor != null)
					canvas.setBlockColor(newFrontColor);
			}
		}); 
	
	    //ʹ��JColorChooser�ķ���ѡ����Ϸ���ı�����ɫ
		miSetBackColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Color newBackColor =
				        JColorChooser.showDialog(ErsBlocksGame.this,
				                "���ñ�����ɫ", canvas.getBackgroundColor());
				if (newBackColor != null)
					canvas.setBackgroundColor(newBackColor);
			}
		});

		//ʹ��Ϸ���Ѷȼ������ӵĲ˵���
		miTurnHarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int curLevel = getLevel();
				if (curLevel < MAX_LEVEL) setLevel(curLevel + 1);
			}
		});
		//ʹ��Ϸ���Ѷȼ��𽵵͵Ĳ˵���
		miTurnEasier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int curLevel = getLevel();
				if (curLevel > 1) setLevel(curLevel - 1);
			}
		});
        //�˳���Ϸ�Ĳ˵���
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
        //��ʼ��Ϸ�Ĳ˵���
		miPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				playGame();
			}
		});
		
		//��ͣ��Ϸ�Ĳ˵���
		miPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				pauseGame();
			}
		});
		
		//�ָ���Ϸ�Ĳ˵���
		miResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				resumeGame();
			}
		});
		//ֹͣ��Ϸ�Ĳ˵���
		miStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				stopGame();
			}
		});
        
		//����������Ϸ�Ĵ��ڷ��������˵���
		miAsWindows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String plaf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
				setWindowStyle(plaf);
				canvas.fanning();
				ctrlPanel.fanning();
				miAsWindows.setState(true);
				miAsMetal.setState(false);
				miAsMotif.setState(false);
			}
		});
		miAsMotif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String plaf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
				setWindowStyle(plaf);
				canvas.fanning();
				ctrlPanel.fanning();
				miAsWindows.setState(false);
				miAsMetal.setState(false);
				miAsMotif.setState(true);
			}
		});
		miAsMetal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String plaf = "javax.swing.plaf.metal.MetalLookAndFeel";
				setWindowStyle(plaf);
				canvas.fanning();
				ctrlPanel.fanning();
				miAsWindows.setState(false);
				miAsMetal.setState(true);
				miAsMotif.setState(false);
			}
		});
	}

	/**
	 * �����ִ����ô������
	 * @param plaf String, ������۵�����
	 */
	private void setWindowStyle(String plaf) {
		try {
          //�趨�û���������
			UIManager.setLookAndFeel(plaf);
   
		 //���û�����ĳɵ�ǰ�趨�����
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
	}

	/**
	 * һ����Ϸ���̣�ʵ����Runnable�ӿ�
	 * һ����Ϸ��һ����ѭ���������ѭ���У�ÿ��100���룬
	 * �����Ϸ�еĵ�ǰ���Ƿ��Ѿ������ˣ����û�У�
	 * �ͼ����ȴ�����������ˣ��Ϳ���û��ȫ�������У�
	 * ����о�ɾ��������Ϊ��Ϸ�߼ӷ֣�ͬʱ�������һ��
	 * �µĵ�ǰ�飬����������������ٶȣ��Զ����䡣
	 * ���²���һ����ʱ���ȼ�黭����˵�һ���Ƿ��Ѿ�
	 * ��ռ�ˣ�����ǣ�������ʾ����Ϸ�������ˡ�
	 */
	private class Game implements Runnable {
		public void run() {
			//������ɿ�ĳ�ʼ�е�λ��
			//������ɿ�ĳ�ʼ��̬��28��֮һ��
			int col = (int) (Math.random() * (canvas.getCols() - 3)),
			    style = ErsBlock.STYLES[(int) (Math.random() * 7)][(int) (Math.random() * 4)];

			while (playing) {
				if (block != null) {    //��һ��ѭ��ʱ��blockΪ��
					if (block.isAlive()) {
						try {
							Thread.currentThread().sleep(100);
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
						continue;
					}
				}

				checkFullLine();        //����Ƿ���ȫ��������

				if (isGameOver()) {     //�����Ϸ�Ƿ�Ӧ�ý�����
					miPlay.setEnabled(true);
					miPause.setEnabled(true);
					miResume.setEnabled(false);
					ctrlPanel.setPlayButtonEnable(true);
					ctrlPanel.setPauseButtonLabel(true);

					reportGameOver();
					return;
				}
                //����һ����Ϸ��
				block = new ErsBlock(style, -1, col, getLevel(), canvas);

				//��Ϊ�߳̿�ʼ����
				block.start();
	
			//���������һ����ĳ�ʼ�е�λ��
			//���������һ����ĳ�ʼ��̬��28��֮һ��
				col = (int) (Math.random() * (canvas.getCols() - 3));
				style = ErsBlock.STYLES[(int) (Math.random() * 7)][(int) (Math.random() * 4)];

			//�ڿ����������ʾ��һ�������״
				ctrlPanel.setTipStyle(style);
			}
		}

		/**
		 * ��黭�����Ƿ���ȫ�������У�����о�ɾ��֮
		 */
		public void checkFullLine() {
			for (int i = 0; i < canvas.getRows(); i++) {
				int row = -1;
				boolean fullLineColorBox = true;
				for (int j = 0; j < canvas.getCols(); j++) {
                           //����i�У���j���Ƿ�Ϊ��ɫ����
					if (!canvas.getBox(i, j).isColorBox()) {
						   //�ǲ�ɫ���飬
						fullLineColorBox = false;
						break;
						//�˳���ѭ���������һ��
					}
				}
				if (fullLineColorBox) {
					row = i--;
					canvas.removeLine(row);
					//��������������ȥ
				}
			}
		}

		/**
		 * ��������Ƿ�ռ���ж���Ϸ�Ƿ��Ѿ������ˡ�
		 * @return boolean, true-��Ϸ�����ˣ�false-��Ϸδ����
		 */
		private boolean isGameOver() {
			for (int i = 0; i < canvas.getCols(); i++) {
				ErsBox box = canvas.getBox(0, i);
				if (box.isColorBox()) return true;
			}
			return false;
		}
	}

	/**
	 * ������ں���
	 * @param args String[], �����������в���������Ϸ
	 * ����Ҫ�������в���
	 */
	public static void main(String[] args) {
		new ErsBlocksGame("����˹������Ϸ");
	}
}


/**
 * File: ErsBox.java
 * User: Administrator
 * Date: Dec 15, 2003
 * Describe: ����˹����� Java ʵ��
 */

import java.awt.*;

/**
 * �����࣬����ɶ���˹����Ļ���Ԫ�أ����Լ�����ɫ����ʾ������
 * һ����ʵ��Cloneable�ӿڣ�����ζ�ſ��ԺϷ���ʹ��Object.clone()����
 * ����ؿ�������󣬷������ֿ������ᵼ���쳣
 */
class ErsBox implements Cloneable {
	private boolean isColor;
	private Dimension size = new Dimension();

	/**
	 * ������Ĺ��캯��
	 * @param isColor �ǲ�����ǰ��ɫ��Ϊ�˷�����ɫ��
	 *      trueǰ��ɫ��false�ñ���ɫ
	 */
	public ErsBox(boolean isColor) {
		this.isColor = isColor;
	}

	/**
	 * �˷����ǲ�����ǰ��ɫ����
	 * @return boolean,true��ǰ��ɫ���֣�false�ñ���ɫ����
	 */
	public boolean isColorBox() {
		return isColor;
	}

	/**
	 * ���÷������ɫ��
	 * @param isColor boolean,true��ǰ��ɫ���֣�false�ñ���ɫ����
	 */
	public void setColor(boolean isColor) {
		this.isColor = isColor;
	}

	/**
	 * �õ��˷���ĳߴ�
	 * @return Dimension,����ĳߴ�
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * ���÷���ĳߴ�
	 * @param size Dimension,����ĳߴ�
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	/**
	 * ����Object��Object clone()��ʵ�ֿ�¡
	 * @return Object,��¡�Ľ��
	 */
	public Object clone() {
		Object cloned = null;
		try {
			cloned = super.clone();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cloned;
	}
}

/**
 * File: ErsBlock.java
 * User: Administrator
 * Date: Dec 15, 2004
 * Describe: ����˹����� Java ʵ��
 */

/**
 * ���࣬�̳����߳��ࣨThread��
 * �� 4 * 4 ������ErsBox������һ���飬
 * ���ƿ���ƶ������䡢���ε�
 */
class ErsBlock extends Thread {
	/**
	 * һ����ռ��������4��
	 */
	public final static int BOXES_ROWS = 4;
	/**
	 * һ����ռ��������4��
	 */
	public final static int BOXES_COLS = 4;
	/**
	 * �������仯ƽ�������ӣ�������󼸼�֮����ٶ�����һ��
	 */
	public final static int LEVEL_FLATNESS_GENE = 3;
	/**
	 * ���������֮�䣬��ÿ����һ�е�ʱ����Ϊ����(����)
	 */
	public final static int BETWEEN_LEVELS_DEGRESS_TIME = 50;
	/**
	 * �������ʽ��ĿΪ7
	 */
	private final static int BLOCK_KIND_NUMBER = 7;
	/**
	 * ÿһ����ʽ�ķ���ķ�ת״̬����Ϊ4
	 */
	private final static int BLOCK_STATUS_NUMBER = 4;
	/**
	 * �ֱ��Ӧ��7��ģ�͵ķ�ת�õ���28��״̬
	 */
	public final static int[][] STYLES = {// ��28��״̬
		{0x0f00, 0x4444, 0x0f00, 0x4444}, // �����͵�����״̬
		{0x04e0, 0x0464, 0x00e4, 0x04c4}, // 'T'�͵�����״̬
		{0x4620, 0x6c00, 0x4620, 0x6c00}, // ��'Z'�͵�����״̬
		{0x2640, 0xc600, 0x2640, 0xc600}, // 'Z'�͵�����״̬
		{0x6220, 0x1700, 0x2230, 0x0740}, // '7'�͵�����״̬
		{0x6440, 0x0e20, 0x44c0, 0x8e00}, // ��'7'�͵�����״̬
		{0x0660, 0x0660, 0x0660, 0x0660}, // ���������״̬
	};

	private GameCanvas canvas;
	private ErsBox[][] boxes = new ErsBox[BOXES_ROWS][BOXES_COLS];
	private int style, y, x, level;
	private boolean pausing = false, moving = true;

	/**
	 * ���캯��������һ���ض��Ŀ�
	 * @param style �����ʽ����ӦSTYLES��28��ֵ�е�һ��
	 * @param y ��ʼλ�ã����Ͻ���canvas�е�������
	 * @param x ��ʼλ�ã����Ͻ���canvas�е�������
	 * @param level ��Ϸ�ȼ������ƿ�������ٶ�
	 * @param canvas ����
	 */
	public ErsBlock(int style, int y, int x, int level, GameCanvas canvas) {
		this.style = style;
		this.y = y;
		this.x = x;
		this.level = level;
		this.canvas = canvas;

		int key = 0x8000;
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				boolean isColor = ((style & key) != 0);
				boxes[i][j] = new ErsBox(isColor);
				key >>= 1;
			}
		}

		display();
	}

	/**
	 * �߳����run()�������ǣ�����飬ֱ���鲻��������
	 */
	public void run() {
		while (moving) {
			try {
				sleep(BETWEEN_LEVELS_DEGRESS_TIME
				        * (ErsBlocksGame.MAX_LEVEL - level + LEVEL_FLATNESS_GENE));
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			//��ߵ�moving�Ǳ�ʾ�ڵȴ���100����䣬movingû���ı�
			if (!pausing) moving = (moveTo(y + 1, x) && moving);
		}
	}

	/**
	 * �������ƶ�һ��
	 */
	public void moveLeft() {
		moveTo(y, x - 1);
	}

	/**
	 * �������ƶ�һ��
	 */
	public void moveRight() {
		moveTo(y, x + 1);
	}

	/**
	 * ��������һ��
	 */
	public void moveDown() {
		moveTo(y + 1, x);
	}

	/**
	 * �����
	 */
	public void turnNext() {
		for (int i = 0; i < BLOCK_KIND_NUMBER; i++) {
			for (int j = 0; j < BLOCK_STATUS_NUMBER; j++) {
				if (STYLES[i][j] == style) {
					int newStyle = STYLES[i][(j + 1) % BLOCK_STATUS_NUMBER];
					turnTo(newStyle);
					return;
				}
			}
		}
	}

	/**
	 * ��ͣ������䣬��Ӧ��Ϸ��ͣ
	 */
	public void pauseMove() {
		pausing = true;
	}

	/**
	 * ����������䣬��Ӧ��Ϸ����
	 */
	public void resumeMove() {
		pausing = false;
	}

	/**
	 * ֹͣ������䣬��Ӧ��Ϸֹͣ
	 */
	public void stopMove() {
		moving = false;
	}

	/**
	 * ����ǰ��ӻ����Ķ�Ӧλ��Ĩȥ��Ҫ�ȵ��´��ػ�����ʱ���ܷ�ӳ����
	 */
	private void earse() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				if (boxes[i][j].isColorBox()) {
					ErsBox box = canvas.getBox(i + y, j + x);
					if (box == null) continue;
					box.setColor(false);
				}
			}
		}
	}

	/**
	 * �õ�ǰ���ڻ����Ķ�Ӧλ������ʾ������Ҫ�ȵ��´��ػ�����ʱ���ܿ���
	 */
	private void display() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				if (boxes[i][j].isColorBox()) {
					ErsBox box = canvas.getBox(y + i, x + j);
					if (box == null) continue;
					box.setColor(true);
				}
			}
		}
	}

	/**
	 * ��ǰ���ܷ��ƶ���newRow/newCol��ָ����λ��
	 * @param newRow int, Ŀ�ĵ�������
	 * @param newCol int, Ŀ�ĵ�������
	 * @return boolean, true-���ƶ���false-����
	 */
	private boolean isMoveAble(int newRow, int newCol) {
		earse();
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				if (boxes[i][j].isColorBox()) {
					ErsBox box = canvas.getBox(newRow + i, newCol + j);
	                //�����ǰλ�ò����Ƿ�����������ķ��飬�����ƶ�
					if (box == null || (box.isColorBox())) {
						display();
						return false;
					}
				}
			}
		}
		display();
		return true;
	}

	/**
	 * ����ǰ���ƶ���newRow/newCol��ָ����λ��
	 * @param newRow int, Ŀ�ĵ�������
	 * @param newCol int, Ŀ�ĵ�������
	 * @return boolean, true-�ƶ��ɹ���false-�ƶ�ʧ��
	 */
	private synchronized boolean moveTo(int newRow, int newCol) {
		//������µ��в����ƶ���ֹͣ�ƶ��򷵻ؼ�
		if (!isMoveAble(newRow, newCol) || !moving) return false;
        //Ĩ���ɵĵĺۼ�
		earse();
		//���µ�λ���ػ�
		y = newRow;
		x = newCol;

		display();
		canvas.repaint();

		return true;
	}

	/**
	 * ��ǰ���ܷ���newStyle��ָ���Ŀ���ʽ����Ҫ��Ҫ����
	 * �߽��Լ��������鵲ס�������ƶ������
	 * @param newStyle int,ϣ���ı�Ŀ���ʽ����ӦSTYLES��28��ֵ�е�һ��
	 * @return boolean,true-�ܸı䣬false-���ܸı�
	 */
	private boolean isTurnAble(int newStyle) {
		int key = 0x8000;
		earse();
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				//���4��4�ĸ����ڣ���ģʽ�ĸ����Ƿ�Ϊ����
				if ((newStyle & key) != 0) {
					//��鵱ǰ�����Ƿ�Ϊ������ڲ��Ҳ�������
					//������ת��
					ErsBox box = canvas.getBox(y + i, x + j);
					if (box == null || box.isColorBox()) {
						display();
						return false;
					}
				}
				key >>= 1;
			}
		}
		display();
		return true;
	}

	/**
	 * ����ǰ����newStyle��ָ���Ŀ���ʽ
	 * @param newStyle int,��Ҫ�ı�ɵĿ���ʽ����ӦSTYLES��28��ֵ�е�һ��
	 * @return boolean,true-�ı�ɹ���false-�ı�ʧ��
	 */
	private boolean turnTo(int newStyle) {
		if (!isTurnAble(newStyle) || !moving) return false;

		earse();
		int key = 0x8000;
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				boolean isColor = ((newStyle & key) != 0);
				boxes[i][j].setColor(isColor);
				key >>= 1;
			}
		}
		style = newStyle;

		display();
		canvas.repaint();

		return true;
	}
}

//////////////////////////////
/**
 * File: ControlPanel.java
 * User: Administrator
 * Date: Dec 15, 2004
 * Describe: ����˹������Ϸ�Ŀ��Ʋ��ֵ�ʵ��
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * ��������࣬�̳�JPanel.
 * �ϱ߰���Ԥ�Լ������ֵķ������͵Ĵ��ڡ��Ѷȼ��𡢵÷֡����ư�ť
 * ��Ҫ����������Ϸ���̡�
 */
class ControlPanel extends JPanel {
	//��һ���ı�����ʾ�Ѷȼ���
	private JTextField
	        tfLevel = new JTextField("" + ErsBlocksGame.DEFAULT_LEVEL),
    //��һ���ı�����ʾ��ҵ÷�
	tfScore = new JTextField("0");

    //����һ����ư�ť
	private JButton
	        btPlay = new JButton("��ʼ"),
	        btPause = new JButton("��ͣ"),
	        btStop = new JButton("ֹͣ"),
	        btTurnLevelUp = new JButton("�����Ѷ�"),
	        btTurnLevelDown = new JButton("�����Ѷ�");

	/**���켸�����,�ֱ�ڷŲ�ͬ���͵Ĺ���
	*/

	//��ʾ��һ����Ϸ������
	private JPanel plTip = new JPanel(new BorderLayout());
	private TipPanel plTipBlock = new TipPanel();
	
	//��ʾ��Ϸ��ǰ��Ϣ����壬4��1��
	private JPanel plInfo = new JPanel(new GridLayout(4, 1));
    
	//��ſ��ư�ť����壬5��1��
	private JPanel plButton = new JPanel(new GridLayout(5, 1));
   
	private Timer timer;

	//��ǰ����Ϸ��
	private ErsBlocksGame game;
    
	//����ͻ����EtchedBorder���͵ı߿�
	private Border border = new EtchedBorder(
	        EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));

	/**
	 * �����������Ĺ��캯��
	 * @param game ErsBlocksGame, ErsBoxesGame���һ��ʵ�����ã�
	 * ����ֱ�ӿ���ErsBoxesGame�����Ϊ��
	 */
	public ControlPanel(final ErsBlocksGame game) {
		//�������������3������壬�ڷ���1�У�
		//ÿ�еļ��Ϊ4
		setLayout(new GridLayout(3, 1, 0, 4));
		this.game = game;

		//Ԥ��ʾ���������������߽�
		plTip.add(new JLabel("��һ������"), BorderLayout.NORTH);
		plTip.add(plTipBlock);
		plTip.setBorder(border);

        //��Ϸ��Ϣ��ʾ����������ǩ�������ı��򼰱߽�
		plInfo.add(new JLabel("�Ѷȼ���"));
		plInfo.add(tfLevel);
		plInfo.add(new JLabel("�÷�"));
		plInfo.add(tfScore);
		plInfo.setBorder(border);

		//�����ı����ǲ��ɱ༭�ģ�ֻ������ʾ��Ϣ
		tfLevel.setEditable(false);
		tfScore.setEditable(false);
        
		//��ť���������ť���߽�
		plButton.add(btPlay);
		plButton.add(btPause);
		plButton.add(btStop);
		plButton.add(btTurnLevelUp);
		plButton.add(btTurnLevelDown);
		plButton.setBorder(border);

		//��3���ϳ������뵽�������
		add(plTip);
		add(plInfo);
		add(plButton);

		//���Ӽ��̵ļ�����		
		addKeyListener(new ControlKeyListener());
        
		//���Ӱ�ť�ļ�����
		btPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.playGame();
			}
		});
		btPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (btPause.getText().equals(new String("��ͣ"))) {
					game.pauseGame();
				} else {
					game.resumeGame();
				}
			}
		});
		btStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.stopGame();
			}
		});
		btTurnLevelUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					int level = Integer.parseInt(tfLevel.getText());
					if (level < ErsBlocksGame.MAX_LEVEL)
						tfLevel.setText("" + (level + 1));
				} catch (NumberFormatException e) {
				}
				requestFocus();
			}
		});
		btTurnLevelDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					int level = Integer.parseInt(tfLevel.getText());
					if (level > 1)
						tfLevel.setText("" + (level - 1));
				} catch (NumberFormatException e) {
				}
				requestFocus();
			}
		});

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				plTipBlock.fanning();
			}
		});
    /**
	 *Timer��Ķ��������һ����ʱ��������һ�������¼�
	 *����һ����ʱ��Ҫ����һ��Timer����ע��һ����������������
	 *����start����������ʱ��
	 *�˴���ʱ����Ϊ500���룬��ʾ��ҵĵ÷֣��Ѷȼ���
	 */
		timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//��ʾ��ҵĵ÷�
				tfScore.setText("" + game.getScore());
				//���÷ֽ����Ѷȼ���
				int scoreForLevelUpdate =
				        game.getScoreForLevelUpdate();
	            //��ʾ���º���Ѷȼ���
				if (scoreForLevelUpdate >= ErsBlocksGame.PER_LEVEL_SCORE
				        && scoreForLevelUpdate > 0)
					game.levelUpdate();
			}
		});
		timer.start();//������ʱ��
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
	 * ����"��ʼ"��ť��״̬��
	 */
	public void setPlayButtonEnable(boolean enable) {
		btPlay.setEnabled(enable);
	}

    //����pause��ֵ���ð�ť����ʾ��ǩ
	public void setPauseButtonLabel(boolean pause) {
		btPause.setText(pause ? "��ͣ" : "����");
	}

	/**
	 * ���ÿ�����壬�÷���Ϊ0
	 */
	public void reset() {
		tfScore.setText("0");
		plTipBlock.setStyle(0);
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

		//��ʾ��Ϣ����ǰ���ͱ�����ɫ
		private Color backColor = Color.darkGray, frontColor = Color.lightGray;
        
		//�����趨�������������Ķ���˹����
		private ErsBox[][] boxes =
		        new ErsBox[ErsBlock.BOXES_ROWS][ErsBlock.BOXES_COLS];
        
        //�����̬����Ⱥ͸߶�
		private int style, boxWidth, boxHeight;
		
		//isTiled�Ƿ�ƽ��
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
		 * ���ݴ��ڵĴ�С���Զ���������Ĵ�С
		 */
		public void fanning() {
			boxWidth = getSize().width / ErsBlock.BOXES_COLS;
			boxHeight = getSize().height / ErsBlock.BOXES_ROWS;
			isTiled = true;
		}
	}

	//��Ϸ�İ�ť���Ƽ��ļ�����
	private class ControlKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent ke) {
			if (!game.isPlaying()) return;

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


///////////////////////////////
/**
 * File: GameCanvas.java
 * User: Administrator
 * Date: Dec 15, 2003
 * Describe: ����˹�����ÿһ������Ļ���
 */

import javax.swing.*;
import javax.swing.border.EtchedBorder;
             //EtchedBorderΪswing���е�ͻ���򰼽��ı߿�
import java.awt.*;

/**
 * �����࣬����<����> * <����>���������ʵ����
 * �̳�JPanel�ࡣ
 * ErsBlock�߳��ද̬�ı仭����ķ�����ɫ��������ͨ��
 * ��鷽����ɫ������ErsBlock����ƶ������
 */
class GameCanvas extends JPanel {
    //Ĭ�ϵķ������ɫΪ�ۻ�ɫ��������ɫΪ��ɫ
	private Color backColor = Color.black, frontColor = Color.orange;
	private int rows, cols, score = 0, scoreForLevelUpdate = 0;
	private ErsBox[][] boxes;
	private int boxWidth, boxHeight;

	//score���÷֣�scoreForLevelUpdate����һ��������Ļ���

	/**
	 * ������ĵ�һ���汾�Ĺ��캯��
	 * @param rows int, ����������
	 * @param cols int, ����������
	 * �����������Է���Ϊ��λ�������Ż���ӵ�з������Ŀ
	 */
	public GameCanvas(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
        
		//��ʼ��rows*cols��ErsBox����
		boxes = new ErsBox[rows][cols];
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				boxes[i][j] = new ErsBox(false);
			}
		}
    
        //���û����ı߽�
		setBorder(new EtchedBorder(
		        EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)));
	}

	/**
	 * ������ĵڶ����汾�Ĺ��캯��
	 * @param rows ��public GameCanvas(int rows, int cols)��rows��ͬ
	 * @param cols ��public GameCanvas(int rows, int cols)��cols��ͬ
	 * @param backColor Color, ����ɫ
	 * @param frontColor Color, ǰ��ɫ
	 */
	public GameCanvas(int rows, int cols,
	                  Color backColor, Color frontColor) {
		this(rows, cols);
                           //���õ�һ���汾�Ĺ��캯��
		this.backColor = backColor;
		this.frontColor = frontColor;
		                  //ͨ���������ñ�����ǰ����ɫ
	}

	/**
	 * ������Ϸ����ɫ��
 	 * @param backColor Color, ����ɫ��
	 */
	public void setBackgroundColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * ȡ����Ϸ����ɫ��
 	 * @return Color, ����ɫ��
	 */
	public Color getBackgroundColor() {
		return backColor;
	}

	/**
	 * ������Ϸ����ɫ��
 	 * @param frontColor Color, ����ɫ��
	 */
	public void setBlockColor(Color frontColor) {
		this.frontColor = frontColor;
	}

	/**
	 * ȡ����Ϸ����ɫ��
 	 * @return Color, ����ɫ��
	 */
	public Color getBlockColor() {
		return frontColor;
	}

	/**
	 * ȡ�û����з��������
	 * @return int, ���������
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * ȡ�û����з��������
	 * @return int, ���������
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * ȡ����Ϸ�ɼ�
	 * @return int, ����
	 */
	public int getScore() {
		return score;
	}

	/**
	 * ȡ������һ��������Ļ���
	 * @return int, ��һ��������Ļ���
	 */
	public int getScoreForLevelUpdate() {
		return scoreForLevelUpdate;
	}

	/**
	 * �����󣬽���һ�����������Ļ�����0
	 */
	public void resetScoreForLevelUpdate() {
		scoreForLevelUpdate -= ErsBlocksGame.PER_LEVEL_SCORE;
	}

	/**
	 * �õ�ĳһ��ĳһ�еķ������á�
	 * @param row int, Ҫ���õķ������ڵ���
	 * @param col int, Ҫ���õķ������ڵ���
	 * @return ErsBox, ��row��col�еķ��������
	 */
	public ErsBox getBox(int row, int col) {
		if (row < 0 || row > boxes.length - 1
		        || col < 0 || col > boxes[0].length - 1)
			return null;
		return (boxes[row][col]);
	}

	/**
	 * ����JComponent��ĺ������������
	 * @param g ͼ���豸����
	 * paint����ʵ���ϰѻ�ͼ����Ҫ����ί�ɸ�paintComponent�����ȷ���
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(frontColor);
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
                //��ǰ����ɫ�򱳾���ɫ����ÿ������
				g.setColor(boxes[i][j].isColorBox() ? frontColor : backColor);
				g.fill3DRect(j * boxWidth, i * boxHeight,
				        boxWidth, boxHeight, true);
			}
		}
	}

	/**
	 * ���ݴ��ڵĴ�С���Զ���������ĳߴ�
	 */
	public void fanning() {
		boxWidth = getSize().width / cols;
		boxHeight = getSize().height / rows;
	}

	/**
	 * ��һ�б���Ϸ�ߵ����󣬽������������Ϊ��Ϸ�߼ӷ�
	 * @param row int, Ҫ������У�����ErsBoxesGame������
	 */
	public synchronized void removeLine(int row) {
		for (int i = row; i > 0; i--) {
			for (int j = 0; j < cols; j++)
				boxes[i][j] = (ErsBox) boxes[i - 1][j].clone();
		}

		score += ErsBlocksGame.PER_LINE_SCORE;
		scoreForLevelUpdate += ErsBlocksGame.PER_LINE_SCORE;
		repaint();
	}

	/**
	 * ���û������û���Ϊ0
	 */
	public void reset() {
		score = 0;
		scoreForLevelUpdate = 0;
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++)
				boxes[i][j].setColor(false);
		}

		repaint();
	}
}


