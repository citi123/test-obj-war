package com.city.testobjwar.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayConstants;

@Controller
@RequestMapping("/receive")
public class ReceiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveController.class);

    @RequestMapping(path = "/response", method = RequestMethod.GET)
    public ModelAndView receiveResponse(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("response");
        printRequest(request);
        printRequestNew(request);
        return mv;
    }
    
    @RequestMapping("/pay_result")
    public ModelAndView response(){
    	ModelAndView mv = new ModelAndView("pay_result");
    	return mv;
    }
    

    @RequestMapping(path = "/notify", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String receiveNotify(HttpServletRequest request, HttpServletResponse response) {
        printRequest(request);
        printRequestNew(request);
        return "success";
    }

    private void printRequest(HttpServletRequest request) {
        LOGGER.info("请求地址:{}", request.getRequestURI());
        try {
            Map<String, String[]> map = request.getParameterMap();
            StringBuffer sb = new StringBuffer();
            sb.append("\n");
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                sb.append(entry.getKey()).append(" -> ").append(convert(entry.getValue())).append("\n");
            }
            if (sb.length() > 0) {
                LOGGER.info("请求参数:{}", sb);
            }
            String data = IOUtils.toString(request.getInputStream());
            LOGGER.info("请求信息:{}", data);
        } catch (Exception e) {
            LOGGER.info("打印失败", e);
        }
    }

    private String convert(String[] args) {
        if (args == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String v : args) {
            sb.append(v).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    private Map<String,String> printRequestNew(HttpServletRequest request){
    	Map<String,String> params = new HashMap<String,String>();
    	Map requestParams = request.getParameterMap();
    	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
    		String name = (String) iter.next();
    		String[] values = (String[]) requestParams.get(name);
    		String valueStr = "";
    		for (int i = 0; i < values.length; i++) {
    			valueStr = (i == values.length - 1) ? valueStr + values[i]
    					: valueStr + values[i] + ",";
    		}
    		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
    		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
    		params.put(name, valueStr);
    	}
    	LOGGER.info("请求信息new:{}", params);
    	return params;
    }
    
    private void accquireParams(Map<String,String> params){
    	// app_id
		params.get(AlipayConstants.APP_ID);
		// method
		params.get(AlipayConstants.METHOD);
		// sign_type
		params.get(AlipayConstants.SIGN_TYPE);
		// sign
		params.get(AlipayConstants.SIGN);
		// charset
		params.get(AlipayConstants.CHARSET);
		// timestamp
		params.get(AlipayConstants.TIMESTAMP);
		// version
		params.get(AlipayConstants.VERSION);
		// auth_app_id
    }
}
