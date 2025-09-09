package com.capacitor.usercentrics;

import android.app.Activity;
import android.content.Context;
import com.getcapacitor.JSObject;
import com.getcapacitor.JSArray;
import com.getcapacitor.Logger;
import com.usercentrics.sdk.*;
import com.usercentrics.sdk.errors.UsercentricsError;
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel;
import com.usercentrics.sdk.models.settings.UsercentricsConsentType;
import com.usercentrics.sdk.services.tcf.interfaces.TCFData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
                    
                    // Convert consents list to array of objects
                    JSArray consentsArr = new JSArray();
                    for (UsercentricsServiceConsent consent : status.getConsents()) {
                        JSObject consentObj = new JSObject();
                        consentObj.put("templateId", consent.getTemplateId());
                        consentObj.put("status", consent.getStatus());
                        consentObj.put("dataProcessor", consent.getDataProcessor());
                        consentObj.put("version", consent.getVersion());
                        consentObj.put("isEssential", consent.isEssential());
                        consentsArr.put(consentObj);
                    }
                    result.put("consents", consentsArr);
                    
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

    public void showBanner(BannerCallback callback) {
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
            activity.runOnUiThread(() -> {
                UsercentricsBanner banner = new UsercentricsBanner(activity, null);
                banner.showFirstLayer((UsercentricsConsentUserResponse response) -> {
                        JSObject result = new JSObject();
                        
                        String userInteraction = response.getUserInteraction().toString();
                        result.put("userInteraction", userInteraction);
                        result.put("controllerId", response.getControllerId());
                        
                        JSArray consentsArr = new JSArray();
                        for (UsercentricsServiceConsent consent : response.getConsents()) {
                            JSObject consentObj = new JSObject();
                            consentObj.put("templateId", consent.getTemplateId());
                            consentObj.put("status", consent.getStatus());
                            consentObj.put("dataProcessor", consent.getDataProcessor());
                            consentObj.put("version", consent.getVersion());
                            consentObj.put("isEssential", consent.isEssential());
                            consentsArr.put(consentObj);
                        }
                        result.put("consents", consentsArr);
                        
                        callback.onSuccess(result);
                        return null;
                    });
            });
        } catch (Exception e) {
            Logger.error("Usercentrics showBanner error", e);
            callback.onError(e.getMessage());
        }
    }

    public void showSecondLayer(BannerCallback callback) {
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
            activity.runOnUiThread(() -> {
                UsercentricsBanner banner = new UsercentricsBanner(activity, null);
                banner.showSecondLayer((UsercentricsConsentUserResponse response) -> {
                        JSObject result = new JSObject();
                        
                        String userInteraction = response.getUserInteraction().toString();
                        result.put("userInteraction", userInteraction);
                        result.put("controllerId", response.getControllerId());
                        
                        JSArray consentsArr = new JSArray();
                        for (UsercentricsServiceConsent consent : response.getConsents()) {
                            JSObject consentObj = new JSObject();
                            consentObj.put("templateId", consent.getTemplateId());
                            consentObj.put("status", consent.getStatus());
                            consentObj.put("dataProcessor", consent.getDataProcessor());
                            consentObj.put("version", consent.getVersion());
                            consentObj.put("isEssential", consent.isEssential());
                            consentsArr.put(consentObj);
                        }
                        result.put("consents", consentsArr);
                        
                        callback.onSuccess(result);
                        return null;
                    });
            });
        } catch (Exception e) {
            Logger.error("Usercentrics showSecondLayer error", e);
            callback.onError(e.getMessage());
        }
    }

    public void reset(Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            Usercentrics.reset();
            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics reset error", e);
            callback.onError(e.getMessage());
        }
    }

    public void getConsents(ConsentsCallback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.getConsents();
            JSArray consentsArr = new JSArray();
            
            for (UsercentricsServiceConsent consent : consents) {
                JSObject consentObj = new JSObject();
                consentObj.put("templateId", consent.getTemplateId());
                consentObj.put("status", consent.getStatus());
                consentObj.put("dataProcessor", consent.getDataProcessor());
                consentObj.put("version", consent.getVersion());
                consentObj.put("isEssential", consent.isEssential());
                consentsArr.put(consentObj);
            }
            
            JSObject result = new JSObject();
            result.put("consents", consentsArr);
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

            Object cmpData = usercentricsSDK.getCMPData();
            JSObject result = new JSObject();
            result.put("cmpData", cmpData);
            
            callback.onSuccess(result);
        } catch (Exception e) {
            Logger.error("Usercentrics getCMPData error", e);
            callback.onError(e.getMessage());
        }
    }

    public void restoreUserSession(String userSession, Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            usercentricsSDK.restoreUserSession(usercentricsSDK.getControllerId(),
                (UsercentricsReadyStatus status) -> {
                    callback.onSuccess();
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
                        new ArrayList<>(), // history - empty list
                        null, // type - null for now
                        dataProcessor, 
                        version, 
                        false // isEssential - default to false
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
                    JSObject result = new JSObject();
                    result.put("tcString", tcfData.getTcString());
                    result.put("features", tcfData.getFeatures());
                    result.put("purposes", tcfData.getPurposes());
                    result.put("specialFeatures", tcfData.getSpecialFeatures());
                    result.put("specialPurposes", tcfData.getSpecialPurposes());
                    result.put("stacks", tcfData.getStacks());
                    result.put("thirdPartyCount", tcfData.getThirdPartyCount());
                    result.put("vendors", tcfData.getVendors());
                    
                    callback.onSuccess(result);
                    return null;
                });
        } catch (Exception e) {
            Logger.error("Usercentrics getTCFData error", e);
            callback.onError(e.getMessage());
        }
    }

    public void acceptAll(Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.acceptAll(UsercentricsConsentType.EXPLICIT);
            applyConsentToSDKs(consents);
            
            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics acceptAll error", e);
            callback.onError(e.getMessage());
        }
    }

    public void denyAll(Callback callback) {
        try {
            if (usercentricsSDK == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentricsSDK.denyAll(UsercentricsConsentType.EXPLICIT);
            applyConsentToSDKs(consents);
            
            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics denyAll error", e);
            callback.onError(e.getMessage());
        }
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
                        new ArrayList<>(), // history - empty list
                        null, // type - null for now
                        dataProcessor, 
                        version, 
                        false // isEssential - default to false
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
}
