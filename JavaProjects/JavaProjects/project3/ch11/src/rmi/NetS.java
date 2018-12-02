//NetS.java
package rmi;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
/**
 * 该类创建远程对象，并绑定到指定的名称上，等待客户机的访问。
 */
public class NetS extends UnicastRemoteObject implements MatrixServer{
	public NetS () throws RemoteException{
		super();
	}
	
	/**
	 * 对指定的任务进行计算
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
		//根据参数，取得端口号
		String sPort;
		if(args.length > 0){
			sPort = args[0];
		}else{
			sPort = Constants.DEFAULT_PORT;
		}
		try{
			int iPort = Integer.parseInt(sPort);
			//启动RMI注册表
			LocateRegistry.createRegistry(iPort);
			//创建远程对象
			MatrixServer obj = new NetS();
			//生成绑定名称
			String sRebind = "rmi://localhost:" + sPort + "/" + 
							Constants.SERVICE_NAME;
			//将指定名称绑定到远程对象上
			Naming.rebind(sRebind, obj);
			
			System.out.println(sRebind);
			System.out.println("Ready to receive tasks.");
		}catch(Exception e){
			e.printStackTrace();
			System.exit(3);
		}
	}		
}
