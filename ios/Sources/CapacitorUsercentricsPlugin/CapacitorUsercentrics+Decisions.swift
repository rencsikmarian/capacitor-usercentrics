import Foundation
import Usercentrics

// MARK: - Consent decision APIs (ported from RNUsercentricsModule)
extension CapacitorUsercentrics {

    public func acceptAll(consentType: String?, completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.acceptAll(consentType: .from(string: consentType))
        completion(.success(convertConsents(consents)))
    }

    public func denyAll(consentType: String?, completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.denyAll(consentType: .from(string: consentType))
        completion(.success(convertConsents(consents)))
    }

    public func acceptAllForTCF(fromLayer: String, consentType: String?, completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.acceptAllForTCF(fromLayer: .from(string: fromLayer),
                                                    consentType: .from(string: consentType))
        completion(.success(convertConsents(consents)))
    }

    public func denyAllForTCF(fromLayer: String,
                              consentType: String?,
                              unsavedPurposeLIDecisions: [[String: Any]],
                              unsavedVendorLIDecisions: [[String: Any]],
                              completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.denyAllForTCF(fromLayer: .from(string: fromLayer),
                                                  consentType: .from(string: consentType),
                                                  unsavedPurposeLIDecisions: extractLIDecisionsMap(unsavedPurposeLIDecisions),
                                                  unsavedVendorLIDecisions: extractLIDecisionsMap(unsavedVendorLIDecisions))
        completion(.success(convertConsents(consents)))
    }

    public func saveDecisions(decisions: [[String: Any]], consentType: String?, completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.saveDecisions(decisions: decisions.compactMap { UserDecision(from: $0) },
                                                  consentType: .from(string: consentType))
        completion(.success(convertConsents(consents)))
    }

    public func saveDecisionsForTCF(tcfDecisions: [String: Any],
                                    fromLayer: String,
                                    serviceDecisions: [[String: Any]],
                                    consentType: String?,
                                    completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.saveDecisionsForTCF(tcfDecisions: TCFUserDecisions(from: tcfDecisions),
                                                        fromLayer: .from(string: fromLayer),
                                                        serviceDecisions: serviceDecisions.compactMap { UserDecision(from: $0) },
                                                        consentType: .from(string: consentType))
        completion(.success(convertConsents(consents)))
    }

    public func saveOptOutForCCPA(isOptedOut: Bool, consentType: String?, completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.saveOptOutForCCPA(isOptedOut: isOptedOut,
                                                      consentType: .from(string: consentType))
        completion(.success(convertConsents(consents)))
    }

    public func getUserSessionData(completion: @escaping SessionCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        completion(.success(usercentrics.getUserSessionData()))
    }

    // Mirrors RNUsercentricsModule.extractLIDecisionsMap: converts
    // [{id, legitimateInterestConsent}] entries into the KMP map form.
    private func extractLIDecisionsMap(_ decisions: [[String: Any]]) -> [KotlinInt: KotlinBoolean]? {
        guard !decisions.isEmpty else { return nil }
        return decisions.reduce(into: [:]) { result, dictionary in
            if let id = (dictionary["id"] as? NSNumber)?.intValue,
               let consent = dictionary["legitimateInterestConsent"] as? Bool {
                result[KotlinInt(int: Int32(id))] = KotlinBoolean(bool: consent)
            }
        }
    }
}
