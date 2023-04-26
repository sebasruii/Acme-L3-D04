<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum-session.list.label.title" path="title" width="60"/>	
	<acme:list-column code="company.practicum-session.list.label.start-period" path="startPeriod" width="20%"/>
	<acme:list-column code="company.practicum-session.list.label.end-period" path="endPeriod" width="20%"/>
</acme:list>

<acme:button test="${showCreate && exceptionalCreate}" code="company.practicum-session.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
<acme:button test="${showCreate && !exceptionalCreate}" code="company.practicum-session.list.button.create-exceptional" action="/company/practicum-session/create?masterId=${masterId}"/>