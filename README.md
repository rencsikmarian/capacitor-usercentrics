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
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

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
restoreUserSession(userSession: string) => Promise<void>
```

| Param             | Type                |
| ----------------- | ------------------- |
| **`userSession`** | <code>string</code> |

--------------------


### saveUserSession()

```typescript
saveUserSession() => Promise<{ session: string; }>
```

**Returns:** <code>Promise&lt;{ session: string; }&gt;</code>

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


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>

</docgen-api>
