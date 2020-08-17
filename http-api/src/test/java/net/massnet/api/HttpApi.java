package net.massnet.api;

import org.junit.jupiter.api.Test;

public class HttpApi {

    @Test
    public void testHttpApiWithJava() {
        // create API service
        var start = System.currentTimeMillis();
        var api = MassNetApiV1Impl.getDefaultService();
        var end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.f);
//        // GET client status
//        var status = api.getClientStatus().blockingGet();
//        System.out.println(status);
//        // send a POST request & print results
//        var req = new CreateWalletRequest("12345678", "wallet_1", null);
//        System.out.println(req);
//        var resp = api.createWallet(req).blockingGet();
//        System.out.println(resp);
    }
}
