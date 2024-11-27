package com.example.musicstore.service;

import com.example.musicstore.model.*;
import com.example.musicstore.repository.*;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public CartService(CartRepository cartRepository, AlbumRepository albumRepository, SongRepository songRepository) {
        this.cartRepository = cartRepository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    public void addToCart(Cart cart, ItemType itemType, Long itemId) {
        if (itemType == ItemType.ALBUM) {
            Album album = albumRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Album not found!"));
            CartItem item = new CartItem();
            item.setAlbum(album);
            cart.addItem(item);
        } else if (itemType == ItemType.SONG) {
            Song song = songRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Song not found!"));
            CartItem item = new CartItem();
            item.setSong(song);
            cart.addItem(item);
        }
    }


    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found!"));
    }

    public void addAlbumToCart(User user, Long albumId, int quantity) {
        Cart cart = getCartByUser(user);
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new RuntimeException("Album not found!"));

        CartItem item = new CartItem();
        item.setAlbum(album);
        item.setQuantity(quantity);
        cart.addItem(item);

        cartRepository.save(cart);
    }

    public void addSongToCart(User user, Long songId, int quantity) {
        Cart cart = getCartByUser(user);
        Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found!"));

        CartItem item = new CartItem();
        item.setSong(song);
        item.setQuantity(quantity);
        cart.addItem(item);

        cartRepository.save(cart);
    }



    public void removeFromCart(Long cartId, Long cartItemId) {
    }
}
