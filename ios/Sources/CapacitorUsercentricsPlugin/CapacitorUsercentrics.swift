import Foundation
import Usercentrics
import UsercentricsUI

@objc public class CapacitorUsercentrics: NSObject {

    weak var plugin: CapacitorUsercentricsPlugin?

    var usercentrics: UsercentricsSDK?

    public enum Result<T> {
        case success(T)
        case failure(String)
    }

    public typealias Callback = (Result<Void>) -> Void
    public typealias ReadyCallback = (Result<[String: Any]>) -> Void
    public typealias BannerCallback = (Result<[String: Any]>) -> Void
    public typealias ConsentsCallback = (Result<[[String: Any]]>) -> Void
    public typealias CMPDataCallback = (Result<[String: Any]>) -> Void
    public typealias TCFDataCallback = (Result<[String: Any]>) -> Void
    public typealias SessionCallback = (Result<String>) -> Void
    public typealias ControllerIdCallback = (Result<String>) -> Void
    public typealias VariantCallback = (Result<String?>) -> Void
    public typealias CCPADataCallback = (Result<[String: Any]>) -> Void
    public typealias ACMDataCallback = (Result<[String: Any]>) -> Void

    private var gppSectionChangeEvent: UsercentricsDisposableEvent<GppSectionChangePayload>?

    public func configure(options: [String: Any], completion: @escaping Callback) {
        guard let settingsId = options["settingsId"] as? String else {
            completion(.failure("settingsId is required"))
            return
        }

        let usercentricsOptions = UsercentricsOptions(settingsId: settingsId)

        if let defaultLanguage = options["defaultLanguage"] as? String {
            usercentricsOptions.defaultLanguage = defaultLanguage
        }
        if let version = options["version"] as? String {
            usercentricsOptions.version = version
        }
        if let timeoutMillis = options["timeoutMillis"] as? Int64 {
            usercentricsOptions.timeoutMillis = timeoutMillis
        }
        if let loggerLevel = options["loggerLevel"] as? String {
            applyLoggerLevel(loggerLevel, to: usercentricsOptions)
        }
        if let rulesetId = options["rulesetId"] as? String {
            usercentricsOptions.ruleSetId = rulesetId
        }
        if let consentMediation = options["consentMediation"] as? Bool {
            usercentricsOptions.consentMediation = consentMediation
        }
        if let networkMode = options["networkMode"] as? String {
            applyNetworkMode(networkMode, to: usercentricsOptions)
        }
        if let initTimeoutMillis = options["initTimeoutMillis"] as? NSNumber {
            usercentricsOptions.initTimeoutMillis = initTimeoutMillis.int64Value
        }

        UsercentricsCore.configure(options: usercentricsOptions)

        completion(.success(()))
    }

    private func applyLoggerLevel(_ loggerLevel: String, to usercentricsOptions: UsercentricsOptions) {
        switch loggerLevel {
        case "debug":
            usercentricsOptions.loggerLevel = .debug
        case "warning":
            usercentricsOptions.loggerLevel = .warning
        case "error":
            usercentricsOptions.loggerLevel = .error
        case "none":
            usercentricsOptions.loggerLevel = .none
        default:
            break
        }
    }

    private func applyNetworkMode(_ networkMode: String, to usercentricsOptions: UsercentricsOptions) {
        switch networkMode {
        case "world":
            usercentricsOptions.networkMode = .world
        case "eu":
            usercentricsOptions.networkMode = .eu
        default:
            break
        }
    }

    public func isReady(completion: @escaping ReadyCallback) {

        UsercentricsCore.isReady(onSuccess: { [weak self] status in
            guard let self = self else {
                completion(.failure("Self reference lost"))
                return
            }

            let result: [String: Any] = [
                "shouldCollectConsent": status.shouldCollectConsent,
                "usercentricsReady": true,
                "controllerId": UsercentricsCore.shared.getControllerId(),
                "consents": self.convertConsents(status.consents)
            ]

            self.usercentrics = UsercentricsCore.shared

            completion(.success(result))
        }, onFailure: { error in
            completion(.failure(error.localizedDescription))
        })
    }

    public func getConsents(completion: @escaping ConsentsCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let consents = usercentrics.getConsents()
        let result = convertConsents(consents)
        completion(.success(result))
    }

    public func getCMPData(completion: @escaping CMPDataCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let cmpData = usercentrics.getCMPData()
        completion(.success(cmpData.toDictionary()))
    }

    public func getTCFData(completion: @escaping TCFDataCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        usercentrics.getTCFData { tcfData in
            completion(.success(tcfData.toDictionary()))
        }
    }

    public func restoreUserSession(controllerId: String, completion: @escaping ReadyCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        usercentrics.restoreUserSession(controllerId: controllerId, onSuccess: { [weak self] status in
            guard let self = self else {
                completion(.failure("Self reference lost"))
                return
            }

            let result: [String: Any] = [
                "shouldCollectConsent": status.shouldCollectConsent,
                "usercentricsReady": true,
                "controllerId": usercentrics.getControllerId(),
                "consents": self.convertConsents(status.consents)
            ]

            completion(.success(result))
        }, onFailure: { error in
            completion(.failure(error.localizedDescription))
        })
    }

    public func saveUserSession(completion: @escaping SessionCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let session = usercentrics.getUserSessionData()
        completion(.success(session))
    }

    public func applyConsent(consents: [String: Any], completion: @escaping Callback) {
        guard self.usercentrics != nil else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        // No-op platform hook to let app apply to 3rd-party SDKs if needed
        completion(.success(()))
    }

    public func saveConsent(consents: [String: Any], completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        var decisions: [UserDecision] = []
        for (_, value) in consents {
            if let dict = value as? [String: Any],
               let templateId = dict["templateId"] as? String,
               let status = dict["status"] as? Bool {
                decisions.append(UserDecision(serviceId: templateId, consent: status))
            }
        }
        _ = usercentrics.saveDecisions(decisions: decisions, consentType: .explicit_)
        completion(.success(()))
    }

    public func getControllerId(completion: @escaping ControllerIdCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        completion(.success(usercentrics.getControllerId()))
    }

    public func clearUserSession(completion: @escaping ReadyCallback) {
        UsercentricsCore.shared.clearUserSession(onSuccess: { [weak self] status in
            guard let self = self else {
                completion(.failure("Self reference lost"))
                return
            }

            let result: [String: Any] = [
                "shouldCollectConsent": status.shouldCollectConsent,
                "usercentricsReady": true,
                "controllerId": UsercentricsCore.shared.getControllerId(),
                "consents": self.convertConsents(status.consents)
            ]

            self.usercentrics = UsercentricsCore.shared

            completion(.success(result))
        }, onError: { error in
            completion(.failure(error.localizedDescription))
        })
    }

    public func changeLanguage(language: String, completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        usercentrics.changeLanguage(language: language, onSuccess: {
            completion(.success(()))
        }, onFailure: { error in
            completion(.failure(error.localizedDescription))
        })
    }

    public func setCMPId(id: Int32) {
        UsercentricsCore.shared.setCMPId(id: id)
    }

    public func setABTestingVariant(variant: String) {
        UsercentricsCore.shared.setABTestingVariant(variantName: variant)
    }

    public func getABTestingVariant(completion: @escaping VariantCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        completion(.success(usercentrics.getABTestingVariant()))
    }

    public func track(event: Int) {
        guard let eventType = UsercentricsAnalyticsEventType.values().get(index: Int32(event)) else { return }
        UsercentricsCore.shared.track(event: eventType)
    }

    // Helper method to convert consents to dictionary format
    func convertConsents(_ consents: [UsercentricsServiceConsent]) -> [[String: Any]] {
        return consents.map { consent in
            return [
                "templateId": consent.templateId,
                "status": consent.status,
                "type": consent.type?.description().lowercased() as Any,
                "dataProcessor": consent.dataProcessor,
                "version": consent.version,
                "isEssential": consent.isEssential
            ]
        }
    }
}

// MARK: - GPP and DPS metadata (Usercentrics SDK 2.26.1+)
extension CapacitorUsercentrics {

    public typealias GppDataCallback = (Result<[String: Any]>) -> Void
    public typealias GppStringCallback = (Result<String?>) -> Void
    public typealias DpsMetadataCallback = (Result<[String: Any]?>) -> Void

    public func getGPPData(completion: @escaping GppDataCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        let gppData = usercentrics.getGPPData()
        let result: [String: Any] = [
            "gppString": gppData.gppString as Any,
            "applicableSections": gppData.applicableSections,
            "sections": bridgeGppValue(gppData.sections)
        ]
        completion(.success(result))
    }

    public func getGPPString(completion: @escaping GppStringCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        completion(.success(usercentrics.getGPPString()))
    }

    public func setGPPConsent(sectionName: String, fieldName: String, value: Any, completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        usercentrics.setGPPConsent(sectionName: sectionName, fieldName: fieldName, value: value)
        completion(.success(()))
    }

    public func getDpsMetadata(templateId: String, completion: @escaping DpsMetadataCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        completion(.success(usercentrics.getDpsMetadata(templateId: templateId)))
    }

    public func startGppSectionChangeListener(onSectionChange: @escaping ([String: Any]) -> Void) {
        guard gppSectionChangeEvent == nil else { return }
        gppSectionChangeEvent = UsercentricsEvent.shared.onGppSectionChange { payload in
            onSectionChange(["data": payload.data])
        }
    }

    public func stopGppSectionChangeListener() {
        gppSectionChangeEvent?.dispose()
        gppSectionChangeEvent = nil
    }

    // Recursively converts SDK GPP structures into plist-compatible values for the bridge
    private func bridgeGppValue(_ value: Any) -> Any {
        switch value {
        case let dictionary as [String: Any]:
            return dictionary.mapValues { bridgeGppValue($0) }
        case let array as [Any]:
            return array.map { bridgeGppValue($0) }
        default:
            return value
        }
    }
}
