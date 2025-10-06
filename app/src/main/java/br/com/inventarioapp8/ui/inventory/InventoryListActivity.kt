package br.com.inventarioapp8.ui.inventory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityInventoryListBinding

class InventoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventoryListBinding
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Gerenciar Inventários"

        setupRecyclerView()
        setupListeners()
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
        // 👇 MUDANÇA AQUI 👇
        binding.fabAddInventory.setOnClickListener {
            // Agora abre a tela de cadastro de inventário
            startActivity(Intent(this, AddInventoryActivity::class.java))
        }
    }
}