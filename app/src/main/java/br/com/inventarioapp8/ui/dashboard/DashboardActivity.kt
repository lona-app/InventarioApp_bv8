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
import br.com.inventarioapp8.ui.inventory.InventoryListActivity // IMPORT NOVO
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
        setupPermissions()
    }

    private fun setupPermissions() {
        val userProfile = sessionManager.getUserProfile()
        if (userProfile == Profile.USUARIO) {
            binding.buttonUsers.visibility = View.GONE
        } else {
            binding.buttonUsers.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        // 👇 MUDANÇA AQUI 👇
        binding.buttonInventories.setOnClickListener {
            // Agora abre a tela de lista de inventários
            startActivity(Intent(this, InventoryListActivity::class.java))
        }

        binding.buttonUsers.setOnClickListener {
            startActivity(Intent(this, UserListActivity::class.java))
        }

        binding.buttonLogout.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }
}