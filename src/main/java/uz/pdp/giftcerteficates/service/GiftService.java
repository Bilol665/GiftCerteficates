package uz.pdp.giftcerteficates.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
    private final TagService tagService;
    public int add(GiftCreateDto model) {
        GiftCertificate map = modelMapper.map(model, GiftCertificate.class);
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
    public List<GiftCertificate> getGiftByTag(Tag tag) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        main:
        for(GiftCertificate g:getAll()) {
            for(Tag t:g.getTags()) {
                if(t.getId().equals(tag.getId())) {
                    giftCertificates.add(g);
                    continue main;
                }
            }
        }
        return giftCertificates;
    }
    public void assign(UUID giftId,UUID tagId) {
        GiftCertificate byId = getById(giftId);
        List<Tag> tags = byId.getTags();
        tags.add(tagService.getById(tagId));
        byId.setTags(tags);
        giftRepository.save(byId);
    }
    public List<GiftCertificate> getByNameOrDescription(String string) {
        return giftRepository.searchGiftCertificatesByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(string,string);
    }
}
