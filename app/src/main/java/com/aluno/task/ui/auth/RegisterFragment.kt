package com.aluno.task.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aluno.task.R
import com.aluno.task.databinding.FragmentRegisterBinding
import com.aluno.task.util.initToolbar
import com.aluno.task.util.showBottomSheet

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonEnviar.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editSenha.text.toString().trim()

        if (email.isEmpty()) {
            showBottomSheet(message = getString(R.string.email_empty_register_fragment))
            return
        }
        if (password.isEmpty()) {
            showBottomSheet(message = getString(R.string.password_empty_register_fragment))
            return
        }

        showBottomSheet(message = getString(R.string.cadastro_sucesso))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
