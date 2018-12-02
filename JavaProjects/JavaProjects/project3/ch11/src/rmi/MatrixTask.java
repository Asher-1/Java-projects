//MatrixTask.java
package rmi;
/**
 * ���������������������г˷����㡣
 */
public class MatrixTask implements Task {
	public int[][] m_arrMatrixA;
	public int[][] m_arrMatrixB;
	public MatrixTask(int[][] arrA,int[][] arrB){
		m_arrMatrixA = arrA;
		m_arrMatrixB = arrB;
	}
	/**
	 * ������������ˣ�Ȼ�󷵻ؽ��
	 */
	public int[][] execute(){
		int[][] arrResult;
		int iRows_A = m_arrMatrixA.length;
		int iCols_A = m_arrMatrixA[0].length;
		int iRows_B = m_arrMatrixB.length;
		int iCols_B = m_arrMatrixB[0].length;
		arrResult = new int[iRows_A][iCols_B];
		for(int i = 0; i < iRows_A; i++){
			for(int j = 0; j < iCols_B; j++){
				int iSum = 0;
				for(int k = 0; k < iCols_A; k++){
					iSum += m_arrMatrixA[i][k] * m_arrMatrixB[k][j];
				}
				arrResult[i][j] = iSum;	
			}	
		}
		return arrResult; 
	}
	/**
	 * ����Object���toString()������
	 * ������������˵ľ�����ʾ������
	 */
	public String toString(){
		String sRet = "MatrixA is:\n";
		int iRows = m_arrMatrixA.length;
		int iCols = m_arrMatrixA[0].length;
		for(int i = 0; i < iRows; i++){
			for(int j = 0; j < iCols; j++){
				sRet += m_arrMatrixA[i][j] + "\t";
			}
			sRet += "\n";
		}
		sRet += "MatrixB is:\n";
		iRows = m_arrMatrixB.length;
		iCols = m_arrMatrixB[0].length;
		for(int i = 0; i < iRows; i++){
			for(int j = 0; j < iCols; j++){
				sRet += m_arrMatrixB[i][j] + "\t";
			}
			sRet += "\n";
		}
		return sRet;
	}
}	
