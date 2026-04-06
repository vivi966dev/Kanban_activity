package com.aluno.task.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aluno.task.R
import com.aluno.task.data.model.Status
import com.aluno.task.data.model.Task
import com.aluno.task.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val taskSelected: (Task, Int) -> Unit
) : ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val SELECT_BACK = 1
        const val SELECT_REMOVE = 2
        const val SELECT_EDIT = 3
        const val SELECT_DETAILS = 4
        const val SELECT_NEXT = 5

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id && oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem && oldItem.description == newItem.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
        setIndicator(holder.binding, task)

        holder.binding.buttonDelete.setOnClickListener {
            taskSelected(task, SELECT_REMOVE)
        }
        holder.binding.buttonEditar.setOnClickListener {
            taskSelected(task, SELECT_EDIT)
        }
        holder.binding.buttonDetails.setOnClickListener {
            taskSelected(task, SELECT_DETAILS)
        }
        holder.binding.buttonBack.setOnClickListener {
            taskSelected(task, SELECT_BACK)
        }
        holder.binding.buttonForward.setOnClickListener {
            taskSelected(task, SELECT_NEXT)
        }
    }

    private fun setIndicator(binding: ItemTaskBinding, task: Task) {
        when (task.status) {
            Status.TODO -> {
                binding.buttonBack.visibility = android.view.View.GONE
                binding.buttonForward.visibility = android.view.View.VISIBLE
            }
            Status.DOING -> {
                binding.buttonBack.visibility = android.view.View.VISIBLE
                binding.buttonForward.visibility = android.view.View.VISIBLE
                binding.buttonBack.setColorFilter(
                    ContextCompat.getColor(context, R.color.color_arrow_back)
                )
                binding.buttonForward.setColorFilter(
                    ContextCompat.getColor(context, R.color.color_arrow_forward)
                )
            }
            Status.DONE -> {
                binding.buttonBack.visibility = android.view.View.VISIBLE
                binding.buttonForward.visibility = android.view.View.GONE
            }
        }
    }

    inner class MyViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textDescription.text = task.description
        }
    }
}
