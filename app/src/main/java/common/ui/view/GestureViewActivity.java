package common.ui.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.cleaner.commonandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import common.utils.LogUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/21.
 */

public class GestureViewActivity  extends Activity implements View.OnClickListener, GestureOverlayView.OnGestureListener, GestureOverlayView.OnGesturePerformedListener {
    private static final String TAG = "MyGestureViewActivity";
    private GestureOverlayView gestureOverlayView;
    private Button btnSave;
    private Button btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        this.init();
    }

    private void init() {
        gestureOverlayView = (GestureOverlayView) this.findViewById(R.id.gestureOverlayView);
        gestureOverlayView.setUncertainGestureColor(0x17170444);
        gestureOverlayView.setGestureColor(getResources().getColor(R.color.black));
        gestureOverlayView.setGestureStrokeWidth(6.0F);
        gestureOverlayView.setGestureStrokeType(1);
        gestureOverlayView.setFadeOffset(3600000L);
        gestureOverlayView.addOnGestureListener(this);
        gestureOverlayView.addOnGesturePerformedListener(this);
        btnSave = (Button) this.findViewById(R.id.btn_save);
        btnClear = (Button) this.findViewById(R.id.btn_clear);
        btnSave.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_clear:
                clear();
                btnSave.setEnabled(false);
                break;
        }
    }

    private void save() {
        SaveTask saveTask = new SaveTask(this, gestureOverlayView);
        saveTask.execute(new Object[0]);
    }

    private void clear() {
        this.gestureOverlayView.setFadeOffset(100L);
        this.gestureOverlayView.clear(true);
        this.gestureOverlayView.setFadeOffset(3600000L);
        this.gestureOverlayView.destroyDrawingCache();
    }

    @Override
    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
        LogUtils.e(TAG, "onGestureStarted");

    }

    @Override
    public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        LogUtils.e(TAG, "onGesture");
        btnSave.setEnabled(true);
    }

    @Override
    public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
        LogUtils.e(TAG, "onGestureEnded");
    }

    @Override
    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        LogUtils.e(TAG, "onGestureCancelled");
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        LogUtils.e(TAG, "onGesturePerformed手势绘画完成");
    }

    private static class SaveTask extends AsyncTask<Object, Object, File> {
        private Context context;
        private GestureOverlayView gestureOverlayView;
        private Bitmap bitmap;
        private ProgressDialog progressDialog;

        public SaveTask(Context context, GestureOverlayView gestureOverlayView) {
            this.context = context;
            this.gestureOverlayView = gestureOverlayView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            gestureOverlayView.setDrawingCacheEnabled(true);
            gestureOverlayView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
            bitmap = gestureOverlayView.getDrawingCache();
            this.progressDialog = ProgressDialog.show(context, "", "保存中...");
            this.progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected File doInBackground(Object... params) {
            try {
                File file = new File(context.getExternalCacheDir(), "sign" + System.currentTimeMillis() + ".png");
                if (!file.getParentFile().exists()) {
                    file.mkdirs();
                }

                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 60, fos);
                fos.flush();
                fos.close();
                return file;
            } catch (IOException e) {
                e.printStackTrace();
                publishProgress(new Object[0]);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            ToastUtils.showShort(context, "保存失败");
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            gestureOverlayView.destroyDrawingCache();
            progressDialog.cancel();
            if (file != null) {
                Intent intent = new Intent();
                intent.putExtra("filepath", file.toString());
                LogUtils.e(TAG, file.toString());
                ((GestureViewActivity) context).setResult(-1, intent);
            }
            ((GestureViewActivity) context).finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;

    }
}
