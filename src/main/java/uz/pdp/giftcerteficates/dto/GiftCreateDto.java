package uz.pdp.giftcerteficates.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.giftcerteficates.entity.Tag;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GiftCreateDto {
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private List<Tag> tags;
}
