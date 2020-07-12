package com.server.dao;

import com.server.builder.UserBuilder;
import com.server.model.Tier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private UserDao userDao;

    @Before
    public void setup() {
        userDao = new UserDaoImpl();
    }

    @Test
    public void testSingleProfile() {
        userDao.addProfile(UserBuilder.aUserBuilder().withId("768").withEmail("xyz@gmail.com").withName("Tesla").withTier(Tier.TIER_A).build());
        assertNotNull(userDao.getProfile("768"));
        assertEquals(userDao.getProfile("768").getEmail(), "xyz@gmail.com");
        assertEquals(userDao.getProfile("768").getName(), "Tesla");
        assertEquals(userDao.getProfile("768").getTier(), Tier.TIER_A);
    }

    @Test
    public void testMultipleProfile() {
        userDao.addProfile(UserBuilder.aUserBuilder().withId("768").withEmail("xyz@gmail.com").withName("Tesla").withTier(Tier.TIER_A).build());
        userDao.addProfile(UserBuilder.aUserBuilder().withId("123").withEmail("xyz1@gmail.com").withName("Tesla1").withTier(Tier.TIER_B).build());
        userDao.addProfile(UserBuilder.aUserBuilder().withId("829").withEmail("xyz2@gmail.com").withName("Tesla2").withTier(Tier.TIER_C).build());

        assertEquals(userDao.getAllProfiles().size(), 3);
        assertNotNull(userDao.getProfile("829"));
        assertNull(userDao.getProfile("768343"));

        assertEquals(userDao.getProfile("768").getEmail(), "xyz@gmail.com");
        assertEquals(userDao.getProfile("768").getName(), "Tesla");
        assertEquals(userDao.getProfile("768").getTier(), Tier.TIER_A);

        assertEquals(userDao.getProfile("123").getEmail(), "xyz1@gmail.com");
        assertEquals(userDao.getProfile("123").getName(), "Tesla1");
        assertEquals(userDao.getProfile("123").getTier(), Tier.TIER_B);

        assertEquals(userDao.getProfile("829").getEmail(), "xyz2@gmail.com");
        assertEquals(userDao.getProfile("829").getName(), "Tesla2");
        assertEquals(userDao.getProfile("829").getTier(), Tier.TIER_C);
    }

    @Test
    public void testEmptyProfiles() {
        assertTrue(userDao.getAllProfiles().isEmpty());
    }
}
