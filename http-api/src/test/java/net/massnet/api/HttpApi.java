package net.massnet.api;

import org.junit.jupiter.api.Test;

public class HttpApi {

    @Test
    public void testHttpApiWithJava() {
        // create API service
        var api = MassNetApiV1Impl.create();
        // GET client status
        var status = api.getClientStatus().blockingGet();
        System.out.println(status);
        // send a POST request & print results
        var req = new CreateWalletRequest("12345678", "wallet_1", null);
        System.out.println(req);
        var resp = api.createWallet(req).blockingGet();
        System.out.println(resp);
    }
}
