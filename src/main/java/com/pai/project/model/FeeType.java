package com.pai.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FeeType {
    SUBSCRIPTION("SUBSCRIPTION"),
    DOWNLOAD_BOOK("DOWNLOAD_BOOK"),
    DOWNLOAD_FILM("DOWNLOAD_FILM"),
    DOWNLOAD_GAME("DOWNLOAD_GAME");

    @Getter
    private final String code;
}
