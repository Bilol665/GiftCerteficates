package uz.pdp.giftcerteficates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.giftcerteficates.entity.GiftCertificate;
import uz.pdp.giftcerteficates.entity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface GiftRepository extends JpaRepository<GiftCertificate, UUID> {
    List<GiftCertificate> searchGiftCertificatesByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);
}
