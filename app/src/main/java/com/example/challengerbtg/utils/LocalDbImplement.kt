package com.devmaker.dressall_android.utils.dataBase

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.text.Normalizer

/**
 * Created by devmaker on 06/11/17.
 */
class JsonListHelper<T>(wrapped: Class<T>) : ParameterizedType {

    private val wrapped: Class<*>

    override fun getActualTypeArguments(): Array<Type> {
        return arrayOf(wrapped)
    }

    override fun getRawType(): Type {
        return List::class.java
    }

    override fun getOwnerType(): Type? {
        return null
    }

    init {
        this.wrapped = wrapped
    }
}

abstract class AbstractLocalDb<E> protected constructor(protected var context: Context) {
    private val gson: Gson
    private val spName = "MySharedPreferences"

    init {
        this.gson = Gson()
    }

    fun save(obj: E, type: Class<E>, name: String) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()

        val objectJson = gson.toJson(obj, type)

        editor.putString(name, objectJson)
        editor.commit()
    }

    fun save(obj: Any) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()

        val objectJson = gson.toJson(obj)
        editor.putString(obj::class.java.simpleName, objectJson)
        editor.commit()
    }

    fun save(obj: Any, name: String) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()

        val objectJson = gson.toJson(obj)
        editor.putString(name, objectJson)
        editor.commit()
    }

    operator fun get(type: Class<E>, name: String): E? {
        val settings = context.getSharedPreferences(spName, 0)

        val model: E
        val objectJson = settings.getString(name, null)
        if (objectJson != null) {
            model = gson.fromJson<E>(objectJson, type)
        } else {
            return null
        }

        return model

    }

    fun getDefault(type: Class<E>): E? {
        return get(type, type.simpleName)
    }

    fun clearObject(name: String) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()
        editor.remove(name)
        editor.commit()
    }

    fun clearObject(type: Class<E>) {
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()
        editor.remove(type.simpleName)
        editor.commit()
    }

    @Throws(Exception::class)
    private fun getList(type: Class<E>, json: JSONArray): List<E>? {
        val gsonB = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        return gsonB.fromJson<List<E>>(
            removeAcentos(json.toString()),
            JsonListHelper(type)
        )
    }

    fun saveList(objectList: List<E>, type: Class<E>, name: String) {
        val gsonB = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        val json = Gson().toJson(objectList)
        println(json)

        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()

        editor.putString(name, json)
        editor.commit()
    }

    fun getList(type: Class<E>, name: String): List<E>? {
        val settings = context.getSharedPreferences(spName, 0)

        val listJson = settings.getString(name, null)
        if (listJson != null) {
            try {
                return getList(type, JSONArray(listJson))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return null
    }

    fun getDefaultList(type: Class<E>): List<E>? {
        return getList(type, type.simpleName)
    }

    fun removeAcentos(str: String): String {
        var str = str

        str = Normalizer.normalize(str, Normalizer.Form.NFD)
        str = str.replace("[^\\p{ASCII}]".toRegex(), "")
        return str

    }
}

class LocalDbImplement<E>(context: Context) : AbstractLocalDb<E>(context)
