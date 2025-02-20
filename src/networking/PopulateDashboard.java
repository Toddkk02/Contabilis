package networking;

import models.Receipt;
import settings.JsonManager;
import java.util.List;

public class PopulateDashboard {
    public String getData() {
        StringBuilder sb = new StringBuilder();
        try {
            JsonManager jsonManager = new JsonManager();
            List<Receipt> receipts = jsonManager.getReceipts();  // Using the new getReceipts method

            for (Receipt receipt : receipts) {
                String getCateg = receipt.getCategory();
                String getAmount = String.valueOf(receipt.getAmount());
                String getDescr = receipt.getDescription();
                String getDate = receipt.getFormattedDate();  // Using the formatted date method

                sb.append(getCateg).append(",");
                sb.append(getAmount).append(",");
                sb.append(getDescr).append(",");
                sb.append(getDate).append(";");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}