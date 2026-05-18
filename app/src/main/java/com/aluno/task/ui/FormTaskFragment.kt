package com.aluno.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.aluno.task.R
import com.aluno.task.data.model.Status
import com.aluno.task.data.model.Task
import com.aluno.task.databinding.FragmentFormTaskBinding
import com.aluno.task.util.initToolbar
import com.aluno.task.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference





class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask:Boolean= true
    private var status: Status = Status.TODO

    private lateinit var  reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        auth = FirebaseAuth.getInstance()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonSalvar.setOnClickListener {
            validateData()
        }
        binding.radioGroup.setOnCheckedChangeListener{_, id-> status=
            when(id){
                R.id.rbTodo ->Status.TODO
                R.id.rbDoing ->Status.TODO
                else -> Status.DONE
            }
        }
    }

    private fun validateData() {
        val description = binding.editDescricao.text.toString().trim()
        if (description.isNotBlank()) {
            binding.progressBar.isVisible = true
            if (newTask) task = Task()
            task.id = reference.database.reference.push().key ?: " "
            task.description = description
            task.status = status
            saveTask()
        }else{
            showBottomSheet(message = getString(R.string.tarefa_salva_sucesso))
        }
    }

    private fun saveTask(){
        reference
            .child("task")
            .child(auth.currentUser?.uid ?:" ")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful){
                    Toast.makeText(
                        requireContext(),
                        R.string.text_save_sucess_form_task_fragment,
                        Toast.LENGTH_SHORT).show()
                    if(newTask){
                        findNavController().popBackStack()
                    }else{
                        binding.progressBar.isVisible = false
                        showBottomSheet(message = getString(R.string.error_generic))
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
