
package acme.entities.enrolment;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.entities.workbook.Activity;
import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$", message = "{validation.code}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			motivation;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	@Length(max = 75)
	protected String			cardHolderName;

	@Length(max = 4)
	protected String			cardLowerNibble;

	// Relationships ---------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Student			student;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Course			course;

	// Derivated methods ----------------------------------------------------


	public Double workTime(final Collection<Activity> activities) {
		double res = 0.0;
		if (!activities.isEmpty())
			for (final Activity activity : activities) {
				Double hours;
				final Date startDate = activity.getStartDate();
				final Date endDate = activity.getFinishDate();
				hours = Math.abs(endDate.getTime() / 3600000. - startDate.getTime() / 3600000.);
				res += hours;
			}
		return res;
	}
}
