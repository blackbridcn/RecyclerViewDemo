package com.train.orm.orm.dao;

import android.content.ContentValues;

import java.util.List;

/**
 *
 */
public interface IOrmTableDao<T> {


    /**
     * 插入实体类,默认主键自增,调用insert(T,true);.
     *
     * @param entity 映射实体
     */
    void insert(T entity);

    /**
     * 插入实体类,默认主键自增,调用insert(T,true);.
     *
     * @param entity 映射实体
     * @return 插入成功的数据ID
     */
    long insertReturnId(T entity);

    /**
     * 如果不存在，则保存；如果存在，则不处理
     *
     * @param entity 数据
     */
    void insertIfNotExist(T entity);

    /**
     * 插入实体类列表，默认主键自增,调用insertList(List<T>,true);.
     *
     * @param entityList 映射实体列表
     * @return 插入成功的数据行号
     */
    void insertList(List<T> entityList);

    void insertListData(List<T> entityList);

    /**
     * 根据ID删除数据.
     *
     * @param id 数据ID主键
     */
    void delete(int id);

    /**
     * 根据where删除数据.
     *
     * @param where where语句
     */
    void delete(String where);

    /**
     * 删除所有数据.
     */
    void deleteAll();

    /**
     * 更新数据.
     *
     * @param entity 数据,ID主键
     */
    void update(T entity);

    /**
     * @param sqlWhere
     */
    void update(String sqlWhere);

    /**
     * 更新数据.
     *
     * @param entity 数据,ID主键
     */
    void update(T entity, String where);

    /**
     * 根据条件更新数据.
     *
     * @return 影响的行数
     */
    int update(ContentValues contentValues, String where, String[] whereValue);


    /**
     * 如果不存在，则保存；如果存在，则更新
     *
     * @param entity 数据
     */
    void insertSaveOrUpdate(T entity);

    /**
     * 查询列表.
     *
     * @return 映射实体列表
     */
    List<T> queryList();

    /**
     * 根据获取一条数据.
     *
     * @param id 数据ID主键
     * @return 一条数据映射实体
     */
    T queryOne(long id);

    /**
     * 查询单条数据
     *
     * @param selection
     * @param selectionArgs
     * @return
     */
    T queryOne(String[] columns, String selection, String[] selectionArgs);

    /**
     * 映射实体列表.
     *
     * @param selection     where语句的sql
     * @param selectionArgs where语句的sql的绑定变量的参数
     * @return 映射实体列表
     */
    List<T> queryList(String selection, String[] selectionArgs);

    /**
     * 返回一个查询的结果条数.
     *
     * @param selection     查询sql
     * @param selectionArgs 绑定变量的参数值
     * @return 总条数.
     */
    int queryCount(String selection, String[] selectionArgs);

    /**
     * 返回一个查询的结果条数.
     *
     * @param columns       需要查询的 column
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @return
     */
    int queryCount(String[] columns,
                   String selection,
                   String[] selectionArgs,
                   String orderBy);

    /**
     * 执行查询语句.
     *
     * @param sql           sql语句
     * @param selectionArgs 绑定变量的参数值
     * @param clazz         返回的对象类型
     * @return 映射实体列表
     */
    List<T> rawQuery(String sql, String[] selectionArgs, Class<T> clazz);

    /**
     * 映射实体列表.
     *
     * @param columns       查询的列
     * @param selection     where语句的sql
     * @param selectionArgs where语句的sql的绑定变量的参数
     * @param groupBy       分组语句
     * @param having        分组后的过滤语句
     * @param orderBy       排序
     * @param limit         limit语句
     * @return 映射实体列表
     */
    List<T> queryList(String[] columns, String selection,
                      String[] selectionArgs, String groupBy, String having,
                      String orderBy, String limit);

    List<T> queryList(String[] columns, String selection,
                      String[] selectionArgs,
                      String orderBy);

    List<T> queryList(String[] columns, String where, String[] selectionArgs, int limit, int offset);

    List<T> queryList(boolean distinct, String tables, String[] columns, String where, String[] selectionArgs,
                      String groupBy, String having, String orderBy, int limit, int offset);

    /**
     * 检查是否存在数据.
     *
     * @return 如果存在返回true, 不存在为false
     */
    boolean isExist(T obj);

    /**
     * 检查是否存在数据.
     *
     * @param sql           sql语句
     * @param selectionArgs 绑定变量的参数值
     * @return 如果存在返回true, 不存在为false
     */
    boolean isExist(String sql, String[] selectionArgs);

    /**
     * 封装执行sql代码.
     *
     * @param sql           sql语句
     * @param selectionArgs 绑定变量的参数值
     */
    void execSql(String sql, Object[] selectionArgs);
}
