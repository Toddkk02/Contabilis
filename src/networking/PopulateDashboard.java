package networking;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Receipt;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class PopulateDashboard {

    public String getData() {
        StringBuilder sb = new StringBuilder();
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Receipt> receipts = mapper.readValue(Paths.get("receipts.json").toFile(), new TypeReference<List<Receipt>>() {}
            );
            for (Receipt receipt : receipts) {
                String getCateg = receipt.getCategory();
                String getAmount = String.valueOf(receipt.getAmount());
                String getDescr = receipt.getDescription();
                sb.append(getCateg);
                sb.append(getAmount);
                sb.append(getDescr);


            }
            System.out.println(sb.toString());
            return sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
