import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

            System.out.println("Bienvenido al conversor de monedas.");
            Scanner scanner = new Scanner(System.in);
        int menuInt = 1;

        do {
            System.out.println("*************************************");
            System.out.print("Por favor ingresa el número indicado de acuerdo a la converción que deseas realizar:");
            System.out.println();
            System.out.println("""
                    1 - USD => ARS
                    2 - ARS => USD
                    3 - USD => BRL
                    4 - BRL => USD
                    5 - USD => COP
                    6 - COP => USD
                    7 - Salir.
                    """);
            System.out.println("*************************************");

            menuInt = scanner.nextInt();
            String monedaOrigen = "";
            String monedaDestino = "";

            switch (menuInt){
                case 1:
                    monedaOrigen = "USD";
                    monedaDestino = "ARS";
                    break;
                case 2:
                    monedaOrigen = "ARS";
                    monedaDestino = "USD";
                    break;
                case 3:
                    monedaOrigen = "USD";
                    monedaDestino = "BRL";
                    break;
                case 4:
                    monedaOrigen = "BRL";
                    monedaDestino = "USD";
                    break;
                case 5:
                    monedaOrigen = "USD";
                    monedaDestino = "COP";
                    break;
                case 6:
                    monedaOrigen = "COP";
                    monedaDestino = "USD";
                    break;
                default:
                    System.out.println("Esta opción no se encuentra disponible");
                    break;
            }

            if(monedaOrigen.isEmpty() || monedaOrigen.trim() == "" || monedaDestino.isEmpty() || monedaDestino.trim() == ""){
                System.out.println("Usted está saliendo de la aplicación. Gracias por utilizar Conversor de Monedas");
                break;
            }

            System.out.print("Ingrese el valor que desea convertir:");
            double valorInicial = scanner.nextDouble();

            // Llamada a la API
            String direccionURL = "https://v6.exchangerate-api.com/v6/96d7aea357a17f705cdecac9/pair/" + monedaOrigen + "/" + monedaDestino;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccionURL))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            Gson gson = new Gson();
            Moneda nuevaConversion = gson.fromJson(json, Moneda.class);
            double valorDeCambio = nuevaConversion.conversionDeMoneda(valorInicial);

            System.out.println("Usted está convirtiendo $" + monedaOrigen + " a $" + monedaDestino);
            System.out.println("Valor en " + monedaOrigen + ": " + valorInicial);
            System.out.println("Valor en " + monedaDestino + ": " + valorDeCambio);
        } while (menuInt != 7);

    }


}
