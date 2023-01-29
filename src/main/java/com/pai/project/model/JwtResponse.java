package com.pai.project.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwtToken;

}
