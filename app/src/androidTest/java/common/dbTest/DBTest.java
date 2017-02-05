package common.dbTest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import common.db.DbManager;
import common.db.TablesManager;
import common.db.dao.DbDao;
import common.utils.LogUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * 描述:
 * Created by mjd on 2017/1/20.
 */
@RunWith(AndroidJUnit4.class)
public class DBTest {


    private DbDao dao;

    @Before
    public void createDbAndTable() {
        DbManager.DbParams params = new DbManager.DbParams();
        params.dbName = "TuHu.db";
        params.dbVersion = 1;
        DbManager.getInstance().init(InstrumentationRegistry.getTargetContext(), params);
        dao = DbManager.getInstance().getDao(null);
        TablesManager tablesManager = TablesManager.getInstance();
        tablesManager.register(Person.class);
        tablesManager.createTables(dao);
        LogUtils.d("DBTest", "create Db And Table end");
    }

    @Test
    public void insert() {
        int count = 0;
//        DbDao dao = DbManager.getInstance().getDao(null);
        for (int i = 0; i < 10; i++) {
            Person person = new Person("a" + i, 20 + i);
            long result = dao.insert(person);
            if (result != -1) {
                count++;
            } else {
                break;
            }
        }
        LogUtils.d("DBTest", "count = " + count);
        assertEquals(10, count);

    }

    @Test
    public void delete() {
        Boolean delete = dao.delete(Person.class, "name=?", new String[]{"a0"});
        assertTrue(delete);

    }

    @Test
    public void update() {
        Person person = new Person("b1", 18, true);
        long update = dao.update(Person.class, person, "name = ?", new String[]{"a1"});
        assertEquals(1, update);
    }

    @Test
    public void query() {
        ArrayList<Person> personList = dao.query(false, Person.class, null, null, null, null, null, null, null);
        if (personList.size() > 0) {
            LogUtils.d("DBTest", "查询成功" + "共 " + personList.size() + " 条数据");
            for (Person person : personList) {
                LogUtils.d("result", person.toString());
            }
        }
    }
}
