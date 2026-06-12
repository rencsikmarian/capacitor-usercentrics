@file:JvmName("UserDecisionDeserializer")

package com.capacitor.usercentrics.extensions

import com.usercentrics.sdk.UserDecision
import com.usercentrics.sdk.services.tcf.interfaces.AdTechProviderDecision
import com.usercentrics.sdk.services.tcf.interfaces.TCFUserDecisionOnPurpose
import com.usercentrics.sdk.services.tcf.interfaces.TCFUserDecisionOnSpecialFeature
import com.usercentrics.sdk.services.tcf.interfaces.TCFUserDecisionOnVendor
import com.usercentrics.sdk.services.tcf.interfaces.TCFUserDecisions
import org.json.JSONArray
import org.json.JSONObject

fun deserializeUserDecisions(decisions: JSONArray?): List<UserDecision> {
    val decisionList = mutableListOf<UserDecision>()
    if (decisions == null) {
        return decisionList
    }
    for (i in 0 until decisions.length()) {
        val map = decisions.optJSONObject(i)
        map?.let {
            val serviceId = it.getStringOrNull("serviceId") ?: return@let
            decisionList.add(
                UserDecision(
                    serviceId,
                    it.getBooleanOrNull("consent") ?: false
                )
            )
        }
    }
    return decisionList
}

fun deserializePurposeLIDecisionsMap(array: JSONArray?): Map<Int, Boolean>? {
    if (array == null || array.length() == 0) return null
    val map = mutableMapOf<Int, Boolean>()
    for (i in 0 until array.length()) {
        array.optJSONObject(i)?.let { jsonMap ->
            val id = jsonMap.getIntOrNull("id") ?: return@let
            val consent = jsonMap.getBooleanOrNull("legitimateInterestConsent") ?: false
            map[id] = consent
        }
    }
    return map
}

fun deserializeTCFUserDecisions(map: JSONObject?): TCFUserDecisions {
    if (map == null) {
        return TCFUserDecisions(null, null, null, emptyList())
    }

    val purposes = map.optJSONArray("purposes")?.let { purpose ->
        val list = mutableListOf<TCFUserDecisionOnPurpose>()
        for (i in 0 until purpose.length()) {
            purpose.optJSONObject(i)?.let { item -> item.deserializeTCFUserDecisionOnPurpose()?.let { list.add(it) } }
        }
        list
    }

    val specialFeature = map.optJSONArray("specialFeatures")?.let { feature ->
        val list = mutableListOf<TCFUserDecisionOnSpecialFeature>()
        for (i in 0 until feature.length()) {
            feature.optJSONObject(i)?.let { item -> item.deserializeTCFUserDecisionOnSpecialFeature()?.let { list.add(it) } }
        }
        list
    }

    val vendors = map.optJSONArray("vendors")?.let { vendor ->
        val list = mutableListOf<TCFUserDecisionOnVendor>()
        for (i in 0 until vendor.length()) {
            vendor.optJSONObject(i)?.let { item -> item.deserializeTCFUserDecisionOnVendor()?.let { list.add(it) } }
        }
        list
    }

    val adTechProviderDecisions = map.optJSONArray("adTechProviders")?.let { decision ->
        val list = mutableListOf<AdTechProviderDecision>()
        for (i in 0 until decision.length()) {
            decision.optJSONObject(i)?.let { item -> item.deserializeAdTechProviderDecision()?.let { list.add(it) } }
        }
        list
    }

    return TCFUserDecisions(
        purposes = purposes,
        specialFeatures = specialFeature,
        vendors = vendors,
        adTechProviders = adTechProviderDecisions ?: emptyList(),
    )
}

private fun JSONObject.deserializeTCFUserDecisionOnPurpose(): TCFUserDecisionOnPurpose? {
    val id = getIntOrNull("id") ?: return null
    return TCFUserDecisionOnPurpose(
        id = id,
        consent = getBooleanOrNull("consent"),
        legitimateInterestConsent = getBooleanOrNull("legitimateInterestConsent"),
    )
}

private fun JSONObject.deserializeTCFUserDecisionOnSpecialFeature(): TCFUserDecisionOnSpecialFeature? {
    val id = getIntOrNull("id") ?: return null
    return TCFUserDecisionOnSpecialFeature(
        id = id,
        consent = getBooleanOrNull("consent"),
    )
}

private fun JSONObject.deserializeTCFUserDecisionOnVendor(): TCFUserDecisionOnVendor? {
    val id = getIntOrNull("id") ?: return null
    return TCFUserDecisionOnVendor(
        id = id,
        consent = getBooleanOrNull("consent"),
        legitimateInterestConsent = getBooleanOrNull("legitimateInterestConsent"),
    )
}

private fun JSONObject.deserializeAdTechProviderDecision(): AdTechProviderDecision? {
    val id = getIntOrNull("id") ?: return null
    return AdTechProviderDecision(
        id = id,
        consent = getBooleanOrNull("consent") ?: false,
    )
}
