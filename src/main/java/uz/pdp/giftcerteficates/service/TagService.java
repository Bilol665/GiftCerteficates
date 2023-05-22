package uz.pdp.giftcerteficates.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.giftcerteficates.dto.TagCreateDto;
import uz.pdp.giftcerteficates.entity.GiftCertificate;
import uz.pdp.giftcerteficates.entity.Tag;
import uz.pdp.giftcerteficates.repository.GiftRepository;
import uz.pdp.giftcerteficates.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final GiftRepository giftRepository;
    public Tag add(TagCreateDto createDto) {
        Tag map = modelMapper.map(createDto, Tag.class);
        return tagRepository.save(map);
    }
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
    public void delete(UUID id) {
        tagRepository.deleteById(id);
    }
    public Tag getById(UUID id) {
        return tagRepository.findById(id).orElse(null);
    }
    public void deleteTag(UUID id) {
        List<GiftCertificate> all = giftRepository.findAll();
        for(GiftCertificate g:all) {
            for(Tag t:g.getTags()) {
                if(t.getId().equals(id)) {
                    List<Tag> tags = g.getTags();
                    tags.remove(t);
                    g.setTags(tags);
                    break;
                }
            }
        }
        giftRepository.saveAll(all);
        delete(id);
    }
    public Tag searchTagByName(String tag_name) {
        return tagRepository.searchTagByNameContainsIgnoreCase(tag_name);
    }
}
