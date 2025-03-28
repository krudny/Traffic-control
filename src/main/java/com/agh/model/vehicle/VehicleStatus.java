package com.agh.model.vehicle;

import lombok.Getter;

@Getter
public enum VehicleStatus {
    // Stany oczekiwania
    AT_QUEUE("Pojazd czeka w kolejce na wjazd na skrzyżowanie"),
    STOPPED_AT_RED_LIGHT("Pojazd znajduje się na czele kolejki i czeka na zielone światło"),
    AWAITING_CLEAR_PATH("Pojazd czeka na możliwość przejazdu mimo zielonego światła"),

    // Stany ruchu
    APPROACHING_INTERSECTION("Pojazd zbliża się do skrzyżowania"),
    IN_INTERSECTION("Pojazd aktualnie znajduje się na skrzyżowaniu"),
    LEAVING_INTERSECTION("Pojazd pomyślnie przejechał przez skrzyżowanie");

    private final String description;

    VehicleStatus(String description) {
        this.description = description;
    }
}
