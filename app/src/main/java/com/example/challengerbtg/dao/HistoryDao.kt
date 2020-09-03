package com.example.challengerbtg.dao

import com.devmaker.dressall_android.utils.dataBase.LocalDbImplement
import com.example.challengerbtg.MyApplication
import com.example.challengerbtg.model.History

class HistoryDao {
    fun save(results: History) {
        LocalDbImplement<History>(MyApplication.context)
            .save(
                results,
                History::class.java,
                "HistoryDao"
            )
    }

    fun load(): History? {
        return LocalDbImplement<History>(MyApplication.context)[History::class.java, "HistoryDao"]
    }

    fun clear() {
        return LocalDbImplement<History>(MyApplication.context)
            .clearObject(
                "HistoryDao"
            )
    }
}