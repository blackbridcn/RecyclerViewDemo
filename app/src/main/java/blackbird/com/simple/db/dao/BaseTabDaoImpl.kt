package com.train.kdemo.db.dao

import android.database.sqlite.SQLiteOpenHelper
import com.train.kdemo.db.PageSqliteOpenHelper
import com.train.orm.orm.dao.OrmBaseTblDaoImpl

/**
 * Author: yuzzha
 * Date: 2021-02-08 17:45
 * Description:
 * Remark:
 */
abstract class BaseTabDaoImpl<T>(clazz: Class<T>) : OrmBaseTblDaoImpl<T>(clazz) {

    /* override fun getSQLiteOpenHelper(): SQLiteOpenHelper {
         return PageSqliteOpenHelper.Instance().sqLiteOpenHelper
     }*/

    override fun getSQLiteOpenHelper() = PageSqliteOpenHelper.Instance().sqLiteOpenHelper

}
