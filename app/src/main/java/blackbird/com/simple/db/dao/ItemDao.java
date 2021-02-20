package com.train.kdemo.db.dao;

import com.train.kdemo.model.ItemData;

import org.jetbrains.annotations.NotNull;

/**
 * Author: yuzzha
 * Date: 2021-02-09 10:03
 * Description:
 * Remark:
 */
public class ItemDao extends BaseTabDaoImpl<ItemData> {

    private static volatile ItemDao instance;

    private ItemDao() {
        super(ItemData.class);
    }

    public static final ItemDao getInstance() {
        if (instance == null) {
            synchronized (ItemDao.class) {
                if (instance == null) {
                    instance = new ItemDao();
                }
            }
        }
        return instance;
    }


}
