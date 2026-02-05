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
* [`showBanner()`](#showbanner)
* [`showSecondLayer()`](#showsecondlayer)
* [`getConsents()`](#getconsents)
* [`getCMPData()`](#getcmpdata)
* [`getTCFData()`](#gettcfdata)
* [`acceptAll()`](#acceptall)
* [`denyAll()`](#denyall)
* [`applyConsent(...)`](#applyconsent)
* [`saveConsent(...)`](#saveconsent)
* [`restoreUserSession(...)`](#restoreusersession)
* [`saveUserSession()`](#saveusersession)
* [`getControllerId()`](#getcontrollerid)
* [`clearUserSession()`](#clearusersession)
* [`changeLanguage(...)`](#changelanguage)
* [`setCMPId(...)`](#setcmpid)
* [`setABTestingVariant(...)`](#setabtestingvariant)
* [`getABTestingVariant()`](#getabtestingvariant)
* [`getCCPAData()`](#getccpadata)
* [`getAdditionalConsentModeData()`](#getadditionalconsentmodedata)
* [`track(...)`](#track)
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


### showBanner()

```typescript
showBanner() => Promise<UsercentricsBannerResult>
```

**Returns:** <code>Promise&lt;<a href="#usercentricsbannerresult">UsercentricsBannerResult</a>&gt;</code>

--------------------


### showSecondLayer()

```typescript
showSecondLayer() => Promise<UsercentricsBannerResult>
```

**Returns:** <code>Promise&lt;<a href="#usercentricsbannerresult">UsercentricsBannerResult</a>&gt;</code>

--------------------


### getConsents()

```typescript
getConsents() => Promise<UsercentricsConsent[]>
```

**Returns:** <code>Promise&lt;UsercentricsConsent[]&gt;</code>

--------------------


### getCMPData()

```typescript
getCMPData() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getTCFData()

```typescript
getTCFData() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### acceptAll()

```typescript
acceptAll() => Promise<void>
```

--------------------


### denyAll()

```typescript
denyAll() => Promise<void>
```

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


### track(...)

```typescript
track(options: { event: UsercentricsAnalyticsEventType; }) => Promise<void>
```

| Param         | Type                                                                                                  |
| ------------- | ----------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ event: <a href="#usercentricsanalyticseventtype">UsercentricsAnalyticsEventType</a>; }</code> |

--------------------


### Interfaces


#### UsercentricsOptions

| Prop                   | Type                                                   |
| ---------------------- | ------------------------------------------------------ |
| **`settingsId`**       | <code>string</code>                                    |
| **`defaultLanguage`**  | <code>string</code>                                    |
| **`version`**          | <code>string</code>                                    |
| **`timeoutMillis`**    | <code>number</code>                                    |
| **`loggerLevel`**      | <code>'error' \| 'debug' \| 'warning' \| 'none'</code> |
| **`rulesetId`**        | <code>string</code>                                    |
| **`consentMediation`** | <code>boolean</code>                                   |


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


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


### Enums


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
