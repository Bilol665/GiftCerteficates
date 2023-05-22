package uz.pdp.giftcerteficates.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "gift_certificate")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GiftCertificate extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags;
}
