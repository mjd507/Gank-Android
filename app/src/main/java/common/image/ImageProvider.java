package common.image;

import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import common.http.volley.VolleyFactory;

/**
 * 描述:
 * Created by mjd on 2017/2/13.
 */

public class ImageProvider {

    public void getImage(ImageView iv, String url, int defaultImage, int errorImage) {
        ImageLoader imageLoader = VolleyFactory.getInstance().getImageLoader();
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv, defaultImage, errorImage);
        imageLoader.get(url, imageListener);
    }

}
