package com.crazicrafter1.jripper.types;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * The building block for members
 */
public interface IBaseMember extends IBase {
    String getDescriptor();

    /**
     * The class this thing is contained within
     * @return the class
     */
    IBaseClass getBaseClass();

    default UUID getUUID() {
        return UUID.nameUUIDFromBytes(
                (getName() + " " + getDescriptor()).getBytes(StandardCharsets.UTF_8)
        );
    }
}
