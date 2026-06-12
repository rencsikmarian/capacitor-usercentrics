import Foundation
import Usercentrics

// Capacitor adaptation of react-native-sdk UsercentricsConsentType+Int.swift:
// the JS bridge sends string enums instead of ordinals.

extension UsercentricsConsentType {
    static func from(string: String?) -> UsercentricsConsentType {
        switch string {
        case "implicit":
            return .implicit
        default:
            return .explicit_
        }
    }
}

extension TCFDecisionUILayer {
    static func from(string: String?) -> TCFDecisionUILayer {
        switch string {
        case "secondLayer":
            return .secondLayer
        default:
            return .firstLayer
        }
    }
}
