package com.sa.baseproject.appview.permissiondemo.view


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sa.baseproject.R
import com.sa.baseproject.appview.permissiondemo.viewmodel.PermissionDemoViewModel
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.databinding.FragmentPermissionDemoBinding
import com.sa.baseproject.permission.KotlinPermissions
import com.sa.baseproject.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_permission_demo.*

class PermissionDemoFragagmentment : AppFragment() {
    var viewProvider: PermissionDemoViewModel? = null
    override fun initializeComponent(view: View?) {
        btnPermissionDemo.setOnClickListener {
            getLocationPermission()
        }
    }

    private fun getLocationPermission() {
        KotlinPermissions.with((context as AppActivity))
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .onAccepted {
                    if (it.size != 2) {
                        ToastUtils.shortToast(R.string.permissions_denied)
                        return@onAccepted
                    } else {
                        txtMessage.text = "Permission -> Granted"
                    }
                }
                .onDenied {
                    ToastUtils.shortToast(R.string.permissions_denied)
                    txtMessage.text = "Permission -> Denied"
                }
                .onForeverDenied {
                    ToastUtils.shortToast(R.string.permissions_denied)
                    txtMessage.text = "Permission -> Forever Denied"
                }
                .ask()
    }

    override fun pageVisible() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return bind(container)
    }

    private fun bind(container: ViewGroup?): View? {
        viewProvider = ViewModelProviders.of(this).get(PermissionDemoViewModel::class.java)
        var binding = DataBindingUtil.inflate<FragmentPermissionDemoBinding>(LayoutInflater.from(container!!.context), R.layout.fragment_permission_demo, container, false)
        binding.viewProvider = viewProvider
        return binding.root
    }

}
