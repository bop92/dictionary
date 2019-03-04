package com.newb.dictionary.ui.mainview

interface MainActivityContract{

    interface MainPresenter{
        fun getListData(chars: String)
        fun dispose()
    }

    interface MainView{
        fun addToList(words: Collection<String>)
        fun updateList(list: Collection<String>)
        fun isListEmpty() : Boolean
    }
}