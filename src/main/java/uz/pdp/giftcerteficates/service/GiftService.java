package uz.pdp.giftcerteficates.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import uz.pdp.giftcerteficates.controller.exceptions.GiftNameNotTrueException;
import uz.pdp.giftcerteficates.controller.exceptions.GiftNotFoundException;
import uz.pdp.giftcerteficates.controller.exceptions.NoGiftWithThisNameFoundException;
import uz.pdp.giftcerteficates.dto.GiftCreateDto;
import uz.pdp.giftcerteficates.entity.GiftCertificate;
import uz.pdp.giftcerteficates.entity.Tag;
import uz.pdp.giftcerteficates.repository.GiftRepository;
import uz.pdp.giftcerteficates.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;
    public int add(GiftCreateDto model) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        GiftCertificate map = modelMapper.map(model, GiftCertificate.class);
//        modelMapper.map(model.g);
        giftRepository.save(map);
        return 1;
    }
    public List<GiftCertificate> getAll() {
        return giftRepository.findAll();
    }
    public void update(UUID id,GiftCreateDto createDto) {
        GiftCertificate gift = getById(id);
        modelMapper.map(createDto,gift);
        giftRepository.save(gift);
    }
    public GiftCertificate getById(UUID id) {
        Optional<GiftCertificate> byId = giftRepository.findById(id);
        return byId.orElse(null);
    }
    public void deleteById(UUID id) {
        giftRepository.deleteById(id);
    }
    public void justSave(GiftCertificate giftCertificate) {
        giftRepository.save(giftCertificate);
    }
    public List<GiftCertificate> getGiftByTag(String tagName) {
        if (tagName.isBlank()) {
            throw new GiftNameNotTrueException();
        }
        Tag tag = tagRepository.searchTagByNameContainsIgnoreCase(tagName);
        return giftRepository.findGiftCertificatesByTagsContains(tag);
    }
    public void assign(UUID giftId,UUID tagId) {
        GiftCertificate byId = getById(giftId);
        List<Tag> tags = byId.getTags();
        tags.add(tagRepository.findById(tagId).orElse(null));
        byId.setTags(tags);
        giftRepository.save(byId);
    }
    public List<GiftCertificate> getByNameOrDescription(String string,String description) {
        try {
            return giftRepository.searchGiftCertificatesByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(string, description);
        }catch (Exception e) {
            throw new GiftNotFoundException();
        }
    }

    public List<GiftCertificate> find(String tagName, String name, String description) {
        try {
            Tag tag = tagRepository.findFirstByNameContainsIgnoreCase(tagName);
            if (tag == null) {
                return getByNameOrDescription(name,description);
            }
            return giftRepository.findGiftCertificatesByTagsContainsOrNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(
                    tag, name, description
            );
        }catch (Exception e) {
            throw new NoGiftWithThisNameFoundException(e.getMessage());
        }
    }
}
