package com.tipandsplitcal

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tipandsplitcal.R
import com.tipandsplitcal.fragment.BillDetailsFragment
import com.tipandsplitcal.fragment.BillSummaryFragment

/**
 * Created by Monil Panchal, B00838558 on 2020-03-04.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * This is the root level Activity which serves an as entry point for the application.
 * This application is based on Single-activity android architecture.
 * It has two fragments: [BillDetailsFragment] and [BillSummaryFragment] attach to it.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.billDetailsFragment,
                    BillDetailsFragment()
                )
                .add(
                    R.id.billSummaryFragment,
                    BillSummaryFragment()
                )
                .commit()
        }
    }

    /**
     * This activity level method is used to hide the soft keyboard(if opened during EditText event)
     * when the user clicks on area outside the EditText.
     */
    override fun dispatchTouchEvent(motionEvent: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val obj =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            obj.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(motionEvent)
    }
}