package com.dresses.library.base

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Checkable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.*
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dresses.library.utils.*
import com.jess.arms.utils.DeviceUtils


class BaseRecyclerViewHolder(val v:View): BaseViewHolder(v) {
    private fun isBelowM() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.N
    fun setImgPath(@IdRes viewId: Int, path: String): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).disPlay(path)
        return this
    }
    fun setImgPath(@IdRes viewId: Int, path: Uri): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).disPlay(path)
        return this
    }
    //6.0以下 Img不缓存在内存力
    fun mSetImgPath(@IdRes viewId: Int, path: String): BaseRecyclerViewHolder {
        if (isBelowM()){
            getView<ImageView>(viewId).disPlayWithoutCache(path)
        }else{
            getView<ImageView>(viewId).disPlay(path)
        }
        return this
    }
    fun setOnClickListener(l:View.OnClickListener): BaseRecyclerViewHolder{
        v.setOnClickListener(l)
        return this

    }

    fun setOnClickListener(@IdRes viewId: Int,l:View.OnClickListener): BaseRecyclerViewHolder{
        getView<View>(viewId).setOnClickListener(l)
        return this

    }
    fun setChecked(@IdRes viewId: Int,isCheck:Boolean): BaseRecyclerViewHolder{
        (getView<View>(viewId) as Checkable).isChecked = isCheck
        return this
    }
    override fun setText(@IdRes viewId: Int, value: CharSequence?): BaseRecyclerViewHolder {
        getView<TextView>(viewId).text = value
        return this
    }

    override fun setText(@IdRes viewId: Int, @StringRes strId: Int): BaseRecyclerViewHolder {
        getView<TextView>(viewId).setText(strId)
        return this
    }

    override fun setTextColor(@IdRes viewId: Int, @ColorInt color: Int): BaseRecyclerViewHolder {
        getView<TextView>(viewId).setTextColor(color)
        return this
    }

    override fun setTextColorRes(@IdRes viewId: Int, @ColorRes colorRes: Int): BaseRecyclerViewHolder {
        getView<TextView>(viewId).setTextColor(itemView.resources.getColor(colorRes))
        return this
    }

    override fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).setImageResource(imageResId)
        return this
    }

    override fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).setImageDrawable(drawable)
        return this
    }

    override fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).setImageBitmap(bitmap)
        return this
    }

    override fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BaseRecyclerViewHolder {
        getView<View>(viewId).setBackgroundColor(color)
        return this
    }

    override fun setBackgroundResource(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): BaseRecyclerViewHolder {
        getView<View>(viewId).setBackgroundResource(backgroundRes)
        return this
    }

    override fun setVisible(@IdRes viewId: Int, isVisible: Boolean): BaseRecyclerViewHolder {
        val view = getView<View>(viewId)
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        return this
    }

    override fun setGone(@IdRes viewId: Int, isGone: Boolean): BaseRecyclerViewHolder {
        val view = getView<View>(viewId)
        view.visibility = if (isGone) View.GONE else View.VISIBLE
        return this
    }

    override fun setEnabled(@IdRes viewId: Int, isEnabled: Boolean): BaseRecyclerViewHolder {
        getView<View>(viewId).isEnabled = isEnabled
        return this
    }

    fun setImgPathRadiusWithLastHold(viewId: Int, url: String, radius: Int): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).disPlayRoundCornerWithLastHold(url,radius)
        return this
    }
    fun setImgWithoutCash(viewId: Int, url: String): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).disPlayWithoutCache(url)
        return this
    }
    fun changeViewDrawableColor(viewId: Int,color:Int): BaseRecyclerViewHolder {
        (  getView<View>(viewId).background as GradientDrawable).setColor(color)
        return this
    }

    fun setImgPathRadius(viewId: Int, url: String, radius: Int): BaseRecyclerViewHolder {
        getView<ImageView>(viewId).disPlayRoundCorner(url,radius)
        return this
    }
    fun setViewSelect(viewId: Int, isSelect: Boolean): BaseRecyclerViewHolder {
        getView<View>(viewId).isSelected = isSelect
        return this
    }
    fun setItemAlpha( alpha: Float): BaseRecyclerViewHolder {
        itemView.alpha = alpha
        return this
    }

    fun setViewAlpha(viewId: Int, alpha: Float): BaseRecyclerViewHolder {
        getView<View>(viewId).alpha = alpha
        return this
    }
    fun setProgress(viewId: Int,progress:Int): BaseRecyclerViewHolder {
        getView<ProgressBar>(viewId).progress = progress
        return this
    }
    fun setViewPaddingLeft(viewId: Int,paddingLeft:Int): BaseRecyclerViewHolder {
        val v = getView<View>(viewId)
        val pt = v.paddingTop
        val pr = v.paddingRight
        val pb = v.paddingBottom
        getView<View>(viewId).setPadding(dp2px(paddingLeft).toInt(),pt,pr,pb)
        return this
    }
    fun setViewPaddingRightLeft(viewId: Int,paddingRight:Int,paddingLeft:Int): BaseRecyclerViewHolder {
        val v = getView<View>(viewId)
        val pt = v.paddingTop
        val pb = v.paddingBottom
        getView<View>(viewId).setPadding(dp2px(paddingLeft).toInt(),pt,dp2px(paddingRight).toInt(),pb)
        return this
    }
    fun setDrawableLeft(viewId: Int,drawableId:Int): BaseRecyclerViewHolder {
        getView<TextView>(viewId).setCompoundDrawablesWithIntrinsicBounds(drawableId,0,0,0)
        return this
    }
}