package com.capacitor.usercentrics;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorUsercentrics")
public class CapacitorUsercentricsPlugin extends Plugin {

    private CapacitorUsercentrics implementation = new CapacitorUsercentrics();

    @Override
    public void load() {
        super.load();
        implementation.setContext(getActivity());
        implementation.startGppSectionChangeListener((payload) -> notifyListeners("onGppSectionChange", payload));
    }

    @Override
    protected void handleOnDestroy() {
        implementation.stopGppSectionChangeListener();
        super.handleOnDestroy();
    }

    @PluginMethod
    public void configure(PluginCall call) {
        JSObject options = call.getObject("options");
        if (options == null) {
            options = call.getData();
        }
        implementation.configure(
            options,
            new CapacitorUsercentrics.Callback() {
                @Override
                public void onSuccess() {
                    call.resolve();
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void isReady(PluginCall call) {
        implementation.isReady(
            new CapacitorUsercentrics.ReadyCallback() {
                @Override
                public void onSuccess(JSObject status) {
                    call.resolve(status);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void showBanner(PluginCall call) {
        implementation.showBanner(
            call.getData(),
            new CapacitorUsercentrics.BannerCallback() {
                @Override
                public void onSuccess(JSObject result) {
                    call.resolve(result);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void showSecondLayer(PluginCall call) {
        implementation.showSecondLayer(
            call.getData(),
            new CapacitorUsercentrics.BannerCallback() {
                @Override
                public void onSuccess(JSObject result) {
                    call.resolve(result);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getConsents(PluginCall call) {
        implementation.getConsents(
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getCMPData(PluginCall call) {
        implementation.getCMPData(
            new CapacitorUsercentrics.CMPDataCallback() {
                @Override
                public void onSuccess(JSObject data) {
                    call.resolve(data);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getTCFData(PluginCall call) {
        implementation.getTCFData(
            new CapacitorUsercentrics.CMPDataCallback() {
                @Override
                public void onSuccess(JSObject data) {
                    call.resolve(data);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void restoreUserSession(PluginCall call) {
        String controllerId = call.getString("controllerId");
        if (controllerId == null) {
            call.reject("controllerId parameter is required");
            return;
        }
        implementation.restoreUserSession(
            controllerId,
            new CapacitorUsercentrics.ReadyCallback() {
                @Override
                public void onSuccess(JSObject status) {
                    call.resolve(status);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void saveUserSession(PluginCall call) {
        implementation.saveUserSession(
            new CapacitorUsercentrics.SessionCallback() {
                @Override
                public void onSuccess(String session) {
                    JSObject result = new JSObject();
                    result.put("session", session);
                    call.resolve(result);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void acceptAll(PluginCall call) {
        implementation.acceptAll(
            call.getString("consentType"),
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void denyAll(PluginCall call) {
        implementation.denyAll(
            call.getString("consentType"),
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void acceptAllForTCF(PluginCall call) {
        implementation.acceptAllForTCF(
            call.getString("fromLayer"),
            call.getString("consentType"),
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void denyAllForTCF(PluginCall call) {
        implementation.denyAllForTCF(
            call.getString("fromLayer"),
            call.getString("consentType"),
            call.getArray("unsavedPurposeLIDecisions", null),
            call.getArray("unsavedVendorLIDecisions", null),
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void saveDecisions(PluginCall call) {
        JSArray decisions = call.getArray("decisions", null);
        if (decisions == null) {
            call.reject("decisions parameter is required");
            return;
        }
        implementation.saveDecisions(
            decisions,
            call.getString("consentType"),
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void saveDecisionsForTCF(PluginCall call) {
        JSObject tcfDecisions = call.getObject("tcfDecisions");
        if (tcfDecisions == null) {
            call.reject("tcfDecisions parameter is required");
            return;
        }
        implementation.saveDecisionsForTCF(
            tcfDecisions,
            call.getString("fromLayer"),
            call.getArray("decisions", null),
            call.getString("consentType"),
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void saveOptOutForCCPA(PluginCall call) {
        Boolean isOptedOut = call.getBoolean("isOptedOut");
        if (isOptedOut == null) {
            call.reject("isOptedOut parameter is required");
            return;
        }
        implementation.saveOptOutForCCPA(
            isOptedOut,
            call.getString("consentType"),
            new CapacitorUsercentrics.ConsentsCallback() {
                @Override
                public void onSuccess(JSObject consents) {
                    call.resolve(consents);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getUserSessionData(PluginCall call) {
        implementation.getUserSessionData(
            new CapacitorUsercentrics.SessionCallback() {
                @Override
                public void onSuccess(String session) {
                    JSObject result = new JSObject();
                    result.put("session", session);
                    call.resolve(result);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void applyConsent(PluginCall call) {
        JSObject consents = call.getObject("consents");
        if (consents == null) {
            consents = call.getData();
        }
        implementation.applyConsent(
            consents,
            new CapacitorUsercentrics.Callback() {
                @Override
                public void onSuccess() {
                    call.resolve();
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void saveConsent(PluginCall call) {
        JSObject consents = call.getObject("consents");
        if (consents == null) {
            consents = call.getData();
        }
        implementation.saveConsent(
            consents,
            new CapacitorUsercentrics.Callback() {
                @Override
                public void onSuccess() {
                    call.resolve();
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getControllerId(PluginCall call) {
        implementation.getControllerId(
            new CapacitorUsercentrics.ControllerIdCallback() {
                @Override
                public void onSuccess(String controllerId) {
                    JSObject result = new JSObject();
                    result.put("controllerId", controllerId);
                    call.resolve(result);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void clearUserSession(PluginCall call) {
        implementation.clearUserSession(
            new CapacitorUsercentrics.ReadyCallback() {
                @Override
                public void onSuccess(JSObject status) {
                    call.resolve(status);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void changeLanguage(PluginCall call) {
        String language = call.getString("language");
        if (language == null) {
            call.reject("language parameter is required");
            return;
        }
        implementation.changeLanguage(
            language,
            new CapacitorUsercentrics.Callback() {
                @Override
                public void onSuccess() {
                    call.resolve();
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void setCMPId(PluginCall call) {
        Integer id = call.getInt("id");
        if (id == null) {
            call.reject("id parameter is required");
            return;
        }
        implementation.setCMPId(id);
        call.resolve();
    }

    @PluginMethod
    public void setABTestingVariant(PluginCall call) {
        String variant = call.getString("variant");
        if (variant == null) {
            call.reject("variant parameter is required");
            return;
        }
        implementation.setABTestingVariant(variant);
        call.resolve();
    }

    @PluginMethod
    public void getABTestingVariant(PluginCall call) {
        implementation.getABTestingVariant(
            new CapacitorUsercentrics.VariantCallback() {
                @Override
                public void onSuccess(String variant) {
                    JSObject result = new JSObject();
                    result.put("variant", variant);
                    call.resolve(result);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getCCPAData(PluginCall call) {
        implementation.getCCPAData(
            new CapacitorUsercentrics.CMPDataCallback() {
                @Override
                public void onSuccess(JSObject data) {
                    call.resolve(data);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getAdditionalConsentModeData(PluginCall call) {
        implementation.getAdditionalConsentModeData(
            new CapacitorUsercentrics.CMPDataCallback() {
                @Override
                public void onSuccess(JSObject data) {
                    call.resolve(data);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getGPPData(PluginCall call) {
        implementation.getGPPData(
            new CapacitorUsercentrics.CMPDataCallback() {
                @Override
                public void onSuccess(JSObject data) {
                    call.resolve(data);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getGPPString(PluginCall call) {
        implementation.getGPPString(
            new CapacitorUsercentrics.GppStringCallback() {
                @Override
                public void onSuccess(String gppString) {
                    JSObject result = new JSObject();
                    result.put("gppString", gppString);
                    call.resolve(result);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void setGPPConsent(PluginCall call) {
        String sectionName = call.getString("sectionName");
        String fieldName = call.getString("fieldName");
        if (sectionName == null || fieldName == null) {
            call.reject("sectionName and fieldName parameters are required");
            return;
        }
        Object value = call.getData().opt("value");
        implementation.setGPPConsent(
            sectionName,
            fieldName,
            value,
            new CapacitorUsercentrics.Callback() {
                @Override
                public void onSuccess() {
                    call.resolve();
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void getDpsMetadata(PluginCall call) {
        String templateId = call.getString("templateId");
        if (templateId == null) {
            call.reject("templateId parameter is required");
            return;
        }
        implementation.getDpsMetadata(
            templateId,
            new CapacitorUsercentrics.CMPDataCallback() {
                @Override
                public void onSuccess(JSObject data) {
                    call.resolve(data);
                }

                @Override
                public void onError(String error) {
                    call.reject(error);
                }
            }
        );
    }

    @PluginMethod
    public void track(PluginCall call) {
        Integer event = call.getInt("event");
        if (event == null) {
            call.reject("event parameter is required");
            return;
        }
        implementation.track(event);
        call.resolve();
    }
}
