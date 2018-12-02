package chessface;

import java.awt.*;

/**
 * 用户列表Panel。此Panel维护一个服务器的当前用户列表， 所有的用户名都将显示在列表中。
 */
public class userPad extends Panel {
    //用户列表
    public List userList = new List(10);
    public userPad() {
        //设置布局管理器并添加信息
        setLayout(new BorderLayout());
        for (int i = 0; i < 30; i++) {
            userList.add(i + "." + "当前暂无用户");
        }
        //添加到Panel
        add(userList, BorderLayout.CENTER);
    }
}
