import Foundation
import Usercentrics
import UsercentricsUI

@objc public class CapacitorUsercentrics: NSObject {
    
    weak var plugin: CapacitorUsercentricsPlugin?

    private var usercentrics: UsercentricsSDK?
    
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
        if let rulesetId = options["rulesetId"] as? String {
            usercentricsOptions.ruleSetId = rulesetId
        }
        if let consentMediation = options["consentMediation"] as? Bool {
            usercentricsOptions.consentMediation = consentMediation
        }
        
        UsercentricsCore.configure(options: usercentricsOptions)
        
        completion(.success(()))
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
    
    public func showBanner(completion: @escaping BannerCallback) {
        guard self.usercentrics != nil else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        
        DispatchQueue.main.async { [weak self] in
            guard let self = self else {
                completion(.failure("Self reference lost"))
                return
            }

            guard let viewController = self.plugin?.getRootVC() else {
                completion(.failure("No root view controller available"))
                return
            }

            let banner = UsercentricsBanner()
            banner.showFirstLayer(hostView: viewController) { [weak self] response in
                guard let self = self else {
                    completion(.failure("Self reference lost"))
                    return
                }

                let result: [String: Any] = [
                    "userInteraction": String(describing: response.userInteraction),
                    "controllerId": response.controllerId,
                    "consents": self.convertConsents(response.consents)
                ]

                completion(.success(result))
            }
        }
    }

    public func showSecondLayer(completion: @escaping BannerCallback) {
        guard let _ = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        DispatchQueue.main.async { [weak self] in
            guard let self = self else {
                completion(.failure("Self reference lost"))
                return
            }

            guard let viewController = self.plugin?.getRootVC() else {
                completion(.failure("No root view controller available"))
                return
            }

            let banner = UsercentricsBanner()
            banner.showSecondLayer(hostView: viewController) { [weak self] response in
                guard let self = self else {
                    completion(.failure("Self reference lost"))
                    return
                }

                let result: [String: Any] = [
                    "userInteraction": String(describing: response.userInteraction),
                    "controllerId": response.controllerId,
                    "consents": self.convertConsents(response.consents)
                ]

                completion(.success(result))
            }
        }
    }
    
    public func reset(completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        
        UsercentricsCore.reset()
        completion(.success(()))
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
        completion(.success(["cmpData":cmpData]))
    }

    public func getTCFData(completion: @escaping TCFDataCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        usercentrics.getTCFData { tcf in
            let result: [String: Any] = [
                "tcString": tcf.tcString,
                "features": tcf.features,
                "purposes": tcf.purposes,
                "specialFeatures": tcf.specialFeatures,
                "specialPurposes": tcf.specialPurposes,
                "stacks": tcf.stacks,
                "thirdPartyCount": tcf.thirdPartyCount,
                "vendors": tcf.vendors
            ]
            completion(.success(result))
        }
    }
    
    public func restoreUserSession(userSession: String, completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }

        usercentrics.restoreUserSession(controllerId: usercentrics.getControllerId(), onSuccess: { _ in
            completion(.success(()))
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

    public func acceptAll(completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        _ = usercentrics.acceptAll(consentType: .explicit_)
        completion(.success(()))
    }

    public func denyAll(completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        _ = usercentrics.denyAll(consentType: .explicit_)
        completion(.success(()))
    }

    public func applyConsent(consents: [String: Any], completion: @escaping Callback) {
        guard let _ = self.usercentrics else {
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
    
    // Helper method to convert consents to dictionary format
    private func convertConsents(_ consents: [UsercentricsServiceConsent]) -> [[String: Any]] {
        return consents.map { consent in
            return [
                "templateId": consent.templateId,
                "status": consent.status,
                "type": consent.type?.description(),
                "dataProcessor": consent.dataProcessor,
                "version": consent.version,
                "isEssential": consent.isEssential
            ]
        }
    }
}
