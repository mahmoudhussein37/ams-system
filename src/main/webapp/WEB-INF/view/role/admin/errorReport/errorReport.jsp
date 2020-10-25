<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>
<!--end::Head-->

<!--begin::Body-->
<body id="kt_body"  class="header-fixed header-mobile-fixed page-loading"  >
<%@include file="/WEB-INF/view/include/headerBar.jsp" %>
<!--begin::Content-->
<div class="content  d-flex flex-column flex-column-fluid" id="kt_content">
    <!--begin::Entry-->
    <div class="d-flex flex-column-fluid">
        <!--begin::Container-->
        <div class=" container ">
            <div class="row">
                <div class="col-md-12">
                    <!--begin::Card-->
                    <div class="card card-custom">
                        <div class="card-header">
                            <h3 class="card-title font-weight-bolder"><spring:message code="menu.admin.sub5_10"/></h3>

                        </div>
                        <div class="card-body">
                            <div class="table-div">
                                <table class="table table-head-custom table-vertical-center" id="course-list">
                                    <thead>
                                    <tr class="table-secondary text-center">
                                        <th class="pl-0" style=""><spring:message code="common.no"/></th>
                                        <th style=""><spring:message code="common.subject"/></th>
                                        <th style=""><spring:message code="admin.errorDetail"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="text-center">

                                        <td class="pl-0">
                                            1
                                        </td>
                                        <td class="pl-0">
                                            Menu doesn't working
                                        </td>
                                        <td>

                                            Hello i have a problem with page when i'm going on this page, it doesn't loading i have just "white screen" how can i fix that ? a fue day ago i haven't problems with that, i dont know why it doesn't works. I reinstalled mozilla, ( that page is just working at internet explorer, at Google Chrome and Microsoft Edge it doesn't works too ). I send a screen shot with that.
                                            Is it possible to speak about this in polish ? Any polish support is here ? Or even live chat in english ?
                                            I reinstalled mozilla, i did clear boot, i reset memory of mozilla. Please tell me how to fix that.

                                        </td>

                                    </tr>


                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                    <!--end::Card-->
                </div>
            </div>


        </div>
        <!--end::Container-->
    </div>
    <!--end::Entry-->
</div>
<!--end::Content-->

<%@include file="/WEB-INF/view/include/footerBar.jsp" %>


<%@include file="/WEB-INF/view/include/userPanel.jsp" %>


<%@include file="/WEB-INF/view/include/footerScript.jsp" %>

<script>
    $(document).ready(function() {
    });
</script>
</body>
</html>