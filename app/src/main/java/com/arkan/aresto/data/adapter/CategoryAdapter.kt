package com.arkan.aresto.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.aresto.data.model.Category
import com.arkan.aresto.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    private val data = mutableListOf<Category>()

    fun submitData(items: List<Category>) {
        data.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //counting data size
    override fun getItemCount(): Int  = data.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: Category) {
            binding.ivCategoryImage.setImageResource(item.image)
            binding.tvCategoryName.text = item.name
        }
    }
}