package com.example.paintapplication

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.SeekBar
import androidx.core.content.ContextCompat

class ColorPickerDialog2(
    context: Context,
    private val initialColor: Int,
    private val onColorSelectedListener: OnColorSelectedListener
) : Dialog(context) {

    private lateinit var colorPreview: View
    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_color_picker)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        colorPreview = findViewById(R.id.colorPreview)
        redSeekBar = findViewById(R.id.redSeekBar)
        greenSeekBar = findViewById(R.id.greenSeekBar)
        blueSeekBar = findViewById(R.id.blueSeekBar)

        colorPreview.setBackgroundColor(initialColor)

        redSeekBar.progress = Color.red(initialColor)
        greenSeekBar.progress = Color.green(initialColor)
        blueSeekBar.progress = Color.blue(initialColor)

        redSeekBar.setOnSeekBarChangeListener(colorSeekBarChangeListener)
        greenSeekBar.setOnSeekBarChangeListener(colorSeekBarChangeListener)
        blueSeekBar.setOnSeekBarChangeListener(colorSeekBarChangeListener)

        val cancelButton: Button = findViewById(R.id.cancelButton)
        val selectButton: Button = findViewById(R.id.selectButton)

        cancelButton.setOnClickListener { dismiss() }
        selectButton.setOnClickListener {
            val selectedColor = Color.rgb(
                redSeekBar.progress,
                greenSeekBar.progress,
                blueSeekBar.progress
            )
            onColorSelectedListener.onColorSelected(selectedColor)
            dismiss()
        }
    }

    private val colorSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val color = Color.rgb(
                redSeekBar.progress,
                greenSeekBar.progress,
                blueSeekBar.progress
            )
            colorPreview.setBackgroundColor(color)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    interface OnColorSelectedListener {
        fun onColorSelected(color: Int)
    }
}

