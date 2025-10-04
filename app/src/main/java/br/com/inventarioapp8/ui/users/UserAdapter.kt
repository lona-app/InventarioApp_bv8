package br.com.inventarioapp8.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.databinding.ItemUserBinding

// O adapter agora aceita uma função de clique como parâmetro
class UserAdapter(
    private val onUserClicked: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onUserClicked) // Passa a função de clique para o ViewHolder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size

    fun submitList(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }

    // O ViewHolder agora também recebe a função de clique
    class UserViewHolder(
        private val binding: ItemUserBinding,
        private val onUserClicked: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.textViewUserId.text = "ID: ${user.id}"
            binding.textViewName.text = user.name
            binding.textViewUsername.text = user.username
            binding.textViewProfile.text = user.profile.name
            binding.textViewStatus.text = if (user.isActive) "Ativo" else "Inativo"

            // Define o que acontece quando o item inteiro é clicado
            itemView.setOnClickListener {
                onUserClicked(user)
            }
        }
    }
}