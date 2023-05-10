<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h3>
	<acme:message code="company.dashboard.form.title.num-practicum-per-month"/>
</h3>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.january"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('JANUARY')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.february"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('FEBRUARY')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.march"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('MARCH')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.april"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('APRIL')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.may"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('MAY')}"/>
		</td>	
		<th scope="row">
			<acme:message code="company.dashboard.form.label.june"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('JUNE')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.july"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('JULY')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.august"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('AUGUST')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.september"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('SEPTEMBER')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.october"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('OCTOBER')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.november"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('NOVEMBER')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.december"/>
		</th>
		<td>
			<acme:print value="${numPracticumPerMonth.get('DECEMBER')}"/>
		</td>
				
	</tr>
</table>
<h3>
	<acme:message code="company.dashboard.form.title.practicum-statistics"/>
</h3>

<h3>
	
</h3>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.count"/>
		</th>
		<td>
			<acme:print value="${practicumStatistics.count}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.average"/>
		</th>
		<td>
			<acme:print value="${practicumStatistics.average}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.stDev"/>
		</th>
		<td>
			<acme:print value="${practicumStatistics.stDev}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.minimum"/>
		</th>
		<td>
			<acme:print value="${practicumStatistics.minimum}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.maximum"/>
		</th>
		<td>
			<acme:print value="${practicumStatistics.maximum}"/>
		</td>
		
	</tr>
</table>

<h3>
	<acme:message code="company.dashboard.form.title.session-statistics"/>
</h3>

<h3>
	
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.count"/>
		</th>
		<td>
			<acme:print value="${practicumSessionStatistics.count}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.average"/>
		</th>
		<td>
			<acme:print value="${practicumSessionStatistics.average}"/>
		</td>
				<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.stDev"/>
		</th>
		<td>
			<acme:print value="${practicumSessionStatistics.stDev}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.minimum"/>
		</th>
		<td>
			<acme:print value="${practicumSessionStatistics.minimum}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.maximum"/>
		</th>
		<td>
			<acme:print value="${practicumSessionStatistics.maximum}"/>
		</td>

	</tr>
</table>




<acme:return/>