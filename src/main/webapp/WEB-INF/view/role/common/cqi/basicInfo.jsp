<table class="table table-bordered">
    <tr>
        <td>
            <spring:message code="common.year"/>
        </td>
        <td>
            ${pc.semester.year}
        </td>
        <td>
            <spring:message code="common.semester"/>
        </td>
        <td>
            ${pc.semester.semester}
        </td>
        <td>
            <spring:message code="common.divide"/>
        </td>
        <td>
            ${pc.divide}
        </td>
        <td>
            <spring:message code="common.professor"/>
        </td>
        <td>
            ${pc.professorUser.getFullName()}
        </td>

    </tr>
    <tr>
        <td>
            <spring:message code="common.courseTitle"/>
        </td>
        <td colspan="3">
            ${pc.course.title}
        </td>
        <td>
            <spring:message code="common.courseCode"/>
        </td>
        <td>
            ${pc.course.code}
        </td>
        <td>
            <spring:message code="professor.course.ltlp"/>
        </td>
        <td>
            ${pc.course.lec}-${pc.course.tut}-${pc.course.lab}-${pc.course.ws}
        </td>

    </tr>
    <tr>
        <td>
            <spring:message code="common.courseTarget"/>
        </td>
        <td>
            <spring:message code="common.department"/>
        </td>
        <td colspan="2">
            ${pc.course.division.name}
        </td>
        <td>
            <spring:message code="common.schoolYear"/>
        </td>
        <td>
            ${pc.schoolYear}
        </td>
        <td>
            <spring:message code="common.subjCategory"/>
        </td>
        <td>
            <spring:message code="subj.category.${pc.course.subjCategory}"/>
        </td>

    </tr>
    <tr>
        <%--                <td>
                            <spring:message code="common.compCategory"/>
                        </td>
                        <td>
                            <spring:message code="comp.category.${course.compCategory}"/>
                        </td>
                        <td>
                            <spring:message code="common.subjCategory"/>
                        </td>
                        <td>
                            <spring:message code="subj.category.${course.subjCategory}"/>
                        </td>--%>
        <td>
            <spring:message code="professor.engAccreditation"/>
        </td>
        <td colspan="3">
            ${pc.engAccreditation eq true ? 'Y' : 'N'}
        </td>
        <td colspan="4">
        </td>


    </tr>
</table>