package br.com.inventarioapp8.ui.inventory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityInventoryListBinding

class InventoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventoryListBinding
    private val viewModel: InventoryListViewModel by viewModels()
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Gerenciar Inventários"

        setupRecyclerView()
        setupListeners()
        setupObservers() // Nova função
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = InventoryAdapter { inventory ->
            Toast.makeText(this, "Inventário clicado: ${inventory.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewInventories.adapter = adapter
    }

    private fun setupListeners() {
        binding.fabAddInventory.setOnClickListener {
            startActivity(Intent(this, AddInventoryActivity::class.java))
        }
    }

    // 👇 NOVA FUNÇÃO PARA OBSERVAR OS DADOS 👇
    private fun setupObservers() {
        viewModel.allInventories.observe(this) { inventories ->
            inventories?.let {
                adapter.submitList(it)
            }
        }
    }
}