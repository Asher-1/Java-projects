package ant;

import util.Position;

/** �����࣬
 * �������ϡ���ͼ����Ϣ�أ�
 * ����ȫ��ͳ�ƺͿ��ơ�
 * �����������߳�����������Ϊ��
 * @author Leaf
 */
public class Environment {
	/** ��ͼ���� */
	private Map map;
	/** �������� */
	static int antAmount;
	/** �������� */
	Ant[] ants;
	/** ��Ϣ������ */
	Pheromone[][] pGrid;
	/** �������� */
	private AntCanvas canvas;
	/** ѭ�������� */
	public int loopCounter = 0;
	/** ���ѭ������ */
	public static int maxLoop = 500;
	private final double pheMin = 5;
	private final double pheMax = 300;
	/** �������� */
	private int bestAnt;
	/** ����߳��� */
	private class Daemon extends Thread {
		public Daemon(String name) {
			super(name);
		}
		private boolean reset = false;
		public void run() {
			while(true) {
				while(!terminated) {	// �߳�������
					synchronized(this) {
						try {
							wait(timeout);
						} catch(InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
					if(reset) {	// ·���ҵ�����ʱ������
						reset = false;
					} else {
						System.out.println("Daemon: restarting Ants");	// debug
						for(int i = 0; i < antAmount; i++) {
							ants[i].reset();
						}
					}
				}
			}
		}
		
		public void reCount() {	// ��ʱ�����迪��
			reset = true;
		}
	}
	/** ���н������� */
	Thread runner;
	Daemon daemon;
	static int timeout = 10000;
	/** �߳�״̬�����ź� */
	volatile boolean terminated = true, found = false;
	
	int initLength;
	
	/** ������
	 * @param am ��������
	 * @param map ʹ�õĵ�ͼ
	 * @param canvas ʹ�õĻ���
	 */
	public Environment(int am, Map map, AntCanvas canvas) {
		antAmount = am;
		this.map = map;
		this.canvas = canvas;
		initLength = 20 * Map.WIDTH;
		initializePheromone();
		initializeAnts();
		initializeStat();
		initializeCanvas();
		createThread();
		createDaemon();
	}
	
	/** ���������߳� */
	public void createThread() {
		found = false;
		runner = new Thread("Environment") {	//�߳��ڲ���
			public void run() {
				terminated = false;
				startAnts();
			}
		};
	}
	
	private void createDaemon() {
		daemon = new Daemon("Daemon");
		daemon.setDaemon(true);
		daemon.start();
	}
	
	/** ͳ����Ϣ��ʼ�� */
	public void initializeStat() {
		Ant.minTourLength = initLength;
		Ant.minTour.clear();
		Pheromone.refreshList.clear();
	}
	
	/** ��Ϣ�س�ʼ�� */
	private void initializePheromone() {
		Pheromone.min = pheMin;
		Pheromone.max = pheMax;
		Pheromone.currentEnvironment = this;
		Pheromone.estimatedLength = initLength;
		Pheromone.color = canvas.BLUE;
		Pheromone.refreshList.clear();
		pGrid = new Pheromone[Map.WIDTH][Map.HEIGHT];
		for(int x = 0; x < Map.WIDTH; x++) {
			for(int y = 0; y < Map.HEIGHT; y++) {
				pGrid[x][y] = new Pheromone(x, y);
			}
		}
		readMap(map);
	}
	
	private void readMap(Map map) {
		int x = map.food.x;
		int y = map.food.y;
		for(int xx = x - 2; xx <= x + 2; xx++) {
			if(xx < 0 || xx >= Map.WIDTH) continue;
			for(int yy = y - 2; yy <= y + 2; yy++) {
				if(yy < 0 || yy >= Map.HEIGHT) continue;
				if(map.grid[xx][yy] != Map.BLOCKED) {
					pGrid[xx][yy].modifyBy(pheMax / 4);
					Pheromone.refreshList.add(pGrid[xx][yy]);
				}
			}
		}
		for(int xx = x - 1; xx <= x + 1; xx++) {
			if(xx < 0 || xx >= Map.WIDTH) continue;
			for(int yy = y - 1; yy <= y + 1; yy++) {
				if(yy < 0 || yy >= Map.HEIGHT) continue;
				if(map.grid[xx][yy] != Map.BLOCKED) {
					pGrid[xx][yy].modifyBy(pheMax / 2);
				}
			}
		}
		pGrid[x][y].modifyBy(pheMax);
	}
	
	/** ���ϳ�ʼ�� */
	private void initializeAnts() {
		Ant.currentEnvironment = this;
		Ant.color = canvas.BLUE;
		Ant.forwardOutTimes = 5;
		Ant.optLength = 17;
		ants = new Ant[antAmount];
		for(int i = 0; i < antAmount; i++) {
			ants[i] = new Ant(i);
		}
	}
	
	/** ������ʼ�� */
	private void initializeCanvas() {
//		map.draw(canvas.gc);
	}
	
	/** ��������ѭ�� */
	public synchronized void startAnts() {
		int optModifier = 1;
		int orgOptLength = Ant.optLength;
		initializeStat();
		while(!isDone()) {
			while(!found && !terminated) {
				for(int i = 0; i < antAmount; i++) {
					ants[i].Proceed();
				}
			}

			// �ֲ���Ϣ�ظ���
			for(int i = 0; i < antAmount; i++) {
				Pheromone.localPheromoneRelease(ants[i].tour);
			}

			// ȫ����Ϣ�ظ���
			if(found && ants[bestAnt].tourLength < Ant.minTourLength) {
				Ant.minTour = ants[bestAnt].tour.completeClone();
				Ant.minTourLength = ants[bestAnt].tourLength;
				Pheromone.globlePheromoneRelease(ants[bestAnt].tour);
			} else if(!Ant.minTour.isEmpty()) {
				Ant.optLength = optModifier + orgOptLength;	// �ڶ�optLength
				optModifier++;
				optModifier %= 8;
				Ant.minTour = Ant.optimizeTour(Ant.minTour);
				Ant.minTourLength = Ant.minTour.size();
				Pheromone.globlePheromoneRelease(Ant.minTour);
			}
			found = false;
			
			// todo: restart
			for(int i = 0; i < antAmount; i++) {
				ants[i].reset();
			}
		}
	}
	
	/** ��������ѭ�� */
	public void endAnts() {
		terminated = true;
	}
	
	/** ���·������ */
	public void endCycle(int NO) {
		bestAnt = NO;
		found = true;
		
		daemon.reCount();
		synchronized(daemon) {
			daemon.notify();
		}
		
	}
	
	/** ���������ж� */
	private boolean isDone() {
		loopCounter++;
		return (loopCounter == maxLoop || terminated);
	}
	
	/** ������λ��������ͳ����Ϣ */
	public synchronized void reset() {
		ants = new Ant[antAmount];
		for(int i = 0; i < antAmount; i++) {
			ants[i] = new Ant(i);
		}
		initializePheromone();
//		initializeStat();
		initializeCanvas();
		loopCounter = 0;
	}
	
	public Pheromone pheAt(Position p) {
		return pGrid[p.x][p.y];
	}
	public Pheromone pheAt(int x, int y) {
		return pGrid[x][y];
	}
	
	public char terranAt(Position p) {
		return map.grid[p.x][p.y];
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
