package com.tipandsplitcal.util.constants

import com.example.tipandsplitcal.util.CurrencyConstantConstant


/**
 * Created by Monil Panchal, B00838558 on 2020-03-06.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * A Singleton utility class to store application level constants.
 */
object AppConstant {
    const val TIP_AMOUNT = "tipAmount"
    const val TOTAL_AMOUNT = "totalAmount"
    const val BILL_AMOUNT = "billAmount"
    const val PER_PERSON_AMOUNT = "perPersonAmount"
    const val IS_BILL_SPLITTED = "isBillSplitted"
    const val DEFAULT_TIP_PERCENTAGE = 15
    const val MAX_BILL_AMOUNT = "99,999"
    const val MIN_BILL_AMOUNT = "0.00"
    const val RESET = "reset"
    const val BILL_AMOUNT_INFO =
        "Enter bill amount. \n ${CurrencyConstantConstant.CANADA_CURRENCY}${AppConstant.MIN_BILL_AMOUNT} to ${CurrencyConstantConstant.CANADA_CURRENCY}${AppConstant.MAX_BILL_AMOUNT}"
    const val SPLIT_INFO = "Split bill between 1-99 friends"
}

