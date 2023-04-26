<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.note.list.label.instantiation" path="instantiation" width="15%"/>
	<acme:list-column code="authenticated.note.list.label.author" path="author" width="15%"/>
	<acme:list-column code="authenticated.note.list.label.title" path="title" width="70%"/>
</acme:list>