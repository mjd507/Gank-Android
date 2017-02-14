package common.image;

import android.widget.ImageView;

import com.cleaner.commonandroid.R;

/**
 * 描述:
 * Created by mjd on 2017/2/13.
 */

public class ImagePresenter {

    public void getImage(ImageView iv, String url, int defaultImage, int errorImage) {
        ImageProvider imageProvider = new ImageProvider();
        imageProvider.getImage(iv, url, defaultImage, errorImage);
    }

    public void getImage(ImageView iv, String url) {
        ImageProvider imageProvider = new ImageProvider();
        imageProvider.getImage(iv, url, R.mipmap.icon_loading, R.mipmap.icon_error);
    }

}
