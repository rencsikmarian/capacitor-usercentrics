import { WebPlugin } from '@capacitor/core';

import type {
  CapacitorUsercentricsPlugin,
  UsercentricsOptions,
  UsercentricsReadyStatus,
  UsercentricsBannerResult,
  UsercentricsConsent,
  UsercentricsConsentsResult,
  UsercentricsConsentType,
  UsercentricsCMPData,
  TCFData,
  UserDecision,
  CCPAData,
  AdditionalConsentModeData,
  GppData,
  UsercentricsAnalyticsEventType,
} from './definitions';

export class CapacitorUsercentricsWeb extends WebPlugin implements CapacitorUsercentricsPlugin {
  private usercentrics: any = null;
  private isConfigured = false;

  async configure(options: UsercentricsOptions): Promise<void> {
    return new Promise((resolve, reject) => {
      // Load Usercentrics script if not already loaded
      if (!window.usercentrics) {
        const script = document.createElement('script');
        script.src = `https://app.usercentrics.com/browser-ui/latest/loader.js`;
        script.async = true;
        script.onload = () => {
          this.initializeUsercentrics(options, resolve, reject);
        };
        script.onerror = () => {
          reject(new Error('Failed to load Usercentrics script'));
        };
        document.head.appendChild(script);
      } else {
        this.initializeUsercentrics(options, resolve, reject);
      }
    });
  }

  private initializeUsercentrics(options: UsercentricsOptions, resolve: () => void, reject: (error: string) => void) {
    try {
      const usercentricsOptions = {
        settingsId: options.settingsId,
        defaultLanguage: options.defaultLanguage,
        version: options.version,
        timeoutMillis: options.timeoutMillis,
        loggerLevel: options.loggerLevel,
        rulesetId: options.rulesetId,
        consentMediation: options.consentMediation,
      };

      window.usercentrics.init(usercentricsOptions);
      this.usercentrics = window.usercentrics;
      this.isConfigured = true;
      resolve();
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : String(error);
      reject(message || 'Failed to initialize Usercentrics');
    }
  }

  async isReady(): Promise<UsercentricsReadyStatus> {
    return new Promise((resolve, reject) => {
      if (!this.isConfigured || !this.usercentrics) {
        reject(new Error('Usercentrics not configured'));
        return;
      }

      this.usercentrics.isReady(
        (status: any) => {
          const result: UsercentricsReadyStatus = {
            shouldCollectConsent: status.shouldCollectConsent,
            consents: this.convertConsents(status.consents),
            usercentricsReady: status.usercentricsReady,
            controllerId: status.controllerId,
          };
          resolve(result);
        },
        (error: string) => {
          reject(error);
        },
      );
    });
  }

  async showBanner(): Promise<UsercentricsBannerResult> {
    return new Promise((resolve, reject) => {
      if (!this.isConfigured || !this.usercentrics) {
        reject(new Error('Usercentrics not configured'));
        return;
      }

      this.usercentrics.showFirstLayer(
        (response: any) => {
          const result: UsercentricsBannerResult = {
            consents: this.convertConsents(response.consents),
            userInteraction: String(response.userInteraction),
            controllerId: response.controllerId,
          };
          resolve(result);
        },
        (error: string) => {
          reject(error);
        },
      );
    });
  }

  async showSecondLayer(): Promise<UsercentricsBannerResult> {
    return new Promise((resolve, reject) => {
      if (!this.isConfigured || !this.usercentrics) {
        reject(new Error('Usercentrics not configured'));
        return;
      }

      this.usercentrics.showSecondLayer(
        (response: any) => {
          const result: UsercentricsBannerResult = {
            consents: this.convertConsents(response.consents),
            userInteraction: String(response.userInteraction),
            controllerId: response.controllerId,
          };
          resolve(result);
        },
        (error: string) => {
          reject(error);
        },
      );
    });
  }

  async getConsents(): Promise<UsercentricsConsentsResult> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    const consents = this.usercentrics.getConsents();
    return { consents: this.convertConsents(consents) };
  }

  async getCMPData(): Promise<UsercentricsCMPData> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return this.usercentrics.getCMPData();
  }

  async getTCFData(): Promise<TCFData> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return this.usercentrics.getTCFData();
  }

  async restoreUserSession(options: { controllerId: string }): Promise<UsercentricsReadyStatus> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return new Promise((resolve, reject) => {
      this.usercentrics.restoreUserSession(
        options.controllerId,
        (status: any) => {
          const result: UsercentricsReadyStatus = {
            shouldCollectConsent: status.shouldCollectConsent,
            consents: this.convertConsents(status.consents),
            usercentricsReady: status.usercentricsReady,
            controllerId: status.controllerId,
          };
          resolve(result);
        },
        (error: string) => {
          reject(error);
        },
      );
    });
  }

  async saveUserSession(): Promise<{ session: string }> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return { session: this.usercentrics.saveUserSession() };
  }

  async getUserSessionData(): Promise<{ session: string }> {
    return this.saveUserSession();
  }

  async acceptAll(): Promise<UsercentricsConsentsResult> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    this.usercentrics.acceptAll();
    return { consents: this.convertConsents(this.usercentrics.getConsents()) };
  }

  async acceptAllForTCF(): Promise<UsercentricsConsentsResult> {
    throw new Error('acceptAllForTCF is not supported on web');
  }

  async denyAll(): Promise<UsercentricsConsentsResult> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    this.usercentrics.denyAll();
    return { consents: this.convertConsents(this.usercentrics.getConsents()) };
  }

  async denyAllForTCF(): Promise<UsercentricsConsentsResult> {
    throw new Error('denyAllForTCF is not supported on web');
  }

  async saveDecisions(options: {
    decisions: UserDecision[];
    consentType?: UsercentricsConsentType;
  }): Promise<UsercentricsConsentsResult> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    const decisions = options.decisions.map((d) => ({ templateId: d.serviceId, status: d.consent }));
    if (typeof this.usercentrics.saveDecisions === 'function') {
      this.usercentrics.saveDecisions(decisions, options.consentType ?? 'explicit');
    }
    return { consents: this.convertConsents(this.usercentrics.getConsents()) };
  }

  async saveDecisionsForTCF(): Promise<UsercentricsConsentsResult> {
    throw new Error('saveDecisionsForTCF is not supported on web');
  }

  async saveOptOutForCCPA(): Promise<UsercentricsConsentsResult> {
    throw new Error('saveOptOutForCCPA is not supported on web');
  }

  async applyConsent(consents: Record<string, UsercentricsConsent>): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    // On web, we can consider "apply" as saving to runtime consumers only; if SDK exposes a method, wire it here.
    // No-op fallback.
    void consents;
  }

  async saveConsent(consents: Record<string, UsercentricsConsent>): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    const decisions = Object.values(consents).map((c) => ({ templateId: c.templateId, status: c.status }));
    if (typeof this.usercentrics.saveDecisions === 'function') {
      this.usercentrics.saveDecisions(decisions, 'explicit');
    }
  }

  async getControllerId(): Promise<{ controllerId: string }> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    const controllerId = this.usercentrics.getControllerId();
    return { controllerId };
  }

  async clearUserSession(): Promise<UsercentricsReadyStatus> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return new Promise((resolve, reject) => {
      this.usercentrics.clearUserSession(
        (status: any) => {
          const result: UsercentricsReadyStatus = {
            shouldCollectConsent: status.shouldCollectConsent,
            consents: this.convertConsents(status.consents),
            usercentricsReady: status.usercentricsReady,
            controllerId: status.controllerId,
          };
          resolve(result);
        },
        (error: string) => {
          reject(error);
        },
      );
    });
  }

  async changeLanguage(options: { language: string }): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return new Promise((resolve, reject) => {
      this.usercentrics.changeLanguage(
        options.language,
        () => {
          resolve();
        },
        (error: string) => {
          reject(error);
        },
      );
    });
  }

  async setCMPId(options: { id: number }): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    this.usercentrics.setCMPId(options.id);
  }

  async setABTestingVariant(options: { variant: string }): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    this.usercentrics.setABTestingVariant(options.variant);
  }

  async getABTestingVariant(): Promise<{ variant: string | null }> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    const variant = this.usercentrics.getABTestingVariant();
    return { variant: variant || null };
  }

  async getCCPAData(): Promise<CCPAData> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    const uspData = this.usercentrics.getUSPData();
    return {
      version: uspData.version,
      uspString: uspData.uspString,
      optedOut: uspData.optedOut,
      lspact: uspData.lspact,
      noticeGiven: uspData.noticeGiven,
    };
  }

  async getAdditionalConsentModeData(): Promise<AdditionalConsentModeData> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    const acmData = this.usercentrics.getAdditionalConsentModeData();
    return {
      acString: acmData.acString,
      adTechProviders: acmData.adTechProviders || [],
    };
  }

  async getGPPData(): Promise<GppData> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    if (typeof this.usercentrics.getGPPData !== 'function') {
      throw new Error('getGPPData is not supported by the Usercentrics web SDK');
    }
    const gppData = this.usercentrics.getGPPData();
    return {
      gppString: gppData.gppString ?? null,
      applicableSections: gppData.applicableSections || [],
      sections: gppData.sections || {},
    };
  }

  async getGPPString(): Promise<{ gppString: string | null }> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    if (typeof this.usercentrics.getGPPString !== 'function') {
      throw new Error('getGPPString is not supported by the Usercentrics web SDK');
    }
    return { gppString: this.usercentrics.getGPPString() ?? null };
  }

  async setGPPConsent(options: { sectionName: string; fieldName: string; value: unknown }): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    if (typeof this.usercentrics.setGPPConsent !== 'function') {
      throw new Error('setGPPConsent is not supported by the Usercentrics web SDK');
    }
    this.usercentrics.setGPPConsent(options.sectionName, options.fieldName, options.value);
  }

  async getDpsMetadata(options: { templateId: string }): Promise<{ metadata: Record<string, unknown> | null }> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    if (typeof this.usercentrics.getDpsMetadata !== 'function') {
      throw new Error('getDpsMetadata is not supported by the Usercentrics web SDK');
    }
    const metadata = this.usercentrics.getDpsMetadata(options.templateId);
    return { metadata: metadata ?? null };
  }

  async track(options: { event: UsercentricsAnalyticsEventType }): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    this.usercentrics.track(options.event);
  }

  private convertConsents(consents: any[]): UsercentricsConsent[] {
    if (!consents || !Array.isArray(consents)) {
      return [];
    }

    return consents.map((consent) => ({
      templateId: consent.templateId,
      status: consent.isConsentGiven,
      type: consent.type?.toLowerCase() || 'explicit',
      timestamp: consent.timestamp,
      dataProcessor: consent.dataProcessor,
      version: consent.version,
      isEssential: consent.isEssential,
    }));
  }
}

// Extend Window interface to include usercentrics
declare global {
  interface Window {
    usercentrics: any;
  }
}
