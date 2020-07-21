package com.tipandsplitcal.bl

import com.tipandsplitcal.util.constants.AppConstant

/**
 * Created by Monil Panchal, B00838558 on 2020-03-04.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * This singleton class holds the logic for business logic
 * for computing the tip value, total amount, and amount per person.
 */
class Calculator {

    /**
     * Use this companion object directly by [Calculator.calculate] declaration.
     *
     * {@param: billAmount: Double}
     * {@param: tipPercentage: Int}
     * {@param: totalPerson: Int} , default value is 1
     * returns the computed result in form of a [MutableMap] instance which contains
     * [AppConstant.TIP_AMOUNT], [AppConstant.TOTAL_AMOUNT], [AppConstant.PER_PERSON_AMOUNT], and [AppConstant.BILL_AMOUNT] key-value.
     *
     * The computed values are rounded to 2 digits after the decimal point.
     */
    companion object {
        fun calculate(
            billAmount: Double,
            tipPercentage: Int,
            totalPerson: Int = 1
        ): MutableMap<String, Any> {

            val tipAmount = (billAmount * tipPercentage) / 100
            val totalAmount = (billAmount + tipAmount)
            val perPersonAmount = totalAmount / totalPerson

            val map = mutableMapOf<String, Any>()
            map.put(AppConstant.TIP_AMOUNT, "%.2f".format(tipAmount))
            map.put(AppConstant.TOTAL_AMOUNT, "%.2f".format(totalAmount))
            map.put(AppConstant.PER_PERSON_AMOUNT, "%.2f".format(perPersonAmount))
            map.put(AppConstant.BILL_AMOUNT, "%.2f".format(billAmount))

            return map
        }
    }
}