package com.tea.httplibrary.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/*
    Glide工具类
 */
object GlideUtils {
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).apply{ RequestOptions().centerCrop()}.into(imageView)
    }

    fun loadImageFitCenter(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).apply{RequestOptions().fitCenter()}.into(imageView)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView, anim: Int) {
        Glide.with(context).load(url)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
            .transition(DrawableTransitionOptions().transition(anim)).into(imageView)
    }

//    /*
//        当fragment或者activity失去焦点或者destroyed的时候，Glide会自动停止加载相关资源，确保资源不会被浪费
//     */
//    fun loadUrlImage(context: Context, url: String, imageView: ImageView){
//        Glide.with(context).load(url).apply { RequestOptions().placeholder(R.color.bg_normal).error(R.color.bg_normal) }.into(imageView)
//    }

    /**
     * 加载圆形图片
     */
    fun loadCircleImage(context: Context, url: String, imageView: ImageView, placeholderRes:Int) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .apply(RequestOptions().placeholder(placeholderRes))
            .into(imageView)
    }

    fun loadUrlBitmap(context: Context, url: String): Bitmap? {
        return Glide.with(context).asBitmap().load(url).into(100, 60).get()
    }

    fun loadGifImage(context: Context, res: Int, imageView: ImageView, anim: Int) {
        Glide.with(context).load(res)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
            .transition(DrawableTransitionOptions().transition(anim)).into(imageView)
    }
}
