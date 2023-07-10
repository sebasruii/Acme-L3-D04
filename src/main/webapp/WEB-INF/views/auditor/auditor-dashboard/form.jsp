<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-number-of-theory-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTheoryAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-number-of-hands-on-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfHandsOnAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.average-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditingRecordStatistics.average}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviation-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditingRecordStatistics.stDev}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximum-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditingRecordStatistics.maximum}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimum-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditingRecordStatistics.minimum}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.average-time-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${timeStatistics.average}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.time-deviation-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${timeStatistics.stDev}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimum-time-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${timeStatistics.minimum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximum-time-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${timeStatistics.maximum}"/>
		</td>
	</tr>		
</table>

<acme:return/>