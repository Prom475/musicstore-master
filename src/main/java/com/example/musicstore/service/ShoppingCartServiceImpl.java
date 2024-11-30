package com.example.musicstore.service;

import com.example.musicstore.model.Song;
import com.example.musicstore.repository.SongRepository;
import com.example.musicstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Service
@Scope("singleton")
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final SongRepository productRepository;
    private Map<Song, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(SongRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Song product) {
        products.put(product, products.getOrDefault(product, 0) + 1);
    }

    @Override
    public void removeProduct(Song product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1) {
                products.put(product, products.get(product) - 1);
            } else {
                products.remove(product);
            }
        }
    }

    @Override
    public Map<Song, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public void checkout() {
        products.clear();
    }

    @Override
    public Double getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Song, Integer> entry : products.entrySet()) {
            total = total.add(BigDecimal.valueOf(entry.getKey().getPrice()).multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return total.doubleValue();
    }

    @Override
    public int getItemCount() {
        return products.values().stream().mapToInt(Integer::intValue).sum();
    }
}