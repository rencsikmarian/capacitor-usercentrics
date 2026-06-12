@file:JvmName("TCFDataSerializer")

package com.capacitor.usercentrics.extensions

import com.getcapacitor.JSObject
import com.usercentrics.sdk.services.tcf.interfaces.TCFData
import com.usercentrics.sdk.services.tcf.interfaces.TCFFeature
import com.usercentrics.sdk.services.tcf.interfaces.TCFPurpose
import com.usercentrics.sdk.services.tcf.interfaces.TCFSpecialFeature
import com.usercentrics.sdk.services.tcf.interfaces.TCFSpecialPurpose
import com.usercentrics.sdk.services.tcf.interfaces.TCFStack
import com.usercentrics.sdk.services.tcf.interfaces.TCFVendor
import com.usercentrics.sdk.services.tcf.interfaces.TCFVendorRestriction
import com.usercentrics.tcf.core.model.gvl.VendorUrl

fun TCFData.serialize(): JSObject {
    return mapOf(
        "features" to features.map { it.serialize() },
        "purposes" to purposes.map { it.serialize() },
        "specialFeatures" to specialFeatures.map { it.serialize() },
        "specialPurposes" to specialPurposes.map { it.serialize() },
        "stacks" to stacks.map { it.serialize() },
        "vendors" to vendors.map { it.serialize() },
        "tcString" to tcString,
        "thirdPartyCount" to thirdPartyCount
    ).toJSObject()
}

private fun TCFFeature.serialize(): JSObject {
    return mapOf(
        "purposeDescription" to purposeDescription,
        "illustrations" to illustrations,
        "id" to id,
        "name" to name,
    ).toJSObject()
}

private fun TCFPurpose.serialize(): JSObject {
    return mapOf(
        "purposeDescription" to purposeDescription,
        "illustrations" to illustrations,
        "id" to id,
        "name" to name,
        "consent" to consent,
        "isPartOfASelectedStack" to isPartOfASelectedStack,
        "legitimateInterestConsent" to legitimateInterestConsent,
        "showConsentToggle" to showConsentToggle,
        "showLegitimateInterestToggle" to showLegitimateInterestToggle,
        "stackId" to stackId,
        "numberOfVendors" to numberOfVendors,
    ).toJSObject()
}

private fun TCFSpecialPurpose.serialize(): JSObject {
    return mapOf(
        "purposeDescription" to purposeDescription,
        "illustrations" to illustrations,
        "id" to id,
        "name" to name,
    ).toJSObject()
}

private fun TCFSpecialFeature.serialize(): JSObject {
    return mapOf(
        "purposeDescription" to purposeDescription,
        "illustrations" to illustrations,
        "id" to id,
        "name" to name,
        "consent" to consent,
        "isPartOfASelectedStack" to isPartOfASelectedStack,
        "stackId" to stackId,
        "showConsentToggle" to showConsentToggle,
    ).toJSObject()
}

private fun TCFStack.serialize(): JSObject {
    return mapOf(
        "description" to description,
        "id" to id,
        "name" to name,
        "purposeIds" to purposeIds,
        "specialFeatureIds" to specialFeatureIds,
    ).toJSObject()
}

private fun TCFVendor.serialize(): JSObject {
    return mapOf(
        "consent" to consent,
        "features" to features.map { it.id },
        "flexiblePurposes" to flexiblePurposes.map { it.id },
        "id" to id,
        "legitimateInterestConsent" to legitimateInterestConsent,
        "legitimateInterestPurposes" to legitimateInterestPurposes.map { it.id },
        "name" to name,
        "policyUrl" to policyUrl,
        "purposes" to purposes.map { it.id },
        "specialFeatures" to specialFeatures.map { it.id },
        "specialPurposes" to specialPurposes.map { it.id },
        "showConsentToggle" to showConsentToggle,
        "showLegitimateInterestToggle" to showLegitimateInterestToggle,
        "cookieMaxAgeSeconds" to cookieMaxAgeSeconds,
        "usesNonCookieAccess" to usesNonCookieAccess,
        "deviceStorageDisclosureUrl" to deviceStorageDisclosureUrl,
        "usesCookies" to usesCookies,
        "cookieRefresh" to cookieRefresh,
        "dataSharedOutsideEU" to dataSharedOutsideEU,
        "dataCategories" to dataCategories.map { it.id },
        "vendorUrls" to vendorUrls.map { it.serialize() },
        "restrictions" to restrictions.map { it.serialize() }
    ).toJSObject()
}

private fun VendorUrl.serialize(): JSObject {
    return mapOf(
        "langId" to langId,
        "privacy" to privacy,
        "legIntClaim" to legIntClaim
    ).toJSObject()
}

private fun TCFVendorRestriction.serialize(): JSObject {
    return mapOf(
        "purposeId" to purposeId,
        "restrictionType" to restrictionType.ordinal
    ).toJSObject()
}
