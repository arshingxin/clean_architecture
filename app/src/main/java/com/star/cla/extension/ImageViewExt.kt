package com.star.cla.extension

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.star.cla.R
import com.star.cla.bus.NetStatusBus
import com.star.extension.isFileExist
import com.star.extension.isGif

private val TAG = "ImageViewExt"
private fun String.urlIsEmptyAndFileNotExist() = isEmpty() || !isFileExist()

fun ImageView.toImage(
    url: String,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher,
    errorPaceHolder: Int? = R.mipmap.ic_launcher
) {
    if (url.isGif()) loadGif(url)
    else {
        if (fill) {
            if (isCircle) {
                if (url.urlIsEmptyAndFileNotExist())
                    Glide.with(context)
                        .load(placeHolder)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .centerCrop()
                        .apply(RequestOptions.circleCropTransform())
                        .into(this)
                else
                    Glide.with(context)
                        .load(url)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .centerCrop()
                        .apply(RequestOptions.circleCropTransform())
                        .into(this)
            } else {
                if (url.urlIsEmptyAndFileNotExist())
                    Glide.with(context)
                        .load(placeHolder)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .centerCrop()
                        .into(this)
                else
                    Glide.with(context)
                        .load(url)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .centerCrop()
                        .into(this)
            }
        } else {
            if (isCircle) {
                if (url.urlIsEmptyAndFileNotExist())
                    Glide.with(context)
                        .load(placeHolder)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .fitCenter()
                        .apply(RequestOptions.circleCropTransform())
                        .into(this)
                else
                    Glide.with(context)
                        .load(url)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .fitCenter()
                        .apply(RequestOptions.circleCropTransform())
                        .into(this)
            } else {
                if (url.urlIsEmptyAndFileNotExist())
                    Glide.with(context)
                        .load(placeHolder)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .fitCenter()
                        .into(this)
                else
                    Glide.with(context)
                        .load(url)
                        .dontAnimate()
                        .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                        .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                        .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                        .fitCenter()
                        .into(this)
            }
        }
    }
}

fun ImageView.toImage(
    res: Int,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher,
    errorPaceHolder: Int? = R.mipmap.ic_launcher
) {
    if (fill) {
        if (isCircle) {
            Glide.with(context)
                .load(res)
                .dontAnimate()
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(this)
        } else {
            Glide.with(context)
                .load(res)
                .dontAnimate()
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                .centerCrop()
                .into(this)
        }
    } else {
        if (isCircle) {
            Glide.with(context)
                .load(res)
                .dontAnimate()
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                .fitCenter()
                .apply(RequestOptions.circleCropTransform())
                .into(this)
        } else {
            Glide.with(context)
                .load(res)
                .dontAnimate()
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                .fitCenter()
                .into(this)
        }
    }
}

fun ImageView.toImage(
    url: String,
    errorUseBgMain: Boolean = false,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher,
    errorPaceHolder: Int? = R.mipmap.ic_launcher
) {
    if (fill) {
        if (errorUseBgMain) {
            if (isCircle) {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            } else {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(this)
            }
        } else {
            if (isCircle) {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                    .centerCrop()
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            } else {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                    .centerCrop()
                    .into(this)
            }
        }
    } else {
        if (errorUseBgMain) {
            if (isCircle) {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(R.mipmap.ic_launcher)
                    .fitCenter()
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            } else {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(R.mipmap.ic_launcher)
                    .fitCenter()
                    .into(this)
            }
        } else {
            if (isCircle) {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                    .fitCenter()
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            } else {
                Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                    .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                    .error(if (NetStatusBus.peek()) errorPaceHolder else errorPaceHolder)
                    .fitCenter()
                    .into(this)
            }
        }
    }
}

fun ArrayList<ImageView>.toImage(
    url: String,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher
) {
    toMutableList().toImage(url, fill, isCircle, useCache, placeHolder)
}

fun ArrayList<ImageView>.toImage(
    res: Int,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher
) {
    toMutableList().toImage(res, fill, isCircle, useCache, placeHolder)
}

fun MutableList<ImageView>.toImage(
    url: String,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher
) {
    forEach { it.toImage(url, fill, isCircle, useCache, placeHolder) }
}

fun MutableList<ImageView>.toImage(
    res: Int,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher
) {
    forEach { it.toImage(res, fill, isCircle, useCache, placeHolder) }
}

fun MutableList<ImageView>.toImage(
    url: String,
    errorUseBgMain: Boolean = false,
    fill: Boolean = false,
    isCircle: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher
) {
    forEach { it.toImage(url, errorUseBgMain, fill, isCircle, useCache, placeHolder) }
}

fun MutableList<ImageView>.setImageResource(res: Int) {
    forEach { it.setImageResource(res) }
}

fun ImageView.setSvgColor(@ColorRes color: Int) {
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}

fun MutableList<ImageView>.setVectorImageColor(@ColorRes color: Int) {
    forEach { it.setSvgColor(color) }
}

fun ImageView.loadGif(
    res: Int,
    fill: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher,
    errorPlaceHolder: Int? = R.mipmap.ic_launcher
) {
    if (fill) {
        Glide.with(context)
            .asGif()
            .load(res)
            .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
            .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
            .error(if (NetStatusBus.peek()) errorPlaceHolder else errorPlaceHolder)
            .centerCrop()
            .into(this)
    } else {
        Glide.with(context)
            .asGif()
            .load(res)
            .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
            .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
            .error(if (NetStatusBus.peek()) errorPlaceHolder else errorPlaceHolder)
            .fitCenter()
            .into(this)
    }
}

fun ImageView.loadGif(
    url: String,
    fill: Boolean = false,
    useCache: Boolean = false,
    placeHolder: Int = R.mipmap.ic_launcher,
    errorPlaceHolder: Int? = R.mipmap.ic_launcher
) {
    if (fill) {
        if (url.urlIsEmptyAndFileNotExist())
            Glide.with(context)
                .load(placeHolder)
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPlaceHolder else errorPlaceHolder)
                .centerCrop()
                .into(this)
        else
            Glide.with(context)
                .asGif()
                .load(url)
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPlaceHolder else errorPlaceHolder)
                .centerCrop()
                .into(this)
    } else {
        if (url.urlIsEmptyAndFileNotExist())
            Glide.with(context)
                .load(placeHolder)
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPlaceHolder else errorPlaceHolder)
                .fitCenter()
                .into(this)
        else
            Glide.with(context)
                .asGif()
                .load(url)
                .diskCacheStrategy(if (useCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE)
                .placeholder(if (NetStatusBus.peek()) placeHolder else placeHolder)
                .error(if (NetStatusBus.peek()) errorPlaceHolder else errorPlaceHolder)
                .fitCenter()
                .into(this)
    }
}
