package sean.com.example.notetest;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import sean.com.example.notetest.db.DaoMaster;
import sean.com.example.notetest.db.DaoSession;
import sean.com.example.notetest.db.EventsInfoDao;

/**
 * @author Sean
 * @data 2019/5/20
 * 进行一些初始化操作
 */
public class MyApplication extends Application {

    private DaoSession mDaoSession;
    private EventsInfoDao mDao;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initCreenDao();  //初始化GreenDao
        instance = this;

        UMConfigure.init(this,"5cea53620cafb2d7570000b5"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.setLogEnabled(true);

        //微信开放平台申请下离的应用id和md5签名工具生成的md5值
        PlatformConfig.setWeixin("wx9b5134440f179efd", "4efdff8ea6cd8070682a3866c0f444ae");
        //从腾讯开放平台获取你申请的qq分享相关的appid和screct值
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        //从微博开放平台获取你的appkey和screct值
        PlatformConfig.setSinaWeibo("1844955595", "2119f6551d01695b6555555693d575", "http://sns.whalecloud.com");

    }

    public void initCreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                this, "user_db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
        mDao = mDaoSession.getEventsInfoDao();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public EventsInfoDao getDao() {
        return mDao;
    }
}
