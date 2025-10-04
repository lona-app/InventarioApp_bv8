package br.com.inventarioapp8.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // A lista agora é uma variável interna e começa vazia
    private var userList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size

    // Função pública para atualizar a lista de usuários do adapter
    fun submitList(users: List<User>) {
        userList = users
        notifyDataSetChanged() // Notifica o RecyclerView que os dados mudaram
    }

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