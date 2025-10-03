package br.com.inventarioapp8.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityChangePasswordBinding
import br.com.inventarioapp8.ui.dashboard.DashboardActivity

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel: ChangePasswordViewModel by viewModels()
    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Recebe o ID do usuário enviado pela LoginActivity
        userId = intent.getLongExtra("USER_ID", -1L)
        if (userId == -1L) {
            // Se não receber um ID válido, mostra erro e fecha a tela
            Toast.makeText(this, "Erro: ID de usuário inválido.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.buttonSaveNewPassword.setOnClickListener {
            val newPassword = binding.editTextNewPassword.text.toString()
            val confirmPassword = binding.editTextConfirmNewPassword.text.toString()
            viewModel.updatePassword(userId, newPassword, confirmPassword)
        }
    }

    private fun setupObservers() {
        viewModel.updateResult.observe(this) { result ->
            val message = when (result) {
                UpdateResult.SUCCESS -> {
                    // Após salvar, vai para o Dashboard
                    val intent = Intent(this, DashboardActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    "Senha atualizada com sucesso!"
                }
                UpdateResult.PASSWORD_MISMATCH -> "As senhas não conferem."
                UpdateResult.PASSWORD_TOO_SHORT -> "A nova senha deve ter pelo menos 6 caracteres."
                UpdateResult.ERROR -> "Erro ao atualizar a senha. Tente novamente."
                null -> ""
            }
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}