package com.city.testobjwar.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdk.WXPayUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by City Mo on 2018/5/8.
 */
public class DFGhjkl {
    public static void main1(String[] args) throws Exception{
        String text = "{\"transaction_id\":\"4200000157201805095573588565\",\"nonce_str\":\"5c55e22eee144bcc85d93572c1390cc2\",\"bank_type\":\"CFT\",\"openid\":\"oHkLxt9d9pQOG-JvnTsFWV-R3IuE\",\"sign\":\"443022EF23BF581A176253AA75A7717D\",\"fee_type\":\"CNY\",\"mch_id\":\"11473623\",\"cash_fee\":\"1\",\"out_trade_no\":\"2018050916350088\",\"appid\":\"wxab8acb865bb1637e\",\"total_fee\":\"1\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"time_end\":\"20180509163431\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}";
        JSONObject object = JSONObject.parseObject(text);

        Map<String,String> map = new HashMap<String, String>();
        for(Map.Entry entry : object.entrySet()){
            map.put(entry.getKey().toString(),entry.getValue().toString());
        }

        String xml = WXPayUtil.mapToXml(map);

        System.out.println(xml);
    }

    public static void main(String[] args) {
        String text = "{\"reason\":\"页面测试单子\",\"owner_id\":\"苏a\",\"point_payer_account_id\":297741,\"point_payee_account_id\":180,\"source_appid\":\"20091214\",\"type\":1,\"point\":100.00,\"output\":\"JSON\",\"total\":100.00,\"login_entry\":1,\"point_payee_id\":\"mall\",\"outer_trade_no\":\"100002292018051011280006\",\"signtype\":\"MD5\",\"encoding\":\"UTF-8\",\"trade_info\":\"\",\"point_payer_id\":\"苏a\",\"asset_code\":\"POINT\",\"v\":\"3.0\",\"appid\":\"10000229\",\"product_category\":\"\"}";

        JSONObject object = JSONObject.parseObject(text);


        for(Map.Entry entry : object.entrySet()){

            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
