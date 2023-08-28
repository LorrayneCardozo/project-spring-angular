package project.backend.domain.enums;

import lombok.Getter;

@Getter
public enum SchedulingStatus {
    AGENDADO("Agendado"),
    CONCLUIDO("Concluido"),
    CANCELADO("Cancelado");

    private final String description;
    SchedulingStatus(String description) {
        this.description = description;
    }
}
