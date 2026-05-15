package com.aluno.task.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aluno.task.R
import com.aluno.task.databinding.FragmentRegisterBinding
import com.aluno.task.util.initToolbar
import com.aluno.task.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    //váriavel que armazena a API de autenticação com firebase

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
        if (email.isNotBlank()) {
            if (password.isNotBlank()) {
                binding.progressBar.isVisible = true
                registerUser(email, password)
            } else {

                showBottomSheet(message = getString(R.string.password_empty_register_fragment))}
            } else{
                showBottomSheet(message = getString(R.string.email_empty_register_fragment))
        }
    }
    private fun registerUser (email: String, password: String){
        try {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        findNavController().navigate(R.id.action_global_homeFragment)
                }else{
                    binding.progressBar.isVisible= false
                    }
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
