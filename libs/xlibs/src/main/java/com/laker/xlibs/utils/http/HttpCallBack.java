package com.laker.xlibs.utils.http;


public abstract class HttpCallBack<Result> {

    public abstract void onSuccess(Result result);

    public abstract void onFailed(String error);

    public abstract void onProgress(float progressPercent);

}
