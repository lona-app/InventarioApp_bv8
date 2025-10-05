package br.com.inventarioapp8.ui.inventory

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
            // Ação de clique em um item da lista (futuramente abrirá os detalhes)
            Toast.makeText(this, "Inventário clicado: ${inventory.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewInventories.adapter = adapter

        // Por enquanto, a lista estará vazia
        // adapter.submitList(listaVindaDoViewModel)
    }

    private fun setupListeners() {
        binding.fabAddInventory.setOnClickListener {
            // Ação de clique no botão '+' (futuramente abrirá a tela de cadastro)
            Toast.makeText(this, "Adicionar novo inventário em breve!", Toast.LENGTH_SHORT).show()
        }
    }
}