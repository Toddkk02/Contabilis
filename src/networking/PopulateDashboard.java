package networking;

import models.Receipt;
import settings.JsonManager;
import java.util.List;

public class PopulateDashboard {
    public String getData() {
        StringBuilder sb = new StringBuilder();
        try {
            JsonManager jsonManager = new JsonManager();
            List<Receipt> receipts = jsonManager.loadReceipts();

            for (Receipt receipt : receipts) {
                String getCateg = receipt.getCategory();
                String getAmount = String.valueOf(receipt.getAmount());
                String getDescr = receipt.getDescription();
                sb.append(getCateg);
                sb.append(getAmount);
                sb.append(getDescr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}