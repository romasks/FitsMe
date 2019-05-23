package ru.fitsme.android.data.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <RES> the response input type
 * @param <REQ> the request input type
 * @param <D> the model base type
 */
interface Mapper<RES, REQ, D> {

    fun mapFromEntity(type: RES): D

    fun mapToEntity(type: D): REQ

}