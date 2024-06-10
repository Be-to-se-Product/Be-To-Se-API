package com.be.two.c.apibetwoc.service.endereco;

import com.be.two.c.apibetwoc.model.Endereco;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Profile({"local","prod"})
public class AddressApiService implements IEndereco {

    private final OkHttpClient client;

    @Override
    public Endereco returnAddressWithLatitudeAndLongitude(Endereco address) throws IOException {
        AddressDto addressDto = makeApiCall(address.getCep(), address.getNumero());
        address.setGeolocalizacaoX(addressDto.longitude);
        address.setGeolocalizacaoY(addressDto.latitude);
        return address;
    }

    private AddressDto makeApiCall(String cep, String number) throws IOException {
        String url = String.format("http://localhost:5000/adress?cep=%s&numero=%s", cep, number);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            ResponseBody responseBody = response.body();

            String responseBodyString = responseBody.string();
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();

            double latitude = jsonObject.get("latitude").getAsDouble();
            double longitude = jsonObject.get("longitude").getAsDouble();

            return new AddressDto(latitude, longitude);
        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }

    public record AddressDto(double latitude, double longitude) {
        @Override
        public String toString() {
            return "AddressDto{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
}
