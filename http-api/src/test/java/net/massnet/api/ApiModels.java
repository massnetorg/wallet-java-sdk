package net.massnet.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

public class ApiModels {

    private static Gson gson = ModelSerializer.getGSON();

    @Test
    public void testApiModelsWithJava() throws Exception {
        var req = new CreateWalletRequest("", null, null);
        System.out.println(req);
    }
}
