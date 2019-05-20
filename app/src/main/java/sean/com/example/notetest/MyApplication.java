package sean.com.example.notetest;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

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
