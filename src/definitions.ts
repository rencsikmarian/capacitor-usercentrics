export interface UsercentricsOptions {
  settingsId: string;
  defaultLanguage?: string;
  version?: string;
  timeoutMillis?: number;
  loggerLevel?: 'debug' | 'warning' | 'error' | 'none';
  rulesetId?: string;
  consentMediation?: boolean;
}

export interface UsercentricsConsent {
  templateId: string;
  status: boolean;
  type: 'explicit' | 'implicit';
  timestamp: number;
  dataProcessor: string;
  version: string;
  isEssential: boolean;
}

export interface UsercentricsReadyStatus {
  shouldCollectConsent: boolean;
  consents: UsercentricsConsent[];
  usercentricsReady: boolean;
  controllerId: string;
}

export interface UsercentricsBannerResult {
  consents: UsercentricsConsent[];
  userInteraction: boolean;
}

export interface CapacitorUsercentricsPlugin {
  configure(options: UsercentricsOptions): Promise<void>;
  isReady(): Promise<UsercentricsReadyStatus>;
  showBanner(): Promise<UsercentricsBannerResult>;
  reset(): Promise<void>;
  getConsents(): Promise<UsercentricsConsent[]>;
  getCMPData(): Promise<any>;
  restoreUserSession(userSession: string): Promise<void>;
  saveUserSession(): Promise<string>;
}
