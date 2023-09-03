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
import devandroid.george.apptask.databinding.FragmentRegisterBinding
import devandroid.george.apptask.helper.FirebaseHelper

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }
    private fun initClicks() {
        binding.btnCreateAccount.setOnClickListener{ validaDados() }
    }

    private fun validaDados() {
        val email = binding.editEmailRegister.text.toString().trim()
        val senha = binding.editSenhaRegister.text.toString().trim()

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
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Usuário criado com Sucesso", Toast.LENGTH_LONG).show()

                } else {
                    binding.progressBar.visibility = View.GONE
                    //Log.i("authentication","loginUser: ${task.exception?.message}")
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