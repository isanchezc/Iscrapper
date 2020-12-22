
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;

public class Scrapper_pccomponentes {

    public static final String url = "https://www.pccomponentes.com/tv-65-pulgadas";
    public static final int maxPages = 2;

    public static void main (String[] args){
        String[] nombresPrecios = new String[100];


        String[] nombreCol = {"nombre", "precio"};

        Csv_generator csv = new Csv_generator();
        // Compruebo si me da un 200 al hacer la petición
        if (getStatusConnectionCode(url) == 200) {

            // Obtengo el HTML de la web en un objeto Document
            Document document = getHtmlDocument(url);

            // Busco todas las entradas que estan dentro de:
            Elements entradas = document.select(".c-product-card__content");
            System.out.println("Número de Televisores en la pagina de pcpollas: "+entradas.size()+"\n");

            // Paseo cada una de las entradas
            int i = 0;
            for (Element elem : entradas) {
                String titulo = elem.select(".c-product-card__title-link").text();
                String precio = elem.select(".c-product-card__prices-actual").text();
                nombresPrecios[i] = titulo;
                i++;
                nombresPrecios[i] = precio;
                i++;



                // Con el método "text()" obtengo el contenido que hay dentro de las etiquetas HTML
                // Con el método "toString()" obtengo todo el HTML con etiquetas incluidas
            }
            try {
                csv.generador(nombreCol,nombresPrecios);
            } catch (IOException e){
                System.out.println("algo ha fallado: " + e.getMessage());
            }


        }else
            System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(url));

    }

    /**
     * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
     * EJM:
     * 		200 OK			300 Multiple Choices
     * 		301 Moved Permanently	305 Use Proxy
     * 		400 Bad Request		403 Forbidden
     * 		404 Not Found		500 Internal Server Error
     * 		502 Bad Gateway		503 Service Unavailable
     * @param url
     * @return Status Code
     */
    public static int getStatusConnectionCode(String url) {
        Response response = null;

        try{
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException e){
            System.out.println("Excepcion recogida al obtener el Status Code: " + e.getMessage());
        }

        return response.statusCode();

    }

    /**
     * Con este método devuelvo un objeto de la clase Document con el contenido del
     * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup
     * @param url
     * @return Documento con el HTML
     */
    public static Document getHtmlDocument(String url) {

        Document doc = null;
        try{
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (IOException e){
            System.out.println("Excepcion al obtener el documento HTML de la pagina" + e.getMessage());
        }
        return doc;
    }

}
