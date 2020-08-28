package org.massnet.signer;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.List;

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
}
