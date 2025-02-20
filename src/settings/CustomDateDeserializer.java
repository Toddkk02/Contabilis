package settings;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    private static final SimpleDateFormat FORMAT_STANDARD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMAT_COMPLETO = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat FORMAT_EUROPEAN = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateString = jsonParser.getText();

        // Try each format in sequence
        ParseException lastException = null;

        try {
            return FORMAT_STANDARD.parse(dateString);
        } catch (ParseException e1) {
            lastException = e1;
        }

        try {
            return FORMAT_COMPLETO.parse(dateString);
        } catch (ParseException e2) {
            lastException = e2;
        }

        try {
            return FORMAT_EUROPEAN.parse(dateString);
        } catch (ParseException e3) {
            lastException = e3;
        }

        // If none of the formats worked, throw an exception with details
        throw new IOException("Formato data non valido: " + dateString +
                ". Formati supportati: yyyy-MM-dd, dd/MM/yyyy, o EEE MMM dd HH:mm:ss zzz yyyy", lastException);
    }
}