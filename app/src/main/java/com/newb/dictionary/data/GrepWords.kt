package com.newb.dictionary.data

import android.content.res.Resources
import com.newb.dictionary.R
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.io.BufferedReader
import java.io.FileNotFoundException


class GrepWords (val resources: Resources){

    companion object {
        const val TAG = "GrepWords"
    }

    //TODO add logic to combine multilingual dictionaries
    @Throws(FileNotFoundException::class)
    fun getWordList() : Flowable<String>  {

        val file = BufferedReader(resources.openRawResource(R.raw.dict_en).bufferedReader())

        //TODO improve alg with nonlinear search
        return Flowable.create<String>({ subscriber ->
            while (!subscriber.isCancelled) {
                val line = file.readLine()
                if(!line.isNullOrBlank()) {
                    subscriber.onNext(line)
                }
                else {
                    subscriber.onComplete()
                    file.close()
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    fun getSlangList() : Flowable<String> {

        val file = BufferedReader(resources.openRawResource(R.raw.dict_slang_en).bufferedReader())

        return Flowable.create<String>({ subscriber ->
            while (!subscriber.isCancelled) {
                val line = file.readLine()
                if(!line.isNullOrBlank()) {
                    subscriber.onNext(line)
                }
                else {
                    subscriber.onComplete()
                    file.close()
                }
            }
        }, BackpressureStrategy.BUFFER)

    }

    /*Single.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe(db -> db.playerDao().getAll());*/
}