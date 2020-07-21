package com.tipandsplitcal.fragment

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tipandsplitcal.R
import com.example.tipandsplitcal.util.CurrencyConstantConstant
import com.tipandsplitcal.bl.Calculator
import com.tipandsplitcal.sharedmodel.SharedViewModel
import com.tipandsplitcal.util.alert.Alert
import com.tipandsplitcal.util.constants.AppConstant
import com.tipandsplitcal.util.constants.ErrorConstant
import com.tipandsplitcal.util.filter.DecimalAndDigitInputRestriction
import com.tipandsplitcal.util.filter.PersonLimitRestrictionFilter
import kotlinx.android.synthetic.main.bill_details_fragment.*

/**
 * Created by Monil Panchal, B00838558 on 2020-03-04.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * A [Fragment] subclass.
 * This class manages and binds all UI components related to [bill_details_fragment.xml]
 * It uses [SharedViewModel] ViewModel to communicate to the related fragment [BillSummaryFragment]
 */
class BillDetailsFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private var defaultTipPercent = AppConstant.DEFAULT_TIP_PERCENTAGE;
    private var tipPercentage = 0;
    private var billAmount = 0.0
    private var totalPerson = 1
    private lateinit var sharedDataMap: MutableMap<String, Any>
    private var isBillSplitted = false


    /**
     * Initialize fragment level components to their default values as per the requirement
     */
    private fun initializeDefaultItemValues() {
        person.visibility = View.GONE
        personValue.visibility = View.GONE
        billAmountValue.setHint(CurrencyConstantConstant.CANADA_CURRENCY + CurrencyConstantConstant.DEFAULT_CURREENCY)
        tipSeekBar.progress = defaultTipPercent
        tipValue.text = "$defaultTipPercent%"
        tipPercentage = defaultTipPercent

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bill_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeDefaultItemValues()

        /**
         * Initializing [SharedViewModel] instance
         */
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)


        /**
         * [SharedViewModel] instance observes the data persisted into the common storage ([MutableMap] here)
         * and resets the UI components and values.
         */
        viewModel.getSharedData()
            ?.observe(viewLifecycleOwner, object : Observer<MutableMap<String, Any>> {
                override fun onChanged(t: MutableMap<String, Any>?) {
                    if (t?.containsKey(AppConstant.RESET)!! && t.get(AppConstant.RESET) == true) {
                        clearFields()
                    }
                }
            })

        // Restricting the input for bill amount input field.
        // It can contain 5 digit number or 4 digits before the decimal point and 2 digits after the decimal point.
        billAmountValue.setFilters(arrayOf<InputFilter>(DecimalAndDigitInputRestriction(5, 2)))

        // Restricting the input number of person for bill split.
        // It can take values between 1 to 99, both inclusive.
        personValue.setFilters(arrayOf<InputFilter>(PersonLimitRestrictionFilter(1, 99)))

        // Listener for alerting for input field constraints
        billAmountValue.setOnFocusChangeListener(billAmountValueInfoListener)
        personValue.setOnFocusChangeListener(personValueInfoListener)

        // Input listeners for bill amount person for bill split
        billAmountValue.addTextChangedListener(billAmountValueTextChangeListener)
        personValue.addTextChangedListener(personValueTextChangeListener)

        // Tip seekbar listener for responding to the progress change
        tipSeekBar.setOnSeekBarChangeListener(tipSeekBarChangeListener)

        // Split switch toggle listener
        splitSwitch.setOnCheckedChangeListener(splitChangeListener)

    }

    /**
     * Method for calculating the bill breakdown.
     * Calls [Calculator.calculate] method which returns the result a [MutableMap]
     * This Map is passed to the [SharedViewModel] instance by the respective listeners.
     */
    private fun calculateBill(
        billAmount: Double,
        tipPercentage: Int,
        totalPerson: Int = 1
    ): MutableMap<String, Any> {
        return Calculator.calculate(billAmount, tipPercentage, totalPerson)
    }

    /**
     * Method to reset the UI components to its default value.
     */
    private fun clearFields() {
        sharedDataMap.clear()
        payForValue.text.clear()
        billAmountValue.text.clear()
        billAmountValue.setError(null)
        personValue.setError(null)
        tipSeekBar.progress = AppConstant.DEFAULT_TIP_PERCENTAGE
        splitSwitch.isChecked = false
        view?.clearFocus()
    }


    /* All listeners are implemented below */

    /* Alert related listeners */
    private var billAmountValueInfoListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (billAmountValue.text.isNullOrBlank() && hasFocus) {
                billAmountValue.setError(AppConstant.BILL_AMOUNT_INFO);
            } else {
                billAmountValue.setError(null)
            }
        }
    }

    private var personValueInfoListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (personValue.text.isNullOrBlank() && hasFocus) {
                personValue.setError(AppConstant.SPLIT_INFO);
            } else {
                personValue.setError(null)
            }
        }
    }

    /* Input value related listeners */

    /** This listens to the input change in the bill value,
     * calls the calculateBill() method to get the computed bill values and send it to [SharedViewModel]  instance
     * which is listened by [BillSummaryFragment] fragment instance to show the values in its UI component.
     */
    private var billAmountValueTextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Log.i("bill value", s.toString())
            if (s.isNullOrBlank()) {
                sharedDataMap.clear()
                viewModel.setSharedData(sharedDataMap)
                return
            }
            billAmount = s.toString().toDouble()
            tipPercentage = tipSeekBar.progress
            sharedDataMap = calculateBill(billAmount, tipPercentage)
            sharedDataMap.put(AppConstant.IS_BILL_SPLITTED, isBillSplitted)
            viewModel.setSharedData(sharedDataMap)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    /** This listens to the input change in number of people splitting the bill.
     * Calls the calculateBill() method with appropriate person value to get the computed bill values and send it to [SharedViewModel]  instance
     * which is listened by [BillSummaryFragment] fragment instance to show the values in its UI component.
     */
    private var personValueTextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (!s.isNullOrBlank()) {
                totalPerson = s.toString().toInt()
                sharedDataMap = calculateBill(billAmount, tipPercentage, totalPerson)
                sharedDataMap.put(AppConstant.IS_BILL_SPLITTED, isBillSplitted)
                viewModel.setSharedData(sharedDataMap)
            } else {
                sharedDataMap = calculateBill(billAmount, tipPercentage)
                sharedDataMap.put(AppConstant.IS_BILL_SPLITTED, isBillSplitted)
                viewModel.setSharedData(sharedDataMap)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    /** This listens to the tip percent change.
     * Calls the calculateBill() method with appropriate person value to get the computed bill values and send it to [SharedViewModel]  instance
     * which is listened by [BillSummaryFragment] fragment instance to show the values in its UI component.
     */
    private var tipSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            tipValue.text = "$progress%"
            tipPercentage = tipSeekBar.progress
            if (!billAmountValue.text.isNullOrBlank()) {
                sharedDataMap = calculateBill(billAmount, tipPercentage, totalPerson)
                sharedDataMap.put(AppConstant.IS_BILL_SPLITTED, isBillSplitted)
                viewModel.setSharedData(sharedDataMap)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    /** This listens to listen to the number of people splitting the bill.
     * Calls the calculateBill() method with appropriate person value to get the computed bill values and send it to [SharedViewModel]  instance
     * which is listened by [BillSummaryFragment] fragment instance to show the values in its UI component.
     */
    private var splitChangeListener = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
            if (!billAmountValue.text.isNullOrBlank()) {
                if (isChecked == true) {
                    person.visibility = View.VISIBLE;
                    personValue.visibility = View.VISIBLE;
                    isBillSplitted = true
                    sharedDataMap = calculateBill(billAmount, tipPercentage, totalPerson)
                } else {
                    person.visibility = View.GONE;
                    personValue.visibility = View.GONE;
                    isBillSplitted = false
                }
                sharedDataMap.put(AppConstant.IS_BILL_SPLITTED, isBillSplitted)
                viewModel.setSharedData(sharedDataMap)
            } else if (isChecked == true) {
                Alert.alert(
                    getActivity()!!.getApplicationContext(),
                    ErrorConstant.MISSING_BILL_VALUE_SPLIT
                )
                splitSwitch.isChecked = false
            } else if (isChecked == false) {
                isBillSplitted = false
                person.visibility = View.GONE;
                personValue.visibility = View.GONE;
            }
        }
    }
}