package com.aluno.task.util

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.aluno.task.R
import com.aluno.task.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar) {
    val activity = activity as AppCompatActivity
    activity.setSupportActionBar(toolbar)
    activity.title = ""
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        activity.onBackPressedDispatcher.onBackPressed()
    }
}

fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: String,
    onClick: (() -> Unit)? = null
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val binding = BottomSheetBinding.inflate(LayoutInflater.from(requireContext()))

    binding.textTitle.setText(titleDialog ?: R.string.text_title_warning)
    binding.textMessage.text = message
    binding.buttonOk.setText(titleButton ?: R.string.title_button_ok)

    binding.buttonOk.setOnClickListener {
        onClick?.invoke()
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setContentView(binding.root)
    bottomSheetDialog.show()
}
