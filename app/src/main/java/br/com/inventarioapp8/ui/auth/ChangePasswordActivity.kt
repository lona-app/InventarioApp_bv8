package br.com.inventarioapp8.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // O ID do usuário que precisa trocar a senha será recebido aqui
        val userId = intent.getLongExtra("USER_ID", -1L)

        binding.buttonSaveNewPassword.setOnClickListener {
            // A lógica de salvar será implementada na próxima parte
            Toast.makeText(this, "Funcionalidade em breve! (Usuário ID: $userId)", Toast.LENGTH_LONG).show()
        }
    }
}