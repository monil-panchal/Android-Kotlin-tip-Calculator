package com.tipandsplitcal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tipandsplitcal.R
import com.example.tipandsplitcal.util.CurrencyConstantConstant
import com.tipandsplitcal.sharedmodel.SharedViewModel
import com.tipandsplitcal.util.constants.AppConstant
import kotlinx.android.synthetic.main.bill_summary_fragment.*

/**
 * Created by Monil Panchal, B00838558 on 2020-03-04.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * A [Fragment] subclass.
 * This class manages and binds all UI components related to [bill_summary_fragment.xml]
 * It uses [SharedViewModel] ViewModel to communicate to the related fragment [BillDetailsFragment]
 */
class BillSummaryFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private var sharedDataMap: MutableMap<String, Any>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bill_summary_fragment, container, false)
    }

    /**
     * Initialize fragment level components to their default values as per the requirement
     */
    private fun initializeDefaultItemValues() {
        perPerson.visibility = View.GONE
        perPersonAmountValue.visibility = View.GONE
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
         * This listens to the changes done in input fields from [BillDetailsFragment], which sends the computed values
         * and it is rendered in fragment component.
         */
        viewModel.getSharedData()?.observe(viewLifecycleOwner, object :
            Observer<MutableMap<String, Any>> {
            override fun onChanged(t: MutableMap<String, Any>?) {
                if (t != null) {
                    sharedDataMap = t
                }
                /**
                 * if the shared map is not empty, render the bill breakdown components.
                 */
                if (sharedDataMap?.size!! > 0) {
                    billAmountFinal.text =
                        CurrencyConstantConstant.CANADA_CURRENCY + sharedDataMap?.get(AppConstant.BILL_AMOUNT).toString()
                    totalTipAmountValue.text =
                        CurrencyConstantConstant.CANADA_CURRENCY + sharedDataMap?.get(AppConstant.TIP_AMOUNT).toString()
                    totalAmountValue.text =
                        CurrencyConstantConstant.CANADA_CURRENCY + sharedDataMap?.get(AppConstant.TOTAL_AMOUNT).toString()


                    if (sharedDataMap?.containsKey(AppConstant.IS_BILL_SPLITTED)!! && sharedDataMap?.get(
                            AppConstant.IS_BILL_SPLITTED
                        ) == true
                    ) {
                        perPerson.visibility = View.VISIBLE
                        perPersonAmountValue.visibility = View.VISIBLE
                        perPersonAmountValue.text =
                            CurrencyConstantConstant.CANADA_CURRENCY + sharedDataMap?.get(
                                AppConstant.PER_PERSON_AMOUNT
                            ).toString()
                    } else {
                        perPerson.visibility = View.GONE
                        perPersonAmountValue.visibility = View.GONE
                    }
                } else {
                    clearFields()
                }
            }
        })

        /** This listens to the reset button click event and send reset flag to [BillDetailsFragment] via [SharedViewModel]  instance
         * to reset the UI components it its default values.
         */
        resetButton.setOnClickListener {
            if (sharedDataMap != null) {
                sharedDataMap?.put(AppConstant.RESET, true)
                viewModel.setSharedData(sharedDataMap!!)
                clearFields()
            }
        }
    }

    /**
     * Method to reset the UI components to its default value.
     */
    private fun clearFields() {
        billAmountFinal.setText("")
        totalTipAmountValue.setText("")
        totalAmountValue.setText("")
        perPerson.visibility = View.GONE
        perPersonAmountValue.visibility = View.GONE
    }
}