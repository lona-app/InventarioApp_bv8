package br.com.inventarioapp8.ui.users

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityUserListBinding
import br.com.inventarioapp8.ui.auth.RegistrationActivity

class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Esconde a barra de título padrão para esta tela
        supportActionBar?.hide()

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        // Por enquanto, a lista continua vazia
        binding.recyclerViewUsers.adapter = UserAdapter(emptyList())
    }

    private fun setupListeners() {
        // Ação de clique para o novo botão flutuante
        binding.fabAddUser.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}