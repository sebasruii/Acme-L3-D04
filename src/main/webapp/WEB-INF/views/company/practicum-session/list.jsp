<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum-session.list.label.title" path="title" width="60"/>	
	<acme:list-column code="company.practicum-session.list.label.start-date" path="startDate" width="20%"/>
	<acme:list-column code="company.practicum-session.list.label.finish-date" path="finishDate" width="20%"/>
</acme:list>

<acme:button test="${showCreate}" code="company.practicum-session.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
<acme:button test="${exceptionalCreate}" code="company.practicum-session.list.button.create-exceptional" action="/company/practicum-session/create?masterId=${masterId}"/>