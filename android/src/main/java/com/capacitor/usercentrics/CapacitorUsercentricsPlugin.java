package com.capacitor.usercentrics;

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
    }

    @PluginMethod
    public void configure(PluginCall call) {
        JSObject options = call.getObject("options");
        if (options == null) {
            options = call.getData();
        }
        implementation.configure(options, new CapacitorUsercentrics.Callback() {
            @Override
            public void onSuccess() {
                call.resolve();
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void isReady(PluginCall call) {
        implementation.isReady(new CapacitorUsercentrics.ReadyCallback() {
            @Override
            public void onSuccess(JSObject status) {
                call.resolve(status);
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void showBanner(PluginCall call) {
        implementation.showBanner(new CapacitorUsercentrics.BannerCallback() {
            @Override
            public void onSuccess(JSObject result) {
                call.resolve(result);
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void showSecondLayer(PluginCall call) {
        implementation.showSecondLayer(new CapacitorUsercentrics.BannerCallback() {
            @Override
            public void onSuccess(JSObject result) {
                call.resolve(result);
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void reset(PluginCall call) {
        implementation.reset(new CapacitorUsercentrics.Callback() {
            @Override
            public void onSuccess() {
                call.resolve();
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void getConsents(PluginCall call) {
        implementation.getConsents(new CapacitorUsercentrics.ConsentsCallback() {
            @Override
            public void onSuccess(JSObject consents) {
                call.resolve(consents);
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void getCMPData(PluginCall call) {
        implementation.getCMPData(new CapacitorUsercentrics.CMPDataCallback() {
            @Override
            public void onSuccess(JSObject data) {
                call.resolve(data);
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void getTCFData(PluginCall call) {
        implementation.getTCFData(new CapacitorUsercentrics.CMPDataCallback() {
            @Override
            public void onSuccess(JSObject data) {
                call.resolve(data);
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void restoreUserSession(PluginCall call) {
        String userSession = call.getString("userSession");
        implementation.restoreUserSession(userSession, new CapacitorUsercentrics.Callback() {
            @Override
            public void onSuccess() {
                call.resolve();
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void saveUserSession(PluginCall call) {
        implementation.saveUserSession(new CapacitorUsercentrics.SessionCallback() {
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
        });
    }

    @PluginMethod
    public void acceptAll(PluginCall call) {
        implementation.acceptAll(new CapacitorUsercentrics.Callback() {
            @Override
            public void onSuccess() {
                call.resolve();
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void denyAll(PluginCall call) {
        implementation.denyAll(new CapacitorUsercentrics.Callback() {
            @Override
            public void onSuccess() {
                call.resolve();
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void applyConsent(PluginCall call) {
        JSObject consents = call.getObject("consents");
        if (consents == null) {
            consents = call.getData();
        }
        implementation.applyConsent(consents, new CapacitorUsercentrics.Callback() {
            @Override
            public void onSuccess() {
                call.resolve();
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }

    @PluginMethod
    public void saveConsent(PluginCall call) {
        JSObject consents = call.getObject("consents");
        if (consents == null) {
            consents = call.getData();
        }
        implementation.saveConsent(consents, new CapacitorUsercentrics.Callback() {
            @Override
            public void onSuccess() {
                call.resolve();
            }

            @Override
            public void onError(String error) {
                call.reject(error);
            }
        });
    }
}
