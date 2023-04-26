<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum.list.label.practicum-code" path="code"  width="20%"/>
	<acme:list-column code="company.practicum.list.label.title" path="title"  width="60%"/>
	<acme:list-column code="company.practicum.list.label.course-code" path="courseCode" width="20%"/>
</acme:list>

<acme:button code="company.practicum.list.button.create" action="/company/practicum/create"/>		
