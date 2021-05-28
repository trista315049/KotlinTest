package com.trista.kotlintest.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *  @author : zhouff
 *  date : 2020/10/12 16:37
 *  description :
 */
class CommentAdapter private constructor(): RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    private var mDatalist: List<*>? = null
    private var mLayoutId:Int? = null
    private var mBindView: BindView? = null
    fun addDatas(lists: List<*>) {
        mDatalist = lists
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(mLayoutId!!,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDatalist!!.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        mBindView?.onBindView(p0, mDatalist?.get(p1)) //在onBindView方法里面实现绑定
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 绑定接口
     */
    interface BindView{
        fun onBindView(viewHolder: MyViewHolder, data: Any?)
    }

    /**
     * 建造者，用来完成adapter的数据组合
     */
    class Builder{

        private var commentAdapter: CommentAdapter = CommentAdapter()


        fun setDatas(lists: List<*>): Builder {
            commentAdapter.mDatalist = lists
            return this
        }

        fun setLayoutId(layoutId:Int): Builder {
            commentAdapter.mLayoutId = layoutId
            return this
        }

        fun bindView(bindView: BindView): Builder {
            commentAdapter.mBindView = bindView
            return this
        }

        fun create(): CommentAdapter {
            return commentAdapter
        }
    }

}