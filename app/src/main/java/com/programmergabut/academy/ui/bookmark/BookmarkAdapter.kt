package com.programmergabut.academy.ui.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.programmergabut.academy.R
import com.programmergabut.academy.data.source.local.entity.CourseEntity
import com.programmergabut.academy.ui.detail.DetailCourseActivity
import kotlinx.android.synthetic.main.items_bookmark.view.*

class BookmarkAdapter(private val callback: BookmarkFragmentCallback) : PagedListAdapter<CourseEntity, BookmarkAdapter.CourseViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CourseEntity>() {
            override fun areItemsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
                return oldItem.courseId == newItem.courseId
            }
            override fun areContentsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_bookmark, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        if (course != null) {
            holder.bind(course)
        }
    }

    fun getSwipedData(swipedPosition: Int): CourseEntity? {
      return getItem(swipedPosition)
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(course: CourseEntity){

            itemView.apply{
                tv_item_title.text = course.title
                tv_item_description.text = course.description
                tv_item_date.text = resources.getString(R.string.deadline_date, course.deadline)

                setOnClickListener {
                    val intent = Intent(context, DetailCourseActivity::class.java).apply {
                        putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    }
                    context.startActivity(intent)
                }

                img_share.setOnClickListener {
                    callback.onShareClick(course)
                }

                Glide.with(context)
                    .load(course.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
            }
        }
    }
}
