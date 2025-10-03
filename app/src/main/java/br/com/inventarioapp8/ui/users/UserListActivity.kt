package br.com.inventarioapp8.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityUserListBinding
import br.com.inventarioapp8.ui.auth.RegistrationActivity

class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ativa a seta "voltar" na ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "" // Opcional: remove o título da ActionBar

        setupRecyclerView()
        setupListeners()
    }

    // Captura o clique no botão "voltar" da ActionBar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Finaliza a tela atual, voltando para a anterior
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewUsers.adapter = UserAdapter(emptyList())
    }

    private fun setupListeners() {
        binding.fabAddUser.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}