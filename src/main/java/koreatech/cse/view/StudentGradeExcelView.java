package koreatech.cse.view;

import koreatech.cse.domain.role.student.StudentCourse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class StudentGradeExcelView extends AbstractExcelView {
    private final int ROW_START_INDEX = 0;
    private final int CELL_START_INDEX = 0;

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        int ROW_INDEX = ROW_START_INDEX;
        int CELL_INDEX = CELL_START_INDEX;
        CellStyle cellCenter = workbook.createCellStyle();
        cellCenter.setAlignment(CellStyle.ALIGN_CENTER);

        List<StudentCourse> studentCourses = (List<StudentCourse>) map.get("studentCourses");
        MessageSource messageSource = (MessageSource) map.get("messageSource");
        Locale locale = (Locale) map.get("locale");
        ArrayList<String> columnTitles = new ArrayList<>();
        columnTitles.add(messageSource.getMessage("common.no", null, locale));
        columnTitles.add(messageSource.getMessage("common.studentNumber", null, locale));
        columnTitles.add(messageSource.getMessage("common.name", null, locale));


        HSSFSheet sheet = (HSSFSheet) workbook.createSheet();
        HSSFRow currentRow = sheet.createRow(ROW_INDEX);
        for (String columnTitle : columnTitles) {
            Cell currentRowCell = currentRow.createCell(CELL_INDEX);
            currentRowCell.setCellValue(columnTitle);
            currentRowCell.setCellStyle(cellCenter);
            CELL_INDEX++;
        }
        ROW_INDEX++;

        int count = 1;
        for(StudentCourse sc: studentCourses) {


            CELL_INDEX = CELL_START_INDEX;
            currentRow = sheet.createRow(ROW_INDEX);
            List<String> strings = new ArrayList<>();
            strings.add(Integer.toString(count++));
            strings.add(sc.getStudentUser().getNumber());
            strings.add(sc.getStudentUser().getFullName());


            for (String string : strings) {
                Cell currentRowCell = currentRow.createCell(CELL_INDEX);
                currentRowCell.setCellValue(string == null ? "" : string);
                currentRowCell.setCellStyle(cellCenter);

                if(ROW_INDEX == studentCourses.size() - 1 && string != null)
                    sheet.autoSizeColumn(CELL_INDEX);

                CELL_INDEX++;
            }
            ROW_INDEX++;
        }
    }
}
