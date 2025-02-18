package settings;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PathManager {
    private static final String RECEIPTS_FILENAME = "receipts.json";

    public static Path getReceiptsPath() {
        String userDir = System.getProperty("user.dir");
        return Paths.get(userDir, RECEIPTS_FILENAME);
    }

    public static ImageIcon getImageIcon(String imageName) {
        try {
            // Prima prova a caricare dalla directory delle risorse nel JAR
            InputStream is = PathManager.class.getResourceAsStream("/image/" + imageName);
            if (is != null) {
                byte[] imageBytes = is.readAllBytes();
                return new ImageIcon(imageBytes);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void ensureDirectoriesExist() {
        // Create receipts.json file if it doesn't exist
        File receiptsFile = getReceiptsPath().toFile();
        if (!receiptsFile.exists()) {
            try {
                receiptsFile.getParentFile().mkdirs();
                receiptsFile.createNewFile();
                // Initialize with empty array
                new ObjectMapper().writeValue(receiptsFile, new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}