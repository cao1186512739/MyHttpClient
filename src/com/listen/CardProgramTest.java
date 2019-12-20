package com.listen;

import java.io.InputStream;

import org.junit.Test;

import com.listen.api.ConUtil;
import com.listen.model.CardConfig;

/**
 * 一、下发节目
 * 1.运行getDevice()方法获取设备列表信息,只有设备取得了设备信息才能运行下一步的方法;
 * 2.在运行本类之前，需要先把节目格式数据填写到节目模板文本中,如src目录下的html_demo_5.txt、picture_demo_2、video_demo_1.txt等demo文件;
 * 3.设置getProgramContent()函数中，需要下发的节目数据及设备编号，即第2步中已编辑好的节目文件，比如现在函数中设置的是下发图片节目文件picture_demo_2.txt与设备编号QC201905170003;
 * 4.运行sendProgram()方法发送节目
 * 
 * 二、下发指令
 * 1.运行getDevice()方法获取设备列表信息,只有设备取得了设备信息才能运行下一步的方法;
 * 2.在运行本类之前，需要先把指令格式数据赋值到sendTask函数的变量content,如即时开屏指令，设置content="";定时开屏指令设置为content="[{\"time\":\"04:00\",\"isSwitchFlag\":\"1\"},{\"time\":\"06:00\",\"isSwitchFlag\":\"1\"},{\"time\":\"15:30\",\"isSwitchFlag\":\"1\"}]";
 * 3.设置sendTask函数中，需要下发的指令任务类型及设备编号，即开屏任务类型type=10及设备编号QC201905170003;
 * 4.运行sendTask()发送指令任务
 * 
 * 三、下发局部刷新文本节目
 * 1.运行getDevice()方法获取设备列表信息,只有设备取得了设备信息才能运行下一步的方法;
 * 2.在运行本类之前，需要先把节目格式数据填写到节目模板文本中,如src目录下的text-refresh-program-template.txt文件;
 * 3.设置getCardContent()函数中，需要下发的节目数据，即第2步中已编辑好的节目文件，比如现在函数中设置的是下发局部刷新文本节目文件text-refresh-program-template.txt;
 * 4.在函数updateInfo中，设置要下发的设备编号，如目前函数中设置的值为QC201905170003;
 * 5.运行updateInfo()方法发送节目;
 * 
 */
public class CardProgramTest {
	/**
	 * 获取programData文件的内容
	 * @return
	 */
	private static String getProgramContent() {
		InputStream in = null;
		StringBuffer sb = new StringBuffer();
		try {
			in = CardProgramTest.class.getClassLoader().getResourceAsStream("picture_demo_2.txt");
			byte[] buff = new byte[1024 * 10];
			int len = 0;
			while ((len = in.read(buff)) != -1) {
				sb.append(new String(buff, 0, len));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return sb.toString().replace("\r", "").replace("\n", "").replace("\t", "");
	}
	/**
	 * 获取cardData文件的内容
	 * @return
	 */
	private static String getCardContent() {
		InputStream in = null;
		StringBuffer sb = new StringBuffer();
		try {
			in = CardProgramTest.class.getClassLoader().getResourceAsStream("text-refresh-program-template.txt");
			byte[] buff = new byte[1024 * 10];
			int len = 0;
			while ((len = in.read(buff)) != -1) {
				sb.append(new String(buff, 0, len));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return sb.toString().replace("\r", "").replace("\n", "").replace("\t", "");
	}

	/**
	 * 获取设备列表信息
	 */
	@Test
	public void getDevice() {
		//接口路径，必须更改成有效的地址
		String url = "http://182.61.39.40:9999/ListenSdkService/sdk/getDeviceList.php";

		String response = ConUtil.getDevList(url);
		System.out.println(response);
	}

	/**
	 * 发送节目
	 */
	@Test
	public void sendProgram() {
		//接口路径，必须更改成有效的地址
		String url = "http://192.168.1.109:8080/ListenSdkService/sdk/lsPublishProgram.fgl";

//		String url = "http://116.62.172.55:8090/ListenSdkService/sdk/lsPublishProgram.fgl";

		//设备Mac(第二步获取的设备信息中取)
		String devCode = "QC201905170003";

		//需要发送的节目格式内容(必须按programData模板本文的格式填写)
		String content = getProgramContent();
		System.out.println(content);
		String response = ConUtil.sendProgram(url, devCode,content);
		System.out.println(response);
	}

	/**
	 * 发送指令任务
	 */
	@Test
	public void sendTask() {
		//接口路径，必须更改成有效的地址
		String url = "http://192.168.1.109:8080/ListenSdkService/sdk/lsPublishTask.fgl";

		//设备编号(第二步获取的设备信息中取)
		String devCode = "QC201905170003";

		//需要发送的节目格式内容(必须按programData模板本文的格式填写)
		String content = "";//getProgramContent();

		//指令任务类型
		String taskType = "40";

		String response = ConUtil.sendTask(url, devCode,taskType,content);
		System.out.println(response);
	}

	/**
	 * 更新数据
	 */
	@Test
	public void updateInfo() {
		//接口路径，必须更改成有效的地址
		String url = "http://192.168.1.109:8080/ListenSdkService/sdk/updateData.fgl";

		CardConfig cardConfig = new CardConfig();

		//需要更新的内容(必须按cardData模板本文的格式填写)
		cardConfig.setDataInfo(getCardContent());

		//设备编码(第二步获取的设备信息中取)
		cardConfig.setDevCode("QC201905170003");

		//发送内容类型(1.表格数据)
		cardConfig.setDevDataType("1");

		//设备型号(如Q5，X3,X3M等)
		cardConfig.setDevType("Q5");

		String response = ConUtil.updateInfo(url, cardConfig);
		System.out.println(response);
	}
	
	/**
	 * 地图诱导屏绑定设备和图片
	 */
	@Test
	public void bindDeviceAndImage(){
		//接口路径，必须更改成有效的地址
		String url = "http://127.0.0.1:8080/ListenSdkService/sdk/addMapDevice.fgl";

		//设备编号(第二步获取的设备信息中取)
		String devCode = "QC201905170003";

		//地图生成诱导屏显示图片的名称(不带.jpg等后缀)
		String imageName = "efa32637-6fee-4fc9-99d4-3bd9929f";

		String response = ConUtil.bindDeviceAndImage(url, devCode, imageName);
		System.out.println(response);
	}

	/**
	 * 地图诱导屏设备绑定图片信息查询
	 */
	@Test
	public void getDeviceAndImage(){
		//接口路径，必须更改成有效的地址
		String url = "http://127.0.0.1:8080/ListenSdkService/sdk/getMapDeviceList.php";
		
		String response = ConUtil.getDeviceAndImage(url);
		System.out.println(response);
	}
}
