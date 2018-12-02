//NetC.java
package rmi;
/**
 * 该类负责将矩阵相乘的任务进行划分，
 * 然后到到网络上通过RMI找到服务器进行任务计算，
 * 再将各个子任务的计算结果进行合成。
 */
import java.rmi.*;

public class NetC{
	//初始化矩阵的行数和列数
	private static final int ROWS_A = Constants.ROWS_A;
	private static final int COLUMNS_A = Constants.COLUMNS_A;
	private static final int ROWS_B = Constants.ROWS_B;
	private static final int COLUMNS_B = Constants.COLUMNS_B;
	private static final int ROWS_C = ROWS_A;
	private static final int COLUMNS_C = COLUMNS_B;
	//定义矩阵变量
	private int[][] m_arrMatrixA, m_arrMatrixB, m_arrMatrixC;

	public NetC(){
		m_arrMatrixA = new int[ROWS_A][COLUMNS_A];
		m_arrMatrixB = new int[ROWS_B][COLUMNS_B];
		m_arrMatrixC = new int[ROWS_C][COLUMNS_C];
		//初始化矩阵
		for(int i = 0; i < ROWS_A; i++){
			for(int j = 0; j < COLUMNS_A; j++){
				int iValue = i * COLUMNS_A + j;
				m_arrMatrixA[i][j] = iValue;
			}
		}
		for(int i = 0; i < ROWS_B; i++){
			for(int j = 0; j < COLUMNS_B; j++){
				int iValue = i * COLUMNS_B + j;
				m_arrMatrixB[i][j] = iValue;
			}
		}
	}
	/**
	 * 从矩阵A中分割出一个子矩阵，用于独立的任务进行计算
	 * @param iStartRow，指定要提取的矩阵起始行数 
	 * @param iRows， 指定要提取的总行数
	 * @return 返回一个分割出的子矩阵矩阵，
	 */
	public int[][] getPartsOfMatrixA(int iStartRow, int iRows){
		//产生一个新的子矩阵
		int[][] arrRet = new int[iRows][COLUMNS_A];
		//从父矩阵中拷贝数据
		for(int i = 0; i < iRows; i++){
			for(int j = 0; j < COLUMNS_A; j++){
				arrRet[i][j] = m_arrMatrixA[i + iStartRow][j];
			}
		}
		return arrRet;
	}
	/**
	 * 取得用于计算的矩阵B
	 * @return 矩阵B
	 */
	public int[][] getPartsOfMatrixB(){
		return m_arrMatrixB;
	}
	/**
	 * 将计算后的所得得子矩阵合成一个矩阵
	 * @param arrResult，计算所得的子矩阵
	 * @param iStartRow，该子矩阵在合成矩阵中的起始位置
	 */
	public void copyResult(int[][] arrResult, int iStartRow){
		//取得子矩阵的行数
		int iRows = arrResult.length;
		//从子矩阵中拷贝数据
		for(int i = 0; i < iRows; i++){
			for(int j = 0; j < COLUMNS_C; j++){
				m_arrMatrixC[i + iStartRow][j] = arrResult[i][j];
			}
		}
	}
	/**
	 * 覆盖Object类的toString()方法，
	 * 将计算所得的矩阵转为字符串的形式，用于显示。
	 */
	public String toString(){
		String sRet = "";
		for(int i = 0; i < ROWS_C; i++){
			sRet += "\t";
			for(int j = 0; j < COLUMNS_C; j++){
				sRet += m_arrMatrixC[i][j] + "\t";
			}
			sRet += "\n";
		}
		return sRet;
	}
	
	public static void main(String args[]){
		try  {
			Class.forName("rmi.Constants");
		} catch (Exception e)  {
			e.printStackTrace();
			System.exit(3);
		}
		String sServer1, sPort1, sServer2, sPort2;
		//++取得RMI服务器的参数
		if(args.length < 4){
			if(args.length > 1){
				sServer1 = args[0];
				sPort1 = args[1];
			}else{
				sServer1 = Constants.DEFAULT_SERVER;
				sPort1 = Constants.DEFAULT_PORT;
			}
			sServer2 = Constants.DEFAULT_SERVER;
			sPort2 = Constants.DEFAULT_PORT;
		}else{
			sServer1 = args[0];
			sPort1 = args[1];
			sServer2 = args[2];
			sPort2 = args[3];
		}
		//--取得RMI服务器的参数
		//初始化任务分割的参数
		final int PART1_START = Constants.PART1_START; 
		final int PART1_LEN = Constants.PART1_LEN; 
		final int PART2_START = Constants.PART2_START; 
		final int PART2_LEN = Constants.PART2_LEN;
		//如果矩阵任务分割不合理，则输出错误信息，并退出。
		if(PART1_LEN >= ROWS_A){
			System.out.println("Error, the 1st part of matrix A is too more.");
			System.exit(3);
		}
		
		int[][] arrA, arrB, arrC;
		MyThread threadA, threadB;
		//将任务进行分割
		NetC client = new NetC();
		arrA = client.getPartsOfMatrixA(PART1_START, PART1_LEN);
		arrB = client.getPartsOfMatrixA(PART2_START, PART2_LEN);
		arrC = client.getPartsOfMatrixB();;
		//初始化线程A，寻找服务器，进行计算
		threadA = new MyThread(arrA, arrC, sServer1, sPort1);
		threadA.start();
		//初始化线程B，寻找服务器，进行计算
		threadB = new MyThread(arrB, arrC, sServer2, sPort2);
		threadB.start();
		//++等待任务结束
		int[][] arrResult1 = null;
		int[][] arrResult2 = null;
		boolean bSuccessful = true;
		//等待任务结束，取得计算结果。
		while(bSuccessful && (arrResult1 == null || arrResult2 == null)){
			try{
				//休眠200毫秒，等待计算任务结束
				Thread.sleep(200);
			}catch( InterruptedException e){}
			//如果任务A结束，取得结果
			if(arrResult1 == null && threadA.isFinished()){
				arrResult1 = threadA.getResult();
				//如果结果为空指针，则认为任务失败
				if(arrResult1 == null){
					bSuccessful = false;
				}
			}
			//如果任务B结束，取得结果
			if(arrResult2 == null && threadB.isFinished()){
				arrResult2 = threadB.getResult();
				//如果结果为空指针，则认为任务失败
				if(arrResult2 == null){
					bSuccessful = false;
				}
			}
		}
		//--等待任务结束
		//合成计算的结果，输出
		if(bSuccessful){
			client.copyResult(arrResult1, PART1_START);
			client.copyResult(arrResult2, PART2_START);
			System.out.println("The caculation relust is:");
			System.out.println(client.toString());
		}else{
			System.out.println("Mission failed.");
		}
	}
}

/**
 * 该类扩展线程类，在该类中生成计算机任务，并
 * 在独立的线程中的寻找网络中的服务器，进行任务计算。
 */
class MyThread extends Thread{
	private int[][] m_arrResult = null;
	MatrixServer m_objServer = null;
	String m_sServerName = null;
	String m_sServerPort = null;
	Task m_task = null;
	//这是一个多个线程共享的任务状态标志变量
	private volatile boolean m_bFinished = false;
	
	public MyThread(int[][] arrA, int[][] arrB, String sName, String sPort){
		m_sServerName = sName;
		m_sServerPort = sPort;
		m_task = new MatrixTask(arrA, arrB);
			
	}
	/**
	 * @return  true, 如果任务结束或者任务失败
	 * 			false, 任务正在进行中
	 */
	public boolean isFinished(){
		return m_bFinished;
	}
	/**
	 * @return 计算结果，如果任务计算成功
	 * 			null，如果任务正在进行中，或者任务失败
	 */
	public int[][] getResult(){
		return m_arrResult;
	}
	
	public void run(){		
		try{
			//寻找服务器
			String sLookup = "rmi://" + m_sServerName + ":" +
							m_sServerPort + "/" + Constants.SERVICE_NAME;
			m_objServer = (MatrixServer)Naming.lookup(sLookup);
			//把任务传到服务器端进行计算
			m_arrResult = m_objServer.caculate(m_task);
			//输出计算后的结果
			String sRet = "Find Server \"" + sLookup + "\", \nit's calculated result is:\n";
			int iRows = m_arrResult.length;
			int iCols = m_arrResult[0].length;
			for(int i = 0; i < iRows; i++){
				sRet += "\t";
				for(int j = 0; j < iCols; j++){
					sRet += m_arrResult[i][j] + "\t";
				}
				sRet += "\n";
			}
			System.out.println(sRet);
		}catch(Exception e){
			System.out.println("Thread.run():an exception occurred.");
			e.printStackTrace();
		}
		//设置标志
		m_bFinished = true;
	}
}
