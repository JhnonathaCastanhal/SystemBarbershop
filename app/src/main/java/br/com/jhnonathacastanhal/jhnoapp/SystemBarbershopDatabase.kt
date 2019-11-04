package br.com.jhnonathacastanhal.jhnoapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

// anotação define a lista de entidades e a versão do banco
@Database(entities = arrayOf(Disciplina::class), version = 1)
abstract class SystemBarbershopDatabase: RoomDatabase() {
    abstract fun disciplinaDAO(): DisciplinaDAO
}