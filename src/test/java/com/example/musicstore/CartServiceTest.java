package com.example.musicstore.service;

import com.example.musicstore.model.Cart;
import com.example.musicstore.model.CartItem;
import com.example.musicstore.model.ItemType;
import com.example.musicstore.model.Song;
import com.example.musicstore.repository.CartRepository;
import com.example.musicstore.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private Song song;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
        song = new Song();
        song.setId(1L);
        song.setTitle("Test Song");
        song.setPrice(9.99);
    }

    @Test
    void testAddToCart() {
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));

        cartService.addToCart(cart, ItemType.SONG, 1L);

        assertEquals(1, cart.getItems().size());
        CartItem cartItem = cart.getItems().get(0);
        assertEquals("Test Song", cartItem.getSong().getTitle());
        assertEquals(9.99, cartItem.getSong().getPrice());

        verify(cartRepository, times(1)).save(any(Cart.class));
    }
}