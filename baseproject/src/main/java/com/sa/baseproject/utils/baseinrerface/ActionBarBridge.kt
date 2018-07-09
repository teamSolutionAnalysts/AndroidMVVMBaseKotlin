package com.sa.baseproject.utils.baseinrerface

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

/**
 * Purpose  - Handle base interface for BaseActivity.
 * @author  - amit.prajapati
 * Created  - 30/10/17
 * Modified - 26/12/17
 */
interface ActionBarBridge {

    fun initActionBar()

    fun setUpActionBarWithUpButton()
    fun setActionBarTitle(title: String)

    fun setActionBarTitle(@StringRes title: Int)

    fun setActionBarSubtitle(subtitle: String)

    fun setActionBarSubtitle(@StringRes subtitle: Int)

    fun setActionBarIcon(icon: Drawable)

    fun setActionBarIcon(@DrawableRes icon: Int)

    fun setActionBarUpButtonEnabled(enabled: Boolean)
}