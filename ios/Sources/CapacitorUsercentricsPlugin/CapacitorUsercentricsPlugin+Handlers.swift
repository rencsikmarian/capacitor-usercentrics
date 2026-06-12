import Foundation
import Capacitor

// swiftlint:disable file_length
// MARK: - @objc plugin call handlers
// Kept in an extension so the CapacitorUsercentricsPlugin class body stays small;
// Capacitor dispatches the CAPPluginMethod selectors to these just fine.
extension CapacitorUsercentricsPlugin {

    @objc func configure(_ call: CAPPluginCall) {
        let options: [String: Any]
        if let nestedOptions = call.getObject("options") {
            options = nestedOptions
        } else if call.getString("settingsId") != nil {
            // Support direct options without nesting
            options = call.options as? [String: Any] ?? [:]
        } else {
            call.reject("Options parameter is required")
            return
        }

        implementation.configure(options: options) { result in
            switch result {
            case .success:
                call.resolve()
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func isReady(_ call: CAPPluginCall) {
        implementation.isReady { result in
            switch result {
            case .success(let status):
                call.resolve(status)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func showBanner(_ call: CAPPluginCall) {
        implementation.showBanner(settings: call.options as? [String: Any]) { result in
            switch result {
            case .success(let bannerResult):
                call.resolve(bannerResult)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func showSecondLayer(_ call: CAPPluginCall) {
        implementation.showSecondLayer(settings: call.options as? [String: Any]) { result in
            switch result {
            case .success(let bannerResult):
                call.resolve(bannerResult)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getConsents(_ call: CAPPluginCall) {
        implementation.getConsents { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getCMPData(_ call: CAPPluginCall) {
        implementation.getCMPData { result in
            switch result {
            case .success(let data):
                call.resolve(data)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getTCFData(_ call: CAPPluginCall) {
        implementation.getTCFData { result in
            switch result {
            case .success(let data):
                call.resolve(data)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func acceptAll(_ call: CAPPluginCall) {
        implementation.acceptAll(consentType: call.getString("consentType")) { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func denyAll(_ call: CAPPluginCall) {
        implementation.denyAll(consentType: call.getString("consentType")) { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func acceptAllForTCF(_ call: CAPPluginCall) {
        guard let fromLayer = call.getString("fromLayer") else {
            call.reject("fromLayer parameter is required")
            return
        }

        implementation.acceptAllForTCF(fromLayer: fromLayer, consentType: call.getString("consentType")) { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func denyAllForTCF(_ call: CAPPluginCall) {
        guard let fromLayer = call.getString("fromLayer") else {
            call.reject("fromLayer parameter is required")
            return
        }

        let unsavedPurposeLIDecisions = call.getArray("unsavedPurposeLIDecisions") as? [[String: Any]] ?? []
        let unsavedVendorLIDecisions = call.getArray("unsavedVendorLIDecisions") as? [[String: Any]] ?? []

        implementation.denyAllForTCF(fromLayer: fromLayer,
                                     consentType: call.getString("consentType"),
                                     unsavedPurposeLIDecisions: unsavedPurposeLIDecisions,
                                     unsavedVendorLIDecisions: unsavedVendorLIDecisions) { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func saveDecisions(_ call: CAPPluginCall) {
        guard let decisions = call.getArray("decisions") as? [[String: Any]] else {
            call.reject("decisions parameter is required")
            return
        }

        implementation.saveDecisions(decisions: decisions, consentType: call.getString("consentType")) { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func saveDecisionsForTCF(_ call: CAPPluginCall) {
        guard let tcfDecisions = call.getObject("tcfDecisions") else {
            call.reject("tcfDecisions parameter is required")
            return
        }
        guard let fromLayer = call.getString("fromLayer") else {
            call.reject("fromLayer parameter is required")
            return
        }

        let serviceDecisions = call.getArray("decisions") as? [[String: Any]] ?? []

        implementation.saveDecisionsForTCF(tcfDecisions: tcfDecisions,
                                           fromLayer: fromLayer,
                                           serviceDecisions: serviceDecisions,
                                           consentType: call.getString("consentType")) { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func saveOptOutForCCPA(_ call: CAPPluginCall) {
        guard let isOptedOut = call.getBool("isOptedOut") else {
            call.reject("isOptedOut parameter is required")
            return
        }

        implementation.saveOptOutForCCPA(isOptedOut: isOptedOut, consentType: call.getString("consentType")) { result in
            switch result {
            case .success(let consents):
                call.resolve(["consents": consents])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func applyConsent(_ call: CAPPluginCall) {
        guard let consents = call.getObject("consents") else {
            call.reject("consents parameter is required")
            return
        }
        implementation.applyConsent(consents: consents) { result in
            switch result {
            case .success:
                call.resolve()
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func saveConsent(_ call: CAPPluginCall) {
        guard let consents = call.getObject("consents") else {
            call.reject("consents parameter is required")
            return
        }
        implementation.saveConsent(consents: consents) { result in
            switch result {
            case .success:
                call.resolve()
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func restoreUserSession(_ call: CAPPluginCall) {
        guard let controllerId = call.getString("controllerId") else {
            call.reject("controllerId parameter is required")
            return
        }

        implementation.restoreUserSession(controllerId: controllerId) { result in
            switch result {
            case .success(let status):
                call.resolve(status)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func saveUserSession(_ call: CAPPluginCall) {
        implementation.saveUserSession { result in
            switch result {
            case .success(let session):
                call.resolve(["session": session])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getUserSessionData(_ call: CAPPluginCall) {
        implementation.getUserSessionData { result in
            switch result {
            case .success(let session):
                call.resolve(["session": session])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getControllerId(_ call: CAPPluginCall) {
        implementation.getControllerId { result in
            switch result {
            case .success(let controllerId):
                call.resolve(["controllerId": controllerId])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func clearUserSession(_ call: CAPPluginCall) {
        implementation.clearUserSession { result in
            switch result {
            case .success(let status):
                call.resolve(status)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func changeLanguage(_ call: CAPPluginCall) {
        guard let language = call.getString("language") else {
            call.reject("language parameter is required")
            return
        }

        implementation.changeLanguage(language: language) { result in
            switch result {
            case .success:
                call.resolve()
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func setCMPId(_ call: CAPPluginCall) {
        guard let id = call.getInt("id") else {
            call.reject("id parameter is required")
            return
        }

        implementation.setCMPId(id: Int32(id))
        call.resolve()
    }

    @objc func setABTestingVariant(_ call: CAPPluginCall) {
        guard let variant = call.getString("variant") else {
            call.reject("variant parameter is required")
            return
        }

        implementation.setABTestingVariant(variant: variant)
        call.resolve()
    }

    @objc func getABTestingVariant(_ call: CAPPluginCall) {
        implementation.getABTestingVariant { result in
            switch result {
            case .success(let variant):
                call.resolve(["variant": variant as Any])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getCCPAData(_ call: CAPPluginCall) {
        implementation.getCCPAData { result in
            switch result {
            case .success(let data):
                call.resolve(data)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getAdditionalConsentModeData(_ call: CAPPluginCall) {
        implementation.getAdditionalConsentModeData { result in
            switch result {
            case .success(let data):
                call.resolve(data)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getGPPData(_ call: CAPPluginCall) {
        implementation.getGPPData { result in
            switch result {
            case .success(let data):
                call.resolve(data)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getGPPString(_ call: CAPPluginCall) {
        implementation.getGPPString { result in
            switch result {
            case .success(let gppString):
                call.resolve(["gppString": gppString as Any])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func setGPPConsent(_ call: CAPPluginCall) {
        guard let sectionName = call.getString("sectionName"), let fieldName = call.getString("fieldName") else {
            call.reject("sectionName and fieldName parameters are required")
            return
        }

        let value = call.options["value"] ?? NSNull()
        implementation.setGPPConsent(sectionName: sectionName, fieldName: fieldName, value: value) { result in
            switch result {
            case .success:
                call.resolve()
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func getDpsMetadata(_ call: CAPPluginCall) {
        guard let templateId = call.getString("templateId") else {
            call.reject("templateId parameter is required")
            return
        }

        implementation.getDpsMetadata(templateId: templateId) { result in
            switch result {
            case .success(let metadata):
                call.resolve(["metadata": metadata as Any])
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func track(_ call: CAPPluginCall) {
        guard let event = call.getInt("event") else {
            call.reject("event parameter is required")
            return
        }

        implementation.track(event: event)
        call.resolve()
    }
}
