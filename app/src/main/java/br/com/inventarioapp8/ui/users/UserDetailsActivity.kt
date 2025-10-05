package br.com.inventarioapp8.ui.users

import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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

        userId = intent.getLongExtra("USER_ID", -1L)

        if (userId == -1L) {
            Toast.makeText(this, "Erro: ID de usuário inválido.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setupListeners()
        setupObservers()
        viewModel.loadUser(userId)
    }

    private fun setupListeners() {
        binding.buttonSaveChanges.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val username = binding.editTextUsername.text.toString()
            val isActive = binding.switchStatus.isChecked

            val selectedProfileId = binding.radioGroupProfile.checkedRadioButtonId
            val profile = Profile.entries.getOrNull(selectedProfileId) ?: Profile.USUARIO

            viewModel.saveChanges(name, username, profile, isActive)
        }

        binding.buttonResetPassword.setOnClickListener {
            showResetPasswordConfirmationDialog()
        }
    }

    private fun setupObservers() {
        viewModel.user.observe(this) { user ->
            user?.let { populateUi(it) }
        }

        viewModel.updateResult.observe(this) { result ->
            if (result == null) return@observe

            val message = when (result) {
                UserUpdateResult.SUCCESS -> {
                    finish()
                    "Usuário atualizado com sucesso!"
                }
                UserUpdateResult.EMPTY_FIELDS -> "Nome e Usuário não podem estar vazios."
                UserUpdateResult.ERROR -> "Ocorreu um erro ao salvar."
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            viewModel.onUpdateResultHandled()
        }

        viewModel.resetResult.observe(this) { result ->
            if (result == null) return@observe

            val message = when(result) {
                ResetResult.SUCCESS -> "Senha resetada para 'user123'"
                ResetResult.ERROR -> "Erro ao resetar a senha"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            viewModel.onResetResultHandled()
        }
    }

    private fun showResetPasswordConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Reset de Senha")
            .setMessage("Tem certeza que deseja resetar a senha deste usuário para o padrão 'user123'?")
            .setPositiveButton("Confirmar") { _, _ ->
                viewModel.resetPassword(userId)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun populateUi(user: User) {
        binding.textViewUserId.text = user.id.toString()
        binding.editTextName.setText(user.name)
        binding.editTextUsername.setText(user.username)
        binding.switchStatus.isChecked = user.isActive

        binding.radioGroupProfile.removeAllViews()

        val profilesToShow = Profile.entries.filter { profile ->
            profile != Profile.ADMINISTRADOR || user.profile == Profile.ADMINISTRADOR
        }

        profilesToShow.forEach { profile ->
            val radioButton = RadioButton(this).apply {
                text = profile.name
                id = profile.ordinal
                isEnabled = user.id != 1000L
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