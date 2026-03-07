package koreatech.cse.service;

import koreatech.cse.domain.Curriculum;
import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.CurriculumStatus;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.dto.CurriculumRowDTO;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.repository.CurriculumMapper;
import koreatech.cse.repository.DivisionMapper;
import koreatech.cse.repository.UploadedFileMapper;
import koreatech.cse.repository.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer for Curriculum management.
 * Upload creates curriculum via {@link #publishCurriculumVersion}.
 * Update replaces file via {@link #updateCurriculum} (no upsert).
 */
@Service
public class CurriculumService {

    private static final Logger logger = LoggerFactory.getLogger(CurriculumService.class);

    @Inject
    private CurriculumMapper curriculumMapper;

    @Inject
    private FileService fileService;

    @Inject
    private UploadedFileMapper uploadedFileMapper;

    @Inject
    private UserMapper userMapper;

    @Inject
    private DivisionMapper divisionMapper;

    /**
     * Validates curriculum parameters.
     * 
     * @throws IllegalArgumentException on invalid input
     */
    public void validateCurriculumParams(int divisionId, int academicYear) {
        if (divisionId <= 0) {
            throw new IllegalArgumentException("Invalid division_id: " + divisionId + ". Must be > 0.");
        }
        if (academicYear <= 0) {
            throw new IllegalArgumentException("Invalid academic_year: " + academicYear + ". Must be > 0.");
        }
    }

    /**
     * Get active curriculum for a specific division and academic year.
     * Returns null if no active curriculum exists.
     */
    public Curriculum getActiveCurriculum(int divisionId, int academicYear) {
        validateCurriculumParams(divisionId, academicYear);
        return curriculumMapper.findActiveByDivisionYear(divisionId, academicYear);
    }

    /**
     * Build single-row view model for Upload/Update page.
     * Returns null if no ACTIVE curriculum exists.
     */
    public CurriculumRowDTO buildSingleCurriculumRow(int divisionId, int year) {

        if (divisionId <= 0 || year <= 0) {
            throw new IllegalArgumentException("Invalid divisionId or year.");
        }

        Division division = divisionMapper.findOne(divisionId);

        if (division == null) {
            return null; // فقط هنا يُسمح بإرجاع null
        }

        CurriculumRowDTO row = new CurriculumRowDTO();
        row.setDivisionId(division.getId());
        row.setDivisionName(division.getName());
        row.setAcademicYear(year);

        Curriculum curriculum = curriculumMapper.findActiveByDivisionYear(divisionId, year);

        if (curriculum != null) {

            row.setCurriculumId(curriculum.getId());
            row.setStatus(curriculum.getStatus().name());

            if (curriculum.getUploadedFileId() > 0) {

                UploadedFile file = uploadedFileMapper.findOne(curriculum.getUploadedFileId());

                if (file != null) {
                    row.setUploadedFileId(file.getId());
                    row.setUploadedFileName(file.getName());
                    row.setUploadedAt(file.getRegisteredDate());

                    if (file.getUploaderId() > 0) {

                        User uploader = userMapper.findOne(file.getUploaderId());

                        if (uploader != null) {
                            row.setUploaderName(uploader.getUsername());
                        }
                    }
                }
            }
        }

        return row;
    }

    /**
     * Get all active curriculums for a specific academic year (all divisions).
     */
    public List<Curriculum> getActiveCurriculumsByYear(int academicYear) {
        if (academicYear <= 0) {
            throw new IllegalArgumentException("Invalid academic_year: " + academicYear);
        }
        return curriculumMapper.findActiveByYear(academicYear);
    }

    /**
     * Get uploaded files for all ACTIVE curriculums in a given year.
     * Routes through the curriculum table so only files linked to an ACTIVE
     * curriculum row are returned.
     *
     * @param year academic year (must be &gt; 0)
     * @return list of {@link UploadedFile} objects for ACTIVE curriculums
     */
    public List<UploadedFile> getUploadedFilesForYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Invalid academic_year: " + year);
        }
        List<Curriculum> curriculums = curriculumMapper.findActiveByYear(year);
        List<UploadedFile> files = new ArrayList<>();
        for (Curriculum c : curriculums) {
            if (c.getUploadedFileId() > 0) {
                UploadedFile file = uploadedFileMapper.findOne(c.getUploadedFileId());
                if (file != null) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    /**
     * Build view-model rows for the admin Curriculum Management page.
     * Merges division list with active curriculum data and enriches each row
     * with file metadata and uploader name.
     *
     * @param divisions all divisions to display
     * @param year      the selected academic year
     * @return one {@link CurriculumRowDTO} per division
     */
    public List<CurriculumRowDTO> buildCurriculumRows(List<Division> divisions, int year) {
        List<Curriculum> curriculums = curriculumMapper.findActiveByYear(year);

        Map<Integer, Curriculum> curriculumMap = new HashMap<>();
        for (Curriculum c : curriculums) {
            curriculumMap.put(c.getDivisionId(), c);
        }

        List<CurriculumRowDTO> rows = new ArrayList<>();
        for (Division div : divisions) {
            CurriculumRowDTO row = new CurriculumRowDTO();
            row.setDivisionId(div.getId());
            row.setDivisionName(div.getName());
            row.setAcademicYear(year);

            Curriculum c = curriculumMap.get(div.getId());
            if (c != null) {
                row.setCurriculumId(c.getId());
                row.setStatus(c.getStatus() != null ? c.getStatus().name() : null);

                if (c.getUploadedFileId() > 0) {
                    UploadedFile file = uploadedFileMapper.findOne(c.getUploadedFileId());
                    if (file != null) {
                        row.setUploadedFileId(file.getId());
                        row.setUploadedFileName(file.getName());
                        row.setUploadedAt(file.getRegisteredDate());
                        if (file.getUploaderId() > 0) {
                            User uploader = userMapper.findOne(file.getUploaderId());
                            if (uploader != null) {
                                row.setUploaderName(uploader.getUsername());
                            }
                        }
                    }
                }
            }
            rows.add(row);
        }
        return rows;
    }

    /**
     * Check if file type is valid for curriculum uploads.
     */
    private boolean isValidCurriculumFileType(String ext) {
        if (ext == null || ext.isEmpty()) {
            return false;
        }
        String lowerExt = ext.toLowerCase();
        return lowerExt.equals("xls") || lowerExt.equals("xlsx") ||
                lowerExt.equals("doc") || lowerExt.equals("docx") ||
                lowerExt.equals("pdf");
    }

    // =========================================================================
    // Upload / Update — Public API
    // =========================================================================

    /**
     * Upload curriculum for a division/year with NO existing curriculum.
     *
     * @throws IllegalStateException    if a curriculum already exists (409
     *                                  Conflict)
     * @throws IllegalArgumentException if inputs are invalid
     * @throws RuntimeException         if file processing fails
     */
    @Transactional
    public Curriculum uploadCurriculum(MultipartFile file, User currentUser,
            int divisionId, int academicYear) {
        validateCurriculumParams(divisionId, academicYear);
        Curriculum existing = curriculumMapper.findActiveByDivisionYear(divisionId, academicYear);
        if (existing != null) {
            throw new IllegalStateException(
                    "Curriculum already exists for division=" + divisionId + ", year=" + academicYear);
        }
        return publishCurriculumVersion(file, currentUser, divisionId, academicYear);
    }

    /**
     * Update (replace file) for a division/year with an EXISTING curriculum.
     * Self-contained: fetches existing row, uploads new file, updates file link.
     * Does NOT insert or upsert curriculum. Does NOT call publishCurriculumVersion.
     *
     * @throws IllegalStateException    if no curriculum exists (404 Not Found)
     * @throws IllegalArgumentException if inputs are invalid
     * @throws RuntimeException         if file processing fails
     */
    @Transactional
    public Curriculum updateCurriculum(MultipartFile file, User currentUser,
            int divisionId, int academicYear) {
        validateCurriculumParams(divisionId, academicYear);

        Curriculum existing = curriculumMapper.findActiveByDivisionYear(divisionId, academicYear);
        if (existing == null) {
            throw new IllegalStateException(
                    "No curriculum exists for division=" + divisionId + ", year=" + academicYear);
        }

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Curriculum file is required and cannot be empty.");
        }
        if (currentUser == null) {
            throw new IllegalArgumentException(
                    "Current user is required for audit trail.");
        }

        String fileName = file.getOriginalFilename();
        String ext = fileService.getExtension(fileName);
        if (!isValidCurriculumFileType(ext)) {
            throw new IllegalArgumentException(
                    "Invalid file type: " + ext + ". Allowed: xls, xlsx, doc, docx, pdf.");
        }

        logger.info("updateCurriculum: BEGIN division={}, year={}, curriculumId={}, user={}",
                divisionId, academicYear, existing.getId(), currentUser.getId());

        UploadedFile uploadedFile;
        try {
            uploadedFile = fileService.processUploadedFile(
                    file, currentUser, Designation.curriculum, divisionId, 0, academicYear);
        } catch (IOException e) {
            logger.error("updateCurriculum: File upload IOException - division={}, year={}",
                    divisionId, academicYear, e);
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }

        // Capture for orphan tracking — file is on disk after processUploadedFile()
        String orphanFilePath = uploadedFile != null ? uploadedFile.getPath() : "unknown";
        int orphanFileId = uploadedFile != null ? uploadedFile.getId() : -1;

        if (uploadedFile == null) {
            logger.warn("updateCurriculum: ORPHAN_FILE_POSSIBLE - path={}, fileId={}, division={}, year={}, user={}",
                    orphanFilePath, orphanFileId, divisionId, academicYear, currentUser.getId());
            logger.error("updateCurriculum: File storage returned null - division={}, year={}",
                    divisionId, academicYear);
            throw new RuntimeException(
                    "Failed to store curriculum file. Transaction will rollback.");
        }

        int newFileId = uploadedFile.getId();
        if (newFileId <= 0) {
            logger.warn("updateCurriculum: ORPHAN_FILE_POSSIBLE - path={}, fileId={}, division={}, year={}, user={}",
                    orphanFilePath, orphanFileId, divisionId, academicYear, currentUser.getId());
            logger.error("updateCurriculum: Invalid uploaded_file_id={} - division={}, year={}",
                    newFileId, divisionId, academicYear);
            throw new RuntimeException(
                    "Uploaded file has invalid ID. Transaction will rollback.");
        }

        int updated = curriculumMapper.updateUploadedFileId(existing.getId(), newFileId);
        if (updated == 0) {
            logger.warn("updateCurriculum: ORPHAN_FILE_POSSIBLE - path={}, fileId={}, division={}, year={}, user={}",
                    orphanFilePath, orphanFileId, divisionId, academicYear, currentUser.getId());
            throw new IllegalStateException(
                    "Curriculum is no longer ACTIVE");
        }
        existing.setUploadedFileId(newFileId);

        logger.info(
                "updateCurriculum: SUCCESS - Curriculum updated (file replaced), division={}, year={}, curriculumId={}, newFileId={}",
                divisionId, academicYear, existing.getId(), newFileId);

        return existing;
    }

    // =========================================================================
    // Internal — shared publish implementation
    // =========================================================================

    /**
     * Internal: atomic file upload + curriculum INSERT (upsert).
     * Used by {@link #uploadCurriculum} only. Not called by updateCurriculum.
     *
     * @param file         The curriculum file
     * @param currentUser  The user performing the action
     * @param divisionId   Division ID (already validated)
     * @param academicYear Academic year (already validated)
     * @return The inserted ACTIVE Curriculum
     * @throws IllegalArgumentException if file is null/empty or type is invalid
     * @throws RuntimeException         if file processing fails
     */
    @Transactional
    private Curriculum publishCurriculumVersion(MultipartFile file, User currentUser,
            int divisionId, int academicYear) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Curriculum file is required and cannot be empty.");
        }
        if (currentUser == null) {
            throw new IllegalArgumentException(
                    "Current user is required for audit trail.");
        }

        String fileName = file.getOriginalFilename();
        String ext = fileService.getExtension(fileName);

        if (!isValidCurriculumFileType(ext)) {
            throw new IllegalArgumentException(
                    "Invalid file type: " + ext + ". Allowed: xls, xlsx, doc, docx, pdf.");
        }

        logger.info("publishCurriculumVersion: BEGIN division={}, year={}, user={}",
                divisionId, academicYear, currentUser.getId());

        // =====================================================================
        // STEP A: Upload File and Obtain uploaded_file_id (Direct Return)
        // =====================================================================

        UploadedFile uploadedFile;
        try {
            uploadedFile = fileService.processUploadedFile(
                    file, currentUser, Designation.curriculum, divisionId, 0, academicYear);
        } catch (IOException e) {
            logger.error("publishCurriculumVersion: File upload IOException - division={}, year={}",
                    divisionId, academicYear, e);
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }

        // Capture for orphan tracking — file is on disk after processUploadedFile()
        String orphanFilePath = uploadedFile != null ? uploadedFile.getPath() : "unknown";
        int orphanFileId = uploadedFile != null ? uploadedFile.getId() : -1;

        if (uploadedFile == null) {
            logger.warn("publishCurriculumVersion: ORPHAN_FILE_POSSIBLE - path={}, fileId={}, division={}, year={}, user={}",
                    orphanFilePath, orphanFileId, divisionId, academicYear, currentUser.getId());
            logger.error("publishCurriculumVersion: File storage returned null - division={}, year={}",
                    divisionId, academicYear);
            throw new RuntimeException(
                    "Failed to store curriculum file. Transaction will rollback.");
        }

        int uploadedFileId = uploadedFile.getId();

        if (uploadedFileId <= 0) {
            logger.warn("publishCurriculumVersion: ORPHAN_FILE_POSSIBLE - path={}, fileId={}, division={}, year={}, user={}",
                    orphanFilePath, orphanFileId, divisionId, academicYear, currentUser.getId());
            logger.error("publishCurriculumVersion: Invalid uploaded_file_id={} - division={}, year={}",
                    uploadedFileId, divisionId, academicYear);
            throw new RuntimeException(
                    "Uploaded file has invalid ID. Transaction will rollback.");
        }

        logger.info("publishCurriculumVersion: File uploaded successfully, uploadedFileId={}, divisionId={}",
                uploadedFileId, uploadedFile.getDivisionId());

        // =====================================================================
        // STEP B: Upsert Curriculum Row with status = ACTIVE
        // Uses ON DUPLICATE KEY UPDATE for UNIQUE(division_id, academic_year).
        // If no row exists: INSERT new ACTIVE curriculum.
        // If row exists: UPDATE uploaded_file_id, reset status to ACTIVE.
        // =====================================================================

        Curriculum curriculum = new Curriculum();
        curriculum.setDivisionId(divisionId);
        curriculum.setAcademicYear(academicYear);
        curriculum.setUploadedFileId(uploadedFileId);
        curriculum.setStatus(CurriculumStatus.ACTIVE);

        curriculumMapper.upsert(curriculum);

        logger.info("publishCurriculumVersion: Upserted curriculum for division={}, year={}, uploadedFileId={}",
                divisionId, academicYear, uploadedFileId);

        // =====================================================================
        // STEP D: Post-Condition Verification (non-fatal, observability only)
        // True invariants enforced by UNIQUE(division_id, academic_year) + SQL binding.
        // =====================================================================

        // INV-1: Exactly ONE ACTIVE should exist
        int activeCount = curriculumMapper.countActiveCurriculum(divisionId, academicYear);
        if (activeCount != 1) {
            logger.warn("publishCurriculumVersion: INV-1 WARNING - activeCount={}, expected=1, division={}, year={}",
                    activeCount, divisionId, academicYear);
        }

        // INV-2: ACTIVE curriculum should have valid uploaded_file_id
        if (curriculum.getUploadedFileId() <= 0) {
            logger.warn("publishCurriculumVersion: INV-2 WARNING - uploadedFileId={}, division={}, year={}",
                    curriculum.getUploadedFileId(), divisionId, academicYear);
        }

        // INV-3: Verify the database pointer was actually updated
        Curriculum verified = curriculumMapper.findActiveByDivisionYear(divisionId, academicYear);
        if (verified == null || verified.getUploadedFileId() != uploadedFileId) {
            logger.warn(
                    "publishCurriculumVersion: INV-3 WARNING - expected uploadedFileId={}, found={}, division={}, year={}",
                    uploadedFileId, verified != null ? verified.getUploadedFileId() : "null", divisionId, academicYear);
        }

        logger.info("publishCurriculumVersion: SUCCESS - division={}, year={}, curriculumId={}",
                divisionId, academicYear, curriculum.getId());

        return curriculum;
    }
}
