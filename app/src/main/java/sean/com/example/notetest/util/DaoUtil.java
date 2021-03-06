package sean.com.example.notetest.util;

import java.util.List;

import sean.com.example.notetest.MyApplication;
import sean.com.example.notetest.db.EventsInfoDao;
import sean.com.example.notetest.entity.EventsInfo;

/**
 * @author Sean
 * @data 2019/5/20
 * 对数据库的增删查改做封装
 */
public class DaoUtil {

    private static volatile DaoUtil instance;
    private EventsInfoDao mDao;

    private DaoUtil() {}

    //DCL单例模式
    public static DaoUtil getInstance() {
        if(instance == null) {
            synchronized (DaoUtil.class) {
                if(instance == null) {
                    instance = new DaoUtil();
                }
            }
        }
        return instance;
    }

    /*
    * 添加记录
    * */
    public void addInfo(String title, String content, String data, int color) {
        mDao = MyApplication.getInstance().getDaoSession().getEventsInfoDao();
        EventsInfo info = new EventsInfo();
        info.setTitle(title);
        info.setContent(content);
        info.setData(data);
        info.setBackgroundColor(color);
        mDao.insertOrReplace(info);
    }

    /*
    * 删除记录
    * */
    public void deleteInfo(Long id) {
        mDao = MyApplication.getInstance().getDaoSession().getEventsInfoDao();
        mDao.deleteByKey(id);
    }



    /*
    * 删除所有记录
    * */
    public void deleteAll() {
        mDao = MyApplication.getInstance().getDaoSession().getEventsInfoDao();
        mDao.deleteAll();
    }

    /*
    * 修改记录
    * */
    public void updateInfo(String title, String content, String data, int id) {
        mDao = MyApplication.getInstance().getDaoSession().getEventsInfoDao();
        List<EventsInfo> list = mDao.loadAll();

        EventsInfo info = list.get(id);
        info.setTitle(title);
        info.setContent(content);
        info.setData(data);
        mDao.update(info);
    }

    /*
    * 查询记录
    * */
    public List<EventsInfo> queryInfo() {
        mDao = MyApplication.getInstance().getDaoSession().getEventsInfoDao();
        List<EventsInfo> infoList = mDao.loadAll();
        return infoList;
    }

}
