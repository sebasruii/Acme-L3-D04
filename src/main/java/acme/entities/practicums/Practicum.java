
package acme.entities.practicums;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	// Serialisation identifier ----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes ------------------------------------------------------------
	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}\\d{4}$", message = "{validation.code1}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Company			company;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Course				course;

	@NotNull
	protected Boolean			draftMode;
}
