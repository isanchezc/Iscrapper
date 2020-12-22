import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.*;
import java.util.ArrayList;
import java.util.List;


public class Csv_generator {

    public  void generador(String[] titulocol, String[] nombresPrecios) throws IOException{
       String mes = LocalDate.now().getMonth().toString();
       int dia = LocalDate.now().getDayOfMonth();
        try ( Writer writer = Files.newBufferedWriter(Paths.get("./datos-"+dia+mes+".csv"));

              CSVWriter csvWriter = new CSVWriter(writer,';',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END)
        ){
            csvWriter.writeNext(titulocol);
            int i = 0;
            System.out.println(nombresPrecios.length);
            while (nombresPrecios[i]!= null) {
                csvWriter.writeNext(new String[]{nombresPrecios[i], nombresPrecios[i + 1]});
                i += 2;
            }



        }

    }
}
