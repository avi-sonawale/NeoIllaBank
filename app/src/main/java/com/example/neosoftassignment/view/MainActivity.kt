package com.example.neosoftassignment.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.neosoftassignment.R
import com.example.neosoftassignment.adapter.HorizontalListAdapter
import com.example.neosoftassignment.adapter.VerticalListAdapter
import com.example.neosoftassignment.databinding.ActivityMainBinding
import com.example.neosoftassignment.model.HorizontalAndVerticalListModel
import com.example.neosoftassignment.model.VerticalListModel
import com.example.neosoftassignment.network.Status
import com.example.neosoftassignment.repository.LocalRepository
import com.example.neosoftassignment.util.ViewPagerMarginItemDecoration
import com.example.neosoftassignment.viewmodel.CustomViewModel
import com.example.neosoftassignment.viewmodel.CustomViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CustomViewModel
    private lateinit var horizontalListAdapter: HorizontalListAdapter
    private lateinit var verticalListAdapter: VerticalListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            CustomViewModelFactory(LocalRepository())
        )[CustomViewModel::class.java]

        apiCall()
        setObserver()
    }

    private fun apiCall() {
        viewModel.fetchDataToLoad()
    }

    private fun setObserver() {
        viewModel.listLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "setObserver: LOADING")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "setObserver: SUCCESS")
                    it.data?.let { horizontalAndVerticalListModelResponse ->
                        if (horizontalAndVerticalListModelResponse.isNotEmpty()) {
                            setUpHorizontalViewPagerBannerData(
                                horizontalAndVerticalListModelResponse
                            )
                            setUpVerticalListData(horizontalAndVerticalListModelResponse[0].listItems)
                        } else {
                            showNoDataFound(getString(R.string.no_data_found))
                        }
                    } ?: run {
                        showNoDataFound(getString(R.string.no_data_found))
                    }
                }
                Status.ERROR -> {
                    Log.d(TAG, "setObserver: ERROR")
                }
            }
        })
    }

    private fun setUpHorizontalViewPagerBannerData(horizontalAndVerticalListModel: List<HorizontalAndVerticalListModel>) {
        horizontalListAdapter =
            HorizontalListAdapter(horizontalAndVerticalListModel)
        binding.vpHorizontalBanner.adapter = horizontalListAdapter

        binding.vpHorizontalBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setUpVerticalListData(horizontalAndVerticalListModel[position].listItems)
                searchFilterData(horizontalAndVerticalListModel[position].listItems)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        binding.vpHorizontalBanner.addItemDecoration(
            ViewPagerMarginItemDecoration(
                this@MainActivity,
                R.dimen.viewpager_current_item_horizontal_margin
            )
        )

        setCustomItemWidth()
        //To display pager indicator
        TabLayoutMediator(binding.tabsIndicator, binding.vpHorizontalBanner) { _, _ ->

        }.attach()

    }

    private fun setCustomItemWidth() {
        binding.vpHorizontalBanner.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
            page.alpha = 0.5f + (1 - abs(position))
        }
        binding.vpHorizontalBanner.setPageTransformer(pageTransformer)

    }

    fun setUpVerticalListData(verticalListModel: List<VerticalListModel>) {
        verticalListAdapter = VerticalListAdapter(verticalListModel)
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = verticalListAdapter
            isNestedScrollingEnabled = true
        }
    }

    fun searchFilterData(verticalListModel: List<VerticalListModel>) {
        val filterListData = ArrayList<VerticalListModel>()
        clearSearch(verticalListModel)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(inputText: String?): Boolean {
                filterListData.clear()
                if (verticalListModel.isNotEmpty()) {
                    val data = verticalListModel.filter { item: VerticalListModel ->
                        item.title.lowercase().contains(inputText?.lowercase().toString())
                    }
                    if (data.isEmpty()) {
                        showNoDataFound(getString(R.string.no_search_data_found))
                    } else {
                        hideNoDataFound()
                        filterListData.addAll(data)
                    }
                }
                setUpVerticalListData(filterListData)
                return false
            }
        })

        //Display close button on search bar
        val closeButtonId = resources.getIdentifier("android:id/search_close_btn", null, null)
        val closeButtonImage = binding.searchView.findViewById(closeButtonId) as ImageView
        closeButtonImage.setImageResource(R.drawable.ic_close)

        closeButtonImage.setOnClickListener {
            clearSearch(verticalListModel)
            closeButtonImage.visibility = View.GONE
        }
    }

    private fun clearSearch(verticalListModel: List<VerticalListModel>) {
        binding.searchView.setQuery("", true)
        binding.searchView.clearFocus()
        setUpVerticalListData(verticalListModel)
    }

    private fun showNoDataFound(errorText: String) {
        binding.tvNoData.visibility = View.VISIBLE
        binding.tvNoData.text = errorText
    }

    private fun hideNoDataFound() {
        binding.tvNoData.visibility = View.GONE
    }
}