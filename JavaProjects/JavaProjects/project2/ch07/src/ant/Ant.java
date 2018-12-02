package ant;

import java.util.Random;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import util.PosList;
import util.Position;
import util.PosList.PosListIterator;

/** �����࣬
 * ����ÿ������������Ϊ
 * @author Leaf
 */
public class Ant {
	/** ��ʶ */
	int id;
	/** ·������ */
	int tourLength;
	/** ȫ�����·������ */
	static int minTourLength;
	/** �ѷ���·�� */
	PosList tour = new PosList();
	/** ��֪���·�� */
	static PosList minTour = new PosList();
	/** ������ */
	private int section;
	private double slope;
	/** �������� */
	private int d, incrE, incrNE;
	/** Ŀǰλ�� */
	Position now;
	/** ��һ��λ�� */
	Position last;
	/** ������ */
	Position from;
	/** Ŀ�ĵ� */
	Position to;
	
	/** ̽������ */
	static double pExplore = 0.05;
	/** ����Ŷ����� */
	static double pDisturb = 0.1;
	/** ǰ��ο����� */
	static double pLookForward = 0.7;
	
	boolean lookingForward = false;
	static int forwardOutTimes;
	int forwardCounter;
	static int forwardMinLength = 20;
	static int forwardMaxLength = 100;
	/** ����������� */
	static Random rand = new Random();

	/** ��ǰ���� */
	static Environment currentEnvironment;
	/** ·���Ż����� */
	public static int optLength;
	
	/** ������ɫ */
	static Color color;
	
	/** ������
	 * @param id ���ϱ��
	 */
	public Ant(int id) {
		this.id = id;
//		currentEnvironment = e;
		from = currentEnvironment.getMap().nest;
		last = new Position(currentEnvironment.getMap().nest);
		now = new Position(currentEnvironment.getMap().nest);		//��ʼλ����Ϊ��
		to = currentEnvironment.getMap().food;	//������->ʳ��
		forwardCounter = forwardOutTimes; 
		initDirection();
	}
	
	/** ��������
	 * @param gc �豸����
	 */
	public void draw(GC gc) {
		//lastColor = gc.getForeground();
		gc.setForeground(gc.getBackground());
		gc.drawPoint(last.x, last.y);
		gc.setForeground(color);
		gc.drawPoint(now.x, now.y);
		//gc.setForeground(old);
	}
	
	/** ������Ϊ */
	public void Proceed() {
		// λ���ж�
		if(now.equals(to)) {
			tour = optimizeTour(tour);
			tourLength = tour.size();
			currentEnvironment.endCycle(id);
			return;
		}
		// ����
		move();
		// ����ͳ����Ϣ
		tour.add(new Position(now));
		tourLength++;
	}
	
	/** �������ϣ�����
	 * ·����·�����ȣ���㣬�յ㣬��ǰλ��
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
		Position prev = list.get(index), curr;	// prev ��Ϊ·����ʼλ��
		while(index + optLength < list.size()) {
			curr = list.get(index + optLength);	// �Ż�Ŀ��λ�ã���prev����optLength
			inter = genLine(prev, curr);
			if(inter.isEmpty()) {	// ʹ��ԭ·��
				for(int i = 0; i < optLength; i++) {
					opt.add(new Position(list.get(index++)));
				}
			} else {	// ʹ���Ż�·��
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
				while(index < list.size()) {	// ���ʣ��Ĳ���optLength����
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
	
	/** �����ƶ��������� */
	private void move() {
		boolean finished = false;
		if(!lookingForward && rand.nextDouble() > pExplore) {
			Position next = new Position(now), temp;
			Pheromone pt, ph;
			PosList avail = new PosList();	// �����ߵ�λ��
			// �������п�����λ��
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
			// �ڿ�����λ����Ѱ����������
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
	
	/** ���ѡ���� */
	private void initDirection() {
		section = rand.nextInt(8);
		slope = rand.nextDouble();
		d = (int)(100 * (1 - 2 * slope));
		incrE = (int)(-200 * slope);
		incrNE = (int)(200 * (1 - slope));
	}
	
	/** ǰ��ο� */
	private void lookForward() {
		lookingForward = true;
		if(minTourLength == currentEnvironment.initLength) return;
		int sec = 0;	// ��������
		// ��ǰ���ҳ���
		int index = tourLength + forwardMinLength + rand.nextInt(forwardMaxLength - forwardMinLength);
		if(index >= minTourLength) index = minTourLength - 1;
		Position p = minTour.get(index);	// ��֪���·��ǰ��λ��
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
	
	/** ��ͨ�����ж� */
	private boolean passable() {
		boolean t = !now.equals(last) && now.x >= 0 && now.x < Map.WIDTH && now.y >= 0 && now.y < Map.HEIGHT && currentEnvironment.terranAt(now) != Map.BLOCKED;
		return t;
	}
	
	/** ����Ŷ� */
	private void disturb() {
		slope += -1 * rand.nextInt(2) * rand.nextDouble() / 2;
		if(slope > 1) slope = 1;
		if(slope < 0) slope = 0;
		d = (int)(100 * (1 - 2 * slope));
		incrE = (int)(-200 * slope);
		incrNE = (int)(200 * (1 - slope));
	}
	
	/** ����ֱ��·�� */
	private static PosList genLine(Position from, Position to) {
		int dx = to.x - from.x;
		int dy = to.y - from.y;
		int temp;	// �������꽻��
		int sec = 0;
		int d, incrE, incrNE;
		Position t = new Position(from);
		PosList list = new PosList();
		
		// �����ж�
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
		// ��������
		d = dx - 2 * dy;
		incrE = -2 * dy;
		incrNE = 2 * (dx - dy);
		
		// �ж��ϰ�
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
			// �ԳƱ任
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
