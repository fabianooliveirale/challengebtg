package com.example.challengebtg.dao

import com.devmaker.dressall_android.utils.dataBase.LocalDbImplement
import com.example.challengebtg.MyApplication
import com.example.challengebtg.model.Currencies

class CurrenciesDao {
    fun save(results: Currencies) {
        LocalDbImplement<Currencies>(MyApplication.context)
            .save(
                results,
                Currencies::class.java,
                "Currencies"
            )
    }

    fun load(): Currencies? {
        return LocalDbImplement<Currencies>(MyApplication.context)[Currencies::class.java, "Currencies"]
    }

    fun clear() {
        return LocalDbImplement<Currencies>(MyApplication.context)
            .clearObject(
                "Currencies"
            )
    }
}