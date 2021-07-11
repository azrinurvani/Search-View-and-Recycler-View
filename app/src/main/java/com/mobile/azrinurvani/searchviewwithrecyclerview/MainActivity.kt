package com.mobile.azrinurvani.searchviewwithrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.azrinurvani.searchviewwithrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ItemsAdapter.CLickListener {

    private lateinit var binding : ActivityMainBinding

    val imagesName = arrayOf(
            ItemModel("Image1","Image Desc 1",R.drawable.ic_launcher_background),
            ItemModel("Image2","Image Desc 2",R.drawable.ic_launcher_background),
            ItemModel("Image3","Image Desc 3",R.drawable.ic_launcher_background),
            ItemModel("Image4","Image Desc 4",R.drawable.ic_launcher_background),
            ItemModel("Image5","Image Desc 5",R.drawable.ic_launcher_background),
            ItemModel("Image6","Image Desc 6",R.drawable.ic_launcher_background),
            ItemModel("Image7","Image Desc 7",R.drawable.ic_launcher_background),
            ItemModel("Image8","Image Desc 8",R.drawable.ic_launcher_background),
            ItemModel("Image9","Image Desc 9",R.drawable.ic_launcher_background)
    )

    private val itemModelList = ArrayList<ItemModel>()

    var itemsAdapter : ItemsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        for(items in imagesName){
            itemModelList.add(items)
        }

//      Init  Recycler Adapter
        itemsAdapter = ItemsAdapter(this)
        itemsAdapter?.setDataAdapter(itemModelList)

        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = itemsAdapter
        }
    }

    override fun clickedItem(itemModel: ItemModel?) {
        Log.d(TAG, "clickedItem: items : "+itemModel?.name)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        val menuItem = menu?.findItem(R.id.search_view);

        val searchView = menuItem?.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(filterString: String?): Boolean {
                itemsAdapter?.filter?.filter(filterString)
                return false
            }

            override fun onQueryTextChange(filterString: String?): Boolean {
                itemsAdapter?.filter?.filter(filterString)
                return false
            }

        })
        return true
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}