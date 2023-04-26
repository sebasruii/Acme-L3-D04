<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title" width="70%"/>
	<acme:list-column code="lecturer.lecture.list.label.estimatedLearningTime" path="estimatedLearningTime" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.lectureType" path="lectureType" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list-all'}">
	<acme:button code="lecturer.lecture.list.button.create" action="/lecturer/lecture/create"/>
</jstl:if>

<jstl:if test="${_command == 'list' && draftMode == true}">
	<acme:button code="lecturer.lectureCourse.form.button.add" action="/lecturer/lecture-course/add?courseId=${courseId}"/>
</jstl:if>	
	