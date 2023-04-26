<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="auditor.auditingrecord.label.subject" path="subject" width="20%"/>
	<acme:list-column code="auditor.auditingrecord.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.auditingrecord.label.draft" path="draft" width="20%"/>
</acme:list>