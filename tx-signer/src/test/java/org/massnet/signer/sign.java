package org.massnet.signer;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class sign {
    @Test
    void testSigner() {
        var unsigned =
            "080112330a280a24091fc29fe6a799515d11b457408abbafe0e719dc9bd41fd20f13752194f2946b8e501167100119ffffffffffffffff12330a280a24092539f5b65a4f729b11debf45a8a4c3ae3919f9cdaf75aac8d31c21ba3764b4b1ed20de100119ffffffffffffffff1a2a0898bfffc024122200200555f6e6d13d419a272c179a3643a999307d2db481c987b1d7093751724e9aa01a2708ac341222002052586f0c1017b3497a0745d6a5390cd26e34f3d3acb9f1a0d3da7c487e74e42b";
        var priv = List.of(
            "649ce9ec4cf87af806ded9fb95a4eafe55c2e045ba7a16ca7d3ea20a63ac582e",
            "15a9d18c34531d4ac02116890ecd9fa623d4e6d2456769a28dd6206ea6bd9767"
        );
        var amounts = List.of(899999708L, 799997080L);
        var signed = Signer.signRawTransaction(unsigned, amounts, priv, HashType.SigHashAll);
        System.out.println(signed);
        var seed = SecureRandom.getSeed(256);
        var pair = Address.create(seed, false);
        System.out.println(pair.getFirst()); // address
        System.out.println(pair.getSecond()); // private key
    }

    @Test
    void testParseHash() throws Exception {
        var hashStr = "807b26a916bcd6338a5b715f053d9f8bb6d8d188b7770df8a57447891f7341cd";
        var hashBuilder = Proto.Hash.newBuilder();

        hashBuilder.setS0(Long.parseUnsignedLong(hashStr.substring(0,  16), 16));
        hashBuilder.setS1(Long.parseUnsignedLong(hashStr.substring(16, 32), 16));
        hashBuilder.setS2(Long.parseUnsignedLong(hashStr.substring(32, 48), 16));
        hashBuilder.setS3(Long.parseUnsignedLong(hashStr.substring(48, 64), 16));
        System.out.println(hashBuilder.build().toString());
    }


    @Test
    void testCreateTransaction() {
        var inputHash = new byte[32];
        var random = new Random();
        var input = ByteUtils.INSTANCE.hexToBytes("807b26a916bcd6338a5b715f053d9f8bb6d8d188b7770df8a57447891f7341cd");
        for (int i = 0; i < 32; ++i) {
            inputHash[i] = (byte) random.nextInt(256);
        }
        var vin = List.of(
            // no need to provide witness and address if only used for signing
            new Transaction.Input(input, 0, -1, List.of(), null),
            new Transaction.Input(input, 0, -1, List.of(), null)
        );
        var vout = List.of(
            new Transaction.Output(1000, Address.fromString("ms1qq8k8g3kfn23faudluydaadjj3g3fqme2jzz7hdut4lp656r3humuqmkwmmy").getPkScript()),
            new Transaction.Output(1000, Address.fromString("ms1qq8k8g3kfn23faudluydaadjj3g3fqme2jzz7hdut4lp656r3humuqmkwmmy").getPkScript())
        );
        var tx = new Transaction(1, 0, new byte[0], vin, vout);
        System.out.println(tx.toJson());

        // convert to Proto.Tx
        var protoTx = tx.toProtoTx();
        System.out.println(protoTx.toString());

        // sign, either Proto.Tx, Transaction or hex string can be accepted
        // var signedTx = Signer.signTransaction(protoTx, ...);
        // var signedTx = Signer.signTransaction(tx, ...);
        // var signedTx = Signer.signRawTransaction(hex, ...);
    }
}
