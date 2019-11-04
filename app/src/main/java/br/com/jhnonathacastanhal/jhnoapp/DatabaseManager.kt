package br.com.jhnonathacastanhal.jhnoapp

import android.arch.persistence.room.Room

object DatabaseManager {

    // singleton
    private var dbInstance: SystemBarbershopDatabase
    init {
        val appContext = SystemBarbershop.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext, // contexto global
            SystemBarbershopDatabase::class.java, // Referência da classe do banco
            "SystemBarbershop.sqlite" // nome do arquivo do banco
        ).build()
    }

    fun getDisciplinaDAO(): DisciplinaDAO {
        return dbInstance.disciplinaDAO()
    }
}