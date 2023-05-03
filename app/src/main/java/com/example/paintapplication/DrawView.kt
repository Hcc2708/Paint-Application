package com.example.paintapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

lateinit var mBitmap:Bitmap
class DrawView(context : Context?, attrs :AttributeSet) : View(context, attrs) {
    val touch_tol = 4
    var m : Float = 0.0f
    var n : Float = 0.0f
    var mPath : Path = Path()
    var paint : Paint
    var arrayList = ArrayList<stk>()
    var cColor : Int = 0
    var strokeWidth : Int = 0
    var mCanvas : Canvas = Canvas()
    var mBitmapPaint  = Paint(Paint.DITHER_FLAG)
    init {
        paint = Paint()
        paint.setAntiAlias(true)
        paint.setDither(true)
        paint.setColor(Color.GREEN)
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeJoin(Paint.Join.ROUND)
        paint.setStrokeCap(Paint.Cap.ROUND)
        paint.setAlpha(0xff)
    }

    fun init1(height: Int, width: Int){
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        cColor = Color.GREEN
        strokeWidth = 20;

    }
    fun setColor(color:Int)
    {
        cColor = color
    }
    fun setStrokeWidth2(width: Int)
    {
        strokeWidth = width
    }
    fun undo(){
        if(arrayList.size != 0) {
            arrayList.removeAt(arrayList.size - 1)
            invalidate()
        }
    }
    fun save() : Bitmap{
        return mBitmap
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()

        var backgroundColor = Color.WHITE
        mCanvas.drawColor(backgroundColor)

        for(fp in arrayList){
            paint.setColor(fp.color1)
            paint.setStrokeWidth(fp.strokeWidth1.toFloat())
            mCanvas.drawPath(fp.path1, paint)
        }
        canvas?.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas?.restore()

    }

    fun touchStart(x : Float, y: Float )
    {
        mPath = Path()
        var fp = stk(cColor, strokeWidth, mPath)
        arrayList.add(fp)
        mPath.reset()
        mPath.moveTo(x, y)
        m = x
        n = y
    }
    fun touchMove(x : Float, y: Float)
    {
        var dx = Math.abs(x- m)
        var dy = Math.abs(y - n)
        if(dx >= touch_tol || dy >= touch_tol){
            mPath.quadTo(m , n, (x + m)/2, (y+n)/2)
            m = x
            n = y
        }
    }
    fun touchUp() {
        mPath.lineTo(m, n)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var x : Float  = event!!.getX()
        var y : Float = event!!.getY()

        when(event?.getAction()){
            MotionEvent.ACTION_DOWN ->{
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP ->{
                touchUp()
                invalidate()
            }
        }
        return true
    }
}