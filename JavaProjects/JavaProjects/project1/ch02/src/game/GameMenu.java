/*
 * Created on 2006-4-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package game;

import game.gameset.GameClienSocketSet;
import game.gameset.GameControlkeySet;
import game.gameset.GameExitSet;
import game.gameset.GameHardSet;
import game.gameset.GameServerSocketSet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GameMenu extends JFrame implements ActionListener {
    private JMenuBar bar = new JMenuBar ();

    //�˵�������4���˵�
    private JMenu mGame = new JMenu ("��Ϸ");

    private JMenu mControl = new JMenu ("����");

    private JMenu mInfo = new JMenu ("����");

    //4���˵��зֱ�����Ĳ˵���
    protected  JMenuItem miNewGame = new JMenuItem ("��������Ϸ");

    protected  JMenuItem miConnectGame = new JMenuItem ("������Ϸ");

    protected JMenuItem miExit = new JMenuItem ("�˳�");

    protected JMenuItem miHardset = new JMenuItem ("��Ϸ�Ѷ�����");

    protected JMenuItem miControlkey = new JMenuItem ("���Ƽ�����");

    protected JMenuItem miAuthor = new JMenuItem ("���� : Java��Ϸ�����");

    protected JMenuItem miSourceInfo = new JMenuItem ("�汾��1.0");

    /**
     * ���潨����SOCKET
     */
    private Socket socket ;

    private ServerSocket server = null;

    private int  portNum = 8081;

    private String address =null;
    

    /**
     * �����趨����Ϸ�Ѷ�
     */
    private  int gamehard = 0005;

    /**
     * �����趨����Ϸ���Ͽ��Ƽ�
     */

    private  int up = KeyEvent.VK_UP;

    /**
     * �����趨����Ϸ���¿��Ƽ�
     */
    private int down = KeyEvent.VK_DOWN;

    /**
     * �����趨����Ϸ������Ƽ�
     */
    private int left = KeyEvent.VK_LEFT;

    /**
     * �����趨����Ϸ���ҿ��Ƽ�
     */
    private int right = KeyEvent.VK_RIGHT;

    public GameMenu (String s) {
        super (s);
        bar.add (mGame);
        bar.add (mControl);
        bar.add (mInfo);

        mGame.add (miNewGame);
        miNewGame.addActionListener (this);
       
        mGame.addSeparator ();

        mGame.add (miConnectGame);
        miConnectGame.addActionListener (this);

        mGame.addSeparator ();

        mGame.add (miExit);
        miExit.addActionListener (this);

        mControl.add (miControlkey);
        miControlkey.addActionListener (this);
        mControl.addSeparator ();

        mControl.add (miHardset);
        miHardset.addActionListener (this);
        
        mInfo.add (miAuthor);
        miAuthor.addActionListener (this);
        mInfo.addSeparator ();

        mInfo.add (miSourceInfo);
        miSourceInfo.addActionListener (this);

        setJMenuBar (bar);
        show (true);
        addWindowListener (new WindowAdapter () {
            public void windowClosing (WindowEvent e) {
                System.exit (1);
            }
        });
    }

   

    public void actionPerformed (ActionEvent e) {
        if (e.getSource () == miNewGame) {
            GameServerSocketSet newgameset = new GameServerSocketSet (this);
            portNum = newgameset.getportNum ();
            server = newgameset.getServerSocket ();
        }else
        if (e.getSource () == miConnectGame) {       	
            GameClienSocketSet newgameset = new GameClienSocketSet (this);
            portNum = newgameset.getPortNum ();
            address = newgameset.getAddr ();
        }else
        if (e.getSource () == miExit) {
            GameExitSet gameexit = new GameExitSet (this);
           }else
        if (e.getSource () == miControlkey) {
            GameControlkeySet keycontrol = new GameControlkeySet (this);
            // ȡ��ԭ���ÿ��Ƽ���ֵ
            up = keycontrol.getUP ();
            down = keycontrol.getDOWN ();
            left = keycontrol.getLEFT ();
            right = keycontrol.getRIGHT ();

        }else
        if (e.getSource () == miHardset) {
            GameHardSet gamehardset = new GameHardSet (this);
            gamehard = gamehardset.getgameHard ();
        }else
        if (e.getSource () == miAuthor) {

        }else
        if (e.getSource () == miSourceInfo) {

        }
    } 

    public int getGameHard () {
        return gamehard;

    }

   

    public int getPortNum () {
        return portNum;
    }

    public int getDown () {
        return down;
    }

    public int getLeft () {
        return left;
    }

    public int getRight () {
        return right;
    }

    public int getUp () {
        return up;
    }
    
    

    public JMenu getMControl() {
		return mControl;
	}

	public JMenu getMGame() {
		return mGame;
	}

	public JMenu getMInfo() {
		return mInfo;
	}
    
	public JMenuItem getMiAuthor() {
		return miAuthor;
	}

	public JMenuItem getMiConnectGame() {
		return miConnectGame;
	}

	public JMenuItem getMiControlkey() {
		return miControlkey;
	}

	public JMenuItem getMiExit() {
		return miExit;
	}

	public JMenuItem getMiHardset() {
		return miHardset;
	}

	public JMenuItem getMiNewGame() {
		return miNewGame;
	}

	public JMenuItem getMiSourceInfo() {
		return miSourceInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

}
