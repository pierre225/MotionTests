package com.pierre.domain.models

data class DomainMotion(
    val positions: List<DomainPosition>,
    val startTime: Long,
    val endTime: Long,
    val exceeded: Boolean
)