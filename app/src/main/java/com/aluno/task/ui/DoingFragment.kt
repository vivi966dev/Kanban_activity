package com.aluno.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aluno.task.R
import com.aluno.task.data.model.Status
import com.aluno.task.data.model.Task
import com.aluno.task.databinding.FragmentDoingBinding
import com.aluno.task.ui.adapter.TaskAdapter
import com.aluno.task.util.showBottomSheet

class DoingFragment : Fragment() {

    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getTask()
    }

    private fun initRecyclerView() {
        taskAdapter = TaskAdapter(requireContext()) { task, select ->
            optionSelected(task, select)
        }
        with(binding.recyclerViewTask) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun getTask() {
        val taskList = listOf(
            Task("4", "Desenvolver tela de cadastro de usuário", Status.DOING),
            Task("5", "Integrar TabLayout com ViewPager2", Status.DOING),
            Task("6", "Implementar navegação entre fragments com Navigation Component", Status.DOING),
            Task("7", "Desenvolver adapter do RecyclerView com suporte a diffUtil", Status.DOING),
            Task("8", "Integrar TabLayout com ViewPager2 na tela principal", Status.DOING),
        )
        listEmpty(taskList)
        taskAdapter.submitList(taskList)
    }

    private fun listEmpty(taskList: List<Task>) {
        binding.textListEmpty.visibility = if (taskList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun optionSelected(task: Task, select: Int) {
        when (select) {
            TaskAdapter.SELECT_BACK -> {
                updateTask(task.copy(status = Status.TODO))
            }
            TaskAdapter.SELECT_NEXT -> {
                updateTask(task.copy(status = Status.DONE))
            }
            TaskAdapter.SELECT_REMOVE -> {
                deleteTask(task)
            }
            TaskAdapter.SELECT_EDIT -> {
                showBottomSheet(message = getString(R.string.editar_tarefa_info))
            }
            TaskAdapter.SELECT_DETAILS -> {
                showBottomSheet(message = "Detalhes: ${task.description}")
            }
        }
    }

    private fun updateTask(task: Task) {
        val newList = taskAdapter.currentList.toMutableList()
        newList.remove(newList.find { it.id == task.id })
        taskAdapter.submitList(newList)
        listEmpty(newList)
        showBottomSheet(message = getString(R.string.tarefa_atualizada))
    }

    private fun deleteTask(task: Task) {
        showBottomSheet(
            titleButton = R.string.title_button_confirm,
            message = getString(R.string.confirmar_exclusao)
        ) {
            val newList = taskAdapter.currentList.toMutableList()
            newList.remove(task)
            taskAdapter.submitList(newList)
            listEmpty(newList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
