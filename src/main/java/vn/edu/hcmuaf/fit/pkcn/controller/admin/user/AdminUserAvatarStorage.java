package vn.edu.hcmuaf.fit.pkcn.controller.admin.user;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public final class AdminUserAvatarStorage {
    private static final String PROJECT_AVATAR_URL_PREFIX = "/images/user/";
    private static final String LEGACY_AVATAR_URL_PREFIX = "/admin-user-avatars/";
    private static final Path LEGACY_STORAGE_DIRECTORY = Path.of(
            System.getProperty("user.home"), "pkcn-uploads", "admin-users"
    ).toAbsolutePath().normalize();

    private AdminUserAvatarStorage() {
    }

    public static String save(Part image, ServletContext servletContext, String contextPath) throws IOException {
        if (image == null || image.getSize() == 0) {
            return null;
        }
        if (image.getSize() > 5L * 1024 * 1024) {
            throw new IOException("Ảnh đại diện không được vượt quá 5 MB.");
        }

        String extension = detectImageExtension(image);
        Path storageDirectory = getProjectStorageDirectory(servletContext);
        Files.createDirectories(storageDirectory);
        String fileName = UUID.randomUUID() + extension;
        Path destination = storageDirectory.resolve(fileName);
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
        return contextPath + PROJECT_AVATAR_URL_PREFIX + fileName;
    }

    public static Path findStoredAvatar(String fileName) {
        if (!isValidFileName(fileName)) {
            return null;
        }
        Path candidate = LEGACY_STORAGE_DIRECTORY.resolve(fileName).normalize();
        return candidate.startsWith(LEGACY_STORAGE_DIRECTORY) ? candidate : null;
    }

    public static void deleteIfManaged(String avatarUrl, ServletContext servletContext) throws IOException {
        if (avatarUrl == null) {
            return;
        }

        int projectPrefixIndex = avatarUrl.lastIndexOf(PROJECT_AVATAR_URL_PREFIX);
        if (projectPrefixIndex >= 0) {
            String fileName = avatarUrl.substring(projectPrefixIndex + PROJECT_AVATAR_URL_PREFIX.length());
            Path file = findProjectAvatar(fileName, servletContext);
            if (file != null) {
                Files.deleteIfExists(file);
            }
            return;
        }

        int legacyPrefixIndex = avatarUrl.lastIndexOf(LEGACY_AVATAR_URL_PREFIX);
        if (legacyPrefixIndex < 0) {
            return;
        }
        Path file = findStoredAvatar(
                avatarUrl.substring(legacyPrefixIndex + LEGACY_AVATAR_URL_PREFIX.length())
        );
        if (file != null) {
            Files.deleteIfExists(file);
        }
    }

    private static Path findProjectAvatar(String fileName, ServletContext servletContext) throws IOException {
        if (!isValidFileName(fileName)) {
            return null;
        }
        Path storageDirectory = getProjectStorageDirectory(servletContext);
        Path candidate = storageDirectory.resolve(fileName).normalize();
        return candidate.startsWith(storageDirectory) ? candidate : null;
    }

    private static Path getProjectStorageDirectory(ServletContext servletContext) throws IOException {
        String realPath = servletContext.getRealPath(PROJECT_AVATAR_URL_PREFIX);
        if (realPath == null) {
            throw new IOException("Không xác định được thư mục lưu avatar trong ứng dụng.");
        }
        return Path.of(realPath).toAbsolutePath().normalize();
    }

    private static boolean isValidFileName(String fileName) {
        return fileName != null && fileName.matches("[0-9a-fA-F-]{36}\\.(jpg|png|gif)");
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
