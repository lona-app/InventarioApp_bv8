package br.com.inventarioapp8.ui.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.inventarioapp8.data.local.entity.Inventory
import br.com.inventarioapp8.databinding.ItemInventoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class InventoryAdapter(
    private val onInventoryClicked: (Inventory) -> Unit
) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    private var inventoryList = emptyList<Inventory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = ItemInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InventoryViewHolder(binding, onInventoryClicked)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bind(inventoryList[position])
    }

    override fun getItemCount() = inventoryList.size

    fun submitList(inventories: List<Inventory>) {
        inventoryList = inventories
        notifyDataSetChanged()
    }

    class InventoryViewHolder(
        private val binding: ItemInventoryBinding,
        private val onInventoryClicked: (Inventory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

        fun bind(inventory: Inventory) {
            binding.textViewInventoryName.text = inventory.name
            binding.textViewInventoryId.text = "ID: ${inventory.id}"
            binding.textViewInventoryDate.text = dateFormat.format(inventory.date)

            itemView.setOnClickListener {
                onInventoryClicked(inventory)
            }
        }
    }
}