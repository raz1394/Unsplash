package com.example.unsplash.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.unsplash.MainApplication
import com.example.unsplash.R
import com.example.unsplash.api.Resource
import com.example.unsplash.dashboard.adapter.DashboardAdapter
import com.example.unsplash.dashboard.datamodel.UnsplashDataItemsModel
import com.example.unsplash.dashboard.viewmodel.DashboardViewModel
import com.example.unsplash.dashboard.viewmodel.DashboardViewModelFactory
import com.example.unsplash.databinding.ActivityDashboardBinding
import com.example.unsplash.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardActivity : AppCompatActivity() {
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        val repository = (application as MainApplication).dashboardRepository
        this.dashboardViewModel = ViewModelProvider(
            this@DashboardActivity, DashboardViewModelFactory(repository)
        )[DashboardViewModel::class.java]
        initializer()

        dashboardViewModel.getImagesResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    dashboardViewModel.addItems(it.value)
                    dashboardAdapter.notifyDataSetChanged()
                }
                is Resource.Failure -> handleApiError(it) {

                }
                else -> {

                }
            }
        }

        binding.nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.

                dashboardViewModel.setCount(1)
                // on below line we are making our progress bar visible.
                binding.progressBar.visibility = View.VISIBLE
                if (dashboardViewModel.getCount() < 10) {
                    // on below line we are again calling
                    // a method to load data in our array list.
                    dashboardViewModel.setPages(1)
                    dashboardViewModel.getImages(dashboardViewModel.getPages(), 10)
                }
            }
        })

        binding.refreshLayout.setOnRefreshListener {
            dashboardViewModel.rowsArrayList.clear()
            dashboardViewModel.clear()
            binding.unsplashRecycler.scrollToPosition(0)
            dashboardViewModel.getImages(1, 10)
            binding.refreshLayout.isRefreshing = false

        }
    }

    private fun initializer() {
        val linearLayoutManager =
            LinearLayoutManager(
                this@DashboardActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        dashboardAdapter = DashboardAdapter(this@DashboardActivity, dashboardViewModel.getItems(), object : ItemListener {
                override fun onItemClick(position: Int, data: ArrayList<UnsplashDataItemsModel>?,action:String) {
                    when(action){
                        "view" -> {
                            data?.get(position)?.let { showDialog(it) }
                        }
                        "download" ->{
                            if (requestWritePermission(this@DashboardActivity, 100)) {
                                lifecycleScope.launch {
                                    val success = withContext(Dispatchers.Default) {
                                        downloadDocs(
                                            applicationContext,
                                            data?.get(position)?.downloadUrl,
                                            "Unsplash_${System.currentTimeMillis()}"
                                        )
                                    }
                                    if (success) {
                                        toast("Image saved to gallery")
                                    } else {
                                        toast("Failed to save image in gallery")
                                    }
                                }
                            }
                        }

                    }

                }
            })
        binding.unsplashRecycler.layoutManager = linearLayoutManager
        binding.unsplashRecycler.adapter = dashboardAdapter
    }

    private fun showDialog(data:UnsplashDataItemsModel) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_layout);
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val authorName = dialog.findViewById<TextView>(R.id.authorTextView)
        val image = dialog.findViewById<ImageView>(R.id.imageIcon)
        val imageId = dialog.findViewById<TextView>(R.id.idTextView)
        authorName.text=data.author
        imageId.text=data.id
        Glide.with(this)
            .load(data.downloadUrl)
            .disallowHardwareConfig()
            .placeholder(R.drawable.splash_icon)
            .into(image)
        dialog.show()
    }


}
