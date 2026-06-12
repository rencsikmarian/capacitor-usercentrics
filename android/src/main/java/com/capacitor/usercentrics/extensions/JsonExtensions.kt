@file:JvmName("JsonExtensions")

package com.capacitor.usercentrics.extensions

import com.getcapacitor.JSArray
import com.getcapacitor.JSObject
import org.json.JSONArray
import org.json.JSONObject

internal fun JSONObject.getIntOrNull(key: String): Int? {
    return if (has(key) && !isNull(key)) {
        optInt(key)
    } else {
        null
    }
}

internal fun JSONObject.getDoubleOrNull(key: String): Double? {
    return if (has(key) && !isNull(key)) {
        optDouble(key)
    } else {
        null
    }
}

internal fun JSONObject.getBooleanOrNull(key: String): Boolean? {
    return if (has(key) && !isNull(key)) {
        optBoolean(key)
    } else {
        null
    }
}

internal fun JSONObject.getStringOrNull(key: String): String? {
    return if (has(key) && !isNull(key)) {
        optString(key)
    } else {
        null
    }
}

internal fun List<*>.serialize(): JSArray {
    val array = JSArray()
    forEach { value ->
        when (value) {
            is Boolean -> {
                array.put(value)
            }

            is Int -> {
                array.put(value)
            }

            is Long -> {
                array.put(value)
            }

            is Double -> {
                array.put(value)
            }

            is Float -> {
                array.put(value.toDouble())
            }

            is String -> {
                array.put(value)
            }

            is Map<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                val newMap = (value as Map<String, Any?>).toJSObject()
                array.put(newMap)
            }

            is JSONObject -> {
                array.put(value)
            }

            is JSONArray -> {
                array.put(value)
            }

            is List<*> -> {
                array.put(value.serialize())
            }
        }
    }
    return array
}

internal fun Map<String, Any?>.toJSObject(): JSObject {
    val map = JSObject()

    forEach { (key, value) ->
        when (value) {
            is Boolean -> {
                map.put(key, value)
            }

            is Int -> {
                map.put(key, value)
            }

            is Long -> {
                map.put(key, value)
            }

            is Double -> {
                map.put(key, value)
            }

            is Float -> {
                map.put(key, value.toDouble())
            }

            is String -> {
                map.put(key, value)
            }

            is JSONObject -> {
                map.put(key, value)
            }

            is JSONArray -> {
                map.put(key, value)
            }

            is Map<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                val newMap = (value as Map<String, Any?>).toJSObject()
                map.put(key, newMap)
            }

            is List<*> -> {
                map.put(key, value.serialize())
            }
        }
    }

    return map
}
