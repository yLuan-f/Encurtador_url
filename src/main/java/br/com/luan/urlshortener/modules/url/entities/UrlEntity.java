package br.com.luan.urlshortener.modules.url.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq")
    @SequenceGenerator(name = "url_seq", sequenceName = "url_id_seq", allocationSize = 50)
    private Long id;

    @NotBlank (message = "A URL original é obrigatória")
    @Column (name = "original_url", nullable = false, length = 2048)
    private String originalUrl;
    @NotBlank (message = "A URL curta é obrigatória")
    @Column (name = "short_url", nullable = false, length =32, unique = true)
    private String shortCode;
}
