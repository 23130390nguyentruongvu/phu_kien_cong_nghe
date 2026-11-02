package vn.edu.hcmuaf.fit.pkcn.controller.admin.user;

import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public final class AdminUserAvatarStorage {
    private static final String AVATAR_URL_PREFIX = "/admin-user-avatars/";
    private static final Path STORAGE_DIRECTORY = Path.of(
            System.getProperty("user.home"), "pkcn-uploads", "admin-users"
    ).toAbsolutePath().normalize();

    private AdminUserAvatarStorage() {
    }

    public static String save(Part image, String contextPath) throws IOException {
        if (image == null || image.getSize() == 0) {
            return null;
        }
        if (image.getSize() > 5L * 1024 * 1024) {
            throw new IOException("Ảnh đại diện không được vượt quá 5 MB.");
        }

        String extension = detectImageExtension(image);
        Files.createDirectories(STORAGE_DIRECTORY);
        String fileName = UUID.randomUUID() + extension;
        Path destination = STORAGE_DIRECTORY.resolve(fileName);
        try {
            try (InputStream input = image.getInputStream()) {
                Files.copy(input, destination);
            }
        } catch (IOException e) {
            try {
                Files.deleteIfExists(destination);
            } catch (IOException cleanupException) {
                e.addSuppressed(cleanupException);
            }
            throw e;
        }
        return contextPath + AVATAR_URL_PREFIX + fileName;
    }

    public static Path findStoredAvatar(String fileName) {
        if (fileName == null || !fileName.matches("[0-9a-fA-F-]{36}\\.(jpg|png|gif)")) {
            return null;
        }
        Path candidate = STORAGE_DIRECTORY.resolve(fileName).normalize();
        return candidate.startsWith(STORAGE_DIRECTORY) ? candidate : null;
    }

    public static void deleteIfManaged(String avatarUrl) throws IOException {
        if (avatarUrl == null) {
            return;
        }
        int prefixIndex = avatarUrl.lastIndexOf(AVATAR_URL_PREFIX);
        if (prefixIndex < 0) {
            return;
        }
        Path file = findStoredAvatar(avatarUrl.substring(prefixIndex + AVATAR_URL_PREFIX.length()));
        if (file != null) {
            Files.deleteIfExists(file);
        }
    }

    private static String detectImageExtension(Part image) throws IOException {
        byte[] header = new byte[8];
        int bytesRead;
        try (InputStream input = image.getInputStream()) {
            bytesRead = input.readNBytes(header, 0, header.length);
        }
        if (bytesRead >= 3
                && (header[0] & 0xff) == 0xff
                && (header[1] & 0xff) == 0xd8
                && (header[2] & 0xff) == 0xff) {
            return ".jpg";
        }
        if (bytesRead == 8
                && (header[0] & 0xff) == 0x89
                && header[1] == 'P' && header[2] == 'N' && header[3] == 'G'
                && header[4] == 0x0d && header[5] == 0x0a
                && header[6] == 0x1a && header[7] == 0x0a) {
            return ".png";
        }
        if (bytesRead >= 6
                && header[0] == 'G' && header[1] == 'I'
                && header[2] == 'F' && header[3] == '8'
                && (header[4] == '7' || header[4] == '9') && header[5] == 'a') {
            return ".gif";
        }
        throw new IOException("Tệp tải lên không phải ảnh JPEG, PNG hoặc GIF hợp lệ.");
    }
}
