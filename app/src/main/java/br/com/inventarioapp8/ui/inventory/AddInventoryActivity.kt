package br.com.inventarioapp8.ui.inventory

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityAddInventoryBinding

class AddInventoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddInventoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Adicionar Inventário"

        binding.buttonSaveInventory.setOnClickListener {
            Toast.makeText(this, "Lógica de salvar em breve!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}