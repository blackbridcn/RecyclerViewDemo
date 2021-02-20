package com.train.kdemo.model;

import com.train.orm.orm.annotation.Column;
import com.train.orm.orm.annotation.Id;
import com.train.orm.orm.annotation.Table;

import java.io.Serializable;

import lombok.Data;

/**
 * Author: yuzzha
 * Date: 2021-02-08 17:10
 * Description:
 * Remark:
 */
@Data
@Table(name = "table_item")
public class ItemData {

    @Id
    @Column(name = "_id")
    private int id;

    @Column(name = "_index")
    private int index;

    @Column(name = "name")
    private String name;

    @Column(name = "score")
    private float score;

    public ItemData() {
    }

    public ItemData(int id, int index, String name, float score) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.score = score;
    }

    public ItemData(int index, String name, float score) {
        this.index = index;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
