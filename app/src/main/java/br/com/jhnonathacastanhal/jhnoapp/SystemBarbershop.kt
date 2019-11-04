package br.com.jhnonathacastanhal.jhnoapp

import android.app.Application
import java.lang.IllegalStateException

class SystemBarbershop: Application() {
    // chamado quando android iniciar o processo da aplicação
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        // singleton
        private var appInstance: SystemBarbershop?  = null
        fun getInstance(): SystemBarbershop {
            if (appInstance == null) {
                throw IllegalStateException("Configurar application no Android Manifest")
            }
            return appInstance!!
        }
    }

    // chamado quando android terminar processo da aplicação
    override fun onTerminate() {
        super.onTerminate()
    }
}