package com.example.paintapplication

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ColorPickerDialog : DialogFragment() {

    interface OnColorSelectedListener {
        fun onColorSelected(color: Int)
    }

    private var listener: OnColorSelectedListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            listener = activity as OnColorSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnColorSelectedListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Pick a color")
        val colors = arrayOf(
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
        )
        builder.setItems(arrayOf("Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Red", "White", "Yellow"), DialogInterface.OnClickListener { dialog, which ->
            listener?.onColorSelected(colors[which])
        })
        return builder.create()
    }
}
