package com.sa.baseproject.imagepicker

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sa.baseproject.R
/**
 * Created by sanjay.sisodiya on 05/07/2019.
 */

object ImageSlider {

        fun showImageSliderDialog(context : Context, imagesList : ArrayList<String>, position : Int = 1) {
                val dialog = Dialog(context, R.style.DialogTheme)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialog_image_slider)
                dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val viewpager : ViewPager = dialog.findViewById(R.id.viewpager)
                val imageAdapter = ImagesViewerAdapter(context, imagesList)
                viewpager.adapter = imageAdapter
                viewpager.currentItem = position - 1
                dialog.show()
        }

        class ImagesViewerAdapter(var context : Context, private var dataList : ArrayList<String>) : PagerAdapter() {

                override fun instantiateItem(container : ViewGroup, position : Int) : Any {
                        val imagePath = dataList[position]
                        val imageView = ImageView(context)
                        val circularProgressDrawable = CircularProgressDrawable(context)
                        circularProgressDrawable.strokeWidth = 8f
                        circularProgressDrawable.centerRadius = 40f
                        circularProgressDrawable.setColorSchemeColors(context.getColor(R.color.white))
                        circularProgressDrawable.start()
                        Glide.with(context).load(imagePath).apply(RequestOptions().placeholder(circularProgressDrawable)).into(imageView)
                        container.addView(imageView)
                        return imageView
                }

                override fun getCount() : Int {
                        return dataList.size
                }

                override fun isViewFromObject(view : View, obj : Any) : Boolean {
                        return view === obj
                }

                override fun destroyItem(container : ViewGroup, position : Int, `object` : Any) {
                        val view = `object` as View
                        container.removeView(view)
                }

        }
}