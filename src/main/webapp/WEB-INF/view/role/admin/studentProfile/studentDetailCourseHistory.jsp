<div class="card-body">
    <div class="row">
        <div class="col-md-12">
            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.curriculumStatus"/></h3>
            <table class="table">
                <tr>
                    <td>
                        <spring:message code="common.classification"/>
                    </td>
                    <td>
                        <spring:message code="common.criteria"/>
                    </td>
                    <td>
                        <spring:message code="common.acquire"/>
                    </td>
                    <td>
                        <spring:message code="common.result"/>
                    </td>
                </tr>
                <tr class="table-light">
                    <td class="table-light"><spring:message code="common.liberal"/></td>
                    <%--<td><spring:message code="common.require"/></td>--%>
                    <td>${graduationCriteria.liberal}</td>
                    <td>${liberalCount}</td>
                    <td style="${(liberalCount - graduationCriteria.liberal) < 0 ? 'color:red' : ''}">${liberalCount - graduationCriteria.liberal}</td>
                </tr>

                <tr>
                    <td >MSC</td>
                    <%--<td><spring:message code="common.require"/></td>--%>
                    <td>${graduationCriteria.msc}</td>
                    <td>${mscCount}</td>
                    <td style="${(mscCount - graduationCriteria.msc) < 0 ? 'color:red' : ''}">${mscCount - graduationCriteria.msc}</td>
                </tr>

                <tr class="table-light">
                    <td class="table-light"><spring:message code="common.major"/></td>
                    <%--<td><spring:message code="common.require"/></td>--%>
                    <td>${graduationCriteria.major}</td>
                    <td>${majorCount}</td>
                    <td style="${(majorCount - graduationCriteria.major) < 0 ? 'color:red' : ''}">${majorCount - graduationCriteria.major}</td>
                </tr>



            </table>


            <br/>
            <div class="separator separator-solid my-5"></div>
            <br/>

            <h3 class="font-size-lg text-dark font-weight-bold mb-6"><spring:message code="common.courseHistory"/></h3>


            <table class="table">
                <thead>
                <tr>
                    <th>
                        <spring:message code="common.no"/>
                    </th>
                    <th>
                        <spring:message code="common.year"/>
                    </th>
                    <th>
                        <spring:message code="common.semester"/>
                    </th>
                    <th>
                        <spring:message code="common.courseCode"/>
                    </th>
                    <th>
                        <spring:message code="common.courseTitle"/>
                    </th>
                    <th>
                        <spring:message code="common.category"/>
                    </th>
                    <th>
                        <spring:message code="common.credit"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sc" items="${studentCourses}" varStatus="varStatus">
                    <tr>
                        <td>
${varStatus.count}
                        </td>
                        <td>
${sc.professorCourse.semester.year}
                        </td>
                        <td>
                                ${sc.professorCourse.semester.semester}
                        </td>
                        <td>
                                ${sc.course.code}
                        </td>
                        <td>
                                ${sc.course.title}
                        </td>
                        <td>
                            <spring:message code="subj.category.${sc.course.subjCategory}"/>
                        </td>
                        <td>
                                ${sc.course.credit}
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>

                    </td>
                    <td>

                    </td>
                    <td>

                    </td>
                    <td>

                    </td>
                    <td>

                    </td>
                    <td>

                    </td>
                    <td>

                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>