package br.com.inventarioapp8.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.core.SessionManager
import br.com.inventarioapp8.databinding.ActivityLoginBinding
import br.com.inventarioapp8.ui.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupListeners()
        setupObservers()
    }

    // ... (dispatchTouchEvent e setupListeners continuam iguais)
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            val identifier = binding.editTextIdentifier.text.toString().trim()
            val password = binding.editTextPassword.text.toString()
            viewModel.login(identifier, password)
        }
    }

    private fun setupObservers() {
        viewModel.loginState.observe(this) { state ->
            if (state == null) {
                return@observe
            }

            when (state.result) {
                LoginResult.SUCCESS -> {
                    // 👇 SALVA A SESSÃO ANTES DE NAVEGAR 👇
                    state.user?.let { user ->
                        sessionManager.saveSession(user.id, user.profile)
                    }
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                // ... (outros 'when' continuam iguais)
                LoginResult.FORCE_PASSWORD_CHANGE -> {
                    val intent = Intent(this, ChangePasswordActivity::class.java)
                    intent.putExtra("USER_ID", state.user?.id)
                    startActivity(intent)
                }
                LoginResult.INVALID_CREDENTIALS -> {
                    Toast.makeText(this, "ID/Usuário ou senha inválidos.", Toast.LENGTH_LONG).show()
                }
                LoginResult.ERROR -> {
                    Toast.makeText(this, "Ocorreu um erro. Tente novamente.", Toast.LENGTH_LONG).show()
                }
            }

            viewModel.onLoginResultHandled()
        }
    }
}