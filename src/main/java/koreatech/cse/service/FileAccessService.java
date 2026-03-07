package koreatech.cse.service;

import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class FileAccessService {

    public void assertUserCanAccessFile(User currentUser, UploadedFile file) {
        if (currentUser == null || file == null) {
            throw new AccessDeniedException("Access denied.");
        }

        boolean isAdmin = currentUser.hasRole(Role.admin);
        String designation = file.getDesignation();

        if ("curriculum".equals(designation)) {
            if (!isAdmin) {
                throw new AccessDeniedException("Access denied.");
            }
        } else if ("profile".equals(designation)) {
            if (!isAdmin && file.getUploaderId() != currentUser.getId()) {
                throw new AccessDeniedException("Access denied.");
            }
        } else if ("student_registration".equals(designation)) {
            if (!isAdmin && file.getUploaderId() != currentUser.getId()) {
                throw new AccessDeniedException("Access denied.");
            }
        } else if ("attendance".equals(designation)) {
            if (!isAdmin && file.getUploaderId() != currentUser.getId()) {
                throw new AccessDeniedException("Access denied.");
            }
        } else {
            throw new AccessDeniedException("Access denied.");
        }
    }
}
