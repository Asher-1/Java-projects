package cn.music.MP3;

import java.awt.Label;
import java.awt.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

public class PlayThread extends Thread {

	boolean isStop = true;// 控制播放线程
	boolean hasStop = true;// 播放线程状态
	String filepath;// 播放文件目录
	String filename;// 播放文件名称
	AudioInputStream audioInputStream;// 文件流
	AudioFormat audioFormat;// 文件格式
	SourceDataLine sourceDataLine;// 输出设备
	List list;// 文件列表
	Label labelfilepath;// 播放目录显示标签
	Label labelfilename;// 播放文件显示标签
	byte tempBuffer[] = new byte[320];

	public void run() {
		try {
			int cnt;
			hasStop = false;
			// 读取数据到缓存数据
			while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if (isStop)
					break;
				if (cnt > 0) {
					// 写入缓存数据
					sourceDataLine.write(tempBuffer, 0, cnt);
				}
			}
			// Block等待临时数据被输出为空
			sourceDataLine.drain();
			sourceDataLine.close();
			hasStop = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
