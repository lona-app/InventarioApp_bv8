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
    private val viewModel: UserListViewModel by viewModels() // Cria o ViewModel
    private lateinit var adapter: UserAdapter // Declara o adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        setupRecyclerView()
        setupListeners()
        setupObservers() // Nova função
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
        // Inicializa o adapter e o define no RecyclerView
        adapter = UserAdapter()
        binding.recyclerViewUsers.adapter = adapter
    }

    private fun setupListeners() {
        binding.fabAddUser.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    // 👇 NOVA FUNÇÃO PARA OBSERVAR OS DADOS 👇
    private fun setupObservers() {
        // Observa a lista de usuários do ViewModel
        viewModel.allUsers.observe(this) { users ->
            // Quando a lista de usuários muda, atualiza o adapter
            // O 'let' garante que o código só rode se 'users' não for nulo
            users?.let {
                adapter.submitList(it)
            }
        }
    }
}