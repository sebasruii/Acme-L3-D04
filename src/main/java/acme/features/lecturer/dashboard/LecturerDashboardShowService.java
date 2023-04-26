
package acme.features.lecturer.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.LecturerDashboard;
import acme.forms.Statistics;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	@Autowired
	protected LecturerDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int lecturerId;
		LecturerDashboard dashboard;
		Statistics courseStatistics;
		Statistics theoryLecturesStatistics;
		Statistics handsOnLecturesStatistics;
		Statistics lectureStatistics;
		Map<String, Statistics> lectureStatisticsMap;

		Integer numberOfTheoryLectures;
		Integer numberOfHandsOnLectures;

		Double averageLearningTimeOfLectures;
		Double maxLearningTimeOfLectures;
		Double minLearningTimeOfLectures;
		Double stdevLearningTimeOfLectures;

		Double averageLearningTimeOfCourses;
		Double maxLearningTimeOfCourses;
		Double minLearningTimeOfCourses;
		Double stdevLearningTimeOfCourses;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();

		dashboard = new LecturerDashboard();
		courseStatistics = new Statistics();
		theoryLecturesStatistics = new Statistics();
		handsOnLecturesStatistics = new Statistics();
		lectureStatistics = new Statistics();
		lectureStatisticsMap = new HashMap<String, Statistics>();

		numberOfTheoryLectures = this.repository.totalNumberOfTheoryLectures(lecturerId);
		numberOfHandsOnLectures = this.repository.totalNumberOfHandsOnLectures(lecturerId);

		averageLearningTimeOfLectures = this.repository.averageLearningTimeOfLectures(lecturerId);
		maxLearningTimeOfLectures = this.repository.maxLearningTimeOfLectures(lecturerId);
		minLearningTimeOfLectures = this.repository.minLearningTimeOfLectures(lecturerId);
		stdevLearningTimeOfLectures = this.repository.stdevLearningTimeOfLectures(lecturerId);

		averageLearningTimeOfCourses = this.repository.averageLearningTimeOfCourses(lecturerId);
		maxLearningTimeOfCourses = this.repository.maxLearningTimeOfCourses(lecturerId);
		minLearningTimeOfCourses = this.repository.minLearningTimeOfCourses(lecturerId);
		stdevLearningTimeOfCourses = this.repository.stdevLearningTimeOfCourses(lecturerId);

		theoryLecturesStatistics.setCount(numberOfTheoryLectures);
		handsOnLecturesStatistics.setCount(numberOfHandsOnLectures);

		lectureStatistics.setAverage(averageLearningTimeOfLectures);
		lectureStatistics.setMaximum(maxLearningTimeOfLectures);
		lectureStatistics.setMinimum(minLearningTimeOfLectures);
		lectureStatistics.setStDev(stdevLearningTimeOfLectures);

		courseStatistics.setAverage(averageLearningTimeOfCourses);
		courseStatistics.setMaximum(maxLearningTimeOfCourses);
		courseStatistics.setMinimum(minLearningTimeOfCourses);
		courseStatistics.setStDev(stdevLearningTimeOfCourses);

		lectureStatisticsMap.put("GENERAL", lectureStatistics);
		lectureStatisticsMap.put("THEORY", theoryLecturesStatistics);
		lectureStatisticsMap.put("HANDS_ON", handsOnLecturesStatistics);

		dashboard.setStatisticsByLectureType(lectureStatisticsMap);
		dashboard.setStatisticsByCourse(courseStatistics);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"statisticsByLectureType", "statisticsByCourse");

		super.getResponse().setData(tuple);
	}
}
