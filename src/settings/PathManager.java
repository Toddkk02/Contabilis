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
    private static final String TARGET_FILENAME = "target.json";

    public static Path getReceiptsPath() {
        String userDir = System.getProperty("user.dir");
        return Paths.get(userDir, RECEIPTS_FILENAME);
    }

    public static Path getTargetPath() {
        String userDir = System.getProperty("user.dir");
        return Paths.get(userDir, TARGET_FILENAME);
    }

    public static ImageIcon getImageIcon(String imageName) {
        try {
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
        ObjectMapper mapper = new ObjectMapper();

        // Create receipts.json file if it doesn't exist
        File receiptsFile = getReceiptsPath().toFile();
        if (!receiptsFile.exists() || receiptsFile.length() == 0) {
            try {
                receiptsFile.getParentFile().mkdirs();
                // Initialize with a valid JSON array
                mapper.writeValue(receiptsFile, new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create target.json file if it doesn't exist
        File targetFile = getTargetPath().toFile();
        if (!targetFile.exists() || targetFile.length() == 0) {
            try {
                targetFile.getParentFile().mkdirs();
                // Initialize with null but in valid JSON format
                mapper.writeValue(targetFile, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}