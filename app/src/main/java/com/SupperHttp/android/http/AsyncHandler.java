package com.SupperHttp.android.http;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by zhou on 2016/8/24.
 */
public class AsyncHandler{

    private static AsyncHandler instance;

    public static final int THREAD_BG_HIGH 		= 1;
    public static final int THREAD_BG_NORMAL 	= 2;
    public static final int THREAD_BG_LOW 		= 3;

    private HandlerThread	mBgHighThread			= new HandlerThread("high_" + this.toString());
    private Handler			mBgHighHandler			= null;
    private HandlerThread 	mBgNormalThread			= new HandlerThread("normal_" + this.toString());
    private Handler 		mBgNormalHandler		= null;
    private HandlerThread	mBgLowThread			= new HandlerThread("low_" + this.toString());
    private Handler			mBgLowHandler			= null;

    private AsyncHandler(){
    }
    public static AsyncHandler getInstance() {
        if (instance == null) {
            synchronized (AsyncHandler.class) {
                if (instance == null) {
                    instance = new AsyncHandler();
                }
            }
        }
        return instance;
    }

    public void excute(int threadLevel,PostRunnable runnable){
        getHandler(threadLevel).post(runnable);
    }


    private synchronized Handler getHandler(int threadLevel)
    {
        switch (threadLevel){
            case THREAD_BG_HIGH:
                if(mBgHighHandler==null)
                {
                    mBgHighThread.start();
                    mBgHighThread.setPriority(Thread.MAX_PRIORITY);
                    mBgHighHandler = new Handler(mBgHighThread.getLooper());
                }
                return mBgHighHandler;
            case THREAD_BG_NORMAL:
                if(mBgNormalHandler==null)
                {
                    mBgNormalThread.start();
                    mBgNormalThread.setPriority(Thread.NORM_PRIORITY);
                    mBgNormalHandler = new Handler(mBgNormalThread.getLooper());
                }
                return mBgNormalHandler;
            case THREAD_BG_LOW:
                if(mBgLowHandler==null)
                {
                    mBgLowThread.start();
                    mBgLowThread.setPriority(Thread.MIN_PRIORITY);
                    mBgLowHandler = new Handler(mBgLowThread.getLooper());
                }
                return mBgLowHandler;
            default:
                return null;
        }
    }

}
