package com.laker.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.util.Map;


public class Alipay {
    private String mParams;
    private PayTask mPayTask;
    private AlipayResultCallBack mCallback;

    public static final int ERROR_RESULT = 1;
    public static final int ERROR_PAY = 2;
    public static final int ERROR_NETWORK = 3;

    public interface AlipayResultCallBack {
        void onSuccess();
        void onDealing();
        void onError(int error_code);
        void onCancel();
    }

    public Alipay(Activity ActivityContext, String params, AlipayResultCallBack callback) {
        mParams = params;
        mCallback = callback;
        mPayTask = new PayTask(ActivityContext);
    }

    public void doPay() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> pay_result = mPayTask.payV2(mParams,true);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mCallback == null) {
                            return;
                        }

                        if(pay_result == null) {
                            mCallback.onError(ERROR_RESULT);
                            return;
                        }

                        String resultStatus = pay_result.get("resultStatus");
                        if(TextUtils.equals(resultStatus, "9000")) {
                            mCallback.onSuccess();
                        } else if(TextUtils.equals(resultStatus, "8000")) {
                            mCallback.onDealing();
                        } else if(TextUtils.equals(resultStatus, "6001")) {
                            mCallback.onCancel();
                        } else if(TextUtils.equals(resultStatus, "6002")) {
                            mCallback.onError(ERROR_NETWORK);
                        } else if(TextUtils.equals(resultStatus, "4000")) {
                            mCallback.onError(ERROR_PAY);
                        }
                    }
                });
            }
        }).start();
    }
}
