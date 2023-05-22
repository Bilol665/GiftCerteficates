package uz.pdp.giftcerteficates.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.giftcerteficates.dto.TagCreateDto;
import uz.pdp.giftcerteficates.entity.GiftCertificate;
import uz.pdp.giftcerteficates.entity.Tag;
import uz.pdp.giftcerteficates.service.GiftService;
import uz.pdp.giftcerteficates.service.TagService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/tag")
public class TagController {
    private final TagService tagService;
    private final GiftService giftService;
    @PostMapping(value = "/add/{id}")
    @ResponseBody
    public String add(
            @PathVariable UUID id,
            @RequestBody TagCreateDto tagCreateDto
    ) {
        Tag add = tagService.add(tagCreateDto);
        GiftCertificate byId = giftService.getById(id);
        List<Tag> tags = byId.getTags();
        tags.add(add);
        byId.setTags(tags);
        giftService.justSave(byId);
        return "Tag added";
    }
    @GetMapping(value = "/get-all")
    @ResponseBody
    public List<Tag> getAll() {
        return tagService.getAll();
    }
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public String delete(
            @PathVariable UUID id
    ) {
        tagService.deleteTag(id);
        return "Deleted Successfully";
    }
}
