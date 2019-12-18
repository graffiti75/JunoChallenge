package br.cericatto.junochallenge.model.room

import androidx.room.TypeConverter
import br.cericatto.junochallenge.model.Owner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OwnerTypeConverter {
    var gson = Gson()
    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

    @TypeConverter
    fun stringToOwner(data: String?): Owner? {
        val type = genericType<Owner>()
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun ownerToString(owner: Owner?): String? {
        val gson = Gson()
        return gson.toJson(owner)
    }
}