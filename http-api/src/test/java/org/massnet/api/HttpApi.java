package org.massnet.api;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class HttpApi {

    @Test
    public void testHttpApiWithJava() {
        // create API service
        MassNetApiV1 api = MassNetApiV1Impl.getDefaultService();

        // GET client status using blocking way
        ClientStatus status = api.getClientStatus().blockingGet();
        // print in readable format
        System.out.println(status);
        // convert back to JSON
        String clientStatusJson = ModelSerializer.getGSON().toJson(status);
        System.out.println(clientStatusJson);

        // or send a POST request & print results using RxJava
        CreateWalletRequest req = new CreateWalletRequest("12345678", "wallet_1", null);
        System.out.println(req);
        api.createWallet(req)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .doOnError(e -> {
                // handle error
            })
            .subscribe(result -> {
                // do something with result
                System.out.println(result.getWalletId());
                String createWalletJson = ModelSerializer.getGSON().toJson(result);
                System.out.println(createWalletJson);
            })
            .dispose();
    }
}
