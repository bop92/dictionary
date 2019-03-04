package com.newb.dictionary.ui.mainview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.newb.dictionary.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityContract.MainView {

    //TODO the english compiler
    //TODO Can be tonal - can analyze potential tones for a linguistic phrase - you can also analyze spoken speech
    //TODO Can be modal - add slang type speak and common english and the like

    companion object{
        const val TAG = "MainActivity"
        const val SEARCH_DONE = "SEARCH_DONE"
        const val SEARCH_ON = "SEARCH_ON"
        const val SEARCH_INIT = "SEARCH_INIT"
    }

    var words: ArrayList<String> = ArrayList()

    val presenter : MainActivityContract.MainPresenter = MainActivityPresenter(this, this)

    var searchState : String = String()

    var isScrolled : Boolean = false

    val wordsListAdapter : WordListAdapter = WordListAdapter(words, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordsList.layoutManager = WordListAdapter.WrapContentLinearLayoutManager(this)
        wordsList.adapter = wordsListAdapter

        SearchButton.setOnClickListener {
            clearList()
            //TODO add back end dictionary call
            if(SearchBar.text.isNotEmpty())
                presenter.getListData(SearchBar.text.toString())
        }

        SearchBar.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(SearchBar.text.isNotEmpty()) {
                    searchState = SEARCH_INIT
                    presenter.getListData(SearchBar.text.toString())
                } else {
                    clearList()
                    updateList(ArrayList())
                }
            }
        })

        wordsList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isScrolled = true
            }
        })
    }

    override fun addToList(words: Collection<String>) {
        if(searchState == SEARCH_INIT){
            searchState = SEARCH_ON
            clearList()
        }
        if(searchState == SEARCH_ON && words.size < 100){
            clearList()
        }
        if(this.words.size < 100){
            searchState = SEARCH_DONE
        }
        this.words.addAll(words)
        Log.d(TAG, "$words")
    }

    fun clearList(){
        /*if(wordsList.isComputingLayout){
            wordsList.stopScroll()
            wordsList.recycledViewPool.clear()
            wordsList.layoutManager?.removeAllViews()
        }*/
        this.words.clear()
        Log.d(TAG, "List Cleared")
    }

    override fun isListEmpty() : Boolean {
        return this.words.isEmpty()
    }

    override fun updateList(list: Collection<String>) {
        addToList(list)
        runOnUiThread {
            /*if(!SearchBar.text.isNotEmpty() || searchState == SEARCH_INIT){
                clearList()
                searchState = SEARCH_ON
            }*/
            if(wordsList.isComputingLayout) {
                val res : ArrayList<String> = ArrayList()
                res.addAll(this.words)
                this.words = res
                wordsList.recycledViewPool.clear()
                wordsList.adapter?.notifyDataSetChanged()
            } else {
                wordsList.recycledViewPool.clear()
                wordsList.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

}
