package sean.com.example.notetest;

import java.util.List;

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
    public void addInfo(String title, String content, String data) {
        mDao = MyApplication.getInstance().getDaoSession().getEventsInfoDao();
        EventsInfo info = new EventsInfo();
        info.setTitle(title);
        info.setContent(content);
        info.setData(data);
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
    public void updateInfo(String title, String content, Long id) {
        mDao = MyApplication.getInstance().getDaoSession().getEventsInfoDao();
        EventsInfo info = new EventsInfo();
        info.setId(id);
        info.setTitle(title);
        info.setContent(content);
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
