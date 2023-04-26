
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="any.course.form.label.code" path="code"/>
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textarea code="any.course.form.label.summary" path="summary"/>
	<jstl:choose>
		<jstl:when test="${not empty courseType }">
			<acme:input-textbox code="any.course.form.label.courseType" path="courseType" readonly="true"/>
		</jstl:when>
	</jstl:choose>
	<acme:input-money code="any.course.form.label.price" path="price"/>
	<acme:input-url code="any.course.form.label.link" path="link"/>

</acme:form>