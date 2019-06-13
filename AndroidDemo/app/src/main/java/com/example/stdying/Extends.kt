package com.example.stdying

import android.util.Log

/**
 * Created by li on 2019/6/13.
 *
 * @author li
 */
fun <T> T.d(TAG: String = getMethodName()) {
    Log.d(TAG, toString())
}

private fun getMethodName(): String = Thread.currentThread().stackTrace[4].methodName