package com.train.kdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.train.kdemo.application.BaseApplication;
import com.train.kdemo.model.ItemData;
import com.train.orm.BaseSQLiteOpenHelper;
import com.train.orm.orm.helper.TableHelper;

/**
 * Author: yuzzha
 * Date: 2021-02-08 17:22
 * Description:
 * Remark:
 */
public class PageSqliteOpenHelper extends BaseSQLiteOpenHelper {

    private static volatile PageSqliteOpenHelper instance;

    private PageSqliteOpenHelper() {
        super(BaseApplication.getApplication(), "K_PAGE", 1);
    }

    public static PageSqliteOpenHelper Instance() {
        if (instance == null)
            synchronized (PageSqliteOpenHelper.class) {
                if (instance == null)
                    instance = new PageSqliteOpenHelper();
            }
        return instance;
    }


    @Override
    public SQLiteOpenHelper getSQLiteOpenHelper() {
        return super.getSQLiteOpenHelper();
    }

    @Override
    protected void onCreateSQLiteDB(SQLiteDatabase db) {
        TableHelper.createTable(db, ItemData.class);
    }

    @Override
    protected void onUpgradeSQLiteDB(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    protected void onDowngradeSQLiteDB(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngradeSQLiteDB(db, oldVersion, newVersion);
    }
}
