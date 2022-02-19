package app.tier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.tier.databinding.ActivityMainBinding
import app.tier.utils.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding
        by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setBackgroundDrawableResource(R.color.background)
    }
}
