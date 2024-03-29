package com.arkan.aresto.presentation.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.arkan.aresto.R
import com.arkan.aresto.data.adapter.CategoryAdapter
import com.arkan.aresto.data.adapter.OnItemCLickedListener
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.adapter.ProductAdapter
import com.arkan.aresto.data.datasource.CategoryDataSource
import com.arkan.aresto.data.datasource.CategoryDataSourceImpl
import com.arkan.aresto.data.datasource.ProductDataSource
import com.arkan.aresto.data.datasource.ProductDataSourceImpl
import com.arkan.aresto.databinding.FragmentHomeBinding
import com.arkan.aresto.presentation.activity.DetailActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val categoryAdapter = CategoryAdapter()
    private var adapter : ProductAdapter? = null
    private val catalogDataSource : ProductDataSource by lazy {
        ProductDataSourceImpl()
    }
    private val categoryDataSource : CategoryDataSource by lazy {
        CategoryDataSourceImpl()
    }
    private var isUsingGridMode : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindProductList(isUsingGridMode)
        setClickAction()
        bindCategoryList()
    }

    private fun setClickAction() {
        binding.layoutListMenu.ivListMenu.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            setButtonImage(isUsingGridMode)
            bindProductList(isUsingGridMode)
        }
    }

    private fun setButtonImage(usingGridMode: Boolean) {
        binding.layoutListMenu.ivListMenu.setImageResource(if (usingGridMode)
            R.drawable.ic_grid_list
        else
            R.drawable.ic_list_grid)
    }

    private fun bindProductList(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid)
            ProductAdapter.MODE_GRID
        else
            ProductAdapter.MODE_LIST
        adapter = ProductAdapter(
            listMode = listMode,
            listener = object : OnItemCLickedListener<Product> {
                override fun onItemClicked(item: Product) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvProduct.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid)
                2
            else
                1)
        }
        adapter?.submitData(catalogDataSource.getProductList())
    }

    private fun bindCategoryList() {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(categoryDataSource.getCategoryList())
    }

    private fun startActivity(item: Product) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRAS_ITEM_ACT, item)
        startActivity(intent)
    }

    private fun navigateToDetail(item: Product) {
        startActivity(item)
    }
}