package com.mj.ly_core.net.download;

import android.os.AsyncTask;

import com.mj.ly_core.net.RestCreator;
import com.mj.ly_core.net.callback.IError;
import com.mj.ly_core.net.callback.IFailure;
import com.mj.ly_core.net.callback.IRequest;
import com.mj.ly_core.net.callback.ISuccess;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {

    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUSET;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url,
                           IRequest request,
                           String downloadDir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error) {
        this.URL = url;
        this.REQUSET = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    public final void handleDownload() {
        if (null != REQUSET) {
            REQUSET.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();

                            final SaveFileTask task = new SaveFileTask(REQUSET, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            // 这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (null != REQUSET) {
                                    REQUSET.onRequestEnd();
                                }
                            }
                        } else {
                            if (null != ERROR) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (null != FAILURE) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
