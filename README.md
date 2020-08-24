# MassNet Wallet Java SDK

## Requirements

At least Java 8 is required. Java 10+ or Kotlin is recommended for simplicity.

## HTTP API

![maven centra version](https://img.shields.io/maven-central/v/org.massnet.sdk/http-api)

This package wraps the HTTP API of a MassNet wallet full node.

### Install

This packages is published to maven central repository as `org.massnet.sdk:http-api`.

You could install it in the following ways (replace `$latest_version` with the actual version shown above):

#### Maven

```xml
<dependency>
    <groupId>org.massnet.sdk</groupId>
    <artifactId>http-api</artifactId>
    <version>$latest_version</version>
</dependency>
```

#### Gradle

```groovy
implementation 'org.massnet.sdk:http-api:$latest_version'
```

#### Local build

After cloning this repository, run:

```bash
./gradlew :http-api:publishToMavenLocal
```

### Usage

First import `org.massnet.api`. All APIs support two invocation styles: RxJava and blocking.

```java
import org.massnet.api.*;
...

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

![maven centra version](https://img.shields.io/maven-central/v/org.massnet.sdk/tx-signer)

This package is an offline transaction signer of MassNet transactions.

### Install

This packages will be published to maven central repository as `org.massnet.sdk:tx-signer`.

See `http-api` for installation instructions.

### Usage

### Create a transaction

We recommend you to use `createRawTransaction` or `autoCreateTransaction` in HTTP API to create a raw MassNet transaction, which is encoded by ProtoBuf in hex.
You can also use `decodeRawTransaction` to decode any encoded transactions.

### Sign a transaction

You could use the tools in `org.massnet.signer` to sign a transaction.

```java
import org.massnet.signer.*;
...

var unsigned = "xxxx"; // you unsigned transaction in hex format
// list of private keys, each corresponding to a input
var priv = List.of(
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
);
// list of amounts of the UTXO, each corresponding to a input
var amount = List.of(1000000L, 200000L);
// sign the transaction
var signed = Signer.signRawTransaction(unsigned, amounts, priv, HashType.SigHashAll);
```

Current this tool only supports `SigHashAll`, which is the default signing type.

To produce a correct signature, you must provide each input in the transaction with:

* the private key associated with the address (derived by BIP32, 256 bits)
* the amount of the UTXO on the address (in the unit of SAT, a.k.a. 10^-8)

### Send a transaction

Once you obtain the signed transaction by using `signRawTransaction`,
you could send it to the chain with `sendRawTransaction` in the HTTP API.
