package org.massnet.api;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class HttpApi {

    @Test
    public void testHttpApiWithJava() {
        // create API service
        var api = MassNetApiV1Impl.getDefaultService();
        // GET client status
        var status = api.getClientStatus().blockingGet();
        System.out.println(status);
        // send a POST request & print results
        var req = new CreateWalletRequest("12345678", "wallet_1", null);
        System.out.println(req);
        var resp = api.createWallet(req)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(System.out::println);
        System.out.println(resp);
    }
}
