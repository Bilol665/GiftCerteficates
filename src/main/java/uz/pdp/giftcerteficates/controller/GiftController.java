package uz.pdp.giftcerteficates.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.giftcerteficates.controller.exceptions.GiftNameNotTrueException;
import uz.pdp.giftcerteficates.controller.exceptions.GiftNotFoundException;
import uz.pdp.giftcerteficates.dto.GiftCreateDto;
import uz.pdp.giftcerteficates.entity.GiftCertificate;
import uz.pdp.giftcerteficates.service.GiftService;
import uz.pdp.giftcerteficates.service.TagService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class GiftController {
    private final GiftService giftService;

    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<Object> addGift(
            @RequestBody GiftCreateDto giftCreateDto
    ) {
        if (giftCreateDto.getName().isBlank()) {
            throw new GiftNameNotTrueException();
        }
        giftService.add(giftCreateDto);
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/get-all")
    @ResponseBody
    public List<GiftCertificate> getAll() {
        return giftService.getAll();
    }

    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public String update(
            @PathVariable UUID id,
            @RequestBody GiftCreateDto giftCreateDto
    ) {
        giftService.update(id, giftCreateDto);
        return "All good";
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public String delete(
            @PathVariable UUID id
    ) {
        giftService.deleteById(id);
        return "Successfully deleted";
    }

    @GetMapping(value = "/get-by-tags")
    @ResponseBody
    public ResponseEntity<Object> getByTags(
            @RequestParam(defaultValue = "justNotFoundIfYouWillNotGiveAllRight",name = "tag_name",required = false) String tagName,
            @RequestParam(defaultValue = "",required = false) String name,
            @RequestParam(defaultValue = "",required = false) String description
    ) {
        List<GiftCertificate> giftByTag = giftService.find(tagName, name, description);
        return new ResponseEntity<>(giftByTag, HttpStatus.OK);
    }

    @PutMapping(value = "/assign-tag2gift/{gift_id}")
    @ResponseBody
    public ResponseEntity<Object> assign(
            @PathVariable UUID gift_id,
            @RequestParam UUID tag_id
    ) {
        try {
            giftService.assign(gift_id, tag_id);
        } catch (Exception e) {
            throw new GiftNotFoundException();
        }
        return new ResponseEntity<>("Tag assigned to gift", HttpStatus.OK);
    }


}
