package br.com.inventarioapp8.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityLoginBinding
import br.com.inventarioapp8.ui.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            val identifier = binding.editTextIdentifier.text.toString().trim()
            val password = binding.editTextPassword.text.toString()
            viewModel.login(identifier, password)
        }
    }

    private fun setupObservers() {
        viewModel.loginResult.observe(this) { result ->
            // O 'when' só executa se o resultado não for nulo
            when (result) {
                LoginResult.SUCCESS -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                LoginResult.INVALID_CREDENTIALS -> {
                    Toast.makeText(this, "ID/Usuário ou senha inválidos.", Toast.LENGTH_LONG).show()
                }
                LoginResult.ERROR -> {
                    Toast.makeText(this, "Ocorreu um erro. Tente novamente.", Toast.LENGTH_LONG).show()
                }
                null -> {
                    // Não faz nada quando o valor é nulo
                }
            }
            // 👇 MUDANÇA: Limpa o evento DEPOIS de ter sido tratado
            // Isso previne que o erro seja mostrado novamente numa segunda tentativa
            if(result != LoginResult.SUCCESS) {
                viewModel.onLoginResultHandled()
            }
        }
    }
}