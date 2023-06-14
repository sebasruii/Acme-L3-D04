
package acme.entities.tutorialSessions;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.NatureType.NatureType;
import acme.entities.tutorials.Tutorial;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class TutorialSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@NotNull
	protected NatureType		type;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@FutureOrPresent
	protected Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@FutureOrPresent
	protected Date				finishDate;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Boolean isDraftMode() {
		return this.tutorial.isDraftMode();
	}


	// Relationships ----------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial tutorial;
}
