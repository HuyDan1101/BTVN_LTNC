package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileStorageTest {
  @Test
  void testFilePathHardcoded() throws IOException {
    // Cố tình dùng dấu gạch chéo ngược của Windows (Windows hardcoded path)
    String path = "target\\test-data\\my-file.txt";

    File file = new File(path);
    file.getParentFile().mkdirs();
    boolean created = file.createNewFile();

    assertTrue(file.exists(), "File should exist at: " + path);
  }
}