package settings;

import models.Target;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import models.Receipt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {
    private ObjectMapper mapper = new ObjectMapper();

    public void saveReceipts(List<Receipt> receipts) {
        try {
            File file = PathManager.getReceiptsPath().toFile();
            file.getParentFile().mkdirs(); // Crea la cartella se non esiste

            // Crea il file se non esiste
            if (!file.exists()) {
                file.createNewFile();
            }

            mapper.writeValue(file, receipts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Receipt> loadReceipts() {
        try {
            File file = PathManager.getReceiptsPath().toFile();
            file.getParentFile().mkdirs(); // Crea la cartella se non esiste

            // Se il file non esiste o è vuoto, inizializzalo con un array vuoto
            if (!file.exists() || file.length() == 0) {
                saveReceipts(new ArrayList<>());
                return new ArrayList<>();
            }

            try {
                return mapper.readValue(file,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Receipt.class));
            } catch (Exception e) {
                // Se c'è un errore di deserializzazione, resetta il file
                e.printStackTrace();
                saveReceipts(new ArrayList<>());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Receipt> getReceipts() {
        return loadReceipts();
    }

    public void addReceipt(Receipt receipt) {
        List<Receipt> receipts = loadReceipts();
        receipts.add(receipt);
        saveReceipts(receipts);
    }

    public void setTarget(Target target) {
        try {
            File file = PathManager.getTargetPath().toFile();
            file.getParentFile().mkdirs(); // Crea la cartella se non esiste

            // Crea il file se non esiste
            if (!file.exists()) {
                file.createNewFile();
            }

            mapper.writeValue(file, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Target getTarget() {
        try {
            File file = PathManager.getTargetPath().toFile();
            file.getParentFile().mkdirs(); // Crea la cartella se non esiste

            if (!file.exists()) {
                return null;
            }

            return mapper.readValue(file, Target.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
