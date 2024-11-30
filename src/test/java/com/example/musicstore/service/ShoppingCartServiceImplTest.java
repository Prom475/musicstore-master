package com.example.musicstore.service;

import com.example.musicstore.model.Song;
import com.example.musicstore.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartServiceImplTest {

    private ShoppingCartServiceImpl shoppingCartService;
    private SongRepository songRepository;

    @BeforeEach
    void setUp() {
        songRepository = Mockito.mock(SongRepository.class);
        shoppingCartService = new ShoppingCartServiceImpl(songRepository);
    }

    @Test
    void testRemoveProduct() {
        Song song = new Song();
        song.setId(1L);
        song.setTitle("Test Song");
        song.setPrice(10.0);

        shoppingCartService.addProduct(song);
        shoppingCartService.addProduct(song);

        assertEquals(2, shoppingCartService.getProductsInCart().get(song));

        shoppingCartService.removeProduct(song);
        assertEquals(1, shoppingCartService.getProductsInCart().get(song));

        shoppingCartService.removeProduct(song);
        assertFalse(shoppingCartService.getProductsInCart().containsKey(song));
    }
}