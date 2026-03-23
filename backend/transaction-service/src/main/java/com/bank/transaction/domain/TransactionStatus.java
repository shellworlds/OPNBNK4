package com.bank.transaction.domain;

public enum TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED,
    /** Fraud rules require manual review (e.g. amount above review threshold). */
    UNDER_REVIEW
}
