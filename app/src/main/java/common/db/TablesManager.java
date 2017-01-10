package common.db;

import java.util.ArrayList;
import java.util.HashMap;

import common.db.dao.CommonDbDao;
import common.db.entity.TableEntity;
import common.utils.logger.Logger;

/**
 * 描述: 所有表的实体仓库
 * Created by mjd on 2017/1/9.
 */
public class TablesManager {

    private static final String TAG = TablesManager.class.getSimpleName();

    private static TablesManager tablesManager;

    private HashMap<Class<?>, TableEntity> entities = new HashMap<>();

    private ArrayList<TableEntity> entityList = new ArrayList<>();

    /**
     * 获取表实体数据
     */
    public TableEntity[] getEntities() {
        TableEntity[] metadatas = new TableEntity[entityList.size()];
        entityList.toArray(metadatas);
        return metadatas;
    }

    /**
     * 注册数据库表实体
     */
    public void register(Class<?>... types) {
        for (Class<?> type : types) {
            TableEntity m = new TableEntity(type);
            entities.put(type, m);
            entityList.add(m);
        }
    }

    /**
     * 获得表实体
     */
    public TableEntity find(Class<?> type) {
        return entities.get(type);
    }

    /**
     * 获取表容器单例对象
     */
    public static TablesManager getInstance() {
        if (tablesManager == null) {
            tablesManager = new TablesManager();
        }
        return tablesManager;
    }

    /**
     * 销毁单例对象，释放占用内存
     */
    public void destroyInstance() {
        entities.clear();
        entityList.clear();
        entities = null;
        entityList = null;
        tablesManager = null;
    }

    /**
     * 创建容器中的所有表
     */
    public void createTables(boolean dropTablesFirst, CommonDbDao db) {
        try {
            for (TableEntity tableEntity : entityList) {
                if (dropTablesFirst) {
                    db.execute(tableEntity.getDropTableStatement(), null);
                }
                db.execute(tableEntity.getCreateTableStatement(), null);
            }
        } catch (Exception ex) {
            Logger.e(TAG, ex.getMessage());
        }
    }
}
