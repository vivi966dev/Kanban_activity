package com.aluno.task.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.aluno.task.R
import com.aluno.task.data.model.Task
import com.aluno.task.databinding.FragmentRecoverAccountBinding
import com.aluno.task.util.initToolbar
import com.aluno.task.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth


class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        auth = FirebaseAuth.getInstance()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonEnviar.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        if (email.isEmpty()) {
            showBottomSheet(message = getString(R.string.email_empty_recover_fragment))
            return
        }
        recoverAccountUser(email)
    }

    private fun recoverAccountUser(email: String){
        try{
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    binding.progressBar.isVisible=false
                    if(task.isSuccessful){
                        showBottomSheet(message = getString(R.string.text_message_recover_account_fragment))

                    }else{
                        Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
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
