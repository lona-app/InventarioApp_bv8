package br.com.inventarioapp8.ui.users

import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.data.local.entity.Profile
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.databinding.ActivityUserDetailsBinding

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private val viewModel: UserDetailsViewModel by viewModels()
    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalhes do Usuário"

        // Recebe o ID do usuário da tela anterior
        userId = intent.getLongExtra("USER_ID", -1L)

        // Se o ID for inválido, fecha a tela
        if (userId == -1L) {
            Toast.makeText(this, "Erro: ID de usuário inválido.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setupObservers()
        // Pede ao ViewModel para carregar os dados do usuário
        viewModel.loadUser(userId)
    }

    private fun setupObservers() {
        // Observa os dados do usuário vindos do ViewModel
        viewModel.user.observe(this) { user ->
            // Se o usuário não for nulo, preenchemos a tela.
            if (user != null) {
                populateUi(user)
            }
        }
    }

    // Função para preencher os campos da tela com os dados do usuário
    private fun populateUi(user: User) {
        binding.textViewUserId.text = user.id.toString()
        binding.editTextName.setText(user.name)
        binding.editTextUsername.setText(user.username)
        binding.switchStatus.isChecked = user.isActive

        // Limpa e recria os RadioButtons para o Perfil
        binding.radioGroupProfile.removeAllViews()
        Profile.entries.forEach { profile ->
            val radioButton = RadioButton(this).apply {
                text = profile.name
                id = profile.ordinal
            }
            binding.radioGroupProfile.addView(radioButton)
            if (profile == user.profile) {
                binding.radioGroupProfile.check(radioButton.id)
            }
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