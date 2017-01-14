package common.db;

import java.util.ArrayList;
import java.util.HashMap;

import common.db.dao.DbDao;
import common.db.entity.TableEntity;
import common.utils.LogUtils;

/**
 * 描述: 所有表的实体仓库
 * Created by mjd on 2017/1/9.
 */
public class TablesManager {

    private static final String TAG = TablesManager.class.getSimpleName();

    private static TablesManager mTablesManager;

    private HashMap<Class<?>, TableEntity> entities = new HashMap<>();

    private ArrayList<TableEntity> entityList = new ArrayList<>();

    public static TablesManager getInstance() {
        if (mTablesManager == null) {
            synchronized (TablesManager.class) {
                if (mTablesManager == null) {
                    mTablesManager = new TablesManager();
                }
            }
        }
        return mTablesManager;
    }

    public void register(Class<?>... types) {
        for (Class<?> type : types) {
            if (find(type) != null) {
                LogUtils.d(TAG, "表已注册过");
                continue;
            }
            TableEntity m = new TableEntity(type);
            entities.put(type, m);
            entityList.add(m);
        }
    }

    public TableEntity find(Class<?> type) {
        return entities.get(type);
    }

    public void destroyInstance() {
        entities.clear();
        entityList.clear();
    }

    public void createTables(DbDao dao) {
        try {
            for (TableEntity tableEntity : entityList) {
                dao.execute(tableEntity.getCreateTableStatement(), null);
            }
        } catch (Exception ex) {
            LogUtils.e(TAG, "表创建失败:" + ex.getMessage());
        }
    }

}
