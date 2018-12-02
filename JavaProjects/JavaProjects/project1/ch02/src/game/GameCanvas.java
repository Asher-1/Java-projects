/*
 * Created on 2006-4-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GameCanvas extends JPanel {
	// Ĭ�ϵķ������ɫΪ�ۻ�ɫ��������ɫΪ��ɫ
	private Color backColor = Color.black, frontColor = Color.orange;

	private int rows, cols, score = 0, scoreForLevelUpdata = 0;

	private ErsBox[][] boxes;

	private int boxWidth, boxHeight;
	
	private int[][] canvasData;

	public final static int CANVAS_ROWS = 22;

	public final static int CANVAS_COLS = 13;

	// score���÷֣�scoreForLevelUpdata����һ��������Ļ���

	/**
	 * ������ĵ�һ���汾�Ĺ��캯��
	 * 
	 * @param rows
	 *            int, ����������
	 * @param cols
	 *            int, ���������� �����������Է���Ϊ��λ�������Ż���ӵ�з������Ŀ
	 */
	public GameCanvas() {
		this.rows = CANVAS_ROWS;
		this.cols = CANVAS_COLS;

		// ��ʼ��rows*cols��ErsBox����
		boxes = new ErsBox[rows][cols];
		canvasData = new int[rows][cols];
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				boxes[i][j] = new ErsBox(false);
			}
		}

		// ���û����ı߽�
		setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(
				148, 145, 140)));
	}

	/**
	 * ������ĵڶ����汾�Ĺ��캯��
	 * 
	 * @param rows
	 *            ��public GameCanvas(int rows, int cols)��rows��ͬ
	 * @param cols
	 *            ��public GameCanvas(int rows, int cols)��cols��ͬ
	 * @param backColor
	 *            Color, ����ɫ
	 * @param frontColor
	 *            Color, ǰ��ɫ
	 */
	public GameCanvas(int rows, int cols, Color backColor, Color frontColor) {
		this(); // ���õ�һ���汾�Ĺ��캯��
		
		this.backColor = backColor;
		this.frontColor = frontColor;  // ͨ���������ñ�����ǰ����ɫ
		
	}

	/**
	 * @param i
	 * @param j
	 */

	/**
	 * ������Ϸ����ɫ��
	 * 
	 * @param backColor
	 *            Color, ����ɫ��
	 */
	public void setBackgroundColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * ȡ����Ϸ����ɫ��
	 * 
	 * @return Color, ����ɫ��
	 */
	public Color getBackgroundColor() {
		return backColor;
	}

	/**
	 * ������Ϸ����ɫ��
	 * 
	 * @param frontColor
	 *            Color, ����ɫ��
	 */
	public void setBlockColor(Color frontColor) {
		this.frontColor = frontColor;
	}

	/**
	 * ȡ����Ϸ����ɫ��
	 * 
	 * @return Color, ����ɫ��
	 */
	public Color getBlockColor() {
		return frontColor;
	}

	/**
	 * ȡ�û����з��������
	 * 
	 * @return int, ���������
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * ȡ�û����з��������
	 * 
	 * @return int, ���������
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * ȡ����Ϸ�ɼ�
	 * 
	 * @return int, ����
	 */
	public int getScore() {
		return score;
	}

	/**
	 * ȡ������һ��������Ļ���
	 * 
	 * @return int, ��һ��������Ļ���
	 */
	public int getScoreForLevelUpdata() {
		return scoreForLevelUpdata;
	}

	/**
	 * �����󣬽���һ�����������Ļ�����0
	 */
	public void resetScoreForLevelUpdata() {
		scoreForLevelUpdata -= ErsBlocksGame.PER_LEVEL_SCORE;
	}

	/**
	 * �õ�ĳһ��ĳһ�еķ������á�
	 * 
	 * @param row
	 *            int, Ҫ���õķ������ڵ���
	 * @param col
	 *            int, Ҫ���õķ������ڵ���
	 * @return ErsBox, ��row��col�еķ��������
	 */
	public ErsBox getBox(int row, int col) {
		if (row < 0 || row > boxes.length - 1 || col < 0
				|| col > boxes[0].length - 1)
			return null;
		return (boxes[row][col]);
	}

	public int[][] getCanvasData() {
		try {
			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++) {

					if (boxes[i][j].isColorBox()) {
						canvasData[i][j] = 1;
					} else {
						canvasData[i][j] = 0;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			
		}
		return canvasData;

	}

	public int currentRow() {

		int currentRow = 0;
		boolean sign = false;
		for (int i = GameCanvas.CANVAS_ROWS - 1; i >= 0; i--) {
			sign = true;
			for (int j = 0; j < GameCanvas.CANVAS_COLS; j++) {

				if (boxes[i][j].isColorBox()) {
					sign = false;
					break;
				}
			}
			if (sign) {
				currentRow = i + 1;
				break;
			}
		}
		return currentRow;
	}

	public ErsBox setAutoBox() {

		ErsBox box = null;
		if (Math.random() * 10 >= 5.0)
			box = new ErsBox(false);
		else
			box = new ErsBox(true);

		return box;
	}

	public boolean addNewRow(int num) {

		int currentRow = currentRow();
		if (currentRow <= num)
			return false;
		else {
			for (int i = currentRow; i < GameCanvas.CANVAS_ROWS; i++) {
				for (int j = 0; j < GameCanvas.CANVAS_COLS; j++) {
					boxes[i - num][j] = (ErsBox)boxes[i][j].clone();
				}
			}

			for (int i = GameCanvas.CANVAS_ROWS - 1; i > GameCanvas.CANVAS_ROWS - num -1; i--) {
				for (int j = 0; j < GameCanvas.CANVAS_COLS; j++) {
					boxes[i][j] = setAutoBox();
				}
			}
			repaint();
			return true;
		}

	}

	/**
	 * ����JComponent��ĺ������������
	 * 
	 * @param g
	 *            ͼ���豸���� paint����ʵ���ϰѻ�ͼ����Ҫ����ί�ɸ�paintComponent�����ȷ���
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(frontColor);

		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {

				// ��ǰ����ɫ�򱳾���ɫ����ÿ������
				if (boxes[i][j].isColorBox()) {
					g.setColor(frontColor);
				} else {
					g.setColor(backColor);
				}
				g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth, boxHeight,
						true);
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
	 * 
	 * @param row
	 *            int, Ҫ������У�����ErsBoxesGame������
	 */
	public synchronized void removeLine(int row) {
		for (int i = row; i > 0; i--) {
			for (int j = 0; j < cols; j++)
				boxes[i][j] = (ErsBox) boxes[i - 1][j].clone();
		}

		score += ErsBlocksGame.PER_LINE_SCORE;
		scoreForLevelUpdata += ErsBlocksGame.PER_LINE_SCORE;
		repaint();
	}

	/**
	 * ���û������û���Ϊ0
	 */
	public void reset() {
		score = 0;
		scoreForLevelUpdata = 0;
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++)
				boxes[i][j].setColor(false);
		}
		repaint();
	}
}
