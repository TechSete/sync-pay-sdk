package tech.techsete.sync_pay_sdk.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MultiFormatDateDeserializer extends JsonDeserializer<OffsetDateTime> {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"),
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
    );

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().trim();
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                if (formatter == FORMATTERS.get(0)) { // sem timezone
                    LocalDateTime ldt = LocalDateTime.parse(value, formatter);
                    return ldt.atOffset(ZoneOffset.UTC); // assume UTC ou outro offset padrão
                } else {
                    return OffsetDateTime.parse(value, formatter);
                }
            } catch (Exception ignored) {}
        }
        throw new IOException("Formato de data inválido: " + value);
    }
}
