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
        CAPPluginMethod(name: "acceptAllForTCF", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "denyAllForTCF", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveDecisions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveDecisionsForTCF", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveOptOutForCCPA", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "applyConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "restoreUserSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveUserSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getUserSessionData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getControllerId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "clearUserSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "changeLanguage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCMPId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setABTestingVariant", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getABTestingVariant", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCCPAData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAdditionalConsentModeData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getGPPData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getGPPString", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setGPPConsent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDpsMetadata", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "track", returnType: CAPPluginReturnPromise)
    ]
    let implementation = CapacitorUsercentrics()

    override public func load() {
        super.load()
        self.implementation.plugin = self
        self.implementation.startGppSectionChangeListener { [weak self] payload in
            self?.notifyListeners("onGppSectionChange", data: payload)
        }
    }

    deinit {
        implementation.stopGppSectionChangeListener()
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
}
