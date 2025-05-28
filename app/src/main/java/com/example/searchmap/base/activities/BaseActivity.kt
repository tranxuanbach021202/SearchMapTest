package com.example.searchmap.base.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.searchmap.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    protected lateinit var viewModel: VM

    abstract fun getViewModelClass(): Class<VM>
    abstract fun getViewBinding(inflater: LayoutInflater): VB
    abstract fun setupView()
    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[getViewModelClass()]

        setupView()
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun showError(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)

        // Customize snackbar
        val view = snackbar.view
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.orange_red))

        val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        textView.textSize = 14f

        snackbar.show()
    }

}
