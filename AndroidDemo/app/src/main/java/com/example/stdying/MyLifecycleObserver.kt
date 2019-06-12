package com.example.stdying

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log


/**
 * Created by li on 2019/6/12.
 *
 * @author li
 */
class MyLifecycleObserver : LifecycleObserver {

    init {
        Log.i(TAG, "init")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.i(TAG, "OnResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.i(TAG, "onPause")
    }


    companion object {
        const val TAG = "MyLifecycleObserver"
    }
}