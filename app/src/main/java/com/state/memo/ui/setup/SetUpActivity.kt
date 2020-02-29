package com.state.memo.ui.setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.state.memo.R
import com.state.memo.ui.MainActivity
import kotlinx.android.synthetic.main.activity_set_up.*

class SetUpActivity : AppCompatActivity() {

    lateinit var viewModel: SetupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up)

        viewModel = ViewModelProviders.of(this).get(SetupViewModel::class.java)

        tvTryAgain.visibility = View.INVISIBLE
        tvTryAgain.setOnClickListener{
            tvTryAgain.visibility = View.INVISIBLE
            fetchAdmin()
        }

        fetchAdmin()
    }


    fun fetchAdmin() = viewModel.fetchAdmin(this, fectched = {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }, failed = {
        tvTryAgain.visibility = View.VISIBLE
    })
}
