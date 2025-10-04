package br.com.inventarioapp8.core

import android.content.Context
import android.content.SharedPreferences
import br.com.inventarioapp8.data.local.entity.Profile

/**
 * Classe para gerenciar a sessão do usuário logado usando SharedPreferences.
 */
class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val USER_PROFILE = "user_profile"
    }

    /**
     * Salva os dados do usuário após o login.
     */
    fun saveSession(userId: Long, profile: Profile) {
        val editor = prefs.edit()
        editor.putLong(USER_ID, userId)
        editor.putString(USER_PROFILE, profile.name)
        editor.apply()
    }

    /**
     * Busca o perfil do usuário logado.
     */
    fun getUserProfile(): Profile? {
        val profileName = prefs.getString(USER_PROFILE, null)
        return profileName?.let { Profile.valueOf(it) }
    }

    /**
     * Limpa a sessão ao fazer logout.
     */
    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}