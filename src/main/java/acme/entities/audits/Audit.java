
package acme.entities.audits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank()
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$", message = "{validation.code}")
	protected String			code;

	@NotBlank()
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank()
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank()
	@Length(max = 100)
	protected String			weakPoints;

	@NotNull
	protected Boolean			draftMode;

	protected int				mark;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Auditor			auditor;


	public Boolean isDraftMode() {
		return this.getDraftMode();
	}

}
