package koreatech.cse.domain.dto;

/**
 * DTO to hold the result statistics from Excel student upload processing.
 */
public class StudentUploadResult {
    private int insertedCount;
    private int skippedNotFoundCount;
    private int skippedDuplicateCount;
    private int invalidRowsCount;
    private int totalRowsProcessed;

    // Detailed invalid row tracking
    private int missingStudentNumberCount;
    private int missingFirstNameCount;
    private int missingLastNameCount;

    public StudentUploadResult() {
        this.insertedCount = 0;
        this.skippedNotFoundCount = 0;
        this.skippedDuplicateCount = 0;
        this.invalidRowsCount = 0;
        this.totalRowsProcessed = 0;
        this.missingStudentNumberCount = 0;
        this.missingFirstNameCount = 0;
        this.missingLastNameCount = 0;
    }

    public int getInsertedCount() {
        return insertedCount;
    }

    public void incrementInserted() {
        this.insertedCount++;
    }

    public int getSkippedNotFoundCount() {
        return skippedNotFoundCount;
    }

    public void incrementSkippedNotFound() {
        this.skippedNotFoundCount++;
    }

    public int getSkippedDuplicateCount() {
        return skippedDuplicateCount;
    }

    public void incrementSkippedDuplicate() {
        this.skippedDuplicateCount++;
    }

    public int getInvalidRowsCount() {
        return invalidRowsCount;
    }

    public void incrementInvalidRows() {
        this.invalidRowsCount++;
    }

    public void incrementMissingStudentNumber() {
        this.missingStudentNumberCount++;
        this.invalidRowsCount++;
    }

    public void incrementMissingFirstName() {
        this.missingFirstNameCount++;
        this.invalidRowsCount++;
    }

    public void incrementMissingLastName() {
        this.missingLastNameCount++;
        this.invalidRowsCount++;
    }

    public int getMissingStudentNumberCount() {
        return missingStudentNumberCount;
    }

    public int getMissingFirstNameCount() {
        return missingFirstNameCount;
    }

    public int getMissingLastNameCount() {
        return missingLastNameCount;
    }

    public int getTotalRowsProcessed() {
        return totalRowsProcessed;
    }

    public void incrementTotalRows() {
        this.totalRowsProcessed++;
    }

    public boolean hasInsertions() {
        return insertedCount > 0;
    }

    public boolean hasInvalidRows() {
        return invalidRowsCount > 0;
    }

    /**
     * Returns URL-encoded string with invalid row details
     */
    public String getInvalidDetails() {
        StringBuilder sb = new StringBuilder();
        if (missingStudentNumberCount > 0) {
            sb.append("sn=").append(missingStudentNumberCount);
        }
        if (missingFirstNameCount > 0) {
            if (sb.length() > 0)
                sb.append("&");
            sb.append("fn=").append(missingFirstNameCount);
        }
        if (missingLastNameCount > 0) {
            if (sb.length() > 0)
                sb.append("&");
            sb.append("ln=").append(missingLastNameCount);
        }
        return sb.toString();
    }

    public String getSummary() {
        return String.format(
                "Inserted: %d, Not Found: %d, Duplicates: %d, Invalid: %d (SN: %d, FN: %d, LN: %d), Total: %d",
                insertedCount, skippedNotFoundCount, skippedDuplicateCount, invalidRowsCount,
                missingStudentNumberCount, missingFirstNameCount, missingLastNameCount, totalRowsProcessed);
    }

    @Override
    public String toString() {
        return getSummary();
    }
}
