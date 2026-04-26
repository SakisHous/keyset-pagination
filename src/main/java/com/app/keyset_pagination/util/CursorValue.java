package com.app.keyset_pagination.util;

import java.time.Instant;

public sealed interface CursorValue
        permits CursorValue.OfInstant, CursorValue.OfDouble {

    record OfInstant(Instant value) implements CursorValue {}
    record OfDouble (Double  value) implements CursorValue {}
}