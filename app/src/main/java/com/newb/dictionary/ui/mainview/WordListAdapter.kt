package com.newb.dictionary.ui.mainview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newb.dictionary.R
import kotlinx.android.synthetic.main.word_list_item.view.*


class WordListAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    companion object {
        const val TAG = "WordListAdapter"
    }


    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.word_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position >= items.size){
            Log.d(TAG, "List Would Be Out Of Bounds")
        } else {
            holder.wordTV?.text = items[position]
        }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val wordTV = view.wordTV
    }

    class WrapContentLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {

        override fun supportsPredictiveItemAnimations(): Boolean {
            return false
        }

    }
}


