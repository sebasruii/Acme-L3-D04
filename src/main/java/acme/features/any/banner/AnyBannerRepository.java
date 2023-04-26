
package acme.features.any.banner;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyBannerRepository extends AbstractRepository {

	@Query("SELECT count(b) from Banner b where b.startDate<=:actualDate and b.finishDate>=:actualDate")
	int countBanners(Date actualDate);

	@Query("SELECT b from Banner b where b.startDate<=?1 and b.finishDate>=?1")
	List<Banner> findActiveBanners(Date actualDate, PageRequest pageRequest);

	default Banner findRandomBanner() {
		Banner banner;
		int count;
		int index;
		ThreadLocalRandom random;
		PageRequest page;
		List<Banner> list;

		count = this.countBanners(MomentHelper.getCurrentMoment());
		if (count == 0)
			banner = null;
		else {
			random = ThreadLocalRandom.current();
			index = random.nextInt(0, count);

			page = PageRequest.of(index, 1);
			list = this.findActiveBanners(MomentHelper.getCurrentMoment(), page);
			banner = list.isEmpty() ? null : list.get(0);
		}

		return banner;
	}
}
