package br.com.jhnonathacastanhal.jhnoapp

import android.content.Context
import android.util.Log
import br.com.jhnonathacastanhal.jhnoapp.Response
import br.com.jhnonathacastanhal.jhnoapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.security.AccessControlContext

object DisciplinaService {

    var host = "https://fesousa.pythonanywhere.com"
    var TAG = "WS_System Barbershop"

    fun getDisciplinas (context: Context): List<Disciplina> {
        var disciplinas = ArrayList<Disciplina>()
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/disciplinas"
            val json = HttpHelper.get(url)
            disciplinas = parserJson(json)
            // salvar offline
            for (d in disciplinas) {
                saveOffline(d)
            }
            return disciplinas
        } else {
            val dao = DatabaseManager.getDisciplinaDAO()
            val disciplinas = dao.findAll()
            return disciplinas
        }

    }

    fun save(disciplina: Disciplina): Response {
        val json = HttpHelper.post("$host/disciplinas", disciplina.toJson())
        return parserJson(json)
    }

    fun saveOffline(disciplina: Disciplina) : Boolean {
        val dao = DatabaseManager.getDisciplinaDAO()

        if (! existeDisciplina(disciplina)) {
            dao.insert(disciplina)
        }

        return true

    }

    fun existeDisciplina(disciplina: Disciplina): Boolean {
        val dao = DatabaseManager.getDisciplinaDAO()
        return dao.getById(disciplina.id) != null
    }

    fun delete(disciplina: Disciplina): Response {
        if (AndroidUtils.isInternetDisponivel(SystemBarbershop.getInstance().applicationContext)) {
            val url = "$host/disciplinas/${disciplina.id}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        } else {
            val dao = DatabaseManager.getDisciplinaDAO()
            dao.delete(disciplina)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }

    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}