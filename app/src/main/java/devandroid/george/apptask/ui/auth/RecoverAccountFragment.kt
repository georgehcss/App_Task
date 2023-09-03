package devandroid.george.apptask.ui.auth

import android.os.Bundle
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
import devandroid.george.apptask.databinding.FragmentRecoverAccountBinding
import devandroid.george.apptask.helper.FirebaseHelper

class RecoverAccountFragment : Fragment() {
    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btnRecover.setOnClickListener {
            validaDados()
        }
    }

    private fun validaDados() {
        val email = binding.editEmailRecover.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.progressBar.visibility = View.VISIBLE
            sendPasswordFireBase(email)
        } else {
            Toast.makeText(requireContext(), "Informe um E-mail.", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendPasswordFireBase(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_recoverAccountFragment_to_loginFragment)
                    Toast.makeText(
                        requireContext(),
                        "Já pode Verificar seu E-mail",
                        Toast.LENGTH_LONG
                    ).show()

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