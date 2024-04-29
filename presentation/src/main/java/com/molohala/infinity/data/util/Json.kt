package com.molohala.infinity.data.util

import org.json.JSONArray
import org.json.JSONObject


object Json {
    fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
        when (val value = this[it])
        {
            is JSONArray ->
            {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else            -> value
        }
    }

    fun String?.isJsonObject():Boolean{
        return this?.startsWith("{") == true && this.endsWith("}")
    }

    fun String?.isJsonArray():Boolean{
        return this?.startsWith("[") == true && this.endsWith("]")
    }
}