package org.massnet.api;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class HttpApi {

    @Test
    public void testHttpApiWithJava() {
        // create API service
        MassNetApiV1 api = MassNetApiV1Impl.getDefaultService();
        // GET client status
        ClientStatus status = api.getClientStatus().blockingGet();
        System.out.println(status);
        // send a POST request & print results
        CreateWalletRequest req = new CreateWalletRequest("12345678", "wallet_1", null);
        System.out.println(req);
        api.createWallet(req)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .doOnError(System.out::println)
            .subscribe(System.out::println)
            .dispose();
    }
}
