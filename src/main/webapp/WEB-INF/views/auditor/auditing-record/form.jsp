<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<acme:form>
	<acme:input-textbox code="auditor.auditingRecord.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditingRecord.form.label.startDate" path="startDate" />
	<acme:input-moment code="auditor.auditingRecord.form.label.finishDate" path="finishDate"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.mark" path="mark"/>
	<acme:input-url code="auditor.auditingRecord.form.label.link" path="link"/>
	<jstl:if test="${_command == 'correct'}">
		<acme:message code="auditor.auditingRecord.message.correction.disclaimer"/>
		<acme:input-checkbox code="auditor.auditingRecord.label.confirm" path="confirm"/>
	</jstl:if>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="auditor.auditingRecord.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditingRecord.form.button.delete" action="/auditor/auditing-record/delete"/>
			<acme:submit code="auditor.auditingRecord.form.button.publish" action="/auditor/auditing-record/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditingRecord.form.button.create" action="/auditor/auditing-record/create?auditId=${auditId}"/>
		</jstl:when>		
		<jstl:when test="${_command == 'correct'}">
			<acme:submit code="auditor.auditingRecord.form.button.correct" action="/auditor/auditing-record/correct?auditId=${auditId}"/>
		</jstl:when>
	</jstl:choose>	
</acme:form>