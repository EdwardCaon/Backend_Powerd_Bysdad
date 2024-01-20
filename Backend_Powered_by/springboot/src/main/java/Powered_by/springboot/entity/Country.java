package Powered_by.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name ="country")
public class Country {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_country")
        private int idCountry;

        @NotBlank
        @Column(name = "name_country")
        private String nameCountry;

        @Size(max = 255)
        @Column(name = "flag")
        private String flag;






}
