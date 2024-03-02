package com.example.foobarpart2.ui.viewmodels;

import com.example.foobarpart2.db.entity.Token;
import com.example.foobarpart2.repository.TokenRepository;

public class TokenViewModel {
    private TokenRepository tokenRepository;

    public TokenViewModel() {
        this.tokenRepository = new TokenRepository();
    }

    public Token getToken() {
        return tokenRepository.get();
    }
}
