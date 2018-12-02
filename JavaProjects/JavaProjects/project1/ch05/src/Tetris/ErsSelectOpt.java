/**
 * File: ErsSelectOpt.java
 * User: ������
 * Date: 2006.11.8
 * Describe: ����˹����� Java ʵ��
 */

package Tetris;
/**
 * ѡ�������߷��ࡣ
 * ��Ҫ������������Ķ���˹�������һ������˹����
 * ȷ����ǰ����˹�������ת״̬������λ�á�
 */
public class ErsSelectOpt {
	
	private int curType;
	private int nextType;
	private int rotType;
	private int leftPos;
	private int putY = 0;
	private int HEIGHT = 20;
	private int WIDTH = 10;
	private int BLOCKTYPE = 7;
	private int ROTTYPE = 4;
	private int[] gHeight = new int[WIDTH];
	public int[][] gState = new int[HEIGHT][WIDTH];
	private int[][] gOriState = new int[HEIGHT][WIDTH];
	private int[][] gTmpState = new int[HEIGHT][WIDTH];
	
	public final static int[] blockTypeNum = 
    {1, 2, 4, 4, 4, 2, 2};
	
	public final static int[][] blockHeight = 
	{
		{2},
		{1, 4},
		{2, 3, 2, 3},
		{2, 3, 2, 3},
		{2, 3, 2, 3},
		{2, 4},
		{2, 4}
	};
	
	public final static int[][] dy =
	{
		{0},
		{0, 0},
		{0, 0, -1, -1},
		{-1, 0, 0, 0},
		{0, -2, 0, 0},
		{-1, 0},
		{0, -1}
	};
	
	public final static int[][] blockWidth = 
	{
		{2},
		{4, 1},
		{3, 2, 3, 2},
		{3, 2, 3, 2},
		{3, 2, 3, 2},
		{4, 2},
		{4, 2}
	};
	
	public final static int[][][][] blockType= 
	{
		{
			{
				{0, 0, 0, 0}, 
				{0, 0, 0, 0}, 
				{1, 1, 0, 0}, 
				{1, 1, 0, 0}
			},
		},		//1
		{
			{
				{0, 0, 0, 0}, 
				{0, 0, 0, 0}, 
				{0, 0, 0, 0}, 
				{1, 1, 1, 1}
			},
			{
				{1, 0, 0, 0}, 
				{1, 0, 0, 0}, 
				{1, 0, 0, 0}, 
				{1, 0, 0, 0}
			}
			
		},		//2
		{
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{1, 1, 1, 0},
			},
			{
				{0, 0, 0, 0},
				{1, 0, 0, 0},
				{1, 1, 0, 0},
				{1, 0, 0, 0},
			},
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 0},
				{0, 1, 0, 0},
			},
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 0, 0},
			}
		},		//3
		{
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 0},
				{0, 0, 1, 0},
			},
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0},
				{1, 1, 0, 0},
			},
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 0, 0, 0},
				{1, 1, 1, 0},
			},
			{
				{0, 0, 0, 0},
				{1, 1, 0, 0},
				{1, 0, 0, 0},
				{1, 0, 0, 0},
			}
		},		//4
		{
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 1, 0},
				{1, 0, 0, 0},
			},
			{
				{0, 0, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 0, 0},
				{0, 1, 0, 0},
			},
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 1, 0},
				{1, 1, 1, 0},
			},
			{
				{0, 0, 0, 0},
				{1, 0, 0, 0},
				{1, 0, 0, 0},
				{1, 1, 0, 0},
			}
		},		//5
		{
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 1, 0},
			},
			{
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{1, 1, 0, 0},
				{1, 0, 0, 0},
			}
		},		//6
		{
			{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{1, 1, 0, 0},
			},
			{
				{0, 0, 0, 0},
				{1, 0, 0, 0},
				{1, 1, 0, 0},
				{0, 1, 0, 0},
			}
		}	//7
	};
	
	/**
	 * ѡ�������߷��๹�캯����
	 * ��Ҫ��״̬��ά����gState�ʹ洢ÿһ�еĸ߶�����gHeight
	 * ���г�ʼ����
	 */
	public ErsSelectOpt(){
		int i;
		int j;
		for(i = 0; i < HEIGHT; i++){
			for(j = 0; j < WIDTH; j++){
				gState[i][j] = 0;
			}
		}
		for(i = 0; i < WIDTH; i++){
			gHeight[i] = 0;
		}
	}
	
	/**
	 * ѡ�������߷��๹�캯����
	 * @param curType int, ��ǰ�����������˹��������
	 * @param nextType int, ��һ������˹����ķ�������
	 */
	public ErsSelectOpt(int curType, int nextType){
		this();
		this.curType = curType;
		this.nextType = nextType;
	}
	
	/**
	 * ���õ�ǰ����˹�������һ������˹��������
	 * @param curType int, ��ǰ�����������˹��������
	 * @param nextType int, ��һ������˹����ķ�������
	 */
	public void setBlockType(int curType, int nextType){
		this.curType = curType;
		this.nextType = nextType;
	}
	
	/**
	 * ȡ�ĵ�ǰ����˹�������ת״̬
	 */
	public int getRotType(){
		return rotType;
	}
	
	/**
	 * ȡ�õ�ǰ����˹����ķ���λ��
	 */
	public int getLeftPos(){
		return leftPos;
	}
	
	/**
	 * �������ô洢ÿ�и߶ȵ�����gHeight
	 */
	private void resetHeight()
	{
		int i;
		int j;

		for(i = 0; i < WIDTH; i++){
			for(j = HEIGHT-1; j >=0; j--){
				if(gState[j][i] == 1){
					gHeight[i] = j+1;
					break;
				}
			}
			if(j < 0) gHeight[i] = 0;
		}
		return ;
	}
	
	/**
	 * ���ݵ�ǰ������������
	 */
	int delFullRow()
	{
		int i;
		int j;
		int k;
		int l;
		int lines = 0;

		for(j = HEIGHT-1; j >= 0; j--){
			for(i = 0; i < WIDTH; i++){
				if(gState[j][i] == 0) break;
			}
			if(i >= WIDTH){
				lines++;
				if(j == HEIGHT-1){
					for(k = 0; k < WIDTH; k++)
						gState[j][k] = 0;
				}
				else{
					for(l = j; l < HEIGHT-1; l++){
						for(k = 0; k < WIDTH; k++)
							gState[l][k] = gState[l+1][k];
					}
				}
			}
		}

		return lines;
	}
	
	/**
	 * �ж��Ƿ���Է��õ�ǰ�Ķ���˹����
	 * @param x int, ����˹���������ߵ�λ�õĺ�����ֵ
	 * @param deltaY int, �������ϵ�deltaֵ
	 * @param currentType int, ����˹���������
	 * @param rotateType int, ����˹�������ת����
	 * @param height int, ����˹����ĸ߶�
	 * @param width int, ����˹����Ŀ��
	 * @return true, ���Է��õ�ǰ�Ķ���˹����
	 * @return false, �����Է��õ�ǰ�Ķ���˹����
	 */
	private boolean canPlace(int x, 
							 int deltaY, 
							 int currentType, 
			                 int rotateType,  
			                 int height, 
			                 int width)
	{
		int i;
		int j;
		int k;
		int max = -1;
		boolean flag = false;

		putY = 0;

		if(x+width-1 >= WIDTH)
			return false;

		for(i = x; i < x+width; i++){
			if(max < gHeight[i]) max = gHeight[i];
		}

		for(i = max; i >= gHeight[x]+deltaY; i--){
			if(i < 0) 
				break;
			if(i+height > HEIGHT) continue;

			flag = true;

			for(j = 3; j >= 0; j--){
				for(k = 0; k < 4; k++){
					if((blockType[currentType][rotateType][j][k] == 1) 
					&& (gState[i+(3-j)][x+k] == 1)){
						flag = false;
						break;
					}
				}
				if(flag == false) break;
			}
			
			if(flag == false) break;
		}
		
		if(i+height-1 >= HEIGHT){
			return false;
		}
		else if((flag == false) || (i < gHeight[x]+deltaY)){
			if(i+height >= HEIGHT){
				return false;
			}
			putY = i+1;
			return true;
		}
		return false;
	}
	
	/**
	 * ���õ�ǰ�Ķ���˹���鵽��Ϸ��������
	 * @param x int, ����˹������õ�����ߵĺ�����ֵ
	 * @param y int, ����˹������õ����±ߵ�λ�õ�������ֵ
	 * @param currentType int, ����˹���������
	 * @param rotateType int, ����˹�������ת����
	 * @param height int, ����˹����ĸ߶�
	 * @param width int, ����˹����Ŀ��
	 */
	private void placeBlock(int x, 
							int y, 
							int currentType, 
							int rotateType, 
							int height, 
							int width)
	{
		int i;
		int j;
		
		for(i = y; i < y+height; i++){
			for(j = x; j < x+width; j++){
				if(gState[i][j] == 0){
					gState[i][j] = blockType[currentType][rotateType][3-(i-y)][j-x];
				}
			}
		}
		
		return ;
	}
	
	/**
	 * �Ե�ǰ������й���
	 * @param delLines int, �þ�����������ĺ�����Ŀ
	 * @return double, ��ǰ�������ֵ
	 */
	private double evaluateFun(int delLines)
	{
		int i;
		int j;
		int hole = 0;
		int score = 0;
		double eval = 0;
		double aveHeight = 0;

		for(i = 0; i < WIDTH; i++){
			aveHeight += (double)gHeight[i];
		}
		aveHeight /= WIDTH;
		
		for(i = 0; i < WIDTH; i++){
			eval += (gHeight[i]-aveHeight)*(gHeight[i]-aveHeight);
		}
		
		for(i = 0; i < WIDTH; i++){
			for(j = gHeight[i]-1; j>=0; j--) if(gState[j][i] == 0){
				hole++;
			}
		}

		switch(delLines){
			case 1:
				score = 0;
				break;
			case 2:
				score = -30;
				break;
			case 3:
				score = -60;
				break;
			case 4:
				score = -120;
				break;
			default:
				score = 0;
				break;
		}

		return (score*1000000+100000*eval+hole*1200000);
	}
	
	/**
	 * �쿴��ǰ�������
	 * @param state int[][], �洢��ǰ����״̬����
	 */
	public void viewBlock(int state[][])
	{
		int i;
		int j;
		
		for(j = HEIGHT-1; j >= 0; j--){
			for(i = 0; i < WIDTH; i++){
				System.out.printf("%d", state[j][i]);
			}
			System.out.println();
		}
		for(i = 0 ; i < WIDTH; i++){
			System.out.printf("%d ", gHeight[i]);
		}
		System.out.println();
		System.out.println();
		return ;
	}
	
	/**
	 * ����˹����������㷨��
	 * (I)	ö�ٵ�ǰ����˹�������ת�����ö���Ƿ���ϣ������(VI)��������(II)��
	 * (II)	ö�ٵ�ǰ����˹����ķ���λ�ã�ö���Ƿ���ϣ������(I)��������(III)��
	 * (III)ö����һ������˹�������ת�����ö���Ƿ���ϣ������(II)��������(IV)��
	 * (IV)	ö����һ������˹����ķ���λ�ã�ö���Ƿ���ϣ������(III)��������(V)��
	 * (V)	����ö��������õ�ǰ����˹�������һ������˹����õ��µľ��棬�Ըþ�����й��ۣ�
	 *      ��¼������۵ľ��档������(IV)��
	 * (VI)	����������۵ľ���Ķ���˹�������ת����ͷ���λ�öԵ�ǰ�Ķ���˹������з��á�
	 */
	public void selectBest(){
		int i;
		int j;
		int ii;
		int jj;
		int m;
		int n;
		int delLines;
		double max = 1e20;
		double tmpEval;
		int[][] blockStore = new int[HEIGHT][WIDTH];
		
		resetHeight();
		for(i = 0 ; i < WIDTH; i++){
			for(j = 0; j < blockTypeNum[curType]; j++){
		
				if(canPlace(i,  
							dy[curType][j], 
							curType,	
							j,
							blockHeight[curType][j], 
							blockWidth[curType][j]) == true){
					
					for(m = 0; m < HEIGHT; m++){
						for(n = 0; n < WIDTH; n++){
							gOriState[m][n] = gState[m][n];
						}
					}
				
					placeBlock(i, 
						       putY, 
						       curType, 
						       j, 
							   blockHeight[curType][j], 
							   blockWidth[curType][j]);
					
					resetHeight();
					
					for(m = 0; m < HEIGHT; m++){
						for(n = 0; n < WIDTH; n++){
							gTmpState[m][n] = gState[m][n];
						}
					}
					
					for(ii = 0; ii < WIDTH; ii++){
						for(jj = 0; jj < blockTypeNum[nextType]; jj++){
							if(canPlace(ii,
										dy[nextType][jj],
										nextType,
										jj,
										blockHeight[nextType][jj],
										blockWidth[nextType][jj]) == true){
							
								placeBlock(ii, 
								           putY, 
								           nextType,
								           jj,
										   blockHeight[nextType][jj], 
										   blockWidth[nextType][jj]);
									
								delLines = delFullRow();
								resetHeight();
					
								tmpEval = evaluateFun(delLines);
						
								if(tmpEval < max){
									max = tmpEval;
									rotType = j;
									leftPos = i;
									for(m = 0; m < HEIGHT; m++){
										for(n = 0; n < WIDTH; n++){
											blockStore[m][n] = gTmpState[m][n];
										}
									}
								}
								
								for(m = 0; m < HEIGHT; m++){
									for(n = 0; n < WIDTH; n++){
										gState[m][n] = gTmpState[m][n];
									}
								}
								resetHeight();
							}
						}
					} // place next block

					for(m = 0; m < HEIGHT; m++){
						for(n = 0; n < WIDTH; n++){
							gState[m][n] = gOriState[m][n];
						}
					}
					resetHeight();
				}
			}
		} // place current block

		for(m = 0; m < HEIGHT; m++){
			for(n = 0; n < WIDTH; n++){
				gState[m][n] = blockStore[m][n];
			}
		}
		delFullRow();
		resetHeight();
		//viewBlock(gState);
		
		return ;
	}
}
