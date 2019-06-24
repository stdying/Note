package com.example.stdying

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf("测试", getString(R.string.item_rx))
        val adapter = MainAdapter(R.layout.item_layout, items)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            when (items[position]) {
                getString(R.string.item_rx) -> RxDemoActivity::class.startActivity(this)

            }
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


