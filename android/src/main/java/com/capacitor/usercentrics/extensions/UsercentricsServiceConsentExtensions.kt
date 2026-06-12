@file:JvmName("ServiceConsentSerializer")

package com.capacitor.usercentrics.extensions

import com.getcapacitor.JSArray
import com.getcapacitor.JSObject
import com.usercentrics.sdk.UsercentricsServiceConsent
import java.util.Locale

fun serializeConsents(consents: List<UsercentricsServiceConsent>): JSArray {
    val array = JSArray()
    consents.forEach { consent ->
        array.put(consent.serialize())
    }
    return array
}

internal fun UsercentricsServiceConsent.serialize(): JSObject {
    return JSObject().apply {
        put("templateId", templateId)
        put("status", status)
        type?.let {
            put("type", it.name.lowercase(Locale.ROOT))
        }
        put("dataProcessor", dataProcessor)
        put("version", version)
        put("isEssential", isEssential)
    }
}
