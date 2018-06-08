package com.city.testobjwar.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import com.swetake.util.Qrcode;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView("freemarker/test");

        mv.addObject("name", "张三");


        List<String> list = Arrays.asList("小张", "小陈", "小罗", "小丁");
        mv.addObject("nameList", list);

        mv.addObject("date", new Date());

        return mv;
    }

    @RequestMapping("/pic")
    public ModelAndView pic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("freemarker/pic");

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

        byte[] d = "https://www.baidu.com".getBytes("gb2312");
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


        return mv;
    }

    @RequestMapping("/pay")
    public String pay(ModelMap modelMap, HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();

        Map<String, String> signParams = new HashMap<String, String>();
        String signKey = "";
        String action = "";
        for (Map.Entry<String, String[]> param : params.entrySet()) {

            if (!param.getKey().equals("appsecret")
                    && !param.getKey().equals("action")) {
                if (param.getValue().length > 1) {
                    signParams.put(param.getKey(), StringUtils.join(param.getValue(), ","));
                } else {
                    signParams.put(param.getKey(), request.getParameter(param.getKey()).toString());
                }
            }
            if (param.getKey().equals("appsecret")) {
                signKey = request.getParameter("appsecret");
            }
            if (param.getKey().equals("action")) {
                action = request.getParameter("action");
                modelMap.put("action", action);
            }
        }

        signParams.put("sign", Sign.signSHA1(signParams, signKey));
        modelMap.put("params", signParams);

        return "freemarker/pay";
    }

    @RequestMapping("/request")
    @ResponseBody
    public Map request(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String a =  IOUtils.toString(request.getInputStream(),"utf-8");

        System.out.println(a);

        Map<String,String> result = new HashMap<String, String>();
        result.put("now", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return result;
    }
}
