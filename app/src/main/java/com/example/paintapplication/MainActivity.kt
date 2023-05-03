package com.example.paintapplication

import android.content.ContentValues
import android.graphics.Bitmap
//import android.graphics.Color.parseColor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.slider.RangeSlider
import java.io.OutputStream

class MainActivity : AppCompatActivity(), ColorPickerDialog2.OnColorSelectedListener {
    private lateinit var paint:DrawView
    private lateinit var save : ImageButton
    private lateinit var color: ImageButton
    private lateinit var stroke: ImageButton
    private lateinit var undo : ImageButton
    private lateinit var rangeSlider : RangeSlider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paint  = findViewById(R.id.draw_view)
        rangeSlider = findViewById(R.id.rangebar)
        undo = findViewById(R.id.btn_undo)
        save = findViewById(R.id.btn_save)
        color = findViewById(R.id.btn_color)
        stroke = findViewById(R.id.btn_stroke)

        undo.setOnClickListener {
            paint.undo()
        }
        save.setOnClickListener{
            var bmp = paint.save()
            var imageOutStream : OutputStream? = null
            var cv = ContentValues()
            cv.put(MediaStore.Images.Media.DISPLAY_NAME, "drawing.png")
            cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

            var uri : Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
            try {
                imageOutStream = contentResolver.openOutputStream(uri!!)
                bmp.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream)
                imageOutStream?.close()
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
        }

        color.setOnClickListener{
            val initialColor = ContextCompat.getColor(this, R.color.black)
            val colorPickerDialog = ColorPickerDialog2(this, initialColor, this)
            colorPickerDialog.show()
        }

        stroke.setOnClickListener{
            if(rangeSlider.visibility== View.VISIBLE)
                rangeSlider.visibility = View.GONE
            else
                rangeSlider.visibility = View.VISIBLE
        }
        rangeSlider.valueFrom = 0.0f
        rangeSlider.valueTo = 100.0f
        rangeSlider.addOnChangeListener{
                slider, value, fromUser ->
            paint.setStrokeWidth2(value.toInt())
        }
        var vto = paint.viewTreeObserver;
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                paint.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = paint.measuredWidth
                val height = paint.measuredHeight
                paint.init1(height, width)
            }
        })

    }

    override fun onColorSelected(color: Int) {
       paint.setColor(color)
    }
}