<%@include file="/WEB-INF/view/include/topTag.jsp" %>
<%@include file="/WEB-INF/view/include/head.jsp" %>

<style>
    .modal-open .select2-container--open {z-index: 999999 !important;width:100% !important}
</style>
<div id ="modal-header" class="modal-header ">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="block">
    </h4>
</div>
<div class="modal-body">
    test
    <%--<div class="row">
        <div class="col-md-12">
            <div class="form-inline">
                <i class="fa fa-info-circle"></i>
                <c:choose>
                    <c:when test="${isKorean}">
                        사용예시<br/> 1) 입력단어 - "금오" &nbsp;&nbsp;2) '검색' 버튼 클릭 &nbsp;&nbsp; 3) 금오공과대학교 확인 및 소속기관명 클릭
                    </c:when>
                    <c:otherwise>
                        Example<br/> 1) Input - "Stanford" &nbsp;&nbsp;2) Click 'Search' button &nbsp;&nbsp; 3) Check and click the name of institution
                    </c:otherwise>
                </c:choose>

                <br/><br/>
            </div>
            <div class="form-inline globalSearchBar pulsate-target" style="width:100%">
                <fieldset>
                    <div class="input-group input-large" style="width:100% !important">
                        <input class="form-control" id="institutionQuery" type="text" placeholder="<spring:message code="user.searchInstitution"/>"/>
                        <span class="input-group-btn" style="width:1px">
				<button id="institutionSearch" class="btn blue" style="color:white !important" onClick="searchQ()">
                    <spring:message code="system.search"/> <i class="fa fa-search"></i>
                </button>
			</span>
                    </div>
                </fieldset>
            </div>
            <span class="alert-span" style="color:red"></span>
            <div id="institutionTableDisplay"></div>
        </div>
    </div>
    <div>
        <hr/>
        <button class="btn blue" id="cannot-find" style="width:100%"><i class="fa fa-question-circle"></i>&nbsp;<spring:message code="user.manualInstitution"/></button>
    </div>
    <div class="row input-manually-div">
        <br/>
        <div class="col-md-12">

            <form class="institution-form">
                <div class="row">
                    <div class="form-group">
                        <label class="control-label col-md-3">
                            <spring:message code="user.institution"/><br/>
                            <c:choose>
                                <c:when test="${isKorean}">
                                    (<spring:message code="system.english"/> <spring:message code="user.institution.full"/>)
                                </c:when>
                                <c:otherwise>
                                    (<spring:message code="user.institution.full"/>)
                                </c:otherwise>
                            </c:choose>
                        </label>
                        <div class="col-md-9">
                            <input class="form-control institution-name" name="name" type="text"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="control-label col-md-3"><spring:message code="user.institution"/><br/>(<spring:message code="user.institution.alias"/>)</label>
                        <div class="col-md-9">
                            <input class="form-control" name="synonym" style="margin-top:10px" type="text"/>
                        </div>
                    </div>
                </div>

                <c:if test="${isKorean}">
                    <div class="row">
                        <div class="form-group">
                            <label class="control-label col-md-3"><spring:message code="user.localInstitution"/> <span style="color:red">*</span></label>
                            <div class="col-md-9">
                                <input class="form-control institution-local-name" name="nameKor" style="margin-top:10px" type="text"/>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="row">
                    <div class="form-group">
                        <label class="control-label col-md-3"><spring:message code="system.homepage"/></label>
                        <div class="col-md-9">
                            <input class="form-control" name="domain" style="margin-top:10px" type="text"/>
                        </div>
                    </div>
                </div>
                <div class="row country-box">
                    <div class="form-group" style="margin-top:10px">
                        <label class="control-label col-md-3"><spring:message code="user.country"/> <span style="color:red">*</span></label>
                        <div class="col-md-9">
                            <select name="country" class="select2 form-control institution-country" >
                                <option></option>
                                <%@ include file="/WEB-INF/views/common/home/countries.jsp" %>
                            </select>
                        </div>
                    </div>
                </div>
                <div style="float:right">
                    <button class="btn blue add-institution" style="margin-top:10px" ><i class="fa fa-plus"></i> <spring:message code="system.add"/></button>
                </div>
            </form>
        </div>
    </div>--%>
</div>
