package com.mobile.azrinurvani.searchviewwithrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mobile.azrinurvani.searchviewwithrecyclerview.databinding.RowItemsBinding
import kotlin.collections.ArrayList

class ItemsAdapter(var clickListener : CLickListener) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() ,Filterable{

    private lateinit var bindingLayout : RowItemsBinding
    var itemModelList = ArrayList<ItemModel>()
    var itemModelListFilter = ArrayList<ItemModel>()

    fun setDataAdapter(itemsList:ArrayList<ItemModel>){
        this.itemModelList = itemsList
        this.itemModelListFilter = itemModelList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        bindingLayout = RowItemsBinding.inflate(inflater,parent,false)
        return ItemViewHolder(bindingLayout)
    }

    override fun getItemCount(): Int {
        return itemModelList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemModelList[position])

        holder.itemView.setOnClickListener {
            clickListener.clickedItem(itemModelList[position])
        }
    }

    inner class ItemViewHolder(binding: RowItemsBinding): RecyclerView.ViewHolder(binding.root) {
        private val binding = binding

        fun bind(itemModel: ItemModel?){
            binding.tvName.text = itemModel?.name
            binding.tvDesc.text = itemModel?.desc
            itemModel?.image?.let { binding.imgItems.setImageResource(it) }
        }
    }

    interface CLickListener{
        fun clickedItem(itemModel: ItemModel?)
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (charSequence==null || charSequence.isEmpty()){
                    filterResults.count = itemModelListFilter.size
                    filterResults.values = itemModelListFilter
                }else{
                   // val searchChr = charSequence.toString().toLowerCase().trim()
                    val searchChr = charSequence.toString()
                    val itemModel = ArrayList<ItemModel>()

                    for (item in itemModelListFilter){
                        if (item.name.contains(searchChr) || item.desc.contains(searchChr)){
                            itemModel.add(item)
                        }
                    }

                    filterResults.count = itemModel.size
                    filterResults.values =  itemModel
                }
                return filterResults
            }


            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                itemModelList = filterResults?.values as ArrayList<ItemModel>
                this@ItemsAdapter.notifyDataSetChanged()
            }

        }
    }

}
