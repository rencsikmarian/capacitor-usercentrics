@file:JvmName("BannerSettingsDeserializer")

package com.capacitor.usercentrics.extensions

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import androidx.annotation.ColorInt
import com.getcapacitor.Logger
import com.usercentrics.sdk.BannerSettings
import com.usercentrics.sdk.ButtonLayout
import com.usercentrics.sdk.ButtonSettings
import com.usercentrics.sdk.ButtonType
import com.usercentrics.sdk.FirstLayerStyleSettings
import com.usercentrics.sdk.GeneralStyleSettings
import com.usercentrics.sdk.HeaderImageSettings
import com.usercentrics.sdk.LegalLinksSettings
import com.usercentrics.sdk.MessageSettings
import com.usercentrics.sdk.PopupPosition
import com.usercentrics.sdk.SecondLayerStyleSettings
import com.usercentrics.sdk.SectionAlignment
import com.usercentrics.sdk.TitleSettings
import com.usercentrics.sdk.ToggleStyleSettings
import com.usercentrics.sdk.UsercentricsImage
import com.usercentrics.sdk.UsercentricsLayout
import org.json.JSONObject
import java.io.File
import java.net.URL

/**
 * Returns true when the given plugin call data contains any banner settings key.
 * When it returns false the caller should behave exactly as before this feature
 * existed, i.e. show the banner with null settings.
 */
fun hasBannerSettings(map: JSONObject?): Boolean {
    if (map == null) {
        return false
    }
    return map.has("generalStyleSettings") ||
        map.has("firstLayerStyleSettings") ||
        map.has("secondLayerStyleSettings") ||
        map.has("variantName")
}

/**
 * Builds the native BannerSettings from the plugin call data. This may perform
 * I/O (logo download / decoding), so it must be invoked on a background thread.
 */
fun bannerSettingsFromJson(context: Context, map: JSONObject?): BannerSettings? {
    if (!hasBannerSettings(map)) {
        return null
    }
    return map!!.bannerSettingsFromMap(context)
}

internal fun String.usercentricsLayoutFromEnumString(): UsercentricsLayout? {
    return when (this) {
        "FULL" -> UsercentricsLayout.Full
        "SHEET" -> UsercentricsLayout.Sheet
        "POPUP_BOTTOM" -> UsercentricsLayout.Popup(PopupPosition.BOTTOM)
        "POPUP_CENTER" -> UsercentricsLayout.Popup(PopupPosition.CENTER)
        else -> null
    }
}

internal fun JSONObject.bannerSettingsFromMap(context: Context): BannerSettings {
    val rawFirstLayerStyleSettings = optJSONObject("firstLayerStyleSettings")
    val rawSecondLayerStyleSettings = optJSONObject("secondLayerStyleSettings")
    val rawGeneralStyleSettings = optJSONObject("generalStyleSettings")

    return BannerSettings(
        firstLayerStyleSettings = rawFirstLayerStyleSettings?.firstLayerStyleSettingsFromMap(context),
        secondLayerStyleSettings = rawSecondLayerStyleSettings?.secondLayerStyleSettingsFromMap(context),
        generalStyleSettings = rawGeneralStyleSettings?.generalStyleSettingsFromMap(context),
        variantName = getStringOrNull("variantName"),
    )
}

/**
 * The banner logo is described by {logoName?, logoPath, logoUrl?} where logoPath
 * may be an http(s) URL, a "data:" base64 URI or an absolute file path. The image
 * is loaded eagerly (and therefore must run on a background thread). Failures are
 * logged and null is returned so the banner is shown without the logo.
 */
internal fun JSONObject.bannerLogoFromMap(context: Context): UsercentricsImage? {
    val logoPath = getStringOrNull("logoPath") ?: getStringOrNull("logoUrl") ?: return null

    val drawableResId = context.resources.getIdentifier(logoPath, "drawable", context.packageName)
    if (drawableResId != 0) {
        return UsercentricsImage.ImageDrawableId(drawableResId)
    }
    return loadUsercentricsImage(logoPath)
}

internal fun loadUsercentricsImage(logoPath: String): UsercentricsImage? {
    val image: UsercentricsImage? = try {
        when {
            logoPath.startsWith("http://") || logoPath.startsWith("https://") -> {
                URL(logoPath).openStream().use { stream ->
                    BitmapFactory.decodeStream(stream)?.let { UsercentricsImage.ImageBitmap(it) }
                }
            }

            logoPath.startsWith("data:") -> {
                val base64Data = logoPath.substringAfter(",", "")
                val bytes = Base64.decode(base64Data, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.let { UsercentricsImage.ImageBitmap(it) }
            }

            File(logoPath).exists() -> {
                BitmapFactory.decodeFile(logoPath)?.let { UsercentricsImage.ImageBitmap(it) }
            }

            else -> null
        }
    } catch (e: Exception) {
        Logger.warn("Usercentrics could not load banner logo from \"$logoPath\": ${e.message}")
        return null
    }
    if (image == null) {
        Logger.warn("Usercentrics could not load banner logo from \"$logoPath\", continuing without it")
    }
    return image
}

internal fun JSONObject.firstLayerStyleSettingsFromMap(context: Context): FirstLayerStyleSettings {
    return FirstLayerStyleSettings(
        layout = getStringOrNull("layout")?.usercentricsLayoutFromEnumString(),
        headerImage = optJSONObject("headerImage")?.headerImageFromMap(context),
        title = optJSONObject("title")?.titleFromMap(context),
        message = optJSONObject("message")?.messageFromMap(context),
        buttonLayout = optJSONObject("buttonLayout")?.buttonLayoutFromMap(context),
        backgroundColor = getStringOrNull("backgroundColorHex")?.deserializeColor(),
        overlayColor = getStringOrNull("overlayColorHex")?.deserializeColor(),
        cornerRadius = getIntOrNull("cornerRadius")
    )
}

internal fun JSONObject.headerImageFromMap(context: Context): HeaderImageSettings? {
    val isHidden = getBooleanOrNull("isHidden") ?: false
    if (isHidden) {
        return HeaderImageSettings.Hidden
    }

    val image = optJSONObject("image")?.bannerLogoFromMap(context) ?: return null
    val isExtended = getBooleanOrNull("isExtended") ?: false

    return if (isExtended) {
        HeaderImageSettings.ExtendedLogoSettings(image)
    } else {
        HeaderImageSettings.LogoSettings(
            image = image,
            alignment = getStringOrNull("alignment")?.sectionAlignmentFromMap(),
            heightInDp = getDoubleOrNull("height")?.toFloat()
        )
    }
}

internal fun JSONObject.titleFromMap(context: Context): TitleSettings {
    return TitleSettings(
        alignment = getStringOrNull("alignment")?.sectionAlignmentFromMap(),
        textColor = getStringOrNull("textColorHex")?.deserializeColor(),
        font = context.createFontFromName(getStringOrNull("fontName")),
        textSizeInSp = getDoubleOrNull("textSize")?.toFloat()
    )
}

internal fun JSONObject.messageFromMap(context: Context): MessageSettings {
    return MessageSettings(
        font = context.createFontFromName(getStringOrNull("fontName")),
        textSizeInSp = getDoubleOrNull("textSize")?.toFloat(),
        textColor = getStringOrNull("textColorHex")?.deserializeColor(),
        alignment = getStringOrNull("alignment")?.sectionAlignmentFromMap(),
        linkTextColor = getStringOrNull("linkTextColorHex")?.deserializeColor(),
        underlineLink = getBooleanOrNull("linkTextUnderline")
    )
}

internal fun String.sectionAlignmentFromMap(): SectionAlignment? {
    return runCatching { SectionAlignment.valueOf(this) }.getOrNull()
}

internal fun JSONObject.buttonLayoutFromMap(context: Context): ButtonLayout? {
    val layout = getStringOrNull("layout")

    val buttons: List<List<ButtonSettings>> = optJSONArray("buttons")?.let { buttonsArray ->
        val buttonsList = mutableListOf<List<ButtonSettings>>()

        for (rowIndex in 0 until buttonsArray.length()) {
            val listRow = mutableListOf<ButtonSettings>()
            val row = buttonsArray.optJSONArray(rowIndex)

            val rowSize = row?.length() ?: 0
            for (rowElement in 0 until rowSize) {
                val element = row?.optJSONObject(rowElement)
                element?.let { listRow.add(it.buttonSettingsFromMap(context)) }
            }
            buttonsList.add(listRow)
        }

        return@let buttonsList.toList()
    } ?: listOf()

    when (layout) {
        "ROW" -> {
            return ButtonLayout.Row(buttons.flatten())
        }
        "COLUMN" -> {
            return ButtonLayout.Column(buttons.flatten())
        }
        "GRID" -> {
            return ButtonLayout.Grid(buttons)
        }
    }
    return null
}

internal fun JSONObject.buttonSettingsFromMap(context: Context): ButtonSettings {
    val buttonType = getStringOrNull("buttonType")?.deserializeButtonType() ?: ButtonType.MORE

    return ButtonSettings(
        type = buttonType,
        isAllCaps = getBooleanOrNull("isAllCaps"),
        font = context.createFontFromName(getStringOrNull("fontName")),
        textColor = getStringOrNull("textColorHex")?.deserializeColor(),
        backgroundColor = getStringOrNull("backgroundColorHex")?.deserializeColor(),
        cornerRadius = getIntOrNull("cornerRadius"),
        textSizeInSp = getDoubleOrNull("textSize")?.toFloat()
    )
}

internal fun String.deserializeButtonType(): ButtonType? {
    return runCatching { ButtonType.valueOf(this) }.getOrNull()
}

internal fun JSONObject.secondLayerStyleSettingsFromMap(context: Context): SecondLayerStyleSettings {
    return SecondLayerStyleSettings(
        buttonLayout = optJSONObject("buttonLayout")?.buttonLayoutFromMap(context),
        showCloseButton = getBooleanOrNull("showCloseButton"),
    )
}

internal fun JSONObject.generalStyleSettingsFromMap(context: Context): GeneralStyleSettings {
    val rawToggleStyleSettings = optJSONObject("toggleStyleSettings")

    return GeneralStyleSettings(
        textColor = getStringOrNull("textColorHex")?.deserializeColor(),
        layerBackgroundColor = getStringOrNull("layerBackgroundColorHex")?.deserializeColor(),
        layerBackgroundSecondaryColor = getStringOrNull("layerBackgroundSecondaryColorHex")?.deserializeColor(),
        linkColor = getStringOrNull("linkColorHex")?.deserializeColor(),
        tabColor = getStringOrNull("tabColorHex")?.deserializeColor(),
        bordersColor = getStringOrNull("bordersColorHex")?.deserializeColor(),
        toggleStyleSettings = rawToggleStyleSettings?.toggleStyleSettingsFromMap(),
        font = optJSONObject("font")?.bannerFontFromMap(context = context),
        logo = optJSONObject("logo")?.bannerLogoFromMap(context = context),
        links = getStringOrNull("links").legalLinksFromEnumString(),
        disableSystemBackButton = getBooleanOrNull("disableSystemBackButton")
    )
}

internal fun JSONObject.toggleStyleSettingsFromMap(): ToggleStyleSettings {
    return ToggleStyleSettings(
        activeBackgroundColor = getStringOrNull("activeBackgroundColorHex")?.deserializeColor(),
        inactiveBackgroundColor = getStringOrNull("inactiveBackgroundColorHex")?.deserializeColor(),
        disabledBackgroundColor = getStringOrNull("disabledBackgroundColorHex")?.deserializeColor(),
        activeThumbColor = getStringOrNull("activeThumbColorHex")?.deserializeColor(),
        inactiveThumbColor = getStringOrNull("inactiveThumbColorHex")?.deserializeColor(),
        disabledThumbColor = getStringOrNull("disabledThumbColorHex")?.deserializeColor(),
    )
}

internal fun String?.legalLinksFromEnumString(): LegalLinksSettings? {
    return when (this) {
        "BOTH" -> LegalLinksSettings.BOTH
        "HIDDEN" -> LegalLinksSettings.HIDDEN
        "FIRST_LAYER_ONLY" -> LegalLinksSettings.FIRST_LAYER_ONLY
        "SECOND_LAYER_ONLY" -> LegalLinksSettings.SECOND_LAYER_ONLY
        else -> null
    }
}

@ColorInt
internal fun String.deserializeColor(): Int? {
    val colorString: String = if (!this.startsWith("#")) {
        "#$this"
    } else {
        this
    }
    return runCatching { Color.parseColor(colorString) }.getOrNull()
}
