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

    public TableEntity[] getEntities() {
        TableEntity[] entities = new TableEntity[entityList.size()];
        entityList.toArray(entities);
        return entities;
    }

    public void register(Class<?>... types) {
        for (Class<?> type : types) {
            TableEntity m = new TableEntity(type);
            entities.put(type, m);
            entityList.add(m);
        }
    }

    public TableEntity find(Class<?> type) {
        return entities.get(type);
    }

    public static TablesManager getInstance() {
        if (mTablesManager == null) {
            mTablesManager = new TablesManager();
        }
        return mTablesManager;
    }

    public void destroyInstance() {
        entities.clear();
        entityList.clear();
        entities = null;
        entityList = null;
        mTablesManager = null;
    }

    public void createTables(boolean dropTablesFirst, DbDao dao) {
        try {
            for (TableEntity tableEntity : entityList) {
                if (dropTablesFirst) {
                    dao.execute(tableEntity.getDropTableStatement(), null);
                }
                dao.execute(tableEntity.getCreateTableStatement(), null);
            }
        } catch (Exception ex) {
            LogUtils.e(TAG, "表创建失败");
        }
    }
}
