package md.usm.model.user;

import javax.persistence.Entity;
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
public class User {

    @Id
    @NonNull
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private String phone;

    @NonNull
    private String password;

    @NonNull
    private String confirmPassword;

}
