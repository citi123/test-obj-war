package com.city.testobjwar.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by City Mo on 2018/4/16.
 */
public class Sign {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sign.class);


    public static String signMd5(Map<String, String> params, String key) {
        return sign(params, key, "MD5");
    }

    public static String signSHA1(Map<String, String> params, String key) {
        return sign(params, key, "SHA1");
    }


    public static String sign(Map<String, String> params, String key, String signType) {
        if (params == null) {
            LOGGER.warn("验签参数为空");
            throw new RuntimeException("");
        }
        if (signType == null) {
            if (params.get("signType") == null) {
                LOGGER.warn("验签类型为空");
                throw new RuntimeException("");
            } else {
                signType = params.get("signType");
            }
        }

        Map<String, String> signParams = new TreeMap<String, String>(params);
        StringBuffer sb = new StringBuffer();
        sb.append(key);
        for (Map.Entry<String, String> entry : signParams.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("sign")
                    || entry.getValue() == null) {
                continue;
            }
            sb.append(entry.getKey()).append(entry.getValue());
        }

        if ("MD5".equalsIgnoreCase(signType)) {
            return DigestUtils.md5Hex(sb.toString()).toUpperCase();
        } else if ("SHA1".equalsIgnoreCase(signType)) {
            return DigestUtils.sha1Hex(sb.toString()).toUpperCase();
        } else {
            LOGGER.warn("未知的签名类型:{}", signType);
            throw new RuntimeException("");
        }
    }
}
