package ant;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;

import util.PosList;
import util.Position;
import util.PosList.PosListIterator;

/** 地图类，
 * 包含地形信息
 * @author Leaf
 */
public class Map {
	/** 地图宽度 */
	final static int WIDTH = 300;
	/** 地图高度 */
	final static int HEIGHT = 300;	//地图尺寸
	/** 窝位置 */
	Position nest;
	/** 食物位置 */
	Position food;
	/** 地形阵列 */
	char[][] grid;
	
	final static char BLOCKED = 'B', DEST = 'D', START = 'S'; 
	
	public Map() {
		grid = new char[WIDTH][HEIGHT];
		addBorder();
	}
	
	private void addBorder() {
		int x = 0;
		while(x < WIDTH) {
			grid[x][0] = BLOCKED;
			grid[x][HEIGHT - 1] = BLOCKED;
			x++;
		}
		int y = 0;
		while(y < HEIGHT) {
			grid[0][y] = BLOCKED;
			grid[WIDTH - 1][y] = BLOCKED;
			y++;
		}
	}
	
	public void setDest(Position p) {
		grid[p.x][p.y] = DEST;
		food = p;
	}
	
	public void setStart(Position p) {
		grid[p.x][p.y] = START;
		nest = p;
	}
	
	public void draw(GC gc) {
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				if(grid[x][y] == BLOCKED) gc.drawPoint(x, y);
			}
		}
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_MAGENTA));
		gc.drawPoint(nest.x, nest.y);
//		if(nest.x > 1) gc.drawPoint(nest.x - 1, nest.y);
//		if(nest.x < WIDTH - 2) gc.drawPoint(nest.x + 1, nest.y);
//		if(nest.y > 1) gc.drawPoint(nest.x, nest.y - 1);
//		if(nest.y < HEIGHT - 2) gc.drawPoint(nest.x, nest.y + 1);
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_CYAN));
		gc.drawPoint(food.x, food.y);
	}

	/**
	 * 读取地图
	 * @param name 地图文件名
	 */
	public static Map load(String name) {
		try {
			Map thisMap = null;
			Position p;
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(name));
			PosList list = (PosList)in.readObject();
			Position nest = (Position)in.readObject();
			Position food = (Position)in.readObject();
			thisMap = new Map();
			PosListIterator iter = list.iterator();
			while(iter.hasNext()) {
				p = iter.next();
				thisMap.grid[p.x][p.y] = BLOCKED;
			}
			thisMap.setStart(nest);
			thisMap.setDest(food);
			return thisMap;
		} catch(IOException e) {
			throw new RuntimeException(e);
		} catch(ClassNotFoundException e) {
			System.err.println("Class not found: " + e.toString());
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 储存地图
	 * @param name 地图文件名
	 */
	public void save(String name) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name));
			PosList list = new PosList();
			for(int i = 1; i < WIDTH - 1; i++) {
				for(int j = 1; j < HEIGHT - 1; j++) {
					if(grid[i][j] == BLOCKED) list.add(new Position(i, j));
				}
			}
			out.writeObject(list);
			out.writeObject(nest);
			out.writeObject(food);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
