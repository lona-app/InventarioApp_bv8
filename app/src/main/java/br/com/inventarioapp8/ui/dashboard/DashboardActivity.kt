package br.com.inventarioapp8.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.core.SessionManager
import br.com.inventarioapp8.data.local.entity.Profile
import br.com.inventarioapp8.databinding.ActivityDashboardBinding
import br.com.inventarioapp8.ui.auth.LoginActivity
import br.com.inventarioapp8.ui.users.UserListActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        supportActionBar?.hide()

        setupListeners()
        setupPermissions() // <-- NOVA FUNÇÃO
    }

    private fun setupPermissions() {
        val userProfile = sessionManager.getUserProfile()

        // Se o perfil for USUARIO, esconde o botão
        if (userProfile == Profile.USUARIO) {
            binding.buttonUsers.visibility = View.GONE
        } else {
            binding.buttonUsers.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        binding.buttonInventories.setOnClickListener {
            showToast("Módulo de Inventários em breve!")
        }

        binding.buttonUsers.setOnClickListener {
            startActivity(Intent(this, UserListActivity::class.java))
        }

        binding.buttonLogout.setOnClickListener {
            // 👇 LIMPA A SESSÃO ANTES DE FAZER LOGOUT 👇
            sessionManager.clearSession()

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