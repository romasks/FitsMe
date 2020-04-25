package ru.fitsme.android.presentation.fragments.checkout

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.redmadrobot.inputmask.MaskedTextChangedListener
import ru.fitsme.android.R
import ru.fitsme.android.app.App
import ru.fitsme.android.databinding.FragmentCheckoutBinding
import ru.fitsme.android.domain.entities.clothes.ClothesItem
import ru.fitsme.android.domain.entities.order.Order
import ru.fitsme.android.presentation.common.extensions.isEmpty
import ru.fitsme.android.presentation.common.listener.BackClickListener
import ru.fitsme.android.presentation.fragments.base.BaseFragment
import ru.fitsme.android.presentation.fragments.checkout.buttonstate.ButtonState
import ru.fitsme.android.presentation.fragments.checkout.buttonstate.NormalState
import ru.fitsme.android.presentation.fragments.checkout.buttonstate.RemoveNoMatchSizeState
import ru.fitsme.android.presentation.fragments.checkout.buttonstate.SetSizeState
import ru.fitsme.android.presentation.fragments.main.MainFragment
import ru.fitsme.android.presentation.fragments.profile.view.BottomSizeDialogFragment
import ru.fitsme.android.presentation.fragments.profile.view.BottomSizeDialogFragment.BottomSizeDialogCallback
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment.TopSizeDialogCallback
import ru.fitsme.android.utils.Constants.RU_PHONE_MASK
import timber.log.Timber

class CheckoutFragment : BaseFragment<CheckoutViewModel>(),
        CheckoutBindingEvents, BackClickListener,
        FinishOrderDialogFragment.FinishOrderDialogCallback,
        TopSizeDialogCallback, BottomSizeDialogCallback {

    private lateinit var binding: FragmentCheckoutBinding
    private var isMaskFilled = false
    private var order: Order? = null
    private var state: ButtonState? = null

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
        viewModel.noSizeItemsRemovedLiveData.observe(viewLifecycleOwner, Observer { onNoSizeItemsRemoved(it) })
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        parentFragment?.let { (parentFragment as MainFragment).showBottomNavbar() }
    }

    override fun onClickMakeOrder() {
        state?.onButtonClick(viewModel, this)
    }

    override fun onDialogOkButtonClick() {
        viewModel.onClickMakeOrder()
    }

    override fun onTopOkButtonClick() {
        viewModel.setIsNeedShowSizeDialogForTop(false)
    }

    override fun onTopCancelButtonClick() {
    }

    override fun onBottomOkButtonClick() {
        viewModel.setIsNeedShowSizeDialogForBottom(false)
    }

    override fun onBottomCancelButtonClick() {
    }

    fun handleButtonClick() {
        state?.onButtonClick(viewModel, this)
    }

    fun removeNoSizeItems() {
        viewModel.removeNoSizeItems()
    }

    fun setSizeInProfile() {
        val message = App.getInstance().getString(R.string.cart_fragment_message_for_size_dialog)
        when {
            viewModel.isNeedShowSizeDialogForTop.value == true -> showTopSizeDialog(message)
            viewModel.isNeedShowSizeDialogForBottom.value == true -> showBottomSizeDialog(message)
            else -> updateButtonState()
        }
    }

    fun makeOrder() {
        Timber.tag(javaClass.name).d("isMaskFilled: %s", isMaskFilled)
        if (!isMaskFilled) {
            Toast.makeText(context, R.string.warning_phone_number_is_not_filled, Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.addressCity.isEmpty() || binding.addressStreet.isEmpty() ||
                binding.addressHouse.isEmpty() || binding.addressApartment.isEmpty()) {
            Toast.makeText(context, R.string.checkout_warning_some_fields_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        FinishOrderDialogFragment.newInstance(this).show(childFragmentManager, "finishOrder")
    }

    private fun setUp() {
        parentFragment?.let { (parentFragment as MainFragment).hideBottomNavbar() }
    }

    private fun setListeners() {
        binding.phoneNumber.setText(App.getInstance().authInfo.login)
        initPhoneFieldListener(binding.phoneNumber)
    }

    private fun onLoadOrder(order: Order?) {
        this.order = order
        updateButtonState()
    }

    private fun updateButtonState() {
        when {
            viewModel.isNeedShowSizeDialogForTop.value!! -> {
                setState(SetSizeState(binding, this))
            }
            viewModel.isNeedShowSizeDialogForBottom.value!! -> {
                setState(SetSizeState(binding, this))
            }
            hasNoSizeItems(order) -> {
                setState(RemoveNoMatchSizeState(binding, this))
            }
            else -> {
                setState(NormalState(binding, this))
            }
        }
    }

    private fun hasNoSizeItems(order: Order?): Boolean =
            order?.orderItemList?.any { it.clothe.sizeInStock == ClothesItem.SizeInStock.NO }
                    ?: false

    private fun setState(buttonState: ButtonState) {
        state = buttonState
    }

    private fun onSuccessMakeOrder(successMakeOrder: Boolean) {
        if (successMakeOrder) goBack()
    }

    private fun onNoSizeItemsRemoved(isRemoved: Boolean) {
        setState(NormalState(binding, this))
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
        phoneField.requestFocus()
    }

    private fun showTopSizeDialog(message: String) {
        TopSizeDialogFragment.newInstance(this, message).show(fragmentManager(), "topSizeDf")
    }

    private fun showBottomSizeDialog(message: String) {
        BottomSizeDialogFragment.newInstance(this, message).show(fragmentManager(), "bottomSizeDf")
    }

    private fun fragmentManager() = (binding.root.context as AppCompatActivity).supportFragmentManager
}
