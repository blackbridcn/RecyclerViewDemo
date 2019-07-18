package blackbird.com.recyclerviewdemo.uitls;


import androidx.annotation.NonNull;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
    //通常核心线程数可以设为CPU数量+1，而最大线程数可以设为CPU的数量*2+1
    //CPU核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
 /*   // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };
    *//**
     * An {@link Executor} that can be used to execute tasks in parallel.
     *//*
    public static final Executor THREAD_POOL_EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }*/

    //可同时下载的任务数（核心线程数）
    private int CORE_POOL_SIZE = CPU_COUNT + 1;
    //缓存队列的大小（最大线程数）
    private int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    //非核心线程闲置的超时时间（秒），如果超时则会被回收
    private long KEEP_ALIVE = 10L;
    private ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    private ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger();

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "app_task#" + mCount.getAndIncrement());
        }
    };

    private ThreadPool() {
    }

    public static ThreadPool getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final ThreadPool instance = new ThreadPool();
    }

    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize == 0) {
            return;
        }
        CORE_POOL_SIZE = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        if (maxPoolSize == 0) {
            return;
        }
        MAX_POOL_SIZE = maxPoolSize;
    }

    public int getCorePoolSize() {
        return CORE_POOL_SIZE;
    }

    public int getMaxPoolSize() {
        return MAX_POOL_SIZE;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        if (THREAD_POOL_EXECUTOR == null) {
            THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                    CORE_POOL_SIZE, MAX_POOL_SIZE,
                    KEEP_ALIVE, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(128),
                    sThreadFactory);
        }
        return THREAD_POOL_EXECUTOR;
    }
}
