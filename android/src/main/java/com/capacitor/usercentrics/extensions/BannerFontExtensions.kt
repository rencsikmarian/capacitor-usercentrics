@file:JvmName("BannerFontDeserializer")

package com.capacitor.usercentrics.extensions

import android.content.Context
import android.graphics.Typeface
import com.getcapacitor.Logger
import com.usercentrics.sdk.BannerFont
import org.json.JSONObject

internal fun JSONObject.bannerFontFromMap(context: Context): BannerFont? {
    val size = getDoubleOrNull("fontSize") ?: return null

    val regularTypeface = context.createFontFromName(getStringOrNull("regularFont")) ?: return null
    val boldTypeface = context.createFontFromName(getStringOrNull("boldFont")) ?: return null

    return BannerFont(regularFont = regularTypeface, boldFont = boldTypeface, sizeInSp = size.toFloat())
}

/**
 * Mirrors the React Native behavior: look for a matching font file inside the
 * "fonts" asset folder first. As a fallback (no asset match), try to resolve the
 * font by family name via Typeface.create. Never throws: failures are logged as
 * warnings and null is returned so the banner is shown without the custom font.
 */
internal fun Context.createFontFromName(fontName: String?): Typeface? {
    if (fontName == null) {
        return null
    }
    return try {
        val fontsFolder = runCatching { assets.list("fonts") }.getOrNull()
        val assetFontName = fontsFolder?.firstOrNull { it.startsWith(fontName) }
        if (assetFontName != null) {
            Typeface.createFromAsset(assets, "fonts/$assetFontName")
        } else {
            Typeface.create(fontName, Typeface.NORMAL)
        }
    } catch (e: Exception) {
        Logger.warn("Usercentrics could not load font \"$fontName\": ${e.message}")
        null
    }
}
