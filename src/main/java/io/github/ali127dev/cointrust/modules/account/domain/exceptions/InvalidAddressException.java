package io.github.ali127dev.cointrust.modules.account.domain.exceptions;

import io.github.ali127dev.cointrust.shared.domain.exceptions.InvalidDataException;

public class InvalidAddressException extends InvalidDataException {
    public InvalidAddressException(Integer min, Integer max) {
        super(buildMessage(min, max));
    }

    private static String buildMessage(Integer min, Integer max) {
        var message = new StringBuilder("Address length ");

        if (min != null && max != null) {
            message.append("must be between ")
                    .append(min)
                    .append(" and ")
                    .append(max);
        } else if (min != null) {
            message.append("must be at least ")
                    .append(min);
        } else if (max != null) {
            message.append("must be at most ")
                    .append(max);
        } else {
            message.append("is invalid");
        }

        return message.toString();
    }
}
