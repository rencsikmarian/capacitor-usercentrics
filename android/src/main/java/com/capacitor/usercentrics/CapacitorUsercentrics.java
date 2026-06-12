package com.capacitor.usercentrics;

import android.app.Activity;
import android.content.Context;
import com.capacitor.usercentrics.extensions.BannerSettingsDeserializer;
import com.capacitor.usercentrics.extensions.CMPDataSerializer;
import com.capacitor.usercentrics.extensions.ServiceConsentSerializer;
import com.capacitor.usercentrics.extensions.TCFDataSerializer;
import com.capacitor.usercentrics.extensions.UserDecisionDeserializer;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.usercentrics.ccpa.CCPAData;
import com.usercentrics.sdk.*;
import com.usercentrics.sdk.errors.UsercentricsError;
import com.usercentrics.sdk.models.common.NetworkMode;
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel;
import com.usercentrics.sdk.models.settings.UsercentricsConsentType;
import com.usercentrics.sdk.services.gpp.GppData;
import com.usercentrics.sdk.services.gpp.GppSectionChangePayload;
import com.usercentrics.sdk.services.tcf.TCFDecisionUILayer;
import com.usercentrics.sdk.services.tcf.interfaces.TCFData;
import com.usercentrics.sdk.services.tcf.interfaces.TCFUserDecisions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class CapacitorUsercentrics {

    private UsercentricsSDK usercentricsSDK;
    private Context context;

    public CapacitorUsercentrics() {
        this.context = null;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Callback interfaces
    public interface Callback {
        void onSuccess();
        void onError(String error);
    }

    public interface ReadyCallback {
        void onSuccess(JSObject status);
        void onError(String error);
    }

    public interface BannerCallback {
        void onSuccess(JSObject result);
        void onError(String error);
    }

    public interface ConsentsCallback {
        void onSuccess(JSObject consents);
        void onError(String error);
    }

    public interface CMPDataCallback {
        void onSuccess(JSObject data);
        void onError(String error);
    }

    public interface SessionCallback {
        void onSuccess(String session);
        void onError(String error);
    }

    public interface ControllerIdCallback {
        void onSuccess(String controllerId);
        void onError(String error);
    }

    public interface VariantCallback {
        void onSuccess(String variant);
        void onError(String error);
    }

    public interface GppStringCallback {
        void onSuccess(String gppString);
        void onError(String error);
    }

    public interface GppSectionChangeListener {
        void onSectionChange(JSObject payload);
    }

    private UsercentricsDisposableEvent<GppSectionChangePayload> gppSectionChangeEvent;

    public void configure(JSObject options, Callback callback) {
        try {
            if (context == null) {
                callback.onError("Context not set");
                return;
            }

            String settingsId = options.getString("settingsId");
            if (settingsId == null) {
                callback.onError("settingsId is required");
                return;
            }

            // Use the simple constructor as shown in the documentation
            UsercentricsOptions usercentricsOptions = new UsercentricsOptions(settingsId);

            // Apply additional options if provided
            if (options.has("defaultLanguage")) {
                usercentricsOptions.setDefaultLanguage(options.getString("defaultLanguage"));
            }
            if (options.has("version")) {
                usercentricsOptions.setVersion(options.getString("version"));
            }
            if (options.has("timeoutMillis")) {
                usercentricsOptions.setTimeoutMillis(options.getInt("timeoutMillis"));
            }
            if (options.has("loggerLevel")) {
                String level = options.getString("loggerLevel");
                switch (level) {
                    case "debug":
                        usercentricsOptions.setLoggerLevel(UsercentricsLoggerLevel.DEBUG);
                        break;
                    case "warning":
                        usercentricsOptions.setLoggerLevel(UsercentricsLoggerLevel.WARNING);
                        break;
                    case "error":
                        usercentricsOptions.setLoggerLevel(UsercentricsLoggerLevel.ERROR);
                        break;
                    case "none":
                        usercentricsOptions.setLoggerLevel(UsercentricsLoggerLevel.NONE);
                        break;
                }
            }
            if (options.has("rulesetId")) {
                usercentricsOptions.setRuleSetId(options.getString("rulesetId"));
            }
            if (options.has("consentMediation")) {
                usercentricsOptions.setConsentMediation(options.getBool("consentMediation"));
            }
            if (options.has("networkMode")) {
                String networkMode = options.getString("networkMode");
                if ("eu".equalsIgnoreCase(networkMode)) {
                    usercentricsOptions.setNetworkMode(NetworkMode.EU);
                } else {
                    usercentricsOptions.setNetworkMode(NetworkMode.WORLD);
                }
            }
            if (options.has("initTimeoutMillis")) {
                usercentricsOptions.setInitTimeoutMillis(options.getLong("initTimeoutMillis"));
            }

            Usercentrics.initialize(context, usercentricsOptions);

            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics configure error", e);
            callback.onError(e.getMessage());
        }
    }

    public void isReady(ReadyCallback callback) {
        try {
            Usercentrics.isReady(
                (UsercentricsReadyStatus status) -> {
                    usercentricsSDK = Usercentrics.getInstance();
                    JSObject result = new JSObject();
                    result.put("shouldCollectConsent", status.getShouldCollectConsent());
                    result.put("controllerId", usercentricsSDK.getControllerId());
                    result.put("consents", ServiceConsentSerializer.serializeConsents(status.getConsents()));

                    callback.onSuccess(result);
                    return null;
                },
                (UsercentricsError error) -> {
                    callback.onError(error.getMessage());

                    return null;
                }
            );
        } catch (Exception e) {
            Logger.error("Usercentrics isReady error", e);
            callback.onError(e.getMessage());
        }
    }

    public void showBanner(JSObject settingsData, BannerCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            if (!(context instanceof Activity)) {
                callback.onError("Context must be an Activity to show banner");
                return;
            }

            Activity activity = (Activity) context;
            withBannerSettings(activity, settingsData, (BannerSettings bannerSettings) ->
                activity.runOnUiThread(() -> {
                    try {
                        UsercentricsBanner banner = new UsercentricsBanner(activity, bannerSettings);
                        banner.showFirstLayer((UsercentricsConsentUserResponse response) -> {
                            callback.onSuccess(serializeBannerResponse(response));
                            return null;
                        });
                    } catch (Exception e) {
                        Logger.error("Usercentrics showBanner error", e);
                        callback.onError(e.getMessage());
                    }
                })
            );
        } catch (Exception e) {
            Logger.error("Usercentrics showBanner error", e);
            callback.onError(e.getMessage());
        }
    }

    public void showSecondLayer(JSObject settingsData, BannerCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            if (!(context instanceof Activity)) {
                callback.onError("Context must be an Activity to show banner");
                return;
            }

            Activity activity = (Activity) context;
            withBannerSettings(activity, settingsData, (BannerSettings bannerSettings) ->
                activity.runOnUiThread(() -> {
                    try {
                        UsercentricsBanner banner = new UsercentricsBanner(activity, bannerSettings);
                        banner.showSecondLayer((UsercentricsConsentUserResponse response) -> {
                            callback.onSuccess(serializeBannerResponse(response));
                            return null;
                        });
                    } catch (Exception e) {
                        Logger.error("Usercentrics showSecondLayer error", e);
                        callback.onError(e.getMessage());
                    }
                })
            );
        } catch (Exception e) {
            Logger.error("Usercentrics showSecondLayer error", e);
            callback.onError(e.getMessage());
        }
    }

    private interface BannerSettingsConsumer {
        void accept(BannerSettings bannerSettings);
    }

    /**
     * Resolves the optional BannerSettings from the plugin call data. When no settings
     * are provided the consumer is called synchronously with null (same behavior as
     * before banner settings were supported). Otherwise the settings are built on a
     * background thread because logo images may need to be downloaded/decoded, which
     * must never happen on the main thread.
     */
    private void withBannerSettings(Activity activity, JSObject settingsData, BannerSettingsConsumer consumer) {
        if (!BannerSettingsDeserializer.hasBannerSettings(settingsData)) {
            consumer.accept(null);
            return;
        }
        new Thread(() -> {
            BannerSettings bannerSettings = null;
            try {
                bannerSettings = BannerSettingsDeserializer.bannerSettingsFromJson(activity, settingsData);
            } catch (Exception e) {
                Logger.warn("Usercentrics could not parse banner settings, showing banner with defaults: " + e.getMessage());
            }
            consumer.accept(bannerSettings);
        })
            .start();
    }

    private JSObject serializeBannerResponse(UsercentricsConsentUserResponse response) {
        JSObject result = new JSObject();
        result.put("userInteraction", response.getUserInteraction().toString());
        result.put("controllerId", response.getControllerId());
        result.put("consents", ServiceConsentSerializer.serializeConsents(response.getConsents()));
        return result;
    }

    public void getConsents(ConsentsCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.getConsents();

            JSObject result = new JSObject();
            result.put("consents", ServiceConsentSerializer.serializeConsents(consents));
            callback.onSuccess(result);
        } catch (Exception e) {
            Logger.error("Usercentrics getConsents error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getCMPData(CMPDataCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            UsercentricsCMPData cmpData = usercentricsSDK.getCMPData();
            callback.onSuccess(CMPDataSerializer.serialize(cmpData));
        } catch (Exception e) {
            Logger.error("Usercentrics getCMPData error", e);
            callback.onError(e.getMessage());
        }
    }

    public void restoreUserSession(String controllerId, ReadyCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            usercentricsSDK.restoreUserSession(
                controllerId,
                (UsercentricsReadyStatus status) -> {
                    JSObject result = new JSObject();
                    result.put("shouldCollectConsent", status.getShouldCollectConsent());
                    result.put("controllerId", usercentricsSDK.getControllerId());
                    result.put("consents", ServiceConsentSerializer.serializeConsents(status.getConsents()));

                    callback.onSuccess(result);
                    return null;
                },
                (UsercentricsError error) -> {
                    callback.onError(error.getMessage());
                    return null;
                }
            );
        } catch (Exception e) {
            Logger.error("Usercentrics restoreUserSession error", e);
            callback.onError(e.getMessage());
        }
    }

    public void saveUserSession(SessionCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            String session = usercentricsSDK.getUserSessionData();
            callback.onSuccess(session);
        } catch (Exception e) {
            Logger.error("Usercentrics saveUserSession error", e);
            callback.onError(e.getMessage());
        }
    }

    public void applyConsent(JSObject consentsData, Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            // Convert JSObject consents back to List<UsercentricsServiceConsent>
            List<UsercentricsServiceConsent> consents = new ArrayList<>();

            // Iterate through the consents object
            for (Iterator<String> it = consentsData.keys(); it.hasNext(); ) {
                String key = it.next();
                JSObject consentData = consentsData.getJSObject(key);
                if (consentData != null) {
                    String templateId = consentData.getString("templateId");
                    boolean status = consentData.getBool("status");
                    String dataProcessor = consentData.getString("dataProcessor");
                    String version = consentData.getString("version");

                    // Create a UsercentricsServiceConsent object
                    UsercentricsServiceConsent consent = new UsercentricsServiceConsent(
                        templateId,
                        status,
                        new ArrayList(), // history - empty list
                        null, // type - null for now
                        dataProcessor,
                        version,
                        false, // isEssential - default to false
                        "" // additional string required by current SDK
                    );
                    consents.add(consent);
                }
            }

            // Apply consent to each service
            applyConsentToSDKs(consents);

            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics applyConsent error", e);
            callback.onError(e.getMessage());
        }
    }

    private void applyConsentToSDKs(List<UsercentricsServiceConsent> consents) {
        if (consents == null) return;

        for (UsercentricsServiceConsent service : consents) {
            String templateId = service.getTemplateId();
            boolean status = service.getStatus();

            // Apply consent based on template ID
            switch (templateId) {
                case "diWdt4yLB": // Google Analytics for Firebase Template ID
                    applyFirebaseConsent(status);
                    break;
                case "x-XXXxXx": // Example: Unity Ads Template ID
                    applyUnityAdsConsent(status);
                    break;
                case "x-xXX-Xx": // Example: AppLovin Template ID
                    applyAppLovinConsent(status);
                    break;
                // Add more cases for other SDKs as needed
                default:
                    Logger.warn("Unknown service template ID: " + templateId);
                    break;
            }
        }
    }

    private void applyFirebaseConsent(boolean consent) {
        try {
            // Example implementation for Firebase Analytics Consent Mode
            // Note: This is a placeholder - you'll need to implement actual Firebase integration
            Logger.info("Applying Firebase consent: " + consent);

            // Example Firebase consent application:
            // Firebase.analytics.setConsent {
            //     analyticsStorage(if (consent) ConsentStatus.GRANTED else ConsentStatus.DENIED)
            //     adStorage(if (consent) ConsentStatus.GRANTED else ConsentStatus.DENIED)
            // }
        } catch (Exception e) {
            Logger.error("Error applying Firebase consent", e);
        }
    }

    private void applyUnityAdsConsent(boolean consent) {
        try {
            // Example implementation for Unity Ads
            Logger.info("Applying Unity Ads consent: " + consent);

            // Example Unity Ads consent application:
            // if (consent) {
            //     UnityAds.initialize(this, "your-game-id", this, true);
            // }
        } catch (Exception e) {
            Logger.error("Error applying Unity Ads consent", e);
        }
    }

    private void applyAppLovinConsent(boolean consent) {
        try {
            // Example implementation for AppLovin
            Logger.info("Applying AppLovin consent: " + consent);

            // Example AppLovin consent application:
            // if (consent) {
            //     AppLovinSdk.getInstance().setPrivacySettings(privacySettings);
            // }
        } catch (Exception e) {
            Logger.error("Error applying AppLovin consent", e);
        }
    }

    public void getTCFData(CMPDataCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            usercentricsSDK.getTCFData((TCFData tcfData) -> {
                callback.onSuccess(TCFDataSerializer.serialize(tcfData));
                return null;
            });
        } catch (Exception e) {
            Logger.error("Usercentrics getTCFData error", e);
            callback.onError(e.getMessage());
        }
    }

    public void acceptAll(String consentType, ConsentsCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.acceptAll(consentTypeFromString(consentType));
            applyConsentToSDKs(consents);

            callback.onSuccess(consentsResult(consents));
        } catch (Exception e) {
            Logger.error("Usercentrics acceptAll error", e);
            callback.onError(e.getMessage());
        }
    }

    public void denyAll(String consentType, ConsentsCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.denyAll(consentTypeFromString(consentType));
            applyConsentToSDKs(consents);

            callback.onSuccess(consentsResult(consents));
        } catch (Exception e) {
            Logger.error("Usercentrics denyAll error", e);
            callback.onError(e.getMessage());
        }
    }

    public void acceptAllForTCF(String fromLayer, String consentType, ConsentsCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.acceptAllForTCF(
                tcfDecisionUILayerFromString(fromLayer),
                consentTypeFromString(consentType)
            );
            applyConsentToSDKs(consents);

            callback.onSuccess(consentsResult(consents));
        } catch (Exception e) {
            Logger.error("Usercentrics acceptAllForTCF error", e);
            callback.onError(e.getMessage());
        }
    }

    public void denyAllForTCF(
        String fromLayer,
        String consentType,
        JSArray unsavedPurposeLIDecisions,
        JSArray unsavedVendorLIDecisions,
        ConsentsCallback callback
    ) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.denyAllForTCF(
                tcfDecisionUILayerFromString(fromLayer),
                consentTypeFromString(consentType),
                UserDecisionDeserializer.deserializePurposeLIDecisionsMap(unsavedPurposeLIDecisions),
                UserDecisionDeserializer.deserializePurposeLIDecisionsMap(unsavedVendorLIDecisions)
            );
            applyConsentToSDKs(consents);

            callback.onSuccess(consentsResult(consents));
        } catch (Exception e) {
            Logger.error("Usercentrics denyAllForTCF error", e);
            callback.onError(e.getMessage());
        }
    }

    public void saveDecisions(JSArray decisions, String consentType, ConsentsCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.saveDecisions(
                UserDecisionDeserializer.deserializeUserDecisions(decisions),
                consentTypeFromString(consentType)
            );
            applyConsentToSDKs(consents);

            callback.onSuccess(consentsResult(consents));
        } catch (Exception e) {
            Logger.error("Usercentrics saveDecisions error", e);
            callback.onError(e.getMessage());
        }
    }

    public void saveDecisionsForTCF(
        JSObject tcfDecisions,
        String fromLayer,
        JSArray decisions,
        String consentType,
        ConsentsCallback callback
    ) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            TCFUserDecisions tcfUserDecisions = UserDecisionDeserializer.deserializeTCFUserDecisions(tcfDecisions);
            List<UsercentricsServiceConsent> consents = usercentricsSDK.saveDecisionsForTCF(
                tcfUserDecisions,
                tcfDecisionUILayerFromString(fromLayer),
                UserDecisionDeserializer.deserializeUserDecisions(decisions),
                consentTypeFromString(consentType)
            );
            applyConsentToSDKs(consents);

            callback.onSuccess(consentsResult(consents));
        } catch (Exception e) {
            Logger.error("Usercentrics saveDecisionsForTCF error", e);
            callback.onError(e.getMessage());
        }
    }

    public void saveOptOutForCCPA(boolean isOptedOut, String consentType, ConsentsCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.saveOptOutForCCPA(isOptedOut, consentTypeFromString(consentType));
            applyConsentToSDKs(consents);

            callback.onSuccess(consentsResult(consents));
        } catch (Exception e) {
            Logger.error("Usercentrics saveOptOutForCCPA error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getUserSessionData(SessionCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            callback.onSuccess(usercentricsSDK.getUserSessionData());
        } catch (Exception e) {
            Logger.error("Usercentrics getUserSessionData error", e);
            callback.onError(e.getMessage());
        }
    }

    private static UsercentricsConsentType consentTypeFromString(String consentType) {
        if ("implicit".equalsIgnoreCase(consentType)) {
            return UsercentricsConsentType.IMPLICIT;
        }
        return UsercentricsConsentType.EXPLICIT;
    }

    private static TCFDecisionUILayer tcfDecisionUILayerFromString(String fromLayer) {
        if ("secondLayer".equalsIgnoreCase(fromLayer)) {
            return TCFDecisionUILayer.SECOND_LAYER;
        }
        return TCFDecisionUILayer.FIRST_LAYER;
    }

    private static JSObject consentsResult(List<UsercentricsServiceConsent> consents) {
        JSObject result = new JSObject();
        result.put("consents", ServiceConsentSerializer.serializeConsents(consents));
        return result;
    }

    public void saveConsent(JSObject consentsData, Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            // Convert JSObject consents back to List<UserDecision>
            List<UserDecision> decisions = new ArrayList<>();
            List<UsercentricsServiceConsent> consents = new ArrayList<>();

            // Iterate through the consents object
            for (Iterator<String> it = consentsData.keys(); it.hasNext(); ) {
                String key = it.next();
                JSObject consentData = consentsData.getJSObject(key);
                if (consentData != null) {
                    String templateId = consentData.getString("templateId");
                    boolean status = consentData.getBool("status");
                    String dataProcessor = consentData.getString("dataProcessor");
                    String version = consentData.getString("version");

                    // Create a UsercentricsServiceConsent object for applyConsentToSDKs
                    UsercentricsServiceConsent consent = new UsercentricsServiceConsent(
                        templateId,
                        status,
                        new ArrayList(), // history - empty list
                        null, // type - null for now
                        dataProcessor,
                        version,
                        false, // isEssential - default to false
                        "" // additional string required by current SDK
                    );
                    consents.add(consent);

                    // Create a UserDecision object for saveDecisions
                    UserDecision decision = new UserDecision(templateId, status);
                    decisions.add(decision);
                }
            }

            // Save consent and apply to SDKs
            usercentricsSDK.saveDecisions(decisions, UsercentricsConsentType.EXPLICIT);
            applyConsentToSDKs(consents);

            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics saveConsent error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getControllerId(ControllerIdCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }
            callback.onSuccess(usercentricsSDK.getControllerId());
        } catch (Exception e) {
            Logger.error("Usercentrics getControllerId error", e);
            callback.onError(e.getMessage());
        }
    }

    public void clearUserSession(ReadyCallback callback) {
        try {
            Usercentrics.getInstance().clearUserSession(
                (UsercentricsReadyStatus status) -> {
                    usercentricsSDK = Usercentrics.getInstance();
                    JSObject result = new JSObject();
                    result.put("shouldCollectConsent", status.getShouldCollectConsent());
                    result.put("controllerId", usercentricsSDK.getControllerId());
                    result.put("consents", ServiceConsentSerializer.serializeConsents(status.getConsents()));

                    callback.onSuccess(result);
                    return null;
                },
                (UsercentricsError error) -> {
                    callback.onError(error.getMessage());
                    return null;
                }
            );
        } catch (Exception e) {
            Logger.error("Usercentrics clearUserSession error", e);
            callback.onError(e.getMessage());
        }
    }

    public void changeLanguage(String language, Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            usercentricsSDK.changeLanguage(
                language,
                () -> {
                    callback.onSuccess();
                    return null;
                },
                (UsercentricsError error) -> {
                    callback.onError(error.getMessage());
                    return null;
                }
            );
        } catch (Exception e) {
            Logger.error("Usercentrics changeLanguage error", e);
            callback.onError(e.getMessage());
        }
    }

    public void setCMPId(int id) {
        Usercentrics.getInstance().setCMPId(id);
    }

    public void setABTestingVariant(String variant) {
        Usercentrics.getInstance().setABTestingVariant(variant);
    }

    public void getABTestingVariant(VariantCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }
            callback.onSuccess(usercentricsSDK.getABTestingVariant());
        } catch (Exception e) {
            Logger.error("Usercentrics getABTestingVariant error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getCCPAData(CMPDataCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            CCPAData uspData = usercentricsSDK.getUSPData();
            JSObject result = new JSObject();
            result.put("version", uspData.getVersion());
            result.put("uspString", uspData.getUspString());
            result.put("optedOut", uspData.getOptedOut());
            result.put("lspact", uspData.getLspact());
            result.put("noticeGiven", uspData.getNoticeGiven());

            callback.onSuccess(result);
        } catch (Exception e) {
            Logger.error("Usercentrics getCCPAData error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getAdditionalConsentModeData(CMPDataCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            AdditionalConsentModeData acmData = usercentricsSDK.getAdditionalConsentModeData();
            JSObject result = new JSObject();
            result.put("acString", acmData.getAcString());

            JSArray providersArr = new JSArray();
            for (AdTechProvider provider : acmData.getAdTechProviders()) {
                JSObject providerObj = new JSObject();
                providerObj.put("id", provider.getId());
                providerObj.put("name", provider.getName());
                providerObj.put("privacyPolicyUrl", provider.getPrivacyPolicyUrl());
                providerObj.put("consent", provider.getConsent());
                providersArr.put(providerObj);
            }
            result.put("adTechProviders", providersArr);

            callback.onSuccess(result);
        } catch (Exception e) {
            Logger.error("Usercentrics getAdditionalConsentModeData error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getGPPData(CMPDataCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            GppData gppData = usercentricsSDK.getGPPData();
            JSObject result = new JSObject();
            result.put("gppString", gppData.getGppString());

            JSArray sectionsArr = new JSArray();
            if (gppData.getApplicableSections() != null) {
                for (Integer section : gppData.getApplicableSections()) {
                    sectionsArr.put(section);
                }
            }
            result.put("applicableSections", sectionsArr);

            JSONObject sections = new JSONObject();
            for (Map.Entry<String, ? extends Map<String, ?>> entry : gppData.getSections().entrySet()) {
                sections.put(entry.getKey(), JSONObject.wrap(entry.getValue()));
            }
            result.put("sections", sections);

            callback.onSuccess(result);
        } catch (Exception e) {
            Logger.error("Usercentrics getGPPData error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getGPPString(GppStringCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }
            callback.onSuccess(usercentricsSDK.getGPPString());
        } catch (Exception e) {
            Logger.error("Usercentrics getGPPString error", e);
            callback.onError(e.getMessage());
        }
    }

    public void setGPPConsent(String sectionName, String fieldName, Object value, Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }
            Object safeValue = value == null ? JSONObject.NULL : jsonValueToJava(value);
            usercentricsSDK.setGPPConsent(sectionName, fieldName, safeValue);
            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics setGPPConsent error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getDpsMetadata(String templateId, CMPDataCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }
            Map<String, Object> metadata = usercentricsSDK.getDpsMetadata(templateId);
            JSObject result = new JSObject();
            result.put("metadata", metadata == null ? JSONObject.NULL : JSONObject.wrap(metadata));
            callback.onSuccess(result);
        } catch (Exception e) {
            Logger.error("Usercentrics getDpsMetadata error", e);
            callback.onError(e.getMessage());
        }
    }

    public void startGppSectionChangeListener(GppSectionChangeListener listener) {
        if (gppSectionChangeEvent != null) return;
        gppSectionChangeEvent = UsercentricsEvent.INSTANCE.onGppSectionChange((payload) -> {
            JSObject result = new JSObject();
            result.put("data", payload.getData());
            listener.onSectionChange(result);
            return null;
        });
    }

    public void stopGppSectionChangeListener() {
        if (gppSectionChangeEvent != null) {
            gppSectionChangeEvent.dispose();
            gppSectionChangeEvent = null;
        }
    }

    // Converts org.json values from the bridge into plain Java types for the SDK
    private Object jsonValueToJava(Object value) throws Exception {
        if (value == null || value == JSONObject.NULL) {
            return JSONObject.NULL;
        }
        if (value instanceof JSONObject) {
            JSONObject obj = (JSONObject) value;
            Map<String, Object> map = new HashMap<>();
            for (Iterator<String> it = obj.keys(); it.hasNext(); ) {
                String key = it.next();
                map.put(key, jsonValueToJava(obj.get(key)));
            }
            return map;
        }
        if (value instanceof JSONArray) {
            JSONArray arr = (JSONArray) value;
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                list.add(jsonValueToJava(arr.get(i)));
            }
            return list;
        }
        return value;
    }

    public void track(int event) {
        try {
            UsercentricsAnalyticsEventType[] eventTypes = UsercentricsAnalyticsEventType.values();
            if (event >= 0 && event < eventTypes.length) {
                Usercentrics.getInstance().track(eventTypes[event]);
            }
        } catch (Exception e) {
            Logger.error("Usercentrics track error", e);
        }
    }
}
