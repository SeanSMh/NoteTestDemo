package sean.com.example.notetest.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import sean.com.example.notetest.entity.EventsInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EVENTS_INFO".
*/
public class EventsInfoDao extends AbstractDao<EventsInfo, Long> {

    public static final String TABLENAME = "EVENTS_INFO";

    /**
     * Properties of entity EventsInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property Data = new Property(3, String.class, "data", false, "DATA");
        public final static Property Day = new Property(4, long.class, "day", false, "DAY");
        public final static Property Hour = new Property(5, long.class, "hour", false, "HOUR");
        public final static Property Minute = new Property(6, long.class, "minute", false, "MINUTE");
        public final static Property Second = new Property(7, long.class, "second", false, "SECOND");
        public final static Property Diff = new Property(8, long.class, "diff", false, "DIFF");
    }


    public EventsInfoDao(DaoConfig config) {
        super(config);
    }
    
    public EventsInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EVENTS_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TITLE\" TEXT," + // 1: title
                "\"CONTENT\" TEXT," + // 2: content
                "\"DATA\" TEXT," + // 3: data
                "\"DAY\" INTEGER NOT NULL ," + // 4: day
                "\"HOUR\" INTEGER NOT NULL ," + // 5: hour
                "\"MINUTE\" INTEGER NOT NULL ," + // 6: minute
                "\"SECOND\" INTEGER NOT NULL ," + // 7: second
                "\"DIFF\" INTEGER NOT NULL );"); // 8: diff
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EVENTS_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EventsInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(4, data);
        }
        stmt.bindLong(5, entity.getDay());
        stmt.bindLong(6, entity.getHour());
        stmt.bindLong(7, entity.getMinute());
        stmt.bindLong(8, entity.getSecond());
        stmt.bindLong(9, entity.getDiff());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EventsInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(4, data);
        }
        stmt.bindLong(5, entity.getDay());
        stmt.bindLong(6, entity.getHour());
        stmt.bindLong(7, entity.getMinute());
        stmt.bindLong(8, entity.getSecond());
        stmt.bindLong(9, entity.getDiff());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public EventsInfo readEntity(Cursor cursor, int offset) {
        EventsInfo entity = new EventsInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // data
            cursor.getLong(offset + 4), // day
            cursor.getLong(offset + 5), // hour
            cursor.getLong(offset + 6), // minute
            cursor.getLong(offset + 7), // second
            cursor.getLong(offset + 8) // diff
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EventsInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setData(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDay(cursor.getLong(offset + 4));
        entity.setHour(cursor.getLong(offset + 5));
        entity.setMinute(cursor.getLong(offset + 6));
        entity.setSecond(cursor.getLong(offset + 7));
        entity.setDiff(cursor.getLong(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(EventsInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(EventsInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EventsInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
