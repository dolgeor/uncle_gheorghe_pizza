package md.usm.model.product;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // @NonNull
    private Category category;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private Double price;

    private Integer weight;

    private Integer volume;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<String> images;
}
