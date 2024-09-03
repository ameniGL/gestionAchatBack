package com.alibou.security.auth;


import com.alibou.security.demo.FavorisRepository;
import com.alibou.security.user.Favoris;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavorisService {

    @Autowired
    private FavorisRepository favorisRepository;

    public void saveFavoris(Favoris favoris) {
        favorisRepository.save(favoris);
    }
}
