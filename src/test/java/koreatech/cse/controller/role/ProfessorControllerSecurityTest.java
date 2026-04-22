package koreatech.cse.controller.role;

import koreatech.cse.domain.dto.CounselingCreateRequest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ProfessorControllerSecurityTest {

    @Test
    public void professorCannotInjectUserIdViaCounselingForm() {
        java.lang.reflect.Field[] fields = CounselingCreateRequest.class.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            String name = field.getName();
            assertFalse(
                "CounselingCreateRequest must not expose server-controlled field: " + name,
                name.equals("userId") || name.equals("profUserId") || name.equals("id") || name.equals("number")
            );
        }
    }
}
