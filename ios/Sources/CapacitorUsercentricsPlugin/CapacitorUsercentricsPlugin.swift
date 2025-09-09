import Foundation
import Capacitor
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
        CAPPluginMethod(name: "reset", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getConsents", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCMPData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getTCFData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "acceptAll", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "denyAll", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "applyConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "restoreUserSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveUserSession", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = CapacitorUsercentrics()

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

    @objc func reset(_ call: CAPPluginCall) {
        implementation.reset { result in
            switch result {
            case .success:
                call.resolve()
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
        guard let userSession = call.getString("userSession") else {
            call.reject("userSession parameter is required")
            return
        }
        
        implementation.restoreUserSession(userSession: userSession) { result in
            switch result {
            case .success:
                call.resolve()
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
}
