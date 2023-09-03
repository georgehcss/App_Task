package devandroid.george.apptask.components

import android.content.Context
import android.widget.Toast

fun validarDados(
    email: String,
    senha: String,
    requireContext: Context,
    funFireBase: Unit
) {

    if (email.isNotEmpty()) {
        if (senha.isNotEmpty()) {

        } else {
            Toast.makeText(requireContext, "Informe uma Senha.", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(requireContext, "Informe um E-mail.", Toast.LENGTH_LONG).show()
    }
}
