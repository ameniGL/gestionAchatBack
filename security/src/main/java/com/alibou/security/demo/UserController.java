package com.alibou.security.demo;


import com.alibou.security.auth.UserService;
import com.alibou.security.user.Favoris;
import com.alibou.security.user.User;
import com.example.recherche.Entities.Produit;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final WebClient.Builder webClientBuilder;
    private final FavorisRepository favoriteRepository;
    private final UserService userService;

    @Autowired
    public UserController(WebClient.Builder webClientBuilder,
                               FavorisRepository favoriteRepository,
                               UserService userService) {
        this.webClientBuilder = webClientBuilder;
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
    }


    @PostMapping("/like/{productId}/{userId}")
    public ResponseEntity<?> likeProduct(@PathVariable Long productId, @PathVariable Long userId) throws Exception {
        // Check if the user has already liked this product
        Optional<Favoris> existingFavoris = favoriteRepository.findByUserIdAndProductId(userId, productId);

        if (existingFavoris.isPresent()) {
            // Throw an exception if the product has already been liked
            throw new Exception("Product already liked by the user.");
        }

        // If not already liked, proceed to like the product
        Favoris favoris = new Favoris();
        favoris.setUserId(userId);
        favoris.setProductId(productId);
        favoris.setLikedAt(LocalDateTime.now());
        favoriteRepository.save(favoris);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/favoris/{userId}")
    public List<Produit> getUserFavoris(@PathVariable Long userId) {
        // Step 1: Retrieve favorite product IDs from the User microservice
        List<Long> productIds = favoriteRepository.findByUserId(userId)
                .stream()
                .map(Favoris::getProductId)
                .collect(Collectors.toList());

        // Step 2: Fetch product details from the Product microservice
        String productServiceUrl = "http://localhost:8090/api/v1/demo-controller/byid/";  // Use your actual Product microservice URL

        List<Produit> products = productIds.stream()
                .map(productId -> webClientBuilder.build()
                        .get()
                        .uri(productServiceUrl + "/" + productId)  // Adjust the URI as needed
                        .retrieve()
                        .bodyToMono(Produit.class)
                        .onErrorResume(e -> {
                            // Log error and return null if the product is not found or there's an error
                            System.err.println("Error fetching product with ID " + productId + ": " + e.getMessage());
                            return Mono.empty();  // Handle error gracefully by returning empty Mono
                        })
                        .block())  // Blocking for simplicity, can be optimized for async
                .filter(Objects::nonNull)  // Filter out null results due to errors
                .collect(Collectors.toList());

        return products;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}

