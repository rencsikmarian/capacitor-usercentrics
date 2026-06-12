import Foundation
import Usercentrics

// MARK: - CCPA and Additional Consent Mode data
extension CapacitorUsercentrics {

    public func getCCPAData(completion: @escaping CCPADataCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let uspData = usercentrics.getUSPData()
        let result: [String: Any] = [
            "version": uspData.version,
            "uspString": uspData.uspString,
            "optedOut": uspData.optedOut as Any,
            "lspact": uspData.lspact as Any,
            "noticeGiven": uspData.noticeGiven as Any
        ]
        completion(.success(result))
    }

    public func getAdditionalConsentModeData(completion: @escaping ACMDataCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let acmData = usercentrics.getAdditionalConsentModeData()
        var providers: [[String: Any]] = []
        for provider in acmData.adTechProviders {
            providers.append([
                "id": provider.id,
                "name": provider.name,
                "privacyPolicyUrl": provider.privacyPolicyUrl,
                "consent": provider.consent
            ])
        }

        let result: [String: Any] = [
            "acString": acmData.acString,
            "adTechProviders": providers
        ]
        completion(.success(result))
    }
}
