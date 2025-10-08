package com.google.firebase.firestore.local;

public enum QueryPurpose {
    LISTEN,
    EXISTENCE_FILTER_MISMATCH,
    EXISTENCE_FILTER_MISMATCH_BLOOM,
    LIMBO_RESOLUTION
}
