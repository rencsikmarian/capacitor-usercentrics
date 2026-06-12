import Foundation
import UIKit

// Ported from react-native-sdk UIImage+UsercentricsLogoDict.swift.
// Adaptation vs React Native: Metro asset resolution is replaced by direct loading
// from a data: base64 URI, an absolute file path / file:// URL, or a remote
// http(s) URL. Remote images are NEVER fetched here synchronously; they must be
// pre-fetched on a background queue via BannerRemoteImageLoader and handed in
// through `remoteImages`. Any failure simply yields `nil` (the setting is skipped).
extension UIImage {

    convenience init?(from dictionary: [String: Any]?, remoteImages: [String: UIImage] = [:]) {
        guard let dictionary = dictionary else { return nil }

        if let logoPath = dictionary["logoPath"] as? String, !logoPath.isEmpty,
           let image = Self.image(fromLogoPath: logoPath, remoteImages: remoteImages),
           let cgImage = image.cgImage {
            self.init(cgImage: cgImage, scale: image.scale, orientation: image.imageOrientation)
            return
        }

        if let logoName = dictionary["logoName"] as? String, !logoName.isEmpty {
            self.init(named: logoName)
            return
        }

        return nil
    }

    private static func image(fromLogoPath logoPath: String, remoteImages: [String: UIImage]) -> UIImage? {
        if logoPath.hasPrefix("data:") {
            if let image = imageFromDataURI(logoPath) { return image }
        } else if logoPath.hasPrefix("file://") {
            let path = logoPath.replacingOccurrences(of: "file://", with: "")
            if let image = UIImage(contentsOfFile: path) { return image }
        } else if logoPath.hasPrefix("/") {
            if let image = UIImage(contentsOfFile: logoPath) { return image }
        } else if logoPath.hasPrefix("http://") || logoPath.hasPrefix("https://") {
            if let image = remoteImages[logoPath] { return image }
        }
        if let image = UIImage(named: logoPath) { return image }
        return nil
    }

    private static func imageFromDataURI(_ uri: String) -> UIImage? {
        guard let commaIndex = uri.firstIndex(of: ",") else { return nil }
        let base64String = String(uri[uri.index(after: commaIndex)...])
        guard let data = Data(base64Encoded: base64String, options: .ignoreUnknownCharacters) else { return nil }
        return UIImage(data: data)
    }
}

// Pre-fetches every remote (http/https) `logoPath` referenced anywhere inside the
// banner settings dictionary on a background queue, so that BannerSettings can be
// built without blocking the main thread with network I/O.
enum BannerRemoteImageLoader {

    static func loadRemoteImages(referencedIn dictionary: [String: Any], completion: @escaping ([String: UIImage]) -> Void) {
        let urlStrings = collectRemoteLogoPaths(in: dictionary)
        guard !urlStrings.isEmpty else {
            completion([:])
            return
        }

        DispatchQueue.global(qos: .userInitiated).async {
            var images: [String: UIImage] = [:]
            for urlString in urlStrings {
                guard let url = URL(string: urlString),
                      let data = try? Data(contentsOf: url),
                      let image = UIImage(data: data) else {
                    // Image could not be fetched/decoded: skip it gracefully.
                    continue
                }
                images[urlString] = image
            }
            completion(images)
        }
    }

    private static func collectRemoteLogoPaths(in value: Any) -> Set<String> {
        var paths: Set<String> = []
        if let dictionary = value as? [String: Any] {
            if let logoPath = dictionary["logoPath"] as? String,
               logoPath.hasPrefix("http://") || logoPath.hasPrefix("https://") {
                paths.insert(logoPath)
            }
            for (_, nested) in dictionary {
                paths.formUnion(collectRemoteLogoPaths(in: nested))
            }
        } else if let array = value as? [Any] {
            for nested in array {
                paths.formUnion(collectRemoteLogoPaths(in: nested))
            }
        }
        return paths
    }
}
