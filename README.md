# capacitor-usercentrics

usercentrics capacitor plugin

## Install

```bash
npm install capacitor-usercentrics
npx cap sync
```

## API

<docgen-index>

* [`configure(...)`](#configure)
* [`isReady()`](#isready)
* [`showBanner(...)`](#showbanner)
* [`showSecondLayer(...)`](#showsecondlayer)
* [`getConsents()`](#getconsents)
* [`getCMPData()`](#getcmpdata)
* [`getTCFData()`](#gettcfdata)
* [`acceptAll(...)`](#acceptall)
* [`acceptAllForTCF(...)`](#acceptallfortcf)
* [`denyAll(...)`](#denyall)
* [`denyAllForTCF(...)`](#denyallfortcf)
* [`saveDecisions(...)`](#savedecisions)
* [`saveDecisionsForTCF(...)`](#savedecisionsfortcf)
* [`saveOptOutForCCPA(...)`](#saveoptoutforccpa)
* [`applyConsent(...)`](#applyconsent)
* [`saveConsent(...)`](#saveconsent)
* [`restoreUserSession(...)`](#restoreusersession)
* [`getUserSessionData()`](#getusersessiondata)
* [`saveUserSession()`](#saveusersession)
* [`getControllerId()`](#getcontrollerid)
* [`clearUserSession()`](#clearusersession)
* [`changeLanguage(...)`](#changelanguage)
* [`setCMPId(...)`](#setcmpid)
* [`setABTestingVariant(...)`](#setabtestingvariant)
* [`getABTestingVariant()`](#getabtestingvariant)
* [`getCCPAData()`](#getccpadata)
* [`getAdditionalConsentModeData()`](#getadditionalconsentmodedata)
* [`getGPPData()`](#getgppdata)
* [`getGPPString()`](#getgppstring)
* [`setGPPConsent(...)`](#setgppconsent)
* [`getDpsMetadata(...)`](#getdpsmetadata)
* [`track(...)`](#track)
* [`addListener('onGppSectionChange', ...)`](#addlistenerongppsectionchange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### configure(...)

```typescript
configure(options: UsercentricsOptions) => Promise<void>
```

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#usercentricsoptions">UsercentricsOptions</a></code> |

--------------------


### isReady()

```typescript
isReady() => Promise<UsercentricsReadyStatus>
```

**Returns:** <code>Promise&lt;<a href="#usercentricsreadystatus">UsercentricsReadyStatus</a>&gt;</code>

--------------------


### showBanner(...)

```typescript
showBanner(settings?: BannerSettings | undefined) => Promise<UsercentricsBannerResult>
```

| Param          | Type                                                      |
| -------------- | --------------------------------------------------------- |
| **`settings`** | <code><a href="#bannersettings">BannerSettings</a></code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsbannerresult">UsercentricsBannerResult</a>&gt;</code>

--------------------


### showSecondLayer(...)

```typescript
showSecondLayer(settings?: BannerSettings | undefined) => Promise<UsercentricsBannerResult>
```

| Param          | Type                                                      |
| -------------- | --------------------------------------------------------- |
| **`settings`** | <code><a href="#bannersettings">BannerSettings</a></code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsbannerresult">UsercentricsBannerResult</a>&gt;</code>

--------------------


### getConsents()

```typescript
getConsents() => Promise<UsercentricsConsentsResult>
```

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### getCMPData()

```typescript
getCMPData() => Promise<UsercentricsCMPData>
```

**Returns:** <code>Promise&lt;<a href="#usercentricscmpdata">UsercentricsCMPData</a>&gt;</code>

--------------------


### getTCFData()

```typescript
getTCFData() => Promise<TCFData>
```

**Returns:** <code>Promise&lt;<a href="#tcfdata">TCFData</a>&gt;</code>

--------------------


### acceptAll(...)

```typescript
acceptAll(options?: { consentType?: UsercentricsConsentType | undefined; } | undefined) => Promise<UsercentricsConsentsResult>
```

| Param         | Type                                                                                           |
| ------------- | ---------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ consentType?: <a href="#usercentricsconsenttype">UsercentricsConsentType</a>; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### acceptAllForTCF(...)

```typescript
acceptAllForTCF(options: { fromLayer: TCFDecisionUILayer; consentType?: UsercentricsConsentType; }) => Promise<UsercentricsConsentsResult>
```

| Param         | Type                                                                                                                                                            |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ fromLayer: <a href="#tcfdecisionuilayer">TCFDecisionUILayer</a>; consentType?: <a href="#usercentricsconsenttype">UsercentricsConsentType</a>; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### denyAll(...)

```typescript
denyAll(options?: { consentType?: UsercentricsConsentType | undefined; } | undefined) => Promise<UsercentricsConsentsResult>
```

| Param         | Type                                                                                           |
| ------------- | ---------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ consentType?: <a href="#usercentricsconsenttype">UsercentricsConsentType</a>; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### denyAllForTCF(...)

```typescript
denyAllForTCF(options: { fromLayer: TCFDecisionUILayer; consentType?: UsercentricsConsentType; unsavedPurposeLIDecisions?: TCFUserDecisionOnPurpose[]; unsavedVendorLIDecisions?: TCFUserDecisionOnVendor[]; }) => Promise<UsercentricsConsentsResult>
```

| Param         | Type                                                                                                                                                                                                                                                                          |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ fromLayer: <a href="#tcfdecisionuilayer">TCFDecisionUILayer</a>; consentType?: <a href="#usercentricsconsenttype">UsercentricsConsentType</a>; unsavedPurposeLIDecisions?: TCFUserDecisionOnPurpose[]; unsavedVendorLIDecisions?: TCFUserDecisionOnVendor[]; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### saveDecisions(...)

```typescript
saveDecisions(options: { decisions: UserDecision[]; consentType?: UsercentricsConsentType; }) => Promise<UsercentricsConsentsResult>
```

| Param         | Type                                                                                                                      |
| ------------- | ------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ decisions: UserDecision[]; consentType?: <a href="#usercentricsconsenttype">UsercentricsConsentType</a>; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### saveDecisionsForTCF(...)

```typescript
saveDecisionsForTCF(options: { tcfDecisions: TCFUserDecisions; fromLayer: TCFDecisionUILayer; decisions?: UserDecision[]; consentType?: UsercentricsConsentType; }) => Promise<UsercentricsConsentsResult>
```

| Param         | Type                                                                                                                                                                                                                                                        |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ tcfDecisions: <a href="#tcfuserdecisions">TCFUserDecisions</a>; fromLayer: <a href="#tcfdecisionuilayer">TCFDecisionUILayer</a>; decisions?: UserDecision[]; consentType?: <a href="#usercentricsconsenttype">UsercentricsConsentType</a>; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### saveOptOutForCCPA(...)

```typescript
saveOptOutForCCPA(options: { isOptedOut: boolean; consentType?: UsercentricsConsentType; }) => Promise<UsercentricsConsentsResult>
```

| Param         | Type                                                                                                                |
| ------------- | ------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ isOptedOut: boolean; consentType?: <a href="#usercentricsconsenttype">UsercentricsConsentType</a>; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsconsentsresult">UsercentricsConsentsResult</a>&gt;</code>

--------------------


### applyConsent(...)

```typescript
applyConsent(consents: Record<string, UsercentricsConsent>) => Promise<void>
```

| Param          | Type                                                                                                            |
| -------------- | --------------------------------------------------------------------------------------------------------------- |
| **`consents`** | <code><a href="#record">Record</a>&lt;string, <a href="#usercentricsconsent">UsercentricsConsent</a>&gt;</code> |

--------------------


### saveConsent(...)

```typescript
saveConsent(consents: Record<string, UsercentricsConsent>) => Promise<void>
```

| Param          | Type                                                                                                            |
| -------------- | --------------------------------------------------------------------------------------------------------------- |
| **`consents`** | <code><a href="#record">Record</a>&lt;string, <a href="#usercentricsconsent">UsercentricsConsent</a>&gt;</code> |

--------------------


### restoreUserSession(...)

```typescript
restoreUserSession(options: { controllerId: string; }) => Promise<UsercentricsReadyStatus>
```

| Param         | Type                                   |
| ------------- | -------------------------------------- |
| **`options`** | <code>{ controllerId: string; }</code> |

**Returns:** <code>Promise&lt;<a href="#usercentricsreadystatus">UsercentricsReadyStatus</a>&gt;</code>

--------------------


### getUserSessionData()

```typescript
getUserSessionData() => Promise<{ session: string; }>
```

**Returns:** <code>Promise&lt;{ session: string; }&gt;</code>

--------------------


### saveUserSession()

```typescript
saveUserSession() => Promise<{ session: string; }>
```

**Returns:** <code>Promise&lt;{ session: string; }&gt;</code>

--------------------


### getControllerId()

```typescript
getControllerId() => Promise<{ controllerId: string; }>
```

**Returns:** <code>Promise&lt;{ controllerId: string; }&gt;</code>

--------------------


### clearUserSession()

```typescript
clearUserSession() => Promise<UsercentricsReadyStatus>
```

**Returns:** <code>Promise&lt;<a href="#usercentricsreadystatus">UsercentricsReadyStatus</a>&gt;</code>

--------------------


### changeLanguage(...)

```typescript
changeLanguage(options: { language: string; }) => Promise<void>
```

| Param         | Type                               |
| ------------- | ---------------------------------- |
| **`options`** | <code>{ language: string; }</code> |

--------------------


### setCMPId(...)

```typescript
setCMPId(options: { id: number; }) => Promise<void>
```

| Param         | Type                         |
| ------------- | ---------------------------- |
| **`options`** | <code>{ id: number; }</code> |

--------------------


### setABTestingVariant(...)

```typescript
setABTestingVariant(options: { variant: string; }) => Promise<void>
```

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ variant: string; }</code> |

--------------------


### getABTestingVariant()

```typescript
getABTestingVariant() => Promise<{ variant: string | null; }>
```

**Returns:** <code>Promise&lt;{ variant: string | null; }&gt;</code>

--------------------


### getCCPAData()

```typescript
getCCPAData() => Promise<CCPAData>
```

**Returns:** <code>Promise&lt;<a href="#ccpadata">CCPAData</a>&gt;</code>

--------------------


### getAdditionalConsentModeData()

```typescript
getAdditionalConsentModeData() => Promise<AdditionalConsentModeData>
```

**Returns:** <code>Promise&lt;<a href="#additionalconsentmodedata">AdditionalConsentModeData</a>&gt;</code>

--------------------


### getGPPData()

```typescript
getGPPData() => Promise<GppData>
```

**Returns:** <code>Promise&lt;<a href="#gppdata">GppData</a>&gt;</code>

--------------------


### getGPPString()

```typescript
getGPPString() => Promise<{ gppString: string | null; }>
```

**Returns:** <code>Promise&lt;{ gppString: string | null; }&gt;</code>

--------------------


### setGPPConsent(...)

```typescript
setGPPConsent(options: { sectionName: string; fieldName: string; value: unknown; }) => Promise<void>
```

| Param         | Type                                                                     |
| ------------- | ------------------------------------------------------------------------ |
| **`options`** | <code>{ sectionName: string; fieldName: string; value: unknown; }</code> |

--------------------


### getDpsMetadata(...)

```typescript
getDpsMetadata(options: { templateId: string; }) => Promise<{ metadata: Record<string, unknown> | null; }>
```

| Param         | Type                                 |
| ------------- | ------------------------------------ |
| **`options`** | <code>{ templateId: string; }</code> |

**Returns:** <code>Promise&lt;{ metadata: <a href="#record">Record</a>&lt;string, unknown&gt; | null; }&gt;</code>

--------------------


### track(...)

```typescript
track(options: { event: UsercentricsAnalyticsEventType; }) => Promise<void>
```

| Param         | Type                                                                                                  |
| ------------- | ----------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ event: <a href="#usercentricsanalyticseventtype">UsercentricsAnalyticsEventType</a>; }</code> |

--------------------


### addListener('onGppSectionChange', ...)

```typescript
addListener(eventName: 'onGppSectionChange', listenerFunc: (payload: GppSectionChangePayload) => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                                                                              |
| ------------------ | ------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'onGppSectionChange'</code>                                                                 |
| **`listenerFunc`** | <code>(payload: <a href="#gppsectionchangepayload">GppSectionChangePayload</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

--------------------


### Interfaces


#### UsercentricsOptions

| Prop                    | Type                                                                        |
| ----------------------- | --------------------------------------------------------------------------- |
| **`settingsId`**        | <code>string</code>                                                         |
| **`defaultLanguage`**   | <code>string</code>                                                         |
| **`version`**           | <code>string</code>                                                         |
| **`timeoutMillis`**     | <code>number</code>                                                         |
| **`loggerLevel`**       | <code>'error' \| 'debug' \| 'warning' \| 'none'</code>                      |
| **`rulesetId`**         | <code>string</code>                                                         |
| **`consentMediation`**  | <code>boolean</code>                                                        |
| **`networkMode`**       | <code><a href="#usercentricsnetworkmode">UsercentricsNetworkMode</a></code> |
| **`initTimeoutMillis`** | <code>number</code>                                                         |


#### UsercentricsReadyStatus

| Prop                       | Type                               |
| -------------------------- | ---------------------------------- |
| **`shouldCollectConsent`** | <code>boolean</code>               |
| **`consents`**             | <code>UsercentricsConsent[]</code> |
| **`usercentricsReady`**    | <code>boolean</code>               |
| **`controllerId`**         | <code>string</code>                |


#### UsercentricsConsent

| Prop                | Type                 |
| ------------------- | -------------------- |
| **`templateId`**    | <code>string</code>  |
| **`status`**        | <code>boolean</code> |
| **`type`**          | <code>string</code>  |
| **`timestamp`**     | <code>number</code>  |
| **`dataProcessor`** | <code>string</code>  |
| **`version`**       | <code>string</code>  |
| **`isEssential`**   | <code>boolean</code> |


#### UsercentricsBannerResult

| Prop                  | Type                               |
| --------------------- | ---------------------------------- |
| **`consents`**        | <code>UsercentricsConsent[]</code> |
| **`userInteraction`** | <code>string</code>                |
| **`controllerId`**    | <code>string</code>                |


#### BannerSettings

| Prop                           | Type                                                                          |
| ------------------------------ | ----------------------------------------------------------------------------- |
| **`generalStyleSettings`**     | <code><a href="#generalstylesettings">GeneralStyleSettings</a></code>         |
| **`firstLayerStyleSettings`**  | <code><a href="#firstlayerstylesettings">FirstLayerStyleSettings</a></code>   |
| **`secondLayerStyleSettings`** | <code><a href="#secondlayerstylesettings">SecondLayerStyleSettings</a></code> |
| **`variantName`**              | <code>string</code>                                                           |


#### GeneralStyleSettings

| Prop                                   | Type                                                                |
| -------------------------------------- | ------------------------------------------------------------------- |
| **`font`**                             | <code><a href="#bannerfont">BannerFont</a></code>                   |
| **`logo`**                             | <code><a href="#bannerlogo">BannerLogo</a></code>                   |
| **`links`**                            | <code><a href="#legallinkssettings">LegalLinksSettings</a></code>   |
| **`textColorHex`**                     | <code>string</code>                                                 |
| **`layerBackgroundColorHex`**          | <code>string</code>                                                 |
| **`layerBackgroundSecondaryColorHex`** | <code>string</code>                                                 |
| **`linkColorHex`**                     | <code>string</code>                                                 |
| **`tabColorHex`**                      | <code>string</code>                                                 |
| **`bordersColorHex`**                  | <code>string</code>                                                 |
| **`toggleStyleSettings`**              | <code><a href="#togglestylesettings">ToggleStyleSettings</a></code> |
| **`disableSystemBackButton`**          | <code>boolean</code>                                                |


#### BannerFont

| Prop              | Type                |
| ----------------- | ------------------- |
| **`regularFont`** | <code>string</code> |
| **`boldFont`**    | <code>string</code> |
| **`fontSize`**    | <code>number</code> |


#### BannerLogo

| Prop           | Type                | Description                                                                 |
| -------------- | ------------------- | --------------------------------------------------------------------------- |
| **`logoName`** | <code>string</code> |                                                                             |
| **`logoPath`** | <code>string</code> | Image source: an https URL, a `data:` base64 URI, or an absolute file path. |
| **`logoUrl`**  | <code>string</code> |                                                                             |


#### ToggleStyleSettings

| Prop                             | Type                |
| -------------------------------- | ------------------- |
| **`activeBackgroundColorHex`**   | <code>string</code> |
| **`inactiveBackgroundColorHex`** | <code>string</code> |
| **`disabledBackgroundColorHex`** | <code>string</code> |
| **`activeThumbColorHex`**        | <code>string</code> |
| **`inactiveThumbColorHex`**      | <code>string</code> |
| **`disabledThumbColorHex`**      | <code>string</code> |


#### FirstLayerStyleSettings

| Prop                     | Type                                                                |
| ------------------------ | ------------------------------------------------------------------- |
| **`layout`**             | <code><a href="#usercentricslayout">UsercentricsLayout</a></code>   |
| **`headerImage`**        | <code><a href="#headerimagesettings">HeaderImageSettings</a></code> |
| **`title`**              | <code><a href="#titlesettings">TitleSettings</a></code>             |
| **`message`**            | <code><a href="#messagesettings">MessageSettings</a></code>         |
| **`buttonLayout`**       | <code><a href="#buttonlayout">ButtonLayout</a></code>               |
| **`backgroundColorHex`** | <code>string</code>                                                 |
| **`cornerRadius`**       | <code>number</code>                                                 |
| **`overlayColorHex`**    | <code>string</code>                                                 |


#### HeaderImageSettings

| Prop             | Type                                                          |
| ---------------- | ------------------------------------------------------------- |
| **`isExtended`** | <code>boolean</code>                                          |
| **`isHidden`**   | <code>boolean</code>                                          |
| **`image`**      | <code><a href="#bannerlogo">BannerLogo</a></code>             |
| **`height`**     | <code>number</code>                                           |
| **`alignment`**  | <code><a href="#sectionalignment">SectionAlignment</a></code> |


#### TitleSettings

| Prop                | Type                                                          |
| ------------------- | ------------------------------------------------------------- |
| **`fontName`**      | <code>string</code>                                           |
| **`textSize`**      | <code>number</code>                                           |
| **`textColorHex`**  | <code>string</code>                                           |
| **`textAlignment`** | <code><a href="#sectionalignment">SectionAlignment</a></code> |


#### MessageSettings

| Prop                    | Type                                                          |
| ----------------------- | ------------------------------------------------------------- |
| **`fontName`**          | <code>string</code>                                           |
| **`textSize`**          | <code>number</code>                                           |
| **`textColorHex`**      | <code>string</code>                                           |
| **`textAlignment`**     | <code><a href="#sectionalignment">SectionAlignment</a></code> |
| **`linkTextColorHex`**  | <code>string</code>                                           |
| **`linkTextUnderline`** | <code>boolean</code>                                          |


#### ButtonLayout

| Prop          | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`buttons`** | <code>ButtonSettings[][]</code>                               |
| **`layout`**  | <code><a href="#buttonlayouttype">ButtonLayoutType</a></code> |


#### ButtonSettings

| Prop                     | Type                                              | Description           |
| ------------------------ | ------------------------------------------------- | --------------------- |
| **`buttonType`**         | <code><a href="#buttontype">ButtonType</a></code> |                       |
| **`fontName`**           | <code>string</code>                               |                       |
| **`textSize`**           | <code>number</code>                               |                       |
| **`textColorHex`**       | <code>string</code>                               |                       |
| **`backgroundColorHex`** | <code>string</code>                               |                       |
| **`cornerRadius`**       | <code>number</code>                               |                       |
| **`isAllCaps`**          | <code>boolean</code>                              | Affects only Android. |


#### SecondLayerStyleSettings

| Prop                  | Type                                                  |
| --------------------- | ----------------------------------------------------- |
| **`buttonLayout`**    | <code><a href="#buttonlayout">ButtonLayout</a></code> |
| **`showCloseButton`** | <code>boolean</code>                                  |


#### UsercentricsConsentsResult

| Prop           | Type                               |
| -------------- | ---------------------------------- |
| **`consents`** | <code>UsercentricsConsent[]</code> |


#### UsercentricsCMPData

Serialized CMP data as returned by the native SDK.

| Prop                | Type                                                                      | Description                                      |
| ------------------- | ------------------------------------------------------------------------- | ------------------------------------------------ |
| **`settings`**      | <code><a href="#usercentricssettings">UsercentricsSettings</a></code>     | The general settings defined for the settingsId. |
| **`services`**      | <code>UsercentricsService[]</code>                                        | The services defined for the settingsId.         |
| **`categories`**    | <code>UsercentricsCategory[]</code>                                       | The categories defined for the settingsId.       |
| **`activeVariant`** | <code><a href="#usercentricsvariant">UsercentricsVariant</a></code>       | The active variant.                              |
| **`userLocation`**  | <code><a href="#usercentricslocation">UsercentricsLocation</a></code>     | The current user location.                       |
| **`legalBasis`**    | <code><a href="#legalbasislocalization">LegalBasisLocalization</a></code> | The current legal basis localization.            |


#### UsercentricsSettings

| Prop                                  | Type                                                                            |
| ------------------------------------- | ------------------------------------------------------------------------------- |
| **`labels`**                          | <code><a href="#usercentricslabels">UsercentricsLabels</a></code>               |
| **`showInitialViewForVersionChange`** | <code>string[]</code>                                                           |
| **`reshowBanner`**                    | <code>number</code>                                                             |
| **`displayOnlyForEU`**                | <code>boolean</code>                                                            |
| **`secondLayer`**                     | <code><a href="#secondlayer">SecondLayer</a></code>                             |
| **`cookiePolicyUrl`**                 | <code>string</code>                                                             |
| **`tcf2`**                            | <code><a href="#tcf2settings">TCF2Settings</a></code>                           |
| **`ccpa`**                            | <code><a href="#ccpasettings">CCPASettings</a></code>                           |
| **`privacyPolicyUrl`**                | <code>string</code>                                                             |
| **`firstLayer`**                      | <code><a href="#firstlayer">FirstLayer</a></code>                               |
| **`imprintUrl`**                      | <code>string</code>                                                             |
| **`firstLayerDescriptionHtml`**       | <code>string</code>                                                             |
| **`bannerMobileDescriptionIsActive`** | <code>boolean</code>                                                            |
| **`firstLayerMobileDescriptionHtml`** | <code>string</code>                                                             |
| **`version`**                         | <code>string</code>                                                             |
| **`language`**                        | <code>string</code>                                                             |
| **`tcf2Enabled`**                     | <code>boolean</code>                                                            |
| **`settingsId`**                      | <code>string</code>                                                             |
| **`languagesAvailable`**              | <code>string[]</code>                                                           |
| **`enablePoweredBy`**                 | <code>boolean</code>                                                            |
| **`editableLanguages`**               | <code>string[]</code>                                                           |
| **`customization`**                   | <code><a href="#usercentricscustomization">UsercentricsCustomization</a></code> |
| **`variants`**                        | <code><a href="#variantssettings">VariantsSettings</a></code>                   |
| **`dpsDisplayFormat`**                | <code><a href="#dpsdisplayformat">DpsDisplayFormat</a></code>                   |
| **`framework`**                       | <code><a href="#usaframeworks">USAFrameworks</a></code>                         |
| **`publishedApps`**                   | <code>PublishedApp[]</code>                                                     |
| **`renewConsentsTimestamp`**          | <code>number</code>                                                             |
| **`consentWebhook`**                  | <code>boolean</code>                                                            |
| **`gppSignalingEnabled`**             | <code>boolean</code>                                                            |
| **`gpcSignalHonoured`**               | <code>boolean</code>                                                            |


#### UsercentricsLabels

| Prop                                | Type                |
| ----------------------------------- | ------------------- |
| **`btnAcceptAll`**                  | <code>string</code> |
| **`btnDeny`**                       | <code>string</code> |
| **`btnSave`**                       | <code>string</code> |
| **`firstLayerTitle`**               | <code>string</code> |
| **`accepted`**                      | <code>string</code> |
| **`denied`**                        | <code>string</code> |
| **`date`**                          | <code>string</code> |
| **`decision`**                      | <code>string</code> |
| **`dataCollectedList`**             | <code>string</code> |
| **`dataCollectedInfo`**             | <code>string</code> |
| **`locationOfProcessing`**          | <code>string</code> |
| **`transferToThirdCountries`**      | <code>string</code> |
| **`transferToThirdCountriesInfo`**  | <code>string</code> |
| **`dataPurposes`**                  | <code>string</code> |
| **`dataPurposesInfo`**              | <code>string</code> |
| **`dataRecipientsList`**            | <code>string</code> |
| **`descriptionOfService`**          | <code>string</code> |
| **`history`**                       | <code>string</code> |
| **`historyDescription`**            | <code>string</code> |
| **`legalBasisList`**                | <code>string</code> |
| **`legalBasisInfo`**                | <code>string</code> |
| **`processingCompanyTitle`**        | <code>string</code> |
| **`retentionPeriod`**               | <code>string</code> |
| **`technologiesUsed`**              | <code>string</code> |
| **`technologiesUsedInfo`**          | <code>string</code> |
| **`cookiePolicyInfo`**              | <code>string</code> |
| **`optOut`**                        | <code>string</code> |
| **`policyOf`**                      | <code>string</code> |
| **`imprintLinkText`**               | <code>string</code> |
| **`privacyPolicyLinkText`**         | <code>string</code> |
| **`categories`**                    | <code>string</code> |
| **`anyDomain`**                     | <code>string</code> |
| **`day`**                           | <code>string</code> |
| **`days`**                          | <code>string</code> |
| **`domain`**                        | <code>string</code> |
| **`duration`**                      | <code>string</code> |
| **`informationLoadingNotPossible`** | <code>string</code> |
| **`hour`**                          | <code>string</code> |
| **`hours`**                         | <code>string</code> |
| **`identifier`**                    | <code>string</code> |
| **`maximumAgeCookieStorage`**       | <code>string</code> |
| **`minute`**                        | <code>string</code> |
| **`minutes`**                       | <code>string</code> |
| **`month`**                         | <code>string</code> |
| **`months`**                        | <code>string</code> |
| **`multipleDomains`**               | <code>string</code> |
| **`no`**                            | <code>string</code> |
| **`nonCookieStorage`**              | <code>string</code> |
| **`seconds`**                       | <code>string</code> |
| **`session`**                       | <code>string</code> |
| **`loadingStorageInformation`**     | <code>string</code> |
| **`storageInformation`**            | <code>string</code> |
| **`detailedStorageInformation`**    | <code>string</code> |
| **`tryAgain`**                      | <code>string</code> |
| **`type`**                          | <code>string</code> |
| **`year`**                          | <code>string</code> |
| **`years`**                         | <code>string</code> |
| **`yes`**                           | <code>string</code> |
| **`storageInformationDescription`** | <code>string</code> |
| **`btnBannerReadMore`**             | <code>string</code> |
| **`readLess`**                      | <code>string</code> |
| **`btnMore`**                       | <code>string</code> |
| **`more`**                          | <code>string</code> |
| **`linkToDpaInfo`**                 | <code>string</code> |
| **`second`**                        | <code>string</code> |
| **`consent`**                       | <code>string</code> |
| **`headerModal`**                   | <code>string</code> |
| **`secondLayerDescriptionHtml`**    | <code>string</code> |
| **`secondLayerTitle`**              | <code>string</code> |
| **`settings`**                      | <code>string</code> |
| **`subConsents`**                   | <code>string</code> |
| **`btnAccept`**                     | <code>string</code> |
| **`poweredBy`**                     | <code>string</code> |
| **`dataProtectionOfficer`**         | <code>string</code> |
| **`nameOfProcessingCompany`**       | <code>string</code> |
| **`btnBack`**                       | <code>string</code> |
| **`copy`**                          | <code>string</code> |
| **`copied`**                        | <code>string</code> |
| **`basic`**                         | <code>string</code> |
| **`advanced`**                      | <code>string</code> |
| **`processingCompany`**             | <code>string</code> |
| **`name`**                          | <code>string</code> |
| **`explicit`**                      | <code>string</code> |
| **`implicit`**                      | <code>string</code> |
| **`btnMoreInfo`**                   | <code>string</code> |
| **`furtherInformationOptOut`**      | <code>string</code> |
| **`cookiePolicyLinkText`**          | <code>string</code> |
| **`noImplicit`**                    | <code>string</code> |
| **`yesImplicit`**                   | <code>string</code> |


#### SecondLayer

| Prop                             | Type                 |
| -------------------------------- | -------------------- |
| **`tabsCategoriesLabel`**        | <code>string</code>  |
| **`tabsServicesLabel`**          | <code>string</code>  |
| **`acceptButtonText`**           | <code>string</code>  |
| **`denyButtonText`**             | <code>string</code>  |
| **`hideButtonDeny`**             | <code>boolean</code> |
| **`hideLanguageSwitch`**         | <code>boolean</code> |
| **`hideTogglesForServices`**     | <code>boolean</code> |
| **`hideDataProcessingServices`** | <code>boolean</code> |


#### TCF2Settings

| Prop                                   | Type                                                                          |
| -------------------------------------- | ----------------------------------------------------------------------------- |
| **`firstLayerTitle`**                  | <code>string</code>                                                           |
| **`secondLayerTitle`**                 | <code>string</code>                                                           |
| **`tabsPurposeLabel`**                 | <code>string</code>                                                           |
| **`tabsVendorsLabel`**                 | <code>string</code>                                                           |
| **`labelsFeatures`**                   | <code>string</code>                                                           |
| **`labelsIabVendors`**                 | <code>string</code>                                                           |
| **`labelsNonIabPurposes`**             | <code>string</code>                                                           |
| **`labelsNonIabVendors`**              | <code>string</code>                                                           |
| **`labelsPurposes`**                   | <code>string</code>                                                           |
| **`vendorFeatures`**                   | <code>string</code>                                                           |
| **`vendorLegitimateInterestPurposes`** | <code>string</code>                                                           |
| **`vendorPurpose`**                    | <code>string</code>                                                           |
| **`vendorSpecialFeatures`**            | <code>string</code>                                                           |
| **`vendorSpecialPurposes`**            | <code>string</code>                                                           |
| **`togglesConsentToggleLabel`**        | <code>string</code>                                                           |
| **`togglesLegIntToggleLabel`**         | <code>string</code>                                                           |
| **`buttonsAcceptAllLabel`**            | <code>string</code>                                                           |
| **`buttonsDenyAllLabel`**              | <code>string</code>                                                           |
| **`buttonsSaveLabel`**                 | <code>string</code>                                                           |
| **`linksManageSettingsLabel`**         | <code>string</code>                                                           |
| **`linksVendorListLinkLabel`**         | <code>string</code>                                                           |
| **`cmpId`**                            | <code>number</code>                                                           |
| **`cmpVersion`**                       | <code>number</code>                                                           |
| **`firstLayerHideToggles`**            | <code>boolean</code>                                                          |
| **`secondLayerHideToggles`**           | <code>boolean</code>                                                          |
| **`hideLegitimateInterestToggles`**    | <code>boolean</code>                                                          |
| **`firstLayerHideButtonDeny`**         | <code>boolean</code>                                                          |
| **`secondLayerHideButtonDeny`**        | <code>boolean</code>                                                          |
| **`publisherCountryCode`**             | <code>string</code>                                                           |
| **`purposeOneTreatment`**              | <code>boolean</code>                                                          |
| **`selectedVendorIds`**                | <code>number[]</code>                                                         |
| **`gdprApplies`**                      | <code>boolean</code>                                                          |
| **`selectedStacks`**                   | <code>number[]</code>                                                         |
| **`disabledSpecialFeatures`**          | <code>number[]</code>                                                         |
| **`firstLayerShowDescriptions`**       | <code>boolean</code>                                                          |
| **`hideNonIabOnFirstLayer`**           | <code>boolean</code>                                                          |
| **`resurfacePeriod`**                  | <code>number</code>                                                           |
| **`resurfacePurposeChanged`**          | <code>boolean</code>                                                          |
| **`resurfaceVendorAdded`**             | <code>boolean</code>                                                          |
| **`firstLayerDescription`**            | <code>string</code>                                                           |
| **`firstLayerAdditionalInfo`**         | <code>string</code>                                                           |
| **`secondLayerDescription`**           | <code>string</code>                                                           |
| **`togglesSpecialFeaturesToggleOn`**   | <code>string</code>                                                           |
| **`togglesSpecialFeaturesToggleOff`**  | <code>string</code>                                                           |
| **`appLayerNoteResurface`**            | <code>string</code>                                                           |
| **`firstLayerNoteResurface`**          | <code>string</code>                                                           |
| **`categoriesOfDataLabel`**            | <code>string</code>                                                           |
| **`dataRetentionPeriodLabel`**         | <code>string</code>                                                           |
| **`legitimateInterestLabel`**          | <code>string</code>                                                           |
| **`version`**                          | <code>string</code>                                                           |
| **`examplesLabel`**                    | <code>string</code>                                                           |
| **`firstLayerMobileVariant`**          | <code><a href="#firstlayermobilevariant">FirstLayerMobileVariant</a></code>   |
| **`showDataSharedOutsideEUText`**      | <code>boolean</code>                                                          |
| **`dataSharedOutsideEUText`**          | <code>string</code>                                                           |
| **`vendorIdsOutsideEUList`**           | <code>number[]</code>                                                         |
| **`scope`**                            | <code><a href="#tcf2scope">TCF2Scope</a></code>                               |
| **`changedPurposes`**                  | <code><a href="#tcf2changedpurposes">TCF2ChangedPurposes</a></code>           |
| **`acmV2Enabled`**                     | <code>boolean</code>                                                          |
| **`selectedATPIds`**                   | <code>number[]</code>                                                         |
| **`consentOrPay`**                     | <code><a href="#tcf2consentorpaysettings">TCF2ConsentOrPaySettings</a></code> |
| **`mandatoryLabel`**                   | <code>string</code>                                                           |


#### TCF2ChangedPurposes

| Prop                 | Type                  |
| -------------------- | --------------------- |
| **`purposes`**       | <code>number[]</code> |
| **`legIntPurposes`** | <code>number[]</code> |


#### TCF2ConsentOrPaySettings

| Prop                        | Type                                                            | Description                                                                      |
| --------------------------- | --------------------------------------------------------------- | -------------------------------------------------------------------------------- |
| **`enableConsentOrPay`**    | <code>boolean</code>                                            |                                                                                  |
| **`showTogglesForVendors`** | <code>boolean</code>                                            |                                                                                  |
| **`publisherRestrictions`** | <code><a href="#record">Record</a>&lt;string, string&gt;</code> | Maps TCF Purpose ID (as string) to "flexible". Absent entries are mandatory.     |
| **`specialFeatures`**       | <code><a href="#record">Record</a>&lt;string, string&gt;</code> | Maps Special Feature ID (as string) to "flexible". Absent entries are mandatory. |


#### CCPASettings

| Prop                                      | Type                 | Description                                     |
| ----------------------------------------- | -------------------- | ----------------------------------------------- |
| **`optOutNoticeLabel`**                   | <code>string</code>  |                                                 |
| **`btnSave`**                             | <code>string</code>  |                                                 |
| **`firstLayerTitle`**                     | <code>string</code>  |                                                 |
| **`isActive`**                            | <code>boolean</code> |                                                 |
| **`showOnPageLoad`**                      | <code>boolean</code> |                                                 |
| **`reshowAfterDays`**                     | <code>number</code>  |                                                 |
| **`iabAgreementExists`**                  | <code>boolean</code> |                                                 |
| **`appFirstLayerDescription`**            | <code>string</code>  |                                                 |
| **`firstLayerMobileDescriptionIsActive`** | <code>boolean</code> |                                                 |
| **`firstLayerMobileDescription`**         | <code>string</code>  |                                                 |
| **`secondLayerTitle`**                    | <code>string</code>  |                                                 |
| **`secondLayerDescription`**              | <code>string</code>  |                                                 |
| **`secondLayerHideLanguageSwitch`**       | <code>boolean</code> |                                                 |
| **`btnMoreInfo`**                         | <code>string</code>  |                                                 |
| **`mspaCoveredTransaction`**              | <code>boolean</code> |                                                 |
| **`mspaMode`**                            | <code>number</code>  | Serialized as the native MspaMode enum ordinal. |


#### FirstLayer

| Prop                 | Type                 |
| -------------------- | -------------------- |
| **`hideButtonDeny`** | <code>boolean</code> |


#### UsercentricsCustomization

| Prop                     | Type                                                              |
| ------------------------ | ----------------------------------------------------------------- |
| **`color`**              | <code><a href="#customizationcolor">CustomizationColor</a></code> |
| **`font`**               | <code><a href="#customizationfont">CustomizationFont</a></code>   |
| **`logoUrl`**            | <code>string</code>                                               |
| **`borderRadiusLayer`**  | <code>number</code>                                               |
| **`borderRadiusButton`** | <code>number</code>                                               |
| **`overlayOpacity`**     | <code>number</code>                                               |


#### CustomizationColor

| Prop                           | Type                |
| ------------------------------ | ------------------- |
| **`primary`**                  | <code>string</code> |
| **`acceptBtnText`**            | <code>string</code> |
| **`acceptBtnBackground`**      | <code>string</code> |
| **`denyBtnText`**              | <code>string</code> |
| **`denyBtnBackground`**        | <code>string</code> |
| **`saveBtnText`**              | <code>string</code> |
| **`saveBtnBackground`**        | <code>string</code> |
| **`linkIcon`**                 | <code>string</code> |
| **`linkFont`**                 | <code>string</code> |
| **`text`**                     | <code>string</code> |
| **`layerBackground`**          | <code>string</code> |
| **`overlay`**                  | <code>string</code> |
| **`toggleInactiveBackground`** | <code>string</code> |
| **`toggleInactiveIcon`**       | <code>string</code> |
| **`toggleActiveBackground`**   | <code>string</code> |
| **`toggleActiveIcon`**         | <code>string</code> |
| **`toggleDisabledBackground`** | <code>string</code> |
| **`toggleDisabledIcon`**       | <code>string</code> |
| **`secondLayerTab`**           | <code>string</code> |
| **`moreBtnBackground`**        | <code>string</code> |
| **`moreBtnText`**              | <code>string</code> |


#### CustomizationFont

| Prop         | Type                |
| ------------ | ------------------- |
| **`family`** | <code>string</code> |
| **`size`**   | <code>number</code> |


#### VariantsSettings

| Prop                  | Type                 |
| --------------------- | -------------------- |
| **`enabled`**         | <code>boolean</code> |
| **`experimentsJson`** | <code>string</code>  |
| **`activateWith`**    | <code>string</code>  |


#### PublishedApp

| Prop           | Type                                                                  |
| -------------- | --------------------------------------------------------------------- |
| **`bundleId`** | <code>string</code>                                                   |
| **`platform`** | <code><a href="#publishedappplatform">PublishedAppPlatform</a></code> |


#### UsercentricsService

| Prop                             | Type                                                                        | Description                                  |
| -------------------------------- | --------------------------------------------------------------------------- | -------------------------------------------- |
| **`templateId`**                 | <code>string</code>                                                         | The template ID of the service.              |
| **`version`**                    | <code>string</code>                                                         | The version of the service.                  |
| **`type`**                       | <code>string</code>                                                         |                                              |
| **`isEssential`**                | <code>boolean</code>                                                        |                                              |
| **`dataProcessor`**              | <code>string</code>                                                         |                                              |
| **`dataPurposes`**               | <code>string[]</code>                                                       |                                              |
| **`processingCompany`**          | <code>string</code>                                                         |                                              |
| **`nameOfProcessingCompany`**    | <code>string</code>                                                         |                                              |
| **`addressOfProcessingCompany`** | <code>string</code>                                                         |                                              |
| **`descriptionOfService`**       | <code>string</code>                                                         |                                              |
| **`languagesAvailable`**         | <code>string[]</code>                                                       |                                              |
| **`dataCollectedList`**          | <code>string[]</code>                                                       |                                              |
| **`dataPurposesList`**           | <code>string[]</code>                                                       |                                              |
| **`dataRecipientsList`**         | <code>string[]</code>                                                       |                                              |
| **`legalBasisList`**             | <code>string[]</code>                                                       |                                              |
| **`retentionPeriodList`**        | <code>string[]</code>                                                       |                                              |
| **`subConsents`**                | <code>string[]</code>                                                       |                                              |
| **`language`**                   | <code>string</code>                                                         |                                              |
| **`linkToDpa`**                  | <code>string</code>                                                         |                                              |
| **`legalGround`**                | <code>string</code>                                                         |                                              |
| **`optOutUrl`**                  | <code>string</code>                                                         |                                              |
| **`policyOfProcessorUrl`**       | <code>string</code>                                                         |                                              |
| **`categorySlug`**               | <code>string</code>                                                         | The category slug identifier of the service. |
| **`retentionPeriodDescription`** | <code>string</code>                                                         |                                              |
| **`dataProtectionOfficer`**      | <code>string</code>                                                         |                                              |
| **`privacyPolicyURL`**           | <code>string</code>                                                         |                                              |
| **`cookiePolicyURL`**            | <code>string</code>                                                         |                                              |
| **`locationOfProcessing`**       | <code>string</code>                                                         |                                              |
| **`dataCollectedDescription`**   | <code>string</code>                                                         |                                              |
| **`thirdCountryTransfer`**       | <code>string</code>                                                         |                                              |
| **`description`**                | <code>string</code>                                                         |                                              |
| **`cookieMaxAgeSeconds`**        | <code>number</code>                                                         |                                              |
| **`usesNonCookieAccess`**        | <code>boolean</code>                                                        |                                              |
| **`deviceStorageDisclosureUrl`** | <code>string</code>                                                         |                                              |
| **`isDeactivated`**              | <code>boolean</code>                                                        |                                              |
| **`disableLegalBasis`**          | <code>boolean</code>                                                        |                                              |
| **`technologyUsed`**             | <code>string[]</code>                                                       |                                              |
| **`deviceStorage`**              | <code><a href="#consentdisclosureobject">ConsentDisclosureObject</a></code> |                                              |
| **`isHidden`**                   | <code>boolean</code>                                                        |                                              |


#### ConsentDisclosureObject

| Prop              | Type                                |
| ----------------- | ----------------------------------- |
| **`disclosures`** | <code>ConsentDisclosure[]</code>    |
| **`sdks`**        | <code>ConsentDisclosureSDK[]</code> |


#### ConsentDisclosure

| Prop                | Type                                                                    |
| ------------------- | ----------------------------------------------------------------------- |
| **`identifier`**    | <code>string</code>                                                     |
| **`type`**          | <code><a href="#consentdisclosuretype">ConsentDisclosureType</a></code> |
| **`name`**          | <code>string</code>                                                     |
| **`maxAgeSeconds`** | <code>number</code>                                                     |
| **`cookieRefresh`** | <code>boolean</code>                                                    |
| **`purposes`**      | <code>number[]</code>                                                   |
| **`domain`**        | <code>string</code>                                                     |
| **`description`**   | <code>string</code>                                                     |


#### ConsentDisclosureSDK

| Prop       | Type                |
| ---------- | ------------------- |
| **`name`** | <code>string</code> |
| **`use`**  | <code>string</code> |


#### UsercentricsCategory

| Prop               | Type                 | Description                              |
| ------------------ | -------------------- | ---------------------------------------- |
| **`categorySlug`** | <code>string</code>  | The category slug identifier.            |
| **`label`**        | <code>string</code>  | The label of the category, if any.       |
| **`description`**  | <code>string</code>  | The description of the category, if any. |
| **`isEssential`**  | <code>boolean</code> | True, if it is an essential category.    |


#### UsercentricsLocation

| Prop                 | Type                 | Description                                                   |
| -------------------- | -------------------- | ------------------------------------------------------------- |
| **`countryCode`**    | <code>string</code>  | The country code, e.g. 'DE'.                                  |
| **`regionCode`**     | <code>string</code>  | The region code following the local format, e.g. 'CA'.        |
| **`isInEU`**         | <code>boolean</code> | True, if the location is inside the European Union.           |
| **`isInUS`**         | <code>boolean</code> | True, if the location is inside the United States of America. |
| **`isInCalifornia`** | <code>boolean</code> | True, if the location is inside the state of California.      |


#### LegalBasisLocalization

| Prop             | Type                                                                    |
| ---------------- | ----------------------------------------------------------------------- |
| **`labelsAria`** | <code><a href="#translationarialabels">TranslationAriaLabels</a></code> |
| **`data`**       | <code><a href="#record">Record</a>&lt;string, string&gt;</code>         |


#### TranslationAriaLabels

| Prop                                   | Type                |
| -------------------------------------- | ------------------- |
| **`acceptAllButton`**                  | <code>string</code> |
| **`ccpaButton`**                       | <code>string</code> |
| **`ccpaMoreInformation`**              | <code>string</code> |
| **`closeButton`**                      | <code>string</code> |
| **`collapse`**                         | <code>string</code> |
| **`cookiePolicyButton`**               | <code>string</code> |
| **`copyControllerId`**                 | <code>string</code> |
| **`denyAllButton`**                    | <code>string</code> |
| **`expand`**                           | <code>string</code> |
| **`fullscreenButton`**                 | <code>string</code> |
| **`imprintButton`**                    | <code>string</code> |
| **`languageSelector`**                 | <code>string</code> |
| **`privacyButton`**                    | <code>string</code> |
| **`privacyPolicyButton`**              | <code>string</code> |
| **`saveButton`**                       | <code>string</code> |
| **`serviceInCategoryDetails`**         | <code>string</code> |
| **`servicesInCategory`**               | <code>string</code> |
| **`tabButton`**                        | <code>string</code> |
| **`usercentricsCMPButtons`**           | <code>string</code> |
| **`usercentricsCMPContent`**           | <code>string</code> |
| **`usercentricsCMPHeader`**            | <code>string</code> |
| **`usercentricsCMPUI`**                | <code>string</code> |
| **`usercentricsCard`**                 | <code>string</code> |
| **`usercentricsList`**                 | <code>string</code> |
| **`vendorConsentToggle`**              | <code>string</code> |
| **`vendorDetailedStorageInformation`** | <code>string</code> |
| **`vendorLegIntToggle`**               | <code>string</code> |


#### TCFData

Serialized TCF data as returned by the native SDK.

| Prop                  | Type                             | Description                                                                                                                                                                                             |
| --------------------- | -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`features`**        | <code>TCFFeature[]</code>        | All TCF features that need to be disclosed to the end-user if TCF is enabled.                                                                                                                           |
| **`purposes`**        | <code>TCFPurpose[]</code>        | All TCF purposes that need to be disclosed to the end-user if TCF is enabled. Purposes that are part of a selected stack are flagged with isPartOfASelectedStack = true and include a non-null stackId. |
| **`specialFeatures`** | <code>TCFSpecialFeature[]</code> | All TCF special features that need to be disclosed to the end-user if TCF is enabled.                                                                                                                   |
| **`specialPurposes`** | <code>TCFSpecialPurpose[]</code> | All TCF special purposes that need to be disclosed to the end-user if TCF is enabled.                                                                                                                   |
| **`stacks`**          | <code>TCFStack[]</code>          | All TCF stacks that need to be disclosed to the end-user if TCF is enabled.                                                                                                                             |
| **`vendors`**         | <code>TCFVendor[]</code>         | All TCF vendors that need to be disclosed to the end-user if TCF is enabled.                                                                                                                            |
| **`tcString`**        | <code>string</code>              | The TC string.                                                                                                                                                                                          |
| **`thirdPartyCount`** | <code>number</code>              | The total of vendors and services.                                                                                                                                                                      |


#### TCFFeature

| Prop                     | Type                  |
| ------------------------ | --------------------- |
| **`purposeDescription`** | <code>string</code>   |
| **`illustrations`**      | <code>string[]</code> |
| **`id`**                 | <code>number</code>   |
| **`name`**               | <code>string</code>   |


#### TCFPurpose

| Prop                               | Type                  |
| ---------------------------------- | --------------------- |
| **`purposeDescription`**           | <code>string</code>   |
| **`illustrations`**                | <code>string[]</code> |
| **`id`**                           | <code>number</code>   |
| **`name`**                         | <code>string</code>   |
| **`consent`**                      | <code>boolean</code>  |
| **`isPartOfASelectedStack`**       | <code>boolean</code>  |
| **`legitimateInterestConsent`**    | <code>boolean</code>  |
| **`showConsentToggle`**            | <code>boolean</code>  |
| **`showLegitimateInterestToggle`** | <code>boolean</code>  |
| **`stackId`**                      | <code>number</code>   |
| **`numberOfVendors`**              | <code>number</code>   |


#### TCFSpecialFeature

| Prop                         | Type                  |
| ---------------------------- | --------------------- |
| **`purposeDescription`**     | <code>string</code>   |
| **`illustrations`**          | <code>string[]</code> |
| **`id`**                     | <code>number</code>   |
| **`name`**                   | <code>string</code>   |
| **`consent`**                | <code>boolean</code>  |
| **`isPartOfASelectedStack`** | <code>boolean</code>  |
| **`stackId`**                | <code>number</code>   |
| **`showConsentToggle`**      | <code>boolean</code>  |


#### TCFSpecialPurpose

| Prop                     | Type                  |
| ------------------------ | --------------------- |
| **`purposeDescription`** | <code>string</code>   |
| **`illustrations`**      | <code>string[]</code> |
| **`id`**                 | <code>number</code>   |
| **`name`**               | <code>string</code>   |


#### TCFStack

| Prop                    | Type                  |
| ----------------------- | --------------------- |
| **`description`**       | <code>string</code>   |
| **`id`**                | <code>number</code>   |
| **`name`**              | <code>string</code>   |
| **`purposeIds`**        | <code>number[]</code> |
| **`specialFeatureIds`** | <code>number[]</code> |


#### TCFVendor

| Prop                               | Type                                |
| ---------------------------------- | ----------------------------------- |
| **`consent`**                      | <code>boolean</code>                |
| **`features`**                     | <code>number[]</code>               |
| **`flexiblePurposes`**             | <code>number[]</code>               |
| **`id`**                           | <code>number</code>                 |
| **`legitimateInterestConsent`**    | <code>boolean</code>                |
| **`legitimateInterestPurposes`**   | <code>number[]</code>               |
| **`name`**                         | <code>string</code>                 |
| **`policyUrl`**                    | <code>string</code>                 |
| **`purposes`**                     | <code>number[]</code>               |
| **`specialFeatures`**              | <code>number[]</code>               |
| **`specialPurposes`**              | <code>number[]</code>               |
| **`showConsentToggle`**            | <code>boolean</code>                |
| **`showLegitimateInterestToggle`** | <code>boolean</code>                |
| **`cookieMaxAgeSeconds`**          | <code>number</code>                 |
| **`usesNonCookieAccess`**          | <code>boolean</code>                |
| **`deviceStorageDisclosureUrl`**   | <code>string</code>                 |
| **`usesCookies`**                  | <code>boolean</code>                |
| **`cookieRefresh`**                | <code>boolean</code>                |
| **`dataSharedOutsideEU`**          | <code>boolean</code>                |
| **`dataCategories`**               | <code>number[]</code>               |
| **`vendorUrls`**                   | <code>VendorUrl[]</code>            |
| **`restrictions`**                 | <code>TCFVendorRestriction[]</code> |


#### VendorUrl

| Prop              | Type                |
| ----------------- | ------------------- |
| **`langId`**      | <code>string</code> |
| **`privacy`**     | <code>string</code> |
| **`legIntClaim`** | <code>string</code> |


#### TCFVendorRestriction

| Prop                  | Type                                                        |
| --------------------- | ----------------------------------------------------------- |
| **`purposeId`**       | <code>number</code>                                         |
| **`restrictionType`** | <code><a href="#restrictiontype">RestrictionType</a></code> |


#### TCFUserDecisionOnPurpose

| Prop                            | Type                 |
| ------------------------------- | -------------------- |
| **`id`**                        | <code>number</code>  |
| **`consent`**                   | <code>boolean</code> |
| **`legitimateInterestConsent`** | <code>boolean</code> |


#### TCFUserDecisionOnVendor

| Prop                            | Type                 |
| ------------------------------- | -------------------- |
| **`id`**                        | <code>number</code>  |
| **`consent`**                   | <code>boolean</code> |
| **`legitimateInterestConsent`** | <code>boolean</code> |


#### UserDecision

| Prop            | Type                 |
| --------------- | -------------------- |
| **`serviceId`** | <code>string</code>  |
| **`consent`**   | <code>boolean</code> |


#### TCFUserDecisions

| Prop                  | Type                                           |
| --------------------- | ---------------------------------------------- |
| **`purposes`**        | <code>TCFUserDecisionOnPurpose[]</code>        |
| **`specialFeatures`** | <code>TCFUserDecisionOnSpecialFeature[]</code> |
| **`vendors`**         | <code>TCFUserDecisionOnVendor[]</code>         |
| **`adTechProviders`** | <code>AdTechProviderDecision[]</code>          |


#### TCFUserDecisionOnSpecialFeature

| Prop          | Type                 |
| ------------- | -------------------- |
| **`id`**      | <code>number</code>  |
| **`consent`** | <code>boolean</code> |


#### AdTechProviderDecision

| Prop          | Type                 |
| ------------- | -------------------- |
| **`id`**      | <code>number</code>  |
| **`consent`** | <code>boolean</code> |


#### CCPAData

| Prop              | Type                 |
| ----------------- | -------------------- |
| **`version`**     | <code>number</code>  |
| **`uspString`**   | <code>string</code>  |
| **`optedOut`**    | <code>boolean</code> |
| **`lspact`**      | <code>boolean</code> |
| **`noticeGiven`** | <code>boolean</code> |


#### AdditionalConsentModeData

| Prop                  | Type                          |
| --------------------- | ----------------------------- |
| **`acString`**        | <code>string</code>           |
| **`adTechProviders`** | <code>AdTechProvider[]</code> |


#### AdTechProvider

| Prop                   | Type                 |
| ---------------------- | -------------------- |
| **`id`**               | <code>number</code>  |
| **`name`**             | <code>string</code>  |
| **`privacyPolicyUrl`** | <code>string</code>  |
| **`consent`**          | <code>boolean</code> |


#### GppData

| Prop                     | Type                                                                                                         |
| ------------------------ | ------------------------------------------------------------------------------------------------------------ |
| **`gppString`**          | <code>string \| null</code>                                                                                  |
| **`applicableSections`** | <code>number[]</code>                                                                                        |
| **`sections`**           | <code><a href="#record">Record</a>&lt;string, <a href="#record">Record</a>&lt;string, unknown&gt;&gt;</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### GppSectionChangePayload

| Prop       | Type                |
| ---------- | ------------------- |
| **`data`** | <code>string</code> |


### Type Aliases


#### UsercentricsNetworkMode

<code>'world' | 'eu'</code>


#### LegalLinksSettings

<code>'FIRST_LAYER_ONLY' | 'SECOND_LAYER_ONLY' | 'BOTH' | 'HIDDEN'</code>


#### UsercentricsLayout

<code>'FULL' | 'SHEET' | 'POPUP_CENTER' | 'POPUP_BOTTOM'</code>


#### SectionAlignment

<code>'START' | 'END' | 'CENTER'</code>


#### ButtonType

<code>'ACCEPT_ALL' | 'DENY_ALL' | 'MORE' | 'SAVE'</code>


#### ButtonLayoutType

<code>'ROW' | 'COLUMN' | 'GRID'</code>


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


#### UsercentricsConsentType

<code>'explicit' | 'implicit'</code>


#### TCFDecisionUILayer

<code>'firstLayer' | 'secondLayer'</code>


### Enums


#### FirstLayerMobileVariant

| Members            | Value          |
| ------------------ | -------------- |
| **`SHEET`**        | <code>0</code> |
| **`FULL`**         | <code>1</code> |
| **`POPUP_BOTTOM`** | <code>2</code> |
| **`POPUP_CENTER`** | <code>3</code> |


#### TCF2Scope

| Members       | Value          |
| ------------- | -------------- |
| **`GLOBAL`**  | <code>0</code> |
| **`SERVICE`** | <code>1</code> |


#### DpsDisplayFormat

| Members     | Value          |
| ----------- | -------------- |
| **`ALL`**   | <code>0</code> |
| **`SHORT`** | <code>1</code> |


#### USAFrameworks

| Members     | Value          |
| ----------- | -------------- |
| **`CPRA`**  | <code>0</code> |
| **`VCDPA`** | <code>1</code> |
| **`CPA`**   | <code>2</code> |
| **`CTDPA`** | <code>3</code> |
| **`UCPA`**  | <code>4</code> |


#### PublishedAppPlatform

| Members       | Value          |
| ------------- | -------------- |
| **`ANDROID`** | <code>0</code> |
| **`IOS`**     | <code>1</code> |


#### ConsentDisclosureType

| Members      | Value          |
| ------------ | -------------- |
| **`COOKIE`** | <code>0</code> |
| **`WEB`**    | <code>1</code> |
| **`APP`**    | <code>2</code> |


#### UsercentricsVariant

| Members       | Value          |
| ------------- | -------------- |
| **`DEFAULT`** | <code>0</code> |
| **`CCPA`**    | <code>1</code> |
| **`TCF`**     | <code>2</code> |


#### RestrictionType

| Members               | Value          |
| --------------------- | -------------- |
| **`NOT_ALLOWED`**     | <code>0</code> |
| **`REQUIRE_CONSENT`** | <code>1</code> |
| **`REQUIRE_LI`**      | <code>2</code> |


#### UsercentricsAnalyticsEventType

| Members                       | Value           |
| ----------------------------- | --------------- |
| **`CMP_SHOWN`**               | <code>0</code>  |
| **`ACCEPT_ALL_FIRST_LAYER`**  | <code>1</code>  |
| **`DENY_ALL_FIRST_LAYER`**    | <code>2</code>  |
| **`SAVE_FIRST_LAYER`**        | <code>3</code>  |
| **`ACCEPT_ALL_SECOND_LAYER`** | <code>4</code>  |
| **`DENY_ALL_SECOND_LAYER`**   | <code>5</code>  |
| **`SAVE_SECOND_LAYER`**       | <code>6</code>  |
| **`IMPRINT_LINK`**            | <code>7</code>  |
| **`MORE_INFORMATION_LINK`**   | <code>8</code>  |
| **`PRIVACY_POLICY_LINK`**     | <code>9</code>  |
| **`CCPA_TOGGLES_ON`**         | <code>10</code> |
| **`CCPA_TOGGLES_OFF`**        | <code>11</code> |

</docgen-api>
