package com.trista.kotlintest.ui.slideshow

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trista.kotlintest.base.Worker
import com.trista.kotlintest.dao.KotDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SlideshowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    public fun add(context: Context,name: String){
        GlobalScope.launch(Dispatchers.IO) {
            val worker = Worker()
            worker.name = name
            KotDatabase.getInstance(context).getWorkerDao().insertWorker(worker)
            withContext(Dispatchers.Main){
//                Toast.makeText(context, "插入完成", Toast.LENGTH_SHORT).show()
            }

        }
    }

    public fun delete(context: Context,name: String){
        GlobalScope.launch(Dispatchers.IO) {
            KotDatabase.getInstance(context).getWorkerDao().deleteWorker(name)
            withContext(Dispatchers.Main){
                Toast.makeText(context, "删除完成", Toast.LENGTH_SHORT).show()
            }

        }
    }

}