
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="company.practicum-session.form.label.title" path="title"/>
	<acme:input-textbox code="company.practicum-session.form.label.abstract$" path="abstract$"/>
	<acme:input-moment code="company.practicum-session.form.label.start-period" path="startPeriod"/>
	<acme:input-moment code="company.practicum-session.form.label.end-period" path="endPeriod"/>
	<acme:input-url code="company.practicum-session.form.label.further-information-link" path="furtherInformationLink"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.practicum-session.form.button.update" action="/company/practicum-session/update"/>
			<acme:submit code="company.practicum-session.form.button.delete" action="/company/practicum-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == true}">
			<acme:submit code="company.practicum-session.form.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == false}">
			<acme:input-checkbox code="company.practicum-session.form.label.confirmation" path="confirmation"/>
			<acme:submit code="company.practicum-session.form.button.create-exceptional" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>
