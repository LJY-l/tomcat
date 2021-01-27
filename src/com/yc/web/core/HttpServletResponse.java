package com.yc.web.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.yc.tomcat.core.ConstantInfo;
import com.yc.tomcat.util.StringUtil;

public class HttpServletResponse implements ServletResponse {

	private OutputStream os = null;
	private String basePath = ConstantInfo.BASE_PATH;
	
	public HttpServletResponse(OutputStream os) {
		this.os = os;
	}

	@Override
	public void sendRedirect(String url) {
		if(StringUtil.checkNull(url)) {
			//报错 404
		}
		//读取指定的资源文件
		File fl = new File(basePath, url);
		if (!fl.exists() || !fl.isFile()) {
			//报错
		}
		try {
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(fl);
			byte[] bt = new byte[fis.available()];
			fis.read(bt);
			
			String responseHeader = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: " + bt.length + "\r\n\r\n";
			os.write(responseHeader.getBytes());
			os.write(bt);
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	public PrintWriter getWriter() throws IOException {
		return null;
	}

}
