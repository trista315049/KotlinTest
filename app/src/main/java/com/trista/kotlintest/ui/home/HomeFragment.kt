package com.trista.kotlintest.ui.home

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.trista.kotlintest.R
import com.trista.kotlintest.base.BaseFragment
import com.trista.kotlintest.http.AsyncCompleteBlock
import com.trista.kotlintest.http.Data
import com.trista.kotlintest.http.HomeListInfo
import com.trista.kotlintest.util.CommentAdapter
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.item_home_list.view.*

class HomeFragment : BaseFragment(), AsyncCompleteBlock<HomeListInfo> {

    private lateinit var homeViewModel: HomeViewModel
    private var page = 0
    private var homeList: LRecyclerView? = null
    private var homeBanner: Banner? = null
    private var list: MutableList<Data> = ArrayList<Data>()
    private var homeListAdater: CommentAdapter? = null
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        homeList = view.findViewById(R.id.home_list)
        homeBanner = view.findViewById(R.id.home_banner)
    }

    override fun initData() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeList?.layoutManager = LinearLayoutManager(activity)
        homeListAdater = CommentAdapter.Builder()
            .setDatas(list)
            .setLayoutId(R.layout.item_home_list)
            .bindView(object : CommentAdapter.BindView {
                override fun onBindView(viewHolder: CommentAdapter.MyViewHolder, data: Any?) {
                    val myData: Data = data as Data
                    viewHolder.itemView.title.text = myData.title
                    var author: String =
                        if (TextUtils.isEmpty(myData.author)) "作者：" + myData.author else "分享人：" + myData.shareUser
                    viewHolder.itemView.content.text = author
                    viewHolder.itemView.is_sc.setOnClickListener {
                        viewHolder.itemView.is_sc.setSelect(true)
                    }
                }
            })
            .create()
        var adapter: LRecyclerViewAdapter = LRecyclerViewAdapter(homeListAdater)
        homeList?.adapter = adapter
        adapter.setOnItemClickListener { view: View, i: Int ->

            val intent = Intent(context, DetailActivity().javaClass)
            intent.putExtra("link", list!![i]!!.link)
            startActivity(intent)
        }

        homeList?.setOnRefreshListener {
            page = 0
            onRefresh()
        }
        homeList?.setOnLoadMoreListener {
            page++
            homeViewModel.getHomeList(page, this)
        }
        homeList?.refresh()
    }


    override fun onComplete(result: Boolean, data: HomeListInfo?, errMessage: String?) {
        Log.e("onComplete", "result$result")
        homeList?.refreshComplete(20)
        if (result) {
            if (page == 0) {
                list.clear()
            }
            list.addAll(data?.datas!!)
            homeListAdater?.addDatas(list)
        }
    }

    fun onRefresh() {
        homeViewModel.getBanner(homeBanner)
        homeViewModel.getHomeList(page, this)
    }
}
