import Foundation
import Capacitor
import Usercentrics
import UsercentricsUI

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorUsercentricsPlugin)
public class CapacitorUsercentricsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapacitorUsercentricsPlugin"
    public let jsName = "CapacitorUsercentrics"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "configure", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isReady", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "showBanner", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "showSecondLayer", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getConsents", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCMPData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getTCFData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "acceptAll", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "denyAll", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "applyConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "restoreUserSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveUserSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getControllerId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "clearUserSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "changeLanguage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCMPId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setABTestingVariant", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getABTestingVariant", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCCPAData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAdditionalConsentModeData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "track", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = CapacitorUsercentrics()

    override public func load() {
        super.load()
        self.implementation.plugin = self
    }

    func getRootVC() -> UIViewController? {
        var window: UIWindow? = UIApplication.shared.delegate?.window ?? nil

        if window == nil {
            let scene: UIWindowScene? = UIApplication.shared.connectedScenes.first as? UIWindowScene
            window = scene?.windows.filter({$0.isKeyWindow}).first
            if window == nil {
                window = scene?.windows.first
            }
        }
        return window?.rootViewController
    }

    @objc func configure(_ call: CAPPluginCall) {
        guard let options = call.getObject("options") else {
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
        implementation.showBanner { result in
            switch result {
            case .success(let bannerResult):
                call.resolve(bannerResult)
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func showSecondLayer(_ call: CAPPluginCall) {
        implementation.showSecondLayer { result in
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
        implementation.acceptAll { result in
            switch result {
            case .success:
                call.resolve()
            case .failure(let error):
                call.reject(error)
            }
        }
    }

    @objc func denyAll(_ call: CAPPluginCall) {
        implementation.denyAll { result in
            switch result {
            case .success:
                call.resolve()
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

    @objc func track(_ call: CAPPluginCall) {
        guard let event = call.getInt("event") else {
            call.reject("event parameter is required")
            return
        }

        implementation.track(event: event)
        call.resolve()
    }
}
