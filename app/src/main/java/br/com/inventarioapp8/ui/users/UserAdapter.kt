package br.com.inventarioapp8.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.databinding.ItemUserBinding

class UserAdapter(private val users: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // 1. Cria a representação visual de um item (o ViewHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    // 2. Conecta os dados de um usuário específico à sua representação visual
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    // 3. Informa ao RecyclerView quantos itens existem na lista
    override fun getItemCount() = users.size

    // Classe interna que representa a view de um único item da lista
    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewUserId.text = "ID: ${user.id}"
            binding.textViewName.text = user.name
            binding.textViewUsername.text = user.username
            binding.textViewProfile.text = user.profile.name
            binding.textViewStatus.text = if (user.isActive) "Ativo" else "Inativo"
        }
    }
}