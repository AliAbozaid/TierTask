package app.tier.map.presentation.di

import app.tier.map.presentation.TierMapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        TierMapViewModel(
            mapUseCase = get(),
            dispatcher = get()
        )
    }
}
