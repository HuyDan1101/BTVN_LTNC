package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileStorageTest {
  @Test
  void testFilePathRefactored() throws IOException {
    // Cách 1: Sử dụng Paths.get (Tự động xử lý dấu gạch theo OS)
    Path path = Paths.get("target", "test-data", "my-file-v2.txt");

    // Cách 2: Sử dụng File.separator
    // String path = "target" + File.separator + "test-data" + File.separator + "my-file.txt";

    File file = path.toFile();
    file.getParentFile().mkdirs();
    boolean created = file.createNewFile();

    assertTrue(file.exists(), "File should exist on any OS!");
  }
}