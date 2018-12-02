/**
 * File: ErsBlocksGame.java
 * User: ������
 * Date: 2006.11.8
 * Describe: ����˹����� Java ʵ��
 */

package Tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ��Ϸ���࣬�̳���JFrame�࣬������Ϸ��ȫ�ֿ��ơ�
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
	/**
	 * Ĭ��������Ϣʱ����200ms
	 */
	public final static int SLOW_SLEEP_TIME = 200;
	/**
	 * Ĭ��������Ϣʱ����100ms
	 */
	public final static int MIDDLE_SLEEP_TIME = 100;
	/**
	 * Ĭ�Ͽ�����Ϣʱ����0ms
	 */
	public final static int FAST_SLEEP_TIME = 0;
	
	public int delLine1 = 0;
	public int delLine2 = 0;
	public int delLine3 = 0;
	public int delLine4 = 0;
	public boolean autoPlay = false;
	private int showSleepTime;
	private Thread thread;
	private MyRand myRand = new MyRand(0.2, 0.2, 0.15, 0.15, 0.15, 0.075, 0.075);
	private GameCanvas canvas;
	private ErsBlock block;
	private boolean playing = false;
	private ControlPanel ctrlPanel;

	private JMenuBar bar = new JMenuBar();
	private JMenu
	        mGame = new JMenu("��Ϸ"),
			mControl = new JMenu("����"),
			mWindowStyle = new JMenu("���"),
			mInfo = new JMenu("����"),
			mSpeed = new JMenu("��ʾ�ٶ�����");
	private JMenuItem
	        miNewGame = new JMenuItem("����Ϸ"),
			miSetBlockColor = new JMenuItem("���÷�����ɫ"),
			miSetBackColor = new JMenuItem("���ñ�����ɫ"),
			miTurnHarder = new JMenuItem("�����Ѷ�"),
			miTurnEasier = new JMenuItem("�����Ѷ�"),
			miExit = new JMenuItem("�˳�"),

			miHplay = new JMenuItem("�˹���Ϸ"),
			miCplay = new JMenuItem("������Ϸ"),
			miPause = new JMenuItem("��ͣ��Ϸ"),
			miResume = new JMenuItem("�ָ���Ϸ"),
			miStop = new JMenuItem("������Ϸ"),
			
			miSetFast = new JMenuItem("����"),
			miSetMiddle = new JMenuItem("����"),
			miSetSlow = new JMenuItem("����"),
			
			miAuthor = new JMenuItem("�������ܶ���˹");

	private JCheckBoxMenuItem
	        miAsWindows = new JCheckBoxMenuItem("Windows"),
			miAsMotif = new JCheckBoxMenuItem("Motif"),
			miAsMetal = new JCheckBoxMenuItem("Metal", true);
	private ErsSelectOpt selectOpt;
	
	/**
	 * ����Ϸ��Ĺ��캯��
	 * @param title String�����ڱ���
	 */
	public ErsBlocksGame(String title) {
		super(title);
		
		delLine1 = 0;
		delLine2 = 0;
		delLine3 = 0;
		delLine4 = 0;
		autoPlay = false;
		
		setSize(315, 400);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrSize.width - getSize().width) / 2,
		        (scrSize.height - getSize().height) / 2);

		createMenu();

		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		//canvas = new GameCanvas(20, 12);
		canvas = new GameCanvas(20, 10);
		ctrlPanel = new ControlPanel(this);

		container.add(canvas, BorderLayout.CENTER);
		container.add(ctrlPanel, BorderLayout.EAST);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				stopGame();
				System.exit(0);
			}
		});

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				canvas.fanning();
			}
		});

		show();
		canvas.fanning();
	}

	/**
	 * ����Ϸ����λ��
	 */
	public void reset() {
		delLine1 = 0;
		delLine2 = 0;
		delLine3 = 0;
		delLine4 = 0;
		showSleepTime = 200;
		ctrlPanel.reset();
		canvas.reset();
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
	 * ���Կ�ʼ��Ϸ
	 */
	public void computerPlayGame() {
		autoPlay = true;
		computerPlay();
		ctrlPanel.setComputerPlayButtonEnabled(false);
		ctrlPanel.setHumanPlayButtonEnabled(false);
		miCplay.setEnabled(false);
		miHplay.setEnabled(false);
		ctrlPanel.requestFocus();
	}
	
	/**
	 * ���Կ�ʼ��Ϸ
	 */
	public void humanPlayGame() {
		autoPlay = false;
		humanPlay();
		ctrlPanel.setComputerPlayButtonEnabled(false);
		ctrlPanel.setHumanPlayButtonEnabled(false);
		miCplay.setEnabled(false);
		miHplay.setEnabled(false);
		ctrlPanel.requestFocus();
	}

	/**
	 * ��Ϸ��ͣ
	 */
	public void pauseGame() {
		if (block != null && autoPlay == false){ 
			block.pauseMove();
		}
		else if(block != null && autoPlay == true){
			thread.suspend();
		}
		ctrlPanel.setPauseButtonEnabled(false);
		ctrlPanel.setResumeButtonEnabled(true);
		miPause.setEnabled(false);
		miResume.setEnabled(true);
	}

	/**
	 * ����ͣ�е���Ϸ����
	 */
	public void resumeGame() {
		if (block != null && autoPlay == false){
			block.resumeMove();
		}
		else if(block != null && autoPlay == true){
			thread.resume();
		}
		ctrlPanel.setPauseButtonEnabled(true);
		ctrlPanel.setResumeButtonEnabled(false);
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
		miCplay.setEnabled(true);
		miHplay.setEnabled(true);
		miPause.setEnabled(true);
		miResume.setEnabled(false);
		ctrlPanel.setComputerPlayButtonEnabled(true);
		ctrlPanel.setHumanPlayButtonEnabled(true);
		ctrlPanel.setPauseButtonEnabled(true);
		ctrlPanel.setResumeButtonEnabled(false);
	}

	/**
	 * �õ���ǰ��Ϸ�����õ���Ϸ�Ѷ�
	 * @return int, ��Ϸ�Ѷ�1��MAX_LEVEL
	 */
	public int getLevel() {
		return ctrlPanel.getLevel();
	}

	/**
	 * ���û�������Ϸ�Ѷ�
	 * @param level int, ��Ϸ�Ѷ�1��MAX_LEVEL
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
	 * �õ���ȥ1�е�����
	 * @return int, ��ȥ1�е�������
	 */
	public int getDelLine1() {
		return delLine1;
	}
	
	/**
	 * �õ���ȥ2�е�����
	 * @return int, ��ȥ2�е�������
	 */
	public int getDelLine2() {
		return delLine2;
	}
	
	/**
	 * �õ���ȥ3�е�����
	 * @return int, ��ȥ3�е�������
	 */
	public int getDelLine3() {
		return delLine3;
	}
	
	/**
	 * �õ���ȥ4�е�����
	 * @return int, ��ȥ4�е�������
	 */
	public int getDelLine4() {
		return delLine4;
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
	 * @return boolean, ture-update successufl, false-update fail
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
	 * ������������Ϸ��ʼ
	 */
	private void computerPlay() {
		reset();
		playing = true;
		thread = new Thread(new Game());
		thread.start();
	}
	
	/**
	 * �˹�����Ϸ��ʼ
	 */
	private void humanPlay() {
		reset();
		playing = true;
		thread = new Thread(new Game());
		thread.start();
	}
	
	/**
	 * ������Ϸ������
	 */
	private void reportGameOver() {
		String msg = "��Ϸ����\nһ����ȥ����Ϊ: ";
		msg += String.valueOf(delLine1+delLine2*2+delLine3*3+delLine4*4);
		msg += "\n";
		JOptionPane.showMessageDialog(this, msg);
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

		mControl.add(miCplay);
		mControl.add(miHplay);
		mControl.add(miPause);
		mControl.add(miResume);
		mControl.add(miStop);
		mControl.add(mSpeed);
		mSpeed.add(miSetFast);
		mSpeed.add(miSetMiddle);
		mSpeed.add(miSetSlow);
		
		mWindowStyle.add(miAsWindows);
		mWindowStyle.add(miAsMotif);
		mWindowStyle.add(miAsMetal);

		mInfo.add(miAuthor);
		
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
		miSetBlockColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Color newFrontColor =
				        JColorChooser.showDialog(ErsBlocksGame.this,
				                "���÷�����ɫ", canvas.getBlockColor());
				if (newFrontColor != null)
					canvas.setBlockColor(newFrontColor);
			}
		});
		miSetBackColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Color newBackColor =
				        JColorChooser.showDialog(ErsBlocksGame.this,
				                "���ñ�����ɫ", canvas.getBackgroundColor());
				if (newBackColor != null)
					canvas.setBackgroundColor(newBackColor);
			}
		});
		miTurnHarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int curLevel = getLevel();
				if (curLevel < MAX_LEVEL) setLevel(curLevel + 1);
			}
		});
		miTurnEasier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int curLevel = getLevel();
				if (curLevel > 1) setLevel(curLevel - 1);
			}
		});
		miSetFast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				showSleepTime = FAST_SLEEP_TIME;
			}
		});
		miSetMiddle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				showSleepTime = MIDDLE_SLEEP_TIME;
			}
		});
		miSetSlow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				showSleepTime = SLOW_SLEEP_TIME;
			}
		});
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		miCplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				computerPlayGame();
			}
		});
		miHplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				humanPlayGame();
			}
		});
		miPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				pauseGame();
			}
		});
		miResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				resumeGame();
			}
		});
		miStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				stopGame();
			}
		});
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
		miAuthor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				AboutBox about = new AboutBox();
			}
		});
	}

	/**
	 * �����ִ����ô������
	 * @param plaf String, ������۵�����
	 */
	private void setWindowStyle(String plaf) {
		try {
			UIManager.setLookAndFeel(plaf);
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
	 * �µĵ�ǰ�飬�����Զ����䡣
	 * ���²���һ����ʱ���ȼ�黭����ϵ�һ���Ƿ��Ѿ�
	 * ��ռ�ˣ�����ǣ������ж�Game Over�ˡ�
	 */
	private class Game implements Runnable {
		public void run() {
			
			int col = (int) (Math.random() * (canvas.getCols() - 3));
			int curType = myRand.getRandom();//(int)(Math.random()*7);
			int nextType;
			int style = ErsBlock.STYLES[curType][(int)(Math.random()*4)]; 
			selectOpt = new ErsSelectOpt();
			
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
					miHplay.setEnabled(true);
					miCplay.setEnabled(true);
					miPause.setEnabled(true);
					miResume.setEnabled(false);
					ctrlPanel.setComputerPlayButtonEnabled(true);
					ctrlPanel.setHumanPlayButtonEnabled(true);
					ctrlPanel.setPauseButtonEnabled(true);
					ctrlPanel.setResumeButtonEnabled(false);
					reportGameOver();
					return;
				}
				
				nextType = myRand.getRandom();//(int)(Math.random()*7);
				block = new ErsBlock(style, -1, col, getLevel(), curType, nextType, selectOpt, canvas);
				block.setAutoPlay(autoPlay);
				if(autoPlay == true){
					block.setShowSleepTime(showSleepTime);
				}
				
				col = (int) (Math.random() * (canvas.getCols() - 3));
				style = ErsBlock.STYLES[nextType][(int)(Math.random()*4)];
				ctrlPanel.setTipStyle(style);
				block.start();
				curType = nextType;
			}
		}

		/**
		 * ��黭�����Ƿ���ȫ�������У�����о�ɾ��֮
		 */
		public void checkFullLine() {
			int delLines = 0;
			for (int i = 0; i < canvas.getRows(); i++) {
				int row = -1;
				boolean fullLineColorBox = true;
				for (int j = 0; j < canvas.getCols(); j++) {
					if (!canvas.getBox(i, j).isColorBox()) {
						fullLineColorBox = false;
						break;
					}
				}
				if (fullLineColorBox) {
					delLines++;
					row = i--;
					canvas.removeLine(row);
				}
			}
			switch(delLines){
				case 0:
					break;
				case 1:
					delLine1++;
					break;
				case 2:
					delLine2++;
					break;
				case 3:
					delLine3++;
					break;
				case 4:
					delLine4++;
					break;
				default:
					break;
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
	 * @param args String[], �����������в���
	 */
	public static void main(String[] args) {
		new ErsBlocksGame("���ܶ���˹����");
	}
}
