import Foundation
import UIKit
import UsercentricsUI

// Ported from react-native-sdk BannerSettings+Dict.swift.
// Adaptations vs React Native:
// - Input is the plain [String: Any] options dictionary coming from a CAPPluginCall
//   instead of an NSDictionary delivered by the RCT bridge.
// - Numeric values are unwrapped through NSNumber so both Int and Double bridge
//   payloads are accepted.
// - Remote logo images are looked up from a pre-fetched `remoteImages` cache
//   (see BannerRemoteImageLoader); fonts/images that cannot be resolved are
//   skipped gracefully instead of failing the whole settings object.

private func cgFloatValue(_ value: Any?) -> CGFloat? {
    guard let number = value as? NSNumber else { return nil }
    return CGFloat(truncating: number)
}

extension BannerSettings {
    init?(from dictionary: [String: Any]?, remoteImages: [String: UIImage] = [:]) {
        guard let dictionary = dictionary else { return nil }

        let generalStyleSettingsDict = dictionary["generalStyleSettings"] as? [String: Any]
        let bannerFontHolder = BannerFontHolder(from: generalStyleSettingsDict?["font"] as? [String: Any])
        let generalStyleSettings = GeneralStyleSettings(from: generalStyleSettingsDict,
                                                        bannerFontHolder: bannerFontHolder,
                                                        remoteImages: remoteImages)

        let firstLayerStyleSettingsDict = dictionary["firstLayerStyleSettings"] as? [String: Any]
        let firstLayerSettings = FirstLayerStyleSettings(from: firstLayerStyleSettingsDict,
                                                         bannerFontHolder: bannerFontHolder,
                                                         remoteImages: remoteImages)

        let secondLayerStyleSettingsDict = dictionary["secondLayerStyleSettings"] as? [String: Any]
        let secondLayerSettings = SecondLayerStyleSettings(from: secondLayerStyleSettingsDict,
                                                           bannerFontHolder: bannerFontHolder)

        self.init(generalStyleSettings: generalStyleSettings,
                  firstLayerStyleSettings: firstLayerSettings,
                  secondLayerStyleSettings: secondLayerSettings,
                  variantName: dictionary["variantName"] as? String)
    }
}

extension GeneralStyleSettings {
    init?(from dictionary: [String: Any]?, bannerFontHolder: BannerFontHolder?, remoteImages: [String: UIImage]) {
        guard let dictionary = dictionary else { return nil }

        let toggleStyleSettingsDict = dictionary["toggleStyleSettings"] as? [String: Any]
        let toggleStyleSettings = ToggleStyleSettings(from: toggleStyleSettingsDict)

        let links = LegalLinksSettings.from(enumString: dictionary["links"] as? String)
        let font = bannerFontHolder?.font
        let logo = UIImage(from: dictionary["logo"] as? [String: Any], remoteImages: remoteImages)

        self.init(font: font,
                  logo: logo,
                  links: links,
                  textColor: UIColor(unsafeHex: dictionary["textColorHex"] as? String),
                  layerBackgroundColor: UIColor(unsafeHex: dictionary["layerBackgroundColorHex"] as? String),
                  layerBackgroundSecondaryColor: UIColor(unsafeHex: dictionary["layerBackgroundSecondaryColorHex"] as? String),
                  linkColor: UIColor(unsafeHex: dictionary["linkColorHex"] as? String),
                  tabColor: UIColor(unsafeHex: dictionary["tabColorHex"] as? String),
                  bordersColor: UIColor(unsafeHex: dictionary["bordersColorHex"] as? String),
                  toggleStyleSettings: toggleStyleSettings)
    }
}

struct BannerFontHolder {
    let font: BannerFont

    let regularFont: UIFont
    let boldFont: UIFont

    init?(from dictionary: [String: Any]?) {
        guard let dictionary = dictionary,
              let regularFontName: String = dictionary["regularFont"] as? String,
              let boldFontName: String = dictionary["boldFont"] as? String,
              let fontSize: CGFloat = cgFloatValue(dictionary["fontSize"]),
              let regularFont = UIFont(name: regularFontName, size: fontSize),
              let boldFont = UIFont(name: boldFontName, size: fontSize)
        else { return nil }

        self.regularFont = regularFont
        self.boldFont = boldFont
        self.font = .init(regularFont: regularFont, boldFont: boldFont)
    }
}

extension UIFont {
    static func initialize(from fontName: String?, fontSizeValue: CGFloat?, fallbackFont: UIFont?) -> UIFont? {
        let fontSize = fontSizeValue ?? UIFont.systemFontSize

        // System font with custom size
        if fontName == nil, fontSizeValue != nil {
            return fallbackFont?.withSize(fontSize) ?? UIFont.systemFont(ofSize: fontSize)
        }

        if let fontName = fontName {
            return UIFont(name: fontName, size: fontSize)
        }

        return fallbackFont?.withSize(fontSize)
    }
}

extension FirstLayerStyleSettings {
    init?(from dictionary: [String: Any]?, bannerFontHolder: BannerFontHolder?, remoteImages: [String: UIImage]) {
        guard let dictionary = dictionary else { return nil }

        self.init(layout: UsercentricsLayout.from(enumString: dictionary["layout"] as? String),
                  headerImage: HeaderImageSettings.from(dictionary: dictionary["headerImage"] as? [String: Any],
                                                        remoteImages: remoteImages),
                  title: TitleSettings(from: dictionary["title"] as? [String: Any], fallbackFont: bannerFontHolder?.boldFont),
                  message: MessageSettings(from: dictionary["message"] as? [String: Any], fallbackFont: bannerFontHolder?.regularFont),
                  buttonLayout: ButtonLayout.from(dictionary: dictionary["buttonLayout"] as? [String: Any], fallbackFont: bannerFontHolder?.boldFont),
                  backgroundColor: UIColor(unsafeHex: dictionary["backgroundColorHex"] as? String),
                  cornerRadius: cgFloatValue(dictionary["cornerRadius"]),
                  overlayColor: UIColor(unsafeHex: dictionary["overlayColorHex"] as? String))
    }
}

extension SecondLayerStyleSettings {
    init?(from dictionary: [String: Any]?, bannerFontHolder: BannerFontHolder?) {
        guard let dictionary = dictionary else { return nil }

        let buttonLayout = ButtonLayout.from(dictionary: dictionary["buttonLayout"] as? [String: Any], fallbackFont: bannerFontHolder?.boldFont)
        let showCloseButton = dictionary["showCloseButton"] as? Bool
        self.init(buttonLayout: buttonLayout,
                  showCloseButton: showCloseButton)
    }
}

extension HeaderImageSettings {
    static func from(dictionary: [String: Any]?, remoteImages: [String: UIImage]) -> HeaderImageSettings? {
        guard let dictionary = dictionary else { return nil }

        if let isHidden = dictionary["isHidden"] as? Bool, isHidden {
            return .hidden
        }

        let logoDict = dictionary["image"] as? [String: Any]
        let logo = UIImage(from: logoDict, remoteImages: remoteImages)

        if let isExtended = dictionary["isExtended"] as? Bool, isExtended {
            return .extended(image: logo)
        }

        let logoUrlString = logoDict?["logoUrl"] as? String

        return .logo(settings: LogoSettings(image: logo,
                                            url: URL(string: logoUrlString ?? ""),
                                            position: SectionPosition.from(enumString: dictionary["alignment"] as? String),
                                            height: cgFloatValue(dictionary["height"])))
    }
}

extension TitleSettings {
    init?(from dictionary: [String: Any]?, fallbackFont: UIFont? = nil) {
        guard let dictionary = dictionary else { return nil }

        let fontName: String? = dictionary["fontName"] as? String
        let fontSize: CGFloat? = cgFloatValue(dictionary["textSize"])

        self.init(font: UIFont.initialize(from: fontName, fontSizeValue: fontSize, fallbackFont: fallbackFont),
                  textColor: UIColor(unsafeHex: dictionary["textColorHex"] as? String ?? ""),
                  textAlignment: NSTextAlignment.from(enumString: dictionary["textAlignment"] as? String))
    }
}

extension MessageSettings {
    init?(from dictionary: [String: Any]?, fallbackFont: UIFont? = nil) {
        guard let dictionary = dictionary else { return nil }

        let fontName: String? = dictionary["fontName"] as? String
        let textSize: CGFloat? = cgFloatValue(dictionary["textSize"])

        self.init(font: UIFont.initialize(from: fontName, fontSizeValue: textSize, fallbackFont: fallbackFont),
                  textColor: UIColor(unsafeHex: dictionary["textColorHex"] as? String ?? ""),
                  textAlignment: NSTextAlignment.from(enumString: dictionary["textAlignment"] as? String),
                  linkTextColor: UIColor(unsafeHex: dictionary["linkTextColorHex"] as? String ?? ""),
                  linkTextUnderline: dictionary["linkTextUnderline"] as? Bool ?? true)
    }
}

extension ButtonLayout {
    static func from(dictionary: [String: Any]?, fallbackFont: UIFont? = nil) -> ButtonLayout? {
        guard let dictionary = dictionary else { return nil }

        let layoutDict = dictionary["layout"] as? String
        let buttons = (dictionary["buttons"] as? [[[String: Any]]]) ?? []

        switch layoutDict {
        case "ROW":
            return .row(buttons: buttons.flatMap { $0 }.compactMap { ButtonSettings(from: $0, fallbackFont: fallbackFont) })
        case "COLUMN":
            return .column(buttons: buttons.flatMap { $0 }.compactMap { ButtonSettings(from: $0, fallbackFont: fallbackFont) })
        case "GRID":
            let gridButtons = buttons.map { $0.compactMap { button in ButtonSettings(from: button, fallbackFont: fallbackFont) }}
            return .grid(buttons: gridButtons)
        default:
            break
        }

        return nil
    }
}

extension ButtonSettings {
    init?(from dictionary: [String: Any]?, fallbackFont: UIFont? = nil) {
        guard
            let dictionary = dictionary,
            let buttonTypeDict = dictionary["buttonType"] as? String,
            let buttonType = ButtonType.from(enumString: buttonTypeDict)
        else { return nil }

        let fontName: String? = dictionary["fontName"] as? String
        let textSize: CGFloat? = cgFloatValue(dictionary["textSize"])

        self.init(type: buttonType,
                  font: UIFont.initialize(from: fontName, fontSizeValue: textSize, fallbackFont: fallbackFont),
                  textColor: UIColor(unsafeHex: dictionary["textColorHex"] as? String),
                  backgroundColor: UIColor(unsafeHex: dictionary["backgroundColorHex"] as? String),
                  cornerRadius: cgFloatValue(dictionary["cornerRadius"]))
    }
}

extension ButtonType {
    static func from(enumString: String) -> ButtonType? {
        switch enumString {
        case "ACCEPT_ALL":
            return .acceptAll
        case "DENY_ALL":
            return .denyAll
        case "MORE":
            return .more
        case "SAVE":
            return .save
        default:
            return nil
        }
    }
}

extension UsercentricsLayout {
    static func from(enumString: String?) -> UsercentricsLayout? {
        guard let enumString = enumString else { return nil }

        switch enumString {
        case "FULL":
            return .full
        case "SHEET":
            return .sheet
        case "POPUP_CENTER":
            return .popup(position: .center)
        case "POPUP_BOTTOM":
            return .popup(position: .bottom)
        default:
            return nil
        }
    }
}

extension SectionPosition {
    static func from(enumString: String?) -> SectionPosition? {
        guard let enumString = enumString else { return nil }

        switch enumString {
        case "CENTER":
            return .center
        case "END":
            return .right
        case "START":
            return .left
        default:
            return nil
        }
    }
}

extension NSTextAlignment {
    static func from(enumString: String?) -> NSTextAlignment? {
        guard let enumString = enumString else { return nil }

        switch enumString {
        case "CENTER":
            return .center
        case "RIGHT":
            return .right
        case "LEFT":
            return .left
        default:
            return nil
        }
    }
}

extension LegalLinksSettings {
    static func from(enumString: String?) -> LegalLinksSettings? {
        guard let enumString = enumString else { return nil }

        switch enumString {
        case "BOTH":
            return .both
        case "FIRST_LAYER_ONLY":
            return .firstLayerOnly
        case "HIDDEN":
            return LegalLinksSettings.hidden
        case "SECOND_LAYER_ONLY":
            return .secondLayerOnly
        default:
            return nil
        }
    }
}

extension ToggleStyleSettings {
    init?(from dictionary: [String: Any]?) {
        guard let dictionary = dictionary else { return nil }

        self.init(activeBackgroundColor: UIColor(unsafeHex: dictionary["activeBackgroundColorHex"] as? String),
                  inactiveBackgroundColor: UIColor(unsafeHex: dictionary["inactiveBackgroundColorHex"] as? String),
                  disabledBackgroundColor: UIColor(unsafeHex: dictionary["disabledBackgroundColorHex"] as? String),
                  activeThumbColor: UIColor(unsafeHex: dictionary["activeThumbColorHex"] as? String),
                  inactiveThumbColor: UIColor(unsafeHex: dictionary["inactiveThumbColorHex"] as? String),
                  disabledThumbColor: UIColor(unsafeHex: dictionary["disabledThumbColorHex"] as? String))
    }
}
