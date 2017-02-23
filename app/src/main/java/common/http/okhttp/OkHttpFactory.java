package common.http.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 描述:
 * Created by mjd on 2017/2/23.
 */

public class OkHttpFactory {

    private static OkHttpFactory okHttpFactory;
    private final OkHttpClient okHttpClient;

    private OkHttpFactory() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
    }

    public static OkHttpFactory getInstance() {
        if (okHttpFactory == null) {
            okHttpFactory = new OkHttpFactory();
        }
        return okHttpFactory;
    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

}
