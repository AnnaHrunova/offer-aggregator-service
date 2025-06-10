package lv.klix.oas.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SelectOfferRequest {

    @NotNull
    private UUID id;

    @NotNull
    private UUID applicationId;
}