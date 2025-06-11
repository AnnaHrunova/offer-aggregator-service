package lv.klix.oas.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends CrudRepository<Offer, UUID> {

    Optional<Offer> findByIdAndApplicationId(UUID id, UUID applicationId);
    List<Offer> findByApplicationIdAndIdNot(UUID id, UUID selectedOfferId);
}
