// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorUsercentrics",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorUsercentrics",
            targets: ["CapacitorUsercentricsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://bitbucket.org/usercentricscode/usercentrics-spm-ui.git", from: "2.27.0")
    ],
    targets: [
        .target(
            name: "CapacitorUsercentricsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "UsercentricsUI", package: "usercentrics-spm-ui")
            ],
            path: "ios/Sources/CapacitorUsercentricsPlugin"),
        .testTarget(
            name: "CapacitorUsercentricsPluginTests",
            dependencies: ["CapacitorUsercentricsPlugin"],
            path: "ios/Tests/CapacitorUsercentricsPluginTests")
    ]
)
