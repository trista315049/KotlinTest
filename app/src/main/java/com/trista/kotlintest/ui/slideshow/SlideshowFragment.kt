package com.trista.kotlintest.ui.slideshow

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trista.kotlintest.R
import com.trista.kotlintest.base.BaseFragment
import com.trista.kotlintest.base.Worker
import com.trista.kotlintest.dao.KotDatabase
import com.trista.kotlintest.util.CommentAdapter
import kotlinx.android.synthetic.main.item_home_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SlideshowFragment : BaseFragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var mAdd: Button? = null;
    private var mQuery: Button? = null;
    private var mWorks: RecyclerView? = null;
    private var workersAdater: CommentAdapter? = null
    private var list: MutableList<Worker> = ArrayList<Worker>()

    override fun setLayoutId(): Int {
        return R.layout.fragment_slideshow
    }

    override fun initView(view: View) {
        mAdd = view.findViewById(R.id.add);
        mQuery = view.findViewById(R.id.query);
        mWorks = view.findViewById(R.id.works);
    }

    override fun initData() {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        mWorks?.layoutManager = LinearLayoutManager(activity)
        workersAdater = CommentAdapter.Builder()
            .setDatas(list)
            .setLayoutId(R.layout.item_home_list)
            .bindView(object : CommentAdapter.BindView {
                override fun onBindView(viewHolder: CommentAdapter.MyViewHolder, data: Any?) {
                    val myData: Worker = data as Worker
                    viewHolder.itemView.title.text = myData.name
                }
            })
            .create()
        mWorks?.adapter = workersAdater

        mAdd?.setOnClickListener { v ->
            for (i in list.size..list.size + 20) {
                activity?.let { slideshowViewModel.add(it, "name$i") }
            }
            Toast.makeText(activity, "插入完成", Toast.LENGTH_SHORT).show()
        }
        mQuery?.setOnClickListener { v ->
            activity?.let { queryAll(it) }
        }
    }

    private fun queryAll(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val workers = KotDatabase.getInstance(context).getWorkerDao().queryAllWorkers()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "查询完成", Toast.LENGTH_SHORT).show()
                workers.forEach { println(it.toString()) }
                list.clear()
                list.addAll(workers)
                workersAdater?.addDatas(list)
            }
        }

    }

}
