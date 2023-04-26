<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="audit.label.code" path="code" width="30%"/>
	<acme:list-column code="audit.label.conclusion" path="conclusion" width="30%"/>
	<acme:list-column code="audit.label.auditor" path="auditor" width="30%"/>
</acme:list>