export interface UsercentricsOptions {
  settingsId: string;
  defaultLanguage?: string;
  version?: string;
  timeoutMillis?: number;
  loggerLevel?: 'debug' | 'warning' | 'error' | 'none';
  rulesetId?: string;
  consentMediation?: boolean;
}

export interface CCPAData {
  version: number;
  uspString: string;
  optedOut?: boolean;
  lspact?: boolean;
  noticeGiven?: boolean;
}

export interface AdTechProvider {
  id: number;
  name: string;
  privacyPolicyUrl: string;
  consent: boolean;
}

export interface AdditionalConsentModeData {
  acString: string;
  adTechProviders: AdTechProvider[];
}

export enum UsercentricsAnalyticsEventType {
  CMP_SHOWN = 0,
  ACCEPT_ALL_FIRST_LAYER = 1,
  DENY_ALL_FIRST_LAYER = 2,
  SAVE_FIRST_LAYER = 3,
  ACCEPT_ALL_SECOND_LAYER = 4,
  DENY_ALL_SECOND_LAYER = 5,
  SAVE_SECOND_LAYER = 6,
  IMPRINT_LINK = 7,
  MORE_INFORMATION_LINK = 8,
  PRIVACY_POLICY_LINK = 9,
  CCPA_TOGGLES_ON = 10,
  CCPA_TOGGLES_OFF = 11,
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
  getConsents(): Promise<UsercentricsConsent[]>;
  getCMPData(): Promise<any>;
  getTCFData(): Promise<any>;
  acceptAll(): Promise<void>;
  denyAll(): Promise<void>;
  applyConsent(consents: Record<string, UsercentricsConsent>): Promise<void>;
  saveConsent(consents: Record<string, UsercentricsConsent>): Promise<void>;
  restoreUserSession(options: { controllerId: string }): Promise<UsercentricsReadyStatus>;
  saveUserSession(): Promise<{ session: string }>;
  getControllerId(): Promise<{ controllerId: string }>;
  clearUserSession(): Promise<UsercentricsReadyStatus>;
  changeLanguage(options: { language: string }): Promise<void>;
  setCMPId(options: { id: number }): Promise<void>;
  setABTestingVariant(options: { variant: string }): Promise<void>;
  getABTestingVariant(): Promise<{ variant: string | null }>;
  getCCPAData(): Promise<CCPAData>;
  getAdditionalConsentModeData(): Promise<AdditionalConsentModeData>;
  track(options: { event: UsercentricsAnalyticsEventType }): Promise<void>;
}
