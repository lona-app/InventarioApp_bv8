package br.com.inventarioapp8.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityUserListBinding
import br.com.inventarioapp8.ui.auth.RegistrationActivity

class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding
    private val viewModel: UserListViewModel by viewModels()

    // O adapter é inicializado aqui, passando a função de clique
    private val adapter: UserAdapter by lazy {
        UserAdapter { user ->
            // O que fazer quando um usuário for clicado:
            // Abrir a UserDetailsActivity, passando o ID do usuário clicado
            val intent = Intent(this, UserDetailsActivity::class.java)
            intent.putExtra("USER_ID", user.id)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        setupRecyclerView()
        setupListeners()
        setupObservers()
    }

    // ... (onOptionsItemSelected e setupListeners continuam iguais)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewUsers.adapter = adapter
    }

    private fun setupListeners() {
        binding.fabAddUser.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun setupObservers() {
        viewModel.allUsers.observe(this) { users ->
            users?.let {
                adapter.submitList(it)
            }
        }
    }
}