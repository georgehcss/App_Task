package devandroid.george.apptask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import devandroid.george.apptask.R
import devandroid.george.apptask.databinding.FragmentDoingBinding
import devandroid.george.apptask.databinding.FragmentTodoBinding


class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks() {
        auth = Firebase.auth
        binding.floatingActionButton.setOnClickListener{ addTarefa()}
    }

    private fun addTarefa() {
        findNavController().navigate(R.id.action_homeFragment_to_formtTaskFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}