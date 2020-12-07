package com.uiuang.learning.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.uiuang.learning.R

/**
 * Mat操作
 */
class MatOperationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mat_operation)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mat_operation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.bitwise_not
//            -> bitwiseNot(source)
//            R.id.bitwise_and
//            -> bitwiseAnd(source, bgr)
//            R.id.bitwise_xor
//            -> bitwiseXor(source, bgr)
//            R.id.bitwise_or
//            -> bitwiseOr(source, bgr)
//            R.id.add
//            -> add(source, bgr)
//            R.id.subtract
//            -> subtract(source, bgr)
//            R.id.multiply
//            -> multiply(source, bgr)
//            R.id.divide
//            -> divide(source, bgr)
//            R.id.addWeight
//            -> addWeight(source, bgr)
//        }
        return true
    }

}