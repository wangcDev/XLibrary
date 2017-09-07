package com.laker.pay;

import android.app.Activity;
import android.content.Context;

import com.laker.pay.alipay.Alipay;
import com.laker.pay.weixin.WXPay;
import com.tencent.mm.sdk.modelpay.PayReq;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


public class PayUtils {


    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void doWXpay(Context context, String wx_appid, PayReq req, WXPay.WXPayResultCallBack callBack) {
        WXPay.init(context, wx_appid);
        WXPay.getInstance().doPay(req, callBack);
    }

    public static void doAlipay(Activity context, String pay_param, Alipay.AlipayResultCallBack callBack) {
        new Alipay(context, pay_param, callBack).doPay();
    }

}
