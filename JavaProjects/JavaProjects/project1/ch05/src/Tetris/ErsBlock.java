/**
 * File: ErsBlock.java
 * User: ������
 * Date: 2006.11.8
 * Describe: ����˹����� Java ʵ��
 */

package Tetris;
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
	 * �ֱ��Ӧ��7��ģ�͵�28��״̬
	 */
	public final static int[][] STYLES = {// ��28��״̬
		{0x00cc, 0x00cc, 0x00cc, 0x00cc}, // ���������״̬
		{0x000f, 0x8888, 0x000f, 0x8888}, // �����͵�����״̬
		{0x004e, 0x08c8, 0x00e4, 0x04c4}, // 'T'�͵�����״̬
		{0x00e2, 0x044c, 0x008e, 0x0c88}, // ��'7'�͵�����״̬
		{0x00e8, 0x0c44, 0x002e, 0x088c}, // '7'�͵�����״̬
		{0x00c6, 0x04c8, 0x00c6, 0x04c8}, // 'Z'�͵�����״̬
		{0x006c, 0x08c4, 0x006c, 0x08c4} // ��'Z'�͵�����״̬
	};

	private GameCanvas canvas;
	private ErsBox[][] boxes = new ErsBox[BOXES_ROWS][BOXES_COLS];
	private int style; 
	private int y;
	private int x;
	private int level;
	private int curType;
	private int nextType;
	private int rotType;
	private int leftPos;
	private int showSleepTime;
	private boolean pausing = false;
	private boolean moving = true;
	private boolean autoPlay = false;
	
	private ErsSelectOpt selectOpt;

	/**
	 * ���캯��������һ���ض��Ŀ�
	 * @param style �����ʽ����ӦSTYLES��28��ֵ�е�һ��
	 * @param y ��ʼλ�ã����Ͻ���canvas�е�������
	 * @param x ��ʼλ�ã����Ͻ���canvas�е�������
	 * @param level ��Ϸ�ȼ������ƿ�������ٶ�
	 * @param canvas ����
	 */
	public ErsBlock(int style, int y, int x, int level, int curType, int nextType, ErsSelectOpt selectOpt, GameCanvas canvas) {
		this.style = style;
		this.y = y;
		this.x = x;
		this.level = level;
		this.curType = curType;
		this.nextType = nextType;
		this.selectOpt = selectOpt;
		this.canvas = canvas;
		this.autoPlay = false;
		
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
	 * �����Զ���Ϸ
	 * @param autoPlay boolean, true��ʾ�Զ���Ϸ��false��ʾ�˹���Ϸ
	 */
	public void setAutoPlay(boolean autoPlay){
		this.autoPlay = autoPlay;
	}
	
	/**
	 * ������Ϣʱ��
	 * @param showSleepTime int, ��Ϣʱ��(��λ:ms)
	 */
	public void setShowSleepTime(int showSleepTime){
		this.showSleepTime = showSleepTime;
	}
	
	/**
	 * �̵߳���Ϣʱ��
	 * @param sleepTime int, �̵߳���Ϣ(��λ:ms)
	 */
	public void pause(int sleepTime){
		try{
			sleep(sleepTime);
		} catch(InterruptedException ie){
			ie.printStackTrace();
		}
	}
	
	/**
	 * �߳����run()�������ǣ�����飬ֱ���鲻��������
	 */
	public void run() {
		
		selectOpt.setBlockType(curType, nextType);
		selectOpt.selectBest();
		leftPos = selectOpt.getLeftPos();
		rotType = selectOpt.getRotType();
		
		int newStyle = STYLES[curType][rotType];
		turnTo(newStyle);

		while (moving) {	
			if(autoPlay == false){
				pause(BETWEEN_LEVELS_DEGRESS_TIME
					  * (ErsBlocksGame.MAX_LEVEL - level + LEVEL_FLATNESS_GENE));
				//��ߵ�moving�Ǳ�ʾ�ڵȴ���100����䣬movingû���ı�
				if (!pausing) 
					moving = (moveTo(y + 1, x) && moving);
			}
			else if (!pausing){
				if(showSleepTime > 0){
					pause(showSleepTime);
				}
				moving = (moveTo(y + 1, leftPos) && moving);
			}
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
	 * ����ǰ��ӻ����Ķ�Ӧλ���Ƴ���Ҫ�ȵ��´��ػ�����ʱ���ܷ�ӳ����
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
	 * �õ�ǰ������ڻ����Ķ�Ӧλ���ϣ�Ҫ�ȵ��´��ػ�����ʱ���ܿ���
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
		if (!isMoveAble(newRow, newCol) || !moving) return false;

		earse();
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
				if ((newStyle & key) != 0) {
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
		//if (!isTurnAble(newStyle) || !moving) return false;
		if(!moving) return false;
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
