package com.tipandsplitcal.util.alert

import android.content.Context
import android.widget.Toast


/**
 * Created by Monil Panchal, B00838558 on 2020-03-06.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * A Singleton utility class for throwing alerts using Android's [Toast].
 */
class Alert {
    /**
     * Use this companion object directly by [Alert.alert] declaration.
     */
    companion object {
        fun alert(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}