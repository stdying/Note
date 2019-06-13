package com.example.stdying

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate")
        val adapter = MainAdapter(R.layout.item_layout, listOf("测试"))
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = adapter

        adapter.setOnItemClickListener { adapter, view, position ->
            "click".d()
        }

        Thread.currentThread().stackTrace.forEachIndexed { index, stackTraceElement ->
            println("$index")
            println(stackTraceElement.className)
            println(stackTraceElement.fileName)
            println(stackTraceElement.methodName)
            println("==============")
        }


        lifecycle.addObserver(MyLifecycleObserver())
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
