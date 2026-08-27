package javacode.holding;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateHoldingRequest(

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal quantity

) {}