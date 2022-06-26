package io.sharan.goodreads.business.mapper

interface Mapper<E, D> {
    fun toDomain(entity: E): D
    fun toEntity(domain: D): E
}