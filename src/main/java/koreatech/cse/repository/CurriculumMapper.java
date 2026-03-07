package koreatech.cse.repository;

import koreatech.cse.domain.Curriculum;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis mapper for Curriculum domain.
 * Manages curriculum records with a UNIQUE(division_id, academic_year)
 * constraint;
 * mutations go through {@link #upsert(Curriculum)} exclusively.
 */
@Repository
public interface CurriculumMapper {

        /**
         * Find active curriculum for a specific division and academic year.
         * Returns null if no active curriculum exists.
         */
        @Select("SELECT c.*, uf.name as file_name, uf.path as file_path, d.name as division_name " +
                        "FROM `curriculum` c " +
                        "LEFT JOIN `uploaded_file` uf ON c.uploaded_file_id = uf.id " +
                        "LEFT JOIN `division` d ON c.division_id = d.id " +
                        "WHERE c.division_id = #{divisionId} " +
                        "AND c.academic_year = #{academicYear} " +
                        "AND c.status = 'ACTIVE'")
        @Results({
                        @Result(property = "divisionName", column = "division_name")
        })
        Curriculum findActiveByDivisionYear(@Param("divisionId") int divisionId,
                        @Param("academicYear") int academicYear);

        /**
         * Find all active curriculums for a specific academic year (all divisions).
         */
        @Select("SELECT c.*, d.name as division_name " +
                        "FROM `curriculum` c " +
                        "LEFT JOIN `division` d ON c.division_id = d.id " +
                        "WHERE c.academic_year = #{academicYear} " +
                        "AND c.status = 'ACTIVE' " +
                        "ORDER BY d.name")
        @Results({
                        @Result(property = "divisionName", column = "division_name")
        })
        List<Curriculum> findActiveByYear(@Param("academicYear") int academicYear);

        /**
         * Count ACTIVE curricula for a division and year.
         * Contract INV-1: Result must be 0 or 1. Result > 1 indicates violation.
         * Used for invariant verification, not business logic.
         * 
         * @return Number of ACTIVE curriculum rows (expected: 0 or 1)
         */
        @Select("SELECT COUNT(*) FROM `curriculum` " +
                        "WHERE `division_id` = #{divisionId} " +
                        "AND `academic_year` = #{academicYear} " +
                        "AND `status` = 'ACTIVE'")
        int countActiveCurriculum(@Param("divisionId") int divisionId, @Param("academicYear") int academicYear);

        /**
         * Upsert a curriculum record: INSERT if no row exists for (division_id,
         * academic_year),
         * or UPDATE the existing row's uploaded_file_id and status if one does.
         * Respects the UNIQUE(division_id, academic_year) constraint.
         *
         * On INSERT: creates a new ACTIVE curriculum.
         * On UPDATE (duplicate key): updates uploaded_file_id, resets status to ACTIVE,
         * clears archived_at, refreshes updated_at.
         */
        @Insert("INSERT INTO `curriculum` (" +
                        "`division_id`, `academic_year`, `uploaded_file_id`, `status`, `updated_at`" +
                        ") VALUES (" +
                        "#{divisionId}, #{academicYear}, #{uploadedFileId}, 'ACTIVE', NOW()" +
                        ") ON DUPLICATE KEY UPDATE " +
                        "`uploaded_file_id` = #{uploadedFileId}, " +
                        "`status` = 'ACTIVE', " +
                        "`archived_at` = NULL, " +
                        "`updated_at` = NOW()")
        void upsert(Curriculum curriculum);

        @Update("UPDATE `curriculum` SET " +
                        "`uploaded_file_id` = #{uploadedFileId}, " +
                        "`updated_at` = NOW() " +
                        "WHERE `id` = #{id} AND `status` = 'ACTIVE'")
        int updateUploadedFileId(@Param("id") int id,
                        @Param("uploadedFileId") int uploadedFileId);
}