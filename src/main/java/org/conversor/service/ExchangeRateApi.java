package org.conversor.service;


import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import org.conversor.model.CurrenciesData;

public class ExchangeRateApi {
  Dotenv dotEnv;
  ApiCall call;
  Gson gson;

  public double conversionRate(String baseCode, String targetCode) {
    call = new ApiCall();
    gson = new Gson();
    dotEnv = Dotenv.load();

    String data = call.Fetch(
            STR."https://v6.exchangerate-api.com/v6/\{dotEnv.get("API_KEY")}/pair/\{baseCode}/\{targetCode}"
    );
    return gson.fromJson(data, CurrenciesData.class).conversion_rate();
  }

  public CurrenciesData getCodeList() {
    call = new ApiCall();
    gson = new Gson();
    dotEnv = Dotenv.load();
    String codeJson = call.Fetch(STR."https://v6.exchangerate-api.com/v6/\{dotEnv.get("API_KEY")}/codes");
    return gson.fromJson(codeJson, CurrenciesData.class);
  }
}
