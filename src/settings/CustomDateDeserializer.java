package settings;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    private static final SimpleDateFormat[] formatters = {
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("dd/MM/yyyy"),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
    };

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateStr = p.getText();

        // Try parsing as timestamp first
        try {
            long timestamp = Long.parseLong(dateStr);
            return new Date(timestamp);
        } catch (NumberFormatException ignored) {
            // Not a timestamp, continue with other formats
        }

        // Try all date formats
        for (SimpleDateFormat formatter : formatters) {
            try {
                return formatter.parse(dateStr);
            } catch (ParseException ignored) {
                // Try next format
            }
        }

        throw new IOException("Invalid date format: " + dateStr);
    }
}