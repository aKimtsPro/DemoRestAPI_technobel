package be.technobel.api.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final Class<?> ressourceType;
    private final Object referencedBy;

    public ResourceNotFoundException(Class<?> ressourceType, Object referencedBy) {
        this(
                ressourceType.getSimpleName() + " could not be found via reference " + referencedBy,
                null,
                ressourceType,
                referencedBy
        );
    }

    public ResourceNotFoundException(String message, Class<?> ressourceType, Object referencedBy) {
        this(
                message,
                null,
                ressourceType,
                referencedBy
        );
    }

    public ResourceNotFoundException(Throwable cause, Class<?> ressourceType, Object referencedBy) {
        this(
                ressourceType.getSimpleName() + " could not be found via reference " + referencedBy,
                cause,
                ressourceType,
                referencedBy
        );
    }

    public ResourceNotFoundException(String message, Throwable cause, Class<?> ressourceType, Object referencedBy) {
        super(message, cause);
        this.ressourceType = ressourceType;
        this.referencedBy = referencedBy;
    }
}
