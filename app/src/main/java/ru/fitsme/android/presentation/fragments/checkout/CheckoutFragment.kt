package ru.fitsme.android.presentation.fragments.checkout

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.redmadrobot.inputmask.MaskedTextChangedListener
import ru.fitsme.android.R
import ru.fitsme.android.app.App
import ru.fitsme.android.databinding.FragmentCheckoutBinding
import ru.fitsme.android.domain.entities.order.Order
import ru.fitsme.android.presentation.common.extensions.isEmpty
import ru.fitsme.android.presentation.common.listener.BackClickListener
import ru.fitsme.android.presentation.fragments.base.BaseFragment
import ru.fitsme.android.presentation.fragments.main.MainFragment
import ru.fitsme.android.utils.Constants.RU_PHONE_MASK
import timber.log.Timber

class CheckoutFragment : BaseFragment<CheckoutViewModel>(),
        CheckoutBindingEvents, BackClickListener,
        FinishOrderDialogFragment.FinishOrderDialogCallback,
        ErrorOrderDialogFragment.ErrorOrderDialogCallback {

    private lateinit var binding: FragmentCheckoutBinding
    private var isMaskFilled = false
    private var order: Order? = null

    companion object {
        @JvmStatic
        fun newInstance() = CheckoutFragment()
    }

    override fun getLayout() = R.layout.fragment_checkout

    override fun afterCreateView(view: View) {
        binding = FragmentCheckoutBinding.bind(view)
        binding.bindingEvents = this
        binding.viewModel = viewModel
        binding.appBar.backClickListener = this
        binding.appBar.title = getString(R.string.screen_title_checkout_order)
        setUp()
        setListeners()
    }

    override fun setUpObservers() {
        viewModel.orderLiveData.observe(viewLifecycleOwner, Observer { onLoadOrder(it) })
        viewModel.successMakeOrderLiveData.observe(viewLifecycleOwner, Observer { onSuccessMakeOrder(it) })
        viewModel.errorMakeOrderLiveData.observe(viewLifecycleOwner, Observer { onErrorMakeOrder(it) })
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        parentFragment?.let { (parentFragment as MainFragment).showBottomNavbar() }
    }

    override fun onClickMakeOrder() {
        Timber.tag(javaClass.name).d("isMaskFilled: %s", isMaskFilled)
        if (!isMaskFilled) {
            Toast.makeText(context, R.string.warning_phone_number_is_not_filled, Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.setPhoneNumber(binding.phoneNumber.text.toString())
        if (binding.addressCity.isEmpty() || binding.addressStreet.isEmpty() ||
                binding.addressHouse.isEmpty() || binding.addressApartment.isEmpty()) {
            Toast.makeText(context, R.string.checkout_warning_some_fields_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.onClickMakeOrder()
    }

    override fun onFinishDialogOkButtonClick() {
        goBack()
    }

    override fun onErrorDialogOkButtonClick() {
    }

    private fun setUp() {
        parentFragment?.let { (parentFragment as MainFragment).hideBottomNavbar() }
    }

    private fun setListeners() {
        initPhoneFieldListener(binding.phoneNumber)
    }

    private fun onLoadOrder(order: Order?) {
        this.order = order
        binding.phoneNumber.setText(
                if (order?.phoneNumber?.isNotEmpty() == true) order.phoneNumber
                else App.getInstance().authInfo.login
        )
    }

    private fun onSuccessMakeOrder(successMakeOrder: Boolean) {
        if (successMakeOrder)
            FinishOrderDialogFragment.newInstance(this).show(childFragmentManager, "finishOrder")
    }

    private fun onErrorMakeOrder(errorMakeOrder: Boolean) {
        if (errorMakeOrder)
            ErrorOrderDialogFragment.newInstance(this).show(childFragmentManager, "errorOrder")
    }

    private fun initPhoneFieldListener(phoneField: EditText) {
        MaskedTextChangedListener.installOn(
                phoneField,
                RU_PHONE_MASK,
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
                        isMaskFilled = maskFilled
                        phoneField.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    }
                })
//        phoneField.requestFocus()
    }
}
