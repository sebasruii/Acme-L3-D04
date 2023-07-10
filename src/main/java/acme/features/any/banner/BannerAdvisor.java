
package acme.features.any.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class BannerAdvisor {

	// Internal state ---------------------------------------------------------

	@Autowired
	private BannerRepository repository;

	// Constructors -----------------------------------------------------------


	@ModelAttribute("banner")
	public Banner getBanner() {
		Banner result;
		result = this.repository.findRandomBanner();
		return result;
	}
}
