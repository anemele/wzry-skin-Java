// https://blog.csdn.net/zlbdmm/article/details/105765371
package com.a3e.wzry;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 功能说明：封装http请求：GET/POST/PUT/DELETE等方法
 * 修改说明：
 * 
 * @author 郑立兵
 * @date 2017年10月17日 上午8:42:30
 * @version V0.1
 * @param <T>
 */
public class HttpRequestUtil {
	/**
	 * 定义全局默认编码格式
	 */
	// private static final String CHARSET_NAME = "UTF-8";
	/**
	 * 定义全局OkHttpClient对象
	 */
	private static final OkHttpClient httpClient = new OkHttpClient();

	/**
	 * 功能说明：同步调用
	 * 修改说明：
	 * 
	 * @author zhenglibing
	 * @date 2018年1月8日 上午10:20:55
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static Response execute(Request request) throws IOException {
		return httpClient.newCall(request).execute();
	}

	/**
	 * 功能说明：开启异步线程调用
	 * 修改说明：
	 * 
	 * @author zhenglibing
	 * @date 2018年1月8日 上午10:23:00
	 * @param request
	 * @param responseCallback
	 */
	public static void enqueue(Request request, Callback responseCallback) {
		httpClient.newCall(request).enqueue(responseCallback);
	}

	/**
	 * 功能说明：开启异步线程调用，且不在意返回结果（实现空callback）
	 * 修改说明：
	 * 
	 * @author zhenglibing
	 * @date 2018年1月8日 上午10:24:53
	 * @param request
	 */
	public static void enqueue(Request request) {
		httpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {

			}

		});
	}

	/**
	 * 功能说明：向指定URL发送GET方法的请求
	 * 修改说明：
	 * 
	 * @author zhenglibing
	 * @date 2018年1月8日 上午10:19:11
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL所代表远程资源的响应结果
	 * @throws IOException
	 */
	public static String sendGet(String url, String param) throws IOException {
		String result = "";
		String urlNameString = url + "?" + param;

		Request req = new Request.Builder().url(urlNameString).build();
		Response response = httpClient.newCall(req).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		}
		result = response.body().string();

		return result;
	}

	/**
	 * 功能说明：向指定URL发送GET方法的请求
	 * 修改说明：
	 * 
	 * @author zhenglibing
	 * @date 2018年1月8日 上午10:54:55
	 * @param url      发送请求的URL
	 * @param param    请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param encoding 设置响应信息的编码格式，如utf-8
	 * @return URL所代表远程资源的响应结果
	 * @throws IOException
	 */
	public static String sendGet(String url, String param, String encoding) throws IOException {
		String result = "";
		String urlNameString = url + "?" + param;

		Request req = new Request.Builder().url(urlNameString).build();
		Response response = httpClient.newCall(req).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		}
		result = response.body().string();

		if (null == encoding || encoding.equals("")) {
			return result;
		}
		byte[] bresult = result.getBytes();
		result = new String(bresult, encoding);

		return result;
	}

	/**
	 * 功能说明：下载素材文件
	 * 修改说明：
	 * 
	 * @author zhenglibing
	 * @date 2018年1月9日 下午2:06:56
	 * @param url         下载的接口地址
	 * @param param       参数
	 * @param outFileName 输出文件
	 * @return 成功返回true，失败返回false
	 * @throws IOException
	 */
	public static boolean downloadFile(String url, String param, String outFileName) throws IOException {
		boolean result = false;
		String urlNameString = url + "?" + param;
		Request req = new Request.Builder().url(url).build();
		Response response = httpClient.newCall(req).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		}
		if (response.body().contentType().toString().toLowerCase().contains("application/json")
				|| response.body().contentType().toString().toLowerCase().contains("text/plain")) {
			throw new IOException("下载资源失败,下载地址为=" + urlNameString);
		} else {
			InputStream in = response.body().byteStream();
			FileOutputStream out = new FileOutputStream(outFileName);
			int bufferSize = 2048;
			byte[] data = new byte[bufferSize];
			int length = 0;
			while ((length = in.read(data, 0, bufferSize)) > 0) {
				out.write(data, 0, length);
			}
			out.close();
			in.close();
			result = true;
		}
		return result;
	}
}
