package settings;

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
            // Create parent directories if they don't exist
            file.getParentFile().mkdirs();
            mapper.writeValue(file, receipts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Receipt> loadReceipts() {
        try {
            File file = PathManager.getReceiptsPath().toFile();
            if (file.exists()) {
                return mapper.readValue(file,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Receipt.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void addReceipt(Receipt receipt) {
        List<Receipt> receipts = loadReceipts();
        receipts.add(receipt);
        saveReceipts(receipts);
    }
}