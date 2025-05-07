package ru.kata.spring.boot_security.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kata.spring.boot_security.demo.model.SocialMedia;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class SocialMediaRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private TestDataFactory factory;

    @Test
    void testCreateSocialMedia() {
        var user = factory.createTestUser("john");
        var social = factory.createSocialMedia(user);
        social = socialMediaRepository.save(social);

        assertNotNull(social.getId());
        assertEquals("Twitter", social.getPlatform());
        assertEquals("john_doe", social.getUsername());
        assertEquals(user.getId(), social.getUser().getId());
    }

    @Test
    void testFindSocialMediaById() {
        var user = factory.createTestUser("alice");
        var social = factory.createSocialMedia(user);
        social = socialMediaRepository.save(social);

        var found = socialMediaRepository.findById(social.getId());

        assertTrue(found.isPresent());
        assertEquals("john_doe", found.get().getUsername());
    }

    @Test
    void testUpdateSocialMedia() {
        var user = factory.createTestUser("michael");
        var social = factory.createSocialMedia(user);
        social = socialMediaRepository.save(social);

        social.setUsername("mike_the_great");
        social.setPlatform("Instagram");
        var updated = socialMediaRepository.save(social);

        assertEquals("Instagram", updated.getPlatform());
        assertEquals("mike_the_great", updated.getUsername());
    }

    @Test
    void testDeleteSocialMedia() {
        var user = factory.createTestUser("kate");
        var social = factory.createSocialMedia(user);
        social = socialMediaRepository.save(social);

        socialMediaRepository.delete(social);
        var found = socialMediaRepository.findById(social.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAllByUserId() {
        var user = factory.createTestUser("bob");
        var social1 = factory.createSocialMedia(user);
        var social2 = new SocialMedia(null, "LinkedIn", "bob_corp", "https://linkedin.com/bob_corp", user);

        socialMediaRepository.saveAll(List.of(social1, social2));

        var result = socialMediaRepository.findAllByUserId(user.getId());

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getPlatform().equals("Twitter")));
        assertTrue(result.stream().anyMatch(s -> s.getPlatform().equals("LinkedIn")));
    }
}
