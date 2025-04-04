package com.behnamuix.karsaz.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.Retrofit.model.RetTasks

class TaskAllRecAdapter(private val dataList: List<RetTasks>) :
    RecyclerView.Adapter<TaskAllRecAdapter.MyViewHolder>() {
    var initialX = 0f
    var dX = 0f
    val maxTranslationX = 540f
    val minTranslationX = 0f

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_task_title: TextView = itemView.findViewById(R.id.tv_title_task_card)
        val tv_task_tag: TextView = itemView.findViewById(R.id.tv_tag_task_card)
        val line_task_parity: LinearLayout = itemView.findViewById(R.id.line_parity)
        val tv_task_time: TextView = itemView.findViewById(R.id.tv_time_task_card)
        val tv_task_date: TextView = itemView.findViewById(R.id.tv_date_task_card)
        val tv_task_cat: TextView = itemView.findViewById(R.id.tv_cat_task_card)
        val tv_task_desc: TextView = itemView.findViewById(R.id.tv_desc_task_card)
        val img_slider: ImageView = itemView.findViewById(R.id.img_slider_handle)
        val tvSliderGide: TextView = itemView.findViewById(R.id.tv_slider_gide)
        val img_arrow: ImageView = itemView.findViewById(R.id.img_slider_arrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_fragment, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.tv_task_title.text = currentItem.task_title
        holder.tv_task_tag.text = currentItem.task_tag
        holder.tv_task_cat.text=currentItem.task_cat
        if(currentItem.task_parity!=null){
            when(currentItem.task_parity){
                "بالا"-> holder.line_task_parity.setBackgroundResource(R.drawable.bg_parity_high)
                "متوسط"-> holder.line_task_parity.setBackgroundResource(R.drawable.bg_parity_med)
                "پایین"-> holder.line_task_parity.setBackgroundResource(R.drawable.bg_parity_low)
            }
        }

        holder.tv_task_time.text = currentItem.task_time
        holder.tv_task_date.text = currentItem.task_date
        holder.tv_task_desc.text = currentItem.task_desc
        holder.img_slider.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.parent.requestDisallowInterceptTouchEvent(true)
                    dX = view.x - event.rawX
                    initialX = view.x
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    var newTranslationX = event.rawX + dX
                    if (newTranslationX > initialX + maxTranslationX) {
                        newTranslationX = initialX + maxTranslationX
                    } else if (newTranslationX < initialX + minTranslationX) {
                        newTranslationX = initialX + minTranslationX
                    }
                    view.animate()
                        .x(newTranslationX).setDuration(0).start()
                    true
                }

                MotionEvent.ACTION_UP -> {
                    view.animate().rotationBy(180f).setDuration(500).start()
                    holder.tvSliderGide.text = "وظیفه تکمیل شد"
                    holder.img_arrow.visibility = View.INVISIBLE
                    view.parent.requestDisallowInterceptTouchEvent(false)
                    holder.img_slider.isEnabled = false

                    true
                }

                else -> false
            }
        }

    }

    override fun getItemCount() = dataList.size
}
