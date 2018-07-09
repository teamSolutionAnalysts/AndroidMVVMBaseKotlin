package com.sa.baseproject.imageview

import android.graphics.*

import com.squareup.picasso.Transformation

/**
 * This class for circle image view.
 */
class CircleTransform : Transformation {

    private val borderwidth = 4
    private var color = "#7f7f7f"

    constructor() {}

    constructor(color: String) {
        this.color = color
    }

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        //        final int width = source.getWidth() + borderwidth;
        //        final int height = source.getHeight() + borderwidth;

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()

        val shader = BitmapShader(squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        //        paint.setShader(null);
        //        paint.setStyle(Paint.Style.STROKE);
        //        paint.setColor(Color.parseColor(color));
        //        paint.setStrokeWidth(borderwidth);
        //        canvas.drawCircle(width / 2, height / 2, r - borderwidth / 2, paint);

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}