package com.arash.altafi.notification2.utils.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideUtils(private val context: Context) {

    private var bitmapRequestBuilder: RequestBuilder<Bitmap>? = null
    private var drawableRequestBuilder: RequestBuilder<Drawable>? = null
    private var svgRequestBuilder: RequestBuilder<PictureDrawable>? = null

    private val defaultBitmapOptions by lazy {
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    private val defaultDrawableOptions by lazy {
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    private val defaultSvgOption by lazy {
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    }

    fun getVideoRequestBuilder(
        frameTime: Float = 0.1f
    ): RequestBuilder<Drawable> {

        return Glide.with(context)
            .asDrawable().sizeMultiplier(frameTime)
    }
    fun getDrawableRequestBuilder(
        requestOptions: RequestOptions? = null
    ): RequestBuilder<Drawable> {
        val requestOptionsTemp: RequestOptions =
            requestOptions?.apply(defaultDrawableOptions) ?: defaultDrawableOptions

        if (drawableRequestBuilder == null)
            drawableRequestBuilder = Glide.with(context)
                .asDrawable()
                .apply(requestOptionsTemp)

        return drawableRequestBuilder!!
    }

    fun getSVGRequestBuilder(
        requestOptions: RequestOptions? = null
    ): RequestBuilder<PictureDrawable> {
        val requestOptionsTemp: RequestOptions =
            requestOptions?.apply(defaultSvgOption) ?: defaultSvgOption

        if (svgRequestBuilder == null)
            svgRequestBuilder = GlideApp.with(context)
                .`as`(PictureDrawable::class.java)
                .apply(requestOptionsTemp)
                .listener(SvgSoftwareLayerSetter())


        return svgRequestBuilder!!
    }

    fun getBitmapRequestBuilder(
        requestOptions: RequestOptions? = null
    ): RequestBuilder<Bitmap> {
        val requestOptionsTemp: RequestOptions =
            requestOptions?.apply(defaultBitmapOptions) ?: defaultBitmapOptions

        if (bitmapRequestBuilder == null)
            bitmapRequestBuilder = Glide.with(context)
                .asBitmap()
                .apply(requestOptionsTemp)

        return bitmapRequestBuilder!!
    }
}