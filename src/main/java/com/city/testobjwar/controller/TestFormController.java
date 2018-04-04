package com.city.testobjwar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by City Mo on 2018/4/4.
 */
@Controller
@RequestMapping("/testForm")
public class TestFormController {

    @RequestMapping("/payPage")
    public ModelAndView payPage() {
        ModelAndView mv = new ModelAndView("testform/pay");
        return mv;
    }

    @RequestMapping("/pay")
    public ModelAndView payRequest(HttpServletRequest request, HttpServletResponse response,
                                   String name, String product, String price) throws Exception {
        ModelAndView mv = new ModelAndView("testform/choose_pay_method");
        mv.addObject("name", name);
        mv.addObject("product", product);
        mv.addObject("price", price);
        return mv;
    }

    @RequestMapping("/realPay")
    public ModelAndView realPay(HttpServletRequest request, HttpServletResponse response,
                                String name, String product, String price, String payMethod, final RedirectAttributes model) throws Exception {
        model.addAttribute("name",name);
        model.addAttribute("product",product);
        model.addAttribute("price",price);
        model.addAttribute("payMethod",payMethod);

        model.addFlashAttribute("namee",name);
        /*response.setStatus(302);
        response.setHeader("Location","/testForm/paying");*/



        return new ModelAndView("redirect:/testForm/paying");
    }

    @RequestMapping("/paying")
    public ModelAndView pay_ing(String name, String product, String price, String payMethod,@ModelAttribute("namee") String namee){
        ModelAndView mv = new ModelAndView("testform/wait");
        return mv;
    }

    @RequestMapping("/test")
    public ModelAndView test(HttpServletRequest request){
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String param = params.nextElement();
            System.out.println(param + " -> " + request.getParameter(param));
        }
        return new ModelAndView("redirect:/testForm/payPage");
    }
}
