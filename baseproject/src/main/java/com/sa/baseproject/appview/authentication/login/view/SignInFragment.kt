package com.sa.baseproject.appview.authentication.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sa.baseproject.R
import com.sa.baseproject.appview.authentication.AuthenticationActivity
import com.sa.baseproject.appview.authentication.login.viewmodel.SignInViewModel
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.databinding.FragmentSigninBinding
import com.sa.baseproject.utils.DialogUtils


class SignInFragment : AppFragment() {

    var viewModel: SignInViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentSigninBinding>(LayoutInflater.from(container!!.context), R.layout.fragment_signin, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initializeComponent(view: View?) {

        viewModel?.showDialog?.observe(this, Observer {
            if (it != "") {
                if (activity != null)
                    DialogUtils.dialog(activity!!, it)
            }
        })

        viewModel?.showProgress?.observe(this, Observer {
            if (it) {
                (activity as AuthenticationActivity).showProgressBar()
            } else {
                (activity as AuthenticationActivity).hideProgressBar()
            }
        })

        viewModel?.addFragment?.observe(this, Observer {
            (activity as AppActivity).appFragmentManager?.addFragment<Any>(it.first, it.second)
        })

        viewModel?.showError?.observe(this, Observer {
            if (it.isNotEmpty()) {
                DialogUtils.dialog(activity!!, it)
            }
        })

    }

    override fun pageVisible() {

    }

}
