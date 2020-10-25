<form class="form">
    <div class="card-body">
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.teachingMaterials.main"/></label>
                    <input type="text" class="form-control"/><br/>
                    <input type="text" class="form-control"/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.teachingMaterials.sub"/></label>
                    <input type="text" class="form-control"/><br/>
                    <input type="text" class="form-control"/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="common.reference"/></label>
                    <input type="text" class="form-control"/><br/>
                    <input type="text" class="form-control"/><br/>
                    <input type="text" class="form-control"/><br/>
                    <input type="text" class="form-control"/><br/>
                    <input type="text" class="form-control"/><br/>
                    <input type="text" class="form-control"/><br/>
                    <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label><spring:message code="professor.course.lectureMethod"/></label>
                    <table class="table table-head-custom table-vertical-center" id="course-list">
                        <thead>
                        <tr class="table-secondary text-center">
                            <th class="pl-0" style=""></th>
                            <th class="pl-0" style=""><spring:message code="common.no"/></th>
                            <th style=""><spring:message code="professor.methods"/></th>
                            <th style=""><spring:message code="professor.directInput"/></th>
                            <th style=""><spring:message code="professor.inputAvailable"/></th>
                            <th style=""></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="text-center">
                            <td class="pl-0">
                                <input type="checkbox" name=""/>
                            </td>
                            <td class="pl-0">
                                1
                            </td>
                            <td class="pl-0">
                                Theory Lecture
                            </td>
                            <td>

                                Text Input

                            </td>

                            <td>
                                Y
                            </td>
                        </tr>


                        </tbody>
                    </table>
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="form-group" style="text-align:right">
                    <button type="button" class="btn btn-primary mr-2">Save</button>
                </div>
            </div>
        </div>


    </div>

</form>