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
	<acme:message code="lecturer.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.number-theory-lectures"/>
		</th>
		<td>
			<acme:print value="${statisticsByLectureType.get('THEORY').getCount()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.number-hands-on-lectures"/>
		</th>
		<td>
			<acme:print value="${statisticsByLectureType.get('HANDS_ON').getCount()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.average-learning-time-lectures"/>
		</th>
		<td>
			<acme:print value="${statisticsByLectureType.get('GENERAL').getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.max-learning-time-lectures"/>
		</th>
		<td>
			<acme:print value="${statisticsByLectureType.get('GENERAL').getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.min-learning-time-lectures"/>
		</th>
		<td>
			<acme:print value="${statisticsByLectureType.get('GENERAL').getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.stdev-learning-time-lectures"/>
		</th>
		<td>
			<acme:print value="${statisticsByLectureType.get('GENERAL').getStDev()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.average-learning-time-courses"/>
		</th>
		<td>
			<acme:print value="${statisticsByCourse.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.max-learning-time-courses"/>
		</th>
		<td>
			<acme:print value="${statisticsByCourse.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.min-learning-time-courses"/>
		</th>
		<td>
			<acme:print value="${statisticsByCourse.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.stdev-learning-time-courses"/>
		</th>
		<td>
			<acme:print value="${statisticsByCourse.getStDev()}"/>
		</td>
	</tr>

</table>

<acme:return/>

