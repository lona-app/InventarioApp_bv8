package br.com.inventarioapp8.ui.auth

import android.content.Context
import android.os.Bundle
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

        setupListeners()
        setupObservers()
    }

    // 👇 NOVA FUNÇÃO PARA ESCONDER O TECLADO 👇
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
    }

    private fun setupObservers() {
        viewModel.registrationResult.observe(this) { result ->
            when (result) {
                RegistrationResult.SUCCESS -> {
                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                    finish()
                }
                RegistrationResult.EMPTY_FIELDS -> {
                    Toast.makeText(this, "Nome e Usuário são obrigatórios.", Toast.LENGTH_LONG).show()
                }
                RegistrationResult.USERNAME_ALREADY_EXISTS -> {
                    Toast.makeText(this, "Este nome de usuário já está em uso.", Toast.LENGTH_LONG).show()
                }
                RegistrationResult.ERROR -> {
                    Toast.makeText(this, "Ocorreu um erro inesperado.", Toast.LENGTH_LONG).show()
                }
                null -> {}
            }
        }
    }
}