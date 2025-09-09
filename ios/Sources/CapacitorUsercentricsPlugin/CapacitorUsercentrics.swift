import Foundation
import UsercentricsUI

@objc public class CapacitorUsercentrics: NSObject {
    
    private var usercentrics: Usercentrics?
    
    // Result types
    public enum Result<T> {
        case success(T)
        case failure(String)
    }
    
    public typealias Callback = (Result<Void>) -> Void
    public typealias ReadyCallback = (Result<[String: Any]>) -> Void
    public typealias BannerCallback = (Result<[String: Any]>) -> Void
    public typealias ConsentsCallback = (Result<[String: Any]>) -> Void
    public typealias CMPDataCallback = (Result<[String: Any]>) -> Void
    public typealias SessionCallback = (Result<String>) -> Void
    
    public func configure(options: [String: Any], completion: @escaping Callback) {
        guard let settingsId = options["settingsId"] as? String else {
            completion(.failure("settingsId is required"))
            return
        }
        
        var usercentricsOptions = UsercentricsOptions(settingsId: settingsId)
        
        if let defaultLanguage = options["defaultLanguage"] as? String {
            usercentricsOptions.defaultLanguage = defaultLanguage
        }
        if let version = options["version"] as? String {
            usercentricsOptions.version = version
        }
        if let timeoutMillis = options["timeoutMillis"] as? Int {
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
            usercentricsOptions.rulesetId = rulesetId
        }
        if let consentMediation = options["consentMediation"] as? Bool {
            usercentricsOptions.consentMediation = consentMediation
        }
        
        UsercentricsCore.configure(options: usercentricsOptions)
        self.usercentrics = UsercentricsCore.shared
        
        completion(.success(()))
    }
    
    public func isReady(completion: @escaping ReadyCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        
        usercentrics.isReady { [weak self] status in
            guard let self = self else {
                completion(.failure("Self reference lost"))
                return
            }
            
            let result: [String: Any] = [
                "shouldCollectConsent": status.shouldCollectConsent,
                "usercentricsReady": status.usercentricsReady,
                "controllerId": status.controllerId,
                "consents": self.convertConsents(status.consents)
            ]
            
            completion(.success(result))
        } onFailure: { error in
            completion(.failure(error))
        }
    }
    
    public func showBanner(completion: @escaping BannerCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        
        guard let viewController = UIApplication.shared.windows.first?.rootViewController else {
            completion(.failure("No root view controller available"))
            return
        }
        
        let banner = UsercentricsBanner()
        banner.showFirstLayer(hostViewController: viewController) { [weak self] response in
            guard let self = self else {
                completion(.failure("Self reference lost"))
                return
            }
            
            let result: [String: Any] = [
                "userInteraction": response.userInteraction,
                "consents": self.convertConsents(response.consents)
            ]
            
            completion(.success(result))
        } onFailure: { error in
            completion(.failure(error))
        }
    }
    
    public func reset(completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        
        usercentrics.reset()
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
        completion(.success(cmpData))
    }
    
    public func restoreUserSession(userSession: String, completion: @escaping Callback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        
        usercentrics.restoreUserSession(userSession: userSession)
        completion(.success(()))
    }
    
    public func saveUserSession(completion: @escaping SessionCallback) {
        guard let usercentrics = self.usercentrics else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        
        let session = usercentrics.saveUserSession()
        completion(.success(session))
    }
    
    // Helper method to convert consents to dictionary format
    private func convertConsents(_ consents: [UsercentricsServiceConsent]) -> [String: Any] {
        var result: [String: Any] = [:]
        
        for consent in consents {
            let consentDict: [String: Any] = [
                "templateId": consent.templateId,
                "status": consent.isConsentGiven,
                "type": consent.type.rawValue.lowercased(),
                "timestamp": consent.timestamp,
                "dataProcessor": consent.dataProcessor,
                "version": consent.version,
                "isEssential": consent.isEssential
            ]
            result[consent.templateId] = consentDict
        }
        
        return result
    }
}
