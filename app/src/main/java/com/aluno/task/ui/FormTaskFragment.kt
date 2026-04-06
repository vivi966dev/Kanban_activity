package com.aluno.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aluno.task.R
import com.aluno.task.databinding.FragmentFormTaskBinding
import com.aluno.task.util.initToolbar
import com.aluno.task.util.showBottomSheet

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

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
        initListeners()
    }

    private fun initListeners() {
        binding.buttonSalvar.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val description = binding.editDescricao.text.toString().trim()
        if (description.isEmpty()) {
            showBottomSheet(message = getString(R.string.descricao_empty_form_fragment))
            return
        }
        showBottomSheet(message = getString(R.string.tarefa_salva_sucesso))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
