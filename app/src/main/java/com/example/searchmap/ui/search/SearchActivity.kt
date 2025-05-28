package com.example.searchmap.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchmap.R
import com.example.searchmap.base.activities.BaseActivity
import com.example.searchmap.databinding.ActivitySearchBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {
    private lateinit var searchAdapter: SearchAdapter

    override fun getViewModelClass(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(inflater)
    }


    // Thiết lập khởi tạo giao diện người dùng
    override fun setupView() {
        enableEdgeToEdge()
        setupSearchView()
        setupRecyclerView()
    }

    // Thiết lập các quan sát cho ViewModel
    override fun observeViewModel() {
        viewModel.searchResults.observe(this) { results ->
            searchAdapter.submitData(results, binding.searchEditText.text.toString())
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                showLoadingProgress()
            } else {
                hideLoadingProgress()
            }
        }

        // Quan sát lỗi từ ViewModel
        viewModel.error.observe(this) { error ->
            error?.let {
                showError(it)
            }
        }

        // Quan sát sự kiện điều hướng từ ViewModel
        viewModel.navigationEvent.observe(this) { event ->
            handleNavigationEvent(event)
        }
    }



    // Thiết lập cho thanh tìm kiếm
    private fun setupSearchView() {
        Log.d("SearchActivity", "Setting up SearchView")
        binding.searchEditText.requestFocus()

       // setup edit text listener
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString()?.trim() ?: ""
                Log.d("SearchActivity", "Text changed: $query")

                // Cập nhật hiển thị nút xóa
                binding.clearButton.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE

                if (query.isEmpty()) {
                    viewModel.clearResults()
                } else {
                    viewModel.searchPlace(query)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Xoá text
        binding.clearButton.setOnClickListener {
            binding.searchEditText.text.clear()
            binding.searchEditText.requestFocus()
        }
    }


    // Thiết lập RecyclerView
    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter(
            onItemClick = { place ->
                Log.d("SearchActivity", "Place clicked: ${place.displayName}")
                viewModel.onPlaceDirectionClick(place)
            }
        )
        binding.recyclerView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }


    // Xử lý sự kiện điều hướng
    private fun handleNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OpenGoogleMaps -> {
                openGoogleMaps(this, event.latitude, event.longitude)
            }
        }
    }


    // Mở Google Maps
    private fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
        val uri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude&travelmode=driving")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }
        context.startActivity(intent)
    }




    // Hiển thị loading và ẩn biểu tượng tìm kiếm
    private fun showLoadingProgress() {
        binding.searchIcon.visibility = View.GONE
        binding.loadingProgress.visibility = View.VISIBLE
    }

    // Ẩn loading và hiển thị biểu tượng tìm kiếm
    private fun hideLoadingProgress() {
        binding.searchIcon.visibility = View.VISIBLE
        binding.loadingProgress.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}