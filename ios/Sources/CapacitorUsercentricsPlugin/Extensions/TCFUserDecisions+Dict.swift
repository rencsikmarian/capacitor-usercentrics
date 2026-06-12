import Foundation
import Usercentrics

// Ported from react-native-sdk TCFUserDecisions+Dict.swift.
// Adaptation vs React Native: input is [String: Any] from a CAPPluginCall and ids
// are unwrapped through NSNumber so both Int and Double bridge payloads work.

public extension TCFUserDecisions {
    convenience init(from dictionary: [String: Any]) {
        let purposes = dictionary["purposes"] as? [[String: Any]]
        let specialFeatures = dictionary["specialFeatures"] as? [[String: Any]]
        let vendors = dictionary["vendors"] as? [[String: Any]]
        let adTechProviders = dictionary["adTechProviders"] as? [[String: Any]]

        self.init(purposes: purposes?.compactMap { TCFUserDecisionOnPurpose(from: $0) },
                  specialFeatures: specialFeatures?.compactMap { TCFUserDecisionOnSpecialFeature(from: $0) },
                  vendors: vendors?.compactMap { TCFUserDecisionOnVendor(from: $0) },
                  adTechProviders: adTechProviders?.compactMap { AdTechProviderDecision(from: $0) } ?? [])
    }
}

extension TCFUserDecisionOnPurpose {
    convenience init?(from dictionary: [String: Any]) {
        guard let id = (dictionary["id"] as? NSNumber)?.intValue else { return nil }

        var consent: KotlinBoolean?
        if let consentBool = dictionary["consent"] as? Bool {
            consent = KotlinBoolean(bool: consentBool)
        }

        var legitimateInterestConsent: KotlinBoolean?
        if let legInterestBool = dictionary["legitimateInterestConsent"] as? Bool {
            legitimateInterestConsent = KotlinBoolean(bool: legInterestBool)
        }

        self.init(id: Int32(id),
                  consent: consent,
                  legitimateInterestConsent: legitimateInterestConsent)
    }
}

extension TCFUserDecisionOnSpecialFeature {
    convenience init?(from dictionary: [String: Any]) {
        guard let id = (dictionary["id"] as? NSNumber)?.intValue else { return nil }

        var consent: KotlinBoolean?
        if let consentBool = dictionary["consent"] as? Bool {
            consent = KotlinBoolean(bool: consentBool)
        }

        self.init(id: Int32(id),
                  consent: consent)
    }
}

extension TCFUserDecisionOnVendor {
    convenience init?(from dictionary: [String: Any]) {
        guard let id = (dictionary["id"] as? NSNumber)?.intValue else { return nil }

        var consent: KotlinBoolean?
        if let consentBool = dictionary["consent"] as? Bool {
            consent = KotlinBoolean(bool: consentBool)
        }

        var legitimateInterestConsent: KotlinBoolean?
        if let legInterestBool = dictionary["legitimateInterestConsent"] as? Bool {
            legitimateInterestConsent = KotlinBoolean(bool: legInterestBool)
        }

        self.init(id: Int32(id),
                  consent: consent,
                  legitimateInterestConsent: legitimateInterestConsent)
    }
}

extension AdTechProviderDecision {
    convenience init?(from dictionary: [String: Any]) {
        guard let id = (dictionary["id"] as? NSNumber)?.intValue else { return nil }

        let consent = dictionary["consent"] as? Bool
        self.init(id: Int32(id), consent: consent ?? false)
    }
}
