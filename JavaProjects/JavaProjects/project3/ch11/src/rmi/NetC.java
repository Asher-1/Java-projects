//NetC.java
package rmi;
/**
 * ���ฺ�𽫾�����˵�������л��֣�
 * Ȼ�󵽵�������ͨ��RMI�ҵ�����������������㣬
 * �ٽ�����������ļ��������кϳɡ�
 */
import java.rmi.*;

public class NetC{
	//��ʼ�����������������
	private static final int ROWS_A = Constants.ROWS_A;
	private static final int COLUMNS_A = Constants.COLUMNS_A;
	private static final int ROWS_B = Constants.ROWS_B;
	private static final int COLUMNS_B = Constants.COLUMNS_B;
	private static final int ROWS_C = ROWS_A;
	private static final int COLUMNS_C = COLUMNS_B;
	//����������
	private int[][] m_arrMatrixA, m_arrMatrixB, m_arrMatrixC;

	public NetC(){
		m_arrMatrixA = new int[ROWS_A][COLUMNS_A];
		m_arrMatrixB = new int[ROWS_B][COLUMNS_B];
		m_arrMatrixC = new int[ROWS_C][COLUMNS_C];
		//��ʼ������
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
	 * �Ӿ���A�зָ��һ���Ӿ������ڶ�����������м���
	 * @param iStartRow��ָ��Ҫ��ȡ�ľ�����ʼ���� 
	 * @param iRows�� ָ��Ҫ��ȡ��������
	 * @return ����һ���ָ�����Ӿ������
	 */
	public int[][] getPartsOfMatrixA(int iStartRow, int iRows){
		//����һ���µ��Ӿ���
		int[][] arrRet = new int[iRows][COLUMNS_A];
		//�Ӹ������п�������
		for(int i = 0; i < iRows; i++){
			for(int j = 0; j < COLUMNS_A; j++){
				arrRet[i][j] = m_arrMatrixA[i + iStartRow][j];
			}
		}
		return arrRet;
	}
	/**
	 * ȡ�����ڼ���ľ���B
	 * @return ����B
	 */
	public int[][] getPartsOfMatrixB(){
		return m_arrMatrixB;
	}
	/**
	 * �����������õ��Ӿ���ϳ�һ������
	 * @param arrResult���������õ��Ӿ���
	 * @param iStartRow�����Ӿ����ںϳɾ����е���ʼλ��
	 */
	public void copyResult(int[][] arrResult, int iStartRow){
		//ȡ���Ӿ��������
		int iRows = arrResult.length;
		//���Ӿ����п�������
		for(int i = 0; i < iRows; i++){
			for(int j = 0; j < COLUMNS_C; j++){
				m_arrMatrixC[i + iStartRow][j] = arrResult[i][j];
			}
		}
	}
	/**
	 * ����Object���toString()������
	 * ���������õľ���תΪ�ַ�������ʽ��������ʾ��
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
		//++ȡ��RMI�������Ĳ���
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
		//--ȡ��RMI�������Ĳ���
		//��ʼ������ָ�Ĳ���
		final int PART1_START = Constants.PART1_START; 
		final int PART1_LEN = Constants.PART1_LEN; 
		final int PART2_START = Constants.PART2_START; 
		final int PART2_LEN = Constants.PART2_LEN;
		//�����������ָ���������������Ϣ�����˳���
		if(PART1_LEN >= ROWS_A){
			System.out.println("Error, the 1st part of matrix A is too more.");
			System.exit(3);
		}
		
		int[][] arrA, arrB, arrC;
		MyThread threadA, threadB;
		//��������зָ�
		NetC client = new NetC();
		arrA = client.getPartsOfMatrixA(PART1_START, PART1_LEN);
		arrB = client.getPartsOfMatrixA(PART2_START, PART2_LEN);
		arrC = client.getPartsOfMatrixB();;
		//��ʼ���߳�A��Ѱ�ҷ����������м���
		threadA = new MyThread(arrA, arrC, sServer1, sPort1);
		threadA.start();
		//��ʼ���߳�B��Ѱ�ҷ����������м���
		threadB = new MyThread(arrB, arrC, sServer2, sPort2);
		threadB.start();
		//++�ȴ��������
		int[][] arrResult1 = null;
		int[][] arrResult2 = null;
		boolean bSuccessful = true;
		//�ȴ����������ȡ�ü�������
		while(bSuccessful && (arrResult1 == null || arrResult2 == null)){
			try{
				//����200���룬�ȴ������������
				Thread.sleep(200);
			}catch( InterruptedException e){}
			//�������A������ȡ�ý��
			if(arrResult1 == null && threadA.isFinished()){
				arrResult1 = threadA.getResult();
				//������Ϊ��ָ�룬����Ϊ����ʧ��
				if(arrResult1 == null){
					bSuccessful = false;
				}
			}
			//�������B������ȡ�ý��
			if(arrResult2 == null && threadB.isFinished()){
				arrResult2 = threadB.getResult();
				//������Ϊ��ָ�룬����Ϊ����ʧ��
				if(arrResult2 == null){
					bSuccessful = false;
				}
			}
		}
		//--�ȴ��������
		//�ϳɼ���Ľ�������
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
 * ������չ�߳��࣬�ڸ��������ɼ�������񣬲�
 * �ڶ������߳��е�Ѱ�������еķ�����������������㡣
 */
class MyThread extends Thread{
	private int[][] m_arrResult = null;
	MatrixServer m_objServer = null;
	String m_sServerName = null;
	String m_sServerPort = null;
	Task m_task = null;
	//����һ������̹߳��������״̬��־����
	private volatile boolean m_bFinished = false;
	
	public MyThread(int[][] arrA, int[][] arrB, String sName, String sPort){
		m_sServerName = sName;
		m_sServerPort = sPort;
		m_task = new MatrixTask(arrA, arrB);
			
	}
	/**
	 * @return  true, ������������������ʧ��
	 * 			false, �������ڽ�����
	 */
	public boolean isFinished(){
		return m_bFinished;
	}
	/**
	 * @return ������������������ɹ�
	 * 			null������������ڽ����У���������ʧ��
	 */
	public int[][] getResult(){
		return m_arrResult;
	}
	
	public void run(){		
		try{
			//Ѱ�ҷ�����
			String sLookup = "rmi://" + m_sServerName + ":" +
							m_sServerPort + "/" + Constants.SERVICE_NAME;
			m_objServer = (MatrixServer)Naming.lookup(sLookup);
			//�����񴫵��������˽��м���
			m_arrResult = m_objServer.caculate(m_task);
			//��������Ľ��
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
		//���ñ�־
		m_bFinished = true;
	}
}
