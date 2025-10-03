package br.com.inventarioapp8.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityDashboardBinding
import br.com.inventarioapp8.ui.auth.LoginActivity
import br.com.inventarioapp8.ui.users.UserListActivity // IMPORT NOVO

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonInventories.setOnClickListener {
            showToast("Módulo de Inventários em breve!")
        }

        // 👇 MUDANÇA AQUI 👇
        binding.buttonUsers.setOnClickListener {
            // Agora abre a tela de lista de usuários
            startActivity(Intent(this, UserListActivity::class.java))
        }

        binding.buttonLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}