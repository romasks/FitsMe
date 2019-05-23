package ru.fitsme.android.presentation.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <V> the input type (base model)
 * @param <D> the view model output type
 */
interface Mapper<out V, in D> {

    fun mapToViewModel(type: D): V

}