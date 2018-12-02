package chessface;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;

/**
 * 聊天信息Panel。Panel上的文本域可以显示用户聊天信息。
 */
public class chatPad extends Panel {
    //创建空的文本域
    public TextArea chatLineArea = new TextArea("", 18, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);

    public chatPad() {
        //设置排版格式
        setLayout(new BorderLayout());
       // 将文本域添加到Panel中心
        add(chatLineArea, BorderLayout.CENTER);
    }

}