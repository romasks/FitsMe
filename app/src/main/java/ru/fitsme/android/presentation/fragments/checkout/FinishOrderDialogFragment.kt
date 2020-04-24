package ru.fitsme.android.presentation.fragments.checkout;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import ru.fitsme.android.R
import ru.fitsme.android.app.App
import ru.fitsme.android.databinding.DialogFragmentFinishOrderBinding

class FinishOrderDialogFragment private constructor(private val callback: FinishOrderDialogCallback?)
    : DialogFragment(), FinishOrderDialogFragmentEvents {

    private lateinit var binding: DialogFragmentFinishOrderBinding

    init {
        App.getInstance().di.inject(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(callback: FinishOrderDialogCallback) = FinishOrderDialogFragment(callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_finish_order, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        binding.bindingEvents = this;
    }

    override fun onOkButtonClicked() {
        callback?.onDialogOkButtonClick()
        dismiss()
    }

    interface FinishOrderDialogCallback {
        fun onDialogOkButtonClick()
    }
}
