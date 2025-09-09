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
  type?: 'explicit' | 'implicit' | string;
  timestamp?: number;
  dataProcessor: string;
  version: string;
  isEssential?: boolean;
}

export interface UsercentricsReadyStatus {
  shouldCollectConsent: boolean;
  consents: UsercentricsConsent[];
  usercentricsReady?: boolean;
  controllerId?: string;
}

export interface UsercentricsBannerResult {
  consents: UsercentricsConsent[];
  userInteraction: string;
  controllerId?: string;
}

export interface CapacitorUsercentricsPlugin {
  configure(options: UsercentricsOptions): Promise<void>;
  isReady(): Promise<UsercentricsReadyStatus>;
  showBanner(): Promise<UsercentricsBannerResult>;
  showSecondLayer(): Promise<UsercentricsBannerResult>;
  reset(): Promise<void>;
  getConsents(): Promise<UsercentricsConsent[]>;
  getCMPData(): Promise<any>;
  getTCFData(): Promise<any>;
  acceptAll(): Promise<void>;
  denyAll(): Promise<void>;
  applyConsent(consents: Record<string, UsercentricsConsent>): Promise<void>;
  saveConsent(consents: Record<string, UsercentricsConsent>): Promise<void>;
  restoreUserSession(userSession: string): Promise<void>;
  saveUserSession(): Promise<{ session: string }>;
}
