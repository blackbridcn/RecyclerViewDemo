package org.aop.wrap;

import lombok.Data;

/**
 * File: SnapShot.java
 * Author: yuzhuzhang
 * Create: 2020/4/4 4:26 PM
 * Description: TODO
 * -----------------------------------------------------------------
 * 2020/4/4 : Create SnapShot.java (yuzhuzhang);
 * -----------------------------------------------------------------
 */
@Data
public class SnapShot {

    private long startTime;
    private long endTime;
    private long elapsedTime;


    private void reset() {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }

    public void start() {
        reset();
        startTime = System.nanoTime();
    }

    public void stop() {
        if (startTime != 0) {
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        } else {
            reset();
        }
    }

    /**
     * 返回花费的时间，单位是纳秒
     * @return
     */
    public long getElapsedTime() {

        return elapsedTime;
    }
}
