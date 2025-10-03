package br.com.inventarioapp8.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.inventarioapp8.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}