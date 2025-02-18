package networking;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Receipt;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DeleteReceipt {

    public static void deleteReceiptAtIndex(int index) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path path = Paths.get("/home/alessandro/IdeaProjects/Contabilita/receipts.json");

            // Leggi il JSON esistente
            List<Receipt> receipts = mapper.readValue(path.toFile(),
                    new TypeReference<List<Receipt>>() {});

            // Rimuovi l'elemento all'indice specificato
            if(index >= 0 && index < receipts.size()) {
                receipts.remove(index);

                // Scrivi il nuovo JSON aggiornato
                mapper.writeValue(path.toFile(), receipts);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}