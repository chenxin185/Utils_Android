package com.example.chenxin.utils_android.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenxin on 2018/4/3.
 */

/**
 * 多线程的封装类，
 * 使用方法:
 *      1、直接使用execute(Runnable r) 没有子线程执行的回调
 *      2、使用execute(PriorityRunnable r) 可以像使用AsyncTask一样使用
 */

public class ThreadExecutor {

    private final static String TAG = ThreadExecutor.class.getName();

    private static final int MESSAGE_PROGRESS = 0;//更新消息
    private static final int MESSAGE_RESULT = 1;//结束消息

    private static ConcurrentLinkedQueue<PriorityRunnable> tasks;
    private static InternalHandler handler;//用此Handler来进行主线程的回调操作
    private ThreadPoolExecutor executor;

    private ThreadExecutor() {
        init();
    }

    private void init() {
        int corePoolSize;
        int maximumPoolSize = corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.HOURS;
        executor = new ThreadPoolExecutor(corePoolSize,
                                          maximumPoolSize,
                                          keepAliveTime,
                                          unit,
                                          new PriorityBlockingQueue<Runnable>(),
                                          Executors.defaultThreadFactory(),
                                          new ThreadPoolExecutor.AbortPolicy());
        handler = new InternalHandler();
        tasks = new ConcurrentLinkedQueue<>();
    }

    public static ThreadExecutor get() {
        return ThreadExecutorHolder.INSTANCE;
    }

    /**
     *  执行一个Runnable
     * @param r
     */
    @SuppressWarnings("JavaDoc")
    public void execute(Runnable r) {
        if (r == null)
            return;
        executor.execute(r);
    }

    /**
     * 执行一个PriorityRunnable 类似于AsyncTask用法
     * @param r
     */
    @SuppressWarnings("JavaDoc")
    public void execute(final PriorityRunnable r) {
        if (r == null) {
            Log.e(TAG, "PriorityRunnable 不能为空!");
            return;
        }
        tasks.add(r);
        r.onPreExecute();
        executor.execute(r.task);
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }

    public int getTasksSize() {
        return tasks.size();
    }

    private static class ThreadExecutorHolder {
        private static final ThreadExecutor INSTANCE = new ThreadExecutor();
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    private static class InternalHandler extends Handler {

        InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            PriorityRunnable r = find(msg);
            if (r != null) {
                Data data = (Data) msg.obj;
                switch (msg.what) {
                    case MESSAGE_PROGRESS:
                        r.onProgressUpdate(data.progress);
                        break;
                    case MESSAGE_RESULT:
                        r.onPostExecute(data.result);
                        break;
                    default:
                }
            }
        }

        private PriorityRunnable find(Message msg) {
            int hashCode = msg.arg1;
            for (PriorityRunnable r : tasks) {
                if (r != null)
                    if (hashCode == r.hashCode()) {
                        return r;
                    }
            }
            return null;
        }
    }

    public static class Task<Result> extends FutureTask implements Comparable<Task> {

        private int priority;

        public Task(@NonNull Callable callable, int priority) {
            super(callable);
            this.priority = priority;
        }

        private int getPriority() {
            return priority;
        }

        public int compareTo(@NonNull Task o) {
            if (this.getPriority() < o.priority) {
                return 1;
            }
            if (this.getPriority() > o.priority) {
                return -1;
            }
            return 0;
        }

    }

    public static abstract class PriorityRunnable<Params, Progress, Result> {

        Task<Result> task;
        WorkerRunnable<Params, Result> workerRunnable;

        public PriorityRunnable(int priority, final Params... params) {

            workerRunnable = new WorkerRunnable<Params, Result>() {
                @Override
                public Result call() throws Exception {
                    Result result = null;
                    try {
                        result = doInBackground(params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        postResult(result);
                    }
                    return null;
                }
            };

            task = new Task(workerRunnable, priority) {
                @Override
                protected void done() {
                    tasks.remove(PriorityRunnable.this);
                }
            };
        }

        private void postResult(Result result) {
            Data<Progress, Result> data = new Data<Progress, Result>(result);
            sendMessage(MESSAGE_RESULT, data);
        }

        private void sendMessage(int type, Data<Progress, Result> data) {
            Message message = handler.obtainMessage(type, data);
            message.arg1 = PriorityRunnable.this.hashCode();
            message.sendToTarget();
        }

        protected final void publishProgress(Progress... values) {
            Data<Progress, Result> data = new Data<Progress, Result>(null, values);
            sendMessage(MESSAGE_PROGRESS, data);
        }

        public void onProgressUpdate(Progress... values) {
        }

        public void onPreExecute() {
        }

        public void onPostExecute(Result progress) {
        }

        public abstract Result doInBackground(Params... params);

    }

    public static class Data<Progress, Result> {
        Progress progress[];
        Result result;

        public Data(Result result, Progress... progresses) {
            this.result = result;
            this.progress = progresses;
        }
    }

}
