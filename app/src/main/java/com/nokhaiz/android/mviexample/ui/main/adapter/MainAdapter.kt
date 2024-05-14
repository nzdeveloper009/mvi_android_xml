package com.nokhaiz.android.mviexample.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nokhaiz.android.mviexample.data.model.FakeDTO
import com.nokhaiz.android.mviexample.databinding.MainViewHolderBinding
import com.nokhaiz.android.mviexample.ui.main.adapter.MainAdapter.MyViewHolder

class MainAdapter : RecyclerView.Adapter<MyViewHolder>() {

    var list = mutableListOf<FakeDTO>()

    fun addItems(list: List<FakeDTO>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val viewDataBinding: MainViewHolderBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            MainViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = holder.viewDataBinding
        val item = this.list[position]

        binding.tvTitle.text = item.title
        binding.tvDesc.text = item.body

    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}