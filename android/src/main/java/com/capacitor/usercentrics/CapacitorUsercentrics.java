package com.capacitor.usercentrics;

import android.app.Activity;
import android.content.Context;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.usercentrics.sdk.Usercentrics;
import com.usercentrics.sdk.UsercentricsBanner;
import com.usercentrics.sdk.UsercentricsConsentUserResponse;
import com.usercentrics.sdk.UsercentricsOptions;
import com.usercentrics.sdk.UsercentricsReadyStatus;
import com.usercentrics.sdk.models.consent.UsercentricsConsent;
import com.usercentrics.sdk.models.consent.UsercentricsServiceConsent;
import java.util.List;
import java.util.Map;

public class CapacitorUsercentrics {

    private Usercentrics usercentrics;
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

            UsercentricsOptions.Builder optionsBuilder = new UsercentricsOptions.Builder(settingsId);

            if (options.has("defaultLanguage")) {
                optionsBuilder.setDefaultLanguage(options.getString("defaultLanguage"));
            }
            if (options.has("version")) {
                optionsBuilder.setVersion(options.getString("version"));
            }
            if (options.has("timeoutMillis")) {
                optionsBuilder.setTimeoutMillis(options.getInt("timeoutMillis"));
            }
            if (options.has("loggerLevel")) {
                String level = options.getString("loggerLevel");
                switch (level) {
                    case "debug":
                        optionsBuilder.setLoggerLevel(UsercentricsOptions.LoggerLevel.DEBUG);
                        break;
                    case "warning":
                        optionsBuilder.setLoggerLevel(UsercentricsOptions.LoggerLevel.WARNING);
                        break;
                    case "error":
                        optionsBuilder.setLoggerLevel(UsercentricsOptions.LoggerLevel.ERROR);
                        break;
                    case "none":
                        optionsBuilder.setLoggerLevel(UsercentricsOptions.LoggerLevel.NONE);
                        break;
                }
            }
            if (options.has("rulesetId")) {
                optionsBuilder.setRulesetId(options.getString("rulesetId"));
            }
            if (options.has("consentMediation")) {
                optionsBuilder.setConsentMediation(options.getBool("consentMediation"));
            }

            UsercentricsOptions usercentricsOptions = optionsBuilder.build();
            usercentrics = Usercentrics.initialize(context, usercentricsOptions);
            
            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics configure error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }

    public void isReady(ReadyCallback callback) {
        try {
            if (usercentrics == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            usercentrics.isReady(new Usercentrics.UsercentricsReadyCallback() {
                @Override
                public void onSuccess(UsercentricsReadyStatus status) {
                    JSObject result = new JSObject();
                    result.put("shouldCollectConsent", status.shouldCollectConsent);
                    result.put("usercentricsReady", status.usercentricsReady);
                    result.put("controllerId", status.controllerId);
                    
                    JSObject consentsArray = new JSObject();
                    for (UsercentricsServiceConsent consent : status.consents) {
                        JSObject consentObj = new JSObject();
                        consentObj.put("templateId", consent.getTemplateId());
                        consentObj.put("status", consent.isConsentGiven());
                        consentObj.put("type", consent.getType().toString().toLowerCase());
                        consentObj.put("timestamp", consent.getTimestamp());
                        consentObj.put("dataProcessor", consent.getDataProcessor());
                        consentObj.put("version", consent.getVersion());
                        consentObj.put("isEssential", consent.isEssential());
                        consentsArray.put(consent.getTemplateId(), consentObj);
                    }
                    result.put("consents", consentsArray);
                    
                    callback.onSuccess(result);
                }

                @Override
                public void onFailure(String error) {
                    callback.onError(error);
                }
            });
        } catch (Exception e) {
            Logger.error("Usercentrics isReady error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }

    public void showBanner(BannerCallback callback) {
        try {
            if (usercentrics == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            if (!(context instanceof Activity)) {
                callback.onError("Context must be an Activity to show banner");
                return;
            }

            UsercentricsBanner banner = new UsercentricsBanner((Activity) context);
            banner.showFirstLayer(new UsercentricsBanner.UsercentricsBannerCallback() {
                @Override
                public void onSuccess(UsercentricsConsentUserResponse response) {
                    JSObject result = new JSObject();
                    result.put("userInteraction", response.isUserInteraction());
                    
                    JSObject consentsArray = new JSObject();
                    for (UsercentricsServiceConsent consent : response.getConsents()) {
                        JSObject consentObj = new JSObject();
                        consentObj.put("templateId", consent.getTemplateId());
                        consentObj.put("status", consent.isConsentGiven());
                        consentObj.put("type", consent.getType().toString().toLowerCase());
                        consentObj.put("timestamp", consent.getTimestamp());
                        consentObj.put("dataProcessor", consent.getDataProcessor());
                        consentObj.put("version", consent.getVersion());
                        consentObj.put("isEssential", consent.isEssential());
                        consentsArray.put(consent.getTemplateId(), consentObj);
                    }
                    result.put("consents", consentsArray);
                    
                    callback.onSuccess(result);
                }

                @Override
                public void onFailure(String error) {
                    callback.onError(error);
                }
            });
        } catch (Exception e) {
            Logger.error("Usercentrics showBanner error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }

    public void reset(Callback callback) {
        try {
            if (usercentrics == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            usercentrics.reset();
            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics reset error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }

    public void getConsents(ConsentsCallback callback) {
        try {
            if (usercentrics == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            List<UsercentricsServiceConsent> consents = usercentrics.getConsents();
            JSObject consentsArray = new JSObject();
            
            for (UsercentricsServiceConsent consent : consents) {
                JSObject consentObj = new JSObject();
                consentObj.put("templateId", consent.getTemplateId());
                consentObj.put("status", consent.isConsentGiven());
                consentObj.put("type", consent.getType().toString().toLowerCase());
                consentObj.put("timestamp", consent.getTimestamp());
                consentObj.put("dataProcessor", consent.getDataProcessor());
                consentObj.put("version", consent.getVersion());
                consentObj.put("isEssential", consent.isEssential());
                consentsArray.put(consent.getTemplateId(), consentObj);
            }
            
            callback.onSuccess(consentsArray);
        } catch (Exception e) {
            Logger.error("Usercentrics getConsents error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }

    public void getCMPData(CMPDataCallback callback) {
        try {
            if (usercentrics == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            Map<String, Object> cmpData = usercentrics.getCMPData();
            JSObject result = new JSObject();
            
            for (Map.Entry<String, Object> entry : cmpData.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
            
            callback.onSuccess(result);
        } catch (Exception e) {
            Logger.error("Usercentrics getCMPData error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }

    public void restoreUserSession(String userSession, Callback callback) {
        try {
            if (usercentrics == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            usercentrics.restoreUserSession(userSession);
            callback.onSuccess();
        } catch (Exception e) {
            Logger.error("Usercentrics restoreUserSession error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }

    public void saveUserSession(SessionCallback callback) {
        try {
            if (usercentrics == null) {
                callback.onError("Usercentrics not configured");
                return;
            }

            String session = usercentrics.saveUserSession();
            callback.onSuccess(session);
        } catch (Exception e) {
            Logger.error("Usercentrics saveUserSession error", e.getMessage());
            callback.onError(e.getMessage());
        }
    }
}
