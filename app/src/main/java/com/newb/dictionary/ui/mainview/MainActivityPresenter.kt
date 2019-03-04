package com.newb.dictionary.ui.mainview

import android.content.Context
import android.util.Log
import com.newb.dictionary.data.GrepWords
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivityPresenter(val view: MainActivityContract.MainView, val context: Context): MainActivityContract.MainPresenter {

    companion object {
        const val TAG = "MainActivityPresenter"
    }

    val disposable : CompositeDisposable = CompositeDisposable()

    override fun getListData(chars: String) {
        val grepWords = GrepWords(context.resources)

        val list : Stack<String> = Stack()

        disposable.add(grepWords
            .getWordList()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    val foo = it.toString()
                    if(foo.startsWith(chars)) {
                        list.push(foo)
                        if(list.size == 100){
                            //view.addToList(list)
                            view.updateList(list)
                            list.clear()
                        }
                    }
                },
                {
                    it.printStackTrace()
                },
                {
                    //view.addToList(list)
                    view.updateList(list)
                    Log.d(TAG, "$list")
                }
            )
        )


        /*for(line in grepWords.readLines(chars)){
            view.addToList(line)
        }
        view.updateList()*/
    }

    override fun dispose(){
        disposable.dispose()
    }

    /*Operator onBackpressureBuffer keeps items emitted by source observable in buffer and respond to request() calls from subscriber.*/
}