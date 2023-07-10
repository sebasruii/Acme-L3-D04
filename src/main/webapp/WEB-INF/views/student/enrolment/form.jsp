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
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <acme:form>
 			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
 			<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
 			<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
 			<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
 			

	<jstl:if test="${_command != 'create'}">
		<acme:input-textbox code="student.enrolment.form.label.estimatedTotalTime" path="estimatedTotalTime" readonly="true"/>	
		<br>	
		<h3>
			<acme:message code="student.enrolment.form.title.finalize"/>
		</h3>	
			<acme:input-textbox code="student.enrolment.form.label.cardHolderName" path="cardHolderName"/>	
			<jstl:if test="${draftMode}">
				<acme:input-textbox code="student.enrolment.form.label.creditCard" path="creditCard" placeholder="XXXX/XXXX/XXXX/XXXX"/>
				<acme:input-textbox code="student.enrolment.form.label.expiryDate" path="expiryDate" placeholder="student.enrolment.form.expiryDate.placeholder"/>
				<acme:input-textbox code="student.enrolment.form.label.cvc" path="cvc" placeholder="XXX"/>
			</jstl:if>
			<jstl:if test="${!draftMode}">
				<acme:input-textbox code="student.enrolment.form.label.cardLowerNibble" path="cardLowerNibble"/>
			</jstl:if>
	</jstl:if>
	
 	<jstl:choose>
 		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|finalize')}">	
			<c:if test="${draftMode}">
				<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
				<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
				<acme:submit code="student.enrolment.form.button.finalize" action="/student/enrolment/finalize"/>
			</c:if>	
			<jstl:if test="${!draftMode}" >
				<acme:button code="student.enrolment.form.button.activities" action="/student/activity/list?enrolmentId=${id}"/>
			</jstl:if>
 		</jstl:when>
 		<jstl:when test="${_command == 'create'}">
 			<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
 		</jstl:when>
 	</jstl:choose>
 			
 </acme:form>