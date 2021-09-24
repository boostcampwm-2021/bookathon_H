package com.khnsoft.bookathon_h.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircleView : View {
    private var padding = 20
    private var radius = 0
    private var paint = Paint()
    private var isInit = false
    private var rotationAmount = 0.4f
    private var loc = 0.0f
    private var isRunning = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context)

    fun start() {
        isRunning = true
        invalidate()
    }

    fun stop() {
        isRunning = false
    }

    fun pause() {
        isRunning = false
    }

    fun resume() {
        isRunning = true
        invalidate()
    }

    private fun initClock() {
        radius = min(width, height) / 2 - padding
        isInit = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isInit) {
            initClock()
        }

        drawCircle(canvas, (width / 2).toFloat(), (height / 2).toFloat())
        if (isRunning) {
            drawDot(canvas, loc)
            loc += rotationAmount
            postInvalidateDelayed(10)
        }
    }

    private fun drawCircle(canvas: Canvas, x: Float, y: Float) {
        paint.reset()
        paint.color = Color.CYAN
        paint.strokeWidth = 6.0f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        canvas.drawCircle(x, y, radius.toFloat(), paint)
    }

    private fun drawDot(canvas: Canvas, loc: Float) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        paint.style = Paint.Style.FILL
        canvas.drawCircle(
            (width / 2 + cos(angle) * radius).toFloat(),
            (height / 2 + sin(angle) * radius).toFloat(),
            16f,
            paint
        )
    }
}