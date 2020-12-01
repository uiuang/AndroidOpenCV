package com.uiuang.learning

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.uiuang.learning.ui.ReadAndWriteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listAdapter = SimpleAdapter(
                this, getData(),
                R.layout.item_simple_list, arrayOf("title"),
                intArrayOf(R.id.tv_title)
        )

        list_view.adapter = listAdapter
        list_view.onItemClickListener = this

    }

    private fun getData(): List<Map<String, Any>> {
        val myData = mutableListOf<Map<String, Any>>()

        myData.add(
                mapOf(
                        "title" to "读取和保存图像",
                        "intent" to activityToIntent(ReadAndWriteActivity::class.java.name)
                )
        )
        return myData
    }
    private fun activityToIntent(activity: String): Intent =
            Intent(Intent.ACTION_VIEW).setClassName(this.packageName, activity)


    override fun onItemClick(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val map = getData() as Map<*, *>

        val intent = Intent(map["intent"] as Intent)
        intent.putExtra("title", map["title"] as String)
        intent.addCategory(Intent.CATEGORY_SAMPLE_CODE)
        startActivity(intent)
    }
}