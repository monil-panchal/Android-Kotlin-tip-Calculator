package com.tipandsplitcal.util.filter

import android.text.InputFilter
import android.text.Spanned


/**
 * Created by Monil Panchal, B00838558 on 2020-03-05.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * A [InputFilter] subclass.
 * Instance of this class is used as a filter to restrict range of a numerical input field
 * Both minimum and maximum numbers are valid numbers, along with the range between them.
 */
open class PersonLimitRestrictionFilter(min: Int, max: Int) : InputFilter {

    private var minimum: Int = 0
    private var maximum: Int = 0

    init {
        this.maximum = max
        this.minimum = min
    }

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(minimum, maximum, input)) return null
        } catch (nfe: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(first: Int, second: Int, third: Int): Boolean {
        return if (second > first) third >= first && third <= second else third >= second && third <= first
    }
}