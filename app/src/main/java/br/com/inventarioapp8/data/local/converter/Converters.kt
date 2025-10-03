package br.com.inventarioapp8.data.local.converter

import androidx.room.TypeConverter
import br.com.inventarioapp8.data.local.entity.Profile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
    // Usaremos Long para datas, é mais seguro e padrão no Room
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromProfile(profile: Profile): String {
        return profile.name
    }

    @TypeConverter
    fun toProfile(profileName: String): Profile {
        return Profile.valueOf(profileName)
    }
}