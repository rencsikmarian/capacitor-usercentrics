import Foundation
import Usercentrics

// Ported from react-native-sdk UserDecision+Dict.swift.
// Adaptation vs React Native: input is [String: Any] from a CAPPluginCall.

public extension UserDecision {
    convenience init?(from dictionary: [String: Any]) {
        guard let serviceId = dictionary["serviceId"] as? String,
              let consent = dictionary["consent"] as? Bool
        else { return nil }

        self.init(serviceId: serviceId,
                  consent: consent)
    }
}
