package com.coffee.swap

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import java.math.BigDecimal
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @des 拓展方法
 * @author zc
 * @date 2021/7/14 10:56
 **/


/**
 * 跳转页面
 * @param clz 所跳转的目的Activity类
 * @param bundle 跳转所携带的信息
 */
fun startAct(activity: Activity, clz: Class<*>, bundle: Bundle?=null) {
    val intent = Intent(activity, clz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    activity.startActivity(intent)
}

/**
 * 扩展String的copy到系统剪贴板功能
 */
fun String.copy(mContext: Context): Boolean {
    val link = this
    val clipData = ClipData.newPlainText("text", link)
    // 复制
    val cm = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    // 将文本内容放到系统剪贴板里。
    cm.setPrimaryClip(clipData)
    return true
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

fun showWheel(
    mContext: Context,
    mArray: ArrayList<String>,
    mArray1: ArrayList<String>?,
    mTitle: String?,
    listener: OnOptionsSelectListener
) {
    var pickerView = OptionsPickerBuilder(mContext, listener)
        .setCancelText(mContext.resources.getString(
            R.string.cancel))
        .setSubmitText(mContext.resources.getString(
            R.string.sure))
        .setTitleText(mTitle)
        .setSubmitColor(0xFF2164E0.toInt())
        .setContentTextSize(14)
        .setSubCalSize(14)
        .setCancelColor(0xFF2164E0.toInt())
        .setLineSpacingMultiplier(3.0f)
        .setItemVisibleCount(5)
        .build<String>()
    pickerView.setPicker(mArray.toMutableList())
    if (mArray1 != null) {
        pickerView.setPicker(mArray1.toMutableList())
    }
    pickerView.show()
}

fun Int.intToBigDecimal(): BigDecimal {
    return BigDecimal(this)
}

//截取数字
fun getNumbers(content: String?): String? {
    val pattern: Pattern = Pattern.compile("\\d+")
    val matcher: Matcher = pattern.matcher(content)
    while (matcher.find()) {
        return matcher.group(0)
    }
    return ""
}

fun ImageView.loadGlide(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_error_imag_icon)
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun ImageView.loadCircleGlide(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_error_imag_icon)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(this)
}