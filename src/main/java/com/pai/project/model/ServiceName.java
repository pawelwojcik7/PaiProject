package com.pai.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ServiceName {
    DEFAULT("DEFAULT"), NORMAL("NORMAL"), PREMIUM("PREMIUM");

    @Getter
    private final String code;
}
