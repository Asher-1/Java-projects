//NetS.java
package rmi;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
/**
 * ���ഴ��Զ�̶��󣬲��󶨵�ָ���������ϣ��ȴ��ͻ����ķ��ʡ�
 */
public class NetS extends UnicastRemoteObject implements MatrixServer{
	public NetS () throws RemoteException{
		super();
	}
	
	/**
	 * ��ָ����������м���
	 */
	public int[][] caculate(Task task) throws RemoteException{
		System.out.println("Get a task:");
		System.out.println(task);
		return task.execute();
	}
	
	public static void main(String args[])throws Exception{
		try  {
			Class.forName("rmi.Constants");
		} catch (Exception e)  {
			e.printStackTrace();
			System.exit(3);
		}
		//���ݲ�����ȡ�ö˿ں�
		String sPort;
		if(args.length > 0){
			sPort = args[0];
		}else{
			sPort = Constants.DEFAULT_PORT;
		}
		try{
			int iPort = Integer.parseInt(sPort);
			//����RMIע���
			LocateRegistry.createRegistry(iPort);
			//����Զ�̶���
			MatrixServer obj = new NetS();
			//���ɰ�����
			String sRebind = "rmi://localhost:" + sPort + "/" + 
							Constants.SERVICE_NAME;
			//��ָ�����ư󶨵�Զ�̶�����
			Naming.rebind(sRebind, obj);
			
			System.out.println(sRebind);
			System.out.println("Ready to receive tasks.");
		}catch(Exception e){
			e.printStackTrace();
			System.exit(3);
		}
	}		
}
