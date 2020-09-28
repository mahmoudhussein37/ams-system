<%@include file="/WEB-INF/view/include/top-tag.jsp" %>
<h3 class="font-size-lg text-dark font-weight-bold mb-6">Information</h3>
<div class="row">
    <div class="col-md-6">

        <div class="form-group">
            <label>Student number</label>
            <input type="text" class="form-control" value="${studentUser.number}" disabled/>
            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
        </div>


        <div class="form-group">
            <label>Division</label>
            <input type="text" class="form-control" value="${user.division.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>




    </div>
    <div class="col-md-6">
        <div class="form-group">
            <label>Name</label>
            <input type="text" class="form-control"  value="${studentUser.getFullName()}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>
        <div class="form-group">
            <label>Major</label>
            <input type="text" class="form-control" value="${user.major.name}" disabled/>
            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
        </div>
    </div>
</div>



<ul class="nav nav-tabs nav-tabs-line">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1">Profile</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2">Course history</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3">Language</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_4">Certificate</a>
    </li>

</ul>
<div class="tab-content mt-5" id="myTabContent">
    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_2">
        <form class="form">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h3 class="font-size-lg text-dark font-weight-bold mb-6">Personal Info</h3>
                        <div class="form-group">
                            <label>Cell phone</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                        </div>


                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>

                        <div class="form-group">
                            <label>Post</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                        <div class="form-group">
                            <label>Address</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>


                        <div class="separator separator-solid my-5"></div>
                        <h3 class="font-size-lg text-dark font-weight-bold mb-6">Parents Info</h3>
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                        </div>


                        <div class="form-group">
                            <label>Relation</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>

                        <div class="form-group">
                            <label>Cell phone</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                        <div class="form-group">
                            <label>Post</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                        <div class="form-group">
                            <label>Address</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <h3 class="font-size-lg text-dark font-weight-bold mb-6">Admission Information</h3>
                        <div class="form-group">
                            <label>Admission Year</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                        </div>


                        <div class="form-group">
                            <label>Admission Date</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>

                        <div class="form-group">
                            <label>High School</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>

                        <div class="separator separator-solid my-5"></div>
                        <h3 class="font-size-lg text-dark font-weight-bold mb-6">Graduation Information</h3>
                        <div class="form-group">
                            <label>Graduation Date</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">Please enter your full name</span>--%>
                        </div>


                        <div class="form-group">
                            <label>Graduation Semester</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>

                        <div class="form-group">
                            <label>Graduation Degree</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                        <div class="form-group">
                            <label>Degree number</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                        <div class="form-group">
                            <label>Certificate number</label>
                            <input type="text" class="form-control" disabled/>
                            <%--<span class="form-text text-muted">We'll never share your email with anyone else</span>--%>
                        </div>
                    </div>
                </div>

            </div>
            <%--<div class="card-footer">
                <button type="reset" class="btn btn-primary mr-2">Submit</button>
                <button type="reset" class="btn btn-secondary">Cancel</button>
            </div>--%>
        </form>



    </div>
    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">Tab content 2</div>
    <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">Tab content 4</div>
    <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">Tab content 5</div>
</div>



<%@include file="/WEB-INF/view/include/footer-script.jsp" %>
<script>

</script>