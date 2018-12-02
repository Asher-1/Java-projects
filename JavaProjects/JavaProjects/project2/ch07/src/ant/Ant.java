package ant;

import java.util.Random;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import util.PosList;
import util.Position;
import util.PosList.PosListIterator;

/** 蚂蚁类，
 * 控制每个独立蚂蚁行为
 * @author Leaf
 */
public class Ant {
	/** 标识 */
	int id;
	/** 路径长度 */
	int tourLength;
	/** 全局最短路径长度 */
	static int minTourLength;
	/** 已访问路径 */
	PosList tour = new PosList();
	/** 已知最短路径 */
	static PosList minTour = new PosList();
	/** 主方向 */
	private int section;
	private double slope;
	/** 增量计算 */
	private int d, incrE, incrNE;
	/** 目前位置 */
	Position now;
	/** 上一个位置 */
	Position last;
	/** 出发地 */
	Position from;
	/** 目的地 */
	Position to;
	
	/** 探索概率 */
	static double pExplore = 0.05;
	/** 随机扰动概率 */
	static double pDisturb = 0.1;
	/** 前向参考概率 */
	static double pLookForward = 0.7;
	
	boolean lookingForward = false;
	static int forwardOutTimes;
	int forwardCounter;
	static int forwardMinLength = 20;
	static int forwardMaxLength = 100;
	/** 随机数发生器 */
	static Random rand = new Random();

	/** 当前环境 */
	static Environment currentEnvironment;
	/** 路径优化长度 */
	public static int optLength;
	
	/** 蚂蚁颜色 */
	static Color color;
	
	/** 构造器
	 * @param id 蚂蚁编号
	 */
	public Ant(int id) {
		this.id = id;
//		currentEnvironment = e;
		from = currentEnvironment.getMap().nest;
		last = new Position(currentEnvironment.getMap().nest);
		now = new Position(currentEnvironment.getMap().nest);		//初始位置设为窝
		to = currentEnvironment.getMap().food;	//方向：窝->食物
		forwardCounter = forwardOutTimes; 
		initDirection();
	}
	
	/** 绘制蚂蚁
	 * @param gc 设备环境
	 */
	public void draw(GC gc) {
		//lastColor = gc.getForeground();
		gc.setForeground(gc.getBackground());
		gc.drawPoint(last.x, last.y);
		gc.setForeground(color);
		gc.drawPoint(now.x, now.y);
		//gc.setForeground(old);
	}
	
	/** 蚂蚁行为 */
	public void Proceed() {
		// 位置判断
		if(now.equals(to)) {
			tour = optimizeTour(tour);
			tourLength = tour.size();
			currentEnvironment.endCycle(id);
			return;
		}
		// 行走
		move();
		// 更新统计信息
		tour.add(new Position(now));
		tourLength++;
	}
	
	/** 重置蚂蚁，包括
	 * 路径，路径长度，起点，终点，当前位置
	 */
	public synchronized void reset() {
		tourLength = 0;
		tour.clear();
		from = currentEnvironment.getMap().nest;
		last.set(currentEnvironment.getMap().nest);
		now.set(currentEnvironment.getMap().nest);
		to = currentEnvironment.getMap().food;
	}
	
	public static PosList optimizeTour(PosList list) {
		PosList opt = new PosList();
		PosList inter = null;
		
		int index = 0;
		Position prev = list.get(index), curr;	// prev 设为路径起始位置
		while(index + optLength < list.size()) {
			curr = list.get(index + optLength);	// 优化目标位置，与prev距离optLength
			inter = genLine(prev, curr);
			if(inter.isEmpty()) {	// 使用原路径
				for(int i = 0; i < optLength; i++) {
					opt.add(new Position(list.get(index++)));
				}
			} else {	// 使用优化路径
				PosListIterator iter = inter.iterator();
				while(iter.hasNext()) {
					opt.add(new Position(iter.next()));
				}
				index += optLength;
			}
			prev = list.get(index);
		}
		if(index < list.size()) {
			inter = genLine(list.get(index), list.get(list.size() - 1));
			if(inter.isEmpty()) {
				while(index < list.size()) {	// 最后剩余的不足optLength部分
					opt.add(new Position(list.get(index++)));
				}
			} else {
				PosListIterator iter = inter.iterator();
				while(iter.hasNext()) {
					opt.add(new Position(iter.next()));
				}
				opt.add(new Position(list.get(list.size() - 1)));
				index = list.size();
			}
		}
		
		return opt;
	}
	
	/** 蚂蚁移动（单步） */
	private void move() {
		boolean finished = false;
		if(!lookingForward && rand.nextDouble() > pExplore) {
			Position next = new Position(now), temp;
			Pheromone pt, ph;
			PosList avail = new PosList();	// 可行走的位置
			// 查找所有可行走位置
			for(int xx = now.x - 1; xx <= now.x + 1; xx++) {
				if(xx < 0 || xx >= Map.WIDTH) continue;
				for(int yy = now.y - 1; yy <= now.y + 1; yy++) {
					if(yy < 0 || yy >= Map.HEIGHT) continue;
					if(currentEnvironment.getMap().grid[xx][yy] != Map.BLOCKED)
						avail.add(new Position(xx, yy));
				}
			}
			avail.remove(now);
			avail.remove(last);
			last.set(now);
			// 在可行走位置中寻找最吸引者
			PosListIterator iter = avail.iterator();
			while(iter.hasNext()) {
				temp = iter.next();
				pt = currentEnvironment.pheAt(temp);
				ph = currentEnvironment.pheAt(next);
				if(pt.get() > ph.get()) next = temp;
			}
			if(!next.equals(now)) {
				now.set(next);
				finished = true;
			}
		}
		if(!finished) {
			int dx = 1, dy = 0, temp = 0;
			boolean done = false;
			if(!lookingForward && rand.nextDouble() < pLookForward) lookForward();
			else if(!lookingForward && rand.nextDouble() < pDisturb) disturb();
			
			if(lookingForward && --forwardCounter == 0) {
				lookingForward = false;
				forwardCounter = forwardOutTimes;
			}
			
			while(!done) {
				if(d > 0) d += incrE;
				else {
					d += incrNE;
					dy = 1;
				}
				if(section > 3) {temp = dx; dx = dy; dy = temp; temp = section - 4;}
				else temp = section;
				switch(temp) {
					case 0:	break;
					case 1: dx *= -1; break;
					case 2: dy *= -1; break;
					case 3: dx *= -1; dy *= -1; break;
				}
				now.dx(dx);
				now.dy(dy);
				if(passable()) {
					last.set(now);
					done = true;
				} else {
					now.set(last);
					initDirection();
				}
			}
		}
	}
	
	/** 随机选择方向 */
	private void initDirection() {
		section = rand.nextInt(8);
		slope = rand.nextDouble();
		d = (int)(100 * (1 - 2 * slope));
		incrE = (int)(-200 * slope);
		incrNE = (int)(200 * (1 - slope));
	}
	
	/** 前向参考 */
	private void lookForward() {
		lookingForward = true;
		if(minTourLength == currentEnvironment.initLength) return;
		int sec = 0;	// 象限区域
		// 向前查找长度
		int index = tourLength + forwardMinLength + rand.nextInt(forwardMaxLength - forwardMinLength);
		if(index >= minTourLength) index = minTourLength - 1;
		Position p = minTour.get(index);	// 已知最短路径前方位置
		int dx = p.x - now.x;
		int dy = p.y - now.y;
		
		if(dx < 0) {
			sec++;
			dx = Math.abs(dx);
		}
		if(dy < 0) {
			sec += 2;
			dy = Math.abs(dy);
		}
		if(dx < dy) {
			sec += 4;
			int temp = dx;
			dx = dy; dy = temp;
		}
		d = dx - 2 * dy;
		incrE = -2 * dy;
		incrNE = 2 * (dx - dy);
	}
	
	/** 可通过性判断 */
	private boolean passable() {
		boolean t = !now.equals(last) && now.x >= 0 && now.x < Map.WIDTH && now.y >= 0 && now.y < Map.HEIGHT && currentEnvironment.terranAt(now) != Map.BLOCKED;
		return t;
	}
	
	/** 随机扰动 */
	private void disturb() {
		slope += -1 * rand.nextInt(2) * rand.nextDouble() / 2;
		if(slope > 1) slope = 1;
		if(slope < 0) slope = 0;
		d = (int)(100 * (1 - 2 * slope));
		incrE = (int)(-200 * slope);
		incrNE = (int)(200 * (1 - slope));
	}
	
	/** 生成直线路径 */
	private static PosList genLine(Position from, Position to) {
		int dx = to.x - from.x;
		int dy = to.y - from.y;
		int temp;	// 用于坐标交换
		int sec = 0;
		int d, incrE, incrNE;
		Position t = new Position(from);
		PosList list = new PosList();
		
		// 区域判断
		if(dx < 0) {
			sec++;
			dx = Math.abs(dx);
		}
		if(dy < 0) {
			sec += 2;
			dy = Math.abs(dy);
		}
		if(dx < dy) {
			sec += 4;
			temp = dx;
			dx = dy; dy = temp;
		}
		// 计算增量
		d = dx - 2 * dy;
		incrE = -2 * dy;
		incrNE = 2 * (dx - dy);
		
		// 判断障碍
		while(!t.equals(to)) {
			
			if(currentEnvironment.terranAt(t) == Map.BLOCKED) {
				list.clear();
				break;
			}
			
			list.add(new Position(t));
			
			if(d > 0) {
				d += incrE;
				dy = 0;
			}
			else {
				d += incrNE;
				dy = 1;
			}
			dx = 1;
			// 对称变换
			if(sec > 3) {temp = dx; dx = dy; dy = temp;}
			
			switch(sec - 4) {
				case 0:	break;
				case 1: dx *= -1; break;
				case 2: dy *= -1; break;
				case 3: dx *= -1; dy *= -1; break;
			}
			
			t.dx(dx);
			t.dy(dy);
		}
		return list;
	}
}
