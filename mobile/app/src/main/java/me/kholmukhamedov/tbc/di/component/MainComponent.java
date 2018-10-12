package me.kholmukhamedov.tbc.di.component;

import dagger.Subcomponent;
import me.kholmukhamedov.tbc.di.module.MainModule;
import me.kholmukhamedov.tbc.di.scope.MainScope;
import me.kholmukhamedov.tbc.presentation.view.MainActivity;

@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity target);

    @Subcomponent.Builder
    interface Builder {

        Builder mainModule(MainModule module);

        MainComponent build();

    }

}
