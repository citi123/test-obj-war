package com.wxpay;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.sdk.IWXPayDomain;
import com.sdk.WXPayConfig;

public class MyWXPayConfig extends WXPayConfig {

	private byte[] certData;
    private static MyWXPayConfig INSTANCE;

    private MyWXPayConfig() throws Exception{
        /*String certPath = "D://CERT/common/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();*/
    }

    public static MyWXPayConfig getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (MyWXPayConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyWXPayConfig();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return "wxab8acb865bb1637e";
    }

    public String getMchID() {
        return "11473623";
    }

    public String getKey() {
        return "2ab9071b06b9f739b950ddb41db2690d";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }

}
