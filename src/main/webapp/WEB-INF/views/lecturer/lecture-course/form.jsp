<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
			<acme:input-textbox code="lecturer.lectureCourse.form.label.lecture" path="lecture.title" readonly="true"/>
			<acme:input-textbox code="lecturer.lectureCourse.form.label.course" path="course.code" readonly="true"/>
			<acme:submit code="lecturer.lectureCourse.form.button.delete" action="/lecturer/lecture-course/delete?id=${id}"/>		
		</jstl:when>	
		<jstl:when test="${_command == 'add'}">
			<acme:input-select code="lecturer.lectureCourse.form.label.select-lecture" path="lecture" choices="${lectures}"/>
			<acme:submit code="lecturer.lectureCourse.form.button.add" action="/lecturer/lecture-course/add?courseId=${courseId}"/>		
		</jstl:when>	
	</jstl:choose>
</acme:form>
