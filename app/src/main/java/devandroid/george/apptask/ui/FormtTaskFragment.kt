package devandroid.george.apptask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import devandroid.george.apptask.R
import devandroid.george.apptask.databinding.FragmentFormtTaskBinding
import devandroid.george.apptask.helper.FirebaseHelper
import devandroid.george.apptask.model.Task

class FormtTaskFragment : Fragment() {
    private val args: FormtTaskFragmentArgs by navArgs()


    private var _binding: FragmentFormtTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask: Boolean = true
    private var statusTask: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormtTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()

        getArgs()
    }

    private fun getArgs() {
        args.task.let {
            if (it != null) {
                task = it
                configTask()
            }
        }
    }

    private fun configTask() {
        newTask = false
        statusTask = task.status
        binding.txtToolbar.text = "Editando Tarefa..."
        binding.editDescription.setText(task.description)
        setStatus()
    }

    private fun setStatus() {
        binding.radioGroup.check(
            when (task.status) {
                0 -> R.id.rbTodo
                1 -> R.id.rbDoing
                else -> R.id.rbDone
            }
        )
    }

    private fun initClicks() {
        binding.btnSaveTarefa.setOnClickListener { validarData() }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            statusTask = when (id) {
                R.id.rbTodo -> 0
                R.id.rbDoing -> 1
                else -> 2
            }
        }
    }

    private fun validarData() {

        val description = binding.editDescription.text.toString().trim()
        if (description.isNotEmpty()) {
            binding.progressBar.isVisible = true

            if (newTask) task = Task()
            task.description = description
            task.status = statusTask

            saveTask()

        } else {
            Toast.makeText(
                requireContext(),
                R.string.txt_editText_enter_a_new_task,
                Toast.LENGTH_LONG
            ).show()

        }
    }

    private fun saveTask() {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) { // Nova tarefa
                    if (newTask) {
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.txt_save_task_sucess_form_task_fragment),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else { // Editando Tarefa
                        findNavController().popBackStack()
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.update_task_sucess_form_task_fragment),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(requireContext(),
                        getString(R.string.text_erro_save_task_form_task_fragment), Toast.LENGTH_SHORT)
                        .show()
                }
            }.addOnFailureListener {
                binding.progressBar.isVisible = true
                Toast.makeText(requireContext(), getString(R.string.text_erro_save_task_form_task_fragment), Toast.LENGTH_SHORT)
                    .show()
            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}