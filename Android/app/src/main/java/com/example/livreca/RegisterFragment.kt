package com.example.livreca

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.livreca.data.AppDatabase
import com.example.livreca.data.User
import com.example.livreca.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val username = binding.etRegisterUsername.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = User(username = username, password = password)
                lifecycleScope.launch {
                    context?.let {
                        val userId = AppDatabase.getDatabase(it).userDao().insert(user)
                        Toast.makeText(it, "Utilizatorul a fost înregistrat cu succes!", Toast.LENGTH_SHORT).show()

                        val action = RegisterFragmentDirections.actionRegisterFragmentToBookListFragment(userId.toInt())
                        findNavController().navigate(action)
                    }
                }
            } else {
                Toast.makeText(context, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

