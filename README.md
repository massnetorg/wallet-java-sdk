# MassNet Wallet Java SDK

## Requirements

At least Java 8 is required. Kotlin is recommended.

## HTTP API

This package wraps the HTTP API of a MassNet wallet full node.

### Usage

First import `net.massnet.api`. All APIs support two invocation styles: RxJava and blocking.

```java
// create API service (might be slow, about 0.5s)
var api = MassNetApiV1Impl.getDefaultService();

// example: get client status, blocking
var status = api.getClientStatus().blockingGet();
System.out.println(status);

// example: create a wallet, using RxJava (optional parameters are nullable)
var req = new CreateWalletRequest("12345678", "wallet_1", null);
var resp = api.createWallet(req)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe(System.out::println);
```

See `HttpApi.kt` for all services, and `ApiModels.kt` for all data models used in API.

## Transaction Signer

This package is an offline transaction signer of MassNet transactions.

### Usage

Development in progress.
