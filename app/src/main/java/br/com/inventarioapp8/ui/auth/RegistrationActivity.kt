package br.com.inventarioapp8.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.R
import br.com.inventarioapp8.data.local.entity.Profile
import br.com.inventarioapp8.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        setupListeners()
        setupObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setupListeners() {
        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val username = binding.editTextUsername.text.toString()

            val selectedProfile = when (binding.radioGroupProfile.checkedRadioButtonId) {
                R.id.radio_button_coordinator -> Profile.COORDENADOR
                else -> Profile.USUARIO
            }

            viewModel.registerUser(name, username, selectedProfile)
        }

        // 👇 AÇÃO PARA O NOVO BOTÃO 👇
        binding.buttonResetPassword.setOnClickListener {
            // Como esta é a tela de CADASTRO, o reset ainda não se aplica.
            // Mostramos uma mensagem informativa.
            Toast.makeText(this, "A senha será 'user123'. O reset se aplica na tela de edição.", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupObservers() {
        viewModel.registrationResult.observe(this) { result ->
            val message = when (result) {
                RegistrationResult.SUCCESS -> {
                    finish()
                    "Usuário cadastrado com sucesso!"
                }
                RegistrationResult.EMPTY_FIELDS -> "Nome e Usuário são obrigatórios."
                RegistrationResult.USERNAME_ALREADY_EXISTS -> "Este nome de usuário já está em uso."
                RegistrationResult.ERROR -> "Ocorreu um erro inesperado."
                null -> ""
            }

            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.nextUserId.observe(this) { nextId ->
            // 👇 TEXTO DO ID ALTERADO AQUI 👇
            binding.textViewNewUserId.text = "ID: $nextId"
        }
    }
}