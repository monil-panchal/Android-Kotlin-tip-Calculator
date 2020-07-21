package com.tipandsplitcal.util.filter

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Monil Panchal, B00838558 on 2020-03-05.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * A [InputFilter] subclass.
 * Instance of this class is used as a filter
 * to restrict the digits entered before and after the decimal point of a number.
 */
open class DecimalAndDigitInputRestriction(beforeZero: Int, afterZero: Int) :
    InputFilter {

    var decimalDigitPattern: Pattern

    init {
        decimalDigitPattern =
            Pattern.compile("[0-9]{0," + (beforeZero - 1) + "}+((\\.[0-9]{0," + (afterZero - 1) + "})?)||(\\.)?");
    }

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = decimalDigitPattern.matcher(dest)
        if (!matcher.matches()) {
            return ""
        }
        return null
    }
}