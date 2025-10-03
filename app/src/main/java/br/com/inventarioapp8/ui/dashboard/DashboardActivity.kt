package br.com.inventarioapp8.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityDashboardBinding
import br.com.inventarioapp8.ui.auth.LoginActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Esconde a ActionBar (a barra de título no topo) para um visual mais limpo
        supportActionBar?.hide()

        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonInventories.setOnClickListener {
            showToast("Módulo de Inventários em breve!")
        }

        binding.buttonUsers.setOnClickListener {
            showToast("Módulo de Usuários em breve!")
        }

        binding.buttonLogout.setOnClickListener {
            // Cria a intenção de voltar para a tela de login
            val intent = Intent(this, LoginActivity::class.java).apply {
                // Essas flags limpam o histórico de telas, para que o usuário
                // não possa voltar para o dashboard pressionando o botão "voltar" do celular
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}