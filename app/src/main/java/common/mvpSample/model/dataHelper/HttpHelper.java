package common.mvpSample.model.dataHelper;

/**
 * 描述:
 * Created by mjd on 2017/1/6.
 */
public class HttpHelper {
    public HttpHelper() {

    }

    public void execRequest(String url, HttpCallback callback) {
        //网络请求的代码，代码略
        if(callback != null){
            callback.onSuccess();
        }
    }

    public interface HttpCallback {
        void onSuccess();

        void onFail();
    }
}

