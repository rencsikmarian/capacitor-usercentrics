import Foundation
import UIKit
import Usercentrics
import UsercentricsUI

// MARK: - Banner presentation (first/second layer) with optional BannerSettings
extension CapacitorUsercentrics {

    enum BannerLayer {
        case first
        case second
    }

    public func showBanner(settings: [String: Any]?, completion: @escaping BannerCallback) {
        guard self.usercentrics != nil else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        presentBanner(layer: .first, settings: settings, completion: completion)
    }

    public func showSecondLayer(settings: [String: Any]?, completion: @escaping BannerCallback) {
        guard self.usercentrics != nil else {
            completion(.failure("Usercentrics not configured"))
            return
        }
        presentBanner(layer: .second, settings: settings, completion: completion)
    }

    private func presentBanner(layer: BannerLayer, settings: [String: Any]?, completion: @escaping BannerCallback) {
        buildBannerSettings(from: settings) { [weak self] bannerSettings in
            DispatchQueue.main.async {
                guard let self = self else {
                    completion(.failure("Self reference lost"))
                    return
                }

                guard let viewController = self.plugin?.getRootVC() else {
                    completion(.failure("No root view controller available"))
                    return
                }

                let handler: (UsercentricsConsentUserResponse) -> Void = { [weak self] response in
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

                let banner = UsercentricsBanner(bannerSettings: bannerSettings)
                switch layer {
                case .first:
                    banner.showFirstLayer(hostView: viewController, completionHandler: handler)
                case .second:
                    banner.showSecondLayer(hostView: viewController, completionHandler: handler)
                }
            }
        }
    }

    // Builds the UsercentricsUI BannerSettings from the raw plugin call options.
    // Remote logo images are downloaded on a background queue first, so the main
    // thread is never blocked by network I/O. Absent/empty settings yield nil,
    // which makes UsercentricsBanner behave exactly as a parameterless banner.
    private func buildBannerSettings(from settings: [String: Any]?, completion: @escaping (BannerSettings?) -> Void) {
        guard let settings = settings, hasBannerSettings(settings) else {
            completion(nil)
            return
        }

        BannerRemoteImageLoader.loadRemoteImages(referencedIn: settings) { remoteImages in
            completion(BannerSettings(from: settings, remoteImages: remoteImages))
        }
    }

    private func hasBannerSettings(_ settings: [String: Any]) -> Bool {
        let settingsKeys = ["generalStyleSettings", "firstLayerStyleSettings", "secondLayerStyleSettings", "variantName"]
        return settingsKeys.contains { key in
            guard let value = settings[key] else { return false }
            return !(value is NSNull)
        }
    }
}
