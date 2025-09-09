import { WebPlugin } from '@capacitor/core';

import type { CapacitorUsercentricsPlugin, UsercentricsOptions, UsercentricsReadyStatus, UsercentricsBannerResult, UsercentricsConsent } from './definitions';

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
        consentMediation: options.consentMediation
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

      this.usercentrics.isReady((status: any) => {
        const result: UsercentricsReadyStatus = {
          shouldCollectConsent: status.shouldCollectConsent,
          consents: this.convertConsents(status.consents),
          usercentricsReady: status.usercentricsReady,
          controllerId: status.controllerId
        };
        resolve(result);
      }, (error: string) => {
        reject(error);
      });
    });
  }

  async showBanner(): Promise<UsercentricsBannerResult> {
    return new Promise((resolve, reject) => {
      if (!this.isConfigured || !this.usercentrics) {
        reject(new Error('Usercentrics not configured'));
        return;
      }

      this.usercentrics.showFirstLayer((response: any) => {
        const result: UsercentricsBannerResult = {
          consents: this.convertConsents(response.consents),
          userInteraction: String(response.userInteraction),
          controllerId: response.controllerId
        };
        resolve(result);
      }, (error: string) => {
        reject(error);
      });
    });
  }

  async showSecondLayer(): Promise<UsercentricsBannerResult> {
    return new Promise((resolve, reject) => {
      if (!this.isConfigured || !this.usercentrics) {
        reject(new Error('Usercentrics not configured'));
        return;
      }

      this.usercentrics.showSecondLayer((response: any) => {
        const result: UsercentricsBannerResult = {
          consents: this.convertConsents(response.consents),
          userInteraction: String(response.userInteraction),
          controllerId: response.controllerId
        };
        resolve(result);
      }, (error: string) => {
        reject(error);
      });
    });
  }

  async reset(): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    this.usercentrics.reset();
  }

  async getConsents(): Promise<UsercentricsConsent[]> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    const consents = this.usercentrics.getConsents();
    return this.convertConsents(consents);
  }

  async getCMPData(): Promise<any> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return this.usercentrics.getCMPData();
  }

  async getTCFData(): Promise<any> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return this.usercentrics.getTCFData();
  }

  async restoreUserSession(userSession: string): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    this.usercentrics.restoreUserSession(userSession);
  }

  async saveUserSession(): Promise<{ session: string }> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }

    return { session: this.usercentrics.saveUserSession() };
  }

  async acceptAll(): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    this.usercentrics.acceptAll();
  }

  async denyAll(): Promise<void> {
    if (!this.isConfigured || !this.usercentrics) {
      throw new Error('Usercentrics not configured');
    }
    this.usercentrics.denyAll();
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
    const decisions = Object.values(consents).map(c => ({ templateId: c.templateId, status: c.status }));
    if (typeof this.usercentrics.saveDecisions === 'function') {
      this.usercentrics.saveDecisions(decisions, 'explicit');
    }
  }

  private convertConsents(consents: any[]): UsercentricsConsent[] {
    if (!consents || !Array.isArray(consents)) {
      return [];
    }

    return consents.map(consent => ({
      templateId: consent.templateId,
      status: consent.isConsentGiven,
      type: consent.type?.toLowerCase() || 'explicit',
      timestamp: consent.timestamp,
      dataProcessor: consent.dataProcessor,
      version: consent.version,
      isEssential: consent.isEssential
    }));
  }
}

// Extend Window interface to include usercentrics
declare global {
  interface Window {
    usercentrics: any;
  }
}
