package org.massnet.signer;

import kotlin.Pair;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class sign {
    @Test
    void testSigner() {
        String unsigned =
            "080112330a280a24091fc29fe6a799515d11b457408abbafe0e719dc9bd41fd20f13752194f2946b8e501167100119ffffffffffffffff12330a280a24092539f5b65a4f729b11debf45a8a4c3ae3919f9cdaf75aac8d31c21ba3764b4b1ed20de100119ffffffffffffffff1a2a0898bfffc024122200200555f6e6d13d419a272c179a3643a999307d2db481c987b1d7093751724e9aa01a2708ac341222002052586f0c1017b3497a0745d6a5390cd26e34f3d3acb9f1a0d3da7c487e74e42b";
        // private keys
        List<String> priv = new ArrayList<>();
        priv.add("649ce9ec4cf87af806ded9fb95a4eafe55c2e045ba7a16ca7d3ea20a63ac582e");
        priv.add("15a9d18c34531d4ac02116890ecd9fa623d4e6d2456769a28dd6206ea6bd9767");
        // amounts
        List<Long> amounts = new ArrayList<>();
        amounts.add(899999708L);
        amounts.add(799997080L);
        // sigh
        String signed = Signer.signRawTransaction(unsigned, amounts, priv, HashType.SigHashAll);
        System.out.println(signed);
        // address creation
        byte[] seed = SecureRandom.getSeed(256);
        Pair<String, String> pair = Address.create(seed, false);
        System.out.println(pair.getFirst()); // address
        System.out.println(pair.getSecond()); // private key

    }

    @Test
    void testParseHash() throws Exception {
        String hashStr = "807b26a916bcd6338a5b715f053d9f8bb6d8d188b7770df8a57447891f7341cd";
        Proto.Hash.Builder hashBuilder = Proto.Hash.newBuilder();

        hashBuilder.setS0(Long.parseUnsignedLong(hashStr.substring(0,  16), 16));
        hashBuilder.setS1(Long.parseUnsignedLong(hashStr.substring(16, 32), 16));
        hashBuilder.setS2(Long.parseUnsignedLong(hashStr.substring(32, 48), 16));
        hashBuilder.setS3(Long.parseUnsignedLong(hashStr.substring(48, 64), 16));
        System.out.println(hashBuilder.build().toString());
    }


    @Test
    void testCreateTransaction() {

        // hash
        byte[] inputHash = new byte[32];
        Random random = new Random();
        byte[] input = ByteUtils.hexToBytes("807b26a916bcd6338a5b715f053d9f8bb6d8d188b7770df8a57447891f7341cd");
        for (int i = 0; i < 32; ++i) {
            inputHash[i] = (byte) random.nextInt(256);
        }

        // inputs
        List<Transaction.Input> vin = new ArrayList<>();
        // no need to provide witness and address if only used for signing
        vin.add(new Transaction.Input(input, 0, -1, new ArrayList<>(), null));
        vin.add(new Transaction.Input(inputHash, 0, -1, new ArrayList<>(), null));

        // outputs
        List<Transaction.Output> vout = new ArrayList<>();
        Address addr = Address.fromString("ms1qq8k8g3kfn23faudluydaadjj3g3fqme2jzz7hdut4lp656r3humuqmkwmmy");
        BindingTarget target = BindingTarget.fromString("165SQQnzVLUzTMVybsAfh3yZhm9TsN9yKrT7o");
        vout.add(new Transaction.Output(1000, ScriptUtils.getP2WSHOutputScript(addr)));
        // vout.add(new Transaction.Output(2000, ScriptUtils.getP2SWSHOutputScript(addr))); // not implemented
        vout.add(new Transaction.Output(3000, ScriptUtils.getP2BWSHOutputScript(addr, target)));

        // generate transaction
        Transaction tx = new Transaction(1, 0, new byte[0], vin, vout);
        System.out.println(tx.toJson());
        System.out.println(tx.getHash()); // transaction id (hash)

        // convert to Proto.Tx
        Proto.Tx protoTx = tx.toProtoTx();
        System.out.println(protoTx.toString());

        // convert to JSON
        String json = tx.toJson();
        System.out.println(json);

        // convert from JSON
        Transaction fromJson = Utils.getGSON().fromJson("{}", Transaction.class); // NOTE: replace with your json
        System.out.println(fromJson.toJson());

        // sign, either Proto.Tx, Transaction or hex string can be accepted
        // var signedTx = Signer.signTransaction(protoTx, ...);
        // var signedTx = Signer.signTransaction(tx, ...);
        // var signedTx = Signer.signRawTransaction(hex, ...);
    }
}
