package sean.com.example.notetest.view;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;


/*
* 定义一个后台服务，用于振动或者播放音乐
* */
public class VibratorService extends Service {

    private Vibrator mVibrator;  //振动

    public VibratorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        //持续振动
        mVibrator.vibrate(new long[] {1000, 800, 1000, 800}, 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        mVibrator.cancel();
        super.onDestroy();
    }
}
