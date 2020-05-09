package ru.fitsme.android.presentation.fragments.agreement

import android.view.View
import ru.fitsme.android.R
import ru.fitsme.android.databinding.FragmentAgreementBinding
import ru.fitsme.android.presentation.common.listener.BackClickListener
import ru.fitsme.android.presentation.fragments.base.BaseFragment

class AgreementFragment : BaseFragment<AgreementViewModel>(), BackClickListener {

    private lateinit var binding: FragmentAgreementBinding

    companion object {
        @JvmStatic
        fun newInstance() = AgreementFragment()
    }

    override fun getLayout() = R.layout.fragment_agreement

    override fun afterCreateView(view: View) {
        binding = FragmentAgreementBinding.bind(view)
        binding.appBar.backClickListener = this
        binding.appBar.title = getString(R.string.screen_title_agreement)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}
