package com.example.stdying

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlin.reflect.KClass

/**
 * Created by li on 2019/6/13.
 *
 * @author li
 */
fun <T> T.d(TAG: String = getMethodName()) {
    Log.d(TAG, toString())
}

private fun getMethodName(): String = Thread.currentThread().stackTrace[4].methodName


fun <T : Activity> KClass<T>.startActivity(context: Context) {
    val intent = Intent(context, this.java)
    context.startActivity(intent)
}

fun <T : Activity> Class<T>.startActivity(context: Context) {
    val intent = Intent(context, this)
    context.startActivity(intent)
}