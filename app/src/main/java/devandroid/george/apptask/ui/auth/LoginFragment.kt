package devandroid.george.apptask.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import devandroid.george.apptask.R
import devandroid.george.apptask.databinding.FragmentLoginBinding
import devandroid.george.apptask.helper.FirebaseHelper

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    companion object {
        const val TAG = "uuid.FragmentLogin"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView")
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated")
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnRecovery.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }
        binding.btnLogin.setOnClickListener {
            validaDados()
        }

    }

    private fun validaDados() {
        val email = binding.editEmail.text.toString().trim()
        val senha = binding.editSenha.text.toString().trim()

        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE

                criarContaFireBase(email, senha)

            } else {
                Toast.makeText(requireContext(), "Informe uma Senha.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "Informe um E-mail.", Toast.LENGTH_LONG).show()
        }
    }

    private fun criarContaFireBase(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment2)
                } else {
                    // Log.i("authentication", "loginUser: ${task.exception?.message}")
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}