package ant;

import util.Position;

/** 环境类，
 * 包含蚂蚁、地图和信息素，
 * 进行全局统计和控制。
 * 建立单独的线程启动蚂蚁行为。
 * @author Leaf
 */
public class Environment {
	/** 地图引用 */
	private Map map;
	/** 蚂蚁数量 */
	static int antAmount;
	/** 蚂蚁数组 */
	Ant[] ants;
	/** 信息素阵列 */
	Pheromone[][] pGrid;
	/** 画布引用 */
	private AntCanvas canvas;
	/** 循环计数器 */
	public int loopCounter = 0;
	/** 最大循环次数 */
	public static int maxLoop = 500;
	private final double pheMin = 5;
	private final double pheMax = 300;
	/** 最优蚂蚁 */
	private int bestAnt;
	/** 监控线程类 */
	private class Daemon extends Thread {
		public Daemon(String name) {
			super(name);
		}
		private boolean reset = false;
		public void run() {
			while(true) {
				while(!terminated) {	// 线程运行中
					synchronized(this) {
						try {
							wait(timeout);
						} catch(InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
					if(reset) {	// 路径找到，计时器重设
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
		
		public void reCount() {	// 计时起重设开关
			reset = true;
		}
	}
	/** 运行进程引用 */
	Thread runner;
	Daemon daemon;
	static int timeout = 10000;
	/** 线程状态控制信号 */
	volatile boolean terminated = true, found = false;
	
	int initLength;
	
	/** 构造器
	 * @param am 蚂蚁数量
	 * @param map 使用的地图
	 * @param canvas 使用的画布
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
	
	/** 蚂蚁运行线程 */
	public void createThread() {
		found = false;
		runner = new Thread("Environment") {	//线程内部类
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
	
	/** 统计信息初始化 */
	public void initializeStat() {
		Ant.minTourLength = initLength;
		Ant.minTour.clear();
		Pheromone.refreshList.clear();
	}
	
	/** 信息素初始化 */
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
	
	/** 蚂蚁初始化 */
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
	
	/** 画布初始化 */
	private void initializeCanvas() {
//		map.draw(canvas.gc);
	}
	
	/** 启动蚂蚁循环 */
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

			// 局部信息素更新
			for(int i = 0; i < antAmount; i++) {
				Pheromone.localPheromoneRelease(ants[i].tour);
			}

			// 全局信息素更新
			if(found && ants[bestAnt].tourLength < Ant.minTourLength) {
				Ant.minTour = ants[bestAnt].tour.completeClone();
				Ant.minTourLength = ants[bestAnt].tourLength;
				Pheromone.globlePheromoneRelease(ants[bestAnt].tour);
			} else if(!Ant.minTour.isEmpty()) {
				Ant.optLength = optModifier + orgOptLength;	// 摆动optLength
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
	
	/** 结束蚂蚁循环 */
	public void endAnts() {
		terminated = true;
	}
	
	/** 最短路径更新 */
	public void endCycle(int NO) {
		bestAnt = NO;
		found = true;
		
		daemon.reCount();
		synchronized(daemon) {
			daemon.notify();
		}
		
	}
	
	/** 结束条件判断 */
	private boolean isDone() {
		loopCounter++;
		return (loopCounter == maxLoop || terminated);
	}
	
	/** 环境复位，不包括统计信息 */
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
