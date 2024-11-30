package com.example.musicstore.service;

import com.example.musicstore.model.Song;
import java.util.Map;

public interface ShoppingCartService {
    void addProduct(Song product);
    void removeProduct(Song product);
    Map<Song, Integer> getProductsInCart();
    void checkout();
    Double getTotal();
    int getItemCount();
}