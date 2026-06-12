import type { PluginListenerHandle } from '@capacitor/core';

export type UsercentricsConsentType = 'explicit' | 'implicit';
export type TCFDecisionUILayer = 'firstLayer' | 'secondLayer';
export type UsercentricsNetworkMode = 'world' | 'eu';

export interface UsercentricsOptions {
  settingsId: string;
  defaultLanguage?: string;
  version?: string;
  timeoutMillis?: number;
  loggerLevel?: 'debug' | 'warning' | 'error' | 'none';
  rulesetId?: string;
  consentMediation?: boolean;
  networkMode?: UsercentricsNetworkMode;
  initTimeoutMillis?: number;
}

export interface UserDecision {
  serviceId: string;
  consent: boolean;
}

export interface TCFUserDecisionOnPurpose {
  id: number;
  consent?: boolean;
  legitimateInterestConsent?: boolean;
}

export interface TCFUserDecisionOnSpecialFeature {
  id: number;
  consent?: boolean;
}

export interface TCFUserDecisionOnVendor {
  id: number;
  consent?: boolean;
  legitimateInterestConsent?: boolean;
}

export interface AdTechProviderDecision {
  id: number;
  consent: boolean;
}

export interface TCFUserDecisions {
  purposes?: TCFUserDecisionOnPurpose[];
  specialFeatures?: TCFUserDecisionOnSpecialFeature[];
  vendors?: TCFUserDecisionOnVendor[];
  adTechProviders?: AdTechProviderDecision[];
}

export type SectionAlignment = 'START' | 'END' | 'CENTER';
export type UsercentricsLayout = 'FULL' | 'SHEET' | 'POPUP_CENTER' | 'POPUP_BOTTOM';
export type LegalLinksSettings = 'FIRST_LAYER_ONLY' | 'SECOND_LAYER_ONLY' | 'BOTH' | 'HIDDEN';
export type ButtonType = 'ACCEPT_ALL' | 'DENY_ALL' | 'MORE' | 'SAVE';
export type ButtonLayoutType = 'ROW' | 'COLUMN' | 'GRID';

export interface BannerLogo {
  logoName?: string;
  /** Image source: an https URL, a `data:` base64 URI, or an absolute file path. */
  logoPath: string;
  logoUrl?: string;
}

export interface BannerFont {
  regularFont: string;
  boldFont: string;
  fontSize: number;
}

export interface ToggleStyleSettings {
  activeBackgroundColorHex?: string;
  inactiveBackgroundColorHex?: string;
  disabledBackgroundColorHex?: string;
  activeThumbColorHex?: string;
  inactiveThumbColorHex?: string;
  disabledThumbColorHex?: string;
}

export interface ButtonSettings {
  buttonType: ButtonType;
  fontName?: string;
  textSize?: number;
  textColorHex?: string;
  backgroundColorHex?: string;
  cornerRadius?: number;
  /** Affects only Android. */
  isAllCaps?: boolean;
}

export interface ButtonLayout {
  buttons: ButtonSettings[][];
  layout: ButtonLayoutType;
}

export interface TitleSettings {
  fontName?: string;
  textSize?: number;
  textColorHex?: string;
  textAlignment?: SectionAlignment;
}

export interface MessageSettings {
  fontName?: string;
  textSize?: number;
  textColorHex?: string;
  textAlignment?: SectionAlignment;
  linkTextColorHex?: string;
  linkTextUnderline?: boolean;
}

export interface HeaderImageSettings {
  isExtended?: boolean;
  isHidden?: boolean;
  image?: BannerLogo;
  height?: number;
  alignment?: SectionAlignment;
}

export interface GeneralStyleSettings {
  font?: BannerFont;
  logo?: BannerLogo;
  links?: LegalLinksSettings;
  textColorHex?: string;
  layerBackgroundColorHex?: string;
  layerBackgroundSecondaryColorHex?: string;
  linkColorHex?: string;
  tabColorHex?: string;
  bordersColorHex?: string;
  toggleStyleSettings?: ToggleStyleSettings;
  disableSystemBackButton?: boolean;
}

export interface FirstLayerStyleSettings {
  layout?: UsercentricsLayout;
  headerImage?: HeaderImageSettings;
  title?: TitleSettings;
  message?: MessageSettings;
  buttonLayout?: ButtonLayout;
  backgroundColorHex?: string;
  cornerRadius?: number;
  overlayColorHex?: string;
}

export interface SecondLayerStyleSettings {
  buttonLayout?: ButtonLayout;
  showCloseButton?: boolean;
}

export interface BannerSettings {
  generalStyleSettings?: GeneralStyleSettings;
  firstLayerStyleSettings?: FirstLayerStyleSettings;
  secondLayerStyleSettings?: SecondLayerStyleSettings;
  variantName?: string;
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

export interface GppData {
  gppString: string | null;
  applicableSections: number[];
  sections: Record<string, Record<string, unknown>>;
}

export interface GppSectionChangePayload {
  data: string;
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

/** The active CMP variant, serialized as the native enum ordinal. */
export enum UsercentricsVariant {
  DEFAULT = 0,
  CCPA = 1,
  TCF = 2,
}

/** Display format for data processing services, serialized as the native enum ordinal. */
export enum DpsDisplayFormat {
  ALL = 0,
  SHORT = 1,
}

/** USA privacy framework, serialized as the native enum ordinal. */
export enum USAFrameworks {
  CPRA = 0,
  VCDPA = 1,
  CPA = 2,
  CTDPA = 3,
  UCPA = 4,
}

/** Platform of a published app, serialized as the native enum ordinal. */
export enum PublishedAppPlatform {
  ANDROID = 0,
  IOS = 1,
}

/** First layer mobile variant, serialized as the native enum ordinal. */
export enum FirstLayerMobileVariant {
  SHEET = 0,
  FULL = 1,
  POPUP_BOTTOM = 2,
  POPUP_CENTER = 3,
}

/** TCF scope, serialized as the native enum ordinal. */
export enum TCF2Scope {
  GLOBAL = 0,
  SERVICE = 1,
}

/** Type of a consent disclosure, serialized as the native enum ordinal. */
export enum ConsentDisclosureType {
  COOKIE = 0,
  WEB = 1,
  APP = 2,
}

export interface ConsentDisclosure {
  identifier?: string;
  type?: ConsentDisclosureType;
  name?: string;
  maxAgeSeconds?: number;
  cookieRefresh: boolean;
  purposes: number[];
  domain?: string;
  description?: string;
}

export interface ConsentDisclosureSDK {
  name: string;
  use: string;
}

export interface ConsentDisclosureObject {
  disclosures: ConsentDisclosure[];
  sdks: ConsentDisclosureSDK[];
}

export interface UsercentricsLabels {
  btnAcceptAll: string;
  btnDeny: string;
  btnSave: string;
  firstLayerTitle: string;
  accepted: string;
  denied: string;
  date: string;
  decision: string;
  dataCollectedList: string;
  dataCollectedInfo: string;
  locationOfProcessing: string;
  transferToThirdCountries: string;
  transferToThirdCountriesInfo: string;
  dataPurposes: string;
  dataPurposesInfo: string;
  dataRecipientsList: string;
  descriptionOfService: string;
  history: string;
  historyDescription: string;
  legalBasisList: string;
  legalBasisInfo: string;
  processingCompanyTitle: string;
  retentionPeriod: string;
  technologiesUsed: string;
  technologiesUsedInfo: string;
  cookiePolicyInfo: string;
  optOut: string;
  policyOf: string;
  imprintLinkText: string;
  privacyPolicyLinkText: string;
  categories: string;
  anyDomain: string;
  day: string;
  days: string;
  domain: string;
  duration: string;
  informationLoadingNotPossible: string;
  hour: string;
  hours: string;
  identifier: string;
  maximumAgeCookieStorage: string;
  minute: string;
  minutes: string;
  month: string;
  months: string;
  multipleDomains: string;
  no: string;
  nonCookieStorage: string;
  seconds: string;
  session: string;
  loadingStorageInformation: string;
  storageInformation: string;
  detailedStorageInformation: string;
  tryAgain: string;
  type: string;
  year: string;
  years: string;
  yes: string;
  storageInformationDescription: string;
  btnBannerReadMore: string;
  readLess: string;
  btnMore: string;
  more: string;
  linkToDpaInfo: string;
  second: string;
  consent: string;
  headerModal: string;
  secondLayerDescriptionHtml: string;
  secondLayerTitle: string;
  settings: string;
  subConsents: string;
  btnAccept: string;
  poweredBy: string;
  dataProtectionOfficer: string;
  nameOfProcessingCompany: string;
  btnBack: string;
  copy: string;
  copied: string;
  basic: string;
  advanced: string;
  processingCompany: string;
  name: string;
  explicit: string;
  implicit: string;
  btnMoreInfo: string;
  furtherInformationOptOut: string;
  cookiePolicyLinkText: string;
  noImplicit: string;
  yesImplicit: string;
}

export interface CCPASettings {
  optOutNoticeLabel: string;
  btnSave: string;
  firstLayerTitle: string;
  isActive: boolean;
  showOnPageLoad: boolean;
  reshowAfterDays: number;
  iabAgreementExists: boolean;
  appFirstLayerDescription?: string;
  firstLayerMobileDescriptionIsActive: boolean;
  firstLayerMobileDescription?: string;
  secondLayerTitle?: string;
  secondLayerDescription?: string;
  secondLayerHideLanguageSwitch: boolean;
  btnMoreInfo?: string;
  mspaCoveredTransaction: boolean;
  /** Serialized as the native MspaMode enum ordinal. */
  mspaMode?: number;
}

export interface TCF2ChangedPurposes {
  purposes: number[];
  legIntPurposes: number[];
}

export interface TCF2ConsentOrPaySettings {
  enableConsentOrPay: boolean;
  showTogglesForVendors: boolean;
  /** Maps TCF Purpose ID (as string) to "flexible". Absent entries are mandatory. */
  publisherRestrictions: Record<string, string>;
  /** Maps Special Feature ID (as string) to "flexible". Absent entries are mandatory. */
  specialFeatures: Record<string, string>;
}

export interface TCF2Settings {
  firstLayerTitle: string;
  secondLayerTitle: string;
  tabsPurposeLabel: string;
  tabsVendorsLabel: string;
  labelsFeatures: string;
  labelsIabVendors: string;
  labelsNonIabPurposes: string;
  labelsNonIabVendors: string;
  labelsPurposes: string;
  vendorFeatures: string;
  vendorLegitimateInterestPurposes: string;
  vendorPurpose: string;
  vendorSpecialFeatures: string;
  vendorSpecialPurposes: string;
  togglesConsentToggleLabel: string;
  togglesLegIntToggleLabel: string;
  buttonsAcceptAllLabel: string;
  buttonsDenyAllLabel: string;
  buttonsSaveLabel: string;
  linksManageSettingsLabel: string;
  linksVendorListLinkLabel: string;
  cmpId: number;
  cmpVersion: number;
  firstLayerHideToggles: boolean;
  secondLayerHideToggles: boolean;
  hideLegitimateInterestToggles: boolean;
  firstLayerHideButtonDeny?: boolean;
  secondLayerHideButtonDeny: boolean;
  publisherCountryCode: string;
  purposeOneTreatment: boolean;
  selectedVendorIds: number[];
  gdprApplies: boolean;
  selectedStacks: number[];
  disabledSpecialFeatures: number[];
  firstLayerShowDescriptions: boolean;
  hideNonIabOnFirstLayer: boolean;
  resurfacePeriod: number;
  resurfacePurposeChanged: boolean;
  resurfaceVendorAdded: boolean;
  firstLayerDescription?: string;
  firstLayerAdditionalInfo?: string;
  secondLayerDescription?: string;
  togglesSpecialFeaturesToggleOn?: string;
  togglesSpecialFeaturesToggleOff?: string;
  appLayerNoteResurface?: string;
  firstLayerNoteResurface?: string;
  categoriesOfDataLabel: string;
  dataRetentionPeriodLabel: string;
  legitimateInterestLabel: string;
  version: string;
  examplesLabel: string;
  firstLayerMobileVariant?: FirstLayerMobileVariant;
  showDataSharedOutsideEUText: boolean;
  dataSharedOutsideEUText?: string;
  vendorIdsOutsideEUList: number[];
  scope: TCF2Scope;
  changedPurposes?: TCF2ChangedPurposes;
  acmV2Enabled: boolean;
  selectedATPIds: number[];
  consentOrPay?: TCF2ConsentOrPaySettings;
  mandatoryLabel: string;
}

export interface CustomizationFont {
  family?: string;
  size?: number;
}

export interface CustomizationColor {
  primary?: string;
  acceptBtnText?: string;
  acceptBtnBackground?: string;
  denyBtnText?: string;
  denyBtnBackground?: string;
  saveBtnText?: string;
  saveBtnBackground?: string;
  linkIcon?: string;
  linkFont?: string;
  text?: string;
  layerBackground?: string;
  overlay?: string;
  toggleInactiveBackground?: string;
  toggleInactiveIcon?: string;
  toggleActiveBackground?: string;
  toggleActiveIcon?: string;
  toggleDisabledBackground?: string;
  toggleDisabledIcon?: string;
  secondLayerTab?: string;
  moreBtnBackground?: string;
  moreBtnText?: string;
}

export interface UsercentricsCustomization {
  color?: CustomizationColor;
  font?: CustomizationFont;
  logoUrl?: string;
  borderRadiusLayer?: number;
  borderRadiusButton?: number;
  overlayOpacity?: number;
}

export interface FirstLayer {
  hideButtonDeny?: boolean;
}

export interface SecondLayer {
  tabsCategoriesLabel: string;
  tabsServicesLabel: string;
  acceptButtonText: string;
  denyButtonText: string;
  hideButtonDeny?: boolean;
  hideLanguageSwitch?: boolean;
  hideTogglesForServices: boolean;
  hideDataProcessingServices: boolean;
}

export interface VariantsSettings {
  enabled: boolean;
  experimentsJson: string;
  activateWith: string;
}

export interface PublishedApp {
  bundleId: string;
  platform: PublishedAppPlatform;
}

export interface UsercentricsSettings {
  labels: UsercentricsLabels;
  showInitialViewForVersionChange: string[];
  reshowBanner?: number;
  displayOnlyForEU: boolean;
  secondLayer: SecondLayer;
  cookiePolicyUrl?: string;
  tcf2?: TCF2Settings;
  ccpa?: CCPASettings;
  privacyPolicyUrl?: string;
  firstLayer?: FirstLayer;
  imprintUrl?: string;
  firstLayerDescriptionHtml?: string;
  bannerMobileDescriptionIsActive: boolean;
  firstLayerMobileDescriptionHtml?: string;
  version: string;
  language: string;
  tcf2Enabled: boolean;
  settingsId: string;
  languagesAvailable: string[];
  enablePoweredBy: boolean;
  editableLanguages: string[];
  customization?: UsercentricsCustomization;
  variants?: VariantsSettings;
  dpsDisplayFormat?: DpsDisplayFormat;
  framework?: USAFrameworks;
  publishedApps?: PublishedApp[];
  renewConsentsTimestamp?: number;
  consentWebhook?: boolean;
  gppSignalingEnabled: boolean;
  gpcSignalHonoured: boolean;
}

export interface UsercentricsService {
  /** The template ID of the service. */
  templateId?: string;
  /** The version of the service. */
  version?: string;
  type?: string;
  isEssential: boolean;
  dataProcessor?: string;
  dataPurposes: string[];
  processingCompany?: string;
  nameOfProcessingCompany: string;
  addressOfProcessingCompany: string;
  descriptionOfService: string;
  languagesAvailable: string[];
  dataCollectedList: string[];
  dataPurposesList: string[];
  dataRecipientsList: string[];
  legalBasisList: string[];
  retentionPeriodList: string[];
  subConsents?: string[];
  language: string;
  linkToDpa: string;
  legalGround: string;
  optOutUrl: string;
  policyOfProcessorUrl: string;
  /** The category slug identifier of the service. */
  categorySlug?: string;
  retentionPeriodDescription: string;
  dataProtectionOfficer: string;
  privacyPolicyURL: string;
  cookiePolicyURL: string;
  locationOfProcessing: string;
  dataCollectedDescription?: string;
  thirdCountryTransfer: string;
  description?: string;
  cookieMaxAgeSeconds?: number;
  usesNonCookieAccess?: boolean;
  deviceStorageDisclosureUrl?: string;
  isDeactivated?: boolean;
  disableLegalBasis?: boolean;
  technologyUsed: string[];
  deviceStorage?: ConsentDisclosureObject;
  isHidden: boolean;
}

export interface UsercentricsCategory {
  /** The category slug identifier. */
  categorySlug: string;
  /** The label of the category, if any. */
  label?: string;
  /** The description of the category, if any. */
  description?: string;
  /** True, if it is an essential category. */
  isEssential: boolean;
}

export interface UsercentricsLocation {
  /** The country code, e.g. 'DE'. */
  countryCode: string;
  /** The region code following the local format, e.g. 'CA'. */
  regionCode: string;
  /** True, if the location is inside the European Union. */
  isInEU: boolean;
  /** True, if the location is inside the United States of America. */
  isInUS: boolean;
  /** True, if the location is inside the state of California. */
  isInCalifornia: boolean;
}

export interface TranslationAriaLabels {
  acceptAllButton?: string;
  ccpaButton?: string;
  ccpaMoreInformation?: string;
  closeButton?: string;
  collapse?: string;
  cookiePolicyButton?: string;
  copyControllerId?: string;
  denyAllButton?: string;
  expand?: string;
  fullscreenButton?: string;
  imprintButton?: string;
  languageSelector?: string;
  privacyButton?: string;
  privacyPolicyButton?: string;
  saveButton?: string;
  serviceInCategoryDetails?: string;
  servicesInCategory?: string;
  tabButton?: string;
  usercentricsCMPButtons?: string;
  usercentricsCMPContent?: string;
  usercentricsCMPHeader?: string;
  usercentricsCMPUI?: string;
  usercentricsCard?: string;
  usercentricsList?: string;
  vendorConsentToggle?: string;
  vendorDetailedStorageInformation?: string;
  vendorLegIntToggle?: string;
}

export interface LegalBasisLocalization {
  labelsAria?: TranslationAriaLabels;
  data?: Record<string, string>;
}

/**
 * Serialized CMP data as returned by the native SDK.
 */
export interface UsercentricsCMPData {
  /** The general settings defined for the settingsId. */
  settings: UsercentricsSettings;
  /** The services defined for the settingsId. */
  services: UsercentricsService[];
  /** The categories defined for the settingsId. */
  categories: UsercentricsCategory[];
  /** The active variant. */
  activeVariant: UsercentricsVariant;
  /** The current user location. */
  userLocation: UsercentricsLocation;
  /** The current legal basis localization. */
  legalBasis: LegalBasisLocalization;
}

export interface TCFFeature {
  purposeDescription: string;
  illustrations: string[];
  id: number;
  name: string;
}

export interface TCFPurpose {
  purposeDescription: string;
  illustrations: string[];
  id: number;
  name: string;
  consent?: boolean;
  isPartOfASelectedStack: boolean;
  legitimateInterestConsent?: boolean;
  showConsentToggle: boolean;
  showLegitimateInterestToggle: boolean;
  stackId?: number;
  numberOfVendors?: number;
}

export interface TCFSpecialFeature {
  purposeDescription: string;
  illustrations: string[];
  id: number;
  name: string;
  consent?: boolean;
  isPartOfASelectedStack: boolean;
  stackId?: number;
  showConsentToggle: boolean;
}

export interface TCFSpecialPurpose {
  purposeDescription: string;
  illustrations: string[];
  id: number;
  name: string;
}

export interface TCFStack {
  description: string;
  id: number;
  name: string;
  purposeIds: number[];
  specialFeatureIds: number[];
}

/** Type of a TCF vendor publisher restriction, serialized as the native enum ordinal. */
export enum RestrictionType {
  NOT_ALLOWED = 0,
  REQUIRE_CONSENT = 1,
  REQUIRE_LI = 2,
}

export interface TCFVendorRestriction {
  purposeId: number;
  restrictionType: RestrictionType;
}

export interface VendorUrl {
  langId?: string;
  privacy?: string;
  legIntClaim?: string;
}

export interface TCFVendor {
  consent?: boolean;
  features: number[];
  flexiblePurposes: number[];
  id: number;
  legitimateInterestConsent?: boolean;
  legitimateInterestPurposes: number[];
  name: string;
  policyUrl: string;
  purposes: number[];
  specialFeatures: number[];
  specialPurposes: number[];
  showConsentToggle: boolean;
  showLegitimateInterestToggle: boolean;
  cookieMaxAgeSeconds?: number;
  usesNonCookieAccess: boolean;
  deviceStorageDisclosureUrl?: string;
  usesCookies: boolean;
  cookieRefresh?: boolean;
  dataSharedOutsideEU?: boolean;
  dataCategories: number[];
  vendorUrls: VendorUrl[];
  restrictions: TCFVendorRestriction[];
}

/**
 * Serialized TCF data as returned by the native SDK.
 */
export interface TCFData {
  /** All TCF features that need to be disclosed to the end-user if TCF is enabled. */
  features: TCFFeature[];
  /**
   * All TCF purposes that need to be disclosed to the end-user if TCF is enabled.
   * Purposes that are part of a selected stack are flagged with
   * isPartOfASelectedStack = true and include a non-null stackId.
   */
  purposes: TCFPurpose[];
  /** All TCF special features that need to be disclosed to the end-user if TCF is enabled. */
  specialFeatures: TCFSpecialFeature[];
  /** All TCF special purposes that need to be disclosed to the end-user if TCF is enabled. */
  specialPurposes: TCFSpecialPurpose[];
  /** All TCF stacks that need to be disclosed to the end-user if TCF is enabled. */
  stacks: TCFStack[];
  /** All TCF vendors that need to be disclosed to the end-user if TCF is enabled. */
  vendors: TCFVendor[];
  /** The TC string. */
  tcString: string;
  /** The total of vendors and services. */
  thirdPartyCount: number;
}

export interface UsercentricsConsentsResult {
  consents: UsercentricsConsent[];
}

export interface CapacitorUsercentricsPlugin {
  configure(options: UsercentricsOptions): Promise<void>;
  isReady(): Promise<UsercentricsReadyStatus>;
  showBanner(settings?: BannerSettings): Promise<UsercentricsBannerResult>;
  showSecondLayer(settings?: BannerSettings): Promise<UsercentricsBannerResult>;
  getConsents(): Promise<UsercentricsConsentsResult>;
  getCMPData(): Promise<UsercentricsCMPData>;
  getTCFData(): Promise<TCFData>;
  acceptAll(options?: { consentType?: UsercentricsConsentType }): Promise<UsercentricsConsentsResult>;
  acceptAllForTCF(options: {
    fromLayer: TCFDecisionUILayer;
    consentType?: UsercentricsConsentType;
  }): Promise<UsercentricsConsentsResult>;
  denyAll(options?: { consentType?: UsercentricsConsentType }): Promise<UsercentricsConsentsResult>;
  denyAllForTCF(options: {
    fromLayer: TCFDecisionUILayer;
    consentType?: UsercentricsConsentType;
    unsavedPurposeLIDecisions?: TCFUserDecisionOnPurpose[];
    unsavedVendorLIDecisions?: TCFUserDecisionOnVendor[];
  }): Promise<UsercentricsConsentsResult>;
  saveDecisions(options: {
    decisions: UserDecision[];
    consentType?: UsercentricsConsentType;
  }): Promise<UsercentricsConsentsResult>;
  saveDecisionsForTCF(options: {
    tcfDecisions: TCFUserDecisions;
    fromLayer: TCFDecisionUILayer;
    decisions?: UserDecision[];
    consentType?: UsercentricsConsentType;
  }): Promise<UsercentricsConsentsResult>;
  saveOptOutForCCPA(options: {
    isOptedOut: boolean;
    consentType?: UsercentricsConsentType;
  }): Promise<UsercentricsConsentsResult>;
  applyConsent(consents: Record<string, UsercentricsConsent>): Promise<void>;
  /** @deprecated Use {@link saveDecisions} instead. */
  saveConsent(consents: Record<string, UsercentricsConsent>): Promise<void>;
  restoreUserSession(options: { controllerId: string }): Promise<UsercentricsReadyStatus>;
  getUserSessionData(): Promise<{ session: string }>;
  /** @deprecated Use {@link getUserSessionData} instead. */
  saveUserSession(): Promise<{ session: string }>;
  getControllerId(): Promise<{ controllerId: string }>;
  clearUserSession(): Promise<UsercentricsReadyStatus>;
  changeLanguage(options: { language: string }): Promise<void>;
  setCMPId(options: { id: number }): Promise<void>;
  setABTestingVariant(options: { variant: string }): Promise<void>;
  getABTestingVariant(): Promise<{ variant: string | null }>;
  getCCPAData(): Promise<CCPAData>;
  getAdditionalConsentModeData(): Promise<AdditionalConsentModeData>;
  getGPPData(): Promise<GppData>;
  getGPPString(): Promise<{ gppString: string | null }>;
  setGPPConsent(options: { sectionName: string; fieldName: string; value: unknown }): Promise<void>;
  getDpsMetadata(options: { templateId: string }): Promise<{ metadata: Record<string, unknown> | null }>;
  track(options: { event: UsercentricsAnalyticsEventType }): Promise<void>;
  addListener(
    eventName: 'onGppSectionChange',
    listenerFunc: (payload: GppSectionChangePayload) => void,
  ): Promise<PluginListenerHandle>;
  removeAllListeners(): Promise<void>;
}
