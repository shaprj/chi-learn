package com.shaprj.chi.learn.components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/*
 * Created by O.Shalaevsky on 5/6/2018
 */
class DrawingView : View {

    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var mPath: Path? = null
    private var mBitmapPaint: Paint? = null
    internal lateinit var context: Context
    private var circlePaint: Paint? = null
    private var circlePath: Path? = null
    val mPaint = Paint()

    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()

    fun setContext(c: Context) {
        this.context = c
    }

    constructor(c: Context) : super(c) {
        init(c)
    }

    private fun init(c: Context) {
        context = c
        mPath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        circlePaint = Paint()
        circlePath = Path()
        circlePaint!!.isAntiAlias = true
        circlePaint!!.color = Color.BLUE
        circlePaint!!.style = Paint.Style.STROKE
        circlePaint!!.strokeJoin = Paint.Join.MITER
        circlePaint!!.strokeWidth = 4f
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context)
    }

    internal constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mBitmap!!, 0f, 0f, mBitmapPaint)
        canvas.drawPath(mPath!!, mPaint)
        canvas.drawPath(circlePath!!, circlePaint!!)
    }

    private fun touch_start(x: Float, y: Float) {
        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touch_move(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y

            circlePath!!.reset()
            circlePath!!.addCircle(mX, mY, 30f, Path.Direction.CW)
        }
    }

    private fun touch_up() {
        mPath!!.lineTo(mX, mY)
        circlePath!!.reset()
        // commit the path to our offscreen
        mCanvas!!.drawPath(mPath!!, mPaint)
        // kill this so we don't double draw
        mPath!!.reset()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touch_start(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touch_move(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touch_up()
                invalidate()
            }
        }
        return true
    }

    companion object {
        private val TOUCH_TOLERANCE = 4f
    }
}