package uz.pdp.giftcerteficates.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Tag extends BaseEntity{
    private String name;
}
