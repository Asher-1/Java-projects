package cn.music.MP3;

import java.awt.Label;
import java.awt.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

public class PlayThread extends Thread {

	boolean isStop = true;// ���Ʋ����߳�
	boolean hasStop = true;// �����߳�״̬
	String filepath;// �����ļ�Ŀ¼
	String filename;// �����ļ�����
	AudioInputStream audioInputStream;// �ļ���
	AudioFormat audioFormat;// �ļ���ʽ
	SourceDataLine sourceDataLine;// ����豸
	List list;// �ļ��б�
	Label labelfilepath;// ����Ŀ¼��ʾ��ǩ
	Label labelfilename;// �����ļ���ʾ��ǩ
	byte tempBuffer[] = new byte[320];

	public void run() {
		try {
			int cnt;
			hasStop = false;
			// ��ȡ���ݵ���������
			while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if (isStop)
					break;
				if (cnt > 0) {
					// д�뻺������
					sourceDataLine.write(tempBuffer, 0, cnt);
				}
			}
			// Block�ȴ���ʱ���ݱ����Ϊ��
			sourceDataLine.drain();
			sourceDataLine.close();
			hasStop = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
