package common.db.entity;

import com.android.common.db.ComSQLiteDatabase;
import com.android.common.db.entity.*;
import com.android.common.db.entity.EntityMetadata;
import com.android.common.db.exception.DBNotOpenException;
import com.android.common.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 描述: 数据库表实体仓库
 * Created by mjd on 2016/11/26.
 */
public class EntityRepository {
    private static EntityRepository entityRepository;
    /**
     * 注册的表实体，键对值关系存储，键：class对象 值：实体数据
     */
    private HashMap<Class<?>, com.android.common.db.entity.EntityMetadata> entities = new HashMap<Class<?>, com.android.common.db.entity.EntityMetadata>();
    /**
     * 表实体数据集合
     */
    private ArrayList<com.android.common.db.entity.EntityMetadata> entityList = new ArrayList<com.android.common.db.entity.EntityMetadata>();

    /**
     * 获取表实体数据
     *
     * @return 实体数据数组
     */
    public com.android.common.db.entity.EntityMetadata[] getEntities() {
        com.android.common.db.entity.EntityMetadata[] metadatas = new com.android.common.db.entity.EntityMetadata[entityList.size()];
        entityList.toArray(metadatas);

        return metadatas;
    }

    /**
     * 注册数据库表实体
     *
     * @param types 实体类型
     */
    public void register(Class<? extends com.android.common.db.entity.BaseDB>... types) {
        for (Class<? extends com.android.common.db.entity.BaseDB> type : types) {
            com.android.common.db.entity.EntityMetadata m = new com.android.common.db.entity.EntityMetadata(type);
            entities.put(type, m);
            entityList.add(m);
        }
    }

    /**
     * 获得表实体
     *
     * @param type 实体类型
     * @return
     */
    public com.android.common.db.entity.EntityMetadata find(Class<? extends com.android.common.db.entity.BaseDB> type) {
        return entities.get(type);
    }

    /**
     * 获取表容器单例对象
     */
    public static EntityRepository getInstance() {
        if (entityRepository == null) {
            entityRepository = new EntityRepository();
        }
        return entityRepository;
    }

    /**
     * 销毁单例对象，释放占用内存
     */
    public void destroyInstance() {
        entities.clear();
        entityList.clear();
        entities = null;
        entityList = null;
        entityRepository = null;
    }

    /**
     * 创建容器中的所有表
     *
     * @param dropTablesFirst 判断的标签。如果存在已存在此表，true删除并重新创建，false不删除不重新创建
     * @param database        数据库操作对象
     */
    public void createTables(boolean dropTablesFirst, ComSQLiteDatabase database) {
        try {
            for (EntityMetadata m : entityList) {
                if (dropTablesFirst) {
                    database.execute(m.getDropTableStatement(), null);
                }
                database.execute(m.getCreateTableStatement(), null);
            }
        } catch (DBNotOpenException ex) {

            Logger.e(ex.getMessage());
        }
    }
}
