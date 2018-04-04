package com.city.testobjwar.controller;

import com.sdk.WXPay;
import com.swetake.util.Qrcode;
import com.wxpay.MyWXPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/jsp/wx")
public class WxPayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WxPayController.class);

	@RequestMapping("/pay")
	public void pay(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setCharacterEncoding("UTF-8");
		String out_trade_no = request.getParameter("out_trade_no");
		if (out_trade_no == null) {
			response.getWriter().write("请输入out_trade_no");
			return;
		}

		MyWXPayConfig config = MyWXPayConfig.getInstance();
		WXPay wxpay = new WXPay(config);

		Map<String, String> params = new HashMap<String, String>();
		params.put("body", "腾讯充值中心-QQ会员充值");
		params.put("out_trade_no", "out_trade_no" + out_trade_no);
		params.put("device_info", "WEB");
		params.put("fee_type", "CNY");
		params.put("total_fee", "1");
//		params.put("spbill_create_ip", getIpAddr(request));
		LOGGER.info("IP:{}",getIpAddr(request));
		params.put("spbill_create_ip", "222.72.159.210");
		params.put("notify_url", "http://118.89.229.222:8080/test-obj-war/receive/notify");
		if (judgeIsMoblie(request)) {
			params.put("trade_type", "MWEB");
		} else {
			params.put("trade_type", "NATIVE");
			params.put("product_id", "12");
		}

		Map<String, String> result = wxpay.unifiedOrder(params);
		LOGGER.info("响应结果:{}", result);

		if (judgeIsMoblie(request)) {
			String a = result.get("mweb_url") + "&redirect_url="
					+ URLEncoder.encode("http://118.89.229.222:8080/test-obj-war/receive/response", "utf-8");		
			
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\">window.location.href=\"");
			sb.append(a);
			sb.append("\"</script>");
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			writer.write(sb.toString());
			writer.flush();
		} else {
			String url = result.get("code_url");

			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');// 纠错等级（分为L、M、H三个等级）
			qrcode.setQrcodeEncodeMode('B');// N代表数字，A代表a-Z，B代表其它字符

			int version = 5;

			qrcode.setQrcodeVersion(version);// 版本
			// 生成二维码中要存储的信息
			// 设置一下二维码的像素
			int width = 67 + 12 * (version - 1);
			int height = 67 + 12 * (version - 1);

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 绘图
			Graphics2D gs = bufferedImage.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.setColor(Color.BLACK);
			gs.clearRect(0, 0, width, height);// 清除下画板内容

			// 设置下偏移量,如果不加偏移量，有时会导致出错。
			int pixoff = 2;

			byte[] d = url.getBytes("gb2312");
			if (d.length > 0 && d.length < 120) {
				boolean[][] s = qrcode.calQrcode(d);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			}
			gs.dispose();
			bufferedImage.flush();

			OutputStream os = response.getOutputStream();
			ImageIO.write(bufferedImage, "png", os);
			os.flush();
			os.close();
		}

	}

	private boolean judgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "ipad", "phone", "mobile", "wap", "netfront", "java",
				"opera mobi", "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry",
				"dopod", "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola",
				"foma", "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad",
				"webos", "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips",
				"sagem", "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			String agent = request.getHeader("User-Agent");
			System.out.println("agent:" + agent);
			for (String mobileAgent : mobileAgents) {
				if (agent.toLowerCase().indexOf(mobileAgent) >= 0 && agent.toLowerCase().indexOf("windows nt") <= 0
						&& agent.toLowerCase().indexOf("macintosh") <= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}

	private String getIpAddr(HttpServletRequest request) throws Exception {

		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String header = headers.nextElement();
			LOGGER.info("{} -> {}", header, request.getHeader(header));
		}
		String ip = request.getHeader("x-forwarded-for");
		System.out.println("x-forwarded-for ip: " + ip);
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println("Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println("WL-Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			System.out.println("HTTP_CLIENT_IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
			System.out.println("X-Real-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println("getRemoteAddr ip: " + ip);
		}
		System.out.println("获取客户端ip: " + ip);

		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			InetAddress address = InetAddress.getLocalHost();
			return address.getHostAddress();
		}

		if (ip == null) {
			return request.getRemoteAddr();
		}
		return ip;
	}
}
