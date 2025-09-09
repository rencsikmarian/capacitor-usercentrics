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
* [`reset()`](#reset)
* [`getConsents()`](#getconsents)
* [`getCMPData()`](#getcmpdata)
* [`restoreUserSession(...)`](#restoreusersession)
* [`saveUserSession()`](#saveusersession)
* [Interfaces](#interfaces)

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


### reset()

```typescript
reset() => Promise<void>
```

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
saveUserSession() => Promise<string>
```

**Returns:** <code>Promise&lt;string&gt;</code>

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

| Prop                | Type                                  |
| ------------------- | ------------------------------------- |
| **`templateId`**    | <code>string</code>                   |
| **`status`**        | <code>boolean</code>                  |
| **`type`**          | <code>'explicit' \| 'implicit'</code> |
| **`timestamp`**     | <code>number</code>                   |
| **`dataProcessor`** | <code>string</code>                   |
| **`version`**       | <code>string</code>                   |
| **`isEssential`**   | <code>boolean</code>                  |


#### UsercentricsBannerResult

| Prop                  | Type                               |
| --------------------- | ---------------------------------- |
| **`consents`**        | <code>UsercentricsConsent[]</code> |
| **`userInteraction`** | <code>boolean</code>               |

</docgen-api>
