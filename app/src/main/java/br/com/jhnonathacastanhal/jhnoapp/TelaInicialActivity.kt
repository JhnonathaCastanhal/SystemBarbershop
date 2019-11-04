package br.com.jhnonathacastanhal.jhnoapp

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaInicialActivity : DebugActivity(),
    NavigationView.OnNavigationItemSelectedListener{


    private val context: Context get() = this
    private var disciplinas = listOf<Disciplina>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE= 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        val args = intent.extras
       // val nome = args.getString("nome_usuario")
        //val n = args.getInt("numero")

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Menu Inicial"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()

        recyclerDisciplinas?.layoutManager = LinearLayoutManager(context)
        recyclerDisciplinas?.itemAnimator = DefaultItemAnimator()
        recyclerDisciplinas.setHasFixedSize(true)
    }


    override fun onResume(){
        super.onResume()
        taskDisciplinas()
    }

    fun taskDisciplinas() {
        // Criar a Thread

        Thread {
            // Código para procurar as disciplinas
            // que será executado em segundo plano / Thread separada
            this.disciplinas = DisciplinaService.getDisciplinas(context)
            runOnUiThread {
                // Código para atualizar a UI com a lista de disciplinas
                recyclerDisciplinas?.adapter = DisciplinaAdapter(this.disciplinas) { onClickDisciplina(it) }
                enviaNotificacao(this.disciplinas.get(0))

            }
        }.start()

    }

    fun enviaNotificacao(disciplina: Disciplina) {
        NotificationUtil.createChannel(this)
        val intent = Intent(this, DisciplinaActivity::class.java)
        intent.putExtra("disciplina", disciplina)
        NotificationUtil.create(this, 1, intent, "SystemBarbershop", "Você tem nova atividade na ${disciplina.nome}")
    }

    fun onClickDisciplina(disciplina: Disciplina) {
        Toast.makeText(context, "Clicou disciplina ${disciplina.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, DisciplinaActivity::class.java)
        intent.putExtra("disciplina", disciplina)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_disciplinas -> {
                Toast.makeText(this, "Clicou Disciplinas", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_mensagens -> {
                Toast.makeText(this, "Clicou Mensagens", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_forum -> {
                Toast.makeText(this, "Clicou Forum", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_localizacao -> {
                Toast.makeText(this, "Clicou Localização", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_config -> {
                Toast.makeText(this, "Clicou Config", Toast.LENGTH_SHORT).show()
            }
        }

        // fecha menu depois de tratar o evento
        layoutMenuLateral.closeDrawer(GravityCompat.START)
        return true
    }

    fun cliqueSair() {
        val returnIntent = Intent();
        returnIntent.putExtra("result","Saída do SystemBarbershop");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    // método sobrescrito para inflar o menu na Actionbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main, menu)
        // vincular evento de buscar
        (menu?.findItem(R.id.action_buscar)?.actionView as SearchView?)?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                // ação enquanto está digitando
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // ação  quando terminou de buscar e enviou
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // id do item clicado
        val id = item?.itemId
        // verificar qual item foi clicado e mostrar a mensagem Toast na tela
        // a comparação é feita com o recurso de id definido no xml
        if  (id == R.id.action_buscar) {
            Toast.makeText(context, "Botão de buscar", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_atualizar) {
            Toast.makeText(context, "Botão de atualizar", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_config) {
            Toast.makeText(context, "Botão de configuracoes", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_adicionar) {
            // iniciar activity de cadastro
            val intent = Intent(context, DisciplinaCadastroActivity::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
        }
        // botão up navigation
        else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE ) {
            // atualizar lista de disciplinas
            taskDisciplinas()
        }
    }

    private fun configuraMenuLateral() {

        var toogle = ActionBarDrawerToggle(
            this,
            layoutMenuLateral,
            toolbar,
            R.string.open,
            R.string.close

            )
        layoutMenuLateral.addDrawerListener(toogle)
        toogle.syncState()
        menu_lateral.setNavigationItemSelectedListener(this)
    }
}


