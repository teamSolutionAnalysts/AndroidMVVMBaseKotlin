package com.sa.baseproject.appview.permissiondemo.view


import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.sa.baseproject.R
import com.sa.baseproject.appview.permissiondemo.viewmodel.PermissionDemoViewModel
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.databinding.FragmentPermissionDemoBinding
import com.sa.baseproject.permission.PermissionUtils
import com.sa.baseproject.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_home.btnPermissionDemo
import kotlinx.android.synthetic.main.fragment_permission_demo.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PermissionDemoFragment : AppFragment() {

    lateinit var mFragmentBinding: FragmentPermissionDemoBinding


    val viewProvider by viewModel<PermissionDemoViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_permission_demo
    }

    override fun preDataBinding(arguments: Bundle?) {
        setHasOptionsMenu(true)
    }

    override fun postDataBinding(binding: ViewDataBinding): ViewDataBinding {
        mFragmentBinding = binding as FragmentPermissionDemoBinding
        mFragmentBinding.viewProvider = viewProvider
        mFragmentBinding.lifecycleOwner = this
        return mFragmentBinding
    }

    override fun initializeComponent(view: View?) {
        btnPermissionDemo.setOnClickListener {
            getLocationPermission()
        }
    }

    private fun getLocationPermission() {
        PermissionUtils.with(context!!, true)
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

}
