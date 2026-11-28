package br.com.luan.urlshortener.modules.url.repositories;

import br.com.luan.urlshortener.modules.url.entities.UrlEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void should_find_url_by_short_code() {
        var url = new UrlEntity();
        var originalUrl = "https://www.example.com";
        var shortCode = "abc123";

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        urlRepository.save(url);

        Optional<UrlEntity> foundUrl = urlRepository.findByShortCode(shortCode);
        assertThat(foundUrl.isPresent()).isTrue();
        assertThat(foundUrl.get().getOriginalUrl()).isEqualTo(originalUrl);
    }

    @Test
    public void should_not_find_url_by_short_code() {
        var url = new UrlEntity();
        var originalUrl = "https://www.example.com";
        var shortCode = "abc123";

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        urlRepository.save(url);

        var result = urlRepository.findByShortCode("thisCodeNotExists");
        assertThat(result).isEmpty();
    }

    @Test
    public void returns_true_when_the_code_exists() {
        var url = new UrlEntity();
        var originalUrl = "https://www.example.com";
        var shortCode = "abc123";

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        urlRepository.save(url);

        var result = urlRepository.existsByShortCode(shortCode);
        assertThat(result).isTrue();
    }

    @Test
    public void returns_false_when_the_code_not_exists() {
        var url = new UrlEntity();
        var originalUrl = "https://www.example.com";
        var shortCode = "abc123";

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        urlRepository.save(url);

        var result = urlRepository.existsByShortCode("thisCodeNotExists");
        assertThat(result).isFalse();
    }

    @Test
    public void when_saving_a_second_record_with_the_same_code_the_database_rejects_it() {
        var url1 = new UrlEntity();
        var url2 = new UrlEntity();
        var originalUrl = "https://www.example.com";
        var shortCode = "abc123";

        url1.setOriginalUrl(originalUrl);
        url1.setShortCode(shortCode);
        urlRepository.save(url1);

        url2.setOriginalUrl(originalUrl);
        url2.setShortCode(shortCode);

        assertThatThrownBy(() -> urlRepository.saveAndFlush(url2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
