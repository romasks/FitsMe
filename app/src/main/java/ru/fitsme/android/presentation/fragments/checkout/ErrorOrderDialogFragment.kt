package ru.fitsme.android.presentation.fragments.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import ru.fitsme.android.R
import ru.fitsme.android.app.App
import ru.fitsme.android.databinding.DialogFragmentErrorOrderBinding

class ErrorOrderDialogFragment private constructor(private val callback: ErrorOrderDialogCallback?)
    : DialogFragment(), ErrorOrderDialogFragmentEvents {

    private lateinit var binding: DialogFragmentErrorOrderBinding

    init {
        App.getInstance().di.inject(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(callback: ErrorOrderDialogCallback) = ErrorOrderDialogFragment(callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_error_order, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindingEvents = this;
    }

    override fun onOkButtonClicked() {
        callback?.onErrorDialogOkButtonClick()
        dismiss()
    }

    interface ErrorOrderDialogCallback {
        fun onErrorDialogOkButtonClick()
    }
}
