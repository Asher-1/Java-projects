/**
 * File: ErsBlocksGame.java
 * User: Administrator
 * Date: Dec 15, 2003
 * Describe: ����˹����� Java ʵ��
 */
package game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * ��Ϸ���࣬�̳�JFrame�࣬������Ϸ��ȫ�ֿ��ơ� �ں� 1, һ��GameCanvas�������ʵ�����ã� 2,
 * һ�����浱ǰ���(ErsBlock)ʵ�������ã� 3, һ�����浱ǰ������壨ControlPanel��ʵ��������;
 */
public class ErsBlocksGame extends GameMenu {
	/**
	 * ÿ����һ�мƶ��ٷ�
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

	public static int selfFail = 0;

	public static int otherFail = 0;
	

	// һ��GameCanvas�������ʵ�����ã�
	private GameCanvas canvas;

	// һ�����浱ǰ���(ErsBlock)ʵ�������ã�
	private ErsBlock block;

	// һ�����浱ǰ������壨ControlPanel��ʵ��������;
	private ControlPanel ctrlPanel;

	private boolean playing = false;

	/**
	 * ����Ϸ��Ĺ��캯��
	 * 
	 * @param title
	 *            String�����ڱ���
	 */
	public ErsBlocksGame(String title) {
		super(title);

		// ��ʼ���ڵĴ�С���û��ɵ���
		setSize(315, 392);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();

		// ����Ϸ����������Ļ����
		setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);

		Container container = getContentPane();

		// ���ֵ�ˮƽ����֮����6�����صľ���
		container.setLayout(new BorderLayout(6, 0));

		// ����20������ߣ�12����������Ϸ����
		canvas = new GameCanvas();

		// ����һ���������
		ctrlPanel = new ControlPanel(this);
		ctrlPanel.setPlayButtonEnable(false);

		// ��Ϸ�����Ϳ������֮�����Ұڷ�
		container.add(canvas, BorderLayout.CENTER);
		container.add(ctrlPanel, BorderLayout.EAST);

		// ���Ӵ��ڼ�����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		show(); // setVisiable

		// ���ݴ��ڵĴ�С���Զ���������ĳߴ�
		canvas.fanning();
		setResizable(false);

	}

	/**
	 * ����Ϸ����λ��
	 */
	public void reset() {
		ctrlPanel.reset(); // ���ƴ��ڸ�λ
		canvas.reset(); // ��Ϸ���帴λ

	}

	public void gameReset() {

		ctrlPanel.setPlayButtonEnable(false);
		// ctrlPanel.setPauseButtonLabel(true);
		getMGame().setEnabled(true);
		getMControl().setEnabled(true);
		getMInfo().setEnabled(true);
		getMiNewGame().setEnabled(true);
		getMiConnectGame().setEnabled(true);
	}

	/**
	 * �ж���Ϸ�Ƿ��ڽ���
	 * 
	 * @return boolean, true-�������У�false-�Ѿ�ֹͣ
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * �õ���ǰ��Ŀ�
	 * 
	 * @return ErsBlock, ��ǰ��������
	 */
	public ErsBlock getCurBlock() {
		return block;
	}

	/**
	 * �õ���ǰ����
	 * 
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
		// miPlay.setEnabled(false);
		ctrlPanel.requestFocus();
	}

	/**
	 * �õ���ǰ��Ϸ�����õ���Ϸ�Ѷ�
	 * 
	 * @return int, ��Ϸ�Ѷ�1��MAX_LEVEL��10
	 */
	public int getLevel() {
		return ctrlPanel.getLevel();
	}

	/**
	 * ���û�������Ϸ�Ѷ�ϵ��
	 * 
	 * @param level
	 *            int, ��Ϸ�Ѷ�ϵ��Ϊ1��MAX_LEVEL��10
	 */
	public void setLevel(int level) {
		if (level < 11 && level > 0)
			ctrlPanel.setLevel(level);
	}

	/**
	 * �õ���Ϸ����
	 * 
	 * @return int, ���֡�
	 */
	public int getScore() {
		if (canvas != null)
			return canvas.getScore();
		return 0;
	}

	/**
	 * �õ����ϴ�������������Ϸ���֣������Ժ󣬴˻�������
	 * 
	 * @return int, ���֡�
	 */
	public int getScoreForLevelUpdate() {
		if (canvas != null)
			return canvas.getScoreForLevelUpdata();
		return 0;
	}

	/**
	 * �������ۼƵ�һ��������ʱ����һ�μ�
	 * 
	 * @return boolean, ture-���³ɹ�, false-����ʧ��
	 */
	public boolean levelUpdate() {
		int curLevel = getLevel();
		if (curLevel < MAX_LEVEL) {
			setLevel(curLevel + 1);
			canvas.resetScoreForLevelUpdata();
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

	private void reportGameWin() {
		JOptionPane.showMessageDialog(this, "��Ӯ��");
	}

	private void reportGameLose() {
		JOptionPane.showMessageDialog(this, "������");
	}

	/**
	 * һ����Ϸ���̣�ʵ����Runnable�ӿ� һ����Ϸ��һ����ѭ���������ѭ���У�ÿ��100���룬 �����Ϸ�еĵ�ǰ���Ƿ��Ѿ������ˣ����û�У�
	 * �ͼ����ȴ�����������ˣ��Ϳ���û��ȫ�������У� ����о�ɾ��������Ϊ��Ϸ�߼ӷ֣�ͬʱ�������һ�� �µĵ�ǰ�飬����������������ٶȣ��Զ����䡣
	 * ���²���һ����ʱ���ȼ�黭����˵�һ���Ƿ��Ѿ� ��ռ�ˣ�����ǣ�������ʾ����Ϸ�������ˡ�
	 */

	private JDialog serverSuc() {

		JDialog dialog = new JDialog(this, "��ʾ��Ϣ", false);
		JLabel label = new JLabel("            �˿���Ч���ȴ����������......");
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setSize(300, 90);
		dialog.setResizable(false);
		dialog.getContentPane().add(label);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);
		return dialog;
	}

	private JDialog socketSuc() {

		JDialog dialog = new JDialog(this, "��ʾ��Ϣ", false);
		JLabel label = new JLabel("��������ӽ����ɹ���10s��ʼ��Ϸ��");
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setSize(300, 90);
		dialog.setResizable(false);
		dialog.getContentPane().add(label);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);
		return dialog;
	}

	private JDialog showException(Exception e) {

		JDialog dialog = new JDialog(this, "��ʾ��Ϣ", false);
		JLabel label = new JLabel(e.toString());
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setSize(300, 90);
		dialog.setResizable(false);
		dialog.getContentPane().add(label);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);
		return dialog;
	}

	private class Game implements Runnable {
		public void run() {

			int col = canvas.getCols() - 8;

			int style = ErsBlock.STYLES[(int) (Math.random() * 7)][(int) (Math
					.random() * 4)];

			ServerSocket server = getServer();
			JDialog serverSuc = serverSuc();
			JDialog socketSuc = socketSuc();
			Socket socketOut = null;
			Socket socketIn = null;
			ObjectInputStream in = null;
			ObjectOutputStream out = null;

			selfFail = 0;
			otherFail = 0;
			int numLose = 0;
			boolean serverSign = false;
			boolean clienSign = false;
			int[][] failData;

			if (getAddress() != null) {

				serverSign = false;
				clienSign = true;
				try {

					socketIn = new Socket(getAddress(), getPortNum());
					socketOut = new Socket(getAddress(), getPortNum());

					in = new ObjectInputStream(socketIn.getInputStream());
					out = new ObjectOutputStream(socketOut.getOutputStream());
				} catch (Exception e) {
					System.out.println(e.toString());

					// TODO: handle exception
				}

			} else {
				if (server != null) {
					serverSign = true;
					clienSign = false;
					try {
						serverSuc.setVisible(true);
						// Thread.sleep (1000*3);

						socketOut = server.accept();
						socketIn = server.accept();
						Thread.sleep(1000 * 2);

						serverSuc.setVisible(false);

						socketSuc.setVisible(true);
						Thread.sleep(1000 * 2);
						socketSuc.setVisible(false);
						out = new ObjectOutputStream(socketOut
								.getOutputStream());
						in = new ObjectInputStream(socketIn.getInputStream());

					} catch (Exception e) {
						showException(e).setVisible(true);
					}
				}
			}
			while (playing) {
				if (otherFail == 1) {

					try {
						
					} catch (Exception e) {
						System.out.println("�Է����ʧ��");
						System.out.println(e.toString());
					}
					reportGameWin();
					try {
						if (in != null)
							in.close();
						if (out != null)
							out.close();
						if (serverSign) {
							if (server != null) {
								server.close();
								getServer().close();
							}
						}
						if (socketIn != null)
							socketIn.close();
						if (socketOut != null)
							socketOut.close();
						setAddress(null);
						setServer(null);
					} catch (Exception e) {
					}
					gameReset();
					reset();
					return;
				}
				if (selfFail == 1) {
					failData = new int[GameCanvas.CANVAS_ROWS][GameCanvas.CANVAS_COLS];
					try {
						out.writeObject(new Data(failData, selfFail,0));

					} catch (Exception e) {
						System.out.println(e.toString());
					}
					reportGameLose();
				
					try {
						if (in != null)
							in.close();
						if (out != null)
							out.close();
						if (serverSign) {
							if (server != null) {
								server.close();
								getServer().close();
							}
						}
						if (socketIn != null)
							socketIn.close();
						if (socketOut != null)
							socketOut.close();
						setAddress(null);
						setServer(null);
					} catch (Exception e) {
						
					}
					gameReset();
					reset();
					return;
				}
				if (block != null) { // ��һ��ѭ��ʱ��blockΪ��
					if (block.isAlive()) {
						try {
							Thread.currentThread().sleep(100);
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
						continue;
					}
				}

				numLose = checkFullLine(); // ����Ƿ���ȫ��������

				if (isGameOver()) { // �����Ϸ�Ƿ�Ӧ�ý�����
					
					selfFail = 1;
				}
				// ����һ����Ϸ��

				block = new ErsBlock(style, -1, col, getLevel(), canvas,
						ctrlPanel, in, out,numLose);

				// ��Ϊ�߳̿�ʼ����
				block.start();

				// ���������һ����ĳ�ʼ�е�λ��
				// ���������һ����ĳ�ʼ��̬��28��֮һ��
				style = ErsBlock.STYLES[(int) (Math.random() * 7)][(int) (Math
						.random() * 4)];

				// �ڿ����������ʾ��һ�������״
				ctrlPanel.setTipStyle(style);
			}
		}

		/**
		 * ��黭�����Ƿ���ȫ�������У�����о�ɾ��֮
		 */
		public int checkFullLine() {
			int num = 0;
			for (int i = 0; i < canvas.getRows(); i++) {
				int row = -1;
				boolean fullLineColorBox = true;
				for (int j = 0; j < canvas.getCols(); j++) {
					// ����i�У���j���Ƿ�Ϊ��ɫ����
					if (!canvas.getBox(i, j).isColorBox()) {
						// �ǲ�ɫ���飬
						fullLineColorBox = false;
						break;
						// �˳���ѭ���������һ��
					}
				}
				if (fullLineColorBox) {
					row = i--;
					canvas.removeLine(row);
					num++;
					// ��������������ȥ
				}
			}
			return num;
		}

		/**
		 * ��������Ƿ�ռ���ж���Ϸ�Ƿ��Ѿ������ˡ�
		 * 
		 * @return boolean, true-��Ϸ�����ˣ�false-��Ϸδ����
		 */
		private boolean isGameOver() {
			for (int i = 0; i < canvas.getCols(); i++) {
				ErsBox box = canvas.getBox(0, i);
				if (box.isColorBox())
					return true;
			}
			return false;
		}
	}

	/**
	 * ������ں���
	 * 
	 * @param args
	 *            String[], �����������в���������Ϸ ����Ҫ�������в���
	 */
	public static void main(String[] args) {
		new ErsBlocksGame("����˹������Ϸ");
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == miNewGame || e.getSource() == miConnectGame) {
			ctrlPanel.setPlayButtonEnable(true);
		}
	}
}