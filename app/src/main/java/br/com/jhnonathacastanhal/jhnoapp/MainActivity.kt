package br.com.jhnonathacastanhal.jhnoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity;
import android.content.Context
import android.content.Intent;
import android.graphics.Color
import android.net.Uri;
import android.view.Menu
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast
import kotlinx.android.synthetic.main.login.*;

class MainActivity : DebugActivity() {
    private val context: Context get() = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        botao_login.setOnClickListener{onCLickLogin()}

    }

    fun onCLickLogin(){
    var texto_nome = campo_usuario.text.toString()
    Toast.makeText(this,
    "Clicou no botao: $texto_nome",
        Toast.LENGTH_SHORT).show()
    val intent = Intent(this, TelaInicialActivity::class.java)

    intent.putExtra("nome_usuario", texto_nome)
    intent.putExtra("numero", 10)

    startActivity(intent)
    }
}