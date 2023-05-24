package uz.pdp.giftcerteficates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.giftcerteficates.entity.Tag;

import java.util.UUID;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Tag searchTagByNameContainsIgnoreCase(String tagName);
    Tag findFirstByNameContainsIgnoreCase(String tagName);
}
