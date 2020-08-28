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

First import `org.massnet.api`.
All APIs support two invocation styles: RxJava and blocking.

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
api.createWallet(req)
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.computation())
    .doOnError(System.out::println) // exception handling
    .subscribe(System.out::println)
    .dispose();
```

The default endpoint is `"http://127.0.0.1:9688/v1/"`.
You should set `disable_tls` to `true` in `config.json` of MassNet wallet to disable HTTPS access.

See `HttpApi.kt` for all APIs, and `ApiModels.kt` for all data models used in APIs.

When the wallet returns an error, an `ApiException` will be thrown,
and you can add a handler in `doOnError` to process.

## Transaction Signer

![maven centra version](https://img.shields.io/maven-central/v/org.massnet.sdk/tx-signer)

This package is an offline transaction signer of MassNet transactions.

### Install

This packages will be published to maven central repository as `org.massnet.sdk:tx-signer`.

See `http-api` for installation instructions.

### Address

All methods and classes are in `org.massnet.signer.Address`.

#### Create an address (and its key)

```java
var seed = SecureRandom.getSeed(256);
var pair = Address.create(seed, false);
System.out.println(pair.getFirst()); // address
System.out.println(pair.getSecond()); // private key
```

The private key is derived with path `m/44'/297'/1'/0/` from the master key generated from the provided seed.

If key generation fails (e.g., the private key happens to be 0 or larger than N), an `HDDerivationException` will be thrown.

#### Parse & validate addresses

To validate an address:

```java
var parsed = Address.validate("ms1xxxxxxxxxx");
if (parsed == null) {
    // not valid
} else {
    // valid
}
```

It actually calls `Address.fromString()`, and returns `null` when any exception happens.

You could also construct an address manually:

```java
var fromString = Address.fromString("ms1yyyyyyyy"); // might throw Exception if not valid
var fromScriptHash = Address.fromScriptHash(hash, false); // hash must be 32 bytes long
var fromPubKey = Address.fromPubKey(key, false); // key must have a compressed public key (33 bytes)
```

### Transaction

All methods and classes are in `org.massnet.signer.Signer`.

#### Create transactions

We recommend you to use `createRawTransaction` or `autoCreateTransaction` in HTTP API to create a raw MassNet transaction, which is encoded by ProtoBuf in hex.
Or you can construct `org.massnet.signer.Proto.Tx` manually (by using `Proto.Tx.Builder` builder) and encode it to hex string.

You can also use `decodeRawTransaction` to decode any encoded transactions.

#### Sign transactions

```java
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

#### Send transactions

Once you obtain the signed transaction by using `signRawTransaction`,
you could send it to the chain with `sendRawTransaction` in the HTTP API.

#### Decode transactions

You could use `decodeRawTransaction` in HTTP API to get the JSON representation of a transaction.

We also provide a `Transaction` class for converting in Java.
Note that this class is different from `Proto.Tx`, which is the internal structure used by `Signer`.

```java
// directly obtain JSON
var json = Transaction.decodeRawToJson("080112a4010a280a24091fc29fe6a799515d11b457408abbafe...");
System.out.println(json);

// or if you want an instance
var transaction = Transaction.fromProtoTx(tx); // tx is a Proto.Tx
System.out.println(transaction.version);
```
