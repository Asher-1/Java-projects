package chess;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ������������ࡣ��
 *
 * @version 2.0
 * @author baokui
 */
public class Five {

    // ������
    protected Shell shell;

    // ����
    private Canvas canvas;

    // ����������
    protected final Image image = new Image(Display.getDefault(), "res/bk.jpg");

    protected Image whitebow = new Image(Display.getDefault(), "res\\whitebow.bmp");

    protected Image blackbow = new Image(Display.getDefault(), "res\\blackbow.bmp");

    protected final Image five = new Image(Display.getDefault(), "res\\fivee.bmp");

    // ��ǰӦ���µ���ɫ��0����ɫ�� 1����ɫ������
    protected int bw = 0;

    // ��������ɫ�� 0����ɫ��1:��ɫ������
    protected int startbw = -1;

    // ���̵�Ԫ���С
    protected final int size = 27;

    // ��ǰ��֣�0�����ӣ�1������ռ�ݣ�2�����С�
    protected int[][] chess = new int[15][15];

    // ���Ժ���ҵ����͡��� �������Ӻ��������Ӹ����������һά��0��1��2��3��ʾ�ᡢ������б����б�����Ӹ�����
    protected int[][][] computer = new int[15][15][4];

    protected int[][][] player = new int[15][15][4];

    protected boolean win = false;

    protected int win_bw;

    // /////////////////////////////////////////
    // ��� group �����label����
    // /////////////////////////////////////////
    private Group group;

    private Label label_luozi;

    private static Label label_time;

    private Label label_black_time;

    private Label label_white_time;

    private Label label__wait_bw;

    // ��������Ŀ����
    private int chess_num = 0;

    // ���ʱ�䡣����λs
    private int total_time = 0;

    private int white_time = 0;

    private int black_time = 0;

    private Timer time;

    /**
     * ��ָ�����껭ָ����ʽ������
     *
     * @param gc ������ͼ��GC��
     * @param x �����ӵ�x���ꡣ
     * @param y �����ӵ�y���ꡣ
     * @param bw ���廹�Ǻ��壬0�����壬1�����塣
     * @param r ���Ӱ뾶��
     */
    private void draw(GC gc, int x, int y, int bwf, int r) {

        Color wh = new Color(Display.getDefault(), 255, 255, 255);
        Color bk = new Color(Display.getDefault(), 0, 0, 0);
        // if (r == 8)
        // System.out.println(x + ":" + y + "-->" + bwf + " in draw..");
        if (bwf == 0)
            gc.setBackground(bk);
        else
            gc.setBackground(wh);

        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
        wh.dispose();
        bk.dispose();
    }

    /**
     * Launch the application
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Five window = new Five();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Open the window
     */
    public void open() {
        final Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        //�ͷ���Դ����
        dispose();
    }

    /**
     * Create contents of the window
     */
    protected void createContents() {
        // ��ʼ�����ڡ�����
        shell = new Shell(SWT.TITLE | SWT.BORDER | SWT.CLOSE);

        shell.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent pe) {
                shell.setBackground(new Color(Display.getDefault(), 100, 55, 255));
                GC gc = pe.gc;
                gc.drawImage(image, 0, 0);
                gc.drawImage(blackbow, 120, 70);
                gc.drawImage(whitebow, 600, 70);
                // Color
                gc.drawText("version 1.0 @Baokui Yang, Wuhan Univ. \n no copyright reserved, copy it free!! ", 250,
                        480, true);

            }
        });
        shell.setSize(800, 545);
        shell.setText("������--�޽���");

        // ������ͼƬ������
        final CLabel label = new CLabel(shell, SWT.NONE);
        label.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent pe) {
                GC gc = pe.gc;
                gc.drawImage(five, 0, 0);
            }
        });
        label.setText("Label");
        label.setBounds(126, 0, 542, 66);
        // chess table size

        // ��ʼ�����̣� 2����ǰλ�ÿ��У�0������ռ�ݣ�1������ռ�ݡ�����
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                chess[i][j] = 2;

        initCanvas();
        initLeft();
        initRight();
        // ��ʼ��ʱ����������������
        startTime();
    }

    /**
     * ��ʼ���Ҳ� buttons��������
     */
    private void initRight() {

        // ���ֺڰ���ѡ��group
        final Group group_1 = new Group(shell, SWT.NONE);
        group_1.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent pe) {
                GC gc = pe.gc;
                gc.drawImage(image, -620, -155);

            }
        });
        group_1.setBounds(619, 155, 133, 80);

        final Button button_2 = new Button(group_1, SWT.RADIO);
        button_2.setText("��������");
        button_2.setBounds(35, 25, 70, 20);
        // Ĭ�� �������С�������
        button_2.setSelection(true);

        final Button button_3 = new Button(group_1, SWT.RADIO);
        button_3.setText("�������");
        button_3.setBounds(35, 55, 70, 20);

        // ///////////////////////////////////
        // ��ʼ�� �Ҳ� button ��
        // /////////////////////////////////////
        final Button aaButton = new Button(shell, SWT.NONE);
        aaButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent arg0) {

                if (button_2.getSelection())
                    startbw = 0;
                else {
                    startbw = 1;
                    putOne(1 - startbw);
                }
                aaButton.setEnabled(false);
                String str = (bw == 0 ? "����" : "����");
                label__wait_bw.setText("�ȴ� " + str + " ���ӡ�����");
            }
        });
        aaButton.setText("��ʼ");
        aaButton.setBounds(653, 267, 76, 34);

        final Button button = new Button(shell, SWT.NONE);
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent se) {
                startbw = -1;
                win = false;
                for (int i = 0; i < 15; i++)
                    for (int j = 0; j < 15; j++) {
                        chess[i][j] = 2;
                        computer[i][j][0] = computer[i][j][1] = computer[i][j][2] = computer[i][j][3] = 0;
                        player[i][j][0] = player[i][j][1] = player[i][j][2] = player[i][j][3] = 0;
                    }
                // �����shell �ػ棿������
                // shell.update();
                // shell.redraw();
                canvas.setVisible(false);
                canvas.setVisible(true);
                aaButton.setEnabled(false);
                bw = 0;
                chess_num = 0;
                total_time = white_time = black_time = 0;
                if (button_2.getSelection())
                    startbw = 0;
                else {
                    startbw = 1;
                    // ��������У������һ�ӡ�
                    // Խ�����м䣬���ӵĸ���Խ�󡣡�
                    int y, x;
                    x = y = 3;
                    for (int i = 3; i < 12; i++)
                        for (int j = 3; j < 12; j++)
                            if (randomTest(20 * (Math.abs(7 - i) + Math.abs(7 - j)) + 2)) {
                                x = i;
                                y = j;
                            }
                    GC gc = new GC(canvas);
                    draw(gc, x * size + 10, y * size + 10, bw, 8);
                    gc.dispose();
                    chess[x][y] = bw;
                    bw = 1 - bw;
                }

            }
        });
        button.setText("���¿�ʼ");
        button.setBounds(653, 307, 76, 34);

        final Button helpButton = new Button(shell, SWT.NONE);
        helpButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent arg0) {
                new Help().open();
            }
        });
        helpButton.setText("����");
        helpButton.setBounds(653, 347, 76, 34);

        final Button button_1 = new Button(shell, SWT.NONE);
        button_1.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent arg0) {
                dispose();
            }
        });
        button_1.setText("�˳�");
        button_1.setBounds(653, 387, 76, 34);

    }


    /**
     * ��ʼ�����group �е�label������
     */
    private void initLeft() {
        // /////////////////////////////////////////
        // ��ʼ����� group
        // /////////////////////////////////////////
        group = new Group(shell, SWT.NONE);
        group.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent pe) {
                GC gc = pe.gc;
                gc.drawImage(image, -24, -126);
            }
        });
        group.setBounds(26, 126, 133, 206);

        label_luozi = new Label(group, SWT.NONE);
        label_luozi.setText("�����ӣ�");
        label_luozi.setBounds(10, 55, 102, 15);

        label_time = new Label(group, SWT.NONE);
        label_time.setText("�ܺ�ʱ��");
        label_time.setBounds(10, 82, 113, 15);

        label_black_time = new Label(group, SWT.NONE);
        label_black_time.setText("���Ӻ�ʱ��");
        label_black_time.setBounds(10, 110, 113, 15);

        label_white_time = new Label(group, SWT.NONE);
        label_white_time.setText("���Ӻ�ʱ��");
        label_white_time.setBounds(10, 131, 113, 15);

        label__wait_bw = new Label(group, SWT.NONE);
        label__wait_bw.setText("�ȴ� x ����...");
        label__wait_bw.setBounds(10, 31, 102, 15);

    }

    /**
     * ������ʱ������ʼ��ʱ�������� ÿ��һ�봥��һ���¼�������������
     */
    private void startTime() {

        time = new Timer(false);
        // ÿ��һ�����һ�Ρ�������
        time.schedule(new TimerTask() {
            public void run() {
                if (!win && startbw != -1) {
                    total_time++;
                    if (bw == 0)
                        black_time++;
                    else
                        white_time++;

                    // Note:����ͬ��ִ�С��������������쳣��������P
                    Display.getDefault().syncExec(new Runnable() {
                        public void run() {
                            label_time.setText("�ܺ�ʱ�� " + total_time / 60 + "�� " + total_time % 60 + "��");
                            label_black_time.setText("�����ʱ�� " + black_time / 60 + "��" + black_time % 60 + "��");
                            label_white_time.setText("�����ʱ�� " + white_time / 60 + "��" + white_time % 60 + "��");

                        }
                    });
                }
            }
        }, 0, 1000);
    }

    /**
     * ���������Ӧlabel
     */
    private void updateChess() {

        chess_num++;
        label_luozi.setText("�����ӣ� " + chess_num);

        String str = (bw == 0 ? "����" : "����");
        label__wait_bw.setText("�ȴ� " + str + " ���ӡ�����");
    }

    /**
     * ��ʼ��Canvas �����̣��� ��Ϣ������
     */
    private void initCanvas() {
        // //////////////////////////////////////////////////
        // ���̣��������¼���Ӧ
        // //////////////////////////////////////////////////
        canvas = new Canvas(shell, SWT.NONE);
        canvas.addMouseListener(new MouseAdapter() {
            public void mouseDown(final MouseEvent me) {

                // ��û��ʼ||�Ѿ�������������
                if (startbw == -1 || win)
                    return;
                // ����������塣����
                if (startbw != bw)
                    return;
                // �������λ��
                int x = me.x + 13;
                int y = me.y + 14;
                x -= 10;
                x /= size;
                y -= 10;
                y /= size;

                // �����λ���Ѿ��������ӡ�����
                if (chess[x][y] != 2)
                    return;

                // ��&�����ӡ�����
                GC gc = new GC(canvas);
                draw(gc, x * size + 10, y * size + 10, startbw, 8);
                chess_num++;
                label_luozi.setText("�����ӣ� " + chess_num);
                // ����Ƿ�ȡʤ����
                boolean flag = haveWin(x, y, bw);

                chess[x][y] = bw;
                bw = 1 - bw;
                String str = (bw == 0 ? "����" : "����");
                label__wait_bw.setText("�ȴ� " + str + " ���ӡ�����");

                if (flag) {
                    wined(gc, 1 - bw);
                    return;
                }

                gc.dispose();
                putOne(bw);
            }
        });
        canvas.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent pe) {

                canvas.setBackground(new Color(Display.getDefault(), 200, 100, 100));
                GC gc = pe.gc;
                int x, y;
                x = 10;
                y = 10;
                // �����̡���
                Color odd = new Color(Display.getDefault(), 0, 0, 0);
                Color even = new Color(Display.getDefault(), 55, 55, 55);

                // ���С�������
                for (int i = 0; i < 15; i++) {
                    if (i % 2 == 0)
                        gc.setForeground(odd);
                    else
                        gc.setForeground(even);
                    gc.drawLine(i * size + x, 0 + y, i * size + x, 14 * size + y);
                }
                // ���С�������
                for (int i = 0; i < 15; i++) {
                    if (i % 2 == 0)
                        gc.setForeground(odd);
                    else
                        gc.setForeground(even);
                    gc.drawLine(0 + x, i * size + y, 14 * size + x, i * size + y);
                }
                // �����׼�㡣������
                draw(gc, 3 * size + x, 3 * size + y, 0, 3);
                draw(gc, 11 * size + x, 3 * size + y, 0, 3);
                draw(gc, 3 * size + x, 11 * size + y, 0, 3);
                draw(gc, 11 * size + x, 11 * size + y, 0, 3);
                draw(gc, 7 * size + x, 7 * size + y, 0, 3);

                // �����ӡ�����
                for (int i = 0; i < 15; i++)
                    for (int j = 0; j < 15; j++)
                        if (chess[i][j] != 2)
                            draw(gc, i * size + 10, j * size + 10, chess[i][j], 8);

                // �ͷ���ɫ��Դ����
                odd.dispose();
                even.dispose();
                if (win) {
                    wined(gc, win_bw);
                }
            }
        });
        canvas.setBounds(189, 72, 400, 401);
    }


    /**
     * ���㲻ͬ���Ͷ�Ӧ��������
     *
     * @param k ���ʹ���
     * @return ��Ӧ�ķ���
     */
    private int getMark(int k) {
        switch (k) {
        case 1:
            return 100000;
        case 2:
            return 10000;
        case 3:
            return 5000;
        case 4:
            return 1000;
        case 5:
            return 500;
        case 6:
            return 200;
        case 7:
            return 100;
        case 8:
            return 50;
        case 9:
            return 10;
        case 10:
            return 5;
        case 11:
            return 3;
        default:
            return 0;
        }
    }


    /**
     * ������ԣ��Է�����ͬ�����ӵ㣬���ѡȡ����
     *
     * @param kt ������ӡ�����Խ���������м䣬ktֵԽС����ѡȡ�ĸ���Խ��
     * @return �Ƿ�ѡ���λ�á�
     */
    private boolean randomTest(int kt) {
        Random rm = new Random();
        return rm.nextInt() % kt == 0;
    }


    /**
     * ����ָ����λ��������������������Ӹ�����
     *
     * @param x,y �����߻�׼һ�㡣
     * @param ex��ey ָ�����򲽽�������
     * @param k ������ɫ��0����ɫ��1����ɫ
     * @throws Exception if the position x,y is not empty.
     */
    private int[] count(int x, int y, int ex, int ey, int k) {
        int rt = 1;

        if (chess[x][y] != 2) {
            // System.out.println(x + " in count " + y);
            throw new IllegalArgumentException("position x,y must be empty!..");
        }
        int i;
        for (i = 1; x + i * ex < 15 && x + i * ex >= 0 && y + i * ey < 15 && y + i * ey >= 0; i++)
            if (chess[x + i * ex][y + i * ey] == k)
                rt++;
            else
                break;

        int ok = 0;
        if (x + i * ex < 15 && x + i * ex >= 0 && y + i * ey < 15 && y + i * ey >= 0
                && chess[x + i * ex][y + i * ey] == 2)
            ok++;

        for (i = 1; x - i * ex >= 0 && x - i * ex < 15 && y - i * ey >= 0 && y - i * ey < 15; i++)
            if (chess[x - i * ex][y - i * ey] == k)
                rt++;
            else
                break;
        if (x - i * ex < 15 && x - i * ex >= 0 && y - i * ey < 15 && y - i * ey >= 0
                && chess[x - i * ex][y - i * ey] == 2)
            ok++;

        // �÷�����������壬����0
        if (rt < 5 && !makesence(x, y, ex, ey, k))
            return new int[] {0, 1};

        return new int[] {rt, ok};
    }

    /**
     * ������ָ����ӣ���ָ���������Ƿ������壬����ķ����������ɷŵ�λ�û򼺷��ӵĸ�������5�������壬���������塣
     *
     * @param x,y �����Ļ�׼�㡣
     * @param ex,ey ������������
     * @param k ��ɫ��
     * @return true:�����壬 false�������塣
     */
    private boolean makesence(int x, int y, int ex, int ey, int bwf) {

        int rt = 1;
        for (int i = 1; x + i * ex < 15 && x + i * ex >= 0 && y + i * ey < 15 && y + i * ey >= 0 && rt < 5; i++)
            if (chess[x + i * ex][y + i * ey] != 1 - bwf)
                rt++;
            else
                break;

        for (int i = 1; x - i * ex >= 0 && x - i * ex < 15 && y - i * ey >= 0 && y - i * ey < 15 && rt < 5; i++)
            if (chess[x - i * ex][y - i * ey] != 1 - bwf)
                rt++;
            else
                break;
        return (rt >= 5);
    }

    /**
     * ʤ���ѷ֡�������ӡ��Ϣ����
     *
     * @param gc ����draw text ��gc��
     * @param bw ָ��ʤ��������ɫ��0����ɫ��1����ɫ��
     */
    private void wined(GC gc, int bw) {
        win = true;
        win_bw = bw;
        Font font = new Font(Display.getDefault(), "Arial", 27, SWT.BOLD | SWT.ITALIC);
        gc.setFont(font);
        Color color = new Color(Display.getDefault(), 120, 242, 105);
        gc.setForeground(color);
        String str = (bw == 0 ? "����ʤ��" : "����ʤ��");
        str = "�������ߡ�" + str;
        gc.drawText(str, 40, 90, true);
        font.dispose();
        color.dispose();
    }

    /**
     * �ж��Ƿ��Ѿ�Ӯ�ˡ���
     *
     * @param x��y ������ӵ㡣��
     * @param bwf ��ɫ 0:��ɫ�� 1����ɫ��
     * @return ture ���Ӯ�ˡ�
     */
    private boolean haveWin(int x, int y, int bwf) {
        boolean flag = false;

        if (count(x, y, 1, 0, bw)[0] >= 5)
            flag = true;
        if (!flag && count(x, y, 0, 1, bw)[0] >= 5)
            flag = true;
        if (!flag && count(x, y, 1, 0, bw)[0] >= 5)
            flag = true;
        if (!flag && count(x, y, 1, -1, bw)[0] >= 5)
            flag = true;
        if (!flag && count(x, y, 1, 1, bw)[0] >= 5)
            flag = true;
        return flag;
    }

    /**
     * ����ϵͳ��Դ������
     */
    private void dispose() {
        shell.dispose();
        canvas.dispose();
        image.dispose();
        whitebow.dispose();
        blackbow.dispose();
        five.dispose();
        time.cancel();
    }

    /**
     * @param x,y ����λ�á�
     * @param bwf ���廹�ǰ��� 0�����ӣ�1������
     * @return ��Ӧ�����ͣ� ���ʹ����Ӧ���£�
     *
     * <pre>
     *             1����5,
     *             2���ɻ�4������˫��4��������4��3
     *             3����˫��3
     *             4������3��3
     *             5������4
     *             6������3
     *             7����˫��2
     *             8������3
     *             9������2��2
     *             10���ɻ�2
     *             11������2
     *             0: ����
     * </pre>
     */
    protected int getType(int x, int y, int bwf) {

        if (chess[x][y] != 2)
            return -1;

        int[][] types = new int[4][2];

        types[0] = count(x, y, 0, 1, bwf);
        types[1] = count(x, y, 1, 0, bwf);
        types[2] = count(x, y, -1, 1, bwf);
        types[3] = count(x, y, 1, 1, bwf);

        // �ж��Ƿ����ڸ������͡�

        int c5, s4, h4, s3, h3, s2, h2;
        c5 = s4 = h4 = s3 = h3 = s2 = h2 = 0;
        for (int k = 0; k < 4; k++) {
            if (types[k][0] == 5)
                c5++;
            else if (types[k][0] == 4 && types[k][1] == 2)
                h4++;
            else if (types[k][0] == 4 && types[k][1] != 2)
                s4++;
            else if (types[k][0] == 3 && types[k][1] == 2)
                h3++;
            else if (types[k][0] == 3 && types[k][1] != 2)
                s3++;
            else if (types[k][0] == 2 && types[k][1] == 2)
                h2++;
            else if (types[k][0] == 2 && types[k][1] != 2)
                s2++;
            else
                ;
        }
        // �Ƿ���������1
        if (c5 != 0)
            return 1; // ��5
        if (h4 != 0 || s4 >= 2 || s4 != 0 && h3 != 0)
            return 2; // �ɻ�4������˫��4��������4��3
        if (h3 >= 2)
            return 3; // ��˫��3
        if (s3 != 0 && h3 != 0)
            return 4; // ����3��3
        if (s4 != 0)
            return 5;// ����4
        if (h3 != 0)
            return 6;// ����3
        if (h2 >= 2)
            return 7;// ��˫��2
        if (s3 != 0)
            return 8; // ����3
        if (h2 != 0 && s2 != 0)
            return 9; // ����2��2
        if (h2 != 0)
            return 10; // �ɻ�2
        if (s2 != 0)
            return 11; // ����2
        return 0;
    }

    /**
     * �Ե�ǰ������д�֡�
     *
     * @return ��ǰ�������
     */
    protected int evaluate() {
        int rt = 0;
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                if (chess[i][j] == 2) {
                    int type = getType(i, j, 1 - startbw);
                    rt += getMark(type);
                    type = getType(i, j, startbw);
                    rt -= getMark(type);
                }
        // System.out.println("rt.evaluaate: "+rt);
        return rt;
    }

    /**
     * ����һ��������ɫ���ӡ�����
     *
     * @param bwf 0����ɫ��1����ɫ����
     */
    private void putOne(int bwf) {
        int it, jt, mx = -100000000;
        it = jt = -1;
        //
        int[][] bests = getBests(bwf);
        for (int k = 0; k < bests.length; k++) {
            int i = bests[k][0];
            int j = bests[k][1];

            if (getType(i, j, bwf) == 1) {
                it = i;
                jt = j;
                break;
            }
            chess[i][j] = bwf;
            // Ԥ��δ����������
            int t = findMin(-100000000, 100000000, 3);
            chess[i][j] = 2;
            // ������ȣ����ѡȡ��
            if (t > mx || t == mx && randomTest(3 * (Math.abs(7 - i) + Math.abs(7 - j)) + 2)) {
                it = i;
                jt = j;
                mx = t;
            }
        }

        GC gc = new GC(canvas);
        // it = jt = 3;
        this.draw(gc, it * size + 10, jt * size + 10, bwf, 8);
        // ʤ���ѷ֡�������
        if (haveWin(it, jt, bwf)) {
            wined(gc, bwf);
        }
        chess[it][jt] = bwf;
        bw = 1 - bw;
        // ������������Ŀ��������
        updateChess();
        // ���塣����û���µ����ӵ㡣��
        if (chess_num == 15 * 15) {
            win = true;
            Font font = new Font(Display.getDefault(), "Arial", 27, SWT.BOLD | SWT.ITALIC);
            gc.setFont(font);
            Color color = new Color(Display.getDefault(), 120, 242, 105);
            gc.setForeground(color);

            String str = "�����ӵ㡣���壡��";
            gc.drawText(str, 40, 90, true);
            font.dispose();
            color.dispose();
            return;
        }
        gc.dispose();

    }

    /**
     * ������ǰ����״̬����ֵ������
     * @param alpha ���Ƚڵ�õ��ĵ�ǰ��С���ֵ������alpha ��֦
     * @param beta  ���Ƚڵ�õ��ĵ�ǰ�����Сֵ������beta ��֦��
     * @param step  ��Ҫ�����Ĳ���
     * @return ��ǰ������������ֵ
     */
    protected int findMax(int alpha, int beta, int step) {

        int mx = alpha;
        // if(true) return 0;
        if (step == 0) {
            return evaluate();
        }
        for (int i = 3; i < 11; i++)
            for (int j = 3; j < 11; j++)
                if (chess[i][j] == 2) {
                    if (getType(i, j, 1 - startbw) == 1)
                        return 100 * getMark(1);
                    chess[i][j] = 1 - startbw;
                    // System.out.println("error....");
                    int t = findMin(mx, beta, step - 1);
                    chess[i][j] = 2;
                    if (t > mx)
                        mx = t;
                    //beta ��֦������
                    if (mx >= beta) {
                        //System.out.println(i + "," + j);
                        return mx;
                    }
                }
        return mx;
    }
    /**
     * ������ǰ����״̬����ֵ������
     * @param alpha ���Ƚڵ�õ��ĵ�ǰ��С���ֵ������alpha ��֦
     * @param beta  ���Ƚڵ�õ��ĵ�ǰ�����Сֵ������beta ��֦��
     * @param step  ��Ҫ�����Ĳ���
     * @return ��ǰ����������Сֵ��
     */
    protected int findMin(int alpha, int beta, int step) {

        // System.out.println(alpha + ".." + beta + ".." + step);
        int mn = beta;
        if (step == 0) {
            return evaluate();
        }
        int[][] rt = getBests(startbw);
        for (int i = 0; i < rt.length; i++) {
            int ii = rt[i][0];
            int jj = rt[i][1];
            if (getType(ii, jj, startbw) == 1)
                return -100 * getMark(1);
            chess[ii][jj] = startbw;
            int t = findMax(alpha, mn, step - 1);
            chess[ii][jj] = 2;
            // System.out.println(ii + ":" + jj + ".. ij-->");
            if (t < mn)
                mn = t;
            //alpha ��֦������
            if (mn <= alpha) {
                // System.out.println("i:"+ii+" j:"+jj+"-->");
                return mn;
            }
        }
        return mn;
    }

    /**
     * ѡȡ�ֲ����ŵļ������ӵ���Ϊ��һ����չ�Ľڵ㡣��
     * @param bwf ��ɫ�� 0�����壬 1�����塣����
     * @return ѡ�����Ľڵ����ꡣ��
     */
    private int[][] getBests(int bwf) {

        int[][] rt = new int[255][3];
        int n = 0;
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                if (chess[i][j] == 2) {
                    rt[n][0] = i;
                    rt[n][1] = j;
                    rt[n][2] = getMark(getType(i, j, bwf)) + getMark(getType(i, j, 1 - bwf));
                    n++;
                }
        //�Զ�ά�������򡣡�
        Arrays.sort(rt, new ArrComparator());
        // for(int i=0; i<n; i++)
        // System.out.println(rt[i][0]+" : "+rt[i][1]+" : "+rt[i][2]);
        int size = 4 > n? n:4;
        int[][] bests = new int[size][3];
        System.arraycopy(rt, 0, bests, 0, size);
        return bests;
    }
}

/**
 * ���� Comparator
 *
 * @author baoku
 * @version 1.0
 */
class ArrComparator implements Comparator {
    int column = 2;

    int sortOrder = -1; // Ĭ�ϵݼ��򡣡�

    public ArrComparator() {
    }

    public ArrComparator(int cl) {
        column = cl;
    }

    public int compare(Object a, Object b) {
        if (a instanceof int[]) {
            return sortOrder * (((int[]) a)[column] - ((int[]) b)[column]);
        }
        throw new IllegalArgumentException("param a,b must int[].");
    }
}
